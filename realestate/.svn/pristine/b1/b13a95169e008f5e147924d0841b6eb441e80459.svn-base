package cn.gtmap.realestate.exchange.service.national;


import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;

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


    QjsjDataModel getQjsjDataModel(QjsjjcDTO qjsjjcDTO);

    /**
     * @param xmid
     * @rerutn
     * @description 根据业务号和不动产单元号获取国家级接入平台Model数据
     */
    DataModelOld getNationalAccessDataModelOld(String xmid);

    /**
     * @param
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    Set<NationalAccessDataService> getNationalAccessDataServiceSet();

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 只获取权籍数据的服务集合
     * @date : 2022/11/22 8:38
     */
    Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet();
}
