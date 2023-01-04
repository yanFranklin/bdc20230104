package cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.impl.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化
 */
@Component
public class SendHyxxServiceChoose implements ApplicationContextAware {

    private Map<String, SendHyxxService> chooseMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public SendHyxxService getService(String serviceName) {
        return chooseMap.get(serviceName);
    }

    @PostConstruct
    public void register() {
        Map<String, SendHyxxService> serviceMap = applicationContext.getBeansOfType(SendHyxxService.class);
        for (SendHyxxService temp : serviceMap.values()) {
            chooseMap.put(temp.getHandleServiceName(), temp);
        }
    }

    /**
     * 获取实现的标识
     *
     * 首次登记需调用接口2.1-2.6、2.11推送数据；
     * 变更登记需调用接口2.1、2.4、2.6、2.11推送数据；
     * 抵押登记需调用接口2.6、2.8推送数据；
     * 查封登记需调用接口2.6、2.9推送数据；
     * 异议登记需调用接口2.6、2.10推送数据；
     * 注销登记需调用2.1-2.3、2.6、2.12推送数据。
     *
     * @param bdcXmDO
     * @return
     */
    public String getHandleServiceKey(BdcXmDO bdcXmDO) {
        if (CommonConstantUtils.DJLX_SCDJ_DM.equals(bdcXmDO.getDjlx())){
            return SendHyScxxServiceImpl.SEND_HY_YY_SERVICE_KEY;
        }
        if (CommonConstantUtils.DJLX_BGDJ_DM.equals(bdcXmDO.getDjlx())){
            return SendHyBgxxServiceImpl.SEND_HY_BG_SERVICE_KEY;
        }
        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())){
            return SendHyDyaxxServiceImpl.SEND_HY_DY_SERVICE_KEY;
        }
        if (CommonConstantUtils.DJLX_CFDJ_DM.equals(bdcXmDO.getDjlx())){
            return SendHyCfxxServiceImpl.SEND_HY_CF_SERVICE_KEY;
        }
        if (CommonConstantUtils.DJLX_YYDJ_DM.equals(bdcXmDO.getDjlx())){
            return SendHyYyxxServiceImpl.SEND_HY_YY_SERVICE_KEY;
        }

        if (CommonConstantUtils.DJLX_ZXDJ_DM.equals(bdcXmDO.getDjlx())){
            return SendHyZxxxServiceImpl.SEND_HY_ZX_SERVICE_KEY;
        }
        return null;
    }

}
