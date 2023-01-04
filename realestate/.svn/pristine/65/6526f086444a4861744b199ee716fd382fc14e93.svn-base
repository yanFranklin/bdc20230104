package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化每个盐城一体化-发证数据同步初始化数据的实现类
 */
@Component
public class YthInitParamServiceChoose implements ApplicationContextAware {

    private Map<String, YthInitParamService> chooseMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public YthInitParamService getService(String serviceName) {
        return chooseMap.get(serviceName);
    }

    @PostConstruct
    public void register() {
        Map<String, YthInitParamService> serviceMap = applicationContext.getBeansOfType(YthInitParamService.class);
        for (YthInitParamService temp : serviceMap.values()) {
            chooseMap.put(temp.getHandleServiceName(), temp);
        }
    }

    /**
     * 获取实现的标识
     *
     * @param bdcXmDO
     * @return
     */
    public String getHandleServiceKey(BdcXmDO bdcXmDO) {
        //转移类业务 抵押业类务 预告登记业务 查封类业务 注销类业务
        if (CommonConstantUtils.DJLX_ZYDJ_DM.equals(bdcXmDO.getDjlx())){
            return YthZyInitParamServiceImpl.ZY_YTH_QL_INIT_PARAM_SERVICE_KEY;
        }
        if (CommonConstantUtils.DJLX_YGDJ_DM.equals(bdcXmDO.getDjlx())){
            return YthYgInitParamServiceImpl.YG_YTH_QL_INIT_PARAM_SERVICE_KEY;
        }
        if (CommonConstantUtils.DJLX_CFDJ_DM.equals(bdcXmDO.getDjlx())){
            return YthCfInitParamServiceImpl.CF_YTH_QL_INIT_PARAM_SERVICE_KEY;
        }
        if (CommonConstantUtils.DJLX_ZXDJ_DM.equals(bdcXmDO.getDjlx())){
            return YthZxaInitParamServiceImpl.ZX_YTH_QL_INIT_PARAM_SERVICE_KEY;
        }
        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())){
            return YthDyaInitParamServiceImpl.DYA_YTH_QL_INIT_PARAM_SERVICE_KEY;
        }
        if (CommonConstantUtils.DJLX_YYDJ_DM.equals(bdcXmDO.getDjlx())){
            return YthYyInitParamServiceImpl.YY_YTH_QL_INIT_PARAM_SERVICE_KEY;
        }
        return YthInitParamServiceImpl.YTH_INIT_PARAM_SERVICE_KEY;
    }

}
