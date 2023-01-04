package cn.gtmap.realestate.check.service.impl;


import cn.gtmap.realestate.check.service.RuleService;
import cn.gtmap.realestate.check.utils.StringRepUtil;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
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
 * 项目表和权利表的数据不一致
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
@Service("ruleBdcXmQlService")
public class RuleBdcXmQlServiceImpl implements RuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleBdcXmQlServiceImpl.class);
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    /**
     * 是否存在权利数据
     * @author lst
     * @param bdcXm 不动产项目对象
     * @param ruleName 规则名
     * @exception 抛出检测报错
     * @return 根据规则名执行相应规则后返回的记录信息
     */
    @Override
    public List<Map<String, String>> excuteRule(BdcXmDO bdcXm, String ruleName) throws Exception {
        LOGGER.warn("ruleBdcXmQlService开始检查");
        //返回值
        List<Map<String,String>> resultMapLst = new ArrayList<>();
        if(bdcXm!=null && StringUtils.isNotBlank(bdcXm.getXmid())){
            BdcCshFwkgSlDO bdcCshFwkgSlDO=bdcXmFeignService.queryFwkgsl(bdcXm.getXmid());
            //生成权利的
            if(bdcCshFwkgSlDO==null || CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql())){
                BdcQl bdcQl=bdcQllxFeignService.queryQlxx(bdcXm.getXmid());
                if(bdcQl!=null){
                    boolean bdcdyhError=false;
                    boolean qsztError=false;
                    boolean djsjError=false;
                    boolean dbrError=false;
                    boolean jzmjError=false;
                    boolean dzwytError=false;
                    //权属状态
                    if(!StringUtils.equals(String.valueOf(bdcQl.getQszt()),String.valueOf(bdcXm.getQszt()))){
                        qsztError=true;
                    }
                    //登记时间
                    if(!((bdcQl.getDjsj()==null && bdcXm.getDjsj()==null) ||
                            (bdcQl.getDjsj()!=null && bdcXm.getDjsj()!=null && bdcQl.getDjsj().compareTo(bdcXm.getDjsj())==0))){
                        djsjError=true;
                    }
                    //登簿人
                    if(!StringUtils.equals(bdcQl.getDbr(),bdcXm.getDbr())){
                        dbrError=true;
                    }
                    //建筑面积
                    if(bdcQl instanceof BdcFdcqDO){
                        if(!StringUtils.equals(String.valueOf(((BdcFdcqDO) bdcQl).getJzmj()),String.valueOf(bdcXm.getDzwmj()))){
                            jzmjError=true;
                        }
                        if(!StringUtils.equals(String.valueOf(((BdcFdcqDO) bdcQl).getGhyt()),String.valueOf(bdcXm.getDzwyt()))){
                            dzwytError=true;
                        }
                    }
                    //bdcdyh
                    if(bdcQl instanceof BdcDyaqDO){
                        if(!StringUtils.equals(((BdcDyaqDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcFdcqDO){
                        if(!StringUtils.equals(((BdcFdcqDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcCfDO){
                        if(!StringUtils.equals(((BdcCfDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcJsydsyqDO){
                        if(!StringUtils.equals(((BdcJsydsyqDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcDyiqDO){
                        if(!StringUtils.equals(((BdcDyiqDO) bdcQl).getGydbdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcFdcq3DO){
                        if(!StringUtils.equals(((BdcFdcq3DO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcGjzwsyqDO){
                        if(!StringUtils.equals(((BdcGjzwsyqDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcHysyqDO){
                        if(!StringUtils.equals(((BdcHysyqDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcLqDO){
                        if(!StringUtils.equals(((BdcLqDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcQtxgqlDO){
                        if(!StringUtils.equals(((BdcQtxgqlDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcTdcbnydsyqDO){
                        if(!StringUtils.equals(((BdcTdcbnydsyqDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcTdsyqDO){
                        if(!StringUtils.equals(((BdcTdsyqDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcYgDO){
                        if(!StringUtils.equals(((BdcYgDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }else if(bdcQl instanceof BdcYyDO){
                        if(!StringUtils.equals(((BdcYyDO) bdcQl).getBdcdyh(),bdcXm.getBdcdyh())){
                            bdcdyhError=true;
                        }
                    }
                    //错误
                    if(bdcdyhError){
                        Map<String,String> errorMap=getErrorMap(bdcXm);
                        errorMap.put("TYPE","BDCDYH");
                        resultMapLst.add(errorMap);
                    }
                    //错误
                    if(qsztError){
                        Map<String,String> errorMap=getErrorMap(bdcXm);
                        errorMap.put("TYPE","QSZT");
                        resultMapLst.add(errorMap);
                    }
                    //错误
                    if(djsjError){
                        Map<String,String> errorMap=getErrorMap(bdcXm);
                        errorMap.put("TYPE","DJSJ");
                        resultMapLst.add(errorMap);
                    }
                    //错误
                    if(dbrError){
                        Map<String,String> errorMap=getErrorMap(bdcXm);
                        errorMap.put("TYPE","DBR");
                        resultMapLst.add(errorMap);
                    }
                    //错误
                    if(jzmjError){
                        Map<String,String> errorMap=getErrorMap(bdcXm);
                        errorMap.put("TYPE","JZMJ");
                        resultMapLst.add(errorMap);
                    }
                    //错误
                    if(dzwytError){
                        Map<String,String> errorMap=getErrorMap(bdcXm);
                        errorMap.put("TYPE","DZWYT");
                        resultMapLst.add(errorMap);
                    }
                }
            }
        }
        LOGGER.warn("ruleBdcXmQlService检查结束");
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
                    if (resultMap.containsKey("TYPE")) {
                        String type = resultMap.get("TYPE");
                        if (StringUtils.isNotBlank(type)) {
                            newBdcGzjcLog.setType(type);
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
