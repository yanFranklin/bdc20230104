package cn.gtmap.realestate.inquiry.ui.web.config;


import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiConsumerContrastRelationDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDetailInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiTestDTO;
import cn.gtmap.realestate.common.core.enums.OpenApiFieldType;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.BdcOpenApiQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.BdcDwJkFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcJkFileDTO;
import cn.gtmap.realestate.inquiry.ui.core.vo.BdcDwJkFieldTypeVO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import io.netty.util.CharsetUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtamp.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/29 09:36
 * @description exchange对外接口管理系统
 */
@RestController
@RequestMapping("/rest/v1.0/apiManagement")
public class BdcOpenApiManagementController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BdcOpenApiManagementController.class);

    @Value("${print.path:}")
    private String printPath;

    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcDwJkFeignService bdcDwJkFeignService;

    @Autowired
    private PdfController pdfController;

    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * 保存接口信息
     *
     * @param bdcOpenApiDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/save")
    public String saveApi(@RequestBody BdcOpenApiDTO bdcOpenApiDTO) {
        bdcOpenApiDTO.setCreatedBy(userManagerUtils.getCurrentUser().getAlias());
        bdcOpenApiDTO.getParamList().forEach(bdcOpenApiParamDTO -> {
            bdcOpenApiParamDTO.setFieldType(OpenApiFieldType.getClassName(bdcOpenApiParamDTO.getFieldType()));
        });
        logger.info("=======================>"+bdcOpenApiDTO.getSql());
        return bdcDwJkFeignService.saveApi(bdcOpenApiDTO);
    }

    /**
     * 分页查询接口信息
     *
     * @param pageable
     * @param consumer
     * @param name
     * @param url
     * @param description
     * @param releaseStatus
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/page/search")
    public Object listApiByPage(@LayuiPageable Pageable pageable,
                                @RequestParam(name = "consumer", required = false) String consumer,
                                @RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "url", required = false) String url,
                                @RequestParam(name = "description", required = false) String description,
                                @RequestParam(name = "releaseStatus", required = false) String releaseStatus) {
        BdcOpenApiQO bdcOpenApiQO = new BdcOpenApiQO();
        if (StringUtils.isNotEmpty(consumer)) {
            bdcOpenApiQO.setConsumerList(Arrays.asList(StringUtils.split(consumer, ",")));
        }
        bdcOpenApiQO.setName(name);
        bdcOpenApiQO.setUrl(url);
        bdcOpenApiQO.setDescription(description);
        bdcOpenApiQO.setReleaseStatus(releaseStatus);
        String bdcOpenApiQOStr = JSON.toJSONString(bdcOpenApiQO);
        return addLayUiCode(bdcDwJkFeignService.listApi(pageable, bdcOpenApiQOStr));
    }

    /**
     * 根据接口id查询接口详细信息
     *
     * @param apiId
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/searchById")
    public Object apiInfoById(@RequestParam(name = "apiId") String apiId) {
        BdcOpenApiDetailInfoDTO bdcOpenApiDetailInfoDTO = bdcDwJkFeignService.searchApiById(apiId);
        return bdcOpenApiDetailInfoDTO;
    }

    /**
     * 导出接口文档
     *
     * @param response
     * @param bdcJkFileDTO 数据
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/api-docs/export")
    public void exportApiDocs(HttpServletResponse response, @ModelAttribute BdcJkFileDTO bdcJkFileDTO) {
        Map<String, String> param = JSON.parseObject(bdcJkFileDTO.getFiledata(), Map.class);
        Map<String, String> data = bdcDwJkFeignService.getXmlData(param.get("apiId"));

        if (Objects.isNull(data) || !data.containsKey("xmlData")) {
            throw new AppException("未查询到相关接口数据!");
        }
        String xmlData = data.get("xmlData");
        logger.info("------xmlData:{}", xmlData);
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "dwjkwdmb.docx");
        pdfExportDTO.setFileName("登记3.0接口文档");
        pdfExportDTO.setXmlData(xmlData);
        pdfController.exportWord(response, pdfExportDTO);
    }

    /**
     * 获取字段类型
     *
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/fieldType/search")
    public Object getFieldTypeList() {
        List<BdcDwJkFieldTypeVO> fieldTypes = new ArrayList<>();
        for (OpenApiFieldType fieldType : OpenApiFieldType.values()) {
            BdcDwJkFieldTypeVO bdcDwJkFieldTypeVO = new BdcDwJkFieldTypeVO();
            bdcDwJkFieldTypeVO.setName(fieldType.getFieldType());
            bdcDwJkFieldTypeVO.setValue(fieldType.getClassName());
            fieldTypes.add(bdcDwJkFieldTypeVO);

        }
        return fieldTypes;
    }

    /**
     * 简单接口调用实现
     *
     * @param bdcOpenApiTestDTO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/api/result")
    public Object getSimpleApiResult(@RequestBody BdcOpenApiTestDTO bdcOpenApiTestDTO) {
        if (!StringUtils.equals("0", bdcOpenApiTestDTO.getType())) {
            BdcOpenApiDTO bdcOpenApiDTO = new BdcOpenApiDTO();
            bdcOpenApiDTO.setId(bdcOpenApiTestDTO.getApiId());
            bdcOpenApiDTO.setUrl(bdcOpenApiTestDTO.getUrl());
            bdcOpenApiDTO.setType(Integer.parseInt(bdcOpenApiTestDTO.getType()));
            bdcOpenApiDTO.setRequestMethod(bdcOpenApiTestDTO.getRequestMethod());
            bdcOpenApiDTO.setName(bdcOpenApiTestDTO.getMethodName());
            JSONObject paramJsonObject = JSON.parseObject(bdcOpenApiTestDTO.getParamJson());
//            JsonUtil.clearAllJsonValue(paramJsonObject);
            bdcOpenApiDTO.setParam(paramJsonObject.toJSONString());
            bdcDwJkFeignService.updateApi(bdcOpenApiDTO);
            return "";
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            logger.info("===>当前登录的用户token信息:{}", authentication);
            return bdcDwJkFeignService.getSimpleApiResult(bdcOpenApiTestDTO);
        }

    }

    /**
     * 程序接口补录
     *
     * @param bdcDwJkDO
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/insert/old/api")
    public CommonResponse insertOldApi(@RequestBody BdcDwJkDO bdcDwJkDO) {
        return bdcDwJkFeignService.addOldApi(bdcDwJkDO);
    }

    /**
     * 更新接口
     *
     * @param bdcOpenApiDTO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/update")
    public void updateApi(@RequestBody BdcOpenApiDTO bdcOpenApiDTO) {
        bdcOpenApiDTO.getParamList().forEach(bdcOpenApiParamDTO -> {
            bdcOpenApiParamDTO.setFieldType(OpenApiFieldType.getClassName(bdcOpenApiParamDTO.getFieldType()));
        });
        bdcOpenApiDTO.setUpdatedBy(userManagerUtils.getCurrentUser().getAlias());
        logger.info("修改接口的参数信息:{}",JSON.toJSONString(bdcOpenApiDTO));
        bdcDwJkFeignService.updateApi(bdcOpenApiDTO);
    }

    /**
     * 更新发布状态
     *
     * @param bdcOpenApiDTO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("releaseStatus/update")
    public void updateReleaseStatus(@RequestBody BdcOpenApiDTO bdcOpenApiDTO) {
        bdcOpenApiDTO.setUpdatedBy(userManagerUtils.getCurrentUser().getAlias());
        bdcDwJkFeignService.updateReleaseStatus(bdcOpenApiDTO);
    }

    /**
     * 删除接口
     *
     * @param baseQO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/delete")
    public void deleteApi(@RequestBody BaseQO baseQO) {
        bdcDwJkFeignService.deleteApi(baseQO);
    }

    /**
     * @param zdNames 逗号隔开的字典名称
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @RequestMapping("/zdMul")
    @ResponseBody
    public Map mulListZd(String zdNames) {
        if (StringUtils.isNotBlank(zdNames)) {
            String[] arr = zdNames.split(",");
            Map<String, List<Map>> resultMap = Maps.newHashMapWithExpectedSize(arr.length);
            if (ArrayUtils.isNotEmpty(arr)) {
                for (String zdName : arr) {
                    if (StringUtils.equals("djbmdm", zdName)) {
                        List<Map> list = new ArrayList<>();
                        List<OrganizationDto> organizationDtoList = this.organizationManagerClient.findRootOrgs();
                        if (CollectionUtils.isNotEmpty(organizationDtoList)) {
                            for (OrganizationDto organizationDto : organizationDtoList) {
                                Map map = new HashMap();
                                map.put("DM", organizationDto.getCode());
                                map.put("MC", organizationDto.getName());
                                list.add(map);
                            }
                        }
                        resultMap.put(zdName, list);
                    } else {
                        resultMap.put(zdName, bdcZdFeignService.queryBdcZd(zdName));
                    }
                }
            }
            return resultMap;
        }
        return null;
    }

    /**
     * 保存接口消费方对照关系
     *
     * @param bdcOpenApiConsumerContrastRelationDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/contrastRelation/save")
    public String saveContrastRelation(@RequestBody BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO) {
        return bdcDwJkFeignService.saveContrastRelation(bdcOpenApiConsumerContrastRelationDTO);
    }

    /**
     * 分页查询接口消费方对照关系
     *
     * @param pageable
     * @param consumer
     * @param principal
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/contrastRelation/page/search")
    public Object listContrastRelation(@LayuiPageable Pageable pageable,
                                       @RequestParam(name = "consumer", required = false) String consumer,
                                       @RequestParam(name = "principal", required = false) String principal) {
        Map<String, Object> param = new HashMap<>(16);
        if (StringUtils.isNotEmpty(consumer)) {
            param.put("consumerList", Arrays.asList(StringUtils.split(consumer, ",")));
        }
        param.put("principal", principal);

        String bdcOpenApiConsumerContrastRelationDTOStr = JSON.toJSONString(param);
        return addLayUiCode(bdcDwJkFeignService.listContrastRelation(pageable, bdcOpenApiConsumerContrastRelationDTOStr));
    }

    /**
     * 更新接口消费方对照关系
     *
     * @param bdcOpenApiConsumerContrastRelationDTO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/contrastRelation/update")
    public void updateContrastRelation(@RequestBody BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO) {
        bdcDwJkFeignService.updateContrastRelation(bdcOpenApiConsumerContrastRelationDTO);
    }

    /**
     * 删除接口消费方对照关系
     *
     * @param baseQO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/contrastRelation/delete")
    public void deleteContrastRelation(@RequestBody BaseQO baseQO) {
        bdcDwJkFeignService.deleteContrastRelation(baseQO);
    }

    /**
     * 导入接口配置
     *
     * @param
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/import/interface")
    public CommonResponse importInterface(MultipartHttpServletRequest httpServletRequest, @RequestParam(name = "updateFlag") boolean updateFlag) {
        //读取txt内容
        if (httpServletRequest == null) {
            return null;
        }
        InputStream fileIn = null;
        List<List<BdcOpenApiDTO>> importDataList = new ArrayList<>();
        try {
            Iterator<String> iterator = httpServletRequest.getFileNames();
            // 遍历所有上传文件
            while (iterator.hasNext()) {
                String fileName = iterator.next();
                MultipartFile multipartFile = httpServletRequest.getFile(fileName);
                fileIn = multipartFile.getInputStream();
                String dataJsonStr = IOUtils.toString(fileIn, CharsetUtil.UTF_8);
                if (StringUtils.isNotBlank(dataJsonStr)){
                    importDataList.add(JSON.parseArray(dataJsonStr,BdcOpenApiDTO.class));
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return CommonResponse.fail("导入接口配置失败");
        } finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
        }
        Map<String,Object> respMap = new HashMap<>(2);
        List<String> errorIdList = new ArrayList<>(4);
        importDataList.forEach(data -> {
            if (CollectionUtils.isNotEmpty(data)){
                CommonResponse commonResponse = bdcDwJkFeignService.importInterface(data, updateFlag);
                if (commonResponse != null && commonResponse.isSuccess()){
                    if (commonResponse.getData() != null && commonResponse.getData() instanceof Map){
                        Map map = (Map) commonResponse.getData();
                        if (map.containsKey("updateIds") && respMap.containsKey("updateIds")){
                            List<String> newUpdateIds = (List<String>) map.get("updateIds");
                            List<String> updateIds = (List<String>) respMap.get("updateIds");
                            updateIds.addAll(newUpdateIds);
                            respMap.put("updateIds",updateIds);
                        }
                        if (map.containsKey("addErrorIds") && respMap.containsKey("addErrorIds")){
                            List<String> newUpdateIds = (List<String>) map.get("addErrorIds");
                            List<String> updateIds = (List<String>) respMap.get("addErrorIds");
                            updateIds.addAll(newUpdateIds);
                            respMap.put("addErrorIds",updateIds);
                        }
                        if (map.containsKey("updateIds") && !respMap.containsKey("updateIds")){
                            respMap.put("updateIds",map.get("updateIds"));
                        }
                        if (map.containsKey("addErrorIds") && !respMap.containsKey("addErrorIds")){
                            respMap.put("addErrorIds",map.get("addErrorIds"));
                        }
                    }
                }else if (commonResponse != null && !commonResponse.isSuccess()){
                    Map map = (Map) commonResponse.getData();
                    if (map.containsKey("error")){
                        errorIdList.add("包含该接口配置信息的"+data.get(0).getName()+"文件导入异常");
                    }
                    respMap.put("error",errorIdList);
                }else {
                    throw new  RuntimeException("导入失败，无返回信息");
                }
            }
        });
        if (MapUtils.isNotEmpty(respMap)){
            return CommonResponse.fail(respMap);
        }else {
            return CommonResponse.ok();
        }
    }

    /**
     * 导出接口配置
     *
     * @param baseQO
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/export/interface")
    public void exportInterface(HttpServletRequest request,HttpServletResponse response,@ModelAttribute BaseQO baseQO) {
        //查询对应的接口信息
        List<BdcOpenApiDTO> bdcOpenApiDTOList = bdcDwJkFeignService.exportInterface(baseQO);
        //转换成json文件导出
        if (CollectionUtils.isNotEmpty(bdcOpenApiDTOList)){
            try {
                exportText(request,response,bdcOpenApiDTOList);
            } catch (IOException e) {
                LOGGER.error("导出接口配置文件异常:{}",e.getMessage());
            }
        }
    }


    /**
     * 导出接口配置
     *
     * @param interfaceId
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/copy/interface")
    public CommonResponse<String> copyInterface(@RequestParam(name = "interfaceId") String interfaceId) {
        //复制对应的接口信息
        return bdcDwJkFeignService.copyInterface(interfaceId);
    }

    /**
     * 保存接口信息
     *
     * @param bdcOpenApiDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/check/sql")
    public CommonResponse checkSql(@RequestBody BdcOpenApiDTO bdcOpenApiDTO) {
        logger.info("=======================>"+bdcOpenApiDTO.getSql());
        return bdcDwJkFeignService.checkSql(bdcOpenApiDTO);
    }

    private void exportText(HttpServletRequest request,HttpServletResponse response,List<BdcOpenApiDTO> bdcOpenApiDTOList) throws IOException {
        response.setContentType("text/plain");
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        String fileName = "interface";
        if (bdcOpenApiDTOList.size() > 1){
            fileName = bdcOpenApiDTOList.get(0).getName()+"等";
        }else {
            fileName = bdcOpenApiDTOList.get(0).getName();
        }
        String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        if (agent.contains("firefox")) {
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), CharsetUtil.ISO_8859_1) + ".txt" );

        } else {
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".txt");

        }
        BufferedOutputStream buff = null;
        StringBuffer write = new StringBuffer(JSON.toJSONString(bdcOpenApiDTOList, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        ServletOutputStream outSTr = null;
        try {
            outSTr = response.getOutputStream();
            buff = new BufferedOutputStream(outSTr);
            buff.write(write.toString().getBytes(CharsetUtil.UTF_8));
            buff.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buff!=null){
                buff.close();
            }
        }
    }

}
