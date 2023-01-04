--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZQ_THXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZQ_THXX
(
  THXXID  VARCHAR2(32) not null,
  SQXXID  VARCHAR2(32),
  THRYXM  VARCHAR2(50),
  THRYID  VARCHAR2(32),
  THSJ    DATE,
  THYY    VARCHAR2(2000),
  BZ      VARCHAR2(2000)
);

--changeset BDC_CKXX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_ZQ_THXX is '征迁退回信息表';
-- Add comments to the columns
comment on column BDC_ZQ_THXX.THXXID is '退回信息ID';
comment on column BDC_ZQ_THXX.SQXXID is '申请信息ID';
comment on column BDC_ZQ_THXX.THRYXM is '退回人员姓名';
comment on column BDC_ZQ_THXX.THRYID is '退回人员ID';
comment on column BDC_ZQ_THXX.THSJ is '退回时间';
comment on column BDC_ZQ_THXX.THYY is '退回原因';
comment on column BDC_ZQ_THXX.BZ is '备注';