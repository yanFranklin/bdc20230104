--liquibase formatted sql
--preconditions dbms:oracle
--changeset DTCX_CXTJ:1 failOnError:false runOnChange:true runAlways:false
create table DTCX_CXTJ
(
  TJID     VARCHAR2(32) not null,
  CXID     VARCHAR2(32) not null,
  CXDH     VARCHAR2(40) not null,
  TJZDID   VARCHAR2(20),
  TJZDNAME VARCHAR2(50),
  MRXS     VARCHAR2(1),
  TJTYPE   VARCHAR2(20),
  TJUSAGE  VARCHAR2(20),
  ZDID     VARCHAR2(30),
  ZDDYFS   VARCHAR2(200),
  PRIORITY NUMBER
);

--changeset DTCX_CXTJ:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CXTJ add(SORTABLE NUMBER(1));
COMMENT ON COLUMN DTCX_CXTJ.SORTABLE IS '是否可排序 0否 1是';

--changeset DTCX_CXTJ:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CXTJ add(CANMHCX NUMBER(1));
COMMENT ON COLUMN DTCX_CXTJ.CANMHCX IS '是否支持模糊 精确查询相互切换';

--changeset DTCX_CXTJ:4 failOnError:false runOnChange:true runAlways:false
update dtcx_cxtj t set t.canmhcx = '1',t.zddyfs = replace(t.zddyfs,'like','#{=}') where t.canmhcx != '0';

--changeset DTCX_CXTJ:5 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CXTJ add(QXDM VARCHAR2(100));

--changeset DTCX_CXTJ:6 failOnError:false runOnChange:true runAlways:false
ALTER TABLE DTCX_CXTJ add(MRMHLX NUMBER(1));
COMMENT ON COLUMN DTCX_CXTJ.MRMHLX IS '默认模糊类型';
