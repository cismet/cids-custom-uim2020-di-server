INSERT INTO WAOW_STATION (
    "KEY", 
    "NAME", 
    DESCRIPTION, 
    GEOMETRY, 
    ZUSTAENDIGE_STELLE, 
    GEWAESSER_NAME, 
    --TAGS, 
    --SAMPLE_VALUES,
    SRC_MESSSTELLE_PK, 
    SRC_CONTENT) 
VALUES( 
    :KEY,
    :NAME,
    :DESCRIPTION,
    :GEOMETRY, 
    (SELECT ID FROM TAG WHERE KEY = :ZUSTAENDIGE_STELLE AND TAGGROUP_KEY='WAOW.ZUSTAENDIGE_STELLE'),  
    (SELECT ID FROM TAG WHERE KEY = :GEW_NAME AND TAGGROUP_KEY='WAOW.GEWAESSER'),  
    -- DOES NOT WORK!!!!!!! CURRVAL IS NOT DEFINED, NEXVTAL = ID-- 
    -- WAOW_STATION_ID_SEQ.CURRVAL TAGS, 
    -- WAOW_STATION_ID_SEQ.CURRVAL SAMPLE_VALUES, 
    --(SELECT MAX(ID)+1 FROM WAOW_STATION), 
    --(SELECT MAX(ID)+1 FROM WAOW_STATION), 
    :SRC_MESSSTELLE_PK, 
    :SRC_CONTENT)
