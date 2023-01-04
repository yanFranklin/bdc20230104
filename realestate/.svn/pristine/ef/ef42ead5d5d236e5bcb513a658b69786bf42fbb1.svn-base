--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_XM:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_XM
(
  xmid       VARCHAR2(32) not null,
  jbxxid     VARCHAR2(32) not null,
  bdcdyh     VARCHAR2(28) not null,
  qjid       VARCHAR2(50),
  bdclx      NUMBER,
  djxl       VARCHAR2(10),
  sfxxid     VARCHAR2(32),
  ybdcqz     VARCHAR2(100),
  jejg       NUMBER(15,6),
  dyje       NUMBER(15,6),
  dyfs       NUMBER,
  zwlxqssj   DATE,
  zwlxjssj   DATE,
  zjjzwzl    VARCHAR2(100),
  zjjzwdyfw  VARCHAR2(200),
  zgzqqdss   VARCHAR2(1000),
  dbfw       VARCHAR2(100),
  dkfs       VARCHAR2(100),
  bdbzzqse   NUMBER(15,6),
  fwpgjg     NUMBER(15,4),
  tdpgjg     NUMBER(15,4),
  cfjg       VARCHAR2(100),
  cflx       NUMBER,
  cfwh       VARCHAR2(200),
  cffw       VARCHAR2(200),
  cfqssj     DATE,
  cfjssj     DATE,
  zxsqr      VARCHAR2(200),
  bzxr       VARCHAR2(200),
  cfyy       VARCHAR2(500),
  lhcfqx     VARCHAR2(100),
  fysdr      VARCHAR2(100),
  ygdjzl     NUMBER,
  qdjg       NUMBER(15,4),
  gyqk       VARCHAR2(500),
  ygzwlxqssj DATE,
  ygzwlxjssj DATE,
  ygdyfs     NUMBER,
  yysx       VARCHAR2(200),
  syqmj      NUMBER(15,4),
  ldsyksqx   DATE,
  ldsyjsqx   DATE,
  ldsyqxz    NUMBER,
  sllmsyqr1  VARCHAR2(100),
  sllmsyqr2  VARCHAR2(100),
  zysz       VARCHAR2(100),
  zs         NUMBER(10),
  lz         NUMBER,
  qy         NUMBER,
  zlnd       NUMBER(4),
  xdm        VARCHAR2(100),
  lb         VARCHAR2(100),
  xb         VARCHAR2(100),
  qlrsjly    NUMBER,
  ywrsjly    NUMBER,
  sfscql     NUMBER,
  qlsjly     VARCHAR2(10),
  zszl       NUMBER,
  zsxh       NUMBER,
  zdzhyt     NUMBER,
  xdyzl      VARCHAR2(100),
  xdybdcdyh  VARCHAR2(28),
  zdzhmj     NUMBER(15,4),
  zl         VARCHAR2(500),
  qlr        VARCHAR2(100),
  sfzlcsh    NUMBER(1),
  bdcdyfwlx  NUMBER(1),
  czrid      VARCHAR2(100),
  czsj       DATE,
  sfzf       NUMBER(1),
  sfhyyzxql       NUMBER(1),
  sfhz       NUMBER(1)
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
comment on table BDC_SL_XM
  is '不动产受理项目';
comment on column BDC_SL_XM.xmid
  is '项目ID
';
comment on column BDC_SL_XM.jbxxid
  is '基本信息ID
';
comment on column BDC_SL_XM.bdcdyh
  is '不动产单元号
';
comment on column BDC_SL_XM.qjid
  is '权籍ID
';
comment on column BDC_SL_XM.bdclx
  is '不动产类型
';
comment on column BDC_SL_XM.djxl
  is '登记小类';
comment on column BDC_SL_XM.sfxxid
  is '收费信息id
';
comment on column BDC_SL_XM.ybdcqz
  is '原不动产权证
';
comment on column BDC_SL_XM.jejg
  is '交易价格
';
comment on column BDC_SL_XM.dyje
  is '抵押金额
';
comment on column BDC_SL_XM.dyfs
  is '抵押方式
';
comment on column BDC_SL_XM.zwlxqssj
  is '债务履行起始时间
';
comment on column BDC_SL_XM.zwlxjssj
  is '债务履行结束时间
';
comment on column BDC_SL_XM.zjjzwzl
  is '在建建筑物坐落
';
comment on column BDC_SL_XM.zjjzwdyfw
  is '在建建筑物抵押范围
';
comment on column BDC_SL_XM.zgzqqdss
  is '最高债权确定事实
';
comment on column BDC_SL_XM.dbfw
  is '担保范围
';
comment on column BDC_SL_XM.dkfs
  is '贷款方式
';
comment on column BDC_SL_XM.bdbzzqse
  is '被担保主债权数额
';
comment on column BDC_SL_XM.fwpgjg
  is '房屋评估价格
';
comment on column BDC_SL_XM.tdpgjg
  is '土地评估价格
';
comment on column BDC_SL_XM.cfjg
  is '查封机关
';
comment on column BDC_SL_XM.cflx
  is '查封类型
';
comment on column BDC_SL_XM.cfwh
  is '查封文号
';
comment on column BDC_SL_XM.cffw
  is '查封范围
';
comment on column BDC_SL_XM.cfqssj
  is '查封起始时间
';
comment on column BDC_SL_XM.cfjssj
  is '查封结束时间
';
comment on column BDC_SL_XM.zxsqr
  is '执行申请人
';
comment on column BDC_SL_XM.bzxr
  is '被执行人
';
comment on column BDC_SL_XM.cfyy
  is '查封原因
';
comment on column BDC_SL_XM.lhcfqx
  is '轮候查封期限
';
comment on column BDC_SL_XM.fysdr
  is '法院送达人
';
comment on column BDC_SL_XM.ygdjzl
  is '预告登记种类
';
comment on column BDC_SL_XM.qdjg
  is '取得价格/主债权数额
';
comment on column BDC_SL_XM.gyqk
  is '共有情况
';
comment on column BDC_SL_XM.ygzwlxqssj
  is '预告债务履行起始时间
';
comment on column BDC_SL_XM.ygzwlxjssj
  is '预告债务履行结束时间
';
comment on column BDC_SL_XM.ygdyfs
  is '预告抵押方式
';
comment on column BDC_SL_XM.yysx
  is '异议事项
';
comment on column BDC_SL_XM.syqmj
  is '使用权（承包方）面积
';
comment on column BDC_SL_XM.ldsyksqx
  is '林地使用（承包）开始期限
';
comment on column BDC_SL_XM.ldsyjsqx
  is '林地使用（承包）结束期限
';
comment on column BDC_SL_XM.ldsyqxz
  is '林地所有权性质
';
comment on column BDC_SL_XM.sllmsyqr1
  is '森林、林木所有权人
';
comment on column BDC_SL_XM.sllmsyqr2
  is '森林、林木使用权人
';
comment on column BDC_SL_XM.zysz
  is '主要树种
';
comment on column BDC_SL_XM.zs
  is '株数
';
comment on column BDC_SL_XM.lz
  is '林种
';
comment on column BDC_SL_XM.qy
  is '起源
';
comment on column BDC_SL_XM.zlnd
  is '造林年度
';
comment on column BDC_SL_XM.xdm
  is '小地名
';
comment on column BDC_SL_XM.lb
  is '林班
';
comment on column BDC_SL_XM.xb
  is '小班
';
comment on column BDC_SL_XM.qlrsjly
  is '权利人数据来源 1：权籍 2：上一手权利人 3：上一手义务人';
comment on column BDC_SL_XM.ywrsjly
  is '义务人数据来源 1：权籍 2：上一手权利人 3：上一手义务人';
comment on column BDC_SL_XM.sfscql
  is '是否生成权利 0：否  1：是';
comment on column BDC_SL_XM.qlsjly
  is '权利数据来源 1：权籍 2：上一手  可组合(1,2)*/';
comment on column BDC_SL_XM.zszl
  is '证书种类   1：证书  2：证明';
comment on column BDC_SL_XM.zsxh
  is '证书序号：用于组合发证 分组';
comment on column BDC_SL_XM.zdzhyt
  is '宗地宗海用途';
comment on column BDC_SL_XM.xdyzl
  is '需地役坐落';
comment on column BDC_SL_XM.xdybdcdyh
  is '需地役不动产单元号';
comment on column BDC_SL_XM.zdzhmj
  is '宗地宗海面积';
comment on column BDC_SL_XM.zl
  is '坐落';
comment on column BDC_SL_XM.qlr
  is '权利人';
comment on column BDC_SL_XM.sfzlcsh
  is '是否增量初始化 0：否  1：是';
comment on column BDC_SL_XM.bdcdyfwlx
  is '不动产单元房屋类型';
comment on column BDC_SL_XM.czrid
  is '操作人ID';
comment on column BDC_SL_XM.czsj
  is '操作时间';
comment on column BDC_SL_XM.sfzf
  is '是否主房  0：否  1：是';
comment on column BDC_SL_XM.sfhyyzxql
  is '是否还原原注销权利  0：否  1：是';
comment on column BDC_SL_XM.sfhz
  is '是否换证  0：否  1：是';
alter table BDC_SL_XM
  add constraint PK_BDC_SL_XM primary key (XMID)
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


--changeset BDC_SL_XM:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM ADD(SFHZ  NUMBER(1));
COMMENT ON COLUMN BDC_SL_XM.SFHZ IS '是否换证  0：否  1：是';
--changeset BDC_SL_XM:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM ADD(SFHYYZXQL  NUMBER(1));
COMMENT ON COLUMN BDC_SL_XM.SFHYYZXQL IS '是否还原原注销权利  0：否  1：是';
--changeset BDC_SL_XM:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(QLLX NUMBER(2));
COMMENT ON COLUMN BDC_SL_XM.QLLX IS '权利类型';
--changeset BDC_SL_XM:5 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XM modify BDCDYH null;
--changeset BDC_SL_XM:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM DROP COLUMN JEJG;
--changeset BDC_SL_XM:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(JDDM VARCHAR2(9));
COMMENT ON COLUMN BDC_SL_XM.JDDM IS '街道代码';

--changeset BDC_SL_XM:8 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XM modify YBDCQZ VARCHAR2(4000);

--changeset BDC_SL_XM:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM DROP COLUMN SFXXID;

--changeset BDC_SL_XM:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(DJYY VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_XM.DJYY IS '登记原因';
ALTER TABLE BDC_SL_XM DROP COLUMN JDDM;

--changeset BDC_SL_XM:11 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(SFFC NUMBER(1));
COMMENT ON COLUMN BDC_SL_XM.SFFC IS '是否房查(房屋信息查询)';

--changeset BDC_SL_XM:12 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(BZ VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_XM.BZ IS '备注';

--changeset BDC_SL_XM:13 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(SQFBCZ NUMBER(1));
COMMENT ON COLUMN BDC_SL_XM.SQFBCZ IS '申请分别持证';
ALTER TABLE BDC_SL_XM add(ZDZHYT2 VARCHAR2(5));
COMMENT ON COLUMN BDC_SL_XM.ZDZHYT2 IS '宗地宗海用途2';
ALTER TABLE BDC_SL_XM add(ZDZHYT3 VARCHAR2(5));
COMMENT ON COLUMN BDC_SL_XM.ZDZHYT3 IS '宗地宗海用途3';
ALTER TABLE BDC_SL_XM add(QLXZ NUMBER(3));
COMMENT ON COLUMN BDC_SL_XM.QLXZ IS '权利性质';

--changeset BDC_SL_XM:14 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(XMYWLX NUMBER(1) default 0);
COMMENT ON COLUMN BDC_SL_XM.XMYWLX IS '项目业务类型 0：登记 1:一窗受理 9：其他';

--changeset BDC_SL_XM:15 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(SPXTYWH VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_XM.SPXTYWH IS '审批系统业务号';

--changeset BDC_SL_XM:16 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(SFSCZS NUMBER(1));
COMMENT ON COLUMN BDC_SL_XM.SFSCZS IS '是否生成证书  0：否  1：是';

--changeset BDC_SL_XM:17 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM DROP COLUMN CFLX;
ALTER TABLE BDC_SL_XM DROP COLUMN CFJG;
ALTER TABLE BDC_SL_XM DROP COLUMN CFWH;
ALTER TABLE BDC_SL_XM DROP COLUMN CFFW;
ALTER TABLE BDC_SL_XM DROP COLUMN CFQSSJ;
ALTER TABLE BDC_SL_XM DROP COLUMN CFJSSJ;
ALTER TABLE BDC_SL_XM DROP COLUMN ZXSQR;
ALTER TABLE BDC_SL_XM DROP COLUMN BZXR;
ALTER TABLE BDC_SL_XM DROP COLUMN CFYY;
ALTER TABLE BDC_SL_XM DROP COLUMN LHCFQX;
ALTER TABLE BDC_SL_XM DROP COLUMN FYSDR;

--changeset BDC_SL_XM:18 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(TSSWSJ DATE);
COMMENT ON COLUMN BDC_SL_XM.TSSWSJ IS '推送税务时间';

--changeset BDC_SL_XM:19 failOnError:false runOnChange:true runAlways:false
CREATE INDEX BDC_SL_XM_JBXXID ON BDC_SL_XM(JBXXID);

--changeset BDC_SL_XM:20 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(YT VARCHAR2(5));
COMMENT ON COLUMN BDC_SL_XM.YT IS '用途';

--changeset BDC_SL_XM:21 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM MODIFY YT VARCHAR2(10);

--changeset BDC_SL_XM:22 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(QJGLDM VARCHAR2(6));
COMMENT ON COLUMN BDC_SL_XM.QJGLDM IS '权籍管理代码';

--changeset BDC_SL_XM:23 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM MODIFY XMYWLX NUMBER(2);

--changeset BDC_SL_XM:24 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM add(QJQLRGXID VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_XM.QJQLRGXID IS '权籍权利人关系ID-用于土地承包经营权，记录承包方与承包宗地关系ID';

--changeset BDC_SL_XM:25 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XM MODIFY QJQLRGXID VARCHAR2(50);

--changeset BDC_SL_XM:26 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XM rename column ZDZHYT to ZDZHYT_TEMP;
alter table BDC_SL_XM add ZDZHYT VARCHAR2(5);
update BDC_SL_XM set ZDZHYT= ZDZHYT_TEMP;
alter table BDC_SL_XM drop column ZDZHYT_TEMP;
COMMENT ON COLUMN BDC_SL_XM.ZDZHYT IS '宗地宗海用途';

--changeset BDC_SL_XM:27 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XM add DZWMJ NUMBER(15,2);
COMMENT ON COLUMN BDC_SL_XM.DZWMJ IS '定着物面积';

--changeset BDC_SL_XM:28 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XM add DAGSD VARCHAR2(20);
comment on column BDC_SL_XM.DAGSD is '档案归属地';

--changeset BDC_SL_XM:29 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XM add FWUUID VARCHAR2(100);
comment on column BDC_SL_XM.FWUUID is '房屋信息唯一识别码';

--changeset BDC_SL_XM:30 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_XM add DZWYTMC VARCHAR2(500);
comment on column BDC_SL_XM.DZWYTMC is '用途名称';
