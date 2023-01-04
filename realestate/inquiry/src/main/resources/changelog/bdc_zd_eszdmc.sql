--liquibase formatted sql
--preconditions dbms:oracle
--changeset BDC_ZD_ESZDMC:1 failOnError:false runOnChange:true runAlways:false
create table BDC_ZD_ESZDMC
(
  DM VARCHAR2(50),
  MC VARCHAR2(50)
);
comment on table BDC_ZD_ESZDMC is 'es字段名称';
comment on column BDC_ZD_ESZDMC.DM is 'es字段key';
comment on column BDC_ZD_ESZDMC.MC is '名称';

INSERT INTO BDC_ZD_ESZDMC VALUES('alias','姓名');
INSERT INTO BDC_ZD_ESZDMC VALUES('ipaddress','操作IP');
INSERT INTO BDC_ZD_ESZDMC VALUES('remoteAddr','操作IP');
INSERT INTO BDC_ZD_ESZDMC VALUES('ip','操作IP');
INSERT INTO BDC_ZD_ESZDMC VALUES('viewTypeName','日志类型');
INSERT INTO BDC_ZD_ESZDMC VALUES('cxtj','查询条件');
INSERT INTO BDC_ZD_ESZDMC VALUES('cxjg','查询结果');
INSERT INTO BDC_ZD_ESZDMC VALUES('bdcdyh','不动产单元号');
INSERT INTO BDC_ZD_ESZDMC VALUES('djyy','登记原因');
INSERT INTO BDC_ZD_ESZDMC VALUES('ghyt','规划用途');
INSERT INTO BDC_ZD_ESZDMC VALUES('ajzt','案件状态');
INSERT INTO BDC_ZD_ESZDMC VALUES('qllx','权利类型');
INSERT INTO BDC_ZD_ESZDMC VALUES('djxl','登记小类');
INSERT INTO BDC_ZD_ESZDMC VALUES('zdzhyt','宗地宗海用途');
INSERT INTO BDC_ZD_ESZDMC VALUES('zdzhmj','宗地宗海面积');
INSERT INTO BDC_ZD_ESZDMC VALUES('qjgldm','权籍管理代码');
INSERT INTO BDC_ZD_ESZDMC VALUES('fwbh','房屋编号');
INSERT INTO BDC_ZD_ESZDMC VALUES('qszt','权属状态');
INSERT INTO BDC_ZD_ESZDMC VALUES('slbh','受理编号');
INSERT INTO BDC_ZD_ESZDMC VALUES('qlrzjh','权利人证件号');
INSERT INTO BDC_ZD_ESZDMC VALUES('qlrmc','权利人名称');
INSERT INTO BDC_ZD_ESZDMC VALUES('qlr','权利人');
INSERT INTO BDC_ZD_ESZDMC VALUES('ywrzjh','义务人证件号');
INSERT INTO BDC_ZD_ESZDMC VALUES('ywrmc','义务人名称');
INSERT INTO BDC_ZD_ESZDMC VALUES('ywr','义务人');
INSERT INTO BDC_ZD_ESZDMC VALUES('zl','坐落');
INSERT INTO BDC_ZD_ESZDMC VALUES('bdcqzh','不动产权证号');
INSERT INTO BDC_ZD_ESZDMC VALUES('gzlslid','工作流实例ID');
INSERT INTO BDC_ZD_ESZDMC VALUES('processInstanceId','工作流实例ID');
INSERT INTO BDC_ZD_ESZDMC VALUES('xmly','项目来源');
INSERT INTO BDC_ZD_ESZDMC VALUES('zslx','证书类型');
INSERT INTO BDC_ZD_ESZDMC VALUES('bdclx','不动产类型');
INSERT INTO BDC_ZD_ESZDMC VALUES('zhlsh','证号流水号');
INSERT INTO BDC_ZD_ESZDMC VALUES('fj','附记');
INSERT INTO BDC_ZD_ESZDMC VALUES('lsdyh','临时单元号');
INSERT INTO BDC_ZD_ESZDMC VALUES('fcxmid','房产项目ID');
INSERT INTO BDC_ZD_ESZDMC VALUES('tdxmid','土地项目ID');
INSERT INTO BDC_ZD_ESZDMC VALUES('methodArgs','请求参数');
INSERT INTO BDC_ZD_ESZDMC VALUES('methodResponse','请求返回值');
INSERT INTO BDC_ZD_ESZDMC VALUES('dbzt','登簿状态');
INSERT INTO BDC_ZD_ESZDMC VALUES('sfyws','是否完税');
INSERT INTO BDC_ZD_ESZDMC VALUES('access','是否上报');
INSERT INTO BDC_ZD_ESZDMC VALUES('gzldymc','流程名称');
INSERT INTO BDC_ZD_ESZDMC VALUES('processDefinitionName','流程名称');
INSERT INTO BDC_ZD_ESZDMC VALUES('qxdm','区县代码');
INSERT INTO BDC_ZD_ESZDMC VALUES('methodName','方法名');
INSERT INTO BDC_ZD_ESZDMC VALUES('reason','原因');
INSERT INTO BDC_ZD_ESZDMC VALUES('opinion','原因');
INSERT INTO BDC_ZD_ESZDMC VALUES('lsdyhbdclx','历史单元号不动产类型');
INSERT INTO BDC_ZD_ESZDMC VALUES('qxlzxmid','取消落宗的项目ID');
INSERT INTO BDC_ZD_ESZDMC VALUES('principalName','用户名');
INSERT INTO BDC_ZD_ESZDMC VALUES('activityName','节点名称');
INSERT INTO BDC_ZD_ESZDMC VALUES('event','日志事件');
INSERT INTO BDC_ZD_ESZDMC VALUES('response','返回值');
INSERT INTO BDC_ZD_ESZDMC VALUES('gtcAppName','应用名');
INSERT INTO BDC_ZD_ESZDMC VALUES('principal','用户id');
INSERT INTO BDC_ZD_ESZDMC VALUES('ZHCXTJ','综合查询条件');
INSERT INTO BDC_ZD_ESZDMC VALUES('time','时间');
INSERT INTO BDC_ZD_ESZDMC VALUES('name','请求地址');
INSERT INTO BDC_ZD_ESZDMC VALUES('logClassify','日志分类');
INSERT INTO BDC_ZD_ESZDMC VALUES('logType','日志类型');
INSERT INTO BDC_ZD_ESZDMC VALUES('ycqzh','原产权证号');
INSERT INTO BDC_ZD_ESZDMC VALUES('xmid','项目ID');
INSERT INTO BDC_ZD_ESZDMC VALUES('userName','用户名');
INSERT INTO BDC_ZD_ESZDMC VALUES('djsj','登记时间');
INSERT INTO BDC_ZD_ESZDMC VALUES('taskId','任务ID');
INSERT INTO BDC_ZD_ESZDMC VALUES('roleCodes','角色code');
INSERT INTO BDC_ZD_ESZDMC VALUES('sply','审批来源');
INSERT INTO BDC_ZD_ESZDMC VALUES('gxbdcdyfwlx','是否更新不动产单元房屋类型');
INSERT INTO BDC_ZD_ESZDMC VALUES('HLNR','忽略内容');
INSERT INTO BDC_ZD_ESZDMC VALUES('gzldyid','工作流定义ID');
INSERT INTO BDC_ZD_ESZDMC VALUES('sqrlb','申请人类别');
INSERT INTO BDC_ZD_ESZDMC VALUES('data','内容');
INSERT INTO BDC_ZD_ESZDMC VALUES('cqzh','产权证号');
INSERT INTO BDC_ZD_ESZDMC VALUES('djjg','登记机构');
INSERT INTO BDC_ZD_ESZDMC VALUES('dzwyt','定着物用途');
INSERT INTO BDC_ZD_ESZDMC VALUES('qlxz','权利性质');
INSERT INTO BDC_ZD_ESZDMC VALUES('djbmdm','登记部门代码');
INSERT INTO BDC_ZD_ESZDMC VALUES('djlx','登记类型');
INSERT INTO BDC_ZD_ESZDMC VALUES('dzwmj','定着物面积');
INSERT INTO BDC_ZD_ESZDMC VALUES('ssxz','所属乡镇');
INSERT INTO BDC_ZD_ESZDMC VALUES('bdcdywybh','不动产单元号唯一编号');
INSERT INTO BDC_ZD_ESZDMC VALUES('eventName','事件名称');
commit;
