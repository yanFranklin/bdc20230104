--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_DYAQ:1 failOnError:false runOnChange:true runAlways:false
-- Create table
create table BDC_SL_DYAQ
(
  ID        VARCHAR2(32) not null,
  XMID      VARCHAR2(32) not null,
  DYFS      NUMBER(1),
  ZJJZWZL   VARCHAR2(1000),
  ZJJZWDYFW VARCHAR2(1000),
  ZWLXQSSJ  DATE,
  ZWLXJSSJ  DATE,
  ZGZQQDSE  NUMBER(15,6),
  ZGZQQDSS  VARCHAR2(1000),
  DYSW      NUMBER(4),
  DBFW      VARCHAR2(100),
  DKFS      VARCHAR2(100),
  BDBZZQSE  NUMBER(15,6),
  FWPGJG    NUMBER(15,6),
  TDPGJG    NUMBER(15,6),
  FWDYJG    NUMBER(15,6),
  TDDYJG    NUMBER(15,6),
  TDDYMJ    NUMBER(15,2),
  FWDYMJ    NUMBER(15,2),
  SFGTDB    NUMBER(1),
  DYBDCLX   NUMBER(1),
  ZWR       VARCHAR2(200),
  FJ        VARCHAR2(1000),
  QLQTZK    VARCHAR2(1000)
)
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
-- Add comments to the table
comment on table BDC_SL_DYAQ
  is '不动产受理抵押信息';
-- Add comments to the columns
comment on column BDC_SL_DYAQ.ID
  is '主键';
comment on column BDC_SL_DYAQ.XMID
  is '项目ID';
comment on column BDC_SL_DYAQ.DYFS
  is '抵押方式';
comment on column BDC_SL_DYAQ.ZJJZWZL
  is '在建建筑物坐落';
comment on column BDC_SL_DYAQ.ZJJZWDYFW
  is '在建建筑物抵押范围';
comment on column BDC_SL_DYAQ.ZWLXQSSJ
  is '债务履行起始时间';
comment on column BDC_SL_DYAQ.ZWLXJSSJ
  is '债务履行结束时间';
comment on column BDC_SL_DYAQ.ZGZQQDSE
  is '最高债权确定数额';
comment on column BDC_SL_DYAQ.ZGZQQDSS
  is '最高债权确定事实';
comment on column BDC_SL_DYAQ.DYSW
  is '抵押顺位';
comment on column BDC_SL_DYAQ.DBFW
  is '担保范围';
comment on column BDC_SL_DYAQ.DKFS
  is '贷款方式';
comment on column BDC_SL_DYAQ.BDBZZQSE
  is '被担保主债权数额';
comment on column BDC_SL_DYAQ.FWPGJG
  is '房屋评估价格';
comment on column BDC_SL_DYAQ.TDPGJG
  is '土地评估价格';
comment on column BDC_SL_DYAQ.FWDYJG
  is '房屋抵押价格';
comment on column BDC_SL_DYAQ.TDDYJG
  is '土地抵押价格';
comment on column BDC_SL_DYAQ.TDDYMJ
  is '土地抵押面积';
comment on column BDC_SL_DYAQ.FWDYMJ
  is '房屋抵押面积';
comment on column BDC_SL_DYAQ.SFGTDB
  is '是否共同担保';
comment on column BDC_SL_DYAQ.DYBDCLX
  is '抵押不动产类型';
comment on column BDC_SL_DYAQ.ZWR
  is '债务人';
comment on column BDC_SL_DYAQ.FJ
  is '附记';
comment on column BDC_SL_DYAQ.QLQTZK
  is '权利其他状况';

