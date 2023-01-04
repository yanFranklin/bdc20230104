--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_sl_csjpz:1 failOnError:false runOnChange:true runAlways:false
-- Create table

create table BDC_SL_CSJPZ
(
    pzid    VARCHAR2(32) not null,
    gzldyid VARCHAR2(32) not null,
    lcmc     VARCHAR2(32),
    zzlx    VARCHAR2(10),
    ywbm    VARCHAR2(100),
    bszzmc  VARCHAR2(200),
    bzzzmc  VARCHAR2(200)
);
comment on table BDC_SL_CSJPZ is '不动产受理长三角配置表';
comment on column BDC_SL_CSJPZ.pzid is '主键配置id';
comment on column BDC_SL_CSJPZ.gzldyid is '工作流定义id';
comment on column BDC_SL_CSJPZ.lcmc is '流程名称';
comment on column BDC_SL_CSJPZ.zzlx is '证照类型';
comment on column BDC_SL_CSJPZ.ywbm is '业务编码';
comment on column BDC_SL_CSJPZ.bszzmc is '本省证照编码';
comment on column BDC_SL_CSJPZ.bzzzmc is '标准证照编码';
alter table BDC_SL_CSJPZ add primary key (PZID);
CREATE INDEX IDX_BDC_SL_CSJPZ_LCMC ON BDC_SL_CSJPZ (lcmc);
CREATE INDEX IDX_BDC_SL_CSJPZ_LCDYID ON BDC_SL_CSJPZ (gzldyid);

--changeset bdc_sl_csjpz:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_CSJPZ MODIFY ZZLX VARCHAR2(100);