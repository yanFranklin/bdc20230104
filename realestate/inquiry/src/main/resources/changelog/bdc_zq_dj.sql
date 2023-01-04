--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZQ_DJ:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZQ_DJ
(
    DJXXID  VARCHAR2(32) not null,
    BDCDYH  VARCHAR2(28),
    BDCQZH  VARCHAR2(100),
    ZL      VARCHAR2(400),
    QLLX    NUMBER(1,0),
    DJLX    NUMBER(1,0),
    BDCLX   NUMBER(1,0),
    QLRMC   VARCHAR2(200),
    QLRZJH  VARCHAR2(100),
    LXDH    VARCHAR2(100),
    JZMJ    NUMBER(15,2),
    ZDZHMJ  NUMBER(20,2),
    DJYY    VARCHAR2(2000),
    DJSQR   VARCHAR2(200),
    DJSQRID VARCHAR2(32),
    DJSQSJ  DATE,
    DJWH    VARCHAR2(200),
    DJSJQ   DATE,
    DJSJZ   DATE,
    JDYY    VARCHAR2(2000),
    JDR     VARCHAR2(200),
    JDRID   VARCHAR2(32),
    JDSJ    DATE,
    BZ      VARCHAR2(2000)
);
--changeset BDC_ZQ_DJ:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_ZQ_DJ is '征迁冻结表';
-- Add comments to the columns
comment on column BDC_ZQ_DJ.DJXXID is '冻结信息ID';
comment on column BDC_ZQ_DJ.BDCDYH is '不动产单元号';
comment on column BDC_ZQ_DJ.BDCQZH is '不动产权证号';
comment on column BDC_ZQ_DJ.ZL is '坐落';
comment on column BDC_ZQ_DJ.QLLX is '权利类型';
comment on column BDC_ZQ_DJ.DJLX is '登记类型';
comment on column BDC_ZQ_DJ.BDCLX is '不动产类型';
comment on column BDC_ZQ_DJ.QLRMC is '权利人名称';
comment on column BDC_ZQ_DJ.QLRZJH is '权利人证件号';
comment on column BDC_ZQ_DJ.LXDH is '联系电话';
comment on column BDC_ZQ_DJ.JZMJ is '建筑面积';
comment on column BDC_ZQ_DJ.ZDZHMJ is '宗地宗海面积';
comment on column BDC_ZQ_DJ.DJYY is '冻结原因';
comment on column BDC_ZQ_DJ.DJSQR is '冻结申请人';
comment on column BDC_ZQ_DJ.DJSQRID is '冻结申请人ID';
comment on column BDC_ZQ_DJ.DJSQSJ is '冻结申请时间';
comment on column BDC_ZQ_DJ.DJWH is '冻结文号';
comment on column BDC_ZQ_DJ.DJSJQ is '冻结时间起';
comment on column BDC_ZQ_DJ.DJSJZ is '冻结时间止';
comment on column BDC_ZQ_DJ.JDYY is '解冻原因';
comment on column BDC_ZQ_DJ.JDR is '解冻人';
comment on column BDC_ZQ_DJ.JDRID is '解冻人ID';
comment on column BDC_ZQ_DJ.JDSJ is '解冻时间';
comment on column BDC_ZQ_DJ.BZ is '备注';

--changeset BDC_ZXYW_YWBM_REL:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_ZQ_DJ add(DJZT NUMBER(1,0));
COMMENT ON COLUMN BDC_ZQ_DJ.DJZT IS '冻结状态';


