--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_sl_cxcs:1 failOnError:false runOnChange:true runAlways:false
-- Create table
create table BDC_SL_CXCS
(
    CXID    VARCHAR2(32) not null,
    GZLSLID VARCHAR2(32) not null,
    CXR     VARCHAR2(32),
    CZSJ    DATE,
    CXCS    VARCHAR2(4000)
);
comment on table BDC_SL_CXCS is '不动产受理查询参数表';
comment on column BDC_SL_CXCS.CXID is '主键';
comment on column BDC_SL_CXCS.GZLSLID is '工作流实例id';
comment on column BDC_SL_CXCS.CXR is '查询人';
comment on column BDC_SL_CXCS.CZSJ is '操作时间';
comment on column BDC_SL_CXCS.CXCS is '查询参数';
create index INDEX_CXCX_GZLSLID on BDC_SL_CXCS (gzlslid);
alter table BDC_SL_CXCS add constraint PK_BDC_SL_CXCS primary key (CXID)
        using index
            pctfree 10
            initrans 2
            maxtrans 255
            storage
            (
            initial 64 K
            next 1 M
            minextents 1
            maxextents unlimited
            );