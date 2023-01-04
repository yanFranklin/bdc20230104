package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/28
 * @description
 */
public interface BdcYgMapper {
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
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 更新预告预抵押原权利权属状态和注销登簿信息
      */
    int udpateYgYdyYqlZxDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新预抵押的登簿信息和权属状态
     */
    int updateYdyaQlDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新预告权属状态
     */
    int udpateBdcYgQsztPl(DbxxQO dbxxQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询预告
     */
    List listBdcYgByXmid(@Param("xmid") String xmid);

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
}
