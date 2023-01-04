--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_SFXX_CZRZ:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_SFXX_CZRZ
(
  ID        VARCHAR2(32) NOT NULL,
  SFXXID    VARCHAR2(32),
  XMID      VARCHAR2(32),
  CZR       VARCHAR2(50),
  SLBH      VARCHAR2(50),
  XGJFYY    VARCHAR2(200),
  XGJFR     VARCHAR2(50),
  XGJFRID   VARCHAR2(50),
  QXJFYY    VARCHAR2(200),
  XGJFSJ    DATE,
  SFSJ      DATE,
  QXSFSJ    DATE
);

comment on table BDC_SL_SFXX_CZRZ
  is '不动产受理收费信息日志表';

comment on column BDC_SL_SFXX_CZRZ.ID
  is '日志id';

comment on column BDC_SL_SFXX_CZRZ.SFXXID
  is '收费信息ID';

comment on column BDC_SL_SFXX_CZRZ.XMID
  is '项目id';

comment on column BDC_SL_SFXX_CZRZ.CZR
  is '操作人';

comment on column BDC_SL_SFXX_CZRZ.SLBH
  is '受理编号';

comment on column BDC_SL_SFXX_CZRZ.XGJFYY
  is '修改缴费原因';

comment on column BDC_SL_SFXX_CZRZ.QXJFYY
  is '取消缴费原因';

comment on column BDC_SL_SFXX_CZRZ.XGJFR
  is '修改缴费人';

comment on column BDC_SL_SFXX_CZRZ.XGJFRID
  is '修改缴费人id';

comment on column BDC_SL_SFXX_CZRZ.XGJFSJ
  is '修改缴费时间';

comment on column BDC_SL_SFXX_CZRZ.SFSJ
  is '收费时间';

comment on column BDC_SL_SFXX_CZRZ.QXSFSJ
  is '取消收费时间';

alter table BDC_SL_SFXX_CZRZ
  add constraint BDC_SL_SFXX_CZRZ primary key (ID)
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


