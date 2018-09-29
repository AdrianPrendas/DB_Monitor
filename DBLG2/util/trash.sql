

Total de espacio aproximado de un registro = 42 bytes por registro;
Cantidad de registros por unidad de tiempo: 75 000 por hora.
Tamaño consumido por hora = 75000*42bytes= 3 150 000 bytes/(1024*1024) = 3.004 MB



Entonces:

select owner, table_name, tablespace_name, status, pct_free, pct_used, ini_trans, max_trans, AVG_SPACE, AVG_ROW_LEN
 from ALL_TABLES where table_name = 'T1';


select * 
	from USER_TAB_COLS 
	where table_name = 'T1';

select tablespace_name, table_name, NUM_ROWS, BLOCKS, EMPTY_BLOCKS, AVG_SPACE,AVG_ROW_LEN
	from USER_ALL_TABLES
	where table_name = 'T1'


select OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, AVG_COL_LEN
	from ALL_TAB_COLUMNS
	where table_name = 'T1';

select * from ALL_TAB_HISTOGRAMS where table_name='T1';


select * from all_all_tables where tablespace_name = 'A1';

select * from dba_data_files where tablespace_name= 'A3'

select * from all_tab_columns where table_name= 't1';


insert into t2 values(1,2,1.2,'le saco el arroz');

select SUM(VSIZE(a)) + SUM(VSIZE(b)) + SUM(VSIZE(c)) + SUM(VSIZE(d))
	from t1;

select SUM(VSIZE(x)) + SUM(VSIZE(y)) + SUM(VSIZE(z)) + SUM(VSIZE(w)) 
	from t2;

select SUM(VSIZE(m)) + SUM(VSIZE(n)) + SUM(VSIZE(o)) + SUM(VSIZE(p)) 
	from t3;
	


  select  SUM(VSIZE(w))
	from t2;
	group by a,b,c,d;


create or replace function tSumCol(tname VARCHAR) return int as
  cursor CUR_1 is
  select SUM( SUM(VSIZE(a)) + SUM(VSIZE(b)) + SUM(VSIZE(c)) + SUM(VSIZE(d)) )
	from t1
	group by a,b,c,d;
    total int;
BEGIN
DBMS_OUTPUT.PUT_LINE('Sumando el total de Bytes del registro');
  open CUR_1;
  FETCH CUR_1 INTO total;
  close CUR_1;
  return total;
END;
/

SELECT table_name, column_name, data_type, data_length, data_upgraded
FROM USER_TAB_COLUMNS
WHERE table_name = 'T1';


select table_name,column_name, data_type, hidden_column, virtual_column
from user_tab_cols
where table_name = 't1' ;

CREATE OR REPLACE function calculateSatHoras(tname VARCHAR) return FLOAT
	IS
	total_sp integer;
	duracion_Horas FLOAT;
	BEGIN
 		SELECT round(sum(BYTES/1024/1024),0) into total_sp
		FROM dba_free_space b
		WHERE tablespace_name = tname;
		duracion_Horas := total_sp/tSumCol(tname);
		return duracion_Horas;
	END;
	/
	
CREATE OR REPLACE function calculateSatDias(tname VARCHAR) return FLOAT
	IS
	total_sp integer;
	duracion_Horas FLOAT;
	BEGIN
 		SELECT round(sum(BYTES/1024/1024),0) into total_sp
		FROM dba_free_space b
		WHERE tablespace_name = tname;
		duracion_Horas := total_sp/72.096;
		return duracion_Horas;
	END;
	/


CREATE OR REPLACE procedure calculateSatHoras(tname VARCHAR) 
	IS
	total_sp integer;
	duracion_Horas FLOAT;
	BEGIN
 		SELECT round(sum(BYTES/1024/1024),0) into total_sp
		FROM dba_free_space b
		WHERE tablespace_name = tname;
		duracion_Horas := total_sp/3.004;
		dbms_output.put_line(duracion_Horas);
	END;
	/

CREATE OR REPLACE procedure calculateSatDias(tname VARCHAR) 
	IS
	total_sp integer;
	duracion_dias FLOAT;
	BEGIN
 		SELECT round(sum(BYTES/1024/1024),0) into total_sp
		FROM dba_free_space b
		WHERE tablespace_name = tname;
		duracion_dias := total_sp/72.096;
		dbms_output.put_line(duracion_dias);
	END;
	/


	
CREATE OR REPLACE function numberOfRecords() return integer
	IS
	total_registers integer;
	BEGIN
 		SELECT round(sum(BYTES/1024/1024),0) into total_sp
		FROM dba_free_space b
		WHERE tablespace_name = tname;
		duracion_Horas := total_sp/72.096;
		return duracion_Horas;
	END;
	/



----------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION CALCULA_SAT_HORAS(tbname varchar) return FLOAT
 IS
  tname varchar(30);
  total_tb_free FLOAT;
  duracion_Horas FLOAT;
  tamano_fila FLOAT;
  tamano_indice FLOAT;
  aux_tam_fila FLOAT;
  aux_tam_indice FLOAT;
  total_tam FLOAT;
  tamano_registro_hora FLOAT;
  cursor c1 is SELECT dba_tables.table_name FROM dba_tables WHERE tablespace_name=tbname;

 BEGIN
  open c1;
  fetch c1 into tname;
  while c1%notfound loop  
   SELECT SUM(DATA_LENGTH)/(1024*1024) into tamano_fila FROM COLS WHERE TABLE_NAME = tname;
   aux_tam_fila := aux_tam_fila+tamano_fila;
   SELECT sum(COLUMN_LENGTH)/(1024*1024) into tamano_indice FROM  dba_ind_columns WHERE table_name = tname;
   aux_tam_indice := aux_tam_indice+tamano_indice;
   fetch c1 into tname;
   commit;
  END loop;
  close c1;

   SELECT round(sum(bytes)/(1024*1024)) into total_tb_free
  FROM dba_free_space b
  WHERE tablespace_name = tbname;
  total_tam := aux_tam_fila + aux_tam_indice;
  tamano_registro_hora := total_tam * 75000;
  duracion_Horas := total_tb_free/tamano_registro_hora;
  return duracion_Horas;
 END;
 /
ANALYZE TABLE T1 COMPUTE STATISTICS;
SELECT calcula_Sat_Horas('A1') FROM DUAL;
CREATE TABLESPACE A1 DATAFILE 'C:\QP\A1\A1.dbf' SIZE 5M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 10M;
















































Select t.tablespace_name  "Tablespace",
		ROUND(MAX(d.bytes)/1024/1024,2) "MB Tamaño",
		ROUND(SUM(decode(f.bytes, NULL,0, f.bytes))/1024/1024,2) "MB Libres",
		ROUND((MAX(d.bytes)/1024/1024) -(SUM(decode(f.bytes, NULL,0, f.bytes))/1024/1024),2) "MB Usados" 
FROM DBA_FREE_SPACE f, DBA_DATA_FILES d,  DBA_TABLESPACES t 
WHERE t.tablespace_name = d.tablespace_name AND 
f.tablespace_name = d.tablespace_name AND  
f.file_id = d.file_id AND 
t.tablespace_name != 'SYSTEM' and 
t.tablespace_name != 'SYSAUX' and 
t.tablespace_name !='UNDOTBS1' and 
t.tablespace_name != 'TEMP' 
GROUP BY 
t.tablespace_name,d.file_name,t.pct_increase, t.status  
ORDER BY 4 DESC;






CREATE OR REPLACE function fuct1
return cursor
as 
        TYPE tablespaces is REF CURSOR;
begin 
  open tablespaces for 
       Select t.tablespace_name  "Tablespace",
		ROUND(MAX(d.bytes)/1024/1024,2) "MB Tamaño",
		ROUND(SUM(decode(f.bytes, NULL,0, f.bytes))/1024/1024,2) "MB Libres",
		ROUND((MAX(d.bytes)/1024/1024) -(SUM(decode(f.bytes, NULL,0, f.bytes))/1024/1024),2) "MB Usados" 
FROM DBA_FREE_SPACE f, DBA_DATA_FILES d,  DBA_TABLESPACES t 
WHERE t.tablespace_name = d.tablespace_name AND 
f.tablespace_name = d.tablespace_name AND  
f.file_id = d.file_id AND 
t.tablespace_name != 'SYSTEM' and 
t.tablespace_name != 'SYSAUX' and 
t.tablespace_name !='UNDOTBS1' and 
t.tablespace_name != 'TEMP' 
GROUP BY 
t.tablespace_name,d.file_name,t.pct_increase, t.status  
ORDER BY 4 DESC;
return tablespaces;
end;
/

