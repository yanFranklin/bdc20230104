package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-21
 * @description 定时任务 相关服务
 */
public class ScheduledService {

    private ExchangeBean exchangeBean;

    public void dealTask(){
        if(exchangeBean != null){
            InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,null);
            requestBuilder.invoke();
        }else{
            throw new AppException("定时任务exchangeBean注入失败");
        }
    }

    public ExchangeBean getExchangeBean() {
        return exchangeBean;
    }

    public void setExchangeBean(ExchangeBean exchangeBean) {
        this.exchangeBean = exchangeBean;
    }
}
