--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_DJXL_PZ:1 failOnError:false runOnChange:true runAlways:false
create table BDC_DJXL_PZ
(
  pzid    VARCHAR2(32) not null,
  djxl    VARCHAR2(20) not null,
  gzldyid VARCHAR2(32) not null
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table BDC_DJXL_PZ
  is '不动产登记小类配置';
-- Add comments to the columns
comment on column BDC_DJXL_PZ.pzid
  is '配置ID';
comment on column BDC_DJXL_PZ.djxl
  is '登记小类';
comment on column BDC_DJXL_PZ.gzldyid
  is '工作流定义ID';
-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_DJXL_PZ
  add constraint PK_BDCDJXLPZ primary key (PZID)
  using index
  tablespace BDCSL
  pctfree 10
  initrans 2
  maxtrans 255;


--changeset BDC_DJXL_PZ:2 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ add(QLLX NUMBER(2));
COMMENT ON COLUMN BDC_DJXL_PZ.QLLX IS '权利类型';

--changeset BDC_DJXL_PZ:3 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ add(SXH NUMBER(1));
COMMENT ON COLUMN BDC_DJXL_PZ.SXH IS '顺序号';

--changeset BDC_DJXL_PZ:4 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ add(BDCLX NUMBER(2));
COMMENT ON COLUMN BDC_DJXL_PZ.BDCLX IS '不动产类型';

--changeset BDC_DJXL_PZ:5 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ add(DYHQLLX NUMBER(2));
COMMENT ON COLUMN BDC_DJXL_PZ.DYHQLLX IS '单元号权利类型';

--changeset BDC_DJXL_PZ:6 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ add(SFSF NUMBER(1));
COMMENT ON COLUMN BDC_DJXL_PZ.SFSF IS '是否收费';

--changeset BDC_DJXL_PZ:7 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ add(SQSDYLX VARCHAR (50));
COMMENT ON COLUMN BDC_DJXL_PZ.SQSDYLX IS '申请书打印类型';

--changeset BDC_DJXL_PZ:8 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (SPBDYLX VARCHAR(50));
COMMENT ON COLUMN BDC_DJXL_PZ.SPBDYLX IS '审批表打印类型';

--changeset BDC_DJXL_PZ:9 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (PZXGSJ DATE);
COMMENT ON COLUMN BDC_DJXL_PZ.PZXGSJ IS '配置修改时间';

--changeset BDC_DJXL_PZ:10 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (YQLLX NUMBER(2));
COMMENT ON COLUMN BDC_DJXL_PZ.YQLLX IS '原权利类型';

--changeset BDC_DJXL_PZ:11 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (sjddylx VARCHAR(50));
COMMENT ON COLUMN BDC_DJXL_PZ.sjddylx IS '收件单打印类型';

--changeset BDC_DJXL_PZ:12 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (damldylx VARCHAR(50));
COMMENT ON COLUMN BDC_DJXL_PZ.damldylx IS '档案目录打印类型';

--changeset BDC_DJXL_PZ:13 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (dafmdylx VARCHAR(50));
COMMENT ON COLUMN BDC_DJXL_PZ.dafmdylx IS '档案封面打印类型';

--changeset BDC_DJXL_PZ:14 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (ygdjzl VARCHAR(50));
COMMENT ON COLUMN BDC_DJXL_PZ.ygdjzl IS '预告登记种类';

--changeset BDC_DJXL_PZ:15 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (jtdafmdylx VARCHAR(50));
COMMENT ON COLUMN BDC_DJXL_PZ.jtdafmdylx IS '金坛档案封面打印类型';

--changeset BDC_DJXL_PZ:16 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (lydafmdylx VARCHAR(50));
COMMENT ON COLUMN BDC_DJXL_PZ.lydafmdylx IS '溧阳档案封面打印类型';

--changeset BDC_DJXL_PZ:17 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ
    add (sfsb NUMBER(2));
COMMENT ON COLUMN BDC_DJXL_PZ.sfsb IS '是否上报';

--changeset BDC_DJXL_PZ:18 failOnError:false runOnChange:true runAlways:false
            create table BDC_JRYWPZ_JCJG
            (
            ID VARCHAR2(32) not null
            primary key,
            gzldyid VARCHAR2(50),
            lcmc VARCHAR2(100),
            djxl VARCHAR2(32),
            bdclx VARCHAR2(32),
            bdcdyfwlx VARCHAR2(32),
            qllx VARCHAR2(10),
            jcjg VARCHAR2(500),
            pzid VARCHAR2(32)
            );
            comment on table BDC_JRYWPZ_JCJG is '接入业务配置检查结果';
            comment on column BDC_JRYWPZ_JCJG.id is '主键';
            comment on column BDC_JRYWPZ_JCJG.dbrzid is '流程定义id';
            comment on column BDC_JRYWPZ_JCJG.xmid is '流程名称';
            comment on column BDC_JRYWPZ_JCJG.bwid is '登记小类';
            comment on column BDC_JRYWPZ_JCJG.bdcdyh is '不动产类型';
            comment on column BDC_JRYWPZ_JCJG.xmid is '不动产单元房屋类型';
            comment on column BDC_JRYWPZ_JCJG.bwid is '权利类型';
            comment on column BDC_JRYWPZ_JCJG.bdcdyh is '检查结果';
            comment on column BDC_JRYWPZ_JCJG.pzid is '配置id';

--changeset BDC_DJXL_PZ:19 failOnError:false runOnChange:true runAlways:false
update bdc_djxl_pz set sfsb = 1;

--changeset BDC_DJXL_PZ:20 failOnError:false runOnChange:true runAlways:false
alter table BDC_DJXL_PZ add (lcscsfxx NUMBER(1));
COMMENT ON COLUMN BDC_DJXL_PZ.lcscsfxx IS '流程删除是否删除收费信息';
alter table BDC_DJXL_PZ modify lcscsfxx default 1;

--changeset BDC_DJXL_PZ:21 failOnError:false runOnChange:true runAlways:false
update bdc_djxl_pz set lcscsfxx = 1;