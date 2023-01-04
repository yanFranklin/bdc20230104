--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_HSXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_HSXX
(
  hsxxid  VARCHAR2(32) not null,
  xmid    VARCHAR2(32) not null,
  hdjsjg  NUMBER(12,4),
  wszt    NUMBER,
  ynsehj  NUMBER(12,4),
  jmsehj  NUMBER(12,4),
  sjyzhj  NUMBER(12,4),
  sqrlb   VARCHAR2(2)
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
comment on table BDC_SL_HSXX
  is '不动产受理核税信息';
comment on column BDC_SL_HSXX.hsxxid
  is '核税信息ID';
comment on column BDC_SL_HSXX.xmid
  is '项目ID';
comment on column BDC_SL_HSXX.hdjsjg
  is '核定计税价格';
comment on column BDC_SL_HSXX.wszt
  is '完税状态';
comment on column BDC_SL_HSXX.ynsehj
  is '应纳税额合计';
comment on column BDC_SL_HSXX.jmsehj
  is '减免税额合计';
comment on column BDC_SL_HSXX.sjyzhj
  is '实际应征合计';
comment on column BDC_SL_HSXX.sqrlb
  is '申请人类别';

alter table BDC_SL_HSXX
  add constraint BDC_SL_HSXX primary key (HSXXID)
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

--changeset BDC_SL_HSXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(SWJGDM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_HSXX.SWJGDM IS '税务机关代码';
ALTER TABLE BDC_SL_HSXX add(NSRSBH VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_HSXX.NSRSBH IS '纳税人识别号';
ALTER TABLE BDC_SL_HSXX add(SPHM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_HSXX.SPHM IS '税票号码';

--changeset BDC_SL_HSXX:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(FPHM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_HSXX.FPHM IS '发票号码';
ALTER TABLE BDC_SL_HSXX add(FPDM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_HSXX.FPDM IS '发票代码';

--changeset BDC_SL_HSXX:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(YHJKRKZT NUMBER(1));
COMMENT ON COLUMN BDC_SL_HSXX.YHJKRKZT IS '银行缴库入库状态';

--changeset BDC_SL_HSXX:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(YTSSWZT NUMBER(1));
COMMENT ON COLUMN BDC_SL_HSXX.YTSSWZT IS '已推送税务状态';

--changeset BDC_SL_HSXX:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(JYPZH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_HSXX.JYPZH IS '交易凭证号';

--changeset BDC_SL_HSXX:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(JFZT NUMBER(1));
COMMENT ON COLUMN BDC_SL_HSXX.JFZT IS '支付状态';

--changeset BDC_SL_HSXX:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(HSZT NUMBER(1));
COMMENT ON COLUMN BDC_SL_HSXX.HSZT IS '核税状态';
ALTER TABLE BDC_SL_HSXX add(THYY VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_HSXX.THYY IS '退回原因';

--changeset BDC_SL_HSXX:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(JKRKSJ DATE);
COMMENT ON COLUMN BDC_SL_HSXX.JKRKSJ IS '缴库入库时间';

--changeset BDC_SL_HSXX:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(JSSKSJ DATE);
COMMENT ON COLUMN BDC_SL_HSXX.JSSKSJ IS '接受税款时间';

--changeset BDC_SL_HSXX:11 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(NSRMC VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_HSXX.NSRMC IS '纳税人名称';
ALTER TABLE BDC_SL_HSXX add(BZ VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_HSXX.BZ IS '备注';

--changeset BDC_SL_HSXX:12 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX MODIFY BZ VARCHAR2(4000);

--changeset BDC_SL_HSXX:13 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX MODIFY NSRSBH VARCHAR2(200);

--changeset BDC_SL_HSXX:14 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add (QYJYZSBZ NUMBER(1));
COMMENT ON COLUMN BDC_SL_HSXX.QYJYZSBZ IS '企业简易征收标记';

--changeset BDC_SL_HSXX:15 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_YJKXX
(
  jkxxid  VARCHAR2(32) not null,
  xmid    VARCHAR2(32) not null,
  NSRSBH   VARCHAR2(32),
  NSRMC  VARCHAR2(300),
  DJXH  VARCHAR2(32),
  ZGSWSKFJDM  VARCHAR2(11),
  PZXH   VARCHAR2(38),
  YZPZMXXH VARCHAR2(20),
  YZPZZLDM VARCHAR2(20),
  SKSSQQ VARCHAR2(20),
  SKSSQZ VARCHAR2(20),
  YBTSE Number(16,2),
  JKZT VARCHAR2(1),
  DZSPHM VARCHAR2(38)
);
COMMENT ON TABLE BDC_SL_YJKXX IS '已缴款信息';
COMMENT ON COLUMN BDC_SL_YJKXX.jkxxid IS '缴款信息id';
COMMENT ON COLUMN BDC_SL_YJKXX.xmid IS '项目id';
COMMENT ON COLUMN BDC_SL_YJKXX.NSRSBH IS '纳税人识别号';
COMMENT ON COLUMN BDC_SL_YJKXX.NSRMC IS '纳税人名称';
COMMENT ON COLUMN BDC_SL_YJKXX.DJXH IS '登记序号';
COMMENT ON COLUMN BDC_SL_YJKXX.ZGSWSKFJDM IS '主管税务科所分局代码';
COMMENT ON COLUMN BDC_SL_YJKXX.PZXH IS '凭证序号';
COMMENT ON COLUMN BDC_SL_YJKXX.YZPZMXXH IS '应征凭证序号明细序号';
COMMENT ON COLUMN BDC_SL_YJKXX.YZPZZLDM IS '应征凭证种类代码';
COMMENT ON COLUMN BDC_SL_YJKXX.SKSSQQ IS '税款所属期起';
COMMENT ON COLUMN BDC_SL_YJKXX.SKSSQZ IS '税款所属期止';
COMMENT ON COLUMN BDC_SL_YJKXX.YBTSE IS '应补（退）税额';
COMMENT ON COLUMN BDC_SL_YJKXX.JKZT IS '缴款状态';
COMMENT
ON COLUMN BDC_SL_YJKXX.DZSPHM IS '电子税票号码';
--changeset BDC_SL_HSXX:16 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX MODIFY HSZT NUMBER (2);
--changeset BDC_SL_HSXX:17 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_HSXX add(SJBH VARCHAR2(32));
COMMENT
ON COLUMN BDC_SL_HSXX.SJBH IS '收件编号';
ALTER TABLE BDC_SL_HSXX add(HTBH VARCHAR2(100));
COMMENT
ON COLUMN BDC_SL_HSXX.HTBH IS '合同编号';
ALTER TABLE BDC_SL_HSXX add(WSFS VARCHAR2(2));
COMMENT
ON COLUMN BDC_SL_HSXX.WSFS IS '完税方式';
--changeset BDC_SL_HSXX:18 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_YJKXX add(qlrlb VARCHAR2(2));
COMMENT
ON COLUMN BDC_SL_YJKXX.qlrlb IS '权利人类别';

--changeset BDC_SL_HSXX:19 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_HSXX add FWUUID VARCHAR2(100);
comment on column BDC_SL_HSXX.FWUUID is '房屋信息唯一识别码';

--changeset BDC_SL_HSXX:20 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_YJKXX add xjze Number(16,2);
comment on column BDC_SL_HSXX.xjze is '小计总额';

--changeset BDC_SL_HSXX:21 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_YJKXX add jkyh VARCHAR2(100);
comment on column BDC_SL_HSXX.jkyh is '缴款银行';


--changeset BDC_SL_HSXX:22 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_HSXX add hsxxlx VARCHAR2(1);
comment on column BDC_SL_HSXX.hsxxlx is '核税信息类型';

--changeset BDC_SL_HSXX:23 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_HSXX add jsewm VARCHAR2(50);
comment on column BDC_SL_HSXX.jsewm is '缴税二维码';

--changeset BDC_SL_HSXX:24 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_HSXX add hyzfxjze Number(16, 4);
comment on column BDC_SL_HSXX.hyzfxjze is '合一支付小计总额';
alter table BDC_SL_HSXX add hyzfjfsbm VARCHAR2(50);
comment on column BDC_SL_HSXX.hyzfjfsbm is '合一支付缴费书编码';
alter table BDC_SL_HSXX add hyzfurl VARCHAR2(1000);
comment on column BDC_SL_HSXX.hyzfurl is '合一支付url';