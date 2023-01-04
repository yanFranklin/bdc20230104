--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_DJYY_SJCL_PZ:1 failOnError:false runOnChange:true runAlways:false

create table BDC_DJYY_SJCL_PZ
(
  pzid   VARCHAR2(32) not null,
  djxl   VARCHAR2(10),
  djyy   VARCHAR2(2000),
  clmc VARCHAR2(200),
  xh     NUMBER(3),
  mrfs   NUMBER(3),
  sjlx   NUMBER(2)
);

comment on table BDC_DJYY_SJCL_PZ
  is '不动产登记原因收件材料配置表';

comment on column BDC_DJYY_SJCL_PZ.pzid
  is '配置ID';
comment on column BDC_DJYY_SJCL_PZ.djxl
  is '登记小类';
comment on column BDC_DJYY_SJCL_PZ.djyy
  is '登记原因';
comment on column BDC_DJYY_SJCL_PZ.clmc
  is '收件材料名称';
comment on column BDC_DJYY_SJCL_PZ.xh
  is '序号';
comment on column BDC_DJYY_SJCL_PZ.mrfs
  is '默认份数';
comment on column BDC_DJYY_SJCL_PZ.sjlx
  is '收件类型';

--changeset BDC_DJYY_SJCL_PZ:2 failOnError:false runOnChange:true runAlways:false
  ALTER TABLE BDC_DJYY_SJCL_PZ add(SQBM VARCHAR2(50));
  COMMENT ON COLUMN BDC_DJXL_PZ.SQBM IS '收取部门';

--changeset BDC_DJYY_SJCL_PZ:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_DJYY_SJCL_PZ ADD(SFBC NUMBER(1) default '0');
COMMENT ON COLUMN BDC_DJYY_SJCL_PZ.SFBC IS '是否必传';