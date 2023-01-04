--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_wqba_lc_gx:1 failOnError:false runOnChange:true runAlways:false

CREATE TABLE BDC_WQBA_LC_GX
(
    GXID    VARCHAR2(32) NOT NULL ENABLE,
    GZLSLID VARCHAR2(32),
    HTBH    VARCHAR2(50),
    constraint BDC_WQBA_LC_GX primary key (GXID)
);
comment on table BDC_WQBA_LC_GX
    is '不动产网签 备案和流程关系表';
COMMENT ON COLUMN BDC_WQBA_LC_GX.GXID IS '关系id';
COMMENT ON COLUMN BDC_WQBA_LC_GX.GZLSLID IS '工作流实例id';
COMMENT ON COLUMN BDC_WQBA_LC_GX.HTBH IS '合同编号';
CREATE INDEX "BDC_WQBA_LC_GX" ON BDC_WQBA_LC_GX (gzlslid);
CREATE INDEX "BDC_WQBA_LC_GX" ON BDC_WQBA_LC_GX (HTBH);