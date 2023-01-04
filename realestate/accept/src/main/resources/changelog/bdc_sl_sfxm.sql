--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SFXM:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_SFXM
(
  sfxmid VARCHAR2(32) not null,
  sfxxid VARCHAR2(32),
  sfxmmc VARCHAR2(200),
  sfxmdm VARCHAR2(50),
  xh     NUMBER(3),
  sl     NUMBER(5),
  jmje   NUMBER(12,4),
  ysje   NUMBER(12,4),
  ssje   NUMBER(12,4),
  sfbl   NUMBER(10,2),
  sfxmbz     VARCHAR2(32),
  jedw   VARCHAR2(100),
  jsff   VARCHAR2(100),
  jffs   NUMBER,
  jfzd   VARCHAR2(100),
  sflx   VARCHAR2(2)
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
comment on table BDC_SL_SFXM
  is '不动产收费项目';
comment on column BDC_SL_SFXM.sfxmid
  is '收费项目id';
comment on column BDC_SL_SFXM.sfxxid
  is '收费单信息id';
comment on column BDC_SL_SFXM.sfxmmc
  is '收费项目名称';
comment on column BDC_SL_SFXM.sfxmdm
  is '收费项目代码?';
comment on column BDC_SL_SFXM.xh
  is '序号';
comment on column BDC_SL_SFXM.sl
  is '数量';
comment on column BDC_SL_SFXM.jmje
  is '减免金额';
comment on column BDC_SL_SFXM.ysje
  is '应收金额';
comment on column BDC_SL_SFXM.ssje
  is '实收金额';
comment on column BDC_SL_SFXM.sfbl
  is '收费比例';
comment on column BDC_SL_SFXM.sfxmbz
  is '收费项目标准';
comment on column BDC_SL_SFXM.jedw
  is '金额单位';
comment on column BDC_SL_SFXM.jsff
  is '计算方法';
comment on column BDC_SL_SFXM.jffs
  is '缴费方式';
comment on column BDC_SL_SFXM.jfzd
  is '缴费终端';
comment on column BDC_SL_SFXM.sflx
  is '收费类型';
alter table BDC_SL_SFXM
  add constraint PK_BDC_SFXM primary key (SFXMID)
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

--changeset BDC_SL_SFXM:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM ADD(QLRLB VARCHAR2(2));
COMMENT ON COLUMN BDC_SL_SFXM.QLRLB IS '权利人类别';

--changeset BDC_SL_SFXM:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM
    MODIFY SL NUMBER(12, 4);

--changeset BDC_SL_SFXM:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM
    ADD (WXZJDDH VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_SFXM.WXZJDDH IS '维修资金订单号';

--changeset BDC_SL_SFXM:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM
    ADD (YH NUMBER(1) default 1);
COMMENT ON COLUMN BDC_SL_SFXM.YH IS '优惠';

--changeset BDC_SL_SFXM:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM
    ADD (DJYY VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_SFXM.DJYY IS '登记原因';

--changeset BDC_SL_SFXM:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM ADD(SFZT NUMBER(1));
COMMENT ON COLUMN BDC_SL_SFXM.SFZT IS '收费状态';
ALTER TABLE BDC_SL_SFXM ADD (JFSEWMURL VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_SFXM.JFSEWMURL IS '缴费书二维码URL';
ALTER TABLE BDC_SL_SFXM ADD (JFSBM VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_SFXM.JFSBM IS '缴费书编码';
ALTER TABLE BDC_SL_SFXM ADD (JKMSJ DATE);
COMMENT ON COLUMN BDC_SL_SFXM.JKMSJ IS '缴款码时间';
ALTER TABLE BDC_SL_SFXM ADD (TKDH VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_SFXM.TKDH IS '退款单号';

--changeset BDC_SL_SFXM:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM ADD (FPH VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_SFXM.FPH IS '发票号';
--changeset BDC_SL_SFXM:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM ADD (HQFPHSJ DATE);
COMMENT ON COLUMN BDC_SL_SFXM.HQFPHSJ IS '缴款码时间';

--changeset BDC_SL_SFXM:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM ADD (DDJB VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_SFXM.DDJB IS '地段级别';
ALTER TABLE BDC_SL_SFXM
    ADD (sfxmdj NUMBER(12,4));
COMMENT ON COLUMN BDC_SL_SFXM.sfxmdj IS '收费项目单价';

--changeset BDC_SL_SFXM:11 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFXM ADD (JMYY VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_SFXM.JMYY IS '减免原因';