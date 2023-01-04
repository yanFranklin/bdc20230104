--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_sl_dbxx_hyxx:1 failOnError:false runOnChange:true runAlways:false
-- Create table


create table bdc_sl_dbxx_hyxx
(
    id     varchar2(32 byte) not null,
    hyzt   varchar2(32 byte),
    poxm   varchar2(32 byte),
    pozjzl number(1,0),
    pozjh  varchar2(50 byte),
    hydjjg varchar2(100 byte),
    djrq   date,
    sfbdtg number(1,0),
    yy     varchar2(1000 byte),
    sqrid  varchar2(50 byte)
)
--changeset bdc_sl_dbxx_hyxx:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table bdc_sl_dbxx_hyxx
  is '婚姻比对信息';
-- Add comments to the columns
comment on column bdc_sl_dbxx_hyxx.id
  is '主键';
comment on column bdc_sl_dbxx_hyxx.hyzt
  is '婚姻状态';
comment on column bdc_sl_dbxx_hyxx.poxm
  is '配偶姓名';
comment on column bdc_sl_dbxx_hyxx.pozjzl
  is '配偶证件种类';
comment on column bdc_sl_dbxx_hyxx.pozjh
  is '配偶证件号';
comment on column bdc_sl_dbxx_hyxx.hydjjg
  is '婚姻登记机构';
comment on column bdc_sl_dbxx_hyxx.djrq
  is '登记日期';
comment on column bdc_sl_dbxx_hyxx.sfbdtg
  is '是否通过对比 0:是 1:否';
comment on column bdc_sl_dbxx_hyxx.yy
  is '比对不通过原因';
comment on column bdc_sl_dbxx_hyxx.sqrid
  is '申请人id';

-- Create/Recreate primary, unique and foreign key constraints
alter table bdc_sl_dbxx_hyxx add constraint pk_bdc_sl_dbxx_hyxx primary key (id)