SELECT TAG.ID,
       TAG.KEY,
       TAG.NAME,
       TAG.DESCRIPTION,
       TAG.TAGGROUP AS TAGGROUPID,
       TAG.TAGGROUP_KEY AS TAGGROUPKEY
FROM TAG
INNER JOIN JT_EPRTR_STT ON JT_EPRTR_STT.TAG = TAG.ID
AND JT_EPRTR_STT.EPRTR_INSTALLATION = :INSTALLATION_ID
ORDER BY TAG.TAGGROUP_KEY,
         TAG.KEY