--liquibase formatted sql
--preconditions dbms:oracle
--changeset bdc_sl_zd:1 failOnError:false runOnChange:true runAlways:false

create table BDC_SL_ZD_CX
(
  DM VARCHAR2(10),
  MC VARCHAR2(20)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_ZD_CX
  is '朝向字典表';
comment on column BDC_SL_ZD_CX.DM
  is '代码';
comment on column BDC_SL_ZD_CX.MC
  is '名称';

create table BDC_SL_ZD_FWTC
(
  DM NUMBER(1) ,
  MC VARCHAR2(20)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_ZD_FWTC
  is '房屋套次字典表';
comment on column BDC_SL_ZD_FWTC.DM
  is '代码';
comment on column BDC_SL_ZD_FWTC.MC
  is '名称';

create table BDC_SL_ZD_JYLX
(
  DM VARCHAR2(10),
  MC VARCHAR2(20)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_ZD_JYLX
  is '交易类型字典表';
comment on column BDC_SL_ZD_JYLX.DM
  is '代码';
comment on column BDC_SL_ZD_JYLX.MC
  is '名称';

create table BDC_SL_ZD_QSZYFS
(
  DM NUMBER(3),
  MC VARCHAR2(20)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_ZD_QSZYFS
  is '权属转移方式字典表';
comment on column BDC_SL_ZD_QSZYFS.DM
  is '代码';
comment on column BDC_SL_ZD_QSZYFS.MC
  is '名称';

create table BDC_SL_ZD_SWZL
(
  DM VARCHAR2(10),
  MC VARCHAR2(20)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_ZD_SWZL
  is '税务种类字典表';
comment on column BDC_SL_ZD_SWZL.DM
  is '代码';
comment on column BDC_SL_ZD_SWZL.MC
  is '名称';

create table BDC_SL_ZD_QSZYYT
(
  DM VARCHAR2(10),
  MC VARCHAR2(20)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_ZD_QSZYYT
  is '权属转移用途字典表';
comment on column BDC_SL_ZD_SWZL.DM
  is '代码';
comment on column BDC_SL_ZD_SWZL.MC
  is '名称';

create table BDC_SL_ZD_QSZYDX
(
  DM VARCHAR2(10),
  MC VARCHAR2(20)
)
tablespace BDCSL
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table BDC_SL_ZD_QSZYDX
  is '权属转移对象字典表';
comment on column BDC_SL_ZD_SWZL.DM
  is '代码';
comment on column BDC_SL_ZD_SWZL.MC
  is '名称';

--changeset bdc_sl_zd:2 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_FKFS
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_FKFS primary key(DM)
);
INSERT INTO BDC_SL_ZD_FKFS VALUES ('1', '一次性付款');

--changeset bdc_sl_zd:3 failOnError:false runOnChange:true runAlways:false
INSERT INTO BDC_SL_ZD_FKFS VALUES ('2', '分期付款');
INSERT INTO BDC_SL_ZD_FKFS VALUES ('3', '按揭贷款');
INSERT INTO BDC_SL_ZD_FKFS VALUES ('4', '公积金贷款');
INSERT INTO BDC_SL_ZD_FKFS VALUES ('5', '商业与公积金组合贷款');
INSERT INTO BDC_SL_ZD_FKFS VALUES ('6', '其他');
INSERT INTO BDC_SL_ZD_FKFS VALUES ('7', '公积金支取');

--changeset bdc_sl_zd:4 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_KPFS
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_KPFS primary key(DM)
);
 INSERT INTO BDC_SL_ZD_KPFS VALUES ('1', '电脑打印');
 create table BDC_SL_ZD_JKFS
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_JKFS primary key(DM)
);
 INSERT INTO BDC_SL_ZD_JKFS VALUES ('1', '转账');

--changeset bdc_sl_zd:5 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_YSQRGX
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_YSQRGX primary key(DM)
);
 INSERT INTO BDC_SL_ZD_YSQRGX VALUES ('01', '配偶');
 INSERT INTO BDC_SL_ZD_YSQRGX VALUES ('02', '未成年子女');
 INSERT INTO BDC_SL_ZD_YSQRGX VALUES ('03', '父母');
 INSERT INTO BDC_SL_ZD_YSQRGX VALUES ('04', '未成年兄妹');

--changeset bdc_sl_zd:6 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_SFJSFF
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_SFJSFF primary key(DM)
);
comment on table BDC_SL_ZD_SFJSFF is '收费项目计算方法字典表';
 INSERT INTO BDC_SL_ZD_SFJSFF VALUES ('1', '取住宅面积和');
 INSERT INTO BDC_SL_ZD_SFJSFF VALUES ('2', '取非住宅面积和');
 INSERT INTO BDC_SL_ZD_SFJSFF VALUES ('3', '取总和值');

--changeset bdc_sl_zd:7 failOnError:false runOnChange:true runAlways:false
 alter table BDC_SL_ZD_SFJSFF modify MC varchar(200);
 INSERT INTO BDC_SL_ZD_SFJSFF VALUES ('4', '取最终出证数量');
 INSERT INTO BDC_SL_ZD_SFJSFF VALUES ('5', '取最终出证数量减第一本');

--changeset bdc_sl_zd:8 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_JDDM
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_JDDM primary key(DM)
);
comment on table BDC_SL_ZD_JDDM is '街道代码字典表';

--changeset bdc_sl_zd:9 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_BMYYY
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_BMYYY primary key(DM)
);
comment on table BDC_SL_ZD_BMYYY is '评价器不满意原因字典表';
 INSERT INTO BDC_SL_ZD_BMYYY(DM, MC) VALUES ('1', '办理时间长');
 INSERT INTO BDC_SL_ZD_BMYYY(DM, MC)VALUES ('2', '服务态度差');
 INSERT INTO BDC_SL_ZD_BMYYY(DM, MC) VALUES ('3', '办理环境差');
 INSERT INTO BDC_SL_ZD_BMYYY(DM, MC) VALUES ('4', '流程太复杂');
 INSERT INTO BDC_SL_ZD_BMYYY(DM, MC) VALUES ('5', '等待时间长');
 INSERT INTO BDC_SL_ZD_BMYYY(DM, MC) VALUES ('6', '其他');

--changeset bdc_sl_zd:10 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_ZD_JDDM modify MC VARCHAR2(100);

--changeset bdc_sl_zd:11 failOnError:false runOnChange:true runAlways:false
INSERT INTO BDC_SL_ZD_JKFS VALUES ('2', '线上缴费');

--changeset bdc_sl_zd:12 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_GXBS
(
  DM NUMBER(1) not null,
  MC VARCHAR2(20)
)
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
--changeset bdc_sl_zd:13 failOnError:false runOnChange:true runAlways:false
INSERT INTO BDC_SL_ZD_GXBS (DM, MC) VALUES (0, '非共享');
--changeset bdc_sl_zd:14 failOnError:false runOnChange:true runAlways:false
INSERT INTO BDC_SL_ZD_GXBS (DM, MC) VALUES (1, '共享');

--changeset bdc_sl_zd:15 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_HYZK
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_HYZK primary key(DM)
);
comment on table BDC_SL_ZD_HYZK is '婚姻状况字典表';
 INSERT INTO BDC_SL_ZD_HYZK(DM, MC) VALUES ('1', '已婚');
 INSERT INTO BDC_SL_ZD_HYZK(DM, MC) VALUES ('2', '未婚');

--changeset bdc_sl_zd:16 failOnError:false runOnChange:true runAlways:false
 create table BDC_SL_ZD_FZZZZSJS
(
  DM VARCHAR2(10),
  MC VARCHAR2(20),
  constraint BDC_SL_ZD_FZZZZSJS primary key(DM)
);
comment on table BDC_SL_ZD_FZZZZSJS is '非住宅增值税计税字典表';
INSERT INTO BDC_SL_ZD_FZZZZSJS(DM, MC) VALUES ('0', '重置无契税票');
INSERT INTO BDC_SL_ZD_FZZZZSJS(DM, MC) VALUES ('1', '提供原购房发票');
INSERT INTO BDC_SL_ZD_FZZZZSJS(DM, MC) VALUES ('2', '重置有契税票');

--changeset bdc_sl_zd:17 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_GHYTXL
(
    DM VARCHAR2(20),
    MC VARCHAR2(20),
    constraint BDC_SL_ZD_GHYTXL primary key(DM)
);
comment on table BDC_SL_ZD_GHYTXL is '规划用途细类字典表';
INSERT INTO BDC_SL_ZD_GHYTXL(DM, MC) VALUES ('ZZ000001', '独门独户院落');
INSERT INTO BDC_SL_ZD_GHYTXL(DM, MC) VALUES ('ZZ000002', '低层住宅');
INSERT INTO BDC_SL_ZD_GHYTXL(DM, MC) VALUES ('ZZ000003', '多层住宅');

--changeset bdc_sl_zd:18 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_XQXX
(
    DM VARCHAR2(32),
    MC VARCHAR2(32)
);
comment on table BDC_SL_ZD_XQXX is '小区名称字典表';
INSERT INTO BDC_SL_ZD_XQXX(DM, MC) VALUES ('1001', '测试小区1');
INSERT INTO BDC_SL_ZD_XQXX(DM, MC) VALUES ('1002', '测试小区2');

--changeset bdc_sl_zd:19 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_XZTZCXTJ
(
    DM VARCHAR2(32),
    MC VARCHAR2(32),
    TZLX VARCHAR2(1)
);
comment on table BDC_SL_ZD_XZTZCXTJ is '受理选择台账查询条件字典表';

--changeset bdc_sl_zd:20 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_HTZT
(
    DM VARCHAR2(2),
    MC VARCHAR2(50),
     constraint BDC_SL_ZD_HTZT primary key(DM)
);
comment on table BDC_SL_ZD_HTZT is '合同状态字典表';
INSERT INTO BDC_SL_ZD_HTZT(DM, MC) VALUES ('1', '非网签');
INSERT INTO BDC_SL_ZD_HTZT(DM, MC) VALUES ('2', '待网签');
INSERT INTO BDC_SL_ZD_HTZT(DM, MC) VALUES ('3', '已网签未备案');
INSERT INTO BDC_SL_ZD_HTZT(DM, MC) VALUES ('4', '已备案');
INSERT INTO BDC_SL_ZD_HTZT(DM, MC) VALUES ('5', '备案失败');


--changeset bdc_sl_zd:21 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_JYFS
(
    DM VARCHAR2(2),
    MC VARCHAR2(50),
     constraint BDC_SL_ZD_JYFS primary key(DM)
);
comment on table BDC_SL_ZD_JYFS is '交易方式字典表';
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('01', '买卖');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('02', '直系亲属赠予');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('03', '法定继承');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('04', '抵偿债务');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('05', '赞助');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('06', '职工福利');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('07', '奖励');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('08', '对外投资');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('09', '分配给股东或其他投资人');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('10', '换取其他非货币性资产');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('11', '析产');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('12', '其他');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('13', '夫妻加名(夫妻赠与)');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('14', '非直系亲属赠与');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('15', '遗赠');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('16', '兄弟姐妹赠与');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('17', '非法定继承');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('18', '法律文书（政府认定/判决/竞价/拍卖）');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('19', '房屋交换');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('20', '房改房');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('21', '拆迁安置补偿');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('22', '买卖（所占份额内转让）');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('23', '土地契税（购买土地）');
  INSERT INTO BDC_SL_ZD_JYFS VALUES ('24', '直系亲属买卖');

--changeset bdc_sl_zd:22 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_ZD_XQXX  ADD(JDDM VARCHAR2(32));
COMMENT ON COLUMN BDC_SL_ZD_XQXX.JDDM IS '街道代码';

--changeset bdc_sl_zd:23 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_SQBM
(
    DM VARCHAR2(32),
    MC VARCHAR2(32)
);
comment
on table BDC_SL_ZD_SQBM is '收件材料收取部门字典表';
INSERT INTO BDC_SL_ZD_SQBM(DM, MC)
VALUES ('1', '登记');
INSERT INTO BDC_SL_ZD_SQBM(DM, MC)
VALUES ('2', '税务');
INSERT INTO BDC_SL_ZD_SQBM(DM, MC)
VALUES ('3', '登记税务');

--changeset bdc_sl_zd:24 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_CDLB
(
    DM VARCHAR2(32),
    MC VARCHAR2(32)
);
comment
on table BDC_SL_ZD_CDLB is '查档类别字典表';
INSERT INTO BDC_SL_ZD_CDLB(DM, MC)
VALUES ('1', '查询有结果');
INSERT INTO BDC_SL_ZD_CDLB(DM, MC)
VALUES ('2', '查询无结果');
INSERT INTO BDC_SL_ZD_CDLB(DM, MC)
VALUES ('3', '不予受理');

--changeset bdc_sl_zd:25 failOnError:false runOnChange:true runAlways:false
INSERT into BDC_SL_ZD_XZTZCXTJ (DM,MC,TZLX) values ('bdcqzh','不动产权证（明）号','8');
INSERT into BDC_SL_ZD_XZTZCXTJ (DM,MC,TZLX) values ('zl','坐落','8');
INSERT into BDC_SL_ZD_XZTZCXTJ (DM,MC,TZLX) values ('qlrmc','权利人','8');
INSERT into BDC_SL_ZD_XZTZCXTJ (DM,MC,TZLX) values ('qlrzjh','权利人证件号','8');
INSERT into BDC_SL_ZD_XZTZCXTJ (DM,MC,TZLX) values ('bdcqzhjc','证号简称','8');

--changeset bdc_sl_zd:26 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_TDCRJZSXM
(
    DM VARCHAR2(100),
    MC VARCHAR2(100)
);
comment on table BDC_SL_ZD_TDCRJZSXM is '土地出让金征收项目';

create table BDC_SL_ZD_TDCRJZSPM
(
    DM VARCHAR2(100),
    MC VARCHAR2(100)
);
comment on table BDC_SL_ZD_TDCRJZSPM is '土地出让金征收品目';

create table BDC_SL_ZD_TDCRJZSBL
(
    DM VARCHAR2(100),
    MC VARCHAR2(100)
);
comment on table BDC_SL_ZD_TDCRJZSBL is '土地出让金征收比例';

create table BDC_SL_ZD_TDCRJJFRLB
(
    DM VARCHAR2(100),
    MC VARCHAR2(100)
);
comment on table BDC_SL_ZD_TDCRJJFRLB is '土地出让金缴费人类别';

--changeset bdc_sl_zd:27 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_JMYY
(
    DM VARCHAR2(100),
    MC VARCHAR2(100)
);
comment on table BDC_SL_ZD_JMYY is '减免原因';

--changeset bdc_sl_zd:28 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_GGLX
(
    DM VARCHAR2(100),
    MC VARCHAR2(100),
    JC VARCHAR2(20)
);
comment on table BDC_SL_ZD_GGLX is '公告类型';
comment on column BDC_SL_ZD_GGLX.JC is '简称';
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('1','不动产首次登记公告','bdcscdjgg');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('2','不动产继承/受遗赠登记公告','bdcjcyzgg');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('3','无权利义务承继人不动产转移登记公告（一）','zydjgg1');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('4','无权利义务承继人不动产转移登记公告（二）','zydjgg2');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('5','不动产更正登记公告','bdcgzdjgg');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('6','不动产注销登记公告','bdczxdjgg');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('7','不动产撤销登记公告','bdccxdjgg');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('8','不动产权证书/登记证明作废公告','bdczszmzfgg');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('9','不动产权证书/登记证明遗失（灭失）声明','bdczszmysgg');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('10','征询异议公告','zxyygg');
INSERT into BDC_SL_ZD_GGLX (DM,MC,JC) values ('11','范围注销公告','fwzxgg');

--changeset bdc_sl_zd:29 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_MFFWTC
(
  DM NUMBER(2) ,
  MC VARCHAR2(20)
);
comment on table BDC_SL_ZD_MFFWTC is '卖方房屋套次';


--changeset bdc_sl_zd:30 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_XZTZLX
(
    DM VARCHAR2(100),
    MC VARCHAR2(100)
);
comment on table BDC_SL_ZD_XZTZLX is '选择台账类型';

INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('1','不动产单元信息');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('2','产权证');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('3','查封');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('4','逻辑幢');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('5','外联证书');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('6','合同监管信息');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('7','证书锁定信息');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('8','查档');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('9','修正');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('10','单元锁定信息');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('11','批量预转现');
INSERT into BDC_SL_ZD_XZTZLX (DM,MC) values ('12','收费用途修正');


--changeset bdc_sl_zd:31 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_CSJZZLX
(
    DM VARCHAR2(100) not null,
    MC VARCHAR2(100) not null
);
comment on table BDC_SL_ZD_CSJZZLX is '长三角证照类型';
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('1','身份证(公安部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('2','结婚证(民政部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('3','出生医学证明(卫健部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('4','离婚证(民政部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('5','户口簿(公安部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('6','社会团体法人登记证书(民政部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('7','民办非企业单位登记证书(民政部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('8','基金法人登记证书(民政部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('9','中华人民共和国护照(公安部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('10','往来港澳通行证(公安部门)');
INSERT into BDC_SL_ZD_CSJZZLX (DM,MC) values ('11','营业执照(市场监管部门)');

--changeset bdc_sl_zd:32 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_CSJYWBM
(
    DM VARCHAR2(200) not null,
    MC VARCHAR2(200) not null
);
comment on table BDC_SL_ZD_CSJZZLX is '长三角业务编码';

create table BDC_SL_ZD_CSJZZMC
(
    DM VARCHAR2(100) not null,
    MC VARCHAR2(100) not null,
    ZZLY VARCHAR(2)
);
comment on table BDC_SL_ZD_CSJZZLX is '长三角业务证照名称';
comment on table BDC_SL_ZD_CSJZZLX.ZZLY is '证照来源1-本省2-标准';

--changeset bdc_sl_zd:33 failOnError:false runOnChange:true runAlways:false
ALTER TABLE BDC_SL_ZD_CSJZZMC ADD (ZZLX VARCHAR2(10));
COMMENT ON COLUMN BDC_SL_ZD_CSJZZMC.zzlx IS '证照类型';


--changeset bdc_sl_zd:34 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_SWSBZT
(
    DM NUMBER(2) not null,
    MC VARCHAR2(100) not null,
    LX VARCHAR2(4)
);
comment on table BDC_SL_ZD_SWSBZT is '税务申报状态';
COMMENT ON COLUMN BDC_SL_ZD_SWSBZT.LX IS '类型';

INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (0,'外部数据接收完成','1');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (1,'数据校检不通过','1');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (2,'婚姻比对不通过','1');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (3,'家庭成员比对不通过','1');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (4,'住房套次比对不通过','1');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (5,'完结审核通过','1');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (6,'申报失败','1');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (7,'申报信息确认单待打印','1');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (8,'申报成功','2');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (9,'申报放弃','2');
INSERT into BDC_SL_ZD_SWSBZT (DM,MC,LX) values (10,'缴款完成','3');

--changeset bdc_sl_zd:35 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_FDLYYJ
(
    DM NUMBER(2) not null,
    MC VARCHAR2(100) not null
);
comment on table BDC_SL_ZD_FDLYYJ is '不予受理的否定理由和依据';


INSERT into BDC_SL_ZD_FDLYYJ (DM,MC) values (0,'申请登记材料不齐全');
INSERT into BDC_SL_ZD_FDLYYJ (DM,MC) values (1,'申请登记的不动产不属于本机构登记管辖范围');
INSERT into BDC_SL_ZD_FDLYYJ (DM,MC) values (2,'申请登记材料不符合法定形式');
INSERT into BDC_SL_ZD_FDLYYJ (DM,MC) values (3,'不符合法律法规规定的其他情形');

--changeset bdc_sl_zd:36 failOnError:false runOnChange:true runAlways:false
create table BDC_DJ_ZD_FDLYYJ
(
    DM NUMBER(2) not null,
    MC VARCHAR2(100) not null
);
comment on table BDC_DJ_ZD_FDLYYJ is '不予受理的否定理由和依据';


INSERT into BDC_DJ_ZD_FDLYYJ (DM,MC) values (0,'违反法律、行政法规规定');
INSERT into BDC_DJ_ZD_FDLYYJ (DM,MC) values (1,'存在尚未解决的权属争议');
INSERT into BDC_DJ_ZD_FDLYYJ (DM,MC) values (2,'申请登记的不动产权利超过规定期限');
INSERT into BDC_DJ_ZD_FDLYYJ (DM,MC) values (3,'法律、行政法规规定不予登记的其他情形');

--changeset bdc_sl_zd:37 failOnError:false runOnChange:true runAlways:false

create table BDC_ZD_CXJG
(
    DM NUMBER(2) not null,
    MC VARCHAR2(100) not null
);
comment on table BDC_ZD_CXJG is '告知单查询结果字典表';

INSERT into BDC_ZD_CXJG (DM,MC) values (0,'按申请人(代理人)提供的坐落或权属证书、登记证明无结果');
INSERT into BDC_ZD_CXJG (DM,MC) values (1,'要求查询的不动产尚未进行登记');
INSERT into BDC_ZD_CXJG (DM,MC) values (2,'要求查询的事项、资料在我中心不存在');
INSERT into BDC_ZD_CXJG (DM,MC) values (3,'在本单位所属登记范围内经查询无结果');
INSERT into BDC_ZD_CXJG (DM,MC) values (4,'在本单位所属登记范围内经查询无现势信息结果');

--changeset bdc_sl_zd:38 failOnError:false runOnChange:true runAlways:false
INSERT into BDC_SL_ZD_XZTZCXTJ (DM,MC,TZLX) values ('ghyt','规划用途','1');

--changeset bdc_sl_zd:39 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_DDZT
(
    DM VARCHAR2(100) not null,
    MC VARCHAR2(100) not null
);
comment on table BDC_SL_ZD_DDZT is '订单状态字典表';

INSERT into BDC_SL_ZD_DDZT (DM,MC) values ('1','待缴费');
INSERT into BDC_SL_ZD_DDZT (DM,MC) values ('2','成功');
INSERT into BDC_SL_ZD_DDZT (DM,MC) values ('3','失败');
INSERT into BDC_SL_ZD_DDZT (DM,MC) values ('4','全部退费');
INSERT into BDC_SL_ZD_DDZT (DM,MC) values ('5','部分退费');
INSERT into BDC_SL_ZD_DDZT (DM,MC) values ('6','失效');
INSERT into BDC_SL_ZD_DDZT (DM,MC) values ('9','取消');

--changeset bdc_sl_zd:40 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_GGTZ
(
    DM VARCHAR2(100) not null,
    MC VARCHAR2(100) not null
);
comment on table BDC_SL_ZD_GGTZ is '工改台账文件类型';

INSERT into BDC_SL_ZD_GGTZ (DM,MC) values ('xcjsgcjgysba','建设工程竣工验收备案');
INSERT into BDC_SL_ZD_GGTZ (DM,MC) values ('xcjsgcjgysbangs','建设工程竣工验收备案（宁国市）');
INSERT into BDC_SL_ZD_GGTZ (DM,MC) values ('xcjsgcjgysbaxzq','建设工程竣工验收备案（宣州区）');

--changeset bdc_sl_zd:41 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_GJ
(
    DM VARCHAR2(100) not null,
    MC VARCHAR2(100) not null
);
comment on table BDC_SL_ZD_GJ is '国籍字典项';

--changeset bdc_sl_zd:42 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_ZRFCSFGX
(
    DM VARCHAR2(10),
    MC VARCHAR2(100),
    constraint BDC_SL_ZD_ZRFCSFGX primary key (DM)
);
comment on table BDC_SL_ZD_ZRFCSFGX is '转让方承受方关系字典项';
INSERT INTO BDC_SL_ZD_ZRFCSFGX
VALUES ('1', '配偶、父母、子女、祖父母、外祖父母、孙子女、外孙子女');
INSERT INTO BDC_SL_ZD_ZRFCSFGX
VALUES ('2', '兄弟姐妹');
INSERT INTO BDC_SL_ZD_ZRFCSFGX
VALUES ('3', '承担直接抚养或者赡养义务的抚养人或赡养人');
INSERT INTO BDC_SL_ZD_ZRFCSFGX
VALUES ('4', '法定继承人、遗嘱继承人、受遗赠人');
INSERT INTO BDC_SL_ZD_ZRFCSFGX
VALUES ('9', '其他');

--changeset bdc_sl_zd:43 failOnError:false runOnChange:true runAlways:false
create table BDC_SL_ZD_CLJGCLLX
(
    DM Number(4),
    MC VARCHAR2(100),
    constraint BDC_SL_ZD_CLJGCLLX primary key (DM)
);
comment on table BDC_SL_ZD_CLJGCLLX is '测绘结果材料类型';
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (0, '测量报告');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (1, '规划信息');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (2, '竣工验收材料');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (3, '土地出让信息');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (4, '土地审批信息');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (5, '查看现场照片');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (6, '宗地图');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (11, '规划核实合格证');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (12, '规划许可证');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (21, '竣工验收备案表');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (31, '出让补充合同或变更合同');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (32, '出让合同及附件');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (33, '出让金缴纳凭证');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (34, '交地确认书');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (41, '划拨土地转让审批结果');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (42, '土地收益缴纳凭证');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (43, '闲置土地认定信息');
INSERT INTO BDC_SL_ZD_CLJGCLLX
VALUES (44, '用地划拨决定书');
