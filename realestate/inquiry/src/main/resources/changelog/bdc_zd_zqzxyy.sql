--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_CKXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZD_ZQZXYY
(
  DM VARCHAR2(10),
  MC VARCHAR2(50)
);

--changeset BDC_CKXX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_ZD_ZQZXYY is '征迁注销原因';
-- Add comments to the columns
comment on column BDC_ZD_ZQZXYY.DM is '代码';
comment on column BDC_ZD_ZQZXYY.MC is '名称';