--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_XM_LSGX:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_XM_LSGX
(
  gxid   VARCHAR2(32) not null,
  xmid   VARCHAR2(32) not null,
  yxmid  VARCHAR2(32),
  zxyql  NUMBER default 1,
  sfwlzs NUMBER default 0,
  bdcdyh VARCHAR2(28),
  zl     VARCHAR2(200),
  qlr    VARCHAR2(200)
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
comment on table BDC_SL_XM_LSGX
  is '不动产受理项目历史关系';
comment on column BDC_SL_XM_LSGX.gxid
  is '关系ID
';
comment on column BDC_SL_XM_LSGX.xmid
  is '项目ID
';
comment on column BDC_SL_XM_LSGX.yxmid
  is '原项目ID
';
comment on column BDC_SL_XM_LSGX.zxyql
  is '是否注销原权利  0:否  1：是';
comment on column BDC_SL_XM_LSGX.sfwlzs
  is '是否外联证书  0:否  1：是"';
comment on column BDC_SL_XM_LSGX.bdcdyh
  is '不动产单元号';
comment on column BDC_SL_XM_LSGX.zl
  is '坐落';
comment on column BDC_SL_XM_LSGX.qlr
  is '权利人';
alter table BDC_SL_XM_LSGX
  add constraint PK_BDC_SL_XM_LSGX primary key (GXID)
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

--changeset BDC_SL_XM_LSGX:2 failOnError:false runOnChange:true runAlways:false
CREATE INDEX BDC_SL_XM_LSGX_XMID ON BDC_SL_XM_LSGX(XMID);

--changeset BDC_SL_XM_LSGX:3 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XM_LSGX modify ZXYQL default null;

