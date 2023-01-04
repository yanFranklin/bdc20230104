--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SJCL_DZZZ_DZ:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SJCL_DZZZ_DZ
(
    dzid   VARCHAR2(32) not null,
    clmc   VARCHAR2(2000),
    dzzzdm VARCHAR2(32),
    dzzzmc VARCHAR2(2000)
);

comment on table BDC_SJCL_DZZZ_DZ
    is '不动产收件材料电子证照对照表';
comment on column BDC_SJCL_DZZZ_DZ.dzid
    is '对照ID';
comment on column BDC_SJCL_DZZZ_DZ.clmc
    is '材料名称';
comment on column BDC_SJCL_DZZZ_DZ.dzzzdm
    is '电子证照代码';
comment on column BDC_SJCL_DZZZ_DZ.dzzzmc
    is '电子证照名称';

create index IDX_BDC_SJCL_DZZZ_CLMC on BDC_SJCL_DZZZ_DZ (CLMC)
    pctfree 10
    initrans 2
    maxtrans 255
    storage
    (
    initial 176M
    next 1M
    minextents 1
    maxextents unlimited
    );

create index IDX_BDC_SJCL_DZZZ_DZZZDM on BDC_SJCL_DZZZ_DZ (DZZZDM)
    pctfree 10
    initrans 2
    maxtrans 255
    storage
    (
    initial 176M
    next 1M
    minextents 1
    maxextents unlimited
    );