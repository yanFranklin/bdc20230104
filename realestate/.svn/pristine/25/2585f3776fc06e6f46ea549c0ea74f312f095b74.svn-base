--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_CKXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_CKXX
(
  CKID VARCHAR2(32) not null,
  CKH  VARCHAR2(50),
  CKZT NUMBER(1),
  ZXMC VARCHAR2(50),
  GXSJ DATE,
  HJH  VARCHAR2(50)
)
--changeset BDC_CKXX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_CKXX
  is '不动产窗口信息';
-- Add comments to the columns
comment on column BDC_CKXX.CKID
  is '窗口ID';
comment on column BDC_CKXX.CKH
  is '窗口号';
comment on column BDC_CKXX.CKZT
  is '窗口状态（0正常 1暂停）';
comment on column BDC_CKXX.ZXMC
  is '中心名称';
comment on column BDC_CKXX.GXSJ
  is '更新时间';
comment on column BDC_CKXX.HJH
  is '呼叫号';