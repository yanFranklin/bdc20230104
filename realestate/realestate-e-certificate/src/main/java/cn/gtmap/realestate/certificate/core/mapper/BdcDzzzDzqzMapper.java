package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import org.mapstruct.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 电子签章数据库服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 11:10
 **/
@Mapper
public interface BdcDzzzDzqzMapper {
    /**
     * @param map 参数集合
     * @return 电子证照信息
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 单表查询电子证照信息
     */
    List<BdcDzzzZzxxDO> getBdcDzzzZzxxDoByMap(Map map);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/10 11:42
     */
    int insertBdcDzzzQzxx(BdcDzzzZzxxDO bdcDzzzZzxxDO) throws DataAccessException;

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据zzid 查询签章信息
     * @date : 2022/3/10 16:57
     */
    BdcDzzzZzxxDO queryBdcDzzzQzxxByZzid(String zzid);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据证照标识查询dzqz
     * @date : 2022/3/10 21:16
     */
    BdcDzzzZzxxDO queryBdcDzzzQzxxByZzbs(String zzbs);

    /**
     * @param bdcDzzzZzxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新证照信息
     * @date : 2022/3/10 17:22
     */
    int updateBdcQzxxByZzid(BdcDzzzZzxxDO bdcDzzzZzxxDO);
}
