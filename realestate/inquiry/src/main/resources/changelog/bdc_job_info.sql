--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_JOB_INFO:1 failOnError:false runOnChange:true runAlways:false
create table BDC_JOB_INFO
(
    ID NUMBER(32) not null,
    JOBGROUP NUMBER(32),
    JOBDESC VARCHAR2(2000),
    ADDTIME DATE,
    UPDATETIME DATE,
    ALARMEMAIL VARCHAR2(2000),
    AUTHOR VARCHAR2(2000),
    SCHEDULETYPE VARCHAR2(2000),
    SCHEDULECONF VARCHAR2(2000),
    MISFIRESTRATEGY VARCHAR2(2000),
    EXECUTORROUTESTRATEGY VARCHAR2(2000),
    EXECUTORHANDLER VARCHAR2(2000),
    EXECUTORPARAM VARCHAR2(2000),
    EXECUTORBLOCKSTRATEGY VARCHAR2(2000),
    EXECUTORTIMEOUT NUMBER(10),
    EXECUTORFAILRETRYCOUNT NUMBER(10),

    GLUETYPE  VARCHAR2(2000),
    GLUESOURCE VARCHAR2(2000),
    GLUEREMARK VARCHAR2(2000),
    GLUEUPDATETIME DATE,
    CHILDJOBID VARCHAR2(2000),

    TRIGGERSTATUS  NUMBER(32),
    TRIGGERLASTTIME  NUMBER(32),
    TRIGGERNEXTTIME NUMBER(32)
);

