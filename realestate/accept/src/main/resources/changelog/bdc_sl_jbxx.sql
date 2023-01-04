--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_JBXX:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_JBXX
(
  jbxxid   VARCHAR2(32) not null,
  slbh     VARCHAR2(50) not null,
  gzlslid  VARCHAR2(32),
  gzldyid  VARCHAR2(50),
  qxdm     VARCHAR2(6),
  djjg     VARCHAR2(200),
  slsj     DATE,
  slrid    VARCHAR2(32),
  slr      VARCHAR2(50),
  sqrxm    VARCHAR2(100),
  sqrdh    VARCHAR2(20),
  sfyjzs   NUMBER(1),
  sfsbclkd NUMBER(1),
  kddz     VARCHAR2(200),
  cnqx     NUMBER,
  xmly     NUMBER,
  ssxz     VARCHAR2(10),
  djyy     VARCHAR2(500),
  bz       VARCHAR2(4000),
  lcmc     VARCHAR2(100),
  sqzsbs   NUMBER(1),
  zl       VARCHAR2(100),
  djbmdm VARCHAR2(50),
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
comment on table BDC_SL_JBXX
  is '不动产受理基本信息';
comment on column BDC_SL_JBXX.jbxxid
  is '基本信息ID';
comment on column BDC_SL_JBXX.slbh
  is '受理编号
';
comment on column BDC_SL_JBXX.gzlslid
  is '工作流实例ID
';
comment on column BDC_SL_JBXX.gzldyid
  is '工作流定义ID
';
comment on column BDC_SL_JBXX.qxdm
  is '区县代码
';
comment on column BDC_SL_JBXX.djjg
  is '登记机构
';
comment on column BDC_SL_JBXX.slsj
  is '受理时间
';
comment on column BDC_SL_JBXX.slrid
  is '受理人ID
';
comment on column BDC_SL_JBXX.slr
  is '受理人
';
comment on column BDC_SL_JBXX.sqrxm
  is '申请人姓名';
comment on column BDC_SL_JBXX.sqrdh
  is '申请人电话';
comment on column BDC_SL_JBXX.sfyjzs
  is '是否邮寄证书
';
comment on column BDC_SL_JBXX.sfsbclkd
  is '是否申报材料快递
';
comment on column BDC_SL_JBXX.kddz
  is '快递地址
';
comment on column BDC_SL_JBXX.cnqx
  is '承诺期限
';
comment on column BDC_SL_JBXX.xmly
  is '项目来源
';
comment on column BDC_SL_JBXX.ssxz
  is '所属乡镇
';
comment on column BDC_SL_JBXX.djyy
  is '登记原因
';
comment on column BDC_SL_JBXX.bz
  is '备注
';
comment on column BDC_SL_JBXX.lcmc
  is '流程名称';
comment on column BDC_SL_JBXX.sqzsbs
  is '是否分别持证';
comment on column BDC_SL_JBXX.zl
  is '坐落';
comment on column BDC_SL_JBXX.djbmdm
is '登记部门代码
 ';
alter table BDC_SL_JBXX
  add constraint PK_BDC_SL_JBXX primary key (JBXXID)
  using index
  tablespace BDCSL
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 6M
    next 1M
    minextents 1
    maxextents unlimited
  );

--changeset BDC_SL_JBXX:2 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_JBXX add(AJZT NUMBER(1) default '1');
COMMENT ON COLUMN BDC_SL_JBXX.AJZT IS '案件状态(非登记流程)';

--changeset BDC_SL_JBXX:3 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_JBXX add(YWSLZT NUMBER(1));
COMMENT ON COLUMN BDC_SL_JBXX.YWSLZT IS '业务受理状态,当前为一窗流程时对应的登记流程案件状态';

--changeset BDC_SL_JBXX:4 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_JBXX add(DJBMDM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_JBXX.DJBMDM IS '登记部门代码';

--changeset BDC_SL_JBXX:5 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_JBXX add(FWUUID VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_JBXX.FWUUID IS '税务唯一标识';

--changeset BDC_SL_JBXX:6 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_JBXX add(sqcltjfs VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_JBXX.sqcltjfs IS '申请材料提交方式';

--changeset BDC_SL_JBXX:7 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_JBXX add(JYUUID VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_JBXX.JYUUID IS '交易唯一标识';

--changeset BDC_SL_JBXX:8 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_JBXX add(ZDJBDB NUMBER(1));
COMMENT ON COLUMN BDC_SL_JBXX.ZDJBDB IS '只登记不登簿';