package cn.gtmap.realestate.accept.service.impl.ddxx;

import cn.gtmap.realestate.accept.service.BdcDdxxAbstractService;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-11-3
 * @description 不动产订单信息工厂类
 */
@Component
public class BdcDdxxFactory {

    @Autowired
    BeansResolveUtils beansResolveUtils;

    /**
     * 根据配置订单接口厂商去SpringApplication中找对应实现类
     * @param type 实现类名称
     * @return 订单自定义实现类
     */
    public BdcDdxxAbstractService getDdxxService(String type){
        return beansResolveUtils.getBeanByName(type);
    }

}
