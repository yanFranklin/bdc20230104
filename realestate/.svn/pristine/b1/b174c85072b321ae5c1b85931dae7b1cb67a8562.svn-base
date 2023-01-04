package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZjXmxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZjQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/4/10
 * @description
 */
public interface BdcZjMapper {
    /**
     * 查询存在的质检信息
     * @param map
     * @return
     */
    Integer queryZjxx(Map map);

    /**
     * 获取受理编号
     * @param map
     * @return
     */
    List<String> listZjGzlslid(Map map);

    /**
     * 获取质检信息
     * @param bdcZjQO 不动产质检QO对象
     * @return 工作流实例ID集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcXmDO> listBdcZjxxByPage(BdcZjQO bdcZjQO);

    /**
     * 获取需要质检的不动产项目信息
     * @param bdcZjQO 不动产质检QO对象
     * @return 不动产质检项目信息
     */
    List<BdcZjXmxxDTO> listBdcZjXmxx(BdcZjQO bdcZjQO);

    /**
     * 获取不动产质检结果汇总
     * @param bdcZjQO 不动产质检QO对象
     * @return 质检汇总数据
     */
    List<BdcZjXmxxDTO> queryBdcZjjgHz(BdcZjQO bdcZjQO);
}
