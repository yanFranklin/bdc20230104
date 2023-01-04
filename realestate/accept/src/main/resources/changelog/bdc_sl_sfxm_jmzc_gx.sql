--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SFXM_JMZC_GX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_SFXM_JMZC_GX
(
  gxid   VARCHAR2(32) not null,
  jmzc  VARCHAR2(200) not null,
  jmzcbz VARCHAR2(32) not null,
  jmzt  NUMBER(1) not null,
  sfxmdm   VARCHAR2(32) not null,
  sfxmdj NUMBER (12,4) default 0.00000
);
alter table BDC_SL_SFXM_JMZC_GX
  add constraint PK_BDC_SL_SFXM_JMZC_GX primary key (GXID);

  comment on table BDC_SL_XM_LSGX
  is '不动产受理收费项目减免政策关系表';
  comment on column BDC_SL_SFXM_JMZC_GX.sfxmdm
  is '收费项目代码';
  comment on column BDC_SL_SFXM_JMZC_GX.sfxmdj
  is '收费项目单价';
  comment on column BDC_SL_SFXM_JMZC_GX.jmzc
  is '减免政策';
  comment on column BDC_SL_SFXM_JMZC_GX.jmzt
  is '减免状态(选择是还是选择否生效减免政策)';
  comment on column BDC_SL_SFXM_JMZC_GX.jmzcbz
  is '减免政策标志(bdc_sl_sfxx减免字段的英文简写)';

--changeset BDC_SL_SFXM_JMZC_GX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM_JMZC_GX ADD(SFXMSL NUMBER(2));
COMMENT ON COLUMN BDC_SL_SFXM_JMZC_GX.SFXMSL IS '收费项目数量';