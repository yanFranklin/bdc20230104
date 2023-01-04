package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.dto.register.BdcdySumDTO;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/28
 * @description 不动产房地产权
 */
public interface BdcFdcqMapper {
    /**
     * @param bdcGyqkDTO 更新参数
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权利共有情况
     */
    int updateQlGyqkPl(BdcGyqkDTO bdcGyqkDTO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 执行数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新当前权利的登簿信息和权属状态
     */
    int udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新上原权利的权属状态和注销登簿信息
     */
    int udpateBdcQlZxDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param map
     * @return 面积和
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 计算房地产权面积之和
     */
    BdcdySumDTO calculatedFdcqMj(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新房地产权的权属状态
     */
    int udpateBdcFdcqQsztPl(DbxxQO dbxxQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据房屋用途统计面积
     */
    List<Map> getSumFdcqMjByGhyt(Map map);

    /**
     * 根据地籍号计算现势房地产权面积之和
     * @return 建筑面积合计
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcdySumDTO computeFdcqMjByDjh(@Param("djh")String djh);

    /**
     * 根据地籍号获取当前宗地上的所有现势的房地产权信息
     * @return 房地产权集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcFdcqDO> queryBdcFdcqByDjh(@Param("djh")String djh);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新权利登簿人
     */
    int updateBdcQlDbrPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新注销权利登簿人
     */
    int updateZxQlDbrPl(DbxxQO dbxxQO);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/9/7 19:20
     */
    List<String> listDistinctTdsyjssj(@Param("gzlslid") String gzlslid, @Param("zdmc") String zdmc);

}
