--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_CKXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZQ_SHXX
(
  SHXXID  VARCHAR2(32) not null,
  SQXXID  VARCHAR2(32),
  SHJD    NUMBER(1),
  SHRYXM  VARCHAR2(50),
  SHRYID  VARCHAR2(32),
  SHSJ    DATE,
  SHYJ    VARCHAR2(2000),
  QMID    VARCHAR2(32),
  BZ      VARCHAR2(2000)
);

--changeset BDC_CKXX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_ZQ_SHXX is '征迁审核信息表';
-- Add comments to the columns
comment on column BDC_ZQ_SHXX.SHXXID is '审核信息ID';
comment on column BDC_ZQ_SHXX.SQXXID is '申请信息ID';
comment on column BDC_ZQ_SHXX.SHJD is '审核阶段';
comment on column BDC_ZQ_SHXX.SHRYXM is '审核人员姓名';
comment on column BDC_ZQ_SHXX.SHRYID is '审核人员ID';
comment on column BDC_ZQ_SHXX.SHSJ is '审核时间';
comment on column BDC_ZQ_SHXX.SHYJ is '审核意见';
comment on column BDC_ZQ_SHXX.QMID is '签名ID';
comment on column BDC_ZQ_SHXX.BZ is '备注';