--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_TDCRJ:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_TDCRJ
(
  tdcrjid VARCHAR2(32) not null,
  xmid VARCHAR2(32),
  zstdcrj NUMBER(1),
  zsje NUMBER(12,4),
  jfrlb     VARCHAR2(10),
  zsxm     VARCHAR2(100),
  zspm   VARCHAR2(100),
  zsbl   VARCHAR2(100)
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
comment on table BDC_SL_TDCRJ
  is '不动产土地出让金';
comment on column BDC_SL_TDCRJ.tdcrjid
  is '土地出让金主键';
comment on column BDC_SL_TDCRJ.xmid
  is '项目ID';
comment on column BDC_SL_TDCRJ.zstdcrj
  is '是否征收土地出让金';
comment on column BDC_SL_TDCRJ.zsje
  is '征收金额';
comment on column BDC_SL_TDCRJ.jfrlb
  is '缴费人类别';
comment on column BDC_SL_TDCRJ.zsxm
  is '征收项目';
comment on column BDC_SL_TDCRJ.zspm
  is '征收品目';
comment on column BDC_SL_TDCRJ.zsbl
  is '征收比例';

alter table BDC_SL_TDCRJ
  add constraint PK_BDC_TDCRJ primary key (TDCRJID)
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

--changeset BDC_SL_TDCRJ:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_TDCRJ ADD ZSTDQS NUMBER(1);
COMMENT ON COLUMN BDC_SL_TDCRJ.SFLZR IS '是否征收土地契税';