--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_XQXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_XQXX
(
    xqxxid VARCHAR2(32) not null,
    xmid   VARCHAR2(32),
    xqmc   VARCHAR2(200),
    xqnr   VARCHAR2(2000),
    xgzt   NUMBER(2),
    xgwcsj DATE,
    qrzt   NUMBER(2),
    qrwcsj DATE,
    bz     VARCHAR2(2000)
);
comment on table BDC_SL_XQXX
    is '受理需求信息';
comment on column BDC_SL_XQXX.xqxxid
    is '需求信息ID';
comment on column BDC_SL_XQXX.xmid
    is '项目id';
comment on column BDC_SL_XQXX.xqmc
    is '需求名称';
comment on column BDC_SL_XQXX.xqnr
    is '需求内容';
comment on column BDC_SL_XQXX.xgzt
    is '修改状态';
comment on column BDC_SL_XQXX.xgwcsj
    is '修改完成时间';
comment on column BDC_SL_XQXX.qrzt
    is '确认状态';
comment on column BDC_SL_XQXX.qrwcsj
    is '确认完成时间 YYYY-MM-DD HH-MM-SS';
comment on column BDC_SL_XQXX.bz
    is '备注';
-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_SL_XQXX
    add constraint PK_BDC_SL_XQXX primary key (XQXXID)
        using index
            pctfree 10
            initrans 2
            maxtrans 255
            storage
            (
            initial 128 K
            next 1 M
            minextents 1
            maxextents unlimited
            );

create index IDX_BDC_SL_XQXX_XMID on BDC_SL_XQXX (XMID)
    pctfree 10
    initrans 2
    maxtrans 255
    storage
    (
    initial 128 K
    next 1 M
    minextents 1
    maxextents unlimited
    );

--changeset BDC_SL_XQXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XQXX add (GZLSLID VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_XQXX.GZLSLID IS '工作流实例ID';