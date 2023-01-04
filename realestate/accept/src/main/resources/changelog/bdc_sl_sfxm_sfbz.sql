--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SFXM_SFBZ:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_SFXM_SFBZ
(
  SFXMDM VARCHAR2(32),
  SFXMBZ VARCHAR2(200)
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
comment on table BDC_SL_SFXM_SFBZ
  is '不动产收费项目收费标准';
comment on column BDC_SL_SFXM_SFBZ.SFXMDM
  is '收费项目代码';
comment on column BDC_SL_SFXM_SFBZ.SFXMBZ
  is '收费项目标准';

  --changeset BDC_SL_SFXM_SFBZ:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM_SFBZ ADD(SFXMDJ  NUMBER(12,4));
COMMENT ON COLUMN BDC_SL_SFXM_SFBZ.SFXMDJ IS '收费项目单价';
