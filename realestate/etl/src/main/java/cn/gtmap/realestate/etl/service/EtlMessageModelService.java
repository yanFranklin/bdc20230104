package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;
import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.etl.util.EtlDataModelEnum;

import java.util.List;
import java.util.Map;

public interface EtlMessageModelService {

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据业务号整理DataModel
     */
    DataModel initDataModel(Map paramMap);
    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据业务号整理HeadModel
     */
    HeadModel initHeadModel(Map paramMap, DjtDjSlsqDO djtDjSlsqDO);

    /**
     * @param map 实体中的字段
     * @param etlDataModelEnum
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过Map 来查询相应的实体
     */
    <T> List<T> selectDataModelByMap(Map map, EtlDataModelEnum etlDataModelEnum);

}
