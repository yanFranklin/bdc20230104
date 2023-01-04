package cn.gtmap.realestate.check.service.impl;


import cn.gtmap.realestate.check.service.RuleService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
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
 * 规则系统类型的规则统一的实现类
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
@Service("ruleGzxtService")
public class RuleGzxtServiceImpl implements RuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleGzxtServiceImpl.class);
    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;

    /**
     * SQL类型规则执行方法 根据规则名动态查找SQL并执行
     * @author lst
     * @param bdcXm 不动产项目对象
     * @param ruleName 规则名
     * @exception 抛出检测报错
     * @return 根据规则名执行相应规则后返回的记录信息
     */
    @Override
    public List<Map<String, String>> excuteRule(BdcXmDO bdcXm, String ruleName) throws Exception {
        LOGGER.warn("ruleGzxtService开始检查");
        //返回值
        List<Map<String,String>> resultMapLst = new ArrayList<>();
        BdcGzYzQO bdcGzYzQO=new BdcGzYzQO();
        bdcGzYzQO.setZhbs(ruleName);
        List<Map<String, Object>> paramList = new ArrayList<>();
        bdcGzYzQO.setParamList(paramList);
        //赋值参数  TODO  后期应用可以考虑批量验证
        Map<String, Object> param = new HashMap<>(4);
        param.put("bdcdyh", bdcXm.getBdcdyh());
        param.put("xmid", bdcXm.getXmid());
        param.put("slbh", bdcXm.getSlbh());
        param.put("gzlslid", bdcXm.getGzlslid());
        paramList.add(param);
        //验证 TODO 此处用多线程调用可能需要增加权限过滤
        BdcGzZhgzTsxxDTO bdcGzZhgzTsxxDTO=bdcGzZhGzFeignService.getZhgzYzTsxx(bdcGzYzQO);
        if(bdcGzZhgzTsxxDTO!=null && CollectionUtils.isNotEmpty(bdcGzZhgzTsxxDTO.getZgzTsxxDTOList())){
            for(BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO:bdcGzZhgzTsxxDTO.getZgzTsxxDTOList()){
                List<String> list=bdcGzZgzTsxxDTO.getTsxx();
                if(CollectionUtils.isNotEmpty(list)){
                    for(String str : list){
                        Map<String,String> map=new HashMap<>();
                        map.put("BDCDYH",bdcXm.getBdcdyh());
                        map.put("TSXX",str);
                        resultMapLst.add(map);
                    }
                }
            }
        }
        LOGGER.warn("ruleGzxtService检查结束");
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
                    if (resultMap.containsKey("TSXX")) {
                        String tsxx = resultMap.get("TSXX");
                        if (StringUtils.isNotBlank(tsxx)) {
                            newBdcGzjcLog.setJcxx(tsxx);
                        }
                    }
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
}
