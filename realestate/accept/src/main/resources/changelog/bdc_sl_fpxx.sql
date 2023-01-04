--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_FPXX:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_FPXX
(
  fpxxid    VARCHAR2(32) not null,
  sfxxid    VARCHAR2(32),
  xmid      VARCHAR2(32),
  fpdm      VARCHAR2(100),
  fph       VARCHAR2(100),
  fpje      NUMBER(12,4),
  kprq      DATE,
  fpzlmc    VARCHAR2(100),
  fpzldm    VARCHAR2(100),
  fpzt      NUMBER,
  jkr       VARCHAR2(100),
  jym       VARCHAR2(100)
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
comment on table BDC_SL_FPXX is '不动产受理发票信息';
comment on column BDC_SL_FPXX.fpxxid is '发票信息ID';
comment on column BDC_SL_FPXX.sfxxid is '收费信息ID';
comment on column BDC_SL_FPXX.xmid is '项目ID';
comment on column BDC_SL_FPXX.fpdm is '发票代码';
comment on column BDC_SL_FPXX.fph is '发票号';
comment on column BDC_SL_FPXX.fpje is '发票金额：元';
comment on column BDC_SL_FPXX.kprq is '开票日期';
comment on column BDC_SL_FPXX.fpzlmc is '发票种类名称';
comment on column BDC_SL_FPXX.fpzldm is '发票种类代码';
comment on column BDC_SL_FPXX.fpzt is '发票状态';
comment on column BDC_SL_FPXX.jkr is '缴款人';
comment on column BDC_SL_FPXX.jym is '校验码';
alter table BDC_SL_FPXX
  add constraint PK_BDC_SL_FPXX primary key (FPXXID)
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

--changeset BDC_SL_FPXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FPXX add(FPURL VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_FPXX.FPURL IS '发票地址';