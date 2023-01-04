--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_XZTZ_PZ:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_XZTZ_PZ
(
  PZID      VARCHAR2(32),
  GZLDYID   VARCHAR2(32),
  XZTZLX    VARCHAR2(50),
  QLLX      VARCHAR2(50),
  ZDTZM     VARCHAR2(50),
  DZWTZM    VARCHAR2(50),
  BDCDYFWLX VARCHAR2(50),
  DYHCXLX   NUMBER(1)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_XZTZ_PZ
  is '不动产受理选择台账配置';
comment on column BDC_SL_XZTZ_PZ.PZID
  is '配置ID';
comment on column BDC_SL_XZTZ_PZ.GZLDYID
  is '工作流定义ID';
comment on column BDC_SL_XZTZ_PZ.XZTZLX
  is '选择台账类型(1:不动产单元信息 2:产权证 3:查封 4:逻辑幢 5:外联证书)';
comment on column BDC_SL_XZTZ_PZ.QLLX
  is '权利类型';
comment on column BDC_SL_XZTZ_PZ.ZDTZM
  is '宗地特征码';
comment on column BDC_SL_XZTZ_PZ.DZWTZM
  is '定着物特征码';
comment on column BDC_SL_XZTZ_PZ.BDCDYFWLX
  is '不动产单元房屋类型';
comment on column BDC_SL_XZTZ_PZ.DYHCXLX
  is '单元号查询类型（选择不动产单元号table使用）（1:土地及其定着物类型不动产单元信息  2:海域及其定作物类型不动产单元信息 3:构筑物不动产单元信息）';

--changeset BDC_SL_XZTZ_PZ:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XZTZ_PZ add(BDCLX VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_XZTZ_PZ.BDCLX IS '不动产类型';

--changeset BDC_SL_XZTZ_PZ:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XZTZ_PZ add constraint BDC_SL_XZTZ_PZ_GZLDYID unique (GZLDYID);

--changeset BDC_SL_XZTZ_PZ:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XZTZ_PZ add(ZSLX NUMBER (1));
COMMENT ON COLUMN BDC_SL_XZTZ_PZ.ZSLX IS '证书类型';

--changeset BDC_SL_XZTZ_PZ:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XZTZ_PZ add(YGDJZL VARCHAR2 (50));
COMMENT ON COLUMN BDC_SL_XZTZ_PZ.YGDJZL IS '预告登记种类,权利类型为预告时使用';

--changeset BDC_SL_XZTZ_PZ:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XZTZ_PZ add(CXTJ VARCHAR2 (500));
COMMENT ON COLUMN BDC_SL_XZTZ_PZ.CXTJ IS '自定义查询条件,配置为空默认展示全部';

--changeset BDC_SL_XZTZ_PZ:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XZTZ_PZ add (XMSJLY VARCHAR2 (10) default '0');
COMMENT ON COLUMN BDC_SL_XZTZ_PZ.XMSJLY IS '项目数据来源';

--changeset BDC_SL_XZTZ_PZ:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XZTZ_PZ add (XMLY VARCHAR2 (20));
COMMENT ON COLUMN BDC_SL_XZTZ_PZ.XMLY IS '项目来源';

--changeset BDC_SL_XZTZ_PZ:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_XZTZ_PZ add (DZWYT VARCHAR2 (100));
COMMENT ON COLUMN BDC_SL_XZTZ_PZ.DZWYT IS '定着物用途';
