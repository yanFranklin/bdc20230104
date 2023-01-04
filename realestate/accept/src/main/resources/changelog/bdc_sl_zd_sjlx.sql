--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_LSH:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_SJLX
(
  dm NUMBER(2),
  mc VARCHAR2(20)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table BDC_SL_ZD_SJLX
  is '收件类型字典表';