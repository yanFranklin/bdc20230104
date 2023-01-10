--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_JOB_REGISTRY:1 failOnError:false runOnChange:true runAlways:false
create table BDC_JOB_REGISTRY
(
    ID NUMBER(32) not null,

    REGISTRYGROUP VARCHAR2(2000),
    REGISTRYKEY VARCHAR2(2000),
    REGISTRYVALUE VARCHAR2(2000),
    UPDATETIME DATE
);

