--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_GZYZ_LOG:1 failOnError:false runOnChange:true runAlways:false
 create table BDC_GZYZ_LOG
 (
     RZID VARCHAR2(32) not null,
     YZRID VARCHAR2(32),
     YZRZH VARCHAR2(100),
     YZSJ DATE,
     ZHBS VARCHAR2(200),
     ZHMC VARCHAR2(200),
     GZID VARCHAR2(32),
     GZMC VARCHAR2(200),
     YZCS VARCHAR2(2000),
     YZJG VARCHAR2(2000),
     SFTG NUMBER(1)
 );

-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_GZYZ_LOG add constraint PK_BDC_GZYZ_LOG primary key (RZID);

-- Add comments to the table
comment on table BDC_GZYZ_LOG is '不动产规则验证日志表';
-- Add comments to the columns
comment on column BDC_GZYZ_LOG.RZID is '日志id';
comment on column BDC_GZYZ_LOG.YZRID is '验证人id';
comment on column BDC_GZYZ_LOG.YZRZH is '验证人账号';
comment on column BDC_GZYZ_LOG.YZSJ is '验证时间';
comment on column BDC_GZYZ_LOG.ZHBS is '组合标示';
comment on column BDC_GZYZ_LOG.ZHMC is '组合名称';
comment on column BDC_GZYZ_LOG.GZID is '子规则id';
comment on column BDC_GZYZ_LOG.GZMC is '子规则名称';
comment on column BDC_GZYZ_LOG.YZCS is '验证参数';
comment on column BDC_GZYZ_LOG.YZJG is '验证结果';
comment on column BDC_GZYZ_LOG.SFTG is '是否通过';

--changeset BDC_GZYZ_LOG:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
ALTER TABLE BDC_GZYZ_LOG add(YZBS VARCHAR2(32));
COMMENT ON COLUMN BDC_GZYZ_LOG.YZBS IS '验证标识';
CREATE INDEX INDEX_BDC_GZYZ_LOG_YZBS on BDC_GZYZ_LOG (YZBS);
--changeset BDC_GZYZ_LOG:3 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
CREATE INDEX INDEX_BDC_GZYZ_LOG_ZHBS on BDC_GZYZ_LOG (ZHBS);