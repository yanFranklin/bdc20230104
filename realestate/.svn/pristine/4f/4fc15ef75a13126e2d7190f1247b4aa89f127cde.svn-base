--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_sl_ywlz:1 failOnError:false runOnChange:true runAlways:false
-- Create table
create table bdc_sl_ywlz
(
    ywlzid  VARCHAR2(32) not null,
    gzlslid VARCHAR2(32) not null,
    xmid    VARCHAR2(32) not null,
    bdcqzh  VARCHAR2(2000),
    ywlzbh  VARCHAR2(50),
    yyr     Varchar2(200),
    yyrdh   VARCHAR2(200),
    bjcr    Varchar2(200),
    yyqk    VARCHAR2(2000),
    dcqk    Varchar2(2000),
    bz      VARCHAR2(2000)
);

comment on table bdc_sl_ywlz
    is '不动产受理业务流转表';
comment on column bdc_sl_ywlz.ywlzid
    is '业务流转信息主键';
comment on column bdc_sl_ywlz.gzlslid
    is '工作流实例id';
comment on column bdc_sl_ywlz.xmid
    is '项目id';
comment on column bdc_sl_ywlz.ywlzbh
    is '业务流转编号';
comment on column bdc_sl_ywlz.yyr
    is '预约人';
comment on column bdc_sl_ywlz.yyrdh
    is '预约人电话';
comment on column bdc_sl_ywlz.bjcr
    is '被继承人';
comment on column bdc_sl_ywlz.yyqk
    is '预约情况';
comment on column bdc_sl_ywlz.dcqk
    is '调查情况';
comment on column bdc_sl_ywlz.bz
    is '备注';
create index INDEX_YWLZ_GZLSLID on bdc_sl_ywlz (gzlslid);
create index INDEX_YWLZ_ywlzid on bdc_sl_ywlz (ywlzid);
alter table bdc_sl_ywlz
    add constraint bdc_sl_ywlz primary key (ywlzid)
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

--changeset bdc_sl_yjxx:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE bdc_sl_ywlz  ADD(ywly NUMBER(1));
COMMENT ON COLUMN bdc_sl_ywlz.ywly IS '业务来源 1-选择数据创建 2- 无数据创建';