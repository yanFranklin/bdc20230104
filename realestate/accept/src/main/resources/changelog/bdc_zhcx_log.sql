--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZHCX_LOG:1 failOnError:false runOnChange:true runAlways:false
 create table BDC_ZHCX_LOG
 (
     rzid VARCHAR2(32) not null,
     rzlx NUMBER(1),
     cxzh VARCHAR2(32),
     cxr VARCHAR2(50),
     szbm VARCHAR2(100),
     cxtj VARCHAR2(4000),
     czjg CLOB,
     czsj DATE,
     dylx VARCHAR2(50)
 );

tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255;

comment on table BDC_ZHCX_LOG is '不动产综合查询日志';
comment on column BDC_ZHCX_LOG.RZID is '日志ID';
comment on column BDC_ZHCX_LOG.RZLX is '日志类型   0：查询日志  1：打印日志';
comment on column BDC_ZHCX_LOG.CXZH is '查询账号';
comment on column BDC_ZHCX_LOG.CXR is '查询人';
comment on column BDC_ZHCX_LOG.SZBM is '所在部门';
comment on column BDC_ZHCX_LOG.CXTJ is '查询条件';
comment on column BDC_ZHCX_LOG.CZJG is '查询操作或打印操作结果';
comment on column BDC_ZHCX_LOG.CZSJ is '查询操作或打印操作时间';
comment on column BDC_ZHCX_LOG.DYLX is '打印类型   yfwfzm：有房无房打印  yfwfzmls：有房无房历史打印  qszm：权属证明  ywtdxxzm：有无土地信息证明';

-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_ZHCX_LOG add constraint PK_BDC_ZHCX_LOG primary key (RZID);

--changeset BDC_ZHCX_LOG:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_ZHCX_LOG add(CXRID VARCHAR2(50));
COMMENT ON COLUMN BDC_ZHCX_LOG.CXRID IS '查询人员ID';
ALTER TABLE BDC_ZHCX_LOG add(SZBMDM VARCHAR2(50));
COMMENT ON COLUMN BDC_ZHCX_LOG.SZBMDM IS '所在部门代码';