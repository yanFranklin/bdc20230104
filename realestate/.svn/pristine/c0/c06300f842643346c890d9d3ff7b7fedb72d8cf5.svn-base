package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJyxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/18
 * @description 受理交易信息
 */
public interface BdcSlJyxxMapper {

    /**
     * @param map 更新内容以及更新条件
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理交易信息
     */
    int batchUpdateBdcSlJyxx(Map map);

    /**
     * @param map 更新内容及更新条件
     * @return 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的受理交易信息
     */
    int updateXmBdcSlJyxx(Map map);

    /**
     * @param bdcSlDeleteCsDTO 受理删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据参数批量删除受理抵押信息
     */
    void deleteBdcSlJyxx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

    /**
     * @param xmids 项目ID集合
     * @return 受理交易信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID查询受理交易信息
     */
    List<BdcSlJyxxDO> listBdcSlJxxByXmids(@Param("xmids") List<String> xmids);

    /**
     * 根据查询参数查询不动产受理交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlJyxxQO 不动产受理交易信息QO
     * @return 不动产受理交易信息
     */
    List<BdcSlJyxxDO> listBdcSlJyxx(BdcSlJyxxQO bdcSlJyxxQO);

}
