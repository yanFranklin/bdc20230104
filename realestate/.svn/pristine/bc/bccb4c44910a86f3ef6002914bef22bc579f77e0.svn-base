--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_DYDZGX:1 failOnError:false runOnChange:true runAlways:false
create table BDC_DYDZGX
(
  FWXMID VARCHAR2(32) not null,
  YCXMID VARCHAR2(32) not null
);

--changeset BDC_DYDZGX:2 failOnError:false runOnChange:true runAlways:false
-- Add comments to the table
comment on table BDC_DYDZGX  is '不动产单元对照关系表';
comment on column BDC_DYDZGX.FWXMID is '房屋项目ID';
comment on column BDC_DYDZGX.YCXMID is '跃层项目ID';