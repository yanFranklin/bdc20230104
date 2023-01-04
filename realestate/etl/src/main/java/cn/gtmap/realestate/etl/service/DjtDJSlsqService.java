package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.domain.etl.EtlDjtDjSlsqDO;
import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;

import java.util.List;
import java.util.Map;

public interface DjtDJSlsqService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ywh
     * @return java.lang.String
     * @description 通过YWH查询受理申请表里的 BDCDYH
     */
    String getBdcdyhByYwh(String ywh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param map
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO>
     * @description 查询受理申请
     */
    List<EtlDjtDjSlsqDO> listDjtDjSlsq(Map<String,Object> map);
    /**
     * @param
     * @return List<DjtDJSlSqDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询未处理的数据
     */
    List<EtlDjtDjSlsqDO> listUnsettledDjtDJSlSqDO(String dealflag);
    /**
     * @param
     * @return Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过map修改
     */
    Integer updateFlagByYwh(Map map);
}
