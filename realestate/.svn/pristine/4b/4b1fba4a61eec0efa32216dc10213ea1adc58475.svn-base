--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_SL_ZJJG:1 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZJJG
(
  zjjgid   VARCHAR2(32) not null,
  gzlslid  VARCHAR2(32),
  xzqdm VARCHAR2(9),
  cqzh VARCHAR2(500),
  fwzl VARCHAR2(500),
  zjjgqrxx VARCHAR2(100)
)
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table BDC_SL_ZJJG
  is '不动产资金监管';
-- Add comments to the columns
comment on column BDC_SL_ZJJG.zjjgid
  is '资金监管ID';
comment on column BDC_SL_ZJJG.gzlslid
  is '工作流示例ID';
comment on column BDC_SL_ZJJG.xzqdm
  is '行政区代码';
  comment on column BDC_SL_ZJJG.cqzh
  is '产权证号';
  comment on column BDC_SL_ZJJG.fwzl
  is '房屋坐落';
comment on column BDC_SL_ZJJG.zjjgqrxx
    is '资金监管确认信息';
-- Create/Recreate primary, unique and foreign key constraints
alter table BDC_SL_ZJJG
    add constraint PK_BDC_SL_ZJJG primary key (ZJJGID)
        using index
            pctfree 10
            initrans 2
            maxtrans 255;

--changeset BDC_SL_ZJJG:2 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_ZJJG
    ADD (cqxmid VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_ZJJG.cqxmid IS '产权项目id';

--changeset BDC_SL_ZJJG:3 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_ZJJG ADD (zt NUMBER(1));
COMMENT ON COLUMN BDC_SL_ZJJG.zt IS '状态';
ALTER TABLE BDC_SL_ZJJG ADD (cxyy VARCHAR2(500));
COMMENT ON COLUMN BDC_SL_ZJJG.cxyy IS '撤销原因';

--changeset BDC_SL_ZJJG:4 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_ZJJG ADD (xmid VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_ZJJG.xmid IS '项目id';