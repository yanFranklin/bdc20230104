package cn.gtmap.realestate.exchange.service.national;


import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.old.DataModelOld;

import java.util.Set;

/**
 * 获取国家级平台接入xml
 *
 * @author
 */
public interface NationalAccessXmlService {
    /**
     * 当前业务编码
     *
     * @return
     */
    String getRecType();

    /**
     * @param xmid
     * @rerutn
     * @description 根据业务号和不动产单元号获取国家级接入平台Model数据
     */
    DataModel getNationalAccessDataModel(String xmid);

    /**
     * @param
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    Set<NationalAccessDataService> getNationalAccessDataServiceSet();
}
