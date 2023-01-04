package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.LqDcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
import cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description 林权相关服务
 */
public interface LqRestService {


    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询林权基本信息
     */
    @GetMapping("/building/rest/v1.0/lq/{bdcdyh}")
    LqDcbDO queryLqByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO>
     * @description 分页查询林权信息
     */
    @PostMapping("/building/rest/v1.0/lq/page")
    Page<Map> listLqDcbByPageJson(Pageable pageable,
                                  @RequestParam(name = "paramJson", required = false) String paramJson);

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林地所有权人
     */
    @GetMapping("/building/rest/v1.0/lq/ldsyqr/dcbIndex/{dcbIndex}")
    List<NydQlrDO> listLdsyqrByDcbIndex(@PathVariable("dcbIndex") String dcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林木使用权人
     */
    @GetMapping("/building/rest/v1.0/lq/lmsuqr/dcbIndex/{dcbIndex}")
    List<NydQlrDO> listLmsuqrByDcbIndex(@PathVariable("dcbIndex") String dcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林木所有权人
     */
    @GetMapping("/building/rest/v1.0/lq/lmsyqr/dcbIndex/{dcbIndex}")
    List<NydQlrDO> listLmsyqrByDcbIndex(@PathVariable("dcbIndex") String dcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林地所有权人
     */
    @GetMapping("/building/rest/v1.0/lq/ldsyqr/djh/{djh}")
    List<NydQlrDO> listLdsyqrByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林木使用权人
     */
    @GetMapping("/building/rest/v1.0/lq/lmsuqr/djh/{djh}")
    List<NydQlrDO> listLmsuqrByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林木所有权人
     */
    @GetMapping("/building/rest/v1.0/lq/lmsyqr/djh/{djh}")
    List<NydQlrDO> listLmsyqrByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
