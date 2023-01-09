package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.dto.building.ImportLpbRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-20
 * @description 楼盘表展现服务
 */
public interface LpbRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param code
     * @return void
     * @description 刷新楼盘表配置
     */
    @GetMapping(value = "/building/rest/v1.0/lpb/refresh")
    void refreshLpbConfig(@RequestParam(name = "code",required = false) String code);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @param code
     * @return cn.gtmap.realestate.common.core.dto.building.ResourceDTO
     * @description
     */
    @GetMapping(value = "/building/rest/v1.0/{code}/{fwDcbIndex}/fwhs", produces = "application/hal+json")
    ResourceDTO queryFwHsListByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                          @PathVariable("code") String code,
                                          @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @param code
     * @return cn.gtmap.realestate.common.core.dto.building.ResourceDTO
     * @description
     */
    @GetMapping(value = "/building/rest/v1.0/{code}/{fwDcbIndex}/fwychs", produces = "application/hal+json")
    ResourceDTO queryFwYchsListByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                          @PathVariable("code") String code,
                                            @RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @param fwHsIndex
     * @param code
     * @return ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 单个户室查询
     */
    @GetMapping(value = "/building/rest/v1.0/{code}/fwhs/{fwHsIndex}", produces = "application/hal+json")
    ResourceDTO queryFwHsByIndex(@PathVariable("fwHsIndex") String fwHsIndex,
                                     @PathVariable("code") String code,
                                 @RequestParam(name = "qjgldm", required = false) String qjgldm);



    /**
     * @param fwDcbIndex
     * @param code
     * @return ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从户室基本信息实体查询预测信息
     */
    @GetMapping(value = "/building/rest/v1.0/{code}/fwychsinhs/{fwDcbIndex}", produces = "application/hal+json")
    ResourceDTO queryFwYcHsByIndexInFwhs(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                 @PathVariable("code") String code,
                                 @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param importLpbRequestDTO
     * @description 导入楼盘表数据
     */
    @PostMapping("/building/rest/v1.0/lpb/import")
    void importLpbInfo(@RequestBody ImportLpbRequestDTO importLpbRequestDTO);

    /**
     * @param importLpbRequestDTO
     * @description 导入楼盘表数据
     */
    @PostMapping("/building/rest/v1.0/lpb/import/yc")
    void importYcLpbInfo(@RequestBody ImportLpbRequestDTO importLpbRequestDTO);

    /**
     * @param importLpbRequestDTO
     * @description 导入楼盘表数据
     */
    @PostMapping("/building/rest/v1.0/scmj/import")
    void importScmj(@RequestBody ImportLpbRequestDTO importLpbRequestDTO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwDcbIndex
     * @return void
     * @description 导出楼盘表数据
     */
    @PostMapping("/building/rest/v1.0/lpb/export")
    List<Map<String,Object>>  exportLpb(@RequestParam(name = "fwDcbIndex", required = false) String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwDcbIndex
     * @param code
     * @return cn.gtmap.realestate.common.core.dto.building.ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @GetMapping("/building/rest/v1.0/lpb/configres/{code}/{fwDcbIndex}")
    ResourceDTO getLpbConfigInfoResource(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                         @PathVariable("code") String code,
                                         @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @param code
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @description
     */
    @GetMapping("/building/rest/v1.0/lpb/hsres/{code}/{fwDcbIndex}")
    List<ResourceDTO> getFwHsResList(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                     @PathVariable("code") String code,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwDcbIndex
     * @param code
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询幢下的预测户室资源列表
     */
    @GetMapping("/building/rest/v1.0/lpb/ychsres/{code}/{fwDcbIndex}")
    List<ResourceDTO> getFwYchsResList(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                       @PathVariable("code") String code,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwDcbIndex
     * @param code
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询幢下的预测户室资源列表包含权利人
     * @date : 2021/1/8 16:24
     */
    @GetMapping("/building/rest/v1.0/lpb/ychsres/{code}/{fwDcbIndex}/qlr")
    List<ResourceDTO> getFwYchsWithQlr(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                       @PathVariable("code") String code,
                                       @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @return Map<String, Object>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询楼盘表状态颜色配置
     */
    @GetMapping("/building/rest/v1.0/lpb/color/pz")
    Map<String, Object> getLpbColorPz();



    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @param fwDcbIndex
     * @param code
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @description 户室信息包含权利人
     */
    @GetMapping("/building/rest/v1.0/lpb/hsres/{code}/{fwDcbIndex}/qlr")
    List<ResourceDTO> getFwHsResListWithQlr(@PathVariable("fwDcbIndex") String fwDcbIndex,
                                            @PathVariable("code") String code,
                                            @RequestParam(name = "qjgldm", required = false) String qjgldm);

}
