--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SJXG:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_SJXG
(
  sjxgid   VARCHAR2(32) not null,
  gzlslid  VARCHAR2(32),
  wtlx VARCHAR2(200),
  sqr VARCHAR2(200),
  sqsj DATE,
  wtms VARCHAR2(1000)
)
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table BDC_SL_SJXG
  is '不动产数据修改';
-- Add comments to the columns
comment on column BDC_SL_SJXG.sjxgid
  is '数据修改ID';
comment on column BDC_SL_SJXG.gzlslid
  is '工作流实例ID';
comment on column BDC_SL_SJXG.wtlx
  is '问题类型';
  comment on column BDC_SL_SJXG.sqr
  is '申请人';
  comment on column BDC_SL_SJXG.sqsj
  is '申请时间';
comment on column BDC_SL_SJXG.wtms
    is '问题描述';
-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_SL_SJXG
    add constraint PK_BDC_SL_SJXG primary key (SJXGID)
        using index
            pctfree 10
            initrans 2
            maxtrans 255;