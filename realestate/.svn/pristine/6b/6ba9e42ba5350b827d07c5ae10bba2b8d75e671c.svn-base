--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_JTCY:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_JTCY
(
  jtcyid  VARCHAR2(32) not null,
  sqrid   VARCHAR2(32) not null,
  jtcymc  VARCHAR2(200),
  zjzl    NUMBER,
  zjh     VARCHAR2(100),
  ysqrgx  VARCHAR2(100)
)
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
comment on table BDC_SL_JTCY
  is '不动产受理家庭成员';
comment on column BDC_SL_JTCY.jtcyid
  is '家庭成员ID';
comment on column BDC_SL_JTCY.sqrid
  is '申请人ID';
comment on column BDC_SL_JTCY.jtcymc
  is '家庭成员名称';
comment on column BDC_SL_JTCY.zjzl
  is '证件种类';
comment on column BDC_SL_JTCY.zjh
  is '证件号';
comment on column BDC_SL_JTCY.ysqrgx
  is '与申请人关系';

alter table BDC_SL_JTCY
  add constraint PK_BDC_SL_JTCY primary key (JTCYID)
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

--changeset BDC_SL_JTCY:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JTCY ADD GXBS number(1);
comment on column BDC_SL_JTCY.GXBS
  is '共享标识';

--changeset BDC_SL_JTCY:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JTCY ADD HH VARCHAR2(100);
comment on column BDC_SL_JTCY.HH is '户号';
ALTER TABLE BDC_SL_JTCY ADD XB VARCHAR2(100);
comment on column BDC_SL_JTCY.XB is '性别';

--changeset BDC_SL_JTCY:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JTCY add CYM VARCHAR2(100);
comment on column BDC_SL_JTCY.CYM is '曾用名';

--changeset BDC_SL_JTCY:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JTCY add HYZK VARCHAR2(100);
comment on column BDC_SL_JTCY.HYZK is '婚姻状况';

--changeset BDC_SL_JTCY:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JTCY add GJ VARCHAR2(100);
comment on column BDC_SL_JTCY.GJ is '国籍';