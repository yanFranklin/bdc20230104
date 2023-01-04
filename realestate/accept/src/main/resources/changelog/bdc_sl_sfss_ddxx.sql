--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SFSS_DDXX:1 failOnError:false runOnChange:true runAlways:false
-- Create table
create table BDC_SL_SFSS_DDXX
(
  ID       VARCHAR2(32) not null,
  DDBH     VARCHAR2(50) not null,
  DDJE     NUMBER(12,4) not null,
  ZE       NUMBER(12,4),
  SFGLID   VARCHAR2(32) not null,
  SFGLIDLX NUMBER(1) not null,
  JFURL    VARCHAR2(100),
  JFZT     NUMBER(1),
  ZDH      VARCHAR2(100),
  CKH      VARCHAR2(100),
  SHDM     VARCHAR2(100),
  TKDH     VARCHAR2(100),
  GZLSLID  VARCHAR2(32) not null
);

-- Add comments to the table
comment on table BDC_SL_SFSS_DDXX
  is '不动产收费收税订单信息';
-- Add comments to the columns
comment on column BDC_SL_SFSS_DDXX.ID
  is '主键';
comment on column BDC_SL_SFSS_DDXX.DDBH
  is '订单编号';
comment on column BDC_SL_SFSS_DDXX.DDJE
  is '订单金额';
comment on column BDC_SL_SFSS_DDXX.ZE
  is '总额';
comment on column BDC_SL_SFSS_DDXX.SFGLID
  is '税费关联ID,1: SFXXID（收费信息主键）、2: SFXMID（收费项目主键）、3: HSXXID（核税信息主键）、4: HSMXID（核税明细信息主键）、5：SFSSXXID（收费收税信息主键）';
comment on column BDC_SL_SFSS_DDXX.SFGLIDLX
  is '税费关联ID类型';
comment on column BDC_SL_SFSS_DDXX.JFURL
  is '缴费URL地址';
comment on column BDC_SL_SFSS_DDXX.JFZT
  is '支付状态';
comment on column BDC_SL_SFSS_DDXX.ZDH
  is '终端号';
comment on column BDC_SL_SFSS_DDXX.CKH
  is '参考号';
comment on column BDC_SL_SFSS_DDXX.SHDM
  is '商户代码';
comment on column BDC_SL_SFSS_DDXX.TKDH
  is '退款单号';
comment on column BDC_SL_SFSS_DDXX.GZLSLID
  is '工作流实例ID';

--changeset BDC_SL_SFSS_DDXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX ADD(QLRLB VARCHAR2(2));
COMMENT ON COLUMN BDC_SL_SFSS_DDXX.QLRLB IS '权利人类别';

--changeset BDC_SL_SFSS_DDXX:3 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_SFSS_DDXX add constraint PK_BDC_SL_SFSS_DDXX primary key (ID);

--changeset BDC_SL_SFSS_DDXX:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX MODIFY JFURL VARCHAR2(500);

--changeset BDC_SL_SFSS_DDXX:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX ADD(DSFDDBH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFSS_DDXX.DSFDDBH IS '第三方订单编号';

--changeset BDC_SL_SFSS_DDXX:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX ADD(DDSCSJ DATE);
COMMENT ON COLUMN BDC_SL_SFSS_DDXX.DDSCSJ IS '订单生成时间';

--changeset BDC_SL_SFSS_DDXX:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX ADD(YJDH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFSS_DDXX.YJDH IS '月结单号';
ALTER TABLE BDC_SL_SFSS_DDXX MODIFY GZLSLID null;
CREATE INDEX "BDC_SL_SFSS_DDXX_YJDH_INDEX" ON BDC_SL_SFSS_DDXX (YJDH);

--changeset BDC_SL_SFSS_DDXX:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX ADD(DDZT NUMBER(2));
COMMENT ON COLUMN BDC_SL_SFSS_DDXX.DDZT IS '订单状态';

--changeset BDC_SL_SFSS_DDXX:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX ADD(JKM VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_SFSS_DDXX.JKM IS '缴款码';

--changeset BDC_SL_SFSS_DDXX:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX ADD(DDLX NUMBER(2));
COMMENT ON COLUMN BDC_SL_SFSS_DDXX.DDLX IS '订单类型';

--changeset BDC_SL_SFSS_DDXX:11 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SFSS_DDXX ADD(HYZFJFTJ NUMBER(2));
COMMENT ON COLUMN BDC_SL_SFSS_DDXX.HYZFJFTJ IS '合一支付缴费途径：1.商业银行 2. 合一支付';