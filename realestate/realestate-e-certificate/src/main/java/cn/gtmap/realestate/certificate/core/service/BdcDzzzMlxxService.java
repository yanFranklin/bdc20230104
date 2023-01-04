package cn.gtmap.realestate.certificate.core.service;



import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxDO;

import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 不动产电子证照目录库业务
 */
public interface BdcDzzzMlxxService {


    /**
     * @param bdcDzzzMlxxDO
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn 返回执行成功与否状态
     * @description 插入目录库信息
     */
    int insertBdcDzzzMlxx(BdcDzzzMlxxDO bdcDzzzMlxxDO);


    /**
     * @param map 查询条件
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 查询目录库符合条件的信息
     */
    List<BdcDzzzMlxxDO> queryBdcDzzzMlxxByMap(Map map);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param map
     * @return
     * @description 统计证照目录信息各地区数量分布
     */
    String countMlxxQuantitativeDistribution(Map map);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param resultList
     * @return
     * @description 统计证照信息组织json数据
     */
    String countToJsonString(List<Map> resultList);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param map
     * @return
     * @description 统计证照目录信息
     */
    List<Map> countBdcDzzzMlxx(Map map);
}
