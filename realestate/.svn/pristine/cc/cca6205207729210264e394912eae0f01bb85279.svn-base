--liquibase formatted sql
--preconditions dbms:oracle
--changeset S_SJ_BDCDYHXSZT:1 failOnError:false runOnChange:true runAlways:false
ALTER TABLE S_SJ_BDCDYHXSZT add(GXSJ DATE);
COMMENT ON COLUMN S_SJ_BDCDYHXSZT.GXSJ IS '更新时间';
