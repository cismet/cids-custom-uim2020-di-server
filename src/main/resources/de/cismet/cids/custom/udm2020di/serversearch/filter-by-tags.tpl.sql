--%OBJECT_ID_CONSTANTS%
--MINUS
(SELECT CLASS_ID, OBJECT_ID
FROM CSX_OBJECT_TAG
  WHERE CLASS_ID = %CLASS_ID%
  AND TAG_ID IN (%TAG_IDS%)
  AND OBJECT_ID IN (%OBJECT_IDS%)
GROUP BY CLASS_ID, OBJECT_ID)