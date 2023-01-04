--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_sl_lzr:1 failOnError:false runOnChange:true runAlways:false

CREATE TABLE BDC_SL_LZR
  (
    LZRID  VARCHAR2(32) NOT NULL ENABLE,
    XMID    VARCHAR2(32),
    LZRMC   VARCHAR2(100),
    LZRDH   VARCHAR2(50),
    LZRZJZL NUMBER,
    LZRZJH  VARCHAR2(50),
    SXH   NUMBER,
    constraint BDC_SL_LZR primary key (LZRID)
);
COMMENT ON COLUMN BDC_SL_LZR.LZRID IS '领证人id';
COMMENT ON COLUMN BDC_SL_LZR.XMID IS '项目id';
COMMENT ON COLUMN BDC_SL_LZR.LZRMC IS '领证人';
COMMENT ON COLUMN BDC_SL_LZR.LZRDH IS '领证人电话';
COMMENT ON COLUMN BDC_SL_LZR.LZRZJZL IS '领证人证件类型';
COMMENT ON COLUMN BDC_SL_LZR.LZRZJH IS '领证人证件号';
COMMENT ON COLUMN BDC_SL_LZR.SXH IS '顺序号';
CREATE INDEX "BDC_SL_LZR_XMID_INDEX" ON BDC_SL_LZR (XMID);

--changeset BDC_SL_LZR:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_LZR
    add (GZLSLID VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_LZR.GZLSLID IS '工作流实例ID';

--changeset BDC_SL_LZR:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_LZR
    add (lzfs NUMBER(2));
COMMENT ON COLUMN BDC_SL_LZR.lzfs IS '领证方式';