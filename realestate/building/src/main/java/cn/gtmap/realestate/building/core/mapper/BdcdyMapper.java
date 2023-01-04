package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.building.core.domain.SSjMaxBdcdyhDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhScmjResponseDTO;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-07
 * @description
 */
public interface BdcdyMapper {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param sSjMaxBdcdyhDO
     * @return int
     * @description
     */
    int updateMaxBdcdyh(SSjMaxBdcdyhDO sSjMaxBdcdyhDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return int
     * @description 批量更新最大号
     */
    int batchUpdateMaxBdcdyh(Map paramMap);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return java.lang.String
     * @description 从H_FW_HS,H_FW_XMXX,H_FW_LJZ,FW_XMXX,FW_LJZ,FW_HS
     * 这些个表里去找现有最大不动产单元号
     */
    String queryMaxBdcdyhByRule(String rule);

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param bdcdyh
     * @return BdcdyhScmjResponseDTO
     * @description 查询不动产单元是否存在实测面积
     */
    BdcdyhScmjResponseDTO queryScmjxx(String bdcdyh);

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param zdzhdm
     * @return BdcdyhScmjResponseDTO
     * @description 查询不动产单元是否锁定
     */
    Integer queryBdcdyhSd(String zdzhdm);
}
