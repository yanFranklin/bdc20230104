--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZXYW_YWBM_REL:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZXYW_YWBM_REL
(
  RELID VARCHAR2(32),
  ZXMC  VARCHAR2(50),
  XYWMC VARCHAR2(100),
  YWBM  VARCHAR2(50),
  SFXS  VARCHAR2(2),
  CKS   NUMBER(4)
)
tablespace BDCDJ_3
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
-- Add comments to the columns
comment on column BDC_ZXYW_YWBM_REL.RELID
  is '关系id';
comment on column BDC_ZXYW_YWBM_REL.ZXMC
  is '中心名称';
comment on column BDC_ZXYW_YWBM_REL.XYWMC
  is '新业务名称';
comment on column BDC_ZXYW_YWBM_REL.YWBM
  is '业务编码';
comment on column BDC_ZXYW_YWBM_REL.SFXS
  is '是否显示';
comment on column BDC_ZXYW_YWBM_REL.CKS
  is '窗口数';

--changeset BDC_ZXYW_YWBM_REL:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_ZXYW_YWBM_REL add(ZXSXH NUMBER(4));
COMMENT ON COLUMN BDC_ZXYW_YWBM_REL.ZXSXH IS '中心顺序号';

--changeset BDC_ZXYW_YWBM_REL:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_ZXYW_YWBM_REL add(DDSJ NUMBER);
COMMENT ON COLUMN BDC_ZXYW_YWBM_REL.DDSJ IS '等待时间';
