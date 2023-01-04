-- liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_JRLW:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_JRLW
(
    lwid  VARCHAR2(32) NOT NULL,
    wxzid VARCHAR2(32) NOT NULL,
    lwyy  VARCHAR2(2000),
    lwsj  date,
    lwry  varchar2(50)
);
comment
on table BDC_SL_JRLW is '不动产受理接入例外表';
comment
on column BDC_SL_JRLW.LWID is '例外id';
comment
on column BDC_SL_JRLW.WXZID is '未销账id';
comment
on column BDC_SL_JRLW.LWYY is '例外原因';
comment
on column BDC_SL_JRLW.lwsj is '例外时间';
comment
on column BDC_SL_JRLW.lwry is '例外人员';