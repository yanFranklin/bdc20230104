--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_DSFDJ_JHXX:1 failOnError:false runOnChange:true runAlways:false
-- Create table
create table BDC_DSFDJ_JHXX
(
  BDCXMID   VARCHAR2(32) not null,
  DSFXMID   VARCHAR2(32),
  DSFDJZT   NUMBER(10) default 0,
  DSFYJ     VARCHAR2(1000),
  DSFDJSLBH VARCHAR2(100),
  DSFSLSJ   DATE,
  FCLX      VARCHAR2(20),
  BDCSHSJ   DATE,
  BDCSLBH   VARCHAR2(50),
  DSFDYWYBH VARCHAR2(20),
  BDCSPRXM  VARCHAR2(50),
  DYDSFJSZT VARCHAR2(10)
)
tablespace BDCDJ_HEFEI
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns
comment on column BDC_DSFDJ_JHXX.BDCXMID
  is '不动产项目ID';
comment on column BDC_DSFDJ_JHXX.DSFXMID
  is '第三方项目ID';
comment on column BDC_DSFDJ_JHXX.DSFDJZT
  is '第三方登记状态';
comment on column BDC_DSFDJ_JHXX.DSFYJ
  is '第三方意见';
comment on column BDC_DSFDJ_JHXX.DSFDJSLBH
  is '第三方登记受理编号';
comment on column BDC_DSFDJ_JHXX.DSFSLSJ
  is '第三方受理时间';
comment on column BDC_DSFDJ_JHXX.FCLX
  is '房产类型';
comment on column BDC_DSFDJ_JHXX.BDCSHSJ
  is '不动产审核时间';
comment on column BDC_DSFDJ_JHXX.BDCSLBH
  is '不动产受理编号';
comment on column BDC_DSFDJ_JHXX.DSFDYWYBH
  is '第三方单元唯一编号';
comment on column BDC_DSFDJ_JHXX.BDCSPRXM
  is '不动产审批人姓名';
comment on column BDC_DSFDJ_JHXX.DYDSFJSZT
  is '调用第三方接收状态';
-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_DSFDJ_JHXX add constraint PK_BDC_DSFDJ_JHXX primary key (BDCXMID);





