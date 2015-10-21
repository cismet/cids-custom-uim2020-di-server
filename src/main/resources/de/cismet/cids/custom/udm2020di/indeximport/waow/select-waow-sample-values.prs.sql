SELECT MESSSTELLE_PK, PROBE_PK, PARAMETER_PK, SAMPLE_DATE, SAMPLE_VALUE,
  --MAX(SAMPLE_DATE) AS MAX_DATE,
  --MIN(SAMPLE_DATE) AS MIN_DATE,
  --MIN(MIN_VALUE) AS MIN_VALUE,
  --MAX(MAX_VALUE) AS MAX_VALUE,
  XMLSerialize(CONTENT XMLRoot(xmlelement("messswert", XMLATTRIBUTES(MESSSTELLE_PK messstellePk, PROBE_PK probePk)
    ), VERSION '1.0', STANDALONE YES)) MESSWERTE_XML 
    FROM (SELECT DISTINCT V_WAOW_MST_D.PK AS MESSSTELLE_PK,
          V_WAOW_PROBE_D.PK AS PROBE_PK ,
          V_WAOW_PARAM_D.PK AS PARAMETER_PK,
          V_WAOW_PARAM_D.NAME_TXT PARAMETER_NAME,
          V_WAOW_PROBE_D.PROBEDATUM_DATE AS SAMPLE_DATE,
          V_WAOW_PARAMWERT_F.WERT_NUM AS SAMPLE_VALUE
          --MIN(V_WAOW_PARAMWERT_F.WERT_NUM) AS MIN_VALUE,
          --MAX(V_WAOW_PARAMWERT_F.WERT_NUM) AS MAX_VALUE
    FROM V_WAOW_MST_D
    INNER JOIN V_WAOW_PROBE_D ON V_WAOW_PROBE_D.FK_V_WAOW_MST_D_ID = V_WAOW_MST_D.PK
    INNER JOIN V_WAOW_PARAMWERT_F ON V_WAOW_PARAMWERT_F.FK_V_WAOW_PROBE_D_ID = V_WAOW_PROBE_D.PK
    INNER JOIN V_WAOW_PARAM_D ON V_WAOW_PARAMWERT_F.FK_V_WAOW_PARAM_D_ID = V_WAOW_PARAM_D.PK
    INNER JOIN V_WAOW_BRIDGEPARAMGRP_D ON V_WAOW_BRIDGEPARAMGRP_D.FK_V_WAOW_PARAM_D_ID = V_WAOW_PARAM_D.PK
    INNER JOIN V_WAOW_PARAMGRUPPE_D  ON V_WAOW_BRIDGEPARAMGRP_D.FK_V_WAOW_PARAMGRUPPE_D_ID = V_WAOW_PARAMGRUPPE_D.PK
    WHERE V_WAOW_MST_D.PK = ?
    AND V_WAOW_PARAMGRUPPE_D.PK IN('22', 
        '23', 
        '4028f0f02e288f22012e2907d7fd0012', 
        '40287e021d85970d011d86c8b4430073', 
        '40287e021d85970d011d86d2be810088', 
        '8ae5e2f31a15970d011a1597231b0012', 
        '8ae5e2f31c416081011c41608ec50004', 
        '8ae5e2f31c416081011c41608ec50005', 
        '8a20a1ac22a102b80122efd78f4458e1', 
        '8a20a1ac22a102b80122efd8a75a58e2', 
        '8a20a1ac23042e650123048410ee01ec', 
        '8a20a1ac23042e6501230485923e01ed', 
        '8a20a2bf4e1cfe1e014e3f7b7a520003', 
        '8a20a2b93cf249d8013cf7aa2b4224f9', 
        '8a20a2b93cf249d8013cf7a0d67424e5', 
        '8a20a2b93cf249d8013cf80ecb8825c8', 
        '8a20a2b93cf249d8013cf80fe9b625d2', 
        '8a20a2b93cf249d8013cf815ee4525db', 
        '8a20a2b93e82afe5013ec77edd2709eb', 
        '8a20a2b931513a2f0131f4bd0ca7537b', 
        '8a20a2b931513a2f0131f4bf5101538c', 
        '8a20a2b931513a2f0131f4c4bc1e5393', 
        '8a20a2b93214a76801328187f15576c6')
    AND V_WAOW_PARAMWERT_F.WERT_NUM IS NOT NULL
    --AND ROWNUM <= 1000
    GROUP BY V_WAOW_MST_D.PK, 
    V_WAOW_PROBE_D.PK,
    V_WAOW_PARAM_D.PK, 
    V_WAOW_PARAM_D.NAME_TXT, 
    V_WAOW_PROBE_D.PROBEDATUM_DATE,
    V_WAOW_PARAMWERT_F.WERT_NUM
    ORDER BY V_WAOW_MST_D.PK,
             V_WAOW_PROBE_D.PK,
             V_WAOW_PARAM_D.PK)