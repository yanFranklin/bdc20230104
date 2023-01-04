package cn.gtmap.realestate.exchange.service;

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
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/24 15:23
 */
public interface BdcOpenApiManagementService {

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/save")
    String saveApi(@RequestBody BdcOpenApiDTO bdcOpenApiDTO);


    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcOpenApiQOStr
     * @return BdcDwJkDO
     */
    @GetMapping("/realestate-exchange/rest/v1.0/list/search/page")
    Page<BdcOpenApiDetailInfoDTO> listBdcOpenApi(@RequestBody Pageable pageable,
                                                 @RequestParam(name = "bdcOpenApiQOStr", required = false) String bdcOpenApiQOStr);


    /**
     * 查询接口详细信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param apiId
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/searchById")
    BdcOpenApiDetailInfoDTO searchOpenApiByApiId(@RequestParam("apiId") String apiId);

    /**
     * 获取接口文档xml
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param jkid
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/xmlData/searchById")
    Map<String,String> getOpenApiDocXmlData(@RequestParam("jkid") String jkid);

    /**
     * 获取接口返回
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiTestDTO
     * @return
     */
    @PostMapping("/realestate-exchange/rest/v1.0/simple/api")
    Object getSimpleOpenApiResult(@RequestBody BdcOpenApiTestDTO bdcOpenApiTestDTO);

    /**
     * 获取接口返回
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     * @return
     */
    @PostMapping("/realestate-exchange/rest/v1.0/apiManagement/update")
    void updateApi(@RequestBody BdcOpenApiDTO bdcOpenApiDTO);

    /**
     * 更新发布状态
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     * @return
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/releaseStatus/update")
    void updateReleaseStatus(@RequestBody BdcOpenApiDTO bdcOpenApiDTO);

    /**
     * 删除接口
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param baseQO
     * @return
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/delete")
    void deleteApi(@RequestBody BaseQO baseQO);

    /**
     * 保存接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiConsumerContrastRelationDTO
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/contrastRelation/save")
    String saveContrastRelation(@RequestBody BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO);

    /**
     * 分页查询接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcOpenApiConsumerContrastRelationDTOStr
     * @return BdcOpenApiConsumerContrastRelationDTO
     */
    @GetMapping("/realestate-exchange/rest/v1.0/contrastRelation/list/search/page")
    Page<BdcOpenApiConsumerContrastRelationDTO> listContrastRelation(@RequestBody Pageable pageable, @RequestParam(name = "bdcOpenApiConsumerContrastRelationDTOStr", required = false) String bdcOpenApiConsumerContrastRelationDTOStr);

    /**
     * 更新接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiConsumerContrastRelationDTO
     * @return
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/contrastRelation/update")
    void updateContrastRelation(@RequestBody BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO);

    /**
     * 删除接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param baseQO
     * @return
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/contrastRelation/delete")
    void deleteContrastRelation(@RequestBody BaseQO baseQO);

    /**
     * 初始化接口表信息
     * @return
     */
    CommonResponse initApiInfo();

    /**
     * 导入接口配置
     *
     * @param bdcOpenApiDTOList
     * @param updateFlag
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
     CommonResponse importInterface(List<BdcOpenApiDTO> bdcOpenApiDTOList,boolean updateFlag);

    /**
     * 导出接口配置
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zedq</a>
     * @param baseQO
     * @return
     */
    List<BdcOpenApiDTO> exportInterface(@RequestBody BaseQO baseQO);

    /**
     * 复制接口配置
     *
     * @param interfaceId
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    CommonResponse<String> copytInterface(String interfaceId);

    /**
     * 检查sql
     *
     * @param bdcOpenApiDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    CommonResponse checkSql(BdcOpenApiDTO bdcOpenApiDTO);

    /**
     * 保存接口信息
     *
     * @param bdcDwJkDO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    CommonResponse addOldApi(BdcDwJkDO bdcDwJkDO);
}
