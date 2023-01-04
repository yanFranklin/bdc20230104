--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_CZ_DZQZ_ZTXX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_CZ_DZQZ_ZTXX
(
    ZSID  VARCHAR2(32) not null,
    BDCQZH VARCHAR2(100) not null,
    TSSJ DATE,
    TSRY VARCHAR2(50),
    TSZT NUMBER(2),
    RZXX VARCHAR2(2000)
);

--changeset BDC_CZ_DZQZ_ZTXX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_CZ_DZQZ_ZTXX  is '常州电子签章状态信息表';
comment on column BDC_CZ_DZQZ_ZTXX.ZSID is '证书id';
comment on column BDC_CZ_DZQZ_ZTXX.BDCQZH is '不动产权证号';
comment on column BDC_CZ_DZQZ_ZTXX.TSSJ is '推送时间';
comment on column BDC_CZ_DZQZ_ZTXX.TSRY is '推送人员';
comment on column BDC_CZ_DZQZ_ZTXX.TSZT is '推送状态：0（或空）未推送签章，1 推送失败，2 已推送未下载，3 已推送下载失败，4 已推送已下载';
comment on column BDC_CZ_DZQZ_ZTXX.RZXX is '日志信息';