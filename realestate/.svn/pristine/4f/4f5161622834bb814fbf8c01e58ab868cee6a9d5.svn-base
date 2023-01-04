package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDkxxDTO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDkxxQO;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/28
 * @description 土地承包农用地使用权Mapper
 */
public interface BdcTdcbnydsyqMapper {
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
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新土地承包农用地使用权的权属状态
     */
    int udpateBdcTdcbnydsyqQsztPl(DbxxQO dbxxQO);

    BdcTdcbnydsyqDO queryByxmid(@Param("xmid") String xmid);

    List<BdcDkxxDTO> queryDkxxBygzlslid(@Param("gzlslid") String gzlslid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 土地承包经营权查询项目或证书关联的地块列表
     */
    List<BdcDkxxDTO> queryTdcbjyqDkxx(BdcDkxxQO bdcDkxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcDkxxQO 查询实体
     * @return {List} 地块信息列表
     * @description 未生成证书情况下查找目标证书 土地承包经营权地块列表
     */
    List<BdcDkxxDTO> queryTdcbjyqDkxxBeforeZsInit(BdcDkxxQO bdcDkxxQO);

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
