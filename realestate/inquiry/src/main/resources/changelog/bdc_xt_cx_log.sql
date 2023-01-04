--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_XT_CX_LOG:1 failOnError:false runOnChange:true runAlways:false
create table BDC_XT_CX_LOG
(
    ID    VARCHAR2(32) not null,
    YHM   VARCHAR2(100),
    YHZH  VARCHAR2(100),
    DLIP  VARCHAR2(50),
    CXTJ  VARCHAR2(4000),
    EXCEL VARCHAR2(500),
    CXJG  CLOB,
    CZSJ  DATE
);
--changeset BDC_XT_CX_LOG:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_XT_CX_LOG is '不动产系统查询日志表';
-- Add comments to the columns
comment on column BDC_XT_CX_LOG.ID    is '日志id';
comment on column BDC_XT_CX_LOG.YHM   is '用户名';
comment on column BDC_XT_CX_LOG.YHZH  is '用户账号';
comment on column BDC_XT_CX_LOG.DLIP  is '登录IP';
comment on column BDC_XT_CX_LOG.CXTJ  is '查询条件';
comment on column BDC_XT_CX_LOG.EXCEL is 'Excel查询文件';
comment on column BDC_XT_CX_LOG.CXJG  is '查询结果';
comment on column BDC_XT_CX_LOG.CZSJ  is '操作时间';

--changeset BDC_XT_CX_LOG:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_XT_CX_LOG ADD CXJGSL NUMBER(8);
comment on column BDC_XT_CX_LOG.CXJGSL is '查询结果数量';

--changeset BDC_XT_CX_LOG:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_XT_CX_LOG modify RZLX VARCHAR2(100);


