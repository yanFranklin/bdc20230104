--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZD_ZQDJZT:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZD_ZQDJZT
(
  DM VARCHAR2(2),
  MC VARCHAR2(50)
);

--changeset BDC_ZD_ZQDJZT:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_ZD_ZQSHZT is '征迁冻结状态';
-- Add comments to the columns
comment on column BDC_ZD_ZQSHZT.DM is '代码';
comment on column BDC_ZD_ZQSHZT.MC is '名称';