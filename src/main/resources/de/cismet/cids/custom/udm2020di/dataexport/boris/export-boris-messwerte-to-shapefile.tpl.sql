SELECT DISTINCT T_BIS_PROBE_B.FK_T_BIS_STANDORT_D AS STANDORT_PK,
                T_BIS_STANDORT_D.RECHTSWERT_NUM AS RECHTSWERT,
                T_BIS_STANDORT_D.HOCHWERT_NUM AS HOCHWERT,
                T_BIS_PROBE_B.PK AS PROBE_PK,
                TO_DATE(T_BIS_PROBE_B.PROBENAHMEDATUM_TXT, 'YYYYMMDD') AS PROBENAHMEDATUM,
                T_BIS_PROBE_B.PROBEVON_NUM AS PROBEVON,
                T_BIS_PROBE_B.PROBEBIS_NUM AS PROBEBIS,
                %MESSWERT_DECODE_STATEMENTS%
FROM T_BIS_MESSWERT_F MESSWERT
INNER JOIN T_BIS_PARAMETER_B PARAMETER ON PARAMETER.PK = MESSWERT.FK_T_BIS_PARAM_B
AND PARAMETER.PK IN (%MESSWERT_PARAMETER_PKS%)
INNER JOIN T_BIS_PROBE_B ON T_BIS_PROBE_B.PK = MESSWERT.FK_T_BIS_PROBE_B
AND T_BIS_PROBE_B.FK_T_BIS_STANDORT_D IN (%STANDORT_PKS%)
INNER JOIN T_BIS_STANDORT_D ON T_BIS_STANDORT_D.PK = T_BIS_PROBE_B.FK_T_BIS_STANDORT_D
GROUP BY T_BIS_PROBE_B.FK_T_BIS_STANDORT_D,
         T_BIS_STANDORT_D.RECHTSWERT_NUM,
         T_BIS_STANDORT_D.HOCHWERT_NUM,
         T_BIS_PROBE_B.PK,
         PROBENAHMEDATUM_TXT,
         T_BIS_PROBE_B.PROBEVON_NUM,
         T_BIS_PROBE_B.PROBEBIS_NUM
ORDER BY T_BIS_PROBE_B.FK_T_BIS_STANDORT_D,
         T_BIS_PROBE_B.PK