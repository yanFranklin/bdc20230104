--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_YQ_FJXX:1 failOnError:false runOnChange:true runAlways:false
 create table BDC_SL_YQ_FJXX
 (
     ID VARCHAR2(32) not null,
     WJZXID VARCHAR2(32),
     GZLSLID VARCHAR2(32),
     SLBH VARCHAR2(32),
     QSZT VARCHAR2(10),
     FJMC VARCHAR2(100)
 );

comment on table BDC_SL_YQ_FJXX is '不动产综合查询日志';
comment on column BDC_SL_YQ_FJXX.ID is 'ID';
comment on column BDC_SL_YQ_FJXX.WJZXID is '文件中心NodeId(记录文件中心与其对应的NodeId)';
comment on column BDC_SL_YQ_FJXX.GZLSLID is '工作流实例ID';
comment on column BDC_SL_YQ_FJXX.SLBH is '受理编号';
comment on column BDC_SL_YQ_FJXX.QSZT is '签署状态';
comment on column BDC_SL_YQ_FJXX.FJMC is '附件名称';

-- Create/Recreate primary, unique and foreign key constraints
create index IDX_BDC_SLYQFJXX_SLBH on BDC_SL_YQ_FJXX (SLBH);

--changeset BDC_SL_YQ_FJXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_YQ_FJXX ADD (WJJMC VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_YQ_FJXX.WJJMC IS '文件夹名称';


