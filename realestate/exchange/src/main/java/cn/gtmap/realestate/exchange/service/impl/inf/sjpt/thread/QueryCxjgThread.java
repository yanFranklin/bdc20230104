package cn.gtmap.realestate.exchange.service.impl.inf.sjpt.thread;


import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.sjpt.SjptCxjgThreadDTO;
import cn.gtmap.realestate.exchange.service.impl.inf.sjpt.QueryCxjgAbstractServiceImpl;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/11/24
 * @description 省厅查询（定时、实时查询）：填充转换接口返回或者保存入库需要的权利实体信息
 *
 *  备注：为保证逻辑功能正常，迁移原有数据填充转换逻辑，只是放到线程执行
 */
public class QueryCxjgThread implements Callable<Object> {
    private SjptCxjgThreadDTO param;

    public QueryCxjgThread(SjptCxjgThreadDTO param) {
        this.param = param;
    }

    @Override
    public Object call() throws Exception {
        if (null == param.getBdcQl() || StringUtils.isBlank(param.getBdcQl().getXmid())) {
            return null;
        }
        // 接口强转为 BDCQL
        Object ql = param.getBdcQlClass().cast(param.getBdcQl());
        // 实例化 GXQL 实体
        Object gxQl = param.getBdcGxQlClass().newInstance();
        BeanUtils.copyProperties(param.getPeCxDO(), gxQl);
        // 1、 权利信息做对照
        param.getDozerBeanMapper().map(ql, gxQl, SjptConstants.DOZER_MAPPING_ID_QLPREFIX + param.getBdcGxQlClass().getSimpleName());

        // 2、填充共有人
        if (param.isNeedGyr()) {
            List<BdcQlrDO> bdcQlrList = param.getXmidQlrsMap().get(param.getBdcQl().getXmid());
            if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                QueryCxjgAbstractServiceImpl.executeFillGyr(param.getPeCxDO(), gxQl, bdcQlrList);
            }
        }

        List<BdcXmDO> xmDOS = param.getXmidXmMap().get(param.getBdcQl().getXmid());
        if(CollectionUtils.isNotEmpty(xmDOS) && null != xmDOS.get(0)) {
            // 3、填充项目数据
            // 替换不动产权证号分隔符
            if(StringUtils.isNotBlank(param.getBdcqzhFgfh())) {
                xmDOS.get(0).setBdcqzh(xmDOS.get(0).getBdcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, param.getBdcqzhFgfh()));
            }
            param.getDozerBeanMapper().map(xmDOS.get(0), gxQl, SjptConstants.DOZER_MAPPING_ID_XMPREFIX + param.getBdcGxQlClass().getSimpleName());

            // 4、填充ywr信息
            QueryCxjgAbstractServiceImpl.executeFillYwr(gxQl, param.getXmidYwrsMap().get(xmDOS.get(0).getXmid()));

            // 5、填充 是否抵押 是否查封
            if (param.isNeedXzql() && StringUtils.isNotBlank(xmDOS.get(0).getBdcdyh())) {
                List<BdcCfDO> cfDOS = param.getBdcdyhCfMap().get(xmDOS.get(0).getBdcdyh());
                QueryCxjgAbstractServiceImpl.executeFillCfDya(cfDOS, gxQl, "sfcf");

                List<BdcDyaqDO> dyaDOS = param.getBdcdyhDyaMap().get(xmDOS.get(0).getBdcdyh());
                QueryCxjgAbstractServiceImpl.executeFillCfDya(dyaDOS, gxQl, "sfdy");
            }
        }

        if(CheckParameter.checkAnyParameter(gxQl)){
            // 转换字典项
            return gxQlZddz(gxQl);
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gxQl
     * @return java.lang.Object
     * @description 处理权利中的 字典项
     */
    public Object gxQlZddz(Object gxQl){
        if(gxQl != null){
            JSONObject tempJson = JSONObject.parseObject(JSONObject.toJSONString(gxQl));
            if(tempJson != null){
                for(String key : tempJson.keySet()){
                    try{
                        String value = tempJson.getString(key);
                        BdcExchangeZddz bdcExchangeZddz = getBdcExchangeZd(key,value);
                        if(bdcExchangeZddz != null){
                            tempJson.put(key,bdcExchangeZddz.getStddm());
                        }
                    }catch (Exception e){

                    }
                }
                return JSONObject.toJavaObject(tempJson,gxQl.getClass());
            }
        }
        return gxQl;
    }

    /**
     * @param key
     * @param value
     * @return cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    private BdcExchangeZddz getBdcExchangeZd(String key,String value){
        Example example = new Example(BdcExchangeZddz.class);
        example.createCriteria().andEqualTo("bdcdjdm",value)
                .andEqualTo("zdlx",key);
        List<BdcExchangeZddz> bdcExchangeZddzList = param.getEntityMapper().selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcExchangeZddzList)){
            return bdcExchangeZddzList.get(0);
        }else{
            return null;
        }
    }
}
