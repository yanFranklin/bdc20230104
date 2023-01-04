--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SQR:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_SQR
(
  sqrid VARCHAR2(32) not null,
  xmid  VARCHAR2(32) not null,
  sqrmc VARCHAR2(200),
  zjzl  NUMBER,
  zjh   VARCHAR2(100),
  txdz  VARCHAR2(200),
  yb    VARCHAR2(100),
  xb    NUMBER,
  frmc  VARCHAR2(100),
  frdh  VARCHAR2(100),
  dlrmc VARCHAR2(100),
  dlrdh VARCHAR2(100),
  dljg  VARCHAR2(100),
  sqrlx NUMBER,
  sqrlb VARCHAR2(2),
  qlbl  NUMBER(10,4),
  qlbl2 VARCHAR2(50),
  gyfs  NUMBER,
  gyqk  VARCHAR2(1000),
  dh    VARCHAR2(100),
  sshy  VARCHAR2(100),
  bz    VARCHAR2(2000),
  sfczr NUMBER,
  sxh   NUMBER
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
comment on table BDC_SL_SQR
  is '不动产受理申请人';
comment on column BDC_SL_SQR.sqrid
  is '申请人人ID
';
comment on column BDC_SL_SQR.xmid
  is '项目ID';
comment on column BDC_SL_SQR.sqrmc
  is '申请人名称';
comment on column BDC_SL_SQR.zjzl
  is '证件种类';
comment on column BDC_SL_SQR.zjh
  is '证件号';
comment on column BDC_SL_SQR.txdz
  is '通讯地址';
comment on column BDC_SL_SQR.yb
  is '邮编';
comment on column BDC_SL_SQR.xb
  is '性别';
comment on column BDC_SL_SQR.frmc
  is '法人名称';
comment on column BDC_SL_SQR.frdh
  is '法人电话';
comment on column BDC_SL_SQR.dlrmc
  is '代理人名称';
comment on column BDC_SL_SQR.dlrdh
  is '代理人电话';
comment on column BDC_SL_SQR.dljg
  is '代理机构';
comment on column BDC_SL_SQR.sqrlx
  is '申请人类型';
comment on column BDC_SL_SQR.sqrlb
  is '申请人类别(1-权利人；2-义务人)';
comment on column BDC_SL_SQR.qlbl
  is '权利比例(数值型权利比例)';
comment on column BDC_SL_SQR.qlbl2
  is '权利比例2(分数或文字型权利比例)';
comment on column BDC_SL_SQR.gyfs
  is '共有方式';
comment on column BDC_SL_SQR.gyqk
  is '共有情况';
comment on column BDC_SL_SQR.dh
  is '电话';
comment on column BDC_SL_SQR.sshy
  is '所属行业';
comment on column BDC_SL_SQR.bz
  is '备注';
comment on column BDC_SL_SQR.sfczr
  is '是否持证人';
comment on column BDC_SL_SQR.sxh
  is '顺序号';
alter table BDC_SL_SQR
  add constraint PK_BDC_SL_SQR primary key (SQRID)
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

--changeset BDC_SL_SQR:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR add(HYZK VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_SQR.HYZK IS '婚姻状况';
ALTER TABLE BDC_SL_SQR add(SFBDHJ NUMBER(1));
COMMENT ON COLUMN BDC_SL_SQR.SFBDHJ IS '是否本地户籍';
ALTER TABLE BDC_SL_SQR add(FWTC VARCHAR2(1));
COMMENT ON COLUMN BDC_SL_SQR.FWTC IS '房屋套次';
ALTER TABLE BDC_SL_SQR add(GYRSFFQ NUMBER(1));
COMMENT ON COLUMN BDC_SL_SQR.GYRSFFQ IS '共有人是否夫妻关系';
ALTER TABLE BDC_SL_SQR add(SFJM NUMBER(1));
COMMENT ON COLUMN BDC_SL_SQR.SFJM IS '是否税费减免';
ALTER TABLE BDC_SL_SQR add(SFZXQS NUMBER(1));
COMMENT ON COLUMN BDC_SL_SQR.SFZXQS IS '是否直系亲属';
ALTER TABLE BDC_SL_SQR add(JTMWWYZZ NUMBER(1));
COMMENT ON COLUMN BDC_SL_SQR.JTMWWYZZ IS '家庭满五唯一住宅';

--changeset BDC_SL_SQR:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR add(SBFWTC VARCHAR2(1));
COMMENT ON COLUMN BDC_SL_SQR.SBFWTC IS '申报房屋套次';
ALTER TABLE BDC_SL_SQR DROP COLUMN QLBL2;
ALTER TABLE BDC_SL_SQR modify QLBL VARCHAR2(50);

--changeset BDC_SL_SQR:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR add(DLRZJZL NUMBER);
COMMENT ON COLUMN BDC_SL_SQR.DLRZJZL IS '代理人证件种类';
ALTER TABLE BDC_SL_SQR add(DLRZJH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SQR.DLRZJH IS '代理人证件号';
ALTER TABLE BDC_SL_SQR add(FRZJZL NUMBER);
COMMENT ON COLUMN BDC_SL_SQR.FRZJZL IS '法人证件种类';
ALTER TABLE BDC_SL_SQR add(FRZJH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SQR.FRZJH IS '法人证件号';

--changeset BDC_SL_SQR:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR modify FWTC VARCHAR2(10);

--changeset BDC_SL_SQR:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD GYRBJ VARCHAR2(100);
COMMENT ON COLUMN BDC_SL_SQR.GYRBJ IS '共有人标识';

--changeset BDC_SL_SQR:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD HYXXBDJG NUMBER(1);
COMMENT ON COLUMN BDC_SL_SQR.HYXXBDJG IS '婚姻信息比对结果';

--changeset BDC_SL_SQR:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD XGMNSR NUMBER(1);
COMMENT ON COLUMN BDC_SL_SQR.XGMNSR IS '小规模纳税人';

--changeset BDC_SL_SQR:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD JYFE VARCHAR2(50);
COMMENT ON COLUMN BDC_SL_SQR.JYFE IS '交易份额';

--changeset BDC_SL_SQR:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD SFGMMLN NUMBER(1);
COMMENT ON COLUMN BDC_SL_SQR.SFGMMLN IS '是否购买满两年';

--changeset BDC_SL_SQR:11 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD fwtcbdjg NUMBER(1);
COMMENT ON COLUMN BDC_SL_SQR.fwtcbdjg IS '房屋套次比对结果';

--changeset BDC_SL_SQR:12 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD qlrly NUMBER(2);
COMMENT ON COLUMN BDC_SL_SQR.qlrly IS '权利人来源';

--changeset BDC_SL_SQR:13 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD CYM VARCHAR2(100);
COMMENT ON COLUMN BDC_SL_SQR.CYM IS '曾用名';

--changeset BDC_SL_SQR:14 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD SFYJ NUMBER(1);
COMMENT ON COLUMN BDC_SL_SQR.SFYJ IS '是否月结';

--changeset BDC_SL_SQR:15 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD SFLZR NUMBER(1);
COMMENT ON COLUMN BDC_SL_SQR.SFLZR IS '是否领证人';

--changeset BDC_SL_SQR:16 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD SFZCQR NUMBER(1);
COMMENT ON COLUMN BDC_SL_SQR.SFZCQR IS '是否主产权人';

--changeset BDC_SL_SQR:17 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SQR ADD ZRFCSFGX NUMBER(1);
COMMENT ON COLUMN BDC_SL_SQR.ZRFCSFGX IS '转让方承受方关系';
