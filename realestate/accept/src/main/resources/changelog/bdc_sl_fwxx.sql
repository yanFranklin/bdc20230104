--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_FWXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_FWXX
(
  fwxxid  VARCHAR2(32) not null,
  xmid    VARCHAR2(32) not null,
  jznf    NUMBER(4),
  jzcx    VARCHAR2(10),
  fjh     VARCHAR2(10),
  ycmj    NUMBER(15,2),
  dxsmj   NUMBER(15,2),
  glmj    NUMBER(15,2),
  ckmj    NUMBER(15,2),
  czqk    VARCHAR2(200),
  xqmc    VARCHAR2(200),
  fwdh    VARCHAR2(100),
  tnmj    NUMBER(15,2),
  fwyt    NUMBER(4)
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
comment on table BDC_SL_FWXX
  is '不动产受理房屋信息';
comment on column BDC_SL_FWXX.fwxxid
  is '房屋信息ID';
comment on column BDC_SL_FWXX.xmid
  is '项目ID';
comment on column BDC_SL_FWXX.jznf
  is '建筑年份';
comment on column BDC_SL_FWXX.jzcx
  is '建筑朝向';
comment on column BDC_SL_FWXX.fjh
  is '房间号';
comment on column BDC_SL_FWXX.ycmj
  is '跃层面积';
comment on column BDC_SL_FWXX.dxsmj
  is '地下室面积';
comment on column BDC_SL_FWXX.glmj
  is '阁楼面积';
comment on column BDC_SL_FWXX.ckmj
  is '车库面积';
comment on column BDC_SL_FWXX.czqk
  is '出租情况';
comment on column BDC_SL_FWXX.xqmc
  is '小区名称';
comment on column BDC_SL_FWXX.fwdh
  is '房屋幢号';
comment on column BDC_SL_FWXX.tnmj
  is '套内面积';
comment on column BDC_SL_FWXX.fwyt
  is '房屋用途';

alter table BDC_SL_FWXX
  add constraint BDC_SL_FWXX primary key (FWXXID)
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

--changeset BDC_SL_FWXX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(FWJG NUMBER(2));
COMMENT ON COLUMN BDC_SL_FWXX.FWJG IS '房屋结构';

--changeset BDC_SL_FWXX:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(XZQH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_FWXX.XZQH IS '行政区划';
ALTER TABLE BDC_SL_FWXX add(SZC NUMBER(4));
COMMENT ON COLUMN BDC_SL_FWXX.SZC IS '所在层';
ALTER TABLE BDC_SL_FWXX add(ZCS NUMBER(4));
COMMENT ON COLUMN BDC_SL_FWXX.ZCS IS '总层数';

--changeset BDC_SL_FWXX:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(DSZCS NUMBER(4));
COMMENT ON COLUMN BDC_SL_FWXX.DSZCS IS '地上总层数';

--changeset BDC_SL_FWXX:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(JDDM VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_FWXX.JDDM IS '街道代码';

--changeset BDC_SL_FWXX:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(YSFWBM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_FWXX.YSFWBM IS '房产预售房屋编码';

--changeset BDC_SL_FWXX:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(FWBM VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_FWXX.FWBM IS '房屋编码';

--changeset BDC_SL_FWXX:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(JZMJ NUMBER(15,2));
COMMENT ON COLUMN BDC_SL_FWXX.JZMJ IS '建筑面积';

--changeset BDC_SL_FWXX:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX modify JDDM VARCHAR2(100);

--changeset BDC_SL_FWXX:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(FWLX NUMBER(2));
COMMENT ON COLUMN BDC_SL_FWXX.FWLX IS '房屋类型';
ALTER TABLE BDC_SL_FWXX add(FWXZ NUMBER(2));
COMMENT ON COLUMN BDC_SL_FWXX.FWXZ IS '房屋性质';
ALTER TABLE BDC_SL_FWXX add(FTJZMJ NUMBER(15,2));
COMMENT ON COLUMN BDC_SL_FWXX.FTJZMJ IS '分摊建筑面积';
ALTER TABLE BDC_SL_FWXX add(TDSYQSSJ DATE);
COMMENT ON COLUMN BDC_SL_FWXX.TDSYQSSJ IS '土地使用起始时间';
ALTER TABLE BDC_SL_FWXX add(TDSYJSSJ DATE);
COMMENT ON COLUMN BDC_SL_FWXX.TDSYJSSJ IS '土地使用结束时间';
ALTER TABLE BDC_SL_FWXX add(TDSYQR VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_FWXX.TDSYQR IS '土地使用权人';
ALTER TABLE BDC_SL_FWXX add(SZMYC VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_FWXX.SZMYC IS '所在名义层';
ALTER TABLE BDC_SL_FWXX add(JGSJ VARCHAR2(20));
COMMENT ON COLUMN BDC_SL_FWXX.JGSJ IS '竣工时间';
ALTER TABLE BDC_SL_FWXX add(TDSYQMJ NUMBER(15,2));
COMMENT ON COLUMN BDC_SL_FWXX.TDSYQMJ IS '土地使用权面积';
ALTER TABLE BDC_SL_FWXX add(FTTDMJ NUMBER(16,3));
COMMENT ON COLUMN BDC_SL_FWXX.FTTDMJ IS '分摊土地面积';
ALTER TABLE BDC_SL_FWXX add(DYTDMJ NUMBER(16,3));
COMMENT ON COLUMN BDC_SL_FWXX.DYTDMJ IS '独用土地面积';
ALTER TABLE BDC_SL_FWXX add(QLQTZK VARCHAR2(4000));
COMMENT ON COLUMN BDC_SL_FWXX.QLQTZK IS '权利其他状况';
ALTER TABLE BDC_SL_FWXX add(FJ VARCHAR2(4000));
COMMENT ON COLUMN BDC_SL_FWXX.FJ IS '附记';

--changeset BDC_SL_FWXX:11 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX modify  FWYT NUMBER (10);

--changeset BDC_SL_FWXX:12 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(GHYTXL VARCHAR2(20));
COMMENT ON COLUMN BDC_SL_FWXX.GHYTXL IS '规划用途细类';

--changeset BDC_SL_FWXX:13 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(hsid VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_FWXX.HSID IS '户室id';

--changeset BDC_SL_FWXX:14 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(XQDM VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_FWXX.XQDM IS '小区代码';


--changeset BDC_SL_FWXX:15 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(FWZJ  NUMBER(20,4));
COMMENT ON COLUMN BDC_SL_FWXX.FWZJ IS '房屋总价';

ALTER TABLE BDC_SL_FWXX add(XZZT NUMBER(2));
COMMENT ON COLUMN BDC_SL_FWXX.XZZT IS '限制状态';

ALTER TABLE BDC_SL_FWXX add(DYZT NUMBER(2));
COMMENT ON COLUMN BDC_SL_FWXX.DYZT IS '抵押状态';

ALTER TABLE BDC_SL_FWXX add(DYH VARCHAR2(20));
COMMENT ON COLUMN BDC_SL_FWXX.DYH IS '单元号';


--changeset BDC_SL_FWXX:16 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(JYJG  NUMBER(20,4));
COMMENT ON COLUMN BDC_SL_FWXX.JYJG IS '房地产交易价格';

ALTER TABLE BDC_SL_FWXX add(MYZCS VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_FWXX.MYZCS IS '名义总层数';


--changeset BDC_SL_FWXX:17 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(YTMC VARCHAR2(500));
COMMENT ON COLUMN BDC_SL_FWXX.YTMC IS '用途名称';


--changeset BDC_SL_FWXX:18 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_FWXX add(FWJGMC VARCHAR2(500));
COMMENT ON COLUMN BDC_SL_FWXX.FWJGMC IS '房屋结构名称';
