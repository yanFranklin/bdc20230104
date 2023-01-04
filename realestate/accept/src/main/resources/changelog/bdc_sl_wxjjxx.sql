--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_WXJJXX:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_WXJJXX
(
  wxjjxxid  VARCHAR2(32) not null,
  gzlslid   VARCHAR2(32),
  xmid      VARCHAR2(32),
  sfsj      DATE,
  jedw      NUMBER,
  hj        NUMBER(12,4),
  sfzt      NUMBER default 0,
  sfztczsj  DATE,
  sfmc      VARCHAR2(200),
  sfdm      VARCHAR2(50),
  ddbh      VARCHAR2(32),
  tzzt      NUMBER,
  qlrlb     VARCHAR2(2)
);
tablespace BDCSL
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
comment on table BDC_SL_WXJJXX is '不动产受理维修基金信息';
comment on column BDC_SL_WXJJXX.wxjjxxid is '维修基金信息ID';
comment on column BDC_SL_WXJJXX.gzlslid is '工作流实例ID';
comment on column BDC_SL_WXJJXX.xmid is '项目ID';
comment on column BDC_SL_WXJJXX.sfsj is '收费时间';
comment on column BDC_SL_WXJJXX.jedw is '金额单位';
comment on column BDC_SL_WXJJXX.hj is '合计';
comment on column BDC_SL_WXJJXX.sfzt is '收费状态';
comment on column BDC_SL_WXJJXX.sfztczsj is '收费状态操作时间';
comment on column BDC_SL_WXJJXX.sfmc is '收费名称';
comment on column BDC_SL_WXJJXX.sfdm is '收费代码';
comment on column BDC_SL_WXJJXX.ddbh is '订单编号';
comment on column BDC_SL_WXJJXX.tzzt is '通知状态';
comment on column BDC_SL_WXJJXX.qlrlb is '权利人类别';

alter table BDC_SL_WXJJXX
  add constraint PK_BBDC_SL_WXJJXX primary key (WXJJXXID)
  using index
  tablespace BDCSL
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

CREATE INDEX BDC_SL_WXJJXX_XMID ON BDC_SL_WXJJXX(XMID);

--changeset BDC_SL_ZJJG:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_WXJJXX ADD (wxjjjfr VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_WXJJXX.wxjjjfr IS '维修基金缴费人';

--changeset BDC_SL_ZJJG:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_WXJJXX ADD (bdcdyh VARCHAR2(28));
COMMENT ON COLUMN BDC_SL_WXJJXX.bdcdyh IS '不动产单元号';