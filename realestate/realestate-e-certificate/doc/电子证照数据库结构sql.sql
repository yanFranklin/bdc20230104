--电子证照字典表信息 2019/5/8 陈永强
create table BDC_DZZZ_CONFIG
(
  dwdm          VARCHAR2(100) not null,
  szsxqc        VARCHAR2(100) not null,
  zzqzr         VARCHAR2(100) not null,
  zzqzmc        VARCHAR2(100) not null,
  zzbfjgdm      VARCHAR2(50),
  containername VARCHAR2(50),
  accesscode    VARCHAR2(50)
);
comment on table BDC_DZZZ_CONFIG
  is '不动产电子证照系统配置表';
comment on column BDC_DZZZ_CONFIG.dwdm
  is '单位代码';
comment on column BDC_DZZZ_CONFIG.szsxqc
  is '所在市县全称';
comment on column BDC_DZZZ_CONFIG.zzqzr
  is '证照签章人';
comment on column BDC_DZZZ_CONFIG.zzqzmc
  is '证照签章名称';
comment on column BDC_DZZZ_CONFIG.zzbfjgdm
  is '证照颁发机构代码';
comment on column BDC_DZZZ_CONFIG.containername
  is '意源签章容器名';
comment on column BDC_DZZZ_CONFIG.accesscode
  is '意源签章访问码';
alter table BDC_DZZZ_CONFIG
  add constraint BDC_DZZZ_CONFIG_PK primary key (DWDM);


--电子证照加注件信息 2019/4/15 zhangyu
create table BDC_DZZZ_JZJXX
(
  jzjid       VARCHAR2(32) not null,
  zzid       VARCHAR2(32) not null,
  jzjzzsj    DATE,
  jzjzzz     VARCHAR2(50),
  jzjzzsy    VARCHAR2(100),
  jzjyxqjzsj DATE,
  zzwjlj     VARCHAR2(200),
  cjsj DATE,
  szzs VARCHAR2(2000)
);
comment on table BDC_DZZZ_JZJXX
  is '不动产电子证照加注件信息';
comment on column BDC_DZZZ_JZJXX.jzjid
  is '加注件id主键';
comment on column BDC_DZZZ_JZJXX.zzid
  is '证照id';
comment on column BDC_DZZZ_JZJXX.jzjzzsj
  is '加注件制作时间';
comment on column BDC_DZZZ_JZJXX.jzjzzz
  is '加注件制作者';
comment on column BDC_DZZZ_JZJXX.jzjzzsy
  is '加注件制作事由';
comment on column BDC_DZZZ_JZJXX.jzjyxqjzsj
  is '加注件有限期截止时间';
comment on column BDC_DZZZ_JZJXX.zzwjlj
  is '证照文件路径(存放路径)';
  comment on column BDC_DZZZ_JZJXX.cjsj
  is '创建时间';
comment on column BDC_DZZZ_JZJXX.szzs
  is '数字证书';
alter table BDC_DZZZ_JZJXX
  add constraint PK_BDC_DZZZ_JZJXX_ID primary key (jzjid);
  

  
--电子证照日志信息 2019/2/19 chenyongqiang
create table BDC_DZZZ_LOG
(
  logid        VARCHAR2(32) not null,
  username     VARCHAR2(50),
  userid       VARCHAR2(50),
  czrq         DATE,
  controller   VARCHAR2(500),
  parmjson     VARCHAR2(4000),
  ip           VARCHAR2(100),
  mac          VARCHAR2(100),
  computername VARCHAR2(100),
  reason       VARCHAR2(1000)
);
comment on table BDC_DZZZ_LOG
  is '不动产电子证照日志表';
comment on column BDC_DZZZ_LOG.logid
  is '主键';
comment on column BDC_DZZZ_LOG.username
  is '用户名称';
comment on column BDC_DZZZ_LOG.userid
  is '用户ID';
comment on column BDC_DZZZ_LOG.czrq
  is '操作日期';
comment on column BDC_DZZZ_LOG.controller
  is '操作调用方法（方法字典表）';
comment on column BDC_DZZZ_LOG.parmjson
  is '方法参数';
comment on column BDC_DZZZ_LOG.ip
  is 'IP';
comment on column BDC_DZZZ_LOG.mac
  is '机器码';
comment on column BDC_DZZZ_LOG.computername
  is '计算机名';
comment on column BDC_DZZZ_LOG.reason
  is '操作原因';
alter table BDC_DZZZ_LOG
  add constraint BDC_DZZZ_LOG_PRIMARY_LOGID primary key (LOGID);
 

--电子证照目录信息 2019/2/19 wenyuanwu
  create table BDC_DZZZ_MLXX
(
  MLID       VARCHAR2(32) not null,
  ZZID       VARCHAR2(32) not null,
  ZZMC       VARCHAR2(50),
  ZZLXDM     VARCHAR2(50),
  ZZBH       VARCHAR2(50),
  ZZBS       VARCHAR2(200),
  ZZBFJG     VARCHAR2(50),
  ZZBFJGDM   VARCHAR2(50),
  ZZBFRQ     DATE,
  CZZT       VARCHAR2(300),
  CZZTDM     VARCHAR2(300),
  CZZTDMLX   VARCHAR2(300),
  CZZTDMLXDM VARCHAR2(50),
  ZZYXQQSRQ  DATE,
  ZZYXQJZRQ  DATE,
  ZZQZR      VARCHAR2(50),
  ZZQZSJ     DATE,
  ZZQZMC     VARCHAR2(100),
  CJSJ       DATE,
  dwdm       VARCHAR2(9),
  bdcqzh     VARCHAR2(50),
  bdcdyh     VARCHAR2(50),
  zzwjlj     VARCHAR2(200),
  gdzt       NUMBER(1),
  ZSTYPE VARCHAR2(20)
);
comment on table BDC_DZZZ_MLXX
  is '不动产电子证照目录信息';
comment on column BDC_DZZZ_MLXX.MLID
  is '目录库id';
comment on column BDC_DZZZ_MLXX.ZZID
  is '证照库id';
comment on column BDC_DZZZ_MLXX.ZZMC
  is '证照名称';
comment on column BDC_DZZZ_MLXX.ZZLXDM
  is '证照类型代码';
comment on column BDC_DZZZ_MLXX.ZZBH
  is '证照编号';
comment on column BDC_DZZZ_MLXX.ZZBS
  is '证照标识';
comment on column BDC_DZZZ_MLXX.ZZBFJG
  is '证照颁发机构';
comment on column BDC_DZZZ_MLXX.ZZBFJGDM
  is '证照颁发机构代码';
comment on column BDC_DZZZ_MLXX.ZZBFRQ
  is '证照颁发日期';
comment on column BDC_DZZZ_MLXX.CZZT
  is '持证主体(持证人)';
comment on column BDC_DZZZ_MLXX.CZZTDM
  is '持证主体代码(持证者证件号码)';
comment on column BDC_DZZZ_MLXX.CZZTDMLX
  is '持证主体代码类型(持证者类型)';
comment on column BDC_DZZZ_MLXX.CZZTDMLXDM
  is '持证主体代码类型代码';
comment on column BDC_DZZZ_MLXX.ZZYXQQSRQ
  is '证照有效期起始日期';
comment on column BDC_DZZZ_MLXX.ZZYXQJZRQ
  is '证照有效期截止日期';
comment on column BDC_DZZZ_MLXX.ZZQZR
  is '证照签章人';
comment on column BDC_DZZZ_MLXX.ZZQZSJ
  is '证照签章时间';
comment on column BDC_DZZZ_MLXX.ZZQZMC
  is '证照签章名称';
comment on column BDC_DZZZ_MLXX.CJSJ
  is '创建时间';
comment on column BDC_DZZZ_MLXX.dwdm
  is '单位代码';
comment on column BDC_DZZZ_MLXX.bdcqzh
  is '不动产权证号';
comment on column BDC_DZZZ_MLXX.bdcdyh
  is '不动产单元号';
comment on column BDC_DZZZ_MLXX.zzwjlj
  is '证照文件路径';
comment on column BDC_DZZZ_MLXX.gdzt
  is '归档状态(0:未归档 1:已归档)';
comment on column BDC_DZZZ_MLXX.ZSTYPE
  is '证书类型';
alter table BDC_DZZZ_MLXX
  add constraint PK_BDC_DZZZ_MLXX_MLID primary key (MLID);
alter table BDC_DZZZ_MLXX
  add constraint PK_BDC_DZZZ_MLXX_ZZID unique (ZZID);
  
 --不动产电子证照请求记录表 
create table BDC_DZZZ_QQJL
(
  qqid VARCHAR2(32) not null,
  qqmc VARCHAR2(50) not null,
  qqbm VARCHAR2(50) not null,
  qqsj DATE not null,
  jkmc VARCHAR2(100)
);
comment on column BDC_DZZZ_QQJL.qqmc
  is '请求名称';
comment on column BDC_DZZZ_QQJL.qqbm
  is '请求部门';
comment on column BDC_DZZZ_QQJL.qqsj
  is '创建时间';
comment on column BDC_DZZZ_QQJL.jkmc
  is '接口名称';
alter table BDC_DZZZ_QQJL  add constraint BDC_DZZZ_QQJL_PK primary key (QQID);



  
  
--电子证照token信息 2019/2/19 chenyongqiang
create table BDC_DZZZ_TOKEN
(
  YYID VARCHAR2(32) not null,
  YYMC VARCHAR2(50) not null,
  YYBM VARCHAR2(2) not null,
  YYQX VARCHAR2(2000) not null
);
comment on table BDC_DZZZ_TOKEN
  is '不动产电子证照TOKEN记录表';
comment on column BDC_DZZZ_TOKEN.YYID
  is '主键';
comment on column BDC_DZZZ_TOKEN.YYMC
  is '名称';
comment on column BDC_DZZZ_TOKEN.YYBM
  is '应用部门';
comment on column BDC_DZZZ_TOKEN.YYQX
  is '应用权限';
alter table BDC_DZZZ_TOKEN
  add constraint BDC_DZZZ_TOKEN_PK_ID primary key (YYID);
alter table BDC_DZZZ_TOKEN
  add constraint BDC_DZZZ_TOKEN_PK_NAME unique (YYMC);




  
  
 --电子证照证照信息 2019/2/19 zhangyu
create table BDC_DZZZ_ZZXX
(
  zzid       VARCHAR2(32) not null,
  zsid       VARCHAR2(32),
  zzmc       VARCHAR2(50),
  zzlxdm     VARCHAR2(50),
  zzbh       VARCHAR2(50),
  zzbs       VARCHAR2(200) not null,
  zzbfjg     VARCHAR2(50),
  zzbfjgdm   VARCHAR2(50),
  zzbfrq     DATE,
  czzt       VARCHAR2(300),
  czztdm     VARCHAR2(300),
  czztdmlx   VARCHAR2(300),
  czztdmlxdm VARCHAR2(50),
  zzyxqqsrq  DATE,
  zzyxqjzrq  DATE,
  zzbgsj     DATE,
  zzbgyy     VARCHAR2(400),
  zzqzr      VARCHAR2(50),
  zzqzsj     DATE,
  zzqzmc     VARCHAR2(100),
  cjsj       DATE,
  zzwjlj     VARCHAR2(200),
  zzzt       NUMBER(1),
  szzs       VARCHAR2(2000),
  bdcqzh     VARCHAR2(100),
  zzlsh      VARCHAR2(20),
  zzqzlj     VARCHAR2(200)
);
comment on table BDC_DZZZ_ZZXX
  is '不动产电子证照证照信息';
comment on column BDC_DZZZ_ZZXX.zzid
  is '证照id主键';
comment on column BDC_DZZZ_ZZXX.zsid
  is '证书id';
comment on column BDC_DZZZ_ZZXX.zzmc
  is '证照名称';
comment on column BDC_DZZZ_ZZXX.zzlxdm
  is '证照类型代码';
comment on column BDC_DZZZ_ZZXX.zzbh
  is '证照编号';
comment on column BDC_DZZZ_ZZXX.zzbs
  is '证照标识';
comment on column BDC_DZZZ_ZZXX.zzbfjg
  is '证照颁发机构';
comment on column BDC_DZZZ_ZZXX.zzbfjgdm
  is '证照颁发机构代码';
comment on column BDC_DZZZ_ZZXX.zzbfrq
  is '证照颁发日期';
comment on column BDC_DZZZ_ZZXX.czzt
  is '持证主体(持证人)';
comment on column BDC_DZZZ_ZZXX.czztdm
  is '持证主体代码(持证者证件号码)';
comment on column BDC_DZZZ_ZZXX.czztdmlx
  is '持证主体代码类型(持证者类型)';
comment on column BDC_DZZZ_ZZXX.czztdmlxdm
  is '持证主体代码类型代码';
comment on column BDC_DZZZ_ZZXX.zzyxqqsrq
  is '证照有效期起始日期';
comment on column BDC_DZZZ_ZZXX.zzyxqjzrq
  is '证照有效期截止日期';
comment on column BDC_DZZZ_ZZXX.zzbgsj
  is '证照变更时间';
comment on column BDC_DZZZ_ZZXX.zzbgyy
  is '证照变更原因';
comment on column BDC_DZZZ_ZZXX.zzqzr
  is '证照签章人';
comment on column BDC_DZZZ_ZZXX.zzqzsj
  is '证照签章时间';
comment on column BDC_DZZZ_ZZXX.zzqzmc
  is '证照签章名称';
comment on column BDC_DZZZ_ZZXX.cjsj
  is '创建时间';
comment on column BDC_DZZZ_ZZXX.zzwjlj
  is '证照文件路径(存放路径)';
comment on column BDC_DZZZ_ZZXX.zzzt
  is '证照状态(1:现势,2:注销)';
comment on column BDC_DZZZ_ZZXX.szzs
  is '数字证书';
comment on column BDC_DZZZ_ZZXX.bdcqzh
  is '不动产权证号';
comment on column BDC_DZZZ_ZZXX.zzlsh
  is '证照流水号';
comment on column BDC_DZZZ_ZZXX.zzqzlj
  is '证照签章路径';
create index INDEX_BDC_DZZZ_ZZXX_BDCQZH on BDC_DZZZ_ZZXX (BDCQZH);
create index INDEX_BDC_DZZZ_ZZXX_ZZLXDM on BDC_DZZZ_ZZXX (ZZLXDM);
alter table BDC_DZZZ_ZZXX add constraint PK_BDC_DZZZ_ZZXX_ID primary key (ZZID);
alter table BDC_DZZZ_ZZXX add constraint PK_BDC_DZZZ_ZZXX_ZZBS unique (ZZBS);
  


  
-- 不动产电子证照持证主体字典表  
create table BDC_DZZZ_ZD_CZZT
(
  czztdmlxdm   VARCHAR2(4) not null,
  czztdmlx VARCHAR2(50) not null,
  czztlbxf VARCHAR2(50),
  czztlb   VARCHAR2(50)
);
comment on table BDC_DZZZ_ZD_CZZT
  is '不动产电子证照持证主体代码类型字典表';
comment on column BDC_DZZZ_ZD_CZZT.czztdmlxdm
  is '持证主体代码类型代码';
comment on column BDC_DZZZ_ZD_CZZT.czztdmlx
  is '持证主体代码类型';
comment on column BDC_DZZZ_ZD_CZZT.czztlbxf
  is '持证主体类别细分';
comment on column BDC_DZZZ_ZD_CZZT.czztlb
  is '持证主体类别';
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('001', '统一社会信用代码', '法人和其他组织', '法人和其他组织');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('099', '其他法人或其他组织有效证件代码', null, '法人和其他组织');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('111', '公民身份号码', '一般公民', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('114', '中国人民解放军军官证编号', '军人', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('115', '中国人民武装警察部队警官证编号', '军人', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('118', '中国人民解放军士兵证编号', '军人', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('119', '中国人民武装警察部队士兵证编号', '军人', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('120', '中国人民解放军文职人员证编号', '军人', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('122', '中国人民武装警察部队文职人员证编号', '军人', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('411', '外交护照护照号', '华侨和外籍人士', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('412', '公务护照护照号', '华侨和外籍人士', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('413', '公务普通护照护照号', '华侨和外籍人士', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('414', '普通护照护照号', '华侨和外籍人士', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('511', '台湾居民来往大陆通行证号码', '台湾人士', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('516', '港澳居民来往内地通行证号码', '港澳人士', '自然人');
insert into BDC_DZZZ_ZD_CZZT (czztdmlxdm, CZZTDMLX, CZZTLBXF, CZZTLB)values ('999', '其他自然人有效证件代码', null, '自然人');
commit;

create table BDC_DZZZ_ZD_ZZLX
(
  zzlxdm VARCHAR2(32) not null,
  zzlxmc VARCHAR2(32) not null
);
comment on table BDC_DZZZ_ZD_ZZLX
  is '不动产电子证照证照类型字典表';
comment on column BDC_DZZZ_ZD_ZZLX.zzlxdm
  is '证照类型代码';
comment on column BDC_DZZZ_ZD_ZZLX.zzlxmc
  is '证照类型名称';
insert into BDC_DZZZ_ZD_ZZLX (ZZLXDM, ZZLXMC)values ('11100000MB03271699001', '中华人民共和国不动产权证');
insert into BDC_DZZZ_ZD_ZZLX (ZZLXDM, ZZLXMC)values ('11100000MB03271699022', '中华人民共和国不动产登记证明');
commit;


  
create table BDC_MLXX_HJLOG
(
  MLID   VARCHAR2(32) not null,
  STMLID VARCHAR2(32),
  XZQDM  VARCHAR2(50),
  SCSJ   TIMESTAMP(6),
  KHDZTS NUMBER,
  FWDZTS NUMBER,
  XZCZ   NUMBER,
  SFGD   NUMBER(1)
);
comment on column BDC_MLXX_HJLOG.MLID
  is '目录ID';
comment on column BDC_MLXX_HJLOG.STMLID
  is '上条目录ID';
comment on column BDC_MLXX_HJLOG.XZQDM
  is '行政区代码';
comment on column BDC_MLXX_HJLOG.SCSJ
  is '上传时间戳';
comment on column BDC_MLXX_HJLOG.KHDZTS
  is '客户端数据总条数';
comment on column BDC_MLXX_HJLOG.FWDZTS
  is '服务端数据总条数';
comment on column BDC_MLXX_HJLOG.XZCZ
  is '新增差值';
comment on column BDC_MLXX_HJLOG.SFGD
  is '是否归档';

create table BDC_MLXX_HJCZ
(
  XZQDM VARCHAR2(50) not null,
  CZ    NUMBER
);
comment on column BDC_MLXX_HJCZ.XZQDM
  is '行政区代码';
comment on column BDC_MLXX_HJCZ.CZ
  is '差值';
  
  
  --电子证照部门字典表信息 2019/5/8 陈永强
  ---不动产电子证照部门字典表
create table BDC_DZZZ_ZD_BM
(
  bmid VARCHAR2(32) not null,
  bmdm VARCHAR2(2) not null,
  bmmc VARCHAR2(50) not null
);
comment on table BDC_DZZZ_ZD_BM
  is '不动产电子证照部门字典表';
comment on column BDC_DZZZ_ZD_BM.bmid
  is '部门 id';
comment on column BDC_DZZZ_ZD_BM.bmdm
  is '部门代码';
comment on column BDC_DZZZ_ZD_BM.bmmc
  is '部门名称';
alter table BDC_DZZZ_ZD_BM
  add constraint BDC_DZZZ_ZD_BM_PK primary key (BMID);

  
  

--增加电子证照证照库持证主体表
CREATE TABLE BDC_DZZZ_ZZXX_CZZT
(
  CZZTID VARCHAR2(32) NOT NULL,
  ZZID VARCHAR2(32) NOT NULL,
  CZZT VARCHAR2(200) NOT NULL,
  CZZTDM VARCHAR2(100), 
  CZZTDMLX VARCHAR2(50), 
  CZZTDMLXDM VARCHAR2(10)
);
COMMENT ON TABLE BDC_DZZZ_ZZXX_CZZT
  IS '不动产电子证照证照信息权利人表';
COMMENT ON COLUMN BDC_DZZZ_ZZXX_CZZT.CZZTID
  IS '主键';
  COMMENT ON COLUMN BDC_DZZZ_ZZXX_CZZT.ZZID
  IS '证照ID';
COMMENT ON COLUMN BDC_DZZZ_ZZXX_CZZT.CZZT
  IS '持证主体';
  COMMENT ON COLUMN BDC_DZZZ_ZZXX_CZZT.CZZTDM
  IS '持证主体证件号';
  COMMENT ON COLUMN BDC_DZZZ_ZZXX_CZZT.CZZTDMLX
  IS '持证主体代码类型';
COMMENT ON COLUMN BDC_DZZZ_ZZXX_CZZT.CZZTDMLXDM
  IS '持证主体代码类型代码';
create index index_Dzzz_Czzt_czzt on Bdc_Dzzz_Zzxx_Czzt(czzt);
create index index_Dzzz_Czzt_czztdm on Bdc_Dzzz_Zzxx_Czzt(czztdm);
create index index_Dzzz_Czzt_czztdmlx on Bdc_Dzzz_Zzxx_Czzt(czztdmlx);
create index index_Dzzz_Czzt_czztdmlxdm on Bdc_Dzzz_Zzxx_Czzt(czztdmlxdm);

  
  -- 电子证照业务信息表 chenyongqiang 2019/05/16
 create table BDC_DZZZ_YWXX
(
  ZZID   VARCHAR2(32) not null,
  YWH    VARCHAR2(32),
  BDCQZH VARCHAR2(50),
  BDCDYH VARCHAR2(50),
  ZL     VARCHAR2(300),
  DWDM   VARCHAR2(10),
  SQSJC  VARCHAR2(50),
  SZSXQC VARCHAR2(100),
  FZRQ   DATE,
  GYQK   VARCHAR2(100),
  QLLX   VARCHAR2(100),
  QLXZ   VARCHAR2(100),
  YT     VARCHAR2(100),
  MJ     VARCHAR2(100),
  SYQX   VARCHAR2(100),
  ZHLSH  VARCHAR2(100),
  QLR    VARCHAR2(200),
  YWR    VARCHAR2(200),
  NF     VARCHAR2(20),
  ZMQLSX VARCHAR2(200),
  FJ     VARCHAR2(600),
  QLQTZK VARCHAR2(600),
  QT     VARCHAR2(600),
  CJSJ   DATE,
  YWID   VARCHAR2(32) not null,
  EWMNR  VARCHAR2(500),
  ZSTYPE VARCHAR2(20)
);
comment on table BDC_DZZZ_YWXX
  is '不动产电子证照业务信息表';
comment on column BDC_DZZZ_YWXX.ZZID
  is '证照id';
comment on column BDC_DZZZ_YWXX.YWH
  is '业务号';
comment on column BDC_DZZZ_YWXX.BDCQZH
  is '不动产权证号';
comment on column BDC_DZZZ_YWXX.BDCDYH
  is '不动产单元号';
comment on column BDC_DZZZ_YWXX.ZL
  is '坐落';
comment on column BDC_DZZZ_YWXX.DWDM
  is '单位代码';
comment on column BDC_DZZZ_YWXX.SQSJC
  is '省区市简称';
comment on column BDC_DZZZ_YWXX.SZSXQC
  is '所在市县全称';
comment on column BDC_DZZZ_YWXX.FZRQ
  is '发证日期';
comment on column BDC_DZZZ_YWXX.GYQK
  is '共有情况';
comment on column BDC_DZZZ_YWXX.QLLX
  is '权利类型';
comment on column BDC_DZZZ_YWXX.QLXZ
  is '权利性质';
comment on column BDC_DZZZ_YWXX.YT
  is '用途';
comment on column BDC_DZZZ_YWXX.MJ
  is '面积';
comment on column BDC_DZZZ_YWXX.SYQX
  is '使用期限';
comment on column BDC_DZZZ_YWXX.ZHLSH
  is '证号流水号';
comment on column BDC_DZZZ_YWXX.QLR
  is '权利人';
comment on column BDC_DZZZ_YWXX.YWR
  is '义务人';
comment on column BDC_DZZZ_YWXX.NF
  is '年份';
comment on column BDC_DZZZ_YWXX.ZMQLSX
  is '证明权利或事项';
comment on column BDC_DZZZ_YWXX.FJ
  is '附记';
comment on column BDC_DZZZ_YWXX.QLQTZK
  is '权利其他状况';
comment on column BDC_DZZZ_YWXX.QT
  is '其他';
comment on column BDC_DZZZ_YWXX.CJSJ
  is '创建时间';
comment on column BDC_DZZZ_YWXX.YWID
  is '主键';
comment on column BDC_DZZZ_YWXX.EWMNR
  is '二维码';
comment on column BDC_DZZZ_YWXX.ZSTYPE
           is '证书类型';
alter table BDC_DZZZ_YWXX
  add constraint BDC_DZZZ_YWXX_PK primary key (YWID);
create index index_Dzzz_ywXX_bdcdyh on BDC_DZZZ_ywXX(bdcdyh);
create index index_Dzzz_ywXX_ywh on BDC_DZZZ_ywXX(ywh);
create index index_Dzzz_ywXX_zzid on BDC_DZZZ_ywXX(zzid);


---电子证照流水号表
create table BDC_DZZZ_LSH
(
  lshid VARCHAR2(32) not null,
  lsh   VARCHAR2(20),
  cjsj  DATE,
  zzbfjgdm VARCHAR2(30),
  zzlxdm  VARCHAR2(30)
);
comment on table BDC_DZZZ_LSH
  is '电子证照流水号表';
comment on column BDC_DZZZ_LSH.lshid
  is '流水号ID';
comment on column BDC_DZZZ_LSH.lsh
  is '流水号';
comment on column BDC_DZZZ_LSH.cjsj
  is '创建时间';
comment on column BDC_DZZZ_LSH.zzlxdm
  is '证照类型代码';
comment on column BDC_DZZZ_LSH.zzbfjgdm
  is '颁发机构代码';
alter table BDC_DZZZ_LSH add constraint BDC_DZZZ_LSH_PRIMARY_LSHID primary key (LSHID);
  
  ---电子证照数字签名
create table BDC_DZZZ_SIGNATURE
(
  seal_id          VARCHAR2(50) not null,
  seal_name        VARCHAR2(200) not null,
  seal_public_key  VARCHAR2(200),
  seal_cjsj        DATE,
  seal_private_key VARCHAR2(200)
);
comment on column BDC_DZZZ_SIGNATURE.seal_id
  is '主键';
comment on column BDC_DZZZ_SIGNATURE.seal_name
  is '申请token的应用名称';
comment on column BDC_DZZZ_SIGNATURE.seal_public_key
  is '公钥';
comment on column BDC_DZZZ_SIGNATURE.seal_cjsj
  is '创建时间';
comment on column BDC_DZZZ_SIGNATURE.seal_private_key
  is '私钥';
alter table BDC_DZZZ_SIGNATURE
  add constraint BDC_DZZZ_SIGNATURE_PK primary key (SEAL_ID);
alter table BDC_DZZZ_SIGNATURE
  add constraint BDC_DZZZ_SIGNATURE_UK unique (SEAL_NAME);

  
  

--电子证照持证注销原因字典表
create table BDC_DZZZ_ZD_ZXYY
(
  dm VARCHAR2(4) not null,
  zxyy   VARCHAR2(200) not null
);
comment on table BDC_DZZZ_ZD_ZXYY  is '电子证照持证注销原因字典表';
comment on column BDC_DZZZ_ZD_ZXYY.dm  is '代码';
comment on column BDC_DZZZ_ZD_ZXYY.zxyy  is '注销原因';
insert into BDC_DZZZ_ZD_ZXYY (dm, zxyy)values ('1', '正常办理登记业务注销');
insert into BDC_DZZZ_ZD_ZXYY (dm, zxyy)values ('2', '电子证照签发错误注销');
commit;

--电子证照2.0 2019-08-27
alter table bdc_dzzz_zzxx add (qzzt NUMBER(1));
comment on column bdc_dzzz_zzxx.qzzt
  is '签章状态';
alter table bdc_dzzz_config add (keysn VARCHAR2(50));
comment on column bdc_dzzz_config.keysn
  is '签章名称';
  
--电子证照2.0 2019-09-05
alter table bdc_dzzz_config add (yymc VARCHAR2(50));
comment on column bdc_dzzz_config.yymc
  is '应用名称';