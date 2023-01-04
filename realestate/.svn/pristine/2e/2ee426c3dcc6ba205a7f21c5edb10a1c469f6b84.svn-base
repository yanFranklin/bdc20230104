--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_PJQ:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_PJQ
(
  pjjlid  VARCHAR2(32) not null,
  ywbh    VARCHAR2(50),
  ywmc    VARCHAR2(200),
  ywblsj    DATE,
  jdmc   VARCHAR2(50),
  myd       NUMBER(1),
  bmyyy  NUMBER(1),
  pjsj  DATE,
  blry  VARCHAR2(50),
  sqrxm   VARCHAR2(2000),
  sqrlxfs   VARCHAR2(2000)
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
comment on table BDC_SL_PJQ
  is '不动产受理评价器';
comment on column BDC_SL_PJQ.pjjlid
  is '评价记录id';
comment on column BDC_SL_PJQ.ywbh
  is '业务编号';
comment on column BDC_SL_PJQ.ywmc
  is '业务名称';
comment on column BDC_SL_PJQ.ywblsj
  is '业务办理时间';
comment on column BDC_SL_PJQ.jdmc
  is '节点名称';
comment on column BDC_SL_PJQ.myd
  is '满意度';
comment on column BDC_SL_PJQ.bmyyy
  is '不满意原因';
comment on column BDC_SL_PJQ.pjsj
  is '评价时间';
comment on column BDC_SL_PJQ.blry
  is '办理人员';
comment on column BDC_SL_PJQ.sqrxm
  is '申请人姓名';
comment on column BDC_SL_PJQ.blry
  is '申请人联系方式';

alter table BDC_SL_PJQ
  add constraint BDC_SL_PJQ primary key (pjjlid)
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

--changeset BDC_SL_PJQ:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_PJQ add(dlrmc VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_PJQ.DLRMC IS '代理人名称';
ALTER TABLE BDC_SL_PJQ add(dlrlxfs VARCHAR2(2000));
COMMENT ON COLUMN BDC_SL_PJQ.DLRLXFS IS '代理人联系方式';
ALTER TABLE BDC_SL_PJQ add(qmxx BLOB);
COMMENT ON COLUMN BDC_SL_PJQ.QMXX IS '签名信息';

--changeset BDC_SL_PJQ:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_PJQ add(djbmdm VARCHAR2(50));
COMMENT ON COLUMN BDC_SL_PJQ.DJBMDM IS '登记部门代码';

--changeset BDC_SL_PJQ:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_PJQ add(blryid VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_PJQ.BLRYID IS '办理人员ID';
ALTER TABLE BDC_SL_PJQ add(djbmmc VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_PJQ.DJBMMC IS '登记部门名称';
ALTER TABLE BDC_SL_PJQ add(df  NUMBER(4, 2));
COMMENT ON COLUMN BDC_SL_PJQ.DF IS '得分';
ALTER TABLE BDC_SL_PJQ MODIFY DF NUMBER(5,2);