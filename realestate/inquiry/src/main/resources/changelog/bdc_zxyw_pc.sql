--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZXYW_PC:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZXYW_PC
(
  ZXYWPCID VARCHAR2(32) not null,
  ZXMC     VARCHAR2(50),
  YWBM     VARCHAR2(50)
)
--changeset BDC_ZXYW_PC:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_ZXYW_PC
  is '不动产中心业务排除表';
-- Add comments to the columns
comment on column BDC_ZXYW_PC.ZXYWPCID
  is '中心业务排除id';
comment on column BDC_ZXYW_PC.ZXMC
  is '中心名称';
comment on column BDC_ZXYW_PC.YWBM
  is '业务代码';