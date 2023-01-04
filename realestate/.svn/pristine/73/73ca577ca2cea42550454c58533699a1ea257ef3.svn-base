package cn.gtmap.realestate.exchange.service.national;


import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;

import java.util.List;

/**
 * 国家级平台接入数据服务
 *
 * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
 * @version 1.0, 2018/12/13
 * @description
 */
public interface NationalAccessDataService {

    /**
     * @param ywh
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据业务号获取接入国家级平台的数据
     */
    <T> List<T> getAccessData(String ywh);

    /**
     * @param ywh       登记系统业务号
     * @param dataModel Model数据
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 将查询到的数据直接放入dataModel
     */
    DataModel getAccessDataModel(String ywh, DataModel dataModel);

    DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel);

    /**
     * 获取到这个数据服务名称
     *
     * @return
     */
    String getAccessDataTagName();

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    List getData(DataModel dataModel);

    List getDataOld(DataModelOld dataModel);

    /**
     * @param dataModel
     * @param dataList
     * @return java.util.List
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    void setData(DataModel dataModel, List dataList);

    void setDataOld(DataModelOld dataModel, List dataList);
}
