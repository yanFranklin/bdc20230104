--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_LSH:1 failOnError:false runOnChange:true runAlways:false

create table BDC_LSH
(
  lshid VARCHAR2(32) not null,
  lsh   NUMBER(20) not null,
  cjsj  DATE not null,
  ywlx  VARCHAR2(50)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table BDC_LSH
  is '不动产流水号';
comment on column BDC_LSH.lshid
  is '流水号ID';
comment on column BDC_LSH.lsh
  is '流水号';
comment on column BDC_LSH.cjsj
  is '创建时间';
comment on column BDC_LSH.ywlx
  is '业务类型';
create index INDEX_BDCLSH_CJSJ on BDC_LSH (CJSJ)
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
create index INDEX_BDCLSH_LSH on BDC_LSH (LSH)
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
create index INDEX_BDCLSH_YWLX on BDC_LSH (YWLX)
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
create index INDEX_BDCLSH_ZH on BDC_LSH (LSH, CJSJ, YWLX)
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
alter table BDC_LSH
  add constraint PK_BDC_LSH_LSHID primary key (LSHID)
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
