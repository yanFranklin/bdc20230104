package cn.gtmap.realestate.exchange.service.national.access;

import cn.gtmap.realestate.exchange.core.domain.BdcExchangeDefaultValueDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.old.MessageModelOld;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-19
 * @description 上报默认必填赋值
 */
public interface AccessDefaultValueService {


    /**
     * @param dataList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 上报实体 赋默认值
     */
    void setDefaultValue(List dataList);

    /**
     * @param accessServiceSet
     * @param messageModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 上报实体  读取 默认值配置表 处理默认值
     */
    void setDefaultValueWithDefaultTable(Set<NationalAccessDataService> accessServiceSet,
                                         MessageModel messageModel, boolean useGzldyidFilter);


    /**
     * @param
     * @return java.util.List<cn.gtmap.realestate.exchange.core.domain.BdcExchangeDefaultValueDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 读取默认值表数据集
     */
    List<BdcExchangeDefaultValueDO> refreshbdcExchangeDefaultValueDOList();

    void setObjDefaulValue(Object obj, List<BdcExchangeDefaultValueDO> defaultValueList, String gzldyid, String djxl);

    List<BdcExchangeDefaultValueDO> getBdcExchangeDefaultValueDOList();
}
