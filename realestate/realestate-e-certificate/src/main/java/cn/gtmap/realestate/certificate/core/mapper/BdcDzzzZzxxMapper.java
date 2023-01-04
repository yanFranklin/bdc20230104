package cn.gtmap.realestate.certificate.core.mapper;


/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, 2019/1/18
 * @description
 */

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

@Mapper
public interface BdcDzzzZzxxMapper {


    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 通过zzid查询证照信息
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxxByZzid(String zzid);

    /**
     * @param zsid
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 通过zsid查询证照信息
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxxByZsid(String zsid);


    /**
     * @param bdcqzh 不动产权证号
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 通过bdcqzh查询证照信息
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxxByBdcqzh(String bdcqzh);

    /**
     * @param map 证照编号 证照类型代码
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @rerutn
     * @description 通过zzbh查询证照信息
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxxByMap(Map map);

    /**
     * @param zzbs
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 通过zzbs查询证照信息
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxxByZzbs(String zzbs);

    /**
     * @param map 参数集合
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 关联持证主体表  根据持证主体等信息查询条件获取电子证照信息
     */
    List<BdcDzzzZzxxDO> getBdcDzzzZzxxDoListByMap(Map map);

    /**
     * @param map 参数集合
     * @return 电子证照信息
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 单表查询电子证照信息
     */
    List<BdcDzzzZzxxDO> getBdcDzzzZzxxDoByMap(Map map);

    /**
     * @param bdcDzzzZzxxDO
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 插入证照信息
     */
    int insertBdcDzzzZzxx(BdcDzzzZzxxDO bdcDzzzZzxxDO) throws DataAccessException;

    /**
     * @param bdcDzzzZzxxDO
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 更新证照信息
     */
    int updateBdcDzzzByZzid(BdcDzzzZzxxDO bdcDzzzZzxxDO);

    /**
     * @param bdcDzzzZzxxDO
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 更新证照信息
     */
    int updateBdcDzzzByZzbs(BdcDzzzZzxxDO bdcDzzzZzxxDO);


    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 根据zsid删除证照信息
     */
    int deleteBdcDzzzByZzid(String zzid);

    /**
     * @param map
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 统计证照信息
     */
    List<Map> countBdcDzzzZzxx(Map map);

    /**
     * @param map
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 统计证照签发和注销信息
     */
    List<Map> countZzxxZxAndQf(Map map);

}
