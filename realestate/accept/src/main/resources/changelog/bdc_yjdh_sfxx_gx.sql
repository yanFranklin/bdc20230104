--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_yjdh_sfxx_gx:1 failOnError:false runOnChange:true runAlways:false

CREATE TABLE BDC_YJDH_SFXX_GX
  (
    GXID  VARCHAR2(32) NOT NULL ENABLE,
    YJDH  VARCHAR2(100),
    SLBH    VARCHAR2(50),
    GZLSLID VARCHAR2(32),
    SFXXID   VARCHAR2(32),
    constraint BDC_YJDH_SFXX_GX primary key(GXID)
);
COMMENT ON COLUMN BDC_YJDH_SFXX_GX.GXID IS '关系ID';
COMMENT ON COLUMN BDC_YJDH_SFXX_GX.YJDH IS '月结单号';
COMMENT ON COLUMN BDC_YJDH_SFXX_GX.SLBH IS '受理编号';
COMMENT ON COLUMN BDC_YJDH_SFXX_GX.GZLSLID IS '工作流实例ID';
COMMENT ON COLUMN BDC_YJDH_SFXX_GX.SFXXID IS '收费信息ID';
CREATE INDEX "BDC_YJDH_SFXX_GX_YJDH_INDEX" ON BDC_YJDH_SFXX_GX (YJDH);