package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.exchange.ProvincialPublicSecurityDepartmentRestService;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.util.IPUtils;
import cn.gtmap.realestate.exchange.util.SjptApiUtils;
import cn.gtmap.realestate.exchange.util.enums.QueryBusinessCategoryEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/4 11:22
 * @description 省公安厅接口
 */
@OpenController(consumer = "BDC")
@RestController
@Api(tags = "BDC")
public class ProvincialPublicSecurityDepartmentRestController implements ProvincialPublicSecurityDepartmentRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvincialPublicSecurityDepartmentRestController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SjptApiUtils sjptApiUtils;
    /**
     * 4.1省公安厅-公民基本信息在线比对接口
     */
    @Value("${sjpt.publicSecurityDepartment.policeRealName.url:}")
    private String policeRealNameUrl;

    /**
     * 4.3省公安厅-居民户成员信息在线查询接口
     */
    @Value("${sjpt.publicSecurityDepartment.policeHouseholdMembers.url:}")
    private String policeHouseholdMembersUrl;

    /*
     * 4.2 省公安厅-公民人像比对接口url
     * */
    @Value("${sjpt.publicSecurityDepartment.rxbd.url:}")
    private String rxbdGajkUrl;

    /**
     * 4.1省公安厅-公民基本信息在线比对接口
     *
     * @param info
     * @return String
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @ApiOperation(value = "公民基本信息在线比对")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "公民基本信息在线比对", dsfFlag = "GongAn", requester = "BDC", responser = "GongAn")
    @OpenApi(apiDescription = "公民基本信息在线比对", apiName = "", openLog = false)
    public PoliceRealNameResponseDTO policeRealName(@RequestBody PoliceRealNameRequestDTO info) {
        LOGGER.info("公民基本信息在线比对:{}", JSON.toJSONString(info));

        if(CollectionUtils.isEmpty(info.getParamDTOList())) {
            return new PoliceRealNameResponseDTO();
        }
        List<Map<String, Object>> fieldsList = new ArrayList<>();
        for (PoliceRealNameParamDTO paramDTO : info.getParamDTOList()) {
            Map<String, Object> param = new HashMap<>(16);
            param.put("GMSFHM", paramDTO.getIdentityNumber());
            param.put("XM", paramDTO.getName());
            fieldsList.add(param);
        }


        List<Map<String, Object>> fieldsMapList = new ArrayList<>();
        Map<String, Object> fieldsMap = new HashMap<>(16);
        fieldsMap.put("fields", fieldsList);
        fieldsMapList.add(fieldsMap);

        Map<String, Object> data = new HashMap<>(16);
        data.put("cxywlb", QueryBusinessCategoryEnum.POLICE_REAL_NAME.getCode());
        data.put("cxywcs", fieldsMapList);

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
        paramMap.put("data", data);

        String response = sjptApiUtils.sendPostRequest(policeRealNameUrl, paramMap);
        if (null == response) {
            return null;
        }
        JSONObject responseObject = JSON.parseObject(response);

        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("code") && StringUtils.equalsIgnoreCase(headObject.getString("code"), "0000")) {
            JSONObject dataObject = responseObject.getJSONObject("data");
            PoliceRealNameResponseDTO policeRealNameResponseDTO = JSON.toJavaObject(dataObject, PoliceRealNameResponseDTO.class);
            return policeRealNameResponseDTO;
        } else {
            throw new AppException(headObject.getString("msg"));
        }
    }

    /**
     * 4.1省公安厅-公民基本信息在线比对接口
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return String
     */
    @Override
    @ApiOperation(value = "居民户成员信息在线查询")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "居民户成员信息在线查询", dsfFlag = "GongAn", requester = "BDC", responser = "GongAn")
    @OpenApi(apiDescription = "居民户成员信息在线查询", apiName = "", openLog = false)
    public PoliceHouseholdMembersResponseDTO policeHouseholdMembers(@RequestBody PoliceHouseholdMembersRequestDTO info) {
        LOGGER.info("居民户成员信息在线查询:{}", JSON.toJSONString(info));


        Map<String,Object> param = new HashMap<>(16);
        if(StringUtils.isBlank(info.getIdentityNumber())){
            throw new AppException("户成员查询查询身份证号为空");
        }
        //接口中身份证号需要加上单引号
        if(!info.getIdentityNumber().startsWith("'")){
            param.put("sfzh","'"+info.getIdentityNumber()+"'");
        }else {
            param.put("sfzh", info.getIdentityNumber());
        }

        List<Map<String, Object>> cxywcs = new ArrayList<>();
        cxywcs.add(param);

        Map<String, Object> data = new HashMap<>(16);
        data.put("cxywlb", QueryBusinessCategoryEnum.POLICE_HOUSEHOLD_MEMBERS.getCode());
        data.put("cxywcs", cxywcs);

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
        paramMap.put("data", data);

        String response = sjptApiUtils.sendPostRequest(policeHouseholdMembersUrl, paramMap);
        if (null == response) {
            return null;
        }
        JSONObject responseObject = JSON.parseObject(response);

        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("code") && StringUtils.equalsIgnoreCase(headObject.getString("code"), "0000")) {
            JSONObject dataObject = responseObject.getJSONObject("data");
            PoliceHouseholdMembersResponseDTO policeHouseholdMembersResponseDTO = JSON.toJavaObject(dataObject.getJSONObject("hcy_xx"), PoliceHouseholdMembersResponseDTO.class);
            return policeHouseholdMembersResponseDTO;
        } else {
            throw new AppException(headObject.getString("msg"));
        }
    }

    /**
     * @param rxbdRequestDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 4.2 人像对比
     * @date : 2022/10/10 16:46
     */
    @Override
    @ApiOperation(value = "公民基本信息人像比对")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "公民基本信息人像比对", dsfFlag = "GongAn", requester = "BDC", responser = "GongAn")
    @OpenApi(apiDescription = "公民基本信息人像比对", apiName = "", openLog = false)
    public RxbdResponseDTO gmrxdb(@RequestBody RxbdRequestDTO rxbdRequestDTO) {
        LOGGER.info("居民户成员信息在线查询:{}", StringUtils.left(JSON.toJSONString(rxbdRequestDTO), 500));
        if (CollectionUtils.isEmpty(rxbdRequestDTO.getRxbdParamDTOList())) {
            return new RxbdResponseDTO();
        }
        List<Map<String, Object>> fieldsList = new ArrayList<>();
        for (RxbdParamDTO rxbdParamDTO : rxbdRequestDTO.getRxbdParamDTOList()) {
            Map<String, Object> param = new HashMap<>(16);
            param.put("gmsfhm", rxbdParamDTO.getGmsfhm());
            param.put("xm", rxbdParamDTO.getXm());
            param.put("img64", rxbdParamDTO.getImg());
            fieldsList.add(param);
        }
        Map<String, Object> data = new HashMap<>(16);
        data.put("cxywlb", QueryBusinessCategoryEnum.POLICE_REAL_PERSON.getCode());
        data.put("cxywcs", fieldsList);
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
        paramMap.put("data", data);
        LOGGER.warn("人像比对接口入参{}", StringUtils.left(JSON.toJSONString(paramMap), 500));
        String response = sjptApiUtils.sendPostRequest(rxbdGajkUrl, paramMap);
        LOGGER.warn("人像比对接口返回参数{}", JSON.toJSONString(paramMap));
        if (null == response) {
            return null;
        }
        JSONObject responseObject = JSON.parseObject(response);
        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("code") && StringUtils.equalsIgnoreCase(headObject.getString("code"), "0000")) {
            JSONObject dataObject = responseObject.getJSONObject("data");
            JSONObject results = dataObject.getJSONObject("results");
            RxbdResponseDTO rxbdResponseDTO = JSON.toJavaObject(results, RxbdResponseDTO.class);
            return rxbdResponseDTO;
        } else {
            throw new AppException(headObject.getString("msg"));
        }
    }
}
