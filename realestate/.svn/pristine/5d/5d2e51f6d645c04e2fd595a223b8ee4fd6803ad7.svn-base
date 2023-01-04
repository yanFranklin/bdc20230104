--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SJCL_PZ:1 failOnError:false runOnChange:true runAlways:false

create table bdc_sl_sjcl_pz
   (
     PZID VARCHAR2(32),
     CLMC   VARCHAR2(200),
     DJXL   VARCHAR2(32),
     SJLX   NUMBER,
     MRFS   NUMBER(3),
     XH     NUMBER(3)
   )
   tablespace BDCSL
     pctfree 10
     initrans 1
     maxtrans 255
     storage
     (
       initial 16K
       next 8K
       minextents 1
       maxextents unlimited
     );
   comment on column bdc_sl_sjcl_pz.PZID
     is '主键';
   comment on column bdc_sl_sjcl_pz.CLMC
     is '材料名称';
      comment on column bdc_sl_sjcl_pz.DJXL
     is '登记小类';
      comment on column bdc_sl_sjcl_pz.SJLX
     is '收件类型';
      comment on column bdc_sl_sjcl_pz.MRFS
     is '默认份数';
      comment on column bdc_sl_sjcl_pz.XH
     is '序号';
alter table BDC_SL_SJCL_PZ
  add constraint BDC_SL_SJCL_PZ primary key (PZID)
  using index
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

--changeset BDC_SL_SJCL_PZ:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL_PZ ADD(SQBM VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SJCL_PZ.SQBM IS '收取部门';

--changeset BDC_SL_SJCL_PZ:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL_PZ ADD(SFBC NUMBER(1) default '0');
COMMENT ON COLUMN BDC_SL_SJCL_PZ.SFBC IS '是否必传';