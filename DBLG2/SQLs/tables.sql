drop tablespace A1  including contents and datafiles;
drop tablespace A2  including contents and datafiles;
drop tablespace A3  including contents and datafiles;
drop tablespace A4  including contents and datafiles;
drop tablespace USERS  including contents and datafiles cascade constraint;

CREATE TABLESPACE A1 DATAFILE 'C:\DBLG2\A1\A1.dbf' SIZE 10M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;
CREATE TABLESPACE A2 DATAFILE 'C:\DBLG2\A2\A2.dbf' SIZE 10M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;
CREATE TABLESPACE A3 DATAFILE 'C:\DBLG2\A3\A3.dbf' SIZE 20M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;
CREATE TABLESPACE A4 DATAFILE 'C:\DBLG2\A4\A4.dbf' SIZE 20M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;
CREATE TABLESPACE USERS DATAFILE 'C:\DBLG2\USERS\USERS.dbf' SIZE 20M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;

drop table T1;
drop table T2;
drop table T3;
drop table T4;
drop table USERS cascade constraint;


--*************************************CREACIÓN DE TABLAS*******************************************
 CREATE TABLE T1(
	a int, 
	b int, 
	c float, 
	d varchar(30)
)TABLESPACE A1;
----------------------------------------------------------------------------------------------------
CREATE TABLE T2(
	x int, 
	y int, 
	z float, 
	w varchar(30)
)TABLESPACE A2;
----------------------------------------------------------------------------------------------------
CREATE TABLE T3(
	m int, 
	n int, 
	o float, 
	p varchar(30)
)TABLESPACE A3;
----------------------------------------------------------------------------------------------------
CREATE TABLE T4(
	m int, 
	n int, 
	o float, 
	p varchar(30)
)TABLESPACE A4;
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
CREATE TABLE USERS(
	user_name varchar(30),
	password varchar(255),
	access_level int,--0:tor, 1:use, 2:uncheked, 3:blocked
	constraint pkuser primary key(user_name)
)TABLESPACE USERS;

----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------
CREATE TABLE DATA_BASE(
	name varchar(30),
	tablespace varchar(30),
	constraint pkdata_base primary key(name),
	constraint fkdata_base foreign key(tablespace) references table_space(name)
)TABLESPACE USERS;
----------------------------------------------------------------------------------------------------
CREATE TABLE TABLE_SPACES(
	name varchar(30),
	table varchar(30),
	size_mb int,
	free_mb int,
	used_mb int,
	constraint pktable_spaces primary key(name),
)TABLESPACE USERS;
----------------------------------------------------------------------------------------------------
CREATE TABLE TIPOS(
	name varchar(30),
	description varchar(255),	
)TABLESPACE USERS;
----------------------------------------------------------------------------------------------------
CREATE TABLE CANALES(
	name varchar(30),

)TABLESPACE USERS;
----------------------------------------------------------------------------------------------------
CREATE TABLE ESTRATEGIAS(
	id int,
	data_base varchar(30),
	tablespaces varchar(30),
	type varchar(30),
	channel varchar(30),
	date_time date,
	constraint pkestrategias primary key(id),
	constraint fkestrategias1 foreign key(data_base) references DATA_BASE(name),
	constraint fkestrategias2 foreign key(tablespaces) references TABLE_SPACES(name),
	constraint fkestrategias3 foreign key(type) references TIPOS(name),
	constraint fkestrategias4 foreign key(channel) references CANALES(name)
)TABLESPACE USERS;
----------------------------------------------------------------------------------------------------
--PROCEDIMIENTO PARA INSERTAR USUARIOS
create or replace procedure createUser(name in varchar, pass in varchar) is
begin
insert into sers(user_name,password)
values(name,pass);
commit;
end;
/
show error 
--PROCEDIMIENTO PARA ELIMIAR USUARIOS
create or replace procedure deleteUser(name in int) is
begin 
delete 
from user
where user_name=name;
end;
/
show error
--FUNCION PARA MOSTRAR USUARIOS
create or replace function getUsers
return Types.ref_cursor
as
usuario_cursor types.ref_cursor;
begin 
open usuario_cursor for select user_name from users;
return usuario_cursor;
end;
/
show error
--FUNCION DE LOGIN
create or replace function login(user varchar, pass varchar)return int
return Types.ref_cursor
as
usuario_cursor types.ref_cursor;
begin 
open usuario_cursor for 
	select user_name, password 
		from users 
			where user_name = user
			and password = pass;
return usuario_cursor;
end;
/
show error