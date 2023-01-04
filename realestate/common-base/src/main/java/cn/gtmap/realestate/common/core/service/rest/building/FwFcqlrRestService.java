package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbFwQlrResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-08
 * @description 权籍权利人相关服务
 */
public interface FwFcqlrRestService {

    /**
     * @param fwFcqlrIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除权利人信息
     */
    @DeleteMapping("/building/rest/v1.0/fcqlr/{fwFcqlrIndex}")
    Integer deleteFcqlrByFwFcqlrIndex(@PathVariable("fwFcqlrIndex") String fwFcqlrIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    @DeleteMapping("/building/rest/v1.0/fcqlr/delbyfwindex/{fwIndex}")
    void deleteFcQlrByFwIndex(@PathVariable("fwIndex") String fwIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @param fwFcqlrDOList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量新增权利人
     */
    @PostMapping("/building/rest/v1.0/fcqlr/batchadd")
    List<FwFcqlrDO>  batchInsert(@RequestBody List<FwFcqlrDO> fwFcqlrDOList);

    /**
     * @param fwFcqlrDO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新增权利人
     */
    @PostMapping("/building/rest/v1.0/fcqlr")
    FwFcqlrDO insertQlr(@RequestBody FwFcqlrDO fwFcqlrDO);


    /**
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键查询权利人
     */
    @GetMapping("/building/rest/v1.0/fcqlr/list/{fwHsIndex}")
    List<FwFcqlrDO> listQlr(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @description 根据BDCDYH查询房屋房产权利人
     */
    @GetMapping("/building/rest/v1.0/fcqlr/listbybdcdyh/{bdcdyh}")
    List<FwFcqlrDO> listQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,
                                    @RequestParam(name = "bdcdyfwlx", required = false) String bdcdyfwlx,
                                    @RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @param fwFcqlrDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改权利人信息
     */
    @PutMapping("/building/rest/v1.0/fcqlr/{fwFcqlrIndex}")
    Integer updateFwFcQlr(@RequestBody FwFcqlrDO fwFcqlrDO, @RequestParam(name = "updateNull", required = false) boolean updateNull);


    /**
     * @param fwFcqlrDOList
     * @param updateNull     true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量修改权利人信息
     */
    @PutMapping("/building/rest/v1.0/fcqlr/batchUpdate")
    void batchUpdateFwFcQlr(@RequestBody List<FwFcqlrDO> fwFcqlrDOList, @RequestParam(name = "updateNull", required = false) boolean updateNull);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.DjdcbFwQlrResponseDTO>
     * @description 分页查询权利人信息
     */
    @PostMapping("/building/rest/v1.0/fcqlr/page")
    Page<DjdcbFwQlrResponseDTO> listQlrByPageJson(Pageable pageable,
                                                  @RequestParam(name = "paramJson", required = false) String paramJson);
}
