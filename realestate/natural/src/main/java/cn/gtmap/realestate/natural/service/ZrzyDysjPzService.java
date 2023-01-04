package cn.gtmap.realestate.natural.service;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/11
 * @description 不动产打印业务类
 */
public interface ZrzyDysjPzService {
    /**
     * @param configParam
     * @return 查询data 数据
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    Map queryPrintDatasList(Map configParam, String configBeanName, List<BdcDysjPzDO> bdcDysjPzDOList);
    /**
     * @param configParam
     * @return 查询detail 数据
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据配置sql查询detail数据
     */
    Multimap queryPrintDetailList(Map configParam, String configBeanName, List<BdcDysjZbPzDO> bdcDysjZbPzDOList);
}
