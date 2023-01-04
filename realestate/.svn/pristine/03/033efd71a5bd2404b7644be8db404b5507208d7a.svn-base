--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_HSXX_MX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_HSXX_MX
(
  hsxxmxid  VARCHAR2(32) not null,
  hsxxid    VARCHAR2(32) not null,
  mxzl      VARCHAR2(200),
  ynse      NUMBER(12,4),
  jmse      NUMBER(12,4),
  sjnse     NUMBER(12,4),
  sxh       NUMBER(3)
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
comment on table BDC_SL_HSXX_MX
  is '不动产受理核税信息明细';
comment on column BDC_SL_HSXX_MX.hsxxmxid
  is '核税信息明细ID';
comment on column BDC_SL_HSXX_MX.hsxxid
  is '核税信息ID';
comment on column BDC_SL_HSXX_MX.mxzl
  is '明细种类';
comment on column BDC_SL_HSXX_MX.ynse
  is '应纳税额';
comment on column BDC_SL_HSXX_MX.jmse
  is '减免税额';
comment on column BDC_SL_HSXX_MX.sjnse
  is '实际纳税额';
comment on column BDC_SL_HSXX_MX.sxh
  is '顺序号';


alter table BDC_SL_HSXX_MX
  add constraint BDC_SL_HSXX_MX primary key (HSXXMXID)
  using index
  tablespace BDCSL
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

--changeset BDC_SL_HSXX_MX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX_MX add(NSRMC VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_HSXX_MX.NSRMC IS '纳税人名称';
ALTER TABLE BDC_SL_HSXX_MX add(ZSXM VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_HSXX_MX.ZSXM IS '征税项目';
ALTER TABLE BDC_SL_HSXX_MX add(JSYJ NUMBER (12,4));
COMMENT ON COLUMN BDC_SL_HSXX_MX.JSYJ IS '计税依据';
ALTER TABLE BDC_SL_HSXX_MX add(SL VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_HSXX_MX.SL IS '税率';
ALTER TABLE BDC_SL_HSXX_MX add(CQBCZSJMSK NUMBER (12,4));
COMMENT ON COLUMN BDC_SL_HSXX_MX.CQBCZSJMSK IS '拆迁补偿折算减免税款';
ALTER TABLE BDC_SL_HSXX_MX add(ZZSXGMNSRJZBL VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_HSXX_MX.ZZSXGMNSRJZBL IS '增值税小规模纳税人减征比例';
ALTER TABLE BDC_SL_HSXX_MX add(ZZSXGMNSRJZE NUMBER (12,4));
COMMENT ON COLUMN BDC_SL_HSXX_MX.ZZSXGMNSRJZE IS '增值税小规模纳税人减征额';

--changeset BDC_SL_HSXX_MX:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX_MX add(KSSL NUMBER (12,2));
COMMENT ON COLUMN BDC_SL_HSXX_MX.KSSL IS '课税数量';
ALTER TABLE BDC_SL_HSXX_MX add(SKSSSQQSSJ DATE);
COMMENT ON COLUMN BDC_SL_HSXX_MX.SKSSSQQSSJ IS '税款所属时期起始时间';
ALTER TABLE BDC_SL_HSXX_MX add(SKSSSQJSSJ DATE );
COMMENT ON COLUMN BDC_SL_HSXX_MX.SKSSSQJSSJ IS '税款所属时期结束时间';

--changeset BDC_SL_HSXX_MX:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX_MX add(YSX NUMBER (10,2));
COMMENT ON COLUMN BDC_SL_HSXX_MX.YSX IS '应税项';
ALTER TABLE BDC_SL_HSXX_MX add(JCX NUMBER (10,2));
COMMENT ON COLUMN BDC_SL_HSXX_MX.JCX IS '减除项';
ALTER TABLE BDC_SL_HSXX_MX add(YBTSE NUMBER (10,2));
COMMENT ON COLUMN BDC_SL_HSXX_MX.YBTSE IS '本期应补（退）税额';
ALTER TABLE BDC_SL_HSXX_MX add(NSRSBH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_HSXX_MX.NSRSBH IS '纳税人识别号';
--changeset BDC_SL_HSXX_MX:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX_MX add(FJID VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_HSXX_MX.FJID IS '收件Id';
ALTER TABLE BDC_SL_HSXX_MX add(FJLX VARCHAR2(4));
COMMENT ON COLUMN BDC_SL_HSXX_MX.FJLX IS '附件类型';

--changeset BDC_SL_HSXX_MX:6 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_HSXX_MX add FWUUID VARCHAR2(100);
comment on column BDC_SL_HSXX_MX.FWUUID is '房屋信息唯一识别码';


