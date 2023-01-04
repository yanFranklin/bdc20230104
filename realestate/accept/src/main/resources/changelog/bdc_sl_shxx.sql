--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SHXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_SHXX
(
  shxxid  VARCHAR2(32) not null,
  jdmc    VARCHAR2(50),
  sxh     NUMBER(4),
  shryxm  VARCHAR2(50),
  shryid  VARCHAR2(32),
  shkssj  DATE,
  shjssj  DATE,
  shyj    VARCHAR2(2000),
  qmsj    DATE,
  czjg    NUMBER(1),
  bz      VARCHAR2(4000),
  gzlslid VARCHAR2(32),
  xmid    VARCHAR2(32),
  qmid    VARCHAR2(32)
);
comment on table BDC_SL_SHXX
  is '受理审核信息';
comment on column BDC_SL_SHXX.shxxid
  is '审核信息ID';
comment on column BDC_SL_SHXX.jdmc
  is '节点名称 节点名称字段用于记录登记业务流程中审核操作所在的节点，一般为“初审”、“复审”或“登簿”';
comment on column BDC_SL_SHXX.sxh
  is '顺序号 顺序号字段用于记录审核的次序，一般从1 开始递增。这是因为审核因提交、回退等操作可能发生多次';
comment on column BDC_SL_SHXX.shryxm
  is '审核人员姓名';
comment on column BDC_SL_SHXX.shryid
  is '审核人员ID';
comment on column BDC_SL_SHXX.shkssj
  is '审核开始时间 YYYY-MM-DD HH-MM-SS';
comment on column BDC_SL_SHXX.shjssj
  is '审核结束时间 YYYY-MM-DD HH-MM-SS';
comment on column BDC_SL_SHXX.shyj
  is '审核意见';
comment on column BDC_SL_SHXX.qmsj
  is '签名时间 YYYY-MM-DD HH-MM-SS';
comment on column BDC_SL_SHXX.czjg
  is '操作结果';
comment on column BDC_SL_SHXX.bz
  is '备注';
comment on column BDC_SL_SHXX.gzlslid
  is '工作流实例ID';
comment on column BDC_SL_SHXX.qmid
  is '签名ID';

alter table BDC_SL_SHXX
  add constraint PK_BDC_SL_SHXX primary key (SHXXID)
  using index
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
--changeset BDC_SL_SHXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SHXX
    ADD (XZXXID VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_SHXX.XZXXID IS '修正信息id';