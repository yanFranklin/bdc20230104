--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_lc_tsjf_gx:1 failOnError:false runOnChange:true runAlways:false
-- Create table
create table bdc_lc_tsjf_gx
(
    gxid    VARCHAR2(32) not null,
    gzlslid VARCHAR2(32) not null,
    sfxxid  VARCHAR2(32) not null,
    slbh    VARCHAR2(50),
    tslx    VARCHAR2(50)
);
comment on table bdc_lc_tsjf_gx
    is '不动产 推送缴费 流程关系表';
comment on column bdc_lc_tsjf_gx.gxid
    is '关系主键';
comment on column bdc_lc_tsjf_gx.gzlslid
    is '工作流实例id';
comment on column bdc_lc_tsjf_gx.sfxxid
    is '收费信息id';
comment on column bdc_lc_tsjf_gx.slbh
    is '受理编号';
comment on column bdc_lc_tsjf_gx.tslx
    is '推送类型-产权/抵押';
create index INDEX_TSJF_GZLSLID on bdc_lc_tsjf_gx (gzlslid);
create index INDEX_TSJF_SFXXID on bdc_lc_tsjf_gx (sfxxid);
alter table bdc_lc_tsjf_gx
    add constraint PK_bdc_lc_tsjf_gx primary key (gxid)
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


--changeset bdc_lc_tsjf_gx:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE bdc_lc_tsjf_gx
    add (qlrmc VARCHAR2(500));
COMMENT ON COLUMN bdc_lc_tsjf_gx.qlrmc IS '权利人名称';
ALTER TABLE bdc_lc_tsjf_gx
    add (dlrmc VARCHAR2(500));
COMMENT ON COLUMN bdc_lc_tsjf_gx.dlrmc IS '代理人名称';

--changeset bdc_lc_tsjf_gx:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE bdc_lc_tsjf_gx
    add (lxrmc VARCHAR2(500));
COMMENT ON COLUMN bdc_lc_tsjf_gx.lxrmc IS '联系人名称-抵押推送时使用';
ALTER TABLE bdc_lc_tsjf_gx
    add (lxdh VARCHAR2(500));
COMMENT ON COLUMN bdc_lc_tsjf_gx.lxdh IS '联系电话-抵押推送时使用';

--changeset bdc_lc_tsjf_gx:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_LC_TSJF_GX add (TSID VARCHAR2(32));
COMMENT ON COLUMN BDC_LC_TSJF_GX.TSID IS '推送给财政的唯一标识ID';

--changeset bdc_lc_tsjf_gx:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_LC_TSJF_GX add (XH NUMBER);
COMMENT ON COLUMN BDC_LC_TSJF_GX.XH IS '序号';