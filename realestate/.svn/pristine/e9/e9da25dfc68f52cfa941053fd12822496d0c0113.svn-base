--liquibase formatted sql
--preconditions dbms:oracle
--changeset DTCX_CX:1 failOnError:false runOnChange:true runAlways:false
create table DTCX_CX
(
  CXID     VARCHAR(32) not null PRIMARY KEY,
  CXMC     VARCHAR(100) ,
  CXDH     VARCHAR(40) ,
  CXSQL   VARCHAR2(4000),
  CJR      VARCHAR(20) ,
  CJSJ     DATE ,
  BGR      VARCHAR(20),
  BGSJ     DATE,
  BZ       VARCHAR(200),
  DQZT     VARCHAR(2),
  SFYXSC VARCHAR(1),
  CXFS VARCHAR(1),
  URL VARCHAR(200),
  YMGJ VARCHAR2(2000),
  HGJ VARCHAR2(2000)
);
comment on column DTCX_CX.CXID
  is '主键查询id';
comment on column DTCX_CX.CXMC
  is '查询名称';
comment on column DTCX_CX.CXDH
  is '查询代号';
comment on column DTCX_CX.CXSQL
  is '查询SQL';
comment on column DTCX_CX.CJR
  is '创建人';
comment on column DTCX_CX.CJSJ
  is '创建时间';
comment on column DTCX_CX.BGR
  is '变更人';
comment on column DTCX_CX.BGSJ
  is '变更时间';
comment on column DTCX_CX.BZ
  is '备注';
comment on column DTCX_CX.DQZT
  is '当前状态 0：已禁用；1：已启用';
comment on column DTCX_CX.SFYXSC
  is '是否允许删除';
  comment on column DTCX_CX.CXFS
  is '查询方式';
  comment on column DTCX_CX.YMGJ
  is '页面工具';
  comment on column DTCX_CX.HGJ
  is '行工具';
  comment on column DTCX_CX.URL
  is '查询地址url';

--changeset DTCX_CX:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(CANMHCX NUMBER(1));
COMMENT ON COLUMN DTCX_CX.CANMHCX IS '是否支持模糊，精确查询相互切换 0否 1是';

--changeset DTCX_CX:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(JS VARCHAR2(4000));
COMMENT ON COLUMN DTCX_CX.JS IS '动态加载js';

--changeset DTCX_CX:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(ZDYYMGJ VARCHAR2(4000));
COMMENT ON COLUMN DTCX_CX.ZDYYMGJ IS '自定义页面工具';

--changeset DTCX_CX:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(FXKYS VARCHAR2(1000));
COMMENT ON COLUMN DTCX_CX.FXKYS IS '复选框颜色';

--changeset DTCX_CX:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(SFSC VARCHAR2(1));
ALTER TABLE DTCX_CX add(QXDM VARCHAR2(100));

--changeset DTCX_CX:7 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(SFJKCX VARCHAR2(1));
COMMENT ON COLUMN DTCX_CX.SFJKCX IS '是否接口查询';
ALTER TABLE DTCX_CX add(JKFF VARCHAR2(100));
COMMENT ON COLUMN DTCX_CX.JKFF IS '接口方法';

--changeset DTCX_CX:8 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(JK VARCHAR2(100));
COMMENT ON COLUMN DTCX_CX.JK IS '调用接口';
ALTER TABLE DTCX_CX add(FHZKEY VARCHAR2(100));
COMMENT ON COLUMN DTCX_CX.FHZKEY IS '返回值key';

--changeset DTCX_CX:9 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(RZLX VARCHAR2(100));
COMMENT ON COLUMN DTCX_CX.RZLX IS '日志类型';
ALTER TABLE DTCX_CX add(RZMC VARCHAR2(100));
COMMENT ON COLUMN DTCX_CX.RZMC IS '日志名称';

--changeset DTCX_CX:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CX add(PZJS clob);
COMMENT ON COLUMN DTCX_CX.PZJS IS '配置js';

