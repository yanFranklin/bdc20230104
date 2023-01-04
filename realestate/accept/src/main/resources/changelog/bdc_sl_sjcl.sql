--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SJCL:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_SJCL
(
  sjclid VARCHAR2(32) not null,
  xmid   VARCHAR2(32),
  clmc   VARCHAR2(200) not null,
  fs     NUMBER(3),
  xh     NUMBER(3),
  sjlx   NUMBER,
  mrfs   NUMBER(3),
  sfsjsy NUMBER(2),
  sfewsj NUMBER(2),
  sfbcsj NUMBER(2),
  ys     NUMBER(4),
  bz     VARCHAR2(2000),
  wjzxid VARCHAR2(32),
  xbclfs NUMBER(2)
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
comment on table BDC_SL_SJCL
  is '不动产受理收件材料';
comment on column BDC_SL_SJCL.sjclid
  is '收件材料ID';
comment on column BDC_SL_SJCL.xmid
  is '项目ID';
comment on column BDC_SL_SJCL.clmc
  is '材料名称';
comment on column BDC_SL_SJCL.fs
  is '份数';
comment on column BDC_SL_SJCL.xh
  is '序号';
comment on column BDC_SL_SJCL.sjlx
  is '收件类型';
comment on column BDC_SL_SJCL.mrfs
  is '默认份数';
comment on column BDC_SL_SJCL.sfsjsy
  is '是否收缴收验';
comment on column BDC_SL_SJCL.sfewsj
  is '是否额外收件';
comment on column BDC_SL_SJCL.sfbcsj
  is '是否补充收件';
comment on column BDC_SL_SJCL.ys
  is '页数';
comment on column BDC_SL_SJCL.bz
  is '备注';
comment on column BDC_SL_SJCL.wjzxid
  is '文件中心NodeId(记录文件中心与其对应的NodeId)';
comment on column BDC_SL_SJCL.xbclfs
  is '需补材料份数';
alter table BDC_SL_SJCL
  add constraint PK_BDC_SL_SJCL primary key (SJCLID)
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
--changeset BDC_SL_SJCL:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL ADD(GZLSLID VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_SJCL.GZLSLID IS '工作流实例ID';
ALTER TABLE BDC_SL_SJCL MODIFY WJZXID varchar2(32);

--changeset BDC_SL_SJCL:3 failOnError:false runOnChange:true runAlways:false
CREATE INDEX BDC_SL_SJCL_GZLSLID ON BDC_SL_SJCL(GZLSLID);

--changeset BDC_SL_SJCL:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL ADD(SFSS NUMBER(1) default '0');
COMMENT ON COLUMN BDC_SL_SJCL.SFSS IS '是否涉税';

--changeset BDC_SL_SJCL:5 failOnError:false runOnChange:true runAlways:false
CREATE INDEX BDC_SL_SJCL_WJZXID ON BDC_SL_SJCL(WJZXID);

--changeset BDC_SL_SJCL:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL
    ADD (SQBM NUMBER(1));
COMMENT ON COLUMN BDC_SL_SJCL.SQBM IS '收取部门';

--changeset BDC_SL_SJCL:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL
    ADD (SQBMTEMP varchar2(100));
COMMENT ON COLUMN BDC_SL_SJCL.SQBMTEMP IS '收取部门';
UPDATE BDC_SL_SJCL
SET SQBMTEMP=SQBM;
ALTER TABLE BDC_SL_SJCL
    DROP COLUMN SQBM;
ALTER TABLE BDC_SL_SJCL RENAME COLUMN SQBMTEMP TO SQBM;

--changeset BDC_SL_SJCL:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL
    ADD (DJXL VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_SJCL.DJXL IS '登记小类';
ALTER TABLE BDC_SL_SJCL
    ADD (SXH NUMBER(1));
COMMENT ON COLUMN BDC_SL_SJCL.SXH IS '顺序号';

--changeset BDC_SL_SJCL:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL ADD(SFBC NUMBER(1) default '0');
COMMENT ON COLUMN BDC_SL_SJCL.SFBC IS '是否必传';

--changeset BDC_SL_SJCL:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_SJCL
    ADD (SFPZ NUMBER(1));
COMMENT ON COLUMN BDC_SL_SJCL.SFPZ IS '是否批注';
ALTER TABLE BDC_SL_SJCL
    ADD (PZ VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_SJCL.PZ IS '批注';