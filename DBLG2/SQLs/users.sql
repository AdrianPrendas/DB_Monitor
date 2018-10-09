
--Creando un tablespace para usuarios--
--CREATE TABLESPACE USERS DATAFILE '/u01/app/oracle/users/users.dbf' SIZE 100M REUSE AUTOEXTEND ON NEXT 120M MAXSIZE 200M;

--Creando Usuarios--
CREATE USER a6r1an IDENTIFIED BY a6r1an DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON SYSTEM;

--Permitiendo que usuarios inicien sesión--
GRANT CREATE SESSION TO a6r1an;

--Creación de roles--
CREATE ROLE auditor;

--Asignacion de permisos a los roles--
GRANT INSERT, SELECT, UPDATE, DELETE ON T1 to auditor;
GRANT INSERT, SELECT, UPDATE, DELETE ON T2 to auditor;
--Permisos sobre vistas atravez de dblink--
GRANT SELECT ON V_$logfile to auditor;

GRANT SELECT ON V_$logfile to auditor;

--Asignacion de roles a usuarios--
GRANT auditor to a6r1an;

--Creando perfiles de usuario--
CREATE PROFILE remoto LIMIT SESSIONS_PER_USER 1 CONNECT_TIME 100 IDLE_TIME 5;

--Asignar periles a usuarios--
ALTER USER a6r1an PROFILE remoto;


