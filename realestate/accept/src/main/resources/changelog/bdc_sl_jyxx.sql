--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_JYXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_JYXX
(
  jyxxid  VARCHAR2(32) not null,
  xmid    VARCHAR2(32) not null,
  htbh    VARCHAR2(100),
  htzt    NUMBER,
  htbah   VARCHAR2(100),
  dj       NUMBER(20,4),
  htdjsj  DATE,
  htbasj  DATE,
  qszyfs  VARCHAR2(2),
  qszydx   VARCHAR2(5),
  qszyyt   VARCHAR2(3),
  jylx     VARCHAR2(10),
  htmj     NUMBER(15,2),
  scjydjsj  DATE,
  jyje     NUMBER(20,4)
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
comment on table BDC_SL_JYXX
  is '不动产受理交易信息';
comment on column BDC_SL_JYXX.jyxxid
  is '交易信息ID';
comment on column BDC_SL_JYXX.xmid
  is '项目ID';
comment on column BDC_SL_JYXX.htbh
  is '合同编号';
comment on column BDC_SL_JYXX.htzt
  is '合同状态';
comment on column BDC_SL_JYXX.htbah
  is '合同备案号';
comment on column BDC_SL_JYXX.dj
  is '单价';
comment on column BDC_SL_JYXX.htdjsj
  is '合同登记时间';
comment on column BDC_SL_JYXX.htbasj
  is '合同备案时间';
comment on column BDC_SL_JYXX.qszyfs
  is '权属转移方式';
comment on column BDC_SL_JYXX.qszydx
  is '权属转移对象';
comment on column BDC_SL_JYXX.qszyyt
  is '权属转移用途';
comment on column BDC_SL_JYXX.jylx
  is '交易类型';
comment on column BDC_SL_JYXX.htmj
  is '合同面积';
comment on column BDC_SL_JYXX.scjydjsj
  is '上次交易登记时间';
comment on column BDC_SL_JYXX.jyje
  is '交易金额';

alter table BDC_SL_JYXX
  add constraint BDC_SL_JYXX primary key (JYXXID)
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

--changeset BDC_SL_JYXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(QZTZRQ DATE);
COMMENT ON COLUMN BDC_SL_JYXX.QZTZRQ IS '权证填制日期';

--changeset BDC_SL_JYXX:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(FKFS VARCHAR2(2));
COMMENT ON COLUMN BDC_SL_JYXX.FKFS IS '付款方式';
ALTER TABLE BDC_SL_JYXX add(YSXKZH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_JYXX.YSXKZH IS '预售许可证号';

--changeset BDC_SL_JYXX:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(GHXKZH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_JYXX.GHXKZH IS '规划许可证号';
ALTER TABLE BDC_SL_JYXX add(GHYSZMBH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_JYXX.GHYSZMBH IS '规划验收证明编号';
ALTER TABLE BDC_SL_JYXX add(JGYSBABH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_JYXX.JGYSBABH IS '竣工验收备案编号';
ALTER TABLE BDC_SL_JYXX add(JGYSBASJ DATE);
COMMENT ON COLUMN BDC_SL_JYXX.JGYSBASJ IS '竣工验收备案时间';
ALTER TABLE BDC_SL_JYXX add(GHYSSJ DATE);
COMMENT ON COLUMN BDC_SL_JYXX.GHYSSJ IS '规划验收日期';

--changeset BDC_SL_JYXX:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(SBJG NUMBER(20,4));
COMMENT ON COLUMN BDC_SL_JYXX.SBJG IS '申报价格';
ALTER TABLE BDC_SL_JYXX add(BHZZSJE NUMBER(20,4));
COMMENT ON COLUMN BDC_SL_JYXX.BHZZSJE IS '不含增值税金额';
ALTER TABLE BDC_SL_JYXX add(FCZFZSJ DATE);
COMMENT ON COLUMN BDC_SL_JYXX.FCZFZSJ IS '房产证发证时间';

--changeset BDC_SL_JYXX:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(MJHDWJH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_JYXX.MJHDWJH IS '面积核定文件号';

--changeset BDC_SL_JYXX:7 failOnError:false runOnChange:true runAlways:false
CREATE INDEX BDC_SL_JYXX_XMID ON BDC_SL_JYXX(XMID);

--changeset BDC_SL_JYXX:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(ZZSJE NUMBER(20,4));
COMMENT ON COLUMN BDC_SL_JYXX.ZZSJE IS '增值税金额';
ALTER TABLE BDC_SL_JYXX add(HZZSJE NUMBER(20,4));
COMMENT ON COLUMN BDC_SL_JYXX.HZZSJE IS '含增值税金额';

--changeset BDC_SL_JYXX:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX DROP COLUMN QZTZRQ;
ALTER TABLE BDC_SL_JYXX DROP COLUMN SBJG;

--changeset BDC_SL_JYXX:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(XMMC VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_JYXX.XMMC IS '项目名称';
ALTER TABLE BDC_SL_JYXX add(BZ VARCHAR2(4000));
COMMENT ON COLUMN BDC_SL_JYXX.BZ IS '备注';

--changeset BDC_SL_JYXX:11 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX DROP COLUMN QSZYDX;
ALTER TABLE BDC_SL_JYXX DROP COLUMN QSZYYT;
ALTER TABLE BDC_SL_JYXX DROP COLUMN QSZYFS;

--changeset BDC_SL_JYXX:12 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(ZZSJM NUMBER(1));
COMMENT ON COLUMN BDC_SL_JYXX.ZZSJM IS '增值税减免';

--changeset BDC_SL_JYXX:13 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(FPJYJG NUMBER(20,4));
COMMENT ON COLUMN BDC_SL_JYXX.FPJYJG IS '发票交易价格';

--changeset BDC_SL_JYXX:14 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX MODIFY JYJE NUMBER(22,6);
ALTER TABLE BDC_SL_JYXX MODIFY FPJYJG NUMBER(22,6);
ALTER TABLE BDC_SL_JYXX MODIFY DJ NUMBER(22,6);
ALTER TABLE BDC_SL_JYXX MODIFY SBJG NUMBER(22,6);
ALTER TABLE BDC_SL_JYXX MODIFY BHZZSJE NUMBER(22,6);
ALTER TABLE BDC_SL_JYXX MODIFY ZZSJE NUMBER(22,6);
ALTER TABLE BDC_SL_JYXX MODIFY HZZSJE NUMBER(22,6);

--changeset BDC_SL_JYXX:15 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(FPHM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_JYXX.FPHM IS '交易发票号码';
ALTER TABLE BDC_SL_JYXX add(FPDM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_JYXX.FPDM IS '交易发票代码';

--changeset BDC_SL_JYXX:16 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(YFPJE NUMBER(22,6));
COMMENT ON COLUMN BDC_SL_JYXX.YFPJE IS '原发票金额';
ALTER TABLE BDC_SL_JYXX add(YQSJE NUMBER(22,6));
COMMENT ON COLUMN BDC_SL_JYXX.YQSJE IS '原契税金额';
ALTER TABLE BDC_SL_JYXX add(PGJG NUMBER(22,6));
COMMENT ON COLUMN BDC_SL_JYXX.PGJG IS '评估价格';
ALTER TABLE BDC_SL_JYXX ADD FZZZZSJS NUMBER(1);
COMMENT ON COLUMN BDC_SL_JYXX.FZZZZSJS IS '非住宅增值税计税';
ALTER TABLE BDC_SL_JYXX add(CZCB NUMBER(22,6));
COMMENT ON COLUMN BDC_SL_JYXX.CZCB IS '重置成本';
ALTER TABLE BDC_SL_JYXX add(YQSRQ DATE);
COMMENT ON COLUMN BDC_SL_JYXX.YQSRQ IS '原契税日期';
ALTER TABLE BDC_SL_JYXX add(YFPSJ DATE);
COMMENT ON COLUMN BDC_SL_JYXX.YFPSJ IS '原发票时间';
ALTER TABLE BDC_SL_JYXX add(GSCEZS NUMBER(1));
COMMENT ON COLUMN BDC_SL_JYXX.GSCEZS IS '个税差额征收';

--changeset BDC_SL_JYXX:17 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(TDCB NUMBER(22,6));
COMMENT ON COLUMN BDC_SL_JYXX.TDCB IS '土地成本';
ALTER TABLE BDC_SL_JYXX add(QCQSJSJE NUMBER(22,6));
COMMENT ON COLUMN BDC_SL_JYXX.QCQSJSJE IS '前次契税计税金额';

--changeset BDC_SL_JYXX:18 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(BAJE NUMBER(22,6));
COMMENT ON COLUMN BDC_SL_JYXX.BAJE IS '备案金额';

--changeset BDC_SL_JYXX:19 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(BASBYY VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_JYXX.BASBYY IS '备案失败原因';

--changeset BDC_SL_JYXX:20 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(JYFS VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_JYXX.JYFS IS '交易方式（bdc_sl_zd_qszydx字典表）';

--changeset BDC_SL_JYXX:21 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(HBBC NUMBER(1));
COMMENT ON COLUMN BDC_SL_JYXX.HBBC IS '货币补偿';

--changeset BDC_SL_JYXX:22 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(BCJE NUMBER(22,6));
COMMENT ON COLUMN BDC_SL_JYXX.BCJE IS '补偿金额';

--changeset BDC_SL_JYXX:23 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(ZL VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_JYXX.ZL IS '坐落';

--changeset BDC_SL_JYXX:24 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_JYXX add(CJJGSFHS NUMBER(1));
COMMENT ON COLUMN BDC_SL_JYXX.CJJGSFHS IS '成交价格是否含税';
ALTER TABLE BDC_SL_JYXX add(sfbzqs NUMBER(1));
COMMENT ON COLUMN BDC_SL_JYXX.sfbzqs IS '是否不征契税';