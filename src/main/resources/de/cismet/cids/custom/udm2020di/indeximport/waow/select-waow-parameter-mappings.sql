SELECT DISTINCT PARAMETER_PK AS PARAMETERPK, 
                NVL(DISPLAY_NAME, PARAMETER_NAME) DISPLAYNAME,
                UNIT,
                POLLUTANT_KEY AS POLLUTANTTAGKEY,
                POLLUTANTGROUP_KEY POLLUTANTGROUPTAGKEY,
                POLLUTANT_TAG.ID AS POLLUTANTTAGID,
                POLLUTANTGROUP_TAG.ID AS POLLUTANTGROUPTAGID,
                POLLUTANT_TAG.NAME AS POLLUTANTTAGNAME,
                POLLUTANTGROUP_TAG.NAME AS POLLUTANTGROUPTAGNAME, 
                AGGREGATION_PK AS PARAMETERAGGREGATIONPK, 
                AGGREGATION_CONVERSION AS PARAMETERAGGREGATIONEXPRESSION
FROM CSX_MAPPING_WAOW
INNER JOIN TAG POLLUTANT_TAG ON POLLUTANT_TAG.KEY = POLLUTANT_KEY 
    AND POLLUTANT_TAG.TAGGROUP_KEY = 'POLLUTANT'
INNER JOIN TAG POLLUTANTGROUP_TAG ON POLLUTANTGROUP_TAG.KEY = POLLUTANTGROUP_KEY
    AND POLLUTANTGROUP_TAG.TAGGROUP_KEY = 'POLLUTANTGROUP'
WHERE POLLUTANT_KEY IS NOT NULL
ORDER BY PARAMETER_PK