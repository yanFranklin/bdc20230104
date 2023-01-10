--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_JOB_LOG_REPORT:1 failOnError:false runOnChange:true runAlways:false
create table BDC_JOB_LOG_REPORT
(
    ID NUMBER(32) not null,

    TRIGGERDAY DATE,
    RUNNINGCOUNT NUMBER(32),
    SUCCOUNT NUMBER(32),
    FAILCOUNT NUMBER(32)


);

