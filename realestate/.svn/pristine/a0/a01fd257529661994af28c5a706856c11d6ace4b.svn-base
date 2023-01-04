--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_HJXX_LOG:1 failOnError:false runOnChange:true runAlways:false
create table BDC_HJXX_LOG
(
  LOGID VARCHAR2(32) not null,
  SCSJ  VARCHAR2(30),
  TS    VARCHAR2(10),
  ZXMC  VARCHAR2(50)
)
--changeset BDC_HJXX_LOG:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_HJXX_LOG
  is '不动产中心呼叫日志信息';
-- Add comments to the columns
comment on column BDC_HJXX_LOG.LOGID
  is '日志id';
comment on column BDC_HJXX_LOG.SCSJ
  is '生成时间';
comment on column BDC_HJXX_LOG.TS
  is '条数';
comment on column BDC_HJXX_LOG.ZXMC
  is '中心名称';