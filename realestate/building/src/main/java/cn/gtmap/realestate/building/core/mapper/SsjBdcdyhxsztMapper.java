package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-10
 * @description
 */
public interface SsjBdcdyhxsztMapper {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO>
     * @description 查询 逻辑幢下的 Xszt
     */
    List<SSjBdcdyhxsztDO> queryXsztByFwDcbIndex(Map<String,String> paramMap);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息
     */
    List<SSjBdcdyhxsztDO> listBdcdyXszt(List<String> bdcdyhList);
}
