--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_YCSL_DJYW_DZB:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_YCSL_DJYW_DZB
   (
     ID VARCHAR2(32),
     ycslgzldyid   VARCHAR2(200),
     djgzldyid   VARCHAR2(200)

   );

-- Add comments to the table
comment on table BDC_SL_YCSL_DJYW_DZB
  is '不动产受理一窗受理与登记业务流程对照表';
-- Add comments to the columns
comment on column BDC_SL_YCSL_DJYW_DZB.id
  is '主键';
comment on column BDC_SL_YCSL_DJYW_DZB.ycslgzldyid
  is '一窗受理流程定义ID';
comment on column BDC_SL_YCSL_DJYW_DZB.djgzldyid
  is '登记流程定义ID';