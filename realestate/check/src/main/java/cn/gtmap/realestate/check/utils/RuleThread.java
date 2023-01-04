package cn.gtmap.realestate.check.utils;

import cn.gtmap.realestate.check.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.check.service.RuleService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzxxDO;
import cn.gtmap.realestate.common.core.domain.check.CheckWbjxmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hqz
 * @version 1.0, 2017-5-11
 * @description 规则执行线程类
 */
@Component
@Scope("prototype")
public class RuleThread implements Runnable {

    /**
     * log日志对象
     * @author hqz
     * @description log日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleThread.class);

    private List<BdcXmDO> bdcXmLst;

    private List<CheckGzxxDO> gzxxLst;

    private List<CheckGzjcLogDO> logList;

    private String name;

    /**
     * entity对象Mapper
     * @author hqz
     * @description entity对象Mapper
     */
    private EntityMapper entityMapper;

    /**
     * bdcXm对象Mapper
     * @author hqz
     * @description bdcXm对象Mapper
     */
    private BdcXmMapper bdcXmMapper;

    /**
     * sql类型规则服务的beanName
     * @author lst
     * @description sql类型规则服务的beanName
     */
    private static final String RULE_SQL_BEAN = "ruleSqlService";
    private static final String RULE_GZXT_BEAN = "ruleGzxtService";

    @Override
    public void run(){
        excute();
    }

    private void excute(){
        RuleService ruleService;
        CheckGzjcLogDO bdcGzjcLog;
        RuleService sqlRuleService = (RuleService) Container.getBean(RULE_SQL_BEAN);
        RuleService gzxtRuleService = (RuleService) Container.getBean(RULE_GZXT_BEAN);
        int i = 0;
        for (BdcXmDO bdcXm : bdcXmLst) {
            String gzldyid = bdcXm.getGzldyid();
            i++;
            List<CheckGzxxDO> useGzxx = getUseGzByGzldyid(bdcXm);
            if (CollectionUtils.isNotEmpty(useGzxx)) {
                List<CheckGzjcLogDO> bdcGzjcLogLst = new ArrayList<>();
                for (CheckGzxxDO bdcGzxx : useGzxx) {
                    if (null != bdcGzxx) {
//                        LOGGER.warn("当前检查线程{},第{}项目、流程定义id{}、流程名称{}、规则：{},开始时间：{}", name, i, gzldyid, bdcXm.getGzldymc(), bdcGzxx.getGzcode(), new Date());
                        Integer gzType = bdcGzxx.getGzlx();
                        String gzName = bdcGzxx.getGzcode();
                        if (StringUtils.isNotBlank(gzName) && gzType!=null) {
                            try {
                                //SQL
                                if (Constants.GZ_TYPE_SQL.equals(gzType)) {
                                    ruleService = sqlRuleService;
                                } else if(Constants.GZ_TYPE_SERVICE.equals(gzType)){
                                    //代码检测要提供单独的bean
                                    ruleService = (RuleService) Container.getBean(gzName);
                                } else if(Constants.GZ_TYPE_GZXT.equals(gzType)){
                                    //规则子系统参数
                                    ruleService = gzxtRuleService;
                                }else{
                                    LOGGER.error("当前规则类型配置有问题:{}", bdcGzxx.getGzcode());
                                    continue;
                                }
                                bdcGzjcLog = new CheckGzjcLogDO();
                                bdcGzjcLog.setXmid(bdcXm.getXmid());
                                bdcGzjcLog.setSlbh(bdcXm.getSlbh());
                                bdcGzjcLog.setGzid(bdcGzxx.getGzid());
                                bdcGzjcLog.setJczt(Constants.GZJC_JCZT_ZC);
                                List<Map<String, String>> ruleMaps = null;
                                try{
                                    // 执行规则
                                    ruleMaps = ruleService.excuteRule(bdcXm, gzName);
                                    if (null != ruleMaps && !ruleMaps.isEmpty()) {
                                        bdcGzjcLog.setJcdj(bdcGzxx.getGzdj());
                                        bdcGzjcLog.setJcxx(bdcGzxx.getTsxx());
                                        bdcGzjcLog.setJjzt(Constants.GZJC_WJJZT);
                                        bdcGzjcLogLst = ruleService.mapToBdcGzjcLog(ruleMaps, bdcGzjcLog);
                                    }else{
                                        //lst 验证通过的要查询记录日志中是否有对应数据   有的话进行处理
                                        bdcGzjcLog.setJjfs(Constants.RETEST_SUCCESS);
                                        bdcGzjcLog.setJjsj(new Date());
                                        bdcGzjcLog.setJjzt(Constants.GZJC_JJZT);
                                        bdcGzjcLogLst.add(bdcGzjcLog);
                                    }
                                }catch (Exception e){
                                    // 如果检查异常 直接基本信息存储到日志表
                                    bdcGzjcLog.setJczt(Constants.GZJC_JCZT_YC);
                                    bdcGzjcLogLst.add(bdcGzjcLog);
                                    LOGGER.error("执行规则异常，规则code:{},xmid:{}",bdcGzxx.getGzcode(),bdcXm.getXmid());
                                    LOGGER.error("异常信息:", e);
                                }
                            } catch (Exception e) {
                                LOGGER.error(null, e);
                            } finally {
                                LOGGER.debug("当前检查线程{},第{}项目、规则：{},结束时间：{}", name, i, bdcGzxx.getGzcode(), new Date());
                            }
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcGzjcLogLst)) {
                    LOGGER.warn("当前检查线程{}日志入库开始{},数据量{}", name, new Date(), bdcGzjcLogLst.size());
                    GzjcLogUtil.addGzjcLog(bdcGzjcLogLst, entityMapper, bdcXmMapper, logList);
                    LOGGER.warn("当前检查线程{}日志入库结束{}", name, new Date());
                }
            }
            //判断项目是否办结  没办结记录到wbjxm表中
            if(!Constants.XMZT_BJ.equals(bdcXm.getAjzt())){
                CheckWbjxmDO bdcWbjxm=new CheckWbjxmDO();
                bdcWbjxm.setXmid(bdcXm.getXmid());
                bdcWbjxm.setJcsj(new Date());
                entityMapper.saveOrUpdate(bdcWbjxm,bdcXm.getXmid());
            }else{
                //办结的项目删除记录
                entityMapper.deleteByPrimaryKey(CheckWbjxmDO.class,bdcXm.getXmid());
            }
        }
    }

    /**
     * 根据工作流定义id判断加载需要验证的规则
     * @author lst
     * @param bdcXm 不动产项目
     * @return
     */
    private List<CheckGzxxDO> getUseGzByGzldyid(BdcXmDO bdcXm){
        List<CheckGzxxDO> useGzxx = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(gzxxLst)) {
            String gzldyid=bdcXm.getGzldyid();
            for (CheckGzxxDO bdcGzxx : gzxxLst) {
                String bhlc = bdcGzxx.getBhlc();
                String qclc = bdcGzxx.getQclc();
                //空的话暂时直接加入规则验证
                if(StringUtils.isBlank(gzldyid)){
                    //老数据并且gzldyid没值的，不查有配置包含的规则。
                    if(StringUtils.isBlank(bhlc) || bdcXm.getXmly()==1){
                        useGzxx.add(bdcGzxx);
                    }
                    continue;
                }
                if(StringUtils.isBlank(qclc) || !CommonUtil.indexOfStrs(qclc.split(","), gzldyid)){
                    if (StringUtils.isNotBlank(bhlc)) {
                        String[] bhlcs = bhlc.split(",");
                        if (CommonUtil.indexOfStrs(bhlcs, gzldyid)) {
                            useGzxx.add(bdcGzxx);
                        }
                    } else {
                        useGzxx.add(bdcGzxx);
                    }
                }
            }
        }
        return useGzxx;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setBdcXmLst(List<BdcXmDO> bdcXmLst) {
        this.bdcXmLst = bdcXmLst;
    }

    public void setGzxxLst(List<CheckGzxxDO> gzxxLst) {
        this.gzxxLst = gzxxLst;
    }


    public void setEntityMapper(EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }

    public void setBdcXmMapper(BdcXmMapper bdcXmMapper) {
        this.bdcXmMapper = bdcXmMapper;
    }

    public void setLogList(List<CheckGzjcLogDO> logList) {
        this.logList = logList;
    }
}
