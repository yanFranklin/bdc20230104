--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_ZJJGXY:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZJJGXY
(
    JGXYBH VARCHAR2(32) not null primary key,
    HTBH   VARCHAR2(32),
    HTBAH  VARCHAR2(50),
    YCQZH  VARCHAR2(500),
    FWZL   VARCHAR2(500),
    CMR    VARCHAR2(200),
    SMR    VARCHAR2(200),
    CJMJ   NUMBER(32),
    CJJE   NUMBER(32),
    JGJE   NUMBER(32),
    JBR    VARCHAR2(500),
    DZBZPZ VARCHAR2(32),
    DKZT   VARCHAR2(32),
    SCSJ   DATE
);
comment on table BDC_SL_ZJJGXY is '资金监管监管协议对象';
comment on column BDC_SL_ZJJGXY.JGXYBH is '监管协议编号';
comment on column BDC_SL_ZJJGXY.HTBH is '合同编号';
comment on column BDC_SL_ZJJGXY.HTBAH is '合同备案编号';
comment on column BDC_SL_ZJJGXY.YCQZH is '原产权证号';
comment on column BDC_SL_ZJJGXY.FWZL is '房屋坐落';
comment on column BDC_SL_ZJJGXY.CMR is '出卖人名称';
comment on column BDC_SL_ZJJGXY.SMR is '买受人名称';
comment on column BDC_SL_ZJJGXY.CJMJ is '成交面积';
comment on column BDC_SL_ZJJGXY.CJJE is '成交金额';
comment on column BDC_SL_ZJJGXY.JGJE is '监管金额';
comment on column BDC_SL_ZJJGXY.JBR is '经办人';
comment on column BDC_SL_ZJJGXY.DZBZPZ is '电子办证凭证';
comment on column BDC_SL_ZJJGXY.DKZT is '贷款状态';
comment on column BDC_SL_ZJJGXY.SCSJ is '生成时间';

create table BDC_SL_ZJJGXYJC
(
    JCMXID VARCHAR2(32) not null primary key,
    JGXYBH VARCHAR2(32),
    CKYH   VARCHAR2(32),
    YJYE   VARCHAR2(32),
    ZJXZ   VARCHAR2(32),
    ZJYE   VARCHAR2(32),
    JRRQ   DATE
);
comment on table BDC_SL_ZJJGXYJC is '资金监管监管协议对象缴存明细';
comment on column BDC_SL_ZJJGXYJC.JCMXID is '缴存明细Id';
comment on column BDC_SL_ZJJGXYJC.JGXYBH is '监管协议编号';
comment on column BDC_SL_ZJJGXYJC.CKYH is '存款银行';
comment on column BDC_SL_ZJJGXYJC.YJYE is '存款金额';
comment on column BDC_SL_ZJJGXYJC.ZJXZ is '资金性质';
comment on column BDC_SL_ZJJGXYJC.ZJYE is '资金余额';
comment on column BDC_SL_ZJJGXYJC.JRRQ is '交易日期';