--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_HJXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_HJXX
(
  HJID VARCHAR2(32) not null,
  ZXMC VARCHAR2(50),
  SCSJ DATE,
  GXSJ DATE,
  HJZT NUMBER(1),
  YWMC VARCHAR2(50),
  HJH  VARCHAR2(50),
  PF   NUMBER
)
--changeset BDC_HJXX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the columns
comment on column BDC_HJXX.HJID
  is '呼叫ID';
comment on column BDC_HJXX.ZXMC
  is '中心名称';
comment on column BDC_HJXX.SCSJ
  is '生成时间';
comment on column BDC_HJXX.GXSJ
  is '更新时间';
comment on column BDC_HJXX.HJZT
  is '状态 （0未呼叫 1：已呼叫）';
comment on column BDC_HJXX.YWMC
  is '业务类型';
comment on column BDC_HJXX.HJH
  is '呼叫号';
comment on column BDC_HJXX.PF
  is '评分';

--changeset BDC_HJXX:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_HJXX add(YWBM VARCHAR2(2));
COMMENT ON COLUMN BDC_HJXX.YWBM IS '业务编码';

--changeset BDC_HJXX:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_HJXX add(CKH VARCHAR2(50));
COMMENT ON COLUMN BDC_HJXX.CKH IS '窗口号';
ALTER TABLE BDC_HJXX add(WCSJ DATE);
COMMENT ON COLUMN BDC_HJXX.WCSJ IS '完成时间';