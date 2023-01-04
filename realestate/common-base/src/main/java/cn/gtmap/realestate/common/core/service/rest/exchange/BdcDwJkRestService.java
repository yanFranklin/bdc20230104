package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiConsumerContrastRelationDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDetailInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiTestDTO;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhongjinpeng.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/24 14:24
 */
public interface BdcDwJkRestService {

    /**
     * 保存接口信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/save")
    String saveApi(@RequestBody BdcOpenApiDTO bdcOpenApiDTO);

    /**
     * 分页查询接口信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcOpenApiQOStr
     * @return BdcDwJkDO
     */
    @GetMapping("/realestate-exchange/rest/v1.0/apiManagement/list/search/page")
    Page<BdcOpenApiDetailInfoDTO> listApi(Pageable pageable, @RequestParam(name = "bdcOpenApiQOStr", required = false) String bdcOpenApiQOStr);

    /**
     * 查询接口详细信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param apiId
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/apiManagement/searchById")
    BdcOpenApiDetailInfoDTO searchApiById(@RequestParam(name = "apiId") String apiId);

    /**
     * 获取接口文档xml数据
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param jkid
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/apiManagement/xmlData/searchById")
    Map<String,String> getXmlData(@RequestParam(name = "jkid") String jkid);

    /**
     * 简单接口的sql查询功能实现
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiTestDTO
     * @return
     */
    @PostMapping("/realestate-exchange/rest/v1.0/apiManagement/simple/api")
    Object getSimpleApiResult(@RequestBody BdcOpenApiTestDTO bdcOpenApiTestDTO);

    /**
     * 更新接口信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/update")
    void updateApi(@RequestBody BdcOpenApiDTO bdcOpenApiDTO);

    /**
     * 更新发布状态
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     * @return
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/releaseStatus/update")
    void updateReleaseStatus(@RequestBody BdcOpenApiDTO bdcOpenApiDTO);

    /**
     * 删除接口
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param baseQO
     * @return
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/delete")
    void deleteApi(@RequestBody BaseQO baseQO);

    /**
     * 保存接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiConsumerContrastRelationDTO
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/contrastRelation/save")
    String saveContrastRelation(@RequestBody BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO);

    /**
     * 分页查询接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcOpenApiConsumerContrastRelationDTOStr
     * @return BdcOpenApiConsumerContrastRelationDTO
     */
    @GetMapping("/realestate-exchange/rest/v1.0/apiManagement/contrastRelation/list/search/page")
    Page<BdcOpenApiConsumerContrastRelationDTO> listContrastRelation(Pageable pageable, @RequestParam(name = "bdcOpenApiConsumerContrastRelationDTOStr", required = false) String bdcOpenApiConsumerContrastRelationDTOStr);

    /**
     * 更新接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiConsumerContrastRelationDTO
     * @return
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/contrastRelation/update")
    void updateContrastRelation(@RequestBody BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO);

    /**
     * 删除接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param baseQO
     * @return
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/contrastRelation/delete")
    void deleteContrastRelation(@RequestBody BaseQO baseQO);

    /**
     * 初始化接口表
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/apiManagement/init")
    public CommonResponse initApiInfo();

    /**
     * 补录程序接口信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param bdcDwJkDO
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/add/old/api")
    CommonResponse addOldApi(@RequestBody BdcDwJkDO bdcDwJkDO);

    /**
     * 导出接口配置
     *
     * @param baseQO
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseBody
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/export/interface")
    List<BdcOpenApiDTO> exportInterface(@RequestBody BaseQO baseQO);


    /**
     * 导入接口配置
     *
     * @param bdcOpenApiDTOList
     * @param updateFlag
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseBody
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/import/interface")
    CommonResponse importInterface(@RequestBody List<BdcOpenApiDTO> bdcOpenApiDTOList,@RequestParam(name = "updateFlag") boolean updateFlag);

    /**
     * 复制接口配置
     *
     * @param interfaceId
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseBody
    @GetMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/copy/interface")
    CommonResponse<String> copyInterface(@RequestParam(name = "interfaceId") String interfaceId);

    /**
     * 检查sql
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param bdcOpenApiDTO
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/apiManagement/check/sql")
    CommonResponse checkSql(@RequestBody BdcOpenApiDTO bdcOpenApiDTO);

}
