ALTER SESSION SET ddl_lock_timeout=360;
DELETE FROM GEOM WHERE ID IN (SELECT EPRTR_INSTALLATION.GEOMETRY FROM EPRTR_INSTALLATION);
DELETE FROM TAG WHERE ID IN (SELECT EPRTR_INSTALLATION.NACE_CLASS FROM EPRTR_INSTALLATION);
DELETE FROM TAG WHERE ID IN (SELECT EPRTR_INSTALLATION.RIVER_CATCHMENT FROM EPRTR_INSTALLATION);
TRUNCATE TABLE JT_EPRTR_ITT;
TRUNCATE TABLE EPRTR_INSTALLATION;
TRUNCATE TABLE EPRTR_RELEASE;