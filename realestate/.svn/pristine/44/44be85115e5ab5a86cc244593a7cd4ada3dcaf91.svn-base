--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_CDXX:1 failOnError:false runOnChange:true runAlways:false
 create table BDC_CDBLXX
            (
            BLXXID VARCHAR2(32) NOT NULL,
            XMID VARCHAR2(32) NOT NULL,
            SQRMC VARCHAR2(200),
            SQRZJZL NUMBER(2),
            SQRZJH VARCHAR2(100),
            FWZL VARCHAR2(1000),
            JZMJ NUMBER(15,4),
            QDFS NUMBER(2),
            DJSJ DATE,
            BZ VARCHAR2(500),
            GYR   VARCHAR2(200),
            ZYSJ   DATE
            );
comment on table BDC_CDBLXX is '不动产补录信息表';
comment on column BDC_CDBLXX.BLXXID is '补录信息id';
comment on column BDC_CDBLXX.XMID is '项目id';
comment on column BDC_CDBLXX.SQRMC is '申请人名称';
comment on column BDC_CDBLXX.SQRZJZL is '申请人证件种类';
comment on column BDC_CDBLXX.SQRZJH is '申请人证件号';
comment on column BDC_CDBLXX.FWZL is '房屋坐落';
comment on column BDC_CDBLXX.JZMJ is '建筑面积';
comment on column BDC_CDBLXX.QDFS is '取得方式';
comment on column BDC_CDBLXX.DJSJ is '登记时间';
comment on column BDC_CDBLXX.BZ is '备注';
comment on column BDC_CDBLXX.GYR is '共有人';
comment on column BDC_CDBLXX.ZYSJ is '转移时间';

-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_CDBLXX
    add constraint BDC_CDBLXX_PK primary key (BLXXID) using index
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
