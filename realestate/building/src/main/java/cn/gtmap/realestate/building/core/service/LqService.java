package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.HLqDcbDO;
import cn.gtmap.realestate.common.core.domain.building.LqDcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
import cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
public interface LqService {
    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.LqDcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询林权
     */
    LqDcbDO queryLqDcbByBdcdyh(String bdcdyh);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.LqDcbDO
     * @description 根据DJH查询林权
     */
    LqDcbDO queryLqDcbByDjh(String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.LqDcbDO
     * @description 根据DJH查询林权
     */
    HLqDcbDO queryHLqDcbByDjh(String djh);


    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询林权调查表信息
     */
    Page<Map> listLqDcbByPageJson(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林木所有权人
     */
    List<NydQlrDO> listLmsyqrByDcbIndex(String dcbIndex);

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林木使用权人
     */
    List<NydQlrDO> listLmsuqrByDcbIndex(String dcbIndex);

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林地所有权人
     */
    List<NydQlrDO> listLdsyqrByDcbIndex(String dcbIndex);

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林木所有权人
     */
    List<NydQlrDO> listLmsyqrByDjh(String djh);

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林木使用权人
     */
    List<NydQlrDO> listLmsuqrByDjh(String djh);

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林地所有权人
     */
    List<NydQlrDO> listLdsyqrByDjh(String djh);

    /**
     * @param list
     * @param qlrlx
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过类型获取权利人
     */
    List<NydQlrDO> listQlrByLx(List<NydQlrDO> list, String qlrlx);

    /**
     * 获取区县下林地总面积和宗地数量
     * @param qxdmList
     * @return
     */
    List<Map> getLdmjAndZd(List<String> qxdmList);
}