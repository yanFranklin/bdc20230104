--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZXYW:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZXYW
(
  ZXYWID VARCHAR2(32) not null,
  ZXMC   VARCHAR2(50),
  YWMC   VARCHAR2(50),
  DDSJ   NUMBER
)
--changeset BDC_ZXYW:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_ZXYW
  is '不动产中心业务表';
-- Add comments to the columns
comment on column BDC_ZXYW.ZXYWID
  is '中心业务id';
comment on column BDC_ZXYW.ZXMC
  is '中心名称';
comment on column BDC_ZXYW.YWMC
  is '业务名称';
comment on column BDC_ZXYW.DDSJ
  is '等待时间';

--changeset BDC_ZXYW:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_ZXYW add(YWBM VARCHAR2(2));
COMMENT ON COLUMN BDC_ZXYW.YWBM IS '业务编码';
ALTER TABLE BDC_ZXYW add(CKS NUMBER(4));
COMMENT ON COLUMN BDC_ZXYW.CKS IS '窗口数';
ALTER TABLE BDC_ZXYW add(CK VARCHAR2(1000));
COMMENT ON COLUMN BDC_ZXYW.CK IS '窗口';

--changeset BDC_ZXYW:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_ZXYW add(YWBMTEMP VARCHAR2(1000));
COMMENT ON COLUMN BDC_ZXYW.YWBMTEMP IS '业务编码';
UPDATE BDC_ZXYW SET YWBMTEMP=YWBM;
ALTER TABLE BDC_ZXYW DROP COLUMN YWBM;
ALTER TABLE BDC_ZXYW RENAME COLUMN YWBMTEMP TO YWBM;
