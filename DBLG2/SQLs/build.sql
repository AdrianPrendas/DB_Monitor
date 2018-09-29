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


startup nomount pfile = C:\DBLG2\parametros\init.ora

create database "DBLG2"
logfile group 1 ('C:\DBLG2\redo\redo1.log') size 100M,
group 2 ('C:\DBLG2\redo\redo2.log') size 100M,
group 3 ('C:\DBLG2\redo\redo3.log') size 100M
character set WE8ISO8859P1
national character set utf8
datafile 'C:\DBLG2\system\system.dbf' size 500M autoextend on next 10M maxsize
unlimited extent management local
sysaux datafile 'C:\DBLG2\sysaux\sysaux.dbf' size 100M autoextend on next 10M
maxsize unlimited
undo tablespace undotbs1 datafile 'C:\DBLG2\undo\undotbs1.dbf' size 100M
default temporary tablespace temp tempfile 'C:\DBLG2\temp\temp01.dbf' size
100M;


--@'C:\oraclexe\app\oracle\product\11.2.0\server\rdbms\admin\catalog.sql'

--@'C:\oraclexe\app\oracle\product\11.2.0\server\rdbms\admin\catproc.sql'
