--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_CKXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZQ_ZXSQ
(
  SQXXID VARCHAR2(32) not null,
  BDCDYH  VARCHAR2(28),
  XMID  VARCHAR2(32),
  BDCQZH  VARCHAR2(100),
  ZL  VARCHAR2(4000),
  QLLX  NUMBER(2),
  DJLX  NUMBER(2),
  BDCLX  NUMBER(2),
  QLRMC  VARCHAR2(200),
  QLRZJH  VARCHAR2(100),
  LXDH  VARCHAR2(100),
  JZMJ  NUMBER(15,2),
  ZDZHMJ  NUMBER(20,2),
  SHZT  NUMBER(1),
  ZXYY  VARCHAR2(2000),
  ZXSQR  VARCHAR2(200),
  ZXSQRID  VARCHAR2(32),
  ZXSQSJ  DATE,
  SCYY  VARCHAR2(2000),
  SCR  VARCHAR2(200),
  SCRID  VARCHAR2(32),
  SCSJ  DATE,
  BZ  VARCHAR2(2000)
);

--changeset BDC_CKXX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_ZQ_ZXSQ is '征迁注销申请表';
-- Add comments to the columns
comment on column BDC_ZQ_ZXSQ.SQXXID is '申请信息ID';
comment on column BDC_ZQ_ZXSQ.BDCDYH is '不动产单元号';
comment on column BDC_ZQ_ZXSQ.XMID is '项目ID';
comment on column BDC_ZQ_ZXSQ.BDCQZH is '不动产权证号';
comment on column BDC_ZQ_ZXSQ.ZL is '坐落';
comment on column BDC_ZQ_ZXSQ.QLLX is '权利类型';
comment on column BDC_ZQ_ZXSQ.DJLX is '登记类型';
comment on column BDC_ZQ_ZXSQ.BDCLX is '不动产类型';
comment on column BDC_ZQ_ZXSQ.QLRMC is '权利人名称';
comment on column BDC_ZQ_ZXSQ.QLRZJH is '权利人证件号';
comment on column BDC_ZQ_ZXSQ.LXDH is '联系电话';
comment on column BDC_ZQ_ZXSQ.JZMJ is '建筑面积';
comment on column BDC_ZQ_ZXSQ.ZDZHMJ is '宗地宗海面积';
comment on column BDC_ZQ_ZXSQ.SHZT is '审核状态';
comment on column BDC_ZQ_ZXSQ.ZXYY is '注销原因';
comment on column BDC_ZQ_ZXSQ.ZXSQR is '注销申请人';
comment on column BDC_ZQ_ZXSQ.ZXSQRID is '注销申请人ID';
comment on column BDC_ZQ_ZXSQ.ZXSQSJ is '注销申请时间';
comment on column BDC_ZQ_ZXSQ.SCYY is '删除原因';
comment on column BDC_ZQ_ZXSQ.SCR is '删除人';
comment on column BDC_ZQ_ZXSQ.SCRID is '删除人ID';
comment on column BDC_ZQ_ZXSQ.SCSJ is '删除时间';
comment on column BDC_ZQ_ZXSQ.BZ is '备注';

--changeset BDC_ZXYW_YWBM_REL:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_ZQ_ZXSQ add(ZXSQBH VARCHAR2(50));
COMMENT ON COLUMN BDC_ZQ_ZXSQ.ZXSQBH IS '注销申请编号';