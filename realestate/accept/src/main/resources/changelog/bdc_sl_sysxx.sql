--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SYSXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_SYSXX
(
  sysid  VARCHAR2(32) not null,
  hsxxid  VARCHAR2(32) not null,
  swjgdm    VARCHAR2(50),
  nsrsbh  VARCHAR2(200),
  dzsphm    VARCHAR2(50),
  yhjkrkzt  NUMBER(1),
  yjse  NUMBER(12,4),
  xmid VARCHAR2(32),
  sqrlb   VARCHAR2(2),
  jkrksj DATE
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_SYSXX
  is '税务三要素信息';
comment on column BDC_SL_SYSXX.sysid
  is '三要素ID';
comment on column BDC_SL_SYSXX.hsxxid
  is '核税信息ID';
comment on column BDC_SL_SYSXX.swjgdm
  is '税务机关代码';
comment on column BDC_SL_SYSXX.nsrsbh
  is '纳税人识别号';
comment on column BDC_SL_SYSXX.dzsphm
  is '电子税票号码';
comment on column BDC_SL_SYSXX.yhjkrkzt
  is '银行缴款入库状态';
comment on column BDC_SL_SYSXX.yjse
  is '应缴税额';
comment on column BDC_SL_SYSXX.xmid
  is '项目ID';
comment on column BDC_SL_SYSXX.sqrlb
  is '申请人类别';
comment on column BDC_SL_SYSXX.jkrksj
  is '缴库入库时间';

alter table BDC_SL_SYSXX
  add constraint BDC_SL_SYSXX primary key (SYSID)
  using index
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

