--#############################################################
--#-EIF402: MONITOR_DB                                        #
--#-Versión: 1                                                #
--#-Autor: Grupo2                                             #
--#-Integrantes: Roger Amador Villagra(Coordinador)           #
--#-             Adrián Prendas Araya                         #
--#-             Oscar Carmona Mora      					  #
--#-             Daniel Mora Cordero                          #
--#-             Jeiko Granados Alvarado                      #
--#############################################################


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


-- select df.tablespace_name "Tablespace",
-- 		totalusedspace "Used MB",
-- 		(df.totalspace - tu.totalusedspace) "Free MB",
-- 		df.totalspace "Total MB",
-- 		round(100 * ( (df.totalspace - tu.totalusedspace)/ df.totalspace)) "Pct. Free"
-- 	from
-- 		(select tablespace_name, round(sum(bytes) / 1048576) TotalSpace
-- 			from dba_data_files 
-- 		group by tablespace_name) df,
-- 		(select round(sum(bytes)/(1024*1024)) totalusedspace, tablespace_name
-- 			from dba_segments 
-- 				group by tablespace_name) tu
-- 					where df.tablespace_name = tu.tablespace_name
-- 					order by df.tablespace_name, tu.tablespace_name;
-- 


--__________________________________________________________________________________________________
--*****************************************FUNCIONES************************************************

--Calculo de transacciones por tabla

--CREATE OR REPLACE FUNCTION CALCULA_TRAN_Tabla(tbname varchar)...

--           FALTÓ DE IMPLEMENTAR
--           VER POSIBLE SOLUCIÓN EN EL ANEXO


----------------------------------------------------------------------------------------------------
--Calcula saturación total en horas

CREATE OR REPLACE FUNCTION saturaciones(tbname varchar)
  RETURN SYS_REFCURSOR
AS
  bufferInf SYS_REFCURSOR;
BEGIN
  OPEN bufferInf FOR SELECT calcula_sat_sp_horas(tbname) sat FROM sys.dual union 
  SELECT calcula_sat_sp_dias(tbname) sat FROM sys.dual union 
  SELECT calcula_sat_total_horas(tbname) sat FROM sys.dual union 
  SELECT calcula_sat_total_dias(tbname) sat FROM sys.dual;
return bufferInf;
END;
/

CREATE OR REPLACE FUNCTION CALCULA_SAT_TOTAL_HORAS(tbname varchar) return number
	IS
		tname varchar(30);
		total_tb_free number;
		duracion_Horas number;
		tamano_fila number;
		tamano_indice number;
		aux_tam_fila number;
		aux_tam_indice number;
		total_tam number;
		tamano_registro_hora number;
		cursor c1 is SELECT dba_tables.table_name FROM dba_tables WHERE tablespace_name=tbname;

	BEGIN
	    aux_tam_fila := 0;
	    aux_tam_indice :=0;
	    tamano_indice :=0;
	    tamano_fila := 0;
		open c1;
	    loop
	    fetch c1 into tname;
			SELECT SUM(DATA_LENGTH)/1024/1024 into tamano_fila FROM cols WHERE table_name = tname;
			SELECT SUM(COLUMN_LENGTH)/1024/1024 into tamano_indice FROM  dba_ind_columns WHERE table_name = tname;	
		EXIT WHEN c1%NOTFOUND;
		aux_tam_fila := aux_tam_fila+tamano_fila;
		aux_tam_indice := aux_tam_indice+tamano_indice;	
		END loop;
		close c1;

 		SELECT SUM(bytes)/1024/1024 into total_tb_free
		FROM dba_free_space b
		WHERE tablespace_name = tbname;

		IF(tamano_indice IS NULL)then
		tamano_registro_hora := aux_tam_fila * 75000;
		duracion_Horas := total_tb_free/tamano_registro_hora;

		else
		total_tam := aux_tam_fila + aux_tam_indice;
		tamano_registro_hora := total_tam * 75000;
		duracion_Horas := total_tb_free/tamano_registro_hora;
		end if;
		return ROUND(duracion_Horas,3);
	END;
/
	
----------------------------------------------------------------------------------------------------
--Calcula saturación total en días

CREATE OR REPLACE FUNCTION CALCULA_SAT_TOTAL_DIAS(tbname varchar) return FLOAT
	IS
		tname varchar(30);
		total_tb_free number;
		duracion_dias number;
		tamano_fila number;
		tamano_indice number;
		aux_tam_fila number;
		aux_tam_indice number;
		total_tam number;
		tamano_registro_dia number;
		cursor c1 is SELECT dba_tables.table_name FROM dba_tables WHERE tablespace_name=tbname;

	BEGIN
	    aux_tam_fila := 0;
	    aux_tam_indice :=0;
	    tamano_indice :=0;
	    tamano_fila := 0;
		open c1;
	    loop
	    fetch c1 into tname;
			SELECT SUM(DATA_LENGTH)/1024/1024 into tamano_fila FROM cols WHERE table_name = tname;
			SELECT SUM(COLUMN_LENGTH)/1024/1024 into tamano_indice FROM  dba_ind_columns WHERE table_name = tname;	
		EXIT WHEN c1%NOTFOUND;
		aux_tam_fila := aux_tam_fila+tamano_fila;
		aux_tam_indice := aux_tam_indice+tamano_indice;	
		END loop;
		close c1;

 		SELECT SUM(bytes)/1024/1024 into total_tb_free
		FROM dba_free_space b
		WHERE tablespace_name = tbname;

		IF(tamano_indice IS NULL)then
		tamano_registro_dia := aux_tam_fila * 75000 *24;
		duracion_dias := total_tb_free/tamano_registro_dia;

		else
		total_tam := aux_tam_fila + aux_tam_indice;
		tamano_registro_dia := total_tam * 75000 *24;
		duracion_dias := total_tb_free/tamano_registro_dia;
		end if;
		return ROUND(duracion_dias,3);
	END;
/ 
	
----------------------------------------------------------------------------------------------------	
--Calcula saturación al SP en horas 
CREATE OR REPLACE FUNCTION CALCULA_SAT_SP_HORAS(tbname varchar) return FLOAT
	IS
		tname varchar(30);
		total_tb_free number;
		duracion_Horas number;
		tamano_fila number;
		tamano_indice number;
		aux_tam_fila number;
		aux_tam_indice number;
		total_tam number;
		tamano_registro_hora number;
		tamano_free_before_sp number;
		tam_max_tbsp number;
		cursor c1 is SELECT dba_tables.table_name FROM dba_tables WHERE tablespace_name=tbname;

	BEGIN
	    aux_tam_fila := 0;
	    aux_tam_indice :=0;
	    tamano_indice :=0;
	    tamano_fila := 0;
		tam_max_tbsp :=0;
		open c1;
	    loop
	    fetch c1 into tname;
			SELECT SUM(DATA_LENGTH)/1024/1024 into tamano_fila FROM cols WHERE table_name = tname;
			SELECT SUM(COLUMN_LENGTH)/1024/1024 into tamano_indice FROM  dba_ind_columns WHERE table_name = tname;	
		EXIT WHEN c1%NOTFOUND;
		aux_tam_fila := aux_tam_fila+tamano_fila;
		aux_tam_indice := aux_tam_indice+tamano_indice;	
		END loop;
		close c1;
		
		SELECT SUM(bytes)/1024/1024 table_size_megabytes into tam_max_tbsp FROM dba_data_files b
        WHERE tablespace_name = tbname;
		
 		SELECT SUM(bytes)/1024/1024 into total_tb_free
		FROM dba_free_space b
		WHERE tablespace_name = tbname;

		IF(tamano_indice IS NULL)then
		tamano_free_before_sp := total_tb_free - ((tam_max_tbsp * 15)/100);
		tamano_registro_hora := aux_tam_fila * 75000;
		duracion_Horas := tamano_free_before_sp/tamano_registro_hora;

		else
		total_tam := aux_tam_fila + aux_tam_indice;
		tamano_free_before_sp := total_tb_free - ((tam_max_tbsp * 15)/100);
		tamano_registro_hora := total_tam * 75000;
		duracion_Horas := tamano_free_before_sp/tamano_registro_hora;
		end if;
		return ROUND(duracion_Horas,3);
	END;
/ 
	
----------------------------------------------------------------------------------------------------	
--Calcula saturación al SP en días 
	
CREATE OR REPLACE FUNCTION CALCULA_SAT_SP_DIAS(tbname varchar) return FLOAT
	IS
		tname varchar(30);
		total_tb_free number;
		duracion_Dias number;
		tamano_fila number;
		tamano_indice number;
		aux_tam_fila number;
		aux_tam_indice number;
		total_tam number;
		tamano_registro_dia number;
		tamano_free_before_sp number;
		tam_max_tbsp number;
		cursor c1 is SELECT dba_tables.table_name FROM dba_tables WHERE tablespace_name=tbname;

	BEGIN
	    aux_tam_fila := 0;
	    aux_tam_indice :=0;
	    tamano_indice :=0;
	    tamano_fila := 0;
		open c1;
	    loop
	    fetch c1 into tname;
			SELECT SUM(DATA_LENGTH)/1024/1024 into tamano_fila FROM cols WHERE table_name = tname;
			SELECT SUM(COLUMN_LENGTH)/1024/1024 into tamano_indice FROM  dba_ind_columns WHERE table_name = tname;	
		EXIT WHEN c1%NOTFOUND;
		aux_tam_fila := aux_tam_fila+tamano_fila;
		aux_tam_indice := aux_tam_indice+tamano_indice;	
		END loop;
		close c1;
		
		SELECT SUM(bytes)/1024/1024 table_size_megabytes into tam_max_tbsp FROM dba_data_files b
        WHERE tablespace_name = tbname;
		
 		SELECT SUM(bytes)/1024/1024 into total_tb_free
		FROM dba_free_space b
		WHERE tablespace_name = tbname;

		IF(tamano_indice IS NULL)then
		tamano_free_before_sp := total_tb_free - ((tam_max_tbsp * 15)/100);
		tamano_registro_dia := aux_tam_fila * 75000 * 24;
		duracion_Dias := tamano_free_before_sp/tamano_registro_dia;

		else
		total_tam := aux_tam_fila + aux_tam_indice;
		tamano_free_before_sp := total_tb_free - ((tam_max_tbsp * 15)/100);
		tamano_registro_dia := total_tam * 75000 * 24;
		duracion_Dias := tamano_free_before_sp/tamano_registro_dia;
		end if;
		 return ROUND(duracion_dias,3);
	END;
/  
