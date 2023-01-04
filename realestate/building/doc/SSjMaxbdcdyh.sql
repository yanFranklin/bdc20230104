---------------------------------------------
-- Export file for user BDCSJGL            --
-- Created by gtis on 2018/12/10, 15:39:03 --
---------------------------------------------

spool table.log

prompt
prompt Creating table S_SJ_MAXBDCDYH
prompt =============================
prompt
create table BDCSJGL.S_SJ_MAXBDCDYH
(
  MAXBDCDYH_INDEX NVARCHAR2(38) not null,
  ZRZH            NVARCHAR2(4) not null,
  MAXLSH          NUMBER not null,
  VERSION         NUMBER not null,
  ZDZHDM          NVARCHAR2(19)
)
tablespace BDCSJGL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column BDCSJGL.S_SJ_MAXBDCDYH.MAXBDCDYH_INDEX
  is '主键';
comment on column BDCSJGL.S_SJ_MAXBDCDYH.ZRZH
  is '自然幢号';
comment on column BDCSJGL.S_SJ_MAXBDCDYH.MAXLSH
  is '最大流水号';
comment on column BDCSJGL.S_SJ_MAXBDCDYH.VERSION
  is '乐观锁版本号';
comment on column BDCSJGL.S_SJ_MAXBDCDYH.ZDZHDM
  is '宗地宗海代码';
alter table BDCSJGL.S_SJ_MAXBDCDYH
  add constraint S_SJ_MAXBDCDYH_MAXBDCDYH_INDEX primary key (MAXBDCDYH_INDEX)
  using index 
  tablespace BDCSJGL
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


spool off
