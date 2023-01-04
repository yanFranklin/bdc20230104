--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_YXBDCDY_KG_PZ:1 failOnError:false runOnChange:true runAlways:false
create table BDC_YXBDCDY_KG_PZ
(
  pzid    VARCHAR2(32) not null,
  gzldyid VARCHAR2(32) not null,
  qlrsjly NUMBER(1),
  ywrsjly NUMBER(1),
  sfscql NUMBER(1),
  qlsjly NUMBER(1),
  zszl NUMBER(1),
  zsxh NUMBER(1),
  sfzlcsh NUMBER(1),
  sfzf NUMBER(1),
  sfhz NUMBER(1),
  sfhyyzxql NUMBER(1),
  zxyql NUMBER(1),
  sfwlzs NUMBER(1),
  qllx NUMBER(1)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table BDC_YXBDCDY_KG_PZ
  is '不动产已选不动产单元开关配置';
-- Add comments to the columns
comment on column BDC_YXBDCDY_KG_PZ.PZID
  is '配置ID';
comment on column BDC_YXBDCDY_KG_PZ.GZLDYID
  is '工作流定义ID';
comment on column BDC_YXBDCDY_KG_PZ.QLRSJLY
  is '权利人数据来源';
comment on column BDC_YXBDCDY_KG_PZ.YWRSJLY
  is '义务人数据来源';
comment on column BDC_YXBDCDY_KG_PZ.SFSCQL
  is '是否生成权利';
comment on column BDC_YXBDCDY_KG_PZ.QLSJLY
  is '权利数据来源';
comment on column BDC_YXBDCDY_KG_PZ.ZSZL
  is '证书种类';
comment on column BDC_YXBDCDY_KG_PZ.ZSXH
  is '证书序号';
comment on column BDC_YXBDCDY_KG_PZ.SFZLCSH
  is '是否增量初始化(未开放)';
comment on column BDC_YXBDCDY_KG_PZ.SFZF
  is '是否主房';
comment on column BDC_YXBDCDY_KG_PZ.SFHZ
  is '是否换证(未开放)';
comment on column BDC_YXBDCDY_KG_PZ.SFHYYZXQL
  is '是否还原原注销权利(未开放)';
comment on column BDC_YXBDCDY_KG_PZ.ZXYQL
  is '注销原权利';
comment on column BDC_YXBDCDY_KG_PZ.SFWLZS
  is '是否外联证书';
comment on column BDC_YXBDCDY_KG_PZ.QLLX
  is '权利类型';

--changeset BDC_YXBDCDY_KG_PZ:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_YXBDCDY_KG_PZ add(ZSXHMRZ NUMBER(1));
COMMENT ON COLUMN BDC_YXBDCDY_KG_PZ.ZSXHMRZ IS '证书序号默认值（为空默认多本证，1：默认一本证）';

--changeset BDC_YXBDCDY_KG_PZ:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_YXBDCDY_KG_PZ rename column zsxh to zssl;
ALTER TABLE BDC_YXBDCDY_KG_PZ rename column zsxhmrz to zsslmrz;

--changeset BDC_YXBDCDY_KG_PZ:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_YXBDCDY_KG_PZ add(SFZX NUMBER(1));
COMMENT ON COLUMN BDC_YXBDCDY_KG_PZ.SFZX IS '是否注销';

--changeset BDC_YXBDCDY_KG_PZ:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_YXBDCDY_KG_PZ add(DJXL VARCHAR2(50));
COMMENT ON COLUMN BDC_YXBDCDY_KG_PZ.DJXL IS '登记小类';
