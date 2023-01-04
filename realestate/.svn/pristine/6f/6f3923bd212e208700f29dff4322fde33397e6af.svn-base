package cn.gtmap.realestate.certificate.core.mapper;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description  不动产电子证照目录库业务
 */
@Mapper
public interface BdcDzzzMlxxMapper {


    /**
     * @param bdcDzzzMlxxDO 目录信息实体
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 插入目录信息
     */

    int insertBdcDzzzMlxx(BdcDzzzMlxxDO bdcDzzzMlxxDO);


    /**
     * @param map
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 计算符合条件的数量
     */
    List<BdcDzzzMlxxDO> queryBdcDzzzMlxxByMap(Map map);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param map
     * @return
     * @description 统计证照目录信息
     */
    List<Map> countBdcDzzzMlxx(Map map);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param map
     * @return
     * @description 统计证照目录信息各地区数量分布
     */
    List<Map> countMlxxQuantitativeDistribution(Map map);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param zzid
     * @return
     * @description 通过zzid 删除目录信息
     */
    int deleteBdcDzzzMlxxByZzid(String zzid);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param zzid
     * @return
     * 通过zzid 获取目录信息
     */
    BdcDzzzMlxxDO queryBdcDzzzMlxxByZzid(String zzid);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param mlid
     * @return
     * @description 通过mlid 获取目录信息
     */
    BdcDzzzMlxxDO queryBdcDzzzMlxxByMlid(String mlid);

}
