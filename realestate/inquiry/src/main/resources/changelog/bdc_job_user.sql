--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_JOB_USER:1 failOnError:false runOnChange:true runAlways:false
create table BDC_JOB_USER
(
    ID NUMBER(32) not null,
    USERNAME VARCHAR2(2000),
    PASSWORD VARCHAR2(2000),
    ROLE NUMBER(2),
    PERMISSION VARCHAR2(2000)

);

