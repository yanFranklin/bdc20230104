--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_CDXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_CDXX
(
    cdxxid    VARCHAR2(32) not null,
    xmid      VARCHAR2(32) not null,
    cdlx      NUMBER(2),
    cjzmlx    NUMBER(2),
    cdr       VARCHAR2(200),
    cdrzjzl   NUMBER(2),
    cdrzjh    VARCHAR2(100),
    cdyy      VARCHAR2(500),
    dyzzlx    VARCHAR2(50),
    dyzzys    NUMBER(4),
    sfcjzm    NUMBER(1),
    fph       VARCHAR2(32),
    xcxr      VARCHAR2(200),
    cqzh      VARCHAR2(2000),
    zl        VARCHAR2(1000),
    cxjg      VARCHAR2(2000),
    dlr       VARCHAR2(20),
    dlrzjzl   NUMBER(2),
    dlrzjh    VARCHAR2(50),
    dlrdh     NUMBER(20),
    cxmdhyt   VARCHAR2(200),
    djbjzxx   VARCHAR2(200),
    djyspz    VARCHAR2(200),
    cxjgyq    VARCHAR2(200),
    cdrdh     VARCHAR2(50),
    cxmdhytqt VARCHAR2(100),
    xcxsx VARCHAR2(200)
);
-- Add comments to the table
comment
on table BDC_SL_CDXX
  is '不动产查档信息表';
-- Add comments to the columns
comment
on column BDC_SL_CDXX.cdxxid
  is '查档信息id';
comment
on column BDC_SL_CDXX.xmid
  is '项目id';
comment
on column BDC_SL_CDXX.cdlx
  is '查档类型';
comment
on column BDC_SL_CDXX.cjzmlx
  is '出具证明类型';
comment
on column BDC_SL_CDXX.cdr
  is '查档人';
comment
on column BDC_SL_CDXX.cdrzjzl
  is '查档人证件种类';
comment
on column BDC_SL_CDXX.cdrzjh
  is '查档人证件号';
comment
on column BDC_SL_CDXX.cdyy
  is '查档原因';
comment
on column BDC_SL_CDXX.dyzzlx
  is '打印纸张类型';
comment
on column BDC_SL_CDXX.dyzzys
  is '打印纸张页数';
comment
on column BDC_SL_CDXX.sfcjzm
  is '是否出具证明';
comment
on column BDC_SL_CDXX.fph
  is '发票号码';
comment
on column BDC_SL_CDXX.xcxr
  is '需查询人';
comment
on column BDC_SL_CDXX.cqzh
  is '产权证号';
comment
on column BDC_SL_CDXX.zl
  is '坐落';
comment
on column BDC_SL_CDXX.cxjg
  is '查询结果';
comment
on column BDC_SL_CDXX.dlr
  is '代理人';
comment
on column BDC_SL_CDXX.dlrzjzl
  is '代理人证件种类';
comment
on column BDC_SL_CDXX.dlrzjh
  is '代理人证件号';
comment
on column BDC_SL_CDXX.dlrdh
  is '代理人电话';
comment
on column BDC_SL_CDXX.cxmdhyt
  is '查询目的或用途';
comment
on column BDC_SL_CDXX.djbjzxx
  is '登记簿记载信息';
comment
on column BDC_SL_CDXX.djyspz
  is '登记原始凭证';
comment
on column BDC_SL_CDXX.cxjgyq
  is '查询结果要求';
comment
on column BDC_SL_CDXX.cdrdh
  is '查询人电话';
comment
on column BDC_SL_CDXX.cxmdhytqt
  is '查询目的或用途其他';
comment
on column BDC_SL_CDXX.xcxsx
  is '需查询事项';
comment
on column BDC_SL_CDXX.cdlb
  is '查档类别';
-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_SL_CDXX
    add constraint BDC_SL_CDXX_PK primary key (CDXXID) using index
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

--changeset BDC_SL_CDXX:2 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_CDXX
    add (cdlb VARCHAR2(20));
COMMENT
    ON COLUMN BDC_SL_CDXX.cdlb IS '查档类别';
--changeset BDC_SL_CDXX:3 failOnError:false runOnChange:true runAlways:false

alter table BDC_SL_CDXX
    add (byslyy VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_CDXX.byslyy IS '不予受理原因';


--changeset BDC_SL_CDXX:4 failOnError:false runOnChange:true runAlways:false

alter table BDC_SL_CDXX
    add (xcxrzjh VARCHAR2(200));
COMMENT ON COLUMN BDC_SL_CDXX.xcxrzjh IS '需查询人证件号';

--changeset BDC_SL_CDXX:5 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_CDXX add (CDLXTEMP VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_CDXX.CDLXTEMP IS '查档类型';
update BDC_SL_CDXX set CDLXTEMP=CDLX;
alter table BDC_SL_CDXX drop COLUMN CDLX;
alter table BDC_SL_CDXX rename COLUMN CDLXTEMP TO CDLX;

--changeset BDC_SL_CDXX:6 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_CDXX drop COLUMN DYZZLX;
alter table BDC_SL_CDXX drop COLUMN DYZZYS;
alter table BDC_SL_CDXX add (DLRDHTEMP VARCHAR2(20));
COMMENT ON COLUMN BDC_SL_CDXX.DLRDHTEMP IS '代理人电话';
update BDC_SL_CDXX set DLRDHTEMP=DLRDH;
alter table BDC_SL_CDXX drop COLUMN DLRDH;
alter table BDC_SL_CDXX rename COLUMN DLRDHTEMP TO DLRDH;


--changeset BDC_SL_CDXX:7 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_CDXX
    add (CXSQLB VARCHAR2(20));
COMMENT ON COLUMN BDC_SL_CDXX.CXSQLB IS '查询申请类别';
alter table BDC_SL_CDXX
    add (CXNR VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_CDXX.CXNR IS '查询内容';
alter table BDC_SL_CDXX
    add (LSSWSMC VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_CDXX.LSSWSMC IS '律师事务所名称';
alter table BDC_SL_CDXX
    add (LSSWSZJH VARCHAR2(100));
COMMENT ON COLUMN BDC_SL_CDXX.LSSWSZJH IS '证件号';
alter table BDC_SL_CDXX
    add (LSSWSCLYS NUMBER(10));
COMMENT ON COLUMN BDC_SL_CDXX.LSSWSCLYS IS '材料页数';
alter table BDC_SL_CDXX
    add (LSSWSFLSW VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_CDXX.LSSWSFLSW IS '法律事务';
alter table BDC_SL_CDXX
    add (LSSWSSJXXMLQD VARCHAR2(1000));
COMMENT ON COLUMN BDC_SL_CDXX.LSSWSSJXXMLQD IS '收集信息目录清单';

--changeset BDC_SL_CDXX:8 failOnError:false runOnChange:true runAlways:false
alter table BDC_SL_CDXX
    add (BDCDYH VARCHAR2(28));
COMMENT ON COLUMN BDC_SL_CDXX.BDCDYH IS '不动产单元号';
--changeset BDC_BYSL:9 failOnError:false runOnChange:true runAlways:false
 create table BDC_BYSL
            (
            byslid VARCHAR2(32) NOT NULL,
            gzlslid VARCHAR2(32) NOT NULL,
            fdsxsqr VARCHAR2(200),
            lxdh VARCHAR2(50),
            thsj DATE,
            zl VARCHAR2(1000),
            blsx VARCHAR2(50),
            fdlyyj VARCHAR2(10),
            jtqk VARCHAR2(500),
            BZ VARCHAR2(2000),
            cbksyj   VARCHAR2(1000),
            zcfgyj VARCHAR2(1000),
            fgldyj VARCHAR2(1000),
            lx VARCHAR2(2)
            );
alter table BDC_BYSL add primary key (byslid);
create INDEX IDX_BDC_BYSL_GZLSLID ON BDC_BYSL (gzlslid);
comment on table BDC_BYSL
  is '不动产不予受理表';
comment on column BDC_BYSL.byslid
  is '不予受理id';

comment on column BDC_BYSL.gzlslid
  is '工作流实例id';

comment on column BDC_BYSL.fdsxsqr
  is '否定事项申请人';

comment on column BDC_BYSL.lxdh
  is '联系电话';
comment on column BDC_BYSL.thsj
  is '退回时间';

comment on column BDC_BYSL.zl
  is '坐落';

comment on column BDC_BYSL.blsx
  is '办理事项';

comment on column BDC_BYSL.fdlyyj
  is '否定理由依据';

comment on column BDC_BYSL.jtqk
  is '具体情况';

comment on column BDC_BYSL.cbksyj
  is '承办科室意见';

comment on column BDC_BYSL.zcfgyj
  is '政策法规意见';

comment on column BDC_BYSL.fgldyj
  is '分管领导意见';
comment on column BDC_BYSL.bz
  is '备注';
comment on column BDC_BYSL.lx
  is '不予受理类型 1： 不予受理2： 不予登记';

--changeset BDC_SL_CDXX:10 failOnError:false runOnChange:true runAlways:false
alter table BDC_BYSL
    add (gzsbh VARCHAR2(100));
COMMENT ON COLUMN BDC_BYSL.gzsbh IS '告知书编号';

--changeset BDC_SL_CDXX:11 failOnError:false runOnChange:true runAlways:false
alter table BDC_BYSL
    add (djyy VARCHAR2(500));
COMMENT ON COLUMN BDC_BYSL.djyy IS '登记原因';