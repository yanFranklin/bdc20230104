package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckDiffInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckParamDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.exchange.ProvincialDataSharingRestService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.SjptApiUtils;
import cn.gtmap.realestate.exchange.util.IPUtils;
import cn.gtmap.realestate.exchange.util.enums.QueryBusinessCategoryEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
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
 * @description 省级数据共享交换平台接口
 */
@OpenController(consumer = "BDC")
@RestController
@Api(tags = "BDC")
public class ProvincialDataSharingRestController implements ProvincialDataSharingRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvincialDataSharingRestController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SjptApiUtils sjptApiUtils;

    /**
     * 市场监管总局-企业基本信息验证接口url
     */
    @Value("${sjpt.zw.samr.enterpriseCheck.url:}")
    private String enterpriseCheckUrl;

    /**
     * 3.9市场监管总局-企业基本信息验证接口
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return String
     *
     */
    @Override
    @ApiOperation(value = "企业基本信息验证")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "企业基本信息验证", dsfFlag = "SCJGZJ ", requester = "BDC", responser = "SCJGZJ ")
    @OpenApi(apiDescription = "企业基本信息验证", apiName = "", openLog = false)
    public ZwSamrEnterpriseCheckResponseDTO enterpriseCheck(@RequestBody ZwSamrEnterpriseCheckRequestDTO info) {
        LOGGER.info("企业基本信息验证接口入参:{}", JSONObject.toJSONString(info));

        List<Map<String,Object>> cxywcs = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(info.getParamDTOList())){
            for(ZwSamrEnterpriseCheckParamDTO zwSamrEnterpriseCheckParamDTO : info.getParamDTOList()){
                Map<String,Object> map = new HashMap<>(16);
                map.put("entname", zwSamrEnterpriseCheckParamDTO.getEntname());
                map.put("uniscid", zwSamrEnterpriseCheckParamDTO.getUniscid());
                cxywcs.add(map);
            }
        }

        Map<String,Object> bodyParamMap = new HashMap<>(16);
        // 查询业务类别
        bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.ZW_SAMR_ENTERPRISE_CHECK.getCode());
        bodyParamMap.put("cxywcs",cxywcs);

        Map<String,Object> paramMap = new HashMap<>(16);
        paramMap.put("head",sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
        paramMap.put("data",bodyParamMap);

        String response = sjptApiUtils.sendPostRequest(enterpriseCheckUrl,paramMap);

        JSONObject responseObject = JSONObject.parseObject(response);
        JSONObject headObject = responseObject.getJSONObject("head");
        if(headObject.containsKey("status") && StringUtil.equals(headObject.getString("status"),"0")){
            JSONObject data = responseObject.getJSONObject("data");
            JSONObject result = data.getJSONObject("cxjg");
            ZwSamrEnterpriseCheckResponseDTO zwSamrEnterpriseCheckResponseDTO = JSONObject.parseObject(result.toString(), ZwSamrEnterpriseCheckResponseDTO.class);;

            List<ZwSamrEnterpriseCheckDiffInfoDTO> diff_list = new ArrayList<>();
            if(result.containsKey("diff_list")){
                JSONArray diffs = result.getJSONArray("diff_list");
                for(int j = 0; j < diffs.size(); j++) {
                    JSONObject diff = diffs.getJSONObject(j);
                    ZwSamrEnterpriseCheckDiffInfoDTO zwSamrEnterpriseCheckDiffInfoDTO = JSONObject.parseObject(diff.toString(), ZwSamrEnterpriseCheckDiffInfoDTO.class);
                    diff_list.add(zwSamrEnterpriseCheckDiffInfoDTO);
                }
            }
            zwSamrEnterpriseCheckResponseDTO.setDiff_list(diff_list);
            return zwSamrEnterpriseCheckResponseDTO;
        }else{
            throw new AppException(headObject.getString("msg"));
        }
    }
}
