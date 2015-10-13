SELECT  INSTALLATIONS.INSTALLATION_ID AS ID,
        INSTALLATIONS.INSTALLATION_ERAS_ID AS ERAS_ID,
        INSTALLATIONS.INSTALLATION_NAME AS NAME, 
        INSTALLATIONS.LONGITUDE, 
        INSTALLATIONS.LATITUDE,
        INSTALLATIONS.RIVER_CATCHMENT, 
        INSTALLATIONS.NACE_ID,
        XMLSerialize(CONTENT XMLRoot(
        XMLELEMENT("INSTALLATION",  
        XMLATTRIBUTES(
            INSTALLATIONS.INSTALLATION_ID id, 
            INSTALLATIONS.INSTALLATION_ERAS_ID erasid,
            INSTALLATIONS.OBLIGATED_PARTY obligatedparty,
            INSTALLATIONS.NACE_ID naceclass,
            INSTALLATIONS.INSTALLATION_NAME name, 
            INSTALLATIONS.PARENT_COMPANY parentcompany,
            INSTALLATIONS.RIVER_CATCHMENT rivercatchment, 
            INSTALLATIONS.LONGITUDE longitude, 
            INSTALLATIONS.LATITUDE latitude),
          (SELECT XMLELEMENT("ACTIVITIES", xmlagg (xmlelement ("ACTIVITY", XMLATTRIBUTES(
                NOTIFICATION_PERIOD.PER_MNEMONIC notificationperiod,
                ACTIVITY.ACT_ACTIVITY_CODE_NEW code,
                ACTIVITY.ACT_MNEMONIC mnemonic,
                ACTIVITY.ACT_DENOTATION name,
                ACTIVITY.ACT_IS_PRIMARY_PERF_ACTIVITY primaryactivity))))
            FROM EDM_EPRTR_MASS.V_EPRTR_ACTIVITY ACTIVITY
            INNER JOIN EDM_EPRTR_MASS.V_EPRTR_ACTIVITY_ROOT ACTIVITY_ROOT
                ON ACTIVITY_ROOT.ID = ACTIVITY.ACT_ACTIVITY_ROOT
            INNER JOIN EDM_EPRTR_MASS.V_EPRTR_NOTIFICATION_HEADER NHDR
                ON NHDR.ID = ACTIVITY_ROOT.ACTR_NOTIFICATION_HEADER
            INNER JOIN EDM_EPRTR_MASS.V_EPRTR_NOTIFICATION_PERIODE NOTIFICATION_PERIOD 
                ON NOTIFICATION_PERIOD.ID = NHDR.NOTR_PERIOD
            WHERE NHDR.NOTR_INSTALLATION = INSTALLATIONS.INSTALLATION_ERAS_ID
            ) AS ACTIVITIES,
            (SELECT XMLELEMENT("NOTIFICATIONS", xmlagg (xmlelement ("NOTIFICATION", XMLATTRIBUTES(
                NHDR.ID id,
                NOTIFICATION_PERIOD.PER_MNEMONIC mnemonic,
                NOTIFICATION_PERIOD.PER_DENOTATION name,
                NOTIFICATION_PERIOD.PER_NOTIFICATION_PERIODE_BEGIN notificationstartdate,
                NOTIFICATION_PERIOD.PER_NOTIFICATION_PERIODE_END notificationenddate,
                NOTIFICATION_PERIOD.PER_REPORTING_PERIODE_BEGIN reportingstartdate,
                NOTIFICATION_PERIOD.PER_REPORTING_PERIODE_END reportingenddate))))
            FROM EDM_EPRTR_MASS.V_EPRTR_NOTIFICATION_HEADER NHDR
            INNER JOIN EDM_EPRTR_MASS.V_EPRTR_NOTIFICATION_PERIODE NOTIFICATION_PERIOD 
                ON NOTIFICATION_PERIOD.ID = NHDR.NOTR_PERIOD
            WHERE NHDR.NOTR_INSTALLATION = INSTALLATIONS.INSTALLATION_ERAS_ID
            ) AS ACTIVITIES,
        /*XMLELEMENT("ACTIVITIES", xmlagg (
            XMLELEMENT ("ACTIVITY", XMLATTRIBUTES(
                INSTALLATIONS.ACTIVITY_CODE code,
                INSTALLATIONS.ACTIVITY_MNEMONIC mnemonic,
                INSTALLATIONS.ACTIVITY_NAME name,
                INSTALLATIONS.ACTIVITY_PRIMARY primary)))),*/
            (SELECT XMLELEMENT("RELEASEPARAMETERS", xmlagg (xmlelement ("PARAMETER", XMLATTRIBUTES(
                RELEASE.REL_RELEASE_NEW parameterpk,
                RELEASE.REL_RELEASE_NEW parametername,
                'kg/Jahr' parametereinheit,
                RELEASE_TYPE.RELTYPE_MNEMONIC parametergruppepk, 
                RELEASE_TYPE.RELTYPE_DENOTATION parametergruppename))))
            FROM EDM_EPRTR_MASS.V_EPRTR_RELEASE RELEASE
            INNER JOIN EDM_EPRTR_MASS.V_EPRTR_RELEASE_ROOT RELEASE_ROOT
                ON RELEASE_ROOT.ID = RELEASE.REL_RELEASE_ROOT
            INNER JOIN EDM_EPRTR_MASS.V_EPRTR_RELEASE_TYPE RELEASE_TYPE 
                ON RELEASE_TYPE.ID = RELEASE.REL_RELEASE_TYPE
            INNER JOIN EDM_EPRTR_MASS.V_EPRTR_NOTIFICATION_HEADER NHDR
                ON NHDR.ID = RELEASE_ROOT.RELR_NOTIFICATION_HEADER
            WHERE NHDR.NOTR_INSTALLATION = INSTALLATIONS.INSTALLATION_ERAS_ID
            --GROUP BY RELEASE.REL_RELEASE_NEW, 
            --    RELEASE_TYPE.RELTYPE_MNEMONIC, 
            --   RELEASE_TYPE.RELTYPE_DENOTATION
            ) AS RELEASEPARAMETERS,
        XMLELEMENT("ADDRESSES", xmlagg (
            XMLELEMENT ("ADDRESS", XMLATTRIBUTES(
                INSTALLATIONS.ADDRESS_TYPE type,
                INSTALLATIONS.ADDRESS_REGION region,
                INSTALLATIONS.ADDRESS_DISTRICT district, 
                INSTALLATIONS.ADDRESS_POSTCODE postcode,
                 INSTALLATIONS.ADDRESS_CITY city,
                INSTALLATIONS.ADDRESS_STREET_NAME streetname))))),
        VERSION '1.0' , STANDALONE YES)) AS INSTALLATION_XML
FROM
(SELECT DISTINCT 
    INSTALLATION.ID AS INSTALLATION_ID,
    INSTALLATION.INST_INSTALLATION AS INSTALLATION_ERAS_ID,
    INSTALLATION.INST_OBLIGATED_PARTY_NAME AS OBLIGATED_PARTY,
    INSTALLATION.INST_NACE_ID AS NACE_ID,
    INSTALLATION.INST_DENOTATION AS INSTALLATION_NAME,
    INSTALLATION.INST_PARENT_COMPANY AS PARENT_COMPANY,
    INSTALLATION.INST_RIVER_CATCHMENT AS RIVER_CATCHMENT,
    COORDINATES.COORD_LONGITUDE AS LONGITUDE, 
    COORDINATES.COORD_LATITUDE AS LATITUDE,
    --ACTIVITY.ACT_ACTIVITY_CODE_NEW AS ACTIVITY_CODE,
    --ACTIVITY.ACT_MNEMONIC AS ACTIVITY_MNEMONIC,
    --ACTIVITY.ACT_DENOTATION AS ACTIVITY_NAME,
    --ACTIVITY.ACT_IS_PRIMARY_PERF_ACTIVITY AS ACTIVITY_PRIMARY,
    --ACTIVITY.ACT_PRODUCTION_VOLUME AS PRODUCTION_VOLUME
    ADDRESS_TYPE.ADDTYPE_DENOTATION AS ADDRESS_TYPE,
    ADDRESS.ADDR_REGION AS ADDRESS_REGION,
    ADDRESS.ADDR_DISTRICT AS ADDRESS_DISTRICT, 
    ADDRESS.ADDR_POSTCODE AS ADDRESS_POSTCODE,
    ADDRESS.ADDR_CITY_NAME AS ADDRESS_CITY,
    ADDRESS.ADDR_STREET_NAME AS ADDRESS_STREET_NAME
FROM EDM_EPRTR_MASS.V_EPRTR_INSTALLATION INSTALLATION 
INNER JOIN EDM_EPRTR_MASS.V_EPRTR_INSTALLATION_ROOT INSTALLATION_ROOT
    ON INSTALLATION_ROOT.ID = INSTALLATION.INST_INSTALLATION_ROOT
INNER JOIN EDM_EPRTR_MASS.V_EPRTR_NOTIFICATION_HEADER NOTIFICATION_HEADER 
    ON NOTIFICATION_HEADER.NOTR_INSTALLATION = INSTALLATION.INST_INSTALLATION
INNER JOIN EDM_EPRTR_MASS.V_EPRTR_RELEASE_ROOT RELEASE_ROOT
    ON RELEASE_ROOT.RELR_NOTIFICATION_HEADER = NOTIFICATION_HEADER.ID
    --ON NOTIFICATION_HEADER.ID = INSTALLATION_ROOT.INSTR_NOTIFICATION_HEADER
/*INNER JOIN EDM_EPRTR_MASS.V_EPRTR_NOTIFICATION_PERIODE NOTIFICATION_PERIODE 
    ON NOTIFICATION_PERIODE.ID = NOTIFICATION_HEADER.NOTR_PERIOD*/
/*INNER JOIN EDM_EPRTR_MASS.V_EPRTR_ACTIVITY_ROOT ACTIVITY_ROOT
    ON ACTIVITY_ROOT.ACTR_NOTIFICATION_HEADER = NOTIFICATION_HEADER.ID
INNER JOIN EDM_EPRTR_MASS.V_EPRTR_ACTIVITY ACTIVITY 
    ON ACTIVITY.ACT_ACTIVITY_ROOT = ACTIVITY_ROOT.ID*/
INNER JOIN EDM_EPRTR_MASS.V_EPRTR_ADDRESS_ROOT ADDRESS_ROOT 
    ON ADDRESS_ROOT.ADDRR_NOTIFICATION_HEADER = NOTIFICATION_HEADER.ID
INNER JOIN EDM_EPRTR_MASS.V_EPRTR_ADDRESS ADDRESS 
    ON ADDRESS.ADDR_ADDRESS_ROOT = ADDRESS_ROOT.ID
INNER JOIN EDM_EPRTR_MASS.V_EPRTR_ADDRESS_TYPE ADDRESS_TYPE
    ON ADDRESS_TYPE.ID = ADDRESS.ADDR_ADDRESS_TYPE
INNER JOIN EDM_EPRTR_MASS.V_EPRTR_INST_COORDINATES COORDINATES 
    ON COORDINATES.COORD_NOTIFICATION_HEADER = NOTIFICATION_HEADER.ID
--WHERE NOTIFICATION_HEADER.NOTR_INSTALLATION = 4710 
WHERE NOTIFICATION_HEADER.ID = (SELECT MAX(NIH.ID) FROM EDM_EPRTR_MASS.V_EPRTR_NOTIFICATION_HEADER NIH 
    WHERE NIH.NOTR_INSTALLATION = INSTALLATION.INST_INSTALLATION)
AND INSTALLATION.ID = (SELECT MAX(INS.ID) FROM EDM_EPRTR_MASS.V_EPRTR_INSTALLATION INS 
    WHERE INS.INST_INSTALLATION = NOTIFICATION_HEADER.NOTR_INSTALLATION)
--AND ROWNUM < 100
--GROUP BY INSTALLATION.INST_INSTALLATION, ADDRESS.ADDR_CITY_NAME, ADDRESS.ADDR_ADDRESS_TYPE
--ORDER BY MAX(INSTALLATION.ID) DESC
ORDER BY INSTALLATION.INST_INSTALLATION) INSTALLATIONS
GROUP BY INSTALLATIONS.INSTALLATION_ID,
  INSTALLATIONS.INSTALLATION_ERAS_ID,
  INSTALLATIONS.NACE_ID,
  INSTALLATIONS.OBLIGATED_PARTY,
  INSTALLATIONS.INSTALLATION_NAME, 
  INSTALLATIONS.PARENT_COMPANY,
  INSTALLATIONS.RIVER_CATCHMENT, 
  INSTALLATIONS.LONGITUDE, 
  INSTALLATIONS.LATITUDE

