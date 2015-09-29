SELECT DISTINCT %CLASS_ID% AS class_id, BORIS_SITE.ID as id, BORIS_SITE.NAME as name 
FROM BORIS_SITE 
INNER JOIN JT_BORIS_SSV ON JT_BORIS_SSV.BORIS_SITE = BORIS_SITE.ID 
AND BORIS_SITE.ID IN (%BORIS_SITE_IDS%) 
INNER JOIN BORIS_SAMPLE_VALUE ON JT_BORIS_SSV.BORIS_SAMPLE_VALUE = BORIS_SAMPLE_VALUE.ID 
WHERE %MAX_SAMPLE_VALUE_CONDITIONS%
GROUP BY BORIS_SITE.ID, BORIS_SITE.NAME
HAVING COUNT (BORIS_SITE.ID) = %NUM_MAX_SAMPLE_VALUE_CONDITIONS%