--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_zszm_dyl:1 failOnError:false runOnChange:true runAlways:false
 create table BDC_ZSZM_DYL
 (
     ID VARCHAR2(32) not null,
     ORGCODE VARCHAR2(50),
     ORGNAME VARCHAR2(50),
     ZSLX NUMBER(1),
     DYL NUMBER(6),
     DYR VARCHAR2(20),
     DYSJ DATE
 );

-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_ZSZM_DYL add constraint PK_BDC_ZSZM_DYL primary key (ID);

-- Add comments to the table
comment on table BDC_ZSZM_DYL is '不动产证书证明打印量';
-- Add comments to the columns
comment on column BDC_ZSZM_DYL.ID is '日志id';
comment on column BDC_ZSZM_DYL.ORGCODE is '组合编码';
comment on column BDC_ZSZM_DYL.ORGNAME is '组织名称';
comment on column BDC_ZSZM_DYL.ZSLX is '证书类型';
comment on column BDC_ZSZM_DYL.DYL is '打印量';
comment on column BDC_ZSZM_DYL.DYR is '打印人';
comment on column BDC_ZSZM_DYL.DYSJ is '打印时间';
