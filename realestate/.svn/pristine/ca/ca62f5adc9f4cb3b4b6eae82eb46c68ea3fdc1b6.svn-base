--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_XZXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_XZXX
(
    xzxxid    VARCHAR2(32) not null,
    xmid      VARCHAR2(32),
    gzlslid   VARCHAR2(32),
    qlr       VARCHAR2(50),
    bdcqzh    VARCHAR2(200),
    zl        VARCHAR2(200),
    xznr      VARCHAR2(2000),
    blfs      NUMBER(2),
    xgr       VARCHAR2(50),
    xgrq      DATE
);
comment on table BDC_SL_XZXX
    is '受理修正信息';
comment on column BDC_SL_XZXX.xzxxid
    is '修正信息ID';
comment on column BDC_SL_XZXX.xmid
    is '项目id';
comment on column BDC_SL_XZXX.qlr
    is '权利人';
comment on column BDC_SL_XZXX.bdcqzh
    is '不动产证号';
comment on column BDC_SL_XZXX.zl
    is '坐落';
comment on column BDC_SL_XZXX.xznr
    is '修正内容';
comment on column BDC_SL_XZXX.blfs
    is '补录方式';
comment on column BDC_SL_XZXX.xgrq
    is '修改日期 YYYY-MM-DD HH-MM-SS';
comment on column BDC_SL_XZXX.xgr
    is '修改人';
-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_SL_XZXX
    add constraint PK_BDC_SL_XZXX primary key (XZXXID)
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

--changeset BDC_SL_XZXX:2 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XZXX rename COLUMN XMID TO YXMID;
alter table BDC_SL_XZXX
    modify ZL VARCHAR2(500);

--changeset BDC_SL_XZXX:3 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XZXX
    add XZLY number(2);
comment on column BDC_SL_XZXX.XZLY
    is '修正来源 1-选择台账创建 2-流程创建 3-无数据创建';

--changeset BDC_SL_XZXX:4 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XZXX
    add XZYJ VARCHAR2(500);
comment on column BDC_SL_XZXX.XZYJ
    is '修正意见';

--changeset BDC_SL_XZXX:5 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XZXX
    add YLCJDMC VARCHAR2(500);
comment on column BDC_SL_XZXX.YLCJDMC
    is '原流程节点名称';
