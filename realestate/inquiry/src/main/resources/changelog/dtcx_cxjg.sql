--liquibase formatted sql
--preconditions dbms:oracle
--changeset DTCX_CXJG:1 failOnError:false runOnChange:true runAlways:false
create table DTCX_CXJG
(
  JGID     VARCHAR2(32) not null,
  CXID     VARCHAR2(32) not null,
  CXDH     VARCHAR2(32) not null,
  JGZDID   VARCHAR2(20) not null,
  JGZDNAME VARCHAR2(50) not null,
  MRXS     VARCHAR2(1) not null,
  JGTYPE   VARCHAR2(20) not null,
  ZDID     VARCHAR2(30),
  URL      VARCHAR2(200),
  LK       VARCHAR2(10),
  JS       VARCHAR2(200),
  DQFS     VARCHAR2(20),
  DW       VARCHAR2(20),
  PRIORITY NUMBER,
  QXDM     VARCHAR2(100),
  SORTABLE NUMBER
);

--changeset DTCX_CXJG:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CXJG add(FXKPD VARCHAR2(1000));
COMMENT ON COLUMN DTCX_CXJG.FXKPD IS '复选框判断';
