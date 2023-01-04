package cn.gtmap.realestate.check.service.impl;


import cn.gtmap.realestate.check.service.RuleService;
import cn.gtmap.realestate.check.utils.RuleUtil;
import cn.gtmap.realestate.check.utils.StringRepUtil;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目表和权利人表的权利人是否一致
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0
 * @description
 */
@Service("ruleBdcXmQlrService")
public class RuleBdcXmQlrServiceImpl implements RuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleBdcXmQlrServiceImpl.class);
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    /**
     * 项目表和权利人表的权利人是否一致
     * @author lst
     * @param bdcXm 不动产项目对象
     * @param ruleName 规则名
     * @exception 抛出检测报错
     * @return 根据规则名执行相应规则后返回的记录信息
     */
    @Override
    public List<Map<String, String>> excuteRule(BdcXmDO bdcXm, String ruleName) throws Exception {
        //返回值
        LOGGER.warn("ruleBdcXmQlrService开始执行");
        List<Map<String,String>> resultMapLst = new ArrayList<>();
        if(bdcXm!=null && StringUtils.isNotBlank(bdcXm.getXmid())){
            BdcQlrQO bdcQlrQO=new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXm.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> list=bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            //权利人集合不为空
            if(CollectionUtils.isNotEmpty(list)){
                String qlrmc = StringToolUtils.resolveBeanToAppendStr(list, "getQlrmc", "");
                if(StringUtils.isBlank(bdcXm.getQlr())
                        || !RuleUtil.compareArrays(bdcXm.getQlr().split(","),qlrmc.split(","))){
                    resultMapLst.add(getErrorMap(bdcXm));
                }
            }
        }
        LOGGER.warn("ruleBdcXmQlrService结束执行");
        return resultMapLst;
    }

    /**
     * 根据返回的限制信息列表返回出对应的规则日志类
     * @param resultMapLst 限制信息列表
     * @param bdcGzjcLog   检查日志实体类包含相关基础信息
     * @return 返回详细日志列表
     * @author lst
     */
    @Override
    public List<CheckGzjcLogDO> mapToBdcGzjcLog(List<Map<String, String>> resultMapLst, CheckGzjcLogDO bdcGzjcLog) {
        List<CheckGzjcLogDO> bdcGzjcLogLst = null;
        if (CollectionUtils.isNotEmpty(resultMapLst)) {
            CheckGzjcLogDO newBdcGzjcLog;
            bdcGzjcLogLst = new ArrayList<>();
            for (Map<String,String> resultMap : resultMapLst) {
                if (null != resultMap) {
                    newBdcGzjcLog = new CheckGzjcLogDO();
                    //属性转换
                    BeanUtils.copyProperties(bdcGzjcLog,newBdcGzjcLog);
                    String tsxx = bdcGzjcLog.getJcxx();
                    if (StringUtils.isNotBlank(tsxx)) {
                        tsxx = StringRepUtil.replaceTsxxFromMap(tsxx,resultMap);
                    }
                    newBdcGzjcLog.setJcxx(tsxx);
                    if (resultMap.containsKey("BDCDYH")) {
                        String bdcdyh = resultMap.get("BDCDYH");
                        if (StringUtils.isNotBlank(bdcdyh)) {
                            newBdcGzjcLog.setBdcdyh(bdcdyh);
                        }
                    }
                    bdcGzjcLogLst.add(newBdcGzjcLog);
                }
            }
        }
        return bdcGzjcLogLst;
    }

    private Map<String,String> getErrorMap(BdcXmDO bdcXm){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("BDCDYH",bdcXm.getBdcdyh());
        errorMap.put("XMID",bdcXm.getXmid());
        errorMap.put("SLBH",bdcXm.getSlbh());
        return errorMap;
    }
}
