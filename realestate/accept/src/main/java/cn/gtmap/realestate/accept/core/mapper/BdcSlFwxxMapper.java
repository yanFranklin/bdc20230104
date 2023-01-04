package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/8/9
 * @description 受理房屋信息
 */
public interface BdcSlFwxxMapper {

    /**
     * @param map 参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据基本信息ID删除不动产受理房屋信息
     */
    void deleteBdcSlFwxx(Map map);

    /**
     * @param bdcSlDeleteCsDTO 受理删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据参数批量删除受理抵押信息
     */
    void batchDeleteBdcSlFwxx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

    /**
     * @param xmidList 项目ID集合
     * @return 不动产受理项目
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 根据项目ID集合获取不动产受理房屋信息
     */
    List<BdcSlFwxxDO> listBdcSlFwxxByXmids(@Param("xmidList") List<String> xmidList);

    /**
     * @param map 更新内容以及更新条件
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理项目信息
     */
    int batchUpdateBdcSlFwxx(Map map);
}
