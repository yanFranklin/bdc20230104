--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZQ_FJCL:1 failOnError:false runOnChange:true runAlways:false
CREATE TABLE BDC_ZQ_FJCL
(
    FJID   VARCHAR2(32) NOT NULL,
    SQXXID VARCHAR2(32),
    CLMC   VARCHAR2(200),
    XH     NUMBER(3,0),
    YS     NUMBER(4,0),
    WJZXID NUMBER(12,0),
    BZ     VARCHAR2(2000)
);
--changeset BDC_ZQ_FJCL:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
COMMENT ON TABLE BDC_ZQ_FJCL IS '附件材料表';
-- Add comments to the columns
COMMENT ON COLUMN BDC_ZQ_FJCL.FJID IS '附件ID';
COMMENT ON COLUMN BDC_ZQ_FJCL.SQXXID IS '申请信息ID';
COMMENT ON COLUMN BDC_ZQ_FJCL.CLMC IS '附件名称';
COMMENT ON COLUMN BDC_ZQ_FJCL.XH IS '序号';
COMMENT ON COLUMN BDC_ZQ_FJCL.YS IS '页数';
COMMENT ON COLUMN BDC_ZQ_FJCL.WJZXID IS '文件中心NodeId';
COMMENT ON COLUMN BDC_ZQ_FJCL.BZ IS '备注';


