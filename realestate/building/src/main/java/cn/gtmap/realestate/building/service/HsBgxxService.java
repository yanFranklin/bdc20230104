package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBgRequestDTO;
import cn.gtmap.realestate.common.core.vo.building.FwHsHbVO;
import cn.gtmap.realestate.common.core.vo.building.FwhsBgVO;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-11-28
 * @description
 */
public interface HsBgxxService {

    /**
     * @param fwHsBgxxDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 保存户室变更信息
     */
    FwHsBgxxDO insertFwHsBgxx(FwHsBgxxDO fwHsBgxxDO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bgbh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @description 根据变更编号查询意见信息
     */
    FwHsBgxxDO getFwHsBgxxByBgbh(String bgbh);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室变更dto获取有效不动产单元号
     */
    List<String> listValidBdcdyhByFwhsBgRequestDTO(FwhsBgRequestDTO fwhsBgRequestDTO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室变更VO获取有效不动产单元号
     */
    List<String> listValidBdcdyhByFwhsBgVo(FwhsBgVO FwhsBgVO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return String
     * @description 根据户室合并VO获取有效不动产单元号
     */
    List<String> listValidBdcdyhByFwhsHbVo(FwHsHbVO fwHsHbVO);


}
