SELECT DISTINCT MESSSTELLE.PK AS MESSSTELLE_PK, 
    PROBE.PK AS PROBE_PK, 
    PROBE.PROBEDATUM_DATE AS PROBEDATUM,
    MESSSTELLE.XKOORDINATE_NUM AS XKOORDINATE,
    MESSSTELLE.YKOORDINATE_NUM AS YKOORDINATE,
    %MESSWERT_DECODE_STATEMENTS%
    FROM V_WAOW_MST_D MESSSTELLE
    INNER JOIN V_WAOW_PROBE_D PROBE ON PROBE.FK_V_WAOW_MST_D_ID = MESSSTELLE.PK
    INNER JOIN V_WAOW_PARAMWERT_F PARAMWERT ON PARAMWERT.FK_V_WAOW_PROBE_D_ID = PROBE.PK
    INNER JOIN V_WAOW_PARAM_D PARAM ON PARAMWERT.FK_V_WAOW_PARAM_D_ID = PARAM.PK
    WHERE MESSSTELLE.PK IN (%MESSSTELLE_PKS%)
    AND PARAM.PK IN(%MESSWERT_PARAMETER_PKS%)
    AND PARAMWERT.WERT_NUM IS NOT NULL
    GROUP BY MESSSTELLE.PK, 
    PROBE.PK,
    PROBE.PROBEDATUM_DATE, 
    MESSSTELLE.XKOORDINATE_NUM, 
    MESSSTELLE.YKOORDINATE_NUM
    ORDER BY MESSSTELLE.PK, 
            PROBE.PK,
            PROBE.PROBEDATUM_DATE