--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_JOB_GROUP:1 failOnError:false runOnChange:true runAlways:false
create table BDC_JOB_GROUP
(
    ID NUMBER(32) not null,
    APPNAME VARCHAR2(2000),
    TITLE VARCHAR2(2000),
    ADDRESSTYPE NUMBER(2),
    ADDRESSLIST VARCHAR2(2000),
    UPDATETIME DATE

);

