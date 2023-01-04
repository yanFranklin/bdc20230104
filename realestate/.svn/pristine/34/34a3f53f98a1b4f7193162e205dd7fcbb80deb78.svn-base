--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_XT_LSCS:2 failOnError:false runOnChange:true runAlways:false
create table BDC_XT_LSCS
(
  CSMC  VARCHAR2(200) not null,
  CSZ   VARCHAR2(32),
  SFSC  NUMBER(1)
);

CREATE INDEX IDX_BDC_XT_LSCS_CSMC ON BDC_XT_LSCS(CSMC);

--changeset BDC_XT_LSCS:3 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_XT_LSCS is '临时参数表';
-- Add comments to the columns
comment on column BDC_XT_LSCS.CSMC is '参数名称';
comment on column BDC_XT_LSCS.CSZ  is '参数值';
comment on column BDC_XT_LSCS.SFSC is '是否删除';