--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SFXM_PZ:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_SFXM_PZ
(
  SFXMPZID VARCHAR2(32) not null,
  DJXL     VARCHAR2(32),
  SFXMDM   VARCHAR2(32),
  XH       NUMBER(3),
  SL     NUMBER(5),
  JEDW     VARCHAR2(50),
  SFXMBZ   VARCHAR2(32),
  QLRLX    VARCHAR2(100),
  YSJE     NUMBER(12,4),
  SFXMMC   VARCHAR2(200)
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
comment on table BDC_SL_SFXM_PZ
  is '不动产收费项目配置';
comment on column BDC_SL_SFXM_PZ.SFXMPZID
  is '收费项目配置id';
comment on column BDC_SL_SFXM_PZ.DJXL
  is '登记小类';
comment on column BDC_SL_SFXM_PZ.SFXMDM
  is '收费项目代码';
comment on column BDC_SL_SFXM_PZ.XH
  is '序号';
comment on column BDC_SL_SFXM_PZ.SL
  is '默认数量';
comment on column BDC_SL_SFXM_PZ.JEDW
  is '单位';
comment on column BDC_SL_SFXM_PZ.SFXMBZ
  is '收费项目标准';
comment on column BDC_SL_SFXM_PZ.QLRLX
  is '权利人类型';
comment on column BDC_SL_SFXM_PZ.YSJE
  is '应收金额';
comment on column BDC_SL_SFXM_PZ.SFXMMC
  is '收费项目名称';
alter table BDC_SL_SFXM_PZ
  add constraint PK_BDC_SFXM_PZ primary key (SFXMPZID)
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

--changeset BDC_SL_SFXM_PZ:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM_PZ rename column qlrlx to qlrlb;
comment on column BDC_SL_SFXM_PZ.qlrlb
    is '权利人类别';

--changeset BDC_SL_SFXM_PZ:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM_PZ
    ADD (JSFF VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFXM_PZ.JSFF IS '计算方法';

--changeset BDC_SL_SFXM_PZ:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM_PZ
    ADD (DJYY VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_SFXM_PZ.DJYY IS '登记原因';

--changeset BDC_SL_SFXM_PZ:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM_PZ
    ADD (ddjb VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_SFXM_PZ.ddjb IS '地段级别';
ALTER TABLE BDC_SL_SFXM_PZ
    ADD (sfxmdj NUMBER(12,4));
COMMENT ON COLUMN BDC_SL_SFXM_PZ.sfxmdj IS '收费项目单价';
