--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_sl_fgyhsf:1 failOnError:false runOnChange:true runAlways:false
-- Create table
create table bdc_sl_fgyhsf
(
    fgyhsfid  VARCHAR2(32) not null,
    gzlslid VARCHAR2(32) not null,
    xmid    VARCHAR2(32)  null,
    jgyy VARCHAR2(2000),
    jgrq Date,
    xzbh  VARCHAR2(50),
    slbh  VARCHAR2(50),
    bz      VARCHAR2(2000),
    yfwsyzh  VARCHAR2(2000),
    fwsyqr VARCHAR2(100)
);
comment on table bdc_sl_fgyhsf
    is '不动产受理房改优惠售房表';
comment on column bdc_sl_fgyhsf.fgyhsfid
    is '房改优惠售房主键';
comment on column bdc_sl_fgyhsf.gzlslid
    is '工作流实例id';
comment on column bdc_sl_fgyhsf.xmid
    is '项目id';
comment on column bdc_sl_fgyhsf.jgyy
    is '接轨原因';
comment on column bdc_sl_fgyhsf.jgrq
    is '接轨日期';
comment on column bdc_sl_fgyhsf.xzbh
    is '新证编号';
comment on column bdc_sl_fgyhsf.slbh
    is '受理编号';
comment on column bdc_sl_fgyhsf.bz
    is '备注';
comment on column bdc_sl_fgyhsf.yfwsyzh
    is '原房屋所有权证号';
comment on column bdc_sl_fgyhsf.fwsyqr
    is '房屋所有权人';


create index INDEX_FGYHSF_GZLSLID on bdc_sl_fgyhsf (gzlslid);
create index INDEX_FGYHSF_FGYHSFID on bdc_sl_fgyhsf (fgyhsfid);
alter table bdc_sl_fgyhsf
    add constraint bdc_sl_fgyhsf primary key (fgyhsfid)
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

--changeset bdc_sl_fgyhsf:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE bdc_sl_fgyhsf ADD(yxmid VARCHAR2(32));
COMMENT ON COLUMN bdc_sl_fgyhsf.yxmid IS '原项目ID';