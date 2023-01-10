--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_JOB_GLUE:1 failOnError:false runOnChange:true runAlways:false
create table BDC_JOB_GLUE
(
    ID NUMBER(32) not null,

    JOBID NUMBER(32),
    GLUETYPE VARCHAR2(2000),
    GLUESOURCE VARCHAR2(2000),
    GLUEREMARK VARCHAR2(2000),
    ADDTIME DATE,
    UPDATETIME DATE

);

