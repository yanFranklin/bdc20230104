--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_sl_dbxx_fwtcxx:1 failOnError:false runOnChange:true runAlways:false
-- Create table
create table bdc_sl_dbxx_fwtcxx
(
    id      varchar2(32 byte) not null,
    qlrmc   varchar2(100 byte),
    qlrzjh  varchar2(100 byte),
    qlrzjzl number(1,0),
    bdcqzh  varchar2(100 byte),
    zl      varchar2(500 byte),
    yt      varchar2(100 byte),
    sfbdtg  number(1,0),
    yy      varchar2(1000 byte),
    sqrid   varchar2(50 byte)
)
--changeset BDC_CKXX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table bdc_sl_dbxx_fwtcxx
  is '房屋套次比对信息';
-- Add comments to the columns
comment on column bdc_sl_dbxx_fwtcxx.id
  is '主键';
comment on column bdc_sl_dbxx_fwtcxx.qlrmc
  is '权利人名称';
comment on column bdc_sl_dbxx_fwtcxx.qlrzjh
  is '权利人证件号';
comment on column bdc_sl_dbxx_fwtcxx.qlrzjzl
  is '配偶证件种类';
comment on column bdc_sl_dbxx_fwtcxx.bdcqzh
  is '不动产权证号';
comment on column bdc_sl_dbxx_fwtcxx.zl
  is '坐落';
comment on column bdc_sl_dbxx_fwtcxx.yt
  is '用途';
comment on column bdc_sl_dbxx_fwtcxx.sfbdtg
  is '是否通过对比 0:是 1:否';
comment on column bdc_sl_dbxx_fwtcxx.yy
  is '比对不通过原因';
comment on column bdc_sl_dbxx_fwtcxx.sqrid
  is '申请人id';
-- Create/Recreate primary, unique and foreign key constraints
alter table bdc_sl_dbxx_fwtcxx add constraint pk_bdc_sl_dbxx_hyxx primary key (id)


