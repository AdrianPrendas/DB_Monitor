
CREATE TABLESPACE rman_user DATAFILE 'C:\DBLG2\rman\rman_user.dbf' SIZE 10M REUSE AUTOEXTEND ON NEXT 7M MAXSIZE 100M;

CREATE USER rman_a6r1an IDENTIFIED BY a6r1an DEFAULT TABLESPACE rman_user QUOTA UNLIMITED ON rman_user;

GRANT RECOVERY_CATALOG_OWNER TO rman_a6r1an;

GRANT CONNECT, RESOURCE TO rman_a6r1an;

--RMAN> CREATE CATALOG TABLESPACE rman_user;
--RMAN> exit;
--$ rman target=/ catalog rman_a6r1an@a6r1an
--RMAN> CONFIGURE CHANNEL DEVICE TYPE ‘SBT_TAPE’ PARMS ‘ENV=(TDPO_OPTFILE=/opt/tivoli/tsm/client/oracle/bin/tdpo_bd.opt)’;
--run {
--allocate channel C1 device type DISK format ‘C:/DBLG2/FR_A/_%d_%u_%t.bak’;
--backup database include current controlfile plus archivelog delete all input;
--} 
