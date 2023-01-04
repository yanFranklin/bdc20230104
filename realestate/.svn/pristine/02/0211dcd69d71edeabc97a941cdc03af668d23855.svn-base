--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SFXX:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_SFXX
(
  sfxxid    VARCHAR2(32) not null,
  jbxxid    VARCHAR2(32),
  sfsj      DATE,
  jedw      NUMBER,
  hj        NUMBER(12,4),
  bz        VARCHAR2(1000),
  sfdcsr    VARCHAR2(50),
  sfdfsr    VARCHAR2(50),
  hsje      NUMBER(12,4),
  sfdwmc    VARCHAR2(500),
  jfrxm     VARCHAR2(100),
  sfrxm     VARCHAR2(100),
  sfrzh     VARCHAR2(100),
  sfrkhyh   VARCHAR2(100),
  sfdwdm    VARCHAR2(100),
  sfzt      NUMBER,
  sfztczrxm VARCHAR2(100),
  sfztczsj  DATE,
  fph       VARCHAR2(100),
  cztxmbh   VARCHAR2(100),
  fff       NUMBER
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
comment on table BDC_SL_SFXX
  is '不动产受理收费信息';
comment on column BDC_SL_SFXX.sfxxid
  is '收费信息id';
comment on column BDC_SL_SFXX.jbxxid
  is '基本信息ID';
comment on column BDC_SL_SFXX.sfsj
  is '收费时间';
comment on column BDC_SL_SFXX.jedw
  is '金额单位';
comment on column BDC_SL_SFXX.hj
  is '合计单位：元';
comment on column BDC_SL_SFXX.bz
  is '备注';
comment on column BDC_SL_SFXX.sfdcsr
  is '收费单初审人';
comment on column BDC_SL_SFXX.sfdfsr
  is '收费单复审人';
comment on column BDC_SL_SFXX.hsje
  is '核收金额(单位：元)';
comment on column BDC_SL_SFXX.sfdwmc
  is '收费单位名称(收费机构)';
comment on column BDC_SL_SFXX.jfrxm
  is '缴费人姓名';
comment on column BDC_SL_SFXX.sfrxm
  is '收费人姓名';
comment on column BDC_SL_SFXX.sfrzh
  is '收费人账号';
comment on column BDC_SL_SFXX.sfrkhyh
  is '收费人开户银行';
comment on column BDC_SL_SFXX.sfdwdm
  is '收费单位代码';
comment on column BDC_SL_SFXX.sfzt
  is '收费状态';
comment on column BDC_SL_SFXX.sfztczrxm
  is '收费状态操作人姓名';
comment on column BDC_SL_SFXX.sfztczsj
  is '收费状态操作时间';
comment on column BDC_SL_SFXX.fph
  is '发票号';
comment on column BDC_SL_SFXX.cztxmbh
  is '财政条形码编号';
comment on column BDC_SL_SFXX.fff
  is '付费方';
alter table BDC_SL_SFXX
  add constraint PK_BDC_SL_SFXX primary key (SFXXID)
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

--changeset BDC_SL_SFXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(GZLSLID VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_SFXX.GZLSLID IS '工作流实例ID';

--changeset BDC_SL_SFXX:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(XMID VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_SFXX.XMID IS '项目ID';

--changeset BDC_SL_SFXX:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(QLRLB VARCHAR2(2));
COMMENT ON COLUMN BDC_SL_SFXX.QLRLB IS '权利人类别';

--changeset BDC_SL_SFXX:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(SFRKHYHWDDM VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_SFXX.SFRKHYHWDDM IS '收费人开户银行网点代码';
ALTER TABLE BDC_SL_SFXX ADD(KPFS VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_SFXX.KPFS IS '开票方式';
ALTER TABLE BDC_SL_SFXX ADD(JKFS VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_SFXX.JKFS IS '缴款方式';

--changeset BDC_SL_SFXX:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JFSBM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_SFXX.JFSBM IS '缴费书编码';

--changeset BDC_SL_SFXX:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX modify  JBXXID VARCHAR2(32) null;

--changeset BDC_SL_SFXX:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JFSEWMURL VARCHAR2(1000));
COMMENT ON COLUMN BDC_SL_SFXX.JFSEWMURL IS '缴费书二维码URL';

--changeset BDC_SL_SFXX:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(KPRXM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_SFXX.KPRXM IS '开票人姓名';

--changeset BDC_SL_SFXX:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(YWR VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFXX.YWR IS '义务人';

--changeset BDC_SL_SFXX:11 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(BCZT NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.BCZT IS '是否点击保存按钮';

--changeset BDC_SL_SFXX:12 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(YHJKRKZT NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.YHJKRKZT IS '银行缴库入库状态（是否字典表，是表示状态为成功，否为失败）';

--changeset BDC_SL_SFXX:13 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(TKDH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFXX.TKDH IS '退款单号';

--changeset BDC_SL_SFXX:14 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JYPZH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFXX.JYPZH IS '交易凭证号';

--changeset BDC_SL_SFXX:15 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(ZDH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFXX.ZDH IS '终端号';
ALTER TABLE BDC_SL_SFXX ADD(CKH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFXX.CKH IS '参考号';
ALTER TABLE BDC_SL_SFXX ADD(SHDM VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFXX.SHDM IS '商户代码';
--changeset BDC_SL_SFXX:16 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(SFDB NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFDB IS '是否低保';
ALTER TABLE BDC_SL_SFXX ADD(SFXGZJY NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFXGZJY IS '是否下岗再就业';
ALTER TABLE BDC_SL_SFXX ADD(SFSDJF NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFSDJF IS '是否收登记费';
ALTER TABLE BDC_SL_SFXX ADD(SFSCQZGBF NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFSCQZGBF IS '是否收产权证工本费';
ALTER TABLE BDC_SL_SFXX ADD(SFSGYZGBF NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFSGYZGBF IS '是否是共有证工本费';

--changeset BDC_SL_SFXX:17 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(ZPH Varchar2(50));
COMMENT ON COLUMN BDC_SL_SFXX.ZPH IS '支票号';

--changeset BDC_SL_SFXX:18 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(SFZF NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFZF IS '是否作废（1：作废）';
ALTER TABLE BDC_SL_SFXX ADD(TFYY VARCHAR2(1000));
COMMENT ON COLUMN BDC_SL_SFXX.TFYY IS '退付原因';
ALTER TABLE BDC_SL_SFXX ADD(SFKP NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFKP IS '是否开票';
ALTER TABLE BDC_SL_SFXX ADD(TFRXM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_SFXX.TFRXM IS '退付人姓名';

--changeset BDC_SL_SFXX:19 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(XWLX Varchar2(200));
COMMENT ON COLUMN BDC_SL_SFXX.XWLX IS '小微类型';

--changeset BDC_SL_SFXX:20 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(SFYJ NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFYJ IS '是否月结';

--changeset BDC_SL_SFXX:21 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JKRKSJ DATE );
COMMENT ON COLUMN BDC_SL_SFXX.JKRKSJ IS '缴库入库时间';

--changeset BDC_SL_SFXX:22 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(SFXSJF NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFXSJF IS '是否线上缴费';

--changeset BDC_SL_SFXX:23 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(SLSJ DATE );
COMMENT ON COLUMN BDC_SL_SFXX.SLSJ IS '受理时间';

--changeset BDC_SL_SFXX:24 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(FSSRFPH VARCHAR (50));
COMMENT ON COLUMN BDC_SL_SFXX.FSSRFPH IS '非税收入发票号';
ALTER TABLE BDC_SL_SFXX ADD(JSPZFPH VARCHAR (50));
COMMENT ON COLUMN BDC_SL_SFXX.JSPZFPH IS '结算凭证发票号';
ALTER TABLE BDC_SL_SFXX ADD(YSNXMLB NUMBER(2));

--changeset BDC_SL_SFXX:25 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(fkrkhyh VARCHAR2 (100));
COMMENT ON COLUMN BDC_SL_SFXX.fkrkhyh IS '付款人开户银行';
ALTER TABLE BDC_SL_SFXX ADD(fkrzh VARCHAR2 (100));
COMMENT ON COLUMN BDC_SL_SFXX.fkrzh IS '付款人账号';

--changeset BDC_SL_SFXX:26 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX MODIFY JFRXM VARCHAR2(2000);

--changeset BDC_SL_SFXX:27 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JKRQ DATE);
COMMENT ON COLUMN BDC_SL_SFXX.JKRQ IS '缴款日期';
ALTER TABLE BDC_SL_SFXX ADD(JKQD VARCHAR (50));
COMMENT ON COLUMN BDC_SL_SFXX.JKQD IS '缴款渠道';

--changeset BDC_SL_SFXX:28 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(DBSJ DATE);
COMMENT ON COLUMN BDC_SL_SFXX.DBSJ IS '登簿时间';

--changeset BDC_SL_SFXX:29 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(HQFPHSJ DATE);
COMMENT ON COLUMN BDC_SL_SFXX.HQFPHSJ IS '获取发票号时间';

--changeset BDC_SL_SFXX:30 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JKMSJ DATE);
COMMENT ON COLUMN BDC_SL_SFXX.JKMSJ IS '缴款码时间';

--changeset BDC_SL_SFXX:31 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JMYY VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_SFXX.JMYY IS '减免原因';

--changeset BDC_SL_SFXX:32 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JMSY VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_SFXX.JMSY IS '减免事由（常州不同地区使用）';

--changeset BDC_SL_SFXX:33 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(HYZFURL VARCHAR2(500));
COMMENT ON COLUMN BDC_SL_SFXX.HYZFURL IS '合一支付url';
ALTER TABLE BDC_SL_SFXX ADD(HYZFDDID VARCHAR2(500));
COMMENT ON COLUMN BDC_SL_SFXX.HYZFDDID IS '合一支付订单id';

--changeset BDC_SL_SFXX:34 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(JFRDH VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_SFXX.JFRDH IS '缴费人电话';

--changeset BDC_SL_SFXX:35 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(XWQY NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.XWQY IS '是否小微企业';

--changeset BDC_SL_SFXX:36 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX ADD(SFSC NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFSC IS '是否删除';
alter table BDC_SL_SFXX modify sfsc default 0;
ALTER TABLE BDC_SL_SFXX ADD(SLBH VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_SFXX.SLBH IS '受理编号';
--changeset BDC_SL_SFXX:37 failOnError:false runOnChange:true runAlways:false
create index INDEX_JFSBM on BDC_SL_SFXX (jfsbm);

--changeset BDC_SL_SFXX:38 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_SFXX
    add xjze Number(16, 2);
comment on column BDC_SL_SFXX.xjze is '小计总额';

--changeset BDC_SL_SFXX:39 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX
    ADD(SFJM NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFJM IS '是否减免';

--changeset BDC_SL_SFXX:40 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX
    ADD(SFYCZF NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXX.SFYCZF IS '是否一次支付';

--changeset BDC_SL_SFXX:41 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXX
    ADD(HYZFJFTJ NUMBER(2));
COMMENT ON COLUMN BDC_SL_SFXX.HYZFJFTJ IS '合一支付缴费途径1.商业银行 2. 合一支付';
ALTER TABLE BDC_SL_SFXX
    ADD(HYZFJFLX NUMBER(2));
COMMENT ON COLUMN BDC_SL_SFXX.HYZFJFLX IS '合一支付缴费类型 1.登记费2.税费统缴';