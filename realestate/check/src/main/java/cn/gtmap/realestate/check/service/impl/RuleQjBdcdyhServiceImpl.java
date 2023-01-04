package cn.gtmap.realestate.check.service.impl;


import cn.gtmap.realestate.check.service.RuleService;
import cn.gtmap.realestate.check.utils.StringRepUtil;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.GzwFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
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
@Service("ruleQjBdcdyhService")
public class RuleQjBdcdyhServiceImpl implements RuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleQjBdcdyhServiceImpl.class);
    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    @Autowired
    private GzwFeignService gzwFeignService;
    @Autowired
    private DjxxFeignService djxxFeignService;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * 服务规则验证权籍单元号信息
     * @author lst
     * @param bdcXm 不动产项目对象
     * @param ruleName 规则名
     * @exception 抛出检测报错
     * @return 根据规则名执行相应规则后返回的记录信息
     */
    @Override
    public List<Map<String, String>> excuteRule(BdcXmDO bdcXm, String ruleName) throws Exception {
        //返回值
        List<Map<String,String>> resultMapLst = new ArrayList<>();
        if(bdcXm!=null && StringUtils.isNotBlank(bdcXm.getBdcdyh())){
            LOGGER.warn("ruleQjBdcdyhService开始检查");
            if (checkBdcdyh(bdcXm.getBdcdyh())) {
                Boolean isFw = false;
                String dzwTzm = bdcXm.getBdcdyh().substring(19, 20);
                if (StringUtils.equals(dzwTzm, CommonConstantUtils.BDCLX_TZM_F)) {
                    isFw = true;
                }
                String qjgldm ="";
                if(StringUtils.isNotBlank(bdcXm.getXmid())) {
                    BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXm.getXmid());
                    if(bdcXmFbDO !=null){
                        qjgldm =bdcXmFbDO.getQjgldm();
                    }
                }

                if(isFw){
                    String bdcdyfwlx = bdcXm.getBdcdyfwlx() != null ? bdcXm.getBdcdyfwlx().toString() : "";

                    BdcdyResponseDTO bdcdyResponseDTO=bdcdyFeignService.queryBdcdy(bdcXm.getBdcdyh(), bdcdyfwlx,qjgldm);
                    if(bdcdyResponseDTO==null){
                        GzwDcbResponseDTO gzwDcbResponseDTO=gzwFeignService.queryGzwxxByBdcdyh(bdcXm.getBdcdyh(),qjgldm);
                        if(gzwDcbResponseDTO==null){
                            Map<String,String> errorMap=new HashMap<>();
                            errorMap.put("BDCDYH",bdcXm.getBdcdyh());
                            errorMap.put("XMID",bdcXm.getXmid());
                            errorMap.put("SLBH",bdcXm.getSlbh());
                            resultMapLst.add(errorMap);
                        }
                    }
                }else{
                    DjxxResponseDTO djxxResponseDTO = djxxFeignService.queryDjxxByBdcdyh(bdcXm.getBdcdyh(),qjgldm);
                    if (djxxResponseDTO == null) {
                        djxxResponseDTO = djxxFeignService.queryHDjxxByBdcdyh(bdcXm.getBdcdyh(),qjgldm);
                        if(djxxResponseDTO == null){
                            Map<String,String> errorMap=new HashMap<>();
                            errorMap.put("BDCDYH",bdcXm.getBdcdyh());
                            errorMap.put("XMID",bdcXm.getXmid());
                            errorMap.put("SLBH",bdcXm.getSlbh());
                            resultMapLst.add(errorMap);
                        }
                    }
                }
            }
            LOGGER.warn("ruleQjBdcdyhService检查结束");
        }
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
            bdcGzjcLogLst = new ArrayList<CheckGzjcLogDO>();
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

    private boolean checkBdcdyh(String bdcdyh){
        return StringUtils.isNotBlank(bdcdyh) && bdcdyh.length()==28 && !BdcdyhToolUtils.checkXnbdcdyh(bdcdyh);
    }
}
