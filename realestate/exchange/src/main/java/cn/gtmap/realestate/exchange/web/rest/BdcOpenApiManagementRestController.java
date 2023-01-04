package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiConsumerContrastRelationDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDetailInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiTestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.service.rest.exchange.BdcDwJkRestService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.config.filter.RequestWrapper;
import cn.gtmap.realestate.exchange.core.dto.yctb.YctbSginpriKeyInfo;
import cn.gtmap.realestate.exchange.core.dto.yctb.YctbZxcdRequest;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcDwJkMapper;
import cn.gtmap.realestate.exchange.service.BdcOpenApiManagementService;
import cn.gtmap.realestate.exchange.service.impl.inf.request.HfYctbInterfaceServiceImpl;
import cn.gtmap.realestate.exchange.util.SM2Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:jpzhong1994@gmail.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/24 15:30
 */
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/apiManagement")
@Api(tags = "不动产对外接口管理")
public class BdcOpenApiManagementRestController implements BdcDwJkRestService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcOpenApiManagementRestController.class);

    @Autowired
    private BdcOpenApiManagementService bdcOpenApiManagementService;

    @Autowired
    BdcDwJkMapper bdcDwJkMapper;

    @Resource(name = "hfyctbPostService")
    private HfYctbInterfaceServiceImpl hfYctbInterfaceService;

    /**
     * 省平台调我们接口，请求体是否解密,true是不加解密，false是加解密
     */
    @Value("${yctb.interface.test.flag:true}")
    private String jmkg;

    @Value("#{${yctb.qxdm.appid:{'340101': 'hefei'}}}")
    private Map<String, String> appidMap;


    @ResponseBody
    @RequestMapping("/simple/**")
    public Object simulationRedirect(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);

        LOGGER.info("开始调用接口管理系统，请求参数为：{}",requestWrapper.getRequestBody());
        String requestUrl = request.getRequestURI();
        String requestMethod = request.getMethod();
        String requestParam = "";

        JSONObject paramJson = new JSONObject();
        if ("false".equals(jmkg)) {
            LOGGER.info("================接口管理系统进行解密=============");
            YctbSginpriKeyInfo syInfo =  hfYctbInterfaceService.getsyInfo();
            String syStr = syInfo.getKey();
            String bodyStr = requestWrapper.getRequestBody();
            bodyStr = bodyStr.substring(1, bodyStr.length() - 1);
            String jmStr = SM2Util.decryptInputParameters(bodyStr, syStr);
            LOGGER.info("解密后：{}", jmStr);
            if(StringUtils.isNotEmpty(jmStr)){
                paramJson = JSON.parseObject(jmStr);
                String qxdm = (String)paramJson.get("qxdm");
                LOGGER.info("header中的appid：{}", appidMap.get(qxdm));
                response.addHeader("appid",appidMap.get(qxdm));
            }
        } else {
            if(StringUtils.isNotEmpty(requestWrapper.getRequestBody())){
                paramJson = JSON.parseObject(requestWrapper.getRequestBody());
            }
        }
        paramJson.putAll(request.getParameterMap());
        LOGGER.info("接口管理系统，paramJson为：{}",paramJson.toJSONString());
        String apiId = "";
        if(!paramJson.containsKey("apiId")){
            return new AppException( "apiId参数缺失!");
        }
        Object object = paramJson.get("apiId");
        if(Objects.isNull(object)){
            return new AppException( "apiId不能为空!");
        }
        if(object instanceof String){
            apiId = (String)object;
        }else{
            apiId = ((String[]) object)[0];
        }
        if(StringUtils.isEmpty(apiId)){
            return new AppException( "apiId不能为空!");
        }
        paramJson.remove("apiId");
        BdcOpenApiTestDTO bdcOpenApiTestDTO = new BdcOpenApiTestDTO();
        bdcOpenApiTestDTO.setApiId(apiId);
        bdcOpenApiTestDTO.setUrl(requestUrl);
        bdcOpenApiTestDTO.setFlag(1);
        bdcOpenApiTestDTO.setParamJson(paramJson.toJSONString());
        Object result = bdcOpenApiManagementService.getSimpleOpenApiResult(bdcOpenApiTestDTO);
        LOGGER.info("接口管理系统返回成功，返回参数：{}",JSON.toJSONString(result));
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(result));
        String data = jsonObject.get("data").toString();
        LOGGER.info("接口管理系统返回成功，返回参数data：{}",data);
        if ("false".equals(jmkg)) {
            LOGGER.info("接口管理系统返回参数进行加密");
            // 获取公钥
            String qxdm = (String)paramJson.get("qxdm");
            YctbSginpriKeyInfo gyInfo =  hfYctbInterfaceService.getSginpriKeyInfo(qxdm,2);
            String gyStr = gyInfo.getPubkey();
            String jmStr = SM2Util.encryptedMealReturn(data, gyStr);
            LOGGER.info("加密后：{}", jmStr);
            jsonObject.put("data",jmStr);
            LOGGER.info("最后返回结果：{}", jsonObject);
            return jsonObject;
        } else {
            return result;
        }

    }

    /**
     * 保存接口信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     */
    @Override
    @ResponseBody
    @RequestMapping("/save")
    public String saveApi(@RequestBody BdcOpenApiDTO bdcOpenApiDTO) {
        return bdcOpenApiManagementService.saveApi(bdcOpenApiDTO);
    }

    /**
     * 分页查询接口信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcOpenApiQOStr
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/list/search/page")
    public Page<BdcOpenApiDetailInfoDTO> listApi(Pageable pageable, @RequestParam(name = "bdcOpenApiQOStr", required = false) String bdcOpenApiQOStr) {
        return bdcOpenApiManagementService.listBdcOpenApi(pageable,bdcOpenApiQOStr);
    }

    /**
     * 获取接口详细信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param apiId
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/searchById")
    public BdcOpenApiDetailInfoDTO searchApiById(@RequestParam(value = "apiId") String apiId) {
        return bdcOpenApiManagementService.searchOpenApiByApiId(apiId);
    }

    /**
     * 获取接口文档xml
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param jkid
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/xmlData/searchById")
    public Map<String,String> getXmlData(@RequestParam(value = "jkid") String jkid) {
        return bdcOpenApiManagementService.getOpenApiDocXmlData(jkid);
    }

    /**
     * 获取简单接口的结果
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiTestDTO
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/simple/api")
    public Object getSimpleApiResult(@RequestBody BdcOpenApiTestDTO bdcOpenApiTestDTO) {
        return bdcOpenApiManagementService.getSimpleOpenApiResult(bdcOpenApiTestDTO);
    }


    /**
     * 更新接口
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/update")
    public void updateApi(@RequestBody BdcOpenApiDTO bdcOpenApiDTO) {
         bdcOpenApiManagementService.updateApi(bdcOpenApiDTO);
    }

    /**
     * 更新发布状态
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiDTO
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/releaseStatus/update")
    public void updateReleaseStatus(@RequestBody BdcOpenApiDTO bdcOpenApiDTO) {
        bdcOpenApiManagementService.updateReleaseStatus(bdcOpenApiDTO);
    }

    /**
     * 删除接口
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param baseQO
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/delete")
    public void deleteApi(@RequestBody BaseQO baseQO) {
        bdcOpenApiManagementService.deleteApi(baseQO);
    }

    /**
     * 保存接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiConsumerContrastRelationDTO
     */
    @Override
    @ResponseBody
    @RequestMapping("/contrastRelation/save")
    public String saveContrastRelation(@RequestBody BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO) {
        return bdcOpenApiManagementService.saveContrastRelation(bdcOpenApiConsumerContrastRelationDTO);
    }

    /**
     * 分页查询接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcOpenApiConsumerContrastRelationDTOStr
     * @return BdcOpenApiConsumerContrastRelationDTO
     */
    @Override
    @ResponseBody
    @RequestMapping("/contrastRelation/list/search/page")
    public Page<BdcOpenApiConsumerContrastRelationDTO> listContrastRelation(Pageable pageable, @RequestParam(name = "bdcOpenApiConsumerContrastRelationDTOStr", required = false) String bdcOpenApiConsumerContrastRelationDTOStr ) {
        return bdcOpenApiManagementService.listContrastRelation(pageable,bdcOpenApiConsumerContrastRelationDTOStr);
    }

    /**
     * 更新接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcOpenApiConsumerContrastRelationDTO
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/contrastRelation/update")
    public void updateContrastRelation(@RequestBody BdcOpenApiConsumerContrastRelationDTO bdcOpenApiConsumerContrastRelationDTO) {
        bdcOpenApiManagementService.updateContrastRelation(bdcOpenApiConsumerContrastRelationDTO);
    }

    /**
     * 删除接口消费方对照关系
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param baseQO
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping("/contrastRelation/delete")
    public void deleteContrastRelation(@RequestBody BaseQO baseQO) {
        bdcOpenApiManagementService.deleteContrastRelation(baseQO);
    }


    /**
     * 初始化接口表
     *
     * @return
     * @author <a href="mailto:zedq@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/init")
    public CommonResponse initApiInfo() {
       return bdcOpenApiManagementService.initApiInfo();
    }

    /**
     * 保存接口信息
     *
     * @param bdcDwJkDO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @RequestMapping("/add/old/api")
    public CommonResponse addOldApi(@RequestBody BdcDwJkDO bdcDwJkDO) {
        return bdcOpenApiManagementService.addOldApi(bdcDwJkDO);
    }

    /**
     * 导出接口配置
     *
     * @param baseQO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @RequestMapping("/export/interface")
    public List<BdcOpenApiDTO> exportInterface(@RequestBody BaseQO baseQO) {
        return bdcOpenApiManagementService.exportInterface(baseQO);
    }

    /**
     * 导入接口配置
     *
     * @param bdcOpenApiDTOList
     * @param updateFlag
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @RequestMapping("/import/interface")
    public CommonResponse importInterface(@RequestBody List<BdcOpenApiDTO> bdcOpenApiDTOList,@RequestParam(name = "updateFlag") boolean updateFlag) {
        return bdcOpenApiManagementService.importInterface(bdcOpenApiDTOList,updateFlag);
    }

    /**
     * 复制接口配置
     *
     * @param interfaceId
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @RequestMapping("/copy/interface")
    public CommonResponse<String> copyInterface(@RequestParam(name = "interfaceId") String interfaceId) {
        return bdcOpenApiManagementService.copytInterface(interfaceId);
    }

    /**
     * 检查sql
     *
     * @param bdcOpenApiDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @RequestMapping("/check/sql")
    public CommonResponse checkSql(@RequestBody BdcOpenApiDTO bdcOpenApiDTO) {
        return bdcOpenApiManagementService.checkSql(bdcOpenApiDTO);
    }


}
