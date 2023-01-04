--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_QJDCSQ:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_QJDCSQ
(
  qjdcsqxxid   VARCHAR2(32) not null,
  gzlslid  VARCHAR2(32),
  xmid VARCHAR2(32),
  bdcdyh VARCHAR2(28),
  dcsqr VARCHAR2(100),
  zl VARCHAR2(500),
  dcmd VARCHAR2(200)
)
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table BDC_SL_QJDCSQ
  is '不动产受理权籍调查申请';
-- Add comments to the columns
comment on column BDC_SL_QJDCSQ.qjdcsqxxid
  is '权籍调查申请信息ID';
comment on column BDC_SL_QJDCSQ.gzlslid
  is '工作流示例ID';
  comment on column BDC_SL_QJDCSQ.xmid
  is '项目ID';
comment on column BDC_SL_QJDCSQ.bdcdyh
  is '不动产单元号';
  comment on column BDC_SL_QJDCSQ.dcsqr
  is '调查申请人';
  comment on column BDC_SL_QJDCSQ.zl
  is '坐落';
comment on column BDC_SL_QJDCSQ.dcmd
    is '调查目的';
-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_SL_QJDCSQ
    add constraint PK_BDC_SL_QJDCSQ primary key (QJDCSQXXID)
        using index
            pctfree 10
            initrans 2
            maxtrans 255;

