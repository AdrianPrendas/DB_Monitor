
startup nomount pfile = C:\Quiz_1\parametros\initqp.ora

create database "Quiz_1"
logfile group 1 ('C:\Quiz_1\redo\redo1.log') size 100M,
group 2 ('C:\Quiz_1\redo\redo2.log') size 100M,
group 3 ('C:\Quiz_1\redo\redo3.log') size 100M
character set WE8ISO8859P1
national character set utf8
datafile 'C:\Quiz_1\system\system.dbf' size 500M autoextend on next 10M maxsize
unlimited extent management local
sysaux datafile 'C:\Quiz_1\sysaux\sysaux.dbf' size 100M autoextend on next 10M
maxsize unlimited
undo tablespace undotbs1 datafile 'C:\Quiz_1\undo\undotbs1.dbf' size 100M
default temporary tablespace temp tempfile 'C:\Quiz_1\temp\temp01.dbf' size
100M;


@'C:\oraclexe\app\oracle\product\11.2.0\server\rdbms\admin\catalog.sql'

@'C:\oraclexe\app\oracle\product\11.2.0\server\rdbms\admin\catproc.sql'

drop tablespace A1  including contents and datafiles;
drop tablespace A2  including contents and datafiles;
drop tablespace A3  including contents and datafiles;
drop tablespace A4  including contents and datafiles;
drop tablespace users  including contents and datafiles;

CREATE TABLESPACE A1 DATAFILE 'C:\Quiz_1\A1\A1.dbf' SIZE 10M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;
CREATE TABLESPACE A2 DATAFILE 'C:\Quiz_1\A2\A2.dbf' SIZE 10M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;
CREATE TABLESPACE A3 DATAFILE 'C:\Quiz_1\A3\A3.dbf' SIZE 20M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;
CREATE TABLESPACE A4 DATAFILE 'C:\Quiz_1\A4\A4.dbf' SIZE 20M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;
CREATE TABLESPACE users DATAFILE 'C:\Quiz_1\users\users.dbf' SIZE 20M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;



drop table T1;
drop table T2;
drop table T3;
drop table T4;

CREATE TABLE T1(
a int, --2bytes
b int, --2bytes
c float, --4bytes
d varchar(30) --30bytes
)
TABLESPACE A1
;

CREATE TABLE T2(
x int, 
y int, 
z float, 
w varchar(30)
)
TABLESPACE A2
;

CREATE TABLE T3(
m int, 
n int, 
o float, 
p varchar(30)
)
TABLESPACE A3
;

CREATE TABLE T4(
m int, 
n int, 
o float, 
p varchar(30)
)
TABLESPACE A4
;


CREATE OR REPLACE FUNCTION storageInfo
  RETURN SYS_REFCURSOR
AS
  tablespaces SYS_REFCURSOR;
BEGIN
  OPEN tablespaces FOR  Select t.tablespace_name  "tablespace",
		ROUND(MAX(d.bytes)/1024/1024,2) "tam",
		ROUND(SUM(decode(f.bytes, NULL,0, f.bytes))/1024/1024,2) "free",
		ROUND((MAX(d.bytes)/1024/1024) -(SUM(decode(f.bytes, NULL,0, f.bytes))/1024/1024),2) "used" 
FROM DBA_FREE_SPACE f, DBA_DATA_FILES d,  DBA_TABLESPACES t 
WHERE t.tablespace_name = d.tablespace_name AND 
f.tablespace_name = d.tablespace_name AND  
f.file_id = d.file_id AND 
t.tablespace_name != 'SYSTEM' and 
t.tablespace_name != 'SYSAUX' and 
t.tablespace_name !='UNDOTBS1' and 
t.tablespace_name != 'TEMP' and
t.tablespace_name != 'USERS' 
GROUP BY 
t.tablespace_name,d.file_name,t.pct_increase, t.status  
ORDER BY 4 DESC;
return tablespaces;
END;
/


CREATE OR REPLACE FUNCTION bufferInfo
  RETURN SYS_REFCURSOR
AS
  bufferInf SYS_REFCURSOR;
BEGIN
  OPEN bufferInf FOR  select name, bytes from v$sgainfo where name = 'Maximum SGA Size' union
select name, bytes from v$sgainfo where name='Free SGA Memory Available';
return bufferInf;
END;
/


select df.tablespace_name "Tablespace",
		totalusedspace "Used MB",
		(df.totalspace - tu.totalusedspace) "Free MB",
		df.totalspace "Total MB",
		round(100 * ( (df.totalspace - tu.totalusedspace)/ df.totalspace)) "Pct. Free"
	from
		(select tablespace_name, round(sum(bytes) / 1048576) TotalSpace
			from dba_data_files 
		group by tablespace_name) df,
		(select round(sum(bytes)/(1024*1024)) totalusedspace, tablespace_name
			from dba_segments 
				group by tablespace_name) tu
					where df.tablespace_name = tu.tablespace_name
					order by df.tablespace_name, tu.tablespace_name;

