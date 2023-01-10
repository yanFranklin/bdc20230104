--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_JOB_LOCK:1 failOnError:false runOnChange:true runAlways:false
create table BDC_JOB_LOCK
(
    LOCK_NAME NUMBER(32)  not null


);

