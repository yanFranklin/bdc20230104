--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_FWTCXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_FWTCXX
(
  fwtcxxid    VARCHAR2(32) not null,
  xmid        VARCHAR2(32) not null,
  bdcqzh      VARCHAR2(2000),
  bdcdyh      VARCHAR2(28),
  qlrmc       VARCHAR2(2000),
  qlrzjh      VARCHAR2(2000),
  zl           VARCHAR2(1000),
  ghyt         VARCHAR2(100),
  sqrlb        VARCHAR2(2)
);

-- Add comments to the table
comment on table BDC_SL_FWTCXX
  is '不动产受理房屋套次信息';
-- Add comments to the columns
comment on column BDC_SL_FWTCXX.fwtcxxid
  is '主键';
comment on column BDC_SL_FWTCXX.xmid
  is '项目ID';
comment on column BDC_SL_FWTCXX.bdcqzh
  is '产权证号';
comment on column BDC_SL_FWTCXX.bdcdyh
  is '不动产单元号';
comment on column BDC_SL_FWTCXX.qlrmc
  is '权利人名称';
comment on column BDC_SL_FWTCXX.qlrzjh
  is '权利人证件号';
comment on column BDC_SL_FWTCXX.zl
  is '坐落';
comment on column BDC_SL_FWTCXX.ghyt
  is '规划用途';
comment on column BDC_SL_FWTCXX.sqrlb
  is '申请人类别:1-转入方 2-转出方';

--changeset BDC_SL_FWTCXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWTCXX add(SQRID VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_FWTCXX.SQRID IS '申请人ID';