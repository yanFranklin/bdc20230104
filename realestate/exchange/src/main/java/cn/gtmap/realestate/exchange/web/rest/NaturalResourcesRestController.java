package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryParamDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.baseInfo.PoliceQueryBaseInfoRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.idcheck.PoliceCheckIdRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck.*;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery.CbircFinancialQueryParamDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery.CbircFinancialQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery.CbircFinancialQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery.ScopsrOrganQueryParamDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery.ScopsrOrganQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery.ScopsrOrganQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.sea.*;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionapply.SupremeCourtDecisionApplyRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionapply.SupremeCourtDecisionApplyResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse.*;
import cn.gtmap.realestate.common.core.enums.BdcZdEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.NaturalResourcesRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.SendHyxxService;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.SendHyxxServiceChoose;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.IPUtils;
import cn.gtmap.realestate.exchange.util.SjptApiUtils;
import cn.gtmap.realestate.exchange.util.enums.QueryBusinessCategoryEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/2 10:20
 * @description 自然资源部接口
 */
@OpenController(consumer = "BDC")
@RestController
@Api(tags = "BDC")
public class NaturalResourcesRestController implements NaturalResourcesRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NaturalResourcesRestController.class);

    private static final String PATTERN1 = "yyyyMMddHHmmss";

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private SendHyxxServiceChoose sendHyxxServiceChoose;

    @Autowired
    private SjptApiUtils sjptApiUtils;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private DozerBeanMapper exchangeDozerMapper;

    /**
     * 登记部门统一信用代码
     */
    @Value("${sjpt.bus_sre_deptcode:}")
    private String busSreDeptCode;

    /**
     * 2.1公安部-身份核查服务接口url
     */
    @Value("${sjpt.police.identityCheck.url:}")
    private String identityCheckUrl;

    /**
     * 2.3民政部-婚姻登记信息查询服务接口url
     */
    @Value("${sjpt.civil.marriageQuery.url:}")
    private String marriageQueryUrl;

    /**
     * 2.5银保监会-金融许可证查询接口url
     */
    @Value("${sjpt.cbirc.financialQuery.url:}")
    private String financialQueryUrl;

    /**
     * 2.6中编办-事业单位登记信息（含机关、群团信息）查询接口
     */
    @Value("${sjpt.scopsr.organQuery.url:}")
    private String organQueryUrl;

    /**
     * 2.7最高法-司法判决信息查询申请接口
     */
    @Value("${sjpt.supremecourt.decisionApply.url:}")
    private String decisionApplyUrl;

    /**
     * 2.8最高法-司法判决信息结果反馈接口
     */
    @Value("${sjpt.supremecourt.decisionResponse.url:}")
    private String decisionResponseUrl;

    /**
     * 3.12公安部-人口库基准信息查询接口url
     */
    @Value("${sjpt.police.query.baseinfo.url:}")
    private String policeBaseInfoUrl;

    /**
     * 3.13公安部-3.13人口库身份核查接口url
     */
    @Value("${sjpt.police.id.check.url:}")
    private String policeIdCheckUrl;

    /**
     * 2.1宗海基本信息url
     */
    @Value("${sjpt.zh.jbxx.url:}")
    private String zhjbxxUrl;

    /**
     * 2.2 用海状况接口
     */
    @Value("${sjpt.zh.yhzk.url:}")
    private String zhyhzkUrl;

    /**
     * 2.3 用海坐标接口
     */
    @Value("${sjpt.zh.yhzb.url:}")
    private String zhyhzbUrl;

    /**
     * 2.4 宗海变化情况接口
     */
    @Value("${sjpt.zh.bhqk.url:}")
    private String zhbhqkUrl;

    /**
     * 2.5 海域使用权信息接口
     */
    @Value("${sjpt.zh.hysyq.url:}")
    private String zhhysyqUrl;

    /**
     * 2.6 权利人信息接口
     */
    @Value("${sjpt.zh.qlr.url:}")
    private String zhqlrUrl;

    /**
     * 2.8 抵押登记信息接口
     */
    @Value("${sjpt.zh.dydjxx.url:}")
    private String zhdydjxxUrl;

    /**
     * 2.9 查封登记信息接口
     */
    @Value("${sjpt.zh.cfdjxx.url:}")
    private String zhcfdjxxUrl;

    /**
     * 2.10 异议登记信息接口
     */
    @Value("${sjpt.zh.yydjxx.url:}")
    private String zhyydjxxUrl;

    /**
     * 2.11 扫描件信息接口
     */
    @Value("${sjpt.zh.smjxx.url:}")
    private String zhsmjxxUrl;

    /**
     * 2.12 注销登记信息接口
     */
    @Value("${sjpt.zh.zxdjxx.url:}")
    private String zhzxdjxxUrl;

    /**
     * 2.1 公安部-身份核查服务接口
     *
     * @param info
     * @return PoliceIdentityCheckResponseDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @ApiOperation(value = "身份核查")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "身份核查", dsfFlag = "GongAn", requester = "BDC", responser = "GongAn")
    @OpenApi(apiDescription = "身份核查", apiName = "", openLog = false)
    public PoliceIdentityCheckResponseDTO identityCheck(@RequestBody PoliceIdentityCheckRequestDTO info) {

        LOGGER.info("身份核查接口入参:{}", JSON.toJSONString(info));

        List<Map<String, Object>> cxywcs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(info.getParamDTOList())) {
            for (PoliceIdentityCheckParamDTO policeIdentityCheckParamDTO : info.getParamDTOList()) {

                Map<String, Object> requestParamMap = new HashMap<>(16);
                List<Map<String, Object>> conditions = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(policeIdentityCheckParamDTO.getConditionDTOS())) {
                    for (PoliceIdentityCheckConditionDTO policeIdentityCheckConditionDTO : policeIdentityCheckParamDTO.getConditionDTOS()) {
                        Map<String, Object> condition = new HashMap<>(16);

                        Map<String, Object> checkCondition = new HashMap<>(16);
                        Map<String, Object> queryCondition = new HashMap<>(16);
                        checkCondition.put("xm", policeIdentityCheckConditionDTO.getXm());
                        queryCondition.put("gmsfhm", policeIdentityCheckConditionDTO.getGmsfhm());

                        condition.put("CheckCondition", checkCondition);
                        condition.put("QueryCondition", queryCondition);
                        conditions.add(condition);
                    }
                }
                requestParamMap.put("Conditions", conditions);

                Map<String, Object> bodyParamDetailMap = new HashMap<>(16);
                // 请求用户身份证号 发起查询的用户身份证号
                bodyParamDetailMap.put("bus_sre_idcard", Objects.isNull(userManagerUtils.getCurrentUser()) ? "" : userManagerUtils.getCurrentUser().getIdCard());
                // 登记部门统一信用代码
                bodyParamDetailMap.put("bus_sre_deptcode", busSreDeptCode);
                // 操作人所在单位地址
                bodyParamDetailMap.put("bus_sre_deptaddr", Objects.isNull(userManagerUtils.getCurrentUser()) ? "" : userManagerUtils.getCurrentUser().getAddress());
                // 调用接口使用的应用名称
                bodyParamDetailMap.put("bus_sre_appname", info.getAppname());
                // 业务类型代码
                bodyParamDetailMap.put("bus_sre_businesstypecode", policeIdentityCheckParamDTO.getBusinesstypecode());
                //业务类型名称 例：商品房首次登记
                bodyParamDetailMap.put("bus_sre_businesstypename", policeIdentityCheckParamDTO.getBusinesstypename());
                // 消息流水号 14位时间串+4位自定义索引码，0001开始，步长为1顺序累加
                bodyParamDetailMap.put("MessageSequence", DateUtil.formatDate(PATTERN1) + "0001");
                bodyParamDetailMap.put("RequestParam", requestParamMap);

                cxywcs.add(bodyParamDetailMap);
            }
        }


        Map<String, Object> bodyParamMap = new HashMap<>(16);
        // 查询业务类别
        bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.POLICE_IDENTITY_CHECK.getCode());
        bodyParamMap.put("cxywcs", cxywcs);

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
        paramMap.put("data", bodyParamMap);

        String response = sjptApiUtils.sendPostRequest(identityCheckUrl, paramMap);
        if (null == response) {
            return null;
        }
        JSONObject responseObject = JSON.parseObject(response);

        /**
         * 状态码
         * 0200 正常
         * 03XX-权限异常: 0301 鉴权服务执行失败;0302 没有访问权限;0303 部分访问权限
         * 04XX-服务异常: 0401 请求频率不合法;0402 过多的请求;0403 超时;0404 服务内部错误
         * 9900	其他异常
         *
         */
        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("status") && StringUtil.equals(headObject.getString("status"), "0200")) {
            JSONObject data = responseObject.getJSONObject("data");
            JSONArray resourceInfos = data.getJSONArray("ResourceInfos");
            List<PoliceIdentityCheckReturnDTO> returnDTOS = new ArrayList<>();
            for (int i = 0; i < resourceInfos.size(); i++) {
                JSONObject resourceInfo = resourceInfos.getJSONObject(i);
                JSONArray returnInfos = resourceInfo.getJSONArray("ReturnInfos");
                for (int j = 0; j < returnInfos.size(); j++) {
                    JSONObject returnInfo = returnInfos.getJSONObject(j);
                    PoliceIdentityCheckReturnDTO policeIdentityCheckReturnDTO = JSONObject.parseObject(returnInfo.toString(), PoliceIdentityCheckReturnDTO.class);
                    returnDTOS.add(policeIdentityCheckReturnDTO);
                }
            }
            PoliceIdentityCheckResponseDTO policeIdentityCheckResponseDTO = new PoliceIdentityCheckResponseDTO();
            policeIdentityCheckResponseDTO.setReturnInfos(returnDTOS);
            return policeIdentityCheckResponseDTO;
        } else {
            throw new AppException(headObject.getString("msg"));
        }
    }

    /**
     * 2.3民政部-婚姻登记信息查询服务接口
     *
     * @param info
     * @return String
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @ApiOperation(value = "婚姻登记信息查询")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "婚姻登记信息查询", dsfFlag = "MinZheng", requester = "BDC", responser = "MinZheng")
    @OpenApi(apiDescription = "婚姻登记信息查询", apiName = "", openLog = false)
    public CivilMarriageQueryResponseDTO marriageQuery(@RequestBody CivilMarriageQueryRequestDTO info) {
        LOGGER.info("婚姻登记信息查询:{}", JSON.toJSONString(info));

        CivilMarriageQueryResponseDTO civilMarriageQueryResponseDTO =new CivilMarriageQueryResponseDTO();
        civilMarriageQueryResponseDTO.setDataDTOList(new ArrayList<>());
        if (CollectionUtils.isNotEmpty(info.getParamDTOList())) {
            //婚姻接口只能单个查询,这里循环多次查询
            for (CivilMarriageQueryParamDTO civilMarriageQueryParamDTO : info.getParamDTOList()) {
                List<Map<String, Object>> cxywcs = new ArrayList<>();
                Map<String, Object> map = new HashMap<>(16);
                map.put("cert_num", civilMarriageQueryParamDTO.getCertNum());
                map.put("name", civilMarriageQueryParamDTO.getName());
                cxywcs.add(map);
                Map<String, Object> bodyParamMap = new HashMap<>(16);
                // 查询业务类别
                bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.CIVIL_MARRIAGE_QUERY.getCode());
                bodyParamMap.put("cxywcs", cxywcs);

                Map<String, Object> paramMap = new HashMap<>(16);
                paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
                paramMap.put("data", bodyParamMap);

                String response = sjptApiUtils.sendPostRequest(marriageQueryUrl, paramMap);
                if (null == response) {
                    return null;
                }
                JSONObject responseObject = JSON.parseObject(response);
                JSONObject headObject = responseObject.getJSONObject("head");
                if (headObject == null) {
                    throw new AppException("婚姻登记信息接口返回为空");
                }
                if (headObject.containsKey("status") && StringUtils.equalsIgnoreCase(headObject.getString("status"), "1")) {
                    JSONObject dataObject = responseObject.getJSONObject("data");
                    CivilMarriageQueryResponseDTO civilMarriageQueryResponse = JSON.toJavaObject(dataObject, CivilMarriageQueryResponseDTO.class);
                    if (CollectionUtils.isNotEmpty(civilMarriageQueryResponse.getDataDTOList())) {
                        civilMarriageQueryResponseDTO.getDataDTOList().addAll(civilMarriageQueryResponse.getDataDTOList());
                    }
                }else {
                    throw new AppException(headObject.getString("msg"));
                }
            }
        }

        if (CollectionUtils.isNotEmpty(civilMarriageQueryResponseDTO.getDataDTOList())) {
            civilMarriageQueryResponseDTO.setCount(civilMarriageQueryResponseDTO.getDataDTOList().size());
            civilMarriageQueryResponseDTO.getDataDTOList().forEach(civilMarriageQueryDataDTO -> {
                civilMarriageQueryDataDTO.setNationManText(zdDmToMcConvert(BdcZdEnum.GJ.name(), civilMarriageQueryDataDTO.getNationMan()));
                civilMarriageQueryDataDTO.setNationWomanText(zdDmToMcConvert(BdcZdEnum.GJ.name(), civilMarriageQueryDataDTO.getNationWoman()));
            });
        }
        return civilMarriageQueryResponseDTO;

//        if (headObject != null &&headObject.containsKey("status") && StringUtils.equalsIgnoreCase(headObject.getString("status"), "1")) {
//            JSONObject dataObject = responseObject.getJSONObject("data");
//            CivilMarriageQueryResponseDTO civilMarriageQueryResponseDTO = JSON.toJavaObject(dataObject, CivilMarriageQueryResponseDTO.class);
//
//        } else {
//            throw new AppException(headObject.getString("msg"));
//        }
    }

    /**
     * 2.5银保监会-金融许可证查询接口
     *
     * @param info
     * @return Object
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @ApiOperation(value = "金融许可证查询")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "金融许可证查询", dsfFlag = "YBJH", requester = "BDC", responser = "YBJH")
    @OpenApi(apiDescription = "金融许可证查询", apiName = "", openLog = false)
    public CbircFinancialQueryResponseDTO financialQuery(@RequestBody CbircFinancialQueryRequestDTO info) {
        LOGGER.info("金融许可证查询:{}", JSONObject.toJSONString(info));

        List<Map<String, Object>> cxywcs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(info.getParamDTOS())) {
            for (CbircFinancialQueryParamDTO cbircFinancialQueryParamDTO : info.getParamDTOS()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("typeId", cbircFinancialQueryParamDTO.getTypeId());
                map.put("certCode", cbircFinancialQueryParamDTO.getCertCode());
                cxywcs.add(map);
            }
        }

        Map<String, Object> bodyParamMap = new HashMap<>(16);
        // 查询业务类别
        bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.CBIRC_FINANCIAL_QUERY.getCode());
        bodyParamMap.put("cxywcs", cxywcs);

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
        paramMap.put("data", bodyParamMap);

        String response = sjptApiUtils.sendPostRequest(financialQueryUrl, paramMap);
        if (null == response) {
            return null;
        }
        JSONObject responseObject = JSON.parseObject(response);

        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("status") && StringUtils.equalsIgnoreCase(headObject.getString("status"), "200")) {
            JSONObject dataObject = responseObject.getJSONObject("data");
            CbircFinancialQueryResponseDTO cbircFinancialQueryResponseDTO = JSON.toJavaObject(dataObject, CbircFinancialQueryResponseDTO.class);
            cbircFinancialQueryResponseDTO.setTypeIdText(zdDmToMcConvert(BdcZdEnum.JRXKZCXLX.name(), cbircFinancialQueryResponseDTO.getTypeId()));
            return cbircFinancialQueryResponseDTO;
        } else {
            throw new AppException(headObject.getString("msg"));
        }
    }

    /**
     * 2.6中编办-事业单位登记信息（含机关、群团信息）查询接口
     *
     * @param info
     * @return Object
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @ApiOperation(value = "事业单位登记信息（含机关、群团信息）查询")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "事业单位登记信息（含机关、群团信息）查询", dsfFlag = "ZBB", requester = "BDC", responser = "ZBB")
    @OpenApi(apiDescription = "事业单位登记信息（含机关、群团信息）查询", apiName = "", openLog = false)
    public ScopsrOrganQueryResponseDTO organQuery(@RequestBody ScopsrOrganQueryRequestDTO info) {
        LOGGER.info("事业单位登记信息（含机关、群团信息）查询:{}", JSONObject.toJSONString(info));

        List<Map<String, Object>> cxywcs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(info.getParamDTOList())) {
            for (ScopsrOrganQueryParamDTO scopsrOrganQueryParamDTO : info.getParamDTOList()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("tyshxydm", scopsrOrganQueryParamDTO.getTyshxydm());
                map.put("name", scopsrOrganQueryParamDTO.getName());
                cxywcs.add(map);
            }
        }

        Map<String, Object> bodyParamMap = new HashMap<>(16);
        // 查询业务类别
        bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.SCOPSR_ORGAN_QUERY.getCode());
        bodyParamMap.put("cxywcs", cxywcs);

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
        paramMap.put("data", bodyParamMap);

        String response = sjptApiUtils.sendPostRequest(organQueryUrl, paramMap);
        if (null == response) {
            return null;
        }
        JSONObject responseObject = JSON.parseObject(response);

        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("status") && StringUtils.equalsIgnoreCase(headObject.getString("status"), "0200")) {
            JSONObject dataObject = responseObject.getJSONObject("data");
            ScopsrOrganQueryResponseDTO scopsrOrganQueryResponseDTO = JSON.toJavaObject(dataObject, ScopsrOrganQueryResponseDTO.class);
            if (CollectionUtils.isNotEmpty(scopsrOrganQueryResponseDTO.getDataDTOS())) {
                scopsrOrganQueryResponseDTO.getDataDTOS().forEach(scopsrOrganQueryDataDTO -> {
                    scopsrOrganQueryDataDTO.setYwlxText(zdDmToMcConvert(BdcZdEnum.ZBBSYDWYWLX.name(), scopsrOrganQueryDataDTO.getYwlx()));
                });
            }
            return scopsrOrganQueryResponseDTO;
        } else {
            throw new AppException(headObject.getString("msg"));
        }
    }

    /**
     * 2.7最高法-司法判决信息查询申请接口
     *
     * @return Object
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @ApiOperation(value = "司法判决信息查询申请")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "司法判决信息查询申请", dsfFlag = "ZGF", requester = "BDC", responser = "ZGF")
    @OpenApi(apiDescription = "司法判决信息查询申请", apiName = "", openLog = false)
    public SupremeCourtDecisionResponseResponseDTO decisionApply(@RequestBody SupremeCourtDecisionApplyRequestDTO info) {
        LOGGER.info("司法判决信息查询申请:{}", JSONObject.toJSONString(info));

        Map<String, Object> headParam = sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request));
        if (!headParam.containsKey("cxqqdh") || Objects.isNull(headParam.get("cxqqdh"))) {
            throw new AppException("cxqqdh不能为空!");
        }
        String cxqqdh = headParam.get("cxqqdh").toString();

        if (CollectionUtils.isNotEmpty(info.getParamDTOList())) {
            info.getParamDTOList().forEach(gxZrzybZgfSfpjxxcxsqParamDTO -> {
                gxZrzybZgfSfpjxxcxsqParamDTO.setCxqqdh(cxqqdh);
                //处理公共请求参数
                sjptApiUtils.handleSupremeCourtDecisionApplyParamDTO(gxZrzybZgfSfpjxxcxsqParamDTO);
            });
        }

        StringWriter writer = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SupremeCourtDecisionApplyRequestDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            writer = new StringWriter();
            marshaller.marshal(info, writer);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            throw new AppException("生成xml失败!");
        }
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + writer.toString();

        List<Map<String, Object>> cxywcs = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(16);
        map.put("xmlContent", xmlContent);
        cxywcs.add(map);

        Map<String, Object> bodyParamMap = new HashMap<>(16);
        // 查询业务类别
        bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.SUPREMECOURT_DECISION_APPLY.getCode());
        bodyParamMap.put("cxywcs", cxywcs);

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("head", headParam);
        paramMap.put("data", bodyParamMap);

        String response = sjptApiUtils.sendPostRequest(decisionApplyUrl, paramMap);
//        String response = "{\"data\":{\"data\":\"\\\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n\\\\n<RESOURCE>\\\\n    <BRIEF>\\\\n        <NUM>1</NUM>\\\\n    </BRIEF>\\\\n    <data>\\\\n        <t_aj>\\\\n            <c_stm>08aa43dba0f739a380de8df5d2c933b245b3</c_stm>\\\\n            <c_ah>（2018）豫1502民初33号</c_ah>\\\\n            <c_gshah>2018豫1502民初33</c_gshah>\\\\n            <t_aj_dsr>\\\\n                <c_mc></c_mc>\\\\n                <c_zjhm></c_zjhm>\\\\n            </t_aj_dsr>\\\\n            <t_aj_dsr>\\\\n                <c_mc></c_mc>\\\\n                <c_zjhm></c_zjhm>\\\\n            </t_aj_dsr>\\\\n            <t_aj_dsr>\\\\n                <c_mc></c_mc>\\\\n                <c_zjhm></c_zjhm>\\\\n            </t_aj_dsr>\\\\n            <t_ws>\\\\n                <c_nr>PCFET0NUWVBFIEhUTUwgUFVCTElDIC0vL1czQy8vRFREIEhUTUwgNC4wIFRyYW5zaXRpb25hbC8vRU4nPjxIVE1MPjxIRUFEPjxUSVRMRT48L1RJVExFPjwvSEVBRD48Qk9EWT48ZGl2IHN0eWxlPSdURVhULUFMSUdOOiBjZW50ZXI7IExJTkUtSEVJR0hUOiAyNXB0OyBNQVJHSU46IDAuNXB0IDBjbTsgRk9OVC1GQU1JTFk6IOm7keS9kzsgRk9OVC1TSVpFOiAxOHB0Oyc+5rKz5Y2X55yB5L+h6Ziz5biC5rWJ5rKz5Yy65Lq65rCR5rOV6ZmiPC9kaXY+PGRpdiBzdHlsZT0nVEVYVC1BTElHTjogY2VudGVyOyBMSU5FLUhFSUdIVDogMjVwdDsgTUFSR0lOOiAwLjVwdCAwY207IEZPTlQtRkFNSUxZOiDpu5HkvZM7IEZPTlQtU0laRTogMThwdDsnPuawkSDkuosg5YikIOWGsyDkuaY8L2Rpdj48ZGl2IHN0eWxlPSdURVhULUFMSUdOOiByaWdodDsgTElORS1IRUlHSFQ6IDI1cHQ7IE1BUkdJTjogMC41cHQgMGNtOyAgRk9OVC1GQU1JTFk6IOWui+S9kztGT05ULVNJWkU6IDE1cHQ7ICc+77yIMjAxOO+8ieixqzE1MDLmsJHliJ0zM+WPtzwvZGl2PjxkaXYgc3R5bGU9J0xJTkUtSEVJR0hUOiAyNXB0OyBURVhULUlOREVOVDogMzBwdDsgTUFSR0lOOiAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+5Y6f5ZGK5byg5piO5omN77yM55S377yM5rGJ5peP77yMMTk3MeW5tDTmnIgyNOaXpeWHuueUn++8jOS9j+S/oemYs+W4gua1ieays+WMuuOAgjwvZGl2PjxkaXYgc3R5bGU9J0xJTkUtSEVJR0hUOiAyNXB0OyBURVhULUlOREVOVDogMzBwdDsgTUFSR0lOOiAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+6KKr5ZGK5byg5piO5b6377yM55S377yM5rGJ5peP77yMMTk2NeW5tDnmnIgyOeaXpeWHuueUn++8jOS9j+S/oemYs+W4gua1ieays+WMuuOAgjwvZGl2PjxkaXYgc3R5bGU9J0xJTkUtSEVJR0hUOiAyNXB0OyBURVhULUlOREVOVDogMzBwdDsgTUFSR0lOOiAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+6KKr5ZGK5byg5Zu95a+M77yM55S377yM5rGJ5peP77yMMTk0NOW5tDTmnIg05pel5Ye655Sf77yM5L2P5L+h6Ziz5biC5rWJ5rKz5Yy644CCPC9kaXY+PGRpdiBzdHlsZT0nTElORS1IRUlHSFQ6IDI1cHQ7IFRFWFQtSU5ERU5UOiAzMHB0OyBNQVJHSU46IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7ljp/lkYrlvKDmmI7miY3or4nooqvlkYrlvKDmmI7lvrfjgIHlvKDlm73lr4zkuI3liqjkuqfnmbvorrDnuqDnurfkuIDmoYjvvIzmnKzpmaLlj5fnkIblkI7vvIzpgILnlKjnroDmmJPnqIvluo/vvIzlhazlvIDlvIDluq3ov5vooYzkuoblrqHnkIbjgILljp/lkYrlvKDmmI7miY3jgIHooqvlkYrlvKDmmI7lvrfjgIHlvKDlm73lr4zliLDluq3lj4LliqDor4norrzvvIzmnKzmoYjnjrDlt7LlrqHnkIbnu4jnu5PjgII8L2Rpdj48ZGl2IHN0eWxlPSdMSU5FLUhFSUdIVDogMjVwdDsgVEVYVC1JTkRFTlQ6IDMwcHQ7IE1BUkdJTjogMC41cHQgMGNtO0ZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTVwdDsnPuWOn+WRiuW8oOaYjuaJjeivieensO+8jOWOn+WRiuaIv+WxizE5OTLlubTlu7rkvZPogrLlnLrooqvmi4bov4HvvIzlkI7nlLHmnZHph4znmoTmnZHmsJHkuIDoh7TlkIzmhI/nmoTmlrnms5XliIbnmoTlroXln7rlnLDvvIzljp/lkYrliIblvpfkuKTpl7TlroXln7rlnLDvvIznlLHljp/lkYrkuIDkurrlh7rotYTmib/mi4Xlu7rotbfvvIzlu7rlpb3miL/lrZDlkI7ljp/lkYrmiorniLbkurLlvKDlm73lr4zlkozmr43kurLogpbln7nlh6TmjqXmnaXlkIzkvY/vvIwyMDAy5bm05Y6f5ZGK5Zyo5aSW5omT5bel77yM6KKr5ZGK5byg5Zu95a+M6K+06KaB5Yqe5oi/5Lqn6K+B77yM5Y6f5ZGK5bCx6K6p6KKr5ZGK5byg5Zu95a+M5Zyo5Y6f5ZGK5aa75a2Q5omL6YeM5ou/6ZKx77yM6K+05aW95Yqe5oi/5Lqn6K+B5YaZ5Y6f5ZGK55qE5ZCN5a2X77yM5oi/5Lqn6K+B5Yqe5aW95ZCO5Y6f5ZGK5aSa5qyh6KaB5ou/5Zue77yM6KKr5ZGK5byg5Zu95a+M6K+05oi/5Lqn6K+B5YWI5pS+5Zyo5LuW5omL6YeM5L+d566h552A77yM5LuW5piv54i25Lqy5Y6f5ZGK5Lmf5rKh5aSa55aR77yMMjAwNOW5tO+8jOiiq+WRiuW8oOaYjuW+t+WSjOaxpOadsOWkq+Wmu+aLv+edgOaIv+S6p+ivgeadpeWIhuWOn+WRiueahOaIv+WtkO+8jOWOn+WRiuaJjeefpemBk+aIv+S6p+ivgeS4iuWGmeeahOaYr+iiq+WRiuW8oOWbveWvjOeahOWQjeWtl++8jOi/meS6m+W5tOaIv+S6p+ivgeS4gOebtOWcqOiiq+WRiuW8oOaYjuW+t+Wkq+Wmu+aJi+mHjOaLv+edgO+8jOS7luS7rOivtOaIv+S6p+ivgeS4iuaYr+iiq+WRiuW8oOWbveWvjOeahOWQjeWtl++8jOaIv+WtkOWwseaYr+W8oOWbveWvjOeahOayoeWOn+WRiueahOS7ve+8jOaIv+enn+enn+mHkeS7luS7rOS5n+W3suaUtuWkmuW5tO+8jOWcqOWkluWkmuasoeaJrOiogOimgeaKiuaIv+WtkOWNluaOie+8jOimgeaKiuWOn+WRiuS4gOWutuWbm+WPo+aStei1sO+8jOiiq+WRiuW8oOaYjuW+t+WPiOaAguaBv+eItuS6suW8oOWbveWvjOWkmuasoeWPkeeUn+WGsueqgeOAguWOn+WRiuS4jueItuS6suW8oOWbveWvjOWboOaIv+S6p+W9kuWxnuWPkeeUn+WGsueqge+8jOWQjumCgOivt+e6ouaYn+adkeWnlOS5puiusOadqOi2heOAgeWJr+S5puiusOadqOWutuaJjeWSjOacrOWnk+WutuaXj+iuqeiiq+WRiuW8oOWbveWvjOOAgeW8oOaYjuW+t+Wkq+Wmu++8jOaKiuaIv+S6p+ivgeaLv+WHuuadpeWKoOS4iuWOn+WRiuaYr+aIv+S6p+ivgeS4iuaIv+S6p+WFseacieS6uu+8jOi/m+ihjOWkmuasoeiwg+ino+mDveaXoOa1juS6juS6i++8jOiiq+WRiuW8oOaYjuW+t+Wkq+Wmu+aDs+S7juS4reWIhuW+l+WIqeebiuiAjOaLv+WOn+WRiuWHuui1hOWKnueahOaIv+S6p+ivgeS4jee7me+8jOS7luS7rOWkq+Wmu+S7peWQhOenjeWAn+WPo+mYu+atouiwg+ino+OAguiiq+WRiuW8oOWbveWvjO+8jOW8oOaYjuW+t+Wkq+Wmu+i/meenjeihjOS4uuS4jeS7hea1qui0ueS6huWOn+WRiuW3qOWkp+eahOeyvuWKm+WSjOaXtumXtO+8jOWQjOaXtuS5n+S4pemHjeaNn+Wus+S6huWOn+WRiueahOWIqeebiuOAgue7vOS4iu+8jOS4uue7tOaKpOWOn+WRiuWQiOazleadg+ebiu+8jOeJueivieiHs+azlemZou+8jOivt+axguWIpOS7pOiiq+WRiuS4pOS6uueri+WNs+S4uuWOn+WRiuWKnueQhua2ieivieaIv+S6p+eahOaIv+S6p+ivgeS4iuWKoOaIv+S6p+WFseacieS6uu+8jOW5tuaJv+aLhei0ueeUqDYwMDDlhYPvvJvmnKzmoYjor4norrzotLnnlLHooqvlkYrmib/mi4XjgII8L2Rpdj48ZGl2IHN0eWxlPSdMSU5FLUhFSUdIVDogMjVwdDsgVEVYVC1JTkRFTlQ6IDMwcHQ7IE1BUkdJTjogMC41cHQgMGNtO0ZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTVwdDsnPuiiq+WRiuW8oOaYjuW+t+OAgeW8oOWbveWvjOi+qeensO+8jOS4gOWOn+WRiuivieensO+8muKAnOWOn+WRiuaIv+WxizE5OTLlubTlu7rkvZPogrLlnLrooqvmi4bov4HvvIzlkI7mnInmnZHph4znmoTmnZHmsJHkuIDoh7TlkIzmhI/nmoTmlrnms5XliIbnmoTlroXln7rlnLDjgILljp/lkYrliIblvpfkuKTpl7TlroXln7rlnLDvvIznlLHljp/lkYrkuIDkurrlh7rotYTmib/mi4Xlu7rotbfjgILigJ3ov5nnp43or7Tms5XmmK/plJnor6/nmoTvvIwxOTky5bm05byg5piO5omN5YiaMjDlsoHvvIzlk6rmnInkvY/miL/kuKTpl7TvvJ/lnKgxOTky5bm05byg5piO5omN6L+Y6Z2g562U6L6p5Lq655Sf5rS777yM5ZOq5p2l55qE5pyJ6IO95Yqb5Y+C5LiO5bu65oi/77yf562U6L6p5Lq65byg5Zu95a+M5ZyoMTk5MuW5tOato+aYr+WjruW5tOaXtuacn++8jOWIhuW+l+WuheWfuuWcsOS4pOmXtO+8jOS4quS6uuWHuui1hOW7uuaIkOS6hueOsOWcqOeahOaIv+WtkOWFremXtOOAgjIwMDPlubTnrZTovqnkurrlvKDlm73lr4zkvp3ms5Xlip7nkIbkuobmiL/lsYvmiYDmnInmnYPor4HvvIzlkozlprvlrZDogpbln7nlh6Tns7vor6XmiL/lsYvnmoTlkIjms5XlhbHmnInkurrjgILov5nmnJ/pl7TnrZTovqnkurrlhajlrrbnmoTnlJ/mtLvmmK/lubPpnZnnmoTvvIwyMDA55bm05byg5piO5omN5L6d552A6Ieq5bex5bm06L275bCG562U6L6p5Lq65byg5Zu95a+M5pK15Ye65LqG5a626Zeo77yM5oqi5Y2g5LqG562U6L6p5Lq65byg5Zu95a+M55qE5oi/5a2Q77yM6L2v56aB5LqG6IKW5Z+55Yek44CCMjAxMeW5tDEw5pyIMzHml6XlvKDmmI7miY3kuI3orrLpgZPkuYnlkozoia/lv4PvvIzov5jmiornrZTovqnkurrlvKDlm73lr4zmiZPkvKTvvIjor6bop4HmtYnmsrPljLrkurrmsJHms5XpmaLvvIgyMDEy77yJ5L+h5rWJ5rCR5Yid5a2X56ysMTY5NeWPt+awkeS6i+WIpOWGs+S5pu+8ieOAgumaj+WQjuW8oOaYjuaJjeWSjOWmu+WtkOWGmeS4i+KAnOmBk+atieS5puKAnei/meS4gOezu+WIl+eahOS6i+WunuivgeaYjuS6huW8oOaYjuaJjeaYr+S4uuS6hui+vuWIsOaKouWNoOetlOi+qeS6uuW8oOWbveWvjOeahOaIv+S6p+eahOebrueahOOAgueOsOWcqOWPiOivtOetlOi+qeS6uuW8oOWbveWvjOeahOaIv+WtkOaYr+S7luS4gOS6uuaJgOW7uu+8jOecn+aYr+W8peWkqeWkp+iwjuOAguS6jOOAgeWOn+WRiui/mOivieensO+8muKAnDIwMDTlubTooqvlkYrlvKDmmI7lvrflkozmsaTmnbDlpKvlprvmi7/nnYDmiL/kuqfor4HmnaXliIbljp/lkYrnmoTmiL/lrZDjgILigJ3lvKDmmI7miY3kuLrkuoblvLrljaDliLDniLbkurLlvKDlm73lr4znmoTnp4HmnInmiL/kuqfvvIzku4DkuYjnno7or53pg73og73nvJbvvIHnrZTovqnkurrlvKDmmI7lvrflpKvlprvmnInoh6rlt7HnmoTmiL/kuqfvvIzku4DkuYjml7blgJnliIbov4fliKvkurrnmoTmiL/kuqc/5byg5piO5omN5piv5oqi5Y2g5Yir5Lq65oi/5Lqn55qE5YW35L2T5Lq677yMMjAwOeW5tOW8oOaYjuaJjeS4uuS6huWNoOacieetlOi+qeS6uueItuS6sueahOaIv+S6p+ebl+i1sOS6hueItuS6suW8oOWbveWvjOeahOKAnOWbveacieWcn+WcsOS9v+eUqOivgeKAne+8iOivpuingeS/oemYs+aXpeaKpeeahOWFrOWRiu+8ieOAguS4ieOAgeWbveWutuihjOaUv+acuuWFs+migeWPkeeahOKAnOWcn+WcsOS9v+eUqOivgeKAneWSjOKAnOaIv+S6p+ivgeKAneaYr+ivgeaYjuaJgOacieadg+eahOWQiOazleWHreivge+8jOWPl+azleW+i+S/neaKpO+8jOetlOi+qeS6uuW8oOWbveWvjOeahOaIv+S6p+ivgeiHs+S7iuW3sumihuWPljE15bm05pe26Ze077yM5YW35pyJ55u45a+555qE56iz5a6a5oCn77yM5Y+X5rOV5b6L5L+d5oqk77yM5LiN5a655Lu75L2V5Lq66Z2e5rOV5L6154qv44CC562U6L6p5Lq65byg5Zu95a+M5Li65LqG5Li75byg5p2D5Yip5L6d5rOV6LW36K+J5LqG5byg5piO5omN77yM5q2k5qGI5rWJ5rKz5Yy65Lq65rCR5rOV6Zmi5q2j5Zyo6YeN5a6h44CC562U6L6p5Lq65byg5Zu95a+M55qE5p2D55uK5Y+X5rOV5b6L5L+d5oqk77yM5LiN5a655byg5piO5omN6ZqP5oSP5L6154qv44CC57u85LiK77yM5bqU6amz5Zue5Y6f5ZGK55qE6K+J6K686K+35rGC44CCPC9kaXY+PGRpdiBzdHlsZT0nTElORS1IRUlHSFQ6IDI1cHQ7IFRFWFQtSU5ERU5UOiAzMHB0OyBNQVJHSU46IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7nu4/lrqHnkIbmn6XmmI7vvIzooqvlkYrlvKDlm73lr4zkuI7ljp/lkYrlvKDmmI7miY3jgIHooqvlkYrlvKDmmI7lvrfns7vniLblrZDlhbPns7vjgILooqvlkYrlvKDlm73lr4zkuI7moYjlpJbkurrogpbln7nlh6Tns7vlpKvlprvlhbPns7vvvIwyMDAz5bm0MeaciDbml6XooqvlkYrlvKDlm73lr4zlj5blvpflnZDokL3kuo7mtYnmtYnmsrPljLrDl8OXw5fDl+e7hOadg+ivgeWPt+S4uuS/oeaIv+adg+ivgea1iea1ieays+WMuuWtl+esrMOXw5flj7flsYvnmoTmiYDmnInmnYPor4HvvIzmiL/lsYvmiYDDl8OX6K6w5Li65byg5Zu95a+M77yM5YWx5pyJ5Lq66IKW5Z+55Yek77yM5bu6562R6Z2i56evMjg1Ljg45bmz5pa557Gz44CC5ZCO5Zug5Li65oi/5Lqn5b2S5bGe6Zeu6aKY5Y6f5ZGK5byg5piO5omN5ZKM6KKr5ZGK5byg5Zu95a+M44CB5byg5piO5b635aSa5qyh5Y+R55Sf57qg57q377yM5ZCO5Y6f5ZGK6K+J6Iez5rOV6Zmi77yM6K+35rGC5Zyo6K+l5oi/5Lqn6K+B5LiK5Yqg5LiK5Y6f5ZGK5byg5piO5omN5Li65YWx5pyJ5p2D5Lq644CC5Li65q2k77yM5Y6f5ZGK5o+Q5Lqk5LqG5L+h6Ziz5biC5oi/5Lqn5qGj5qGI6aaG5Ye65YW355qE5oi/5Lqn5p+l5qGj6KGo44CB5oi357GN6K+B5piO44CB6IKW5Z+55Yek55qE6K+B6KiA44CB6IOh5bu65Yab55qE6K+B6KiA44CB5L+h6Ziz5biC5rWJ5rKz5Yy65LqU5pif6KGX6YGT5Yqe5LqL5aSE57qi5pif56S+5Yy65bGF5rCR5aeU5ZGY5Lya55qE6K+B5piO5Y+K5rWJ5rKz5Yy65rOV6Zmi77yIMjAxN++8ieixqzE1MDLmsJHliJ0yNzI35Y+35rCR5LqL5Yik5Yaz5Lmm77yM55So5Lul6K+B5piO6K+l5oi/5bGL5piv5Y6f5ZGK5byg5piO5omN5Ye66LWE5bu65oiQ77yM5YW25pys5Lq65bqU5piv5oi/5bGL55qE5YWx5pyJ5p2D5Lq644CC6KKr5ZGK5o+Q5Lqk5LqG5oi/5Lqn6K+B44CB6KKr5ZGK5byg5Zu95a+M55uW5oi/5a2Q55qE56yU6K6w5pys44CB5L+h6Ziz5pel5oql55m75oql5aOw5piO44CB5byg5piO57qi55qE6K+B6KiA44CB5a2Z5paM55Sr55qE6K+B6KiA5Lul5Y+K6YGT5q2J5Lmm55So5Lul6K+B5piO5oi/5bGL5piv55Sx6KKr5ZGK5byg5Zu95a+M5Ye66LWE5bu65oiQ77yM5bqU6K+l6amz5Zue5Y6f5ZGK5byg5piO5omN55qE6K+J6K686K+35rGC44CCPC9kaXY+PGRpdiBzdHlsZT0nTElORS1IRUlHSFQ6IDI1cHQ7IFRFWFQtSU5ERU5UOiAzMHB0OyBNQVJHSU46IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7mnKzpmaLorqTkuLrvvIzkuI3liqjkuqfmnYPlsZ7or4HkuabmmK/mnYPliKnkurrkuqvmnInor6XkuI3liqjkuqfnianmnYPnmoTor4HmmI7jgILooqvlkYrlvKDlm73lr4zmi6XmnInkuonorq7miL/kuqfnmoTmiL/lsYvmiYDmnInmnYPor4HvvIzor4HkuabnmbvorrDooajmmI7mjIHor4HkurrvvIjmiYDDl8OX5Li65byg5Zu95a+M77yM5YWx5pyJ5p2D5Lq65Li66IKW5Z+55Yek77yM5Y6f5ZGK5byg5piO5omN6KaB5rGC5Zyo6K+l5oi/5Lqn6K+B5Yqg5LiK6Ieq5bex55qE5ZCN5a2X77yM5bqU5o+Q5Lqk6K+B5o2u6K+B5piO5YW25piv5YWx5pyJ5p2D5Lq65oiW5oyB6K+B5Lq677yI5omAw5fDl+W8oOWbveWvjOWQjOaEj++8jOS9huacrOahiOS4reiiq+WRiuW8oOWbveWvjOaLkue7neWOn+WRiuWKoOWQjeivt+axgu+8jOiAjOWOn+WRiuaPkOS6pOeahOivgeaNruW5tuS4jeiDveivgeaYjuWFtuaYr+a2ieahiOaIv+Wxi+eahOWFseacieadg+S6uu+8jOaVheWvueWOn+WRiueahOivieiuvOivt+axgu+8jOacrOmZouS4jeS6iOaUr+aMgeOAgue7vOS4iu+8jOS+neeFp+OAiuS4reWSjOS6uuawkeWFseWSjOWbveawkeS6i+ivieiuvOazleOAi+esrOWFreWNgeWbm+adoeeahOinhOWumu+8jOWIpOWGs+WmguS4i++8mjwvZGl2PjxkaXYgc3R5bGU9J0xJTkUtSEVJR0hUOiAyNXB0OyBURVhULUlOREVOVDogMzBwdDsgTUFSR0lOOiAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+6amz5Zue5Y6f5ZGK5byg5piO5omN55qE6K+J6K686K+35rGC44CCPC9kaXY+PGRpdiBzdHlsZT0nTElORS1IRUlHSFQ6IDI1cHQ7IFRFWFQtSU5ERU5UOiAzMHB0OyBNQVJHSU46IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7mnKzmoYjlj5fnkIbotLkxMDDlhYPvvIznlLHljp/lkYrlvKDmmI7miY3mib/mi4XjgII8L2Rpdj48ZGl2IHN0eWxlPSdMSU5FLUhFSUdIVDogMjVwdDsgVEVYVC1JTkRFTlQ6IDMwcHQ7IE1BUkdJTjogMC41cHQgMGNtO0ZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTVwdDsnPuWmguS4jeacjeacrOWIpOWGs++8jOWPr+S7peWcqOWIpOWGs+S5pumAgei+vuS5i+aXpei1t+WNgeS6lOaXpeWGhe+8jOWQkeacrOmZoumAkuS6pOS4iuivieeKtu+8jOW5tuaMieWvueaWueW9k+S6i+S6uueahOS6uuaVsOaPkOWHuuWJr+acrO+8jOS4iuivieS6juays+WNl+ecgeS/oemYs+W4guS4ree6p+S6uuawkeazlemZouOAgjwvZGl2PjxkaXYgc3R5bGU9J1RFWFQtQUxJR046IHJpZ2h0OyBMSU5FLUhFSUdIVDogMjVwdDsgTUFSR0lOOiAwLjVwdCAzNnB0IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7lrqHliKTlkZjjgIDjgIDmtKrpn6w8L2Rpdj48ZGl2IHN0eWxlPSdURVhULUFMSUdOOiByaWdodDsgTElORS1IRUlHSFQ6IDI1cHQ7IE1BUkdJTjogMC41cHQgMzZwdCAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+5LqM44CH5LiA5YWr5bm05Zub5pyI5Y2B5LqM5pelPC9kaXY+PGRpdiBzdHlsZT0nVEVYVC1BTElHTjogcmlnaHQ7IExJTkUtSEVJR0hUOiAyNXB0OyBNQVJHSU46IDAuNXB0IDM2cHQgMC41cHQgMGNtO0ZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTVwdDsnPuS5puiusOWRmOOAgOOAgOm7hOS4uTwvZGl2PjwvQk9EWT48L0hUTUw+</c_nr>\\\\n            </t_ws>\\\\n        </t_aj>\\\\n    </data>\\\\n</RESOURCE>\\\\n\\\"\"},\"head\":{\"businessNumber\":\"320600\",\"cxqqdh\":\"20210316320600000017\",\"msg\":\"司法判决信息回调成功\",\"requestType\":\"GF99\",\"status\":\"0200\"}}\n";

        JSONObject responseObject = JSONObject.parseObject(response);
        if(null == responseObject){
            return null;
        }
        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("status") && StringUtils.equalsIgnoreCase(headObject.getString("status"), "0200")) {
            JSONObject dataObject = responseObject.getJSONObject("data");

            String xmlData = dataObject.getString("data");
            if (StringUtils.isBlank(xmlData)) {
                return null;
            }
            //去掉首尾双引号
            if (xmlData.startsWith("\"") && xmlData.endsWith("\"")) {
                xmlData = xmlData.substring(1, xmlData.length() - 1);
            }

            xmlData = StringEscapeUtils.unescapeJava(xmlData);

            LOGGER.info("司法判决信息结果反馈 xml 数据:{}", xmlData);

            SupremeCourtDecisionResponseResponseDTO responseResponseDTO = new SupremeCourtDecisionResponseResponseDTO();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(SupremeCourtDecisionResponseResponseDTO.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader sr = new StringReader(xmlData);
                responseResponseDTO = (SupremeCourtDecisionResponseResponseDTO) jaxbUnmarshaller.unmarshal(sr);
                SupremeCourtDecisionResponseDataDTO dataDTO = responseResponseDTO.getDataDTO();
                if (Objects.nonNull(dataDTO)) {
                    if (CollectionUtils.isNotEmpty(dataDTO.getAjDTOList())) {
                        dataDTO.getAjDTOList().forEach(supremeCourtDecisionResponseAjDTO -> {
                            SupremeCourtDecisionResponseWsDTO wsDTO = supremeCourtDecisionResponseAjDTO.getWsDTO();
                            if (Objects.nonNull(wsDTO) && Objects.nonNull(wsDTO.getNr())) {
                                String content = wsDTO.getNr();
                                LOGGER.info("司法判决信息结果反馈 content 数据:{}", content);
                                content = StringEscapeUtils.unescapeJava(content);
                                LOGGER.info("司法判决信息结果反馈 content 数据,去掉转义:{}", content);
                                byte[] bytes = Base64.getDecoder().decode(content);
                                LOGGER.info("base转码后的文书内容:{}", new String(bytes, StandardCharsets.UTF_8));
                                wsDTO.setNr(new String(bytes, StandardCharsets.UTF_8));
                            }
                        });
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

            return responseResponseDTO;
        } else {
            throw new AppException(headObject.getString("msg"));
        }
    }

    /**
     * 2.8最高法-司法判决信息结果反馈接口
     *
     * @param info
     * @return Object
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    @ApiOperation(value = "司法判决信息结果反馈")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "info", paramType = "body")})
    @DsfLog(logEventName = "司法判决信息结果反馈", dsfFlag = "ZGF", requester = "BDC", responser = "ZGF")
    @OpenApi(apiDescription = "司法判决信息结果反馈", apiName = "", openLog = false)
    public SupremeCourtDecisionResponseResponseDTO decisionResponse(@RequestBody SupremeCourtDecisionResponseRequestDTO info) {
        LOGGER.info("司法判决信息结果反馈:{}", JSONObject.toJSONString(info));

        List<Map<String, Object>> cxywcs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(info.getCxqqdhList())) {
            info.getCxqqdhList().forEach(cxqqdh -> {
                Map<String, Object> map = new HashMap<>();
                map.put("cxqqdh", cxqqdh);
                cxywcs.add(map);
            });
        }

        Map<String, Object> bodyParamMap = new HashMap<>(16);
        // 查询业务类别
        bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.SUPREMECOURT_DECISION_RESPONSE.getCode());
        bodyParamMap.put("cxywcs", cxywcs);

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
        paramMap.put("data", bodyParamMap);

        String response = sjptApiUtils.sendPostRequest(decisionResponseUrl, paramMap);
//        String response = "{\n" +
//                "\"data\": {\"data\":\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n<RESOURCE>\\\\n<BRIEF>\\\\n<NUM>0<\\/NUM>\\\\n<\\/BRIEF>\\\\n<data>\\\\n<t_aj>\\\\n<c_stm>08aa43dba0f739a380de8df5d2c933b245b3<\\/c_stm>\\\\n<c_ah>（2018）豫1502民初33号<\\/c_ah>\\\\n<c_gshah>2018豫<\\/c_gshah>\\\\n<t_aj_dsr>\\\\n<c_mc>当事人1<\\/c_mc>\\\\n<c_zjhm>123455<\\/c_zjhm>\\\\n<\\/t_aj_dsr>\\\\n<t_aj_dsr>\\\\n<c_mc>当事人2<\\/c_mc>\\\\n<c_zjhm>123456<\\/c_zjhm>\\\\n<\\/t_aj_dsr>\\\\n<t_aj_dsr>\\\\n<c_mc>当事人3<\\/c_mc>\\\\n<c_zjhm>123457<\\/c_zjhm>\\\\n<\\/t_aj_dsr>\\\\n<t_ws><c_nr>5LuK5aSp5aSp5rCU5pm06KOF5aSa5LqR<\\/c_nr><\\/t_ws><\\/t_aj><\\/data>\\\\n<\\/RESOURCE>\"},\n" +
//                "   \"head\":{\n" +
//                "           \"businessNumber\": \"111111111\",\n" +
//                "           \"cxqqdh\": \"20191215320000222222\",\n" +
//                "           \"msg\": \"司法判决信息回调成功\",\n" +
//                "           \"requestType\": \"GF99\",\n" +
//                "           \"status\": \"0200\"\n" +
//                "   }\n" +
//                "}";

//        String response = "{\n" +
//                "    \"data\":{\n" +
//                "        \"data\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n\\n<RESOURCE>\\n    <BRIEF>\\n        <NUM>0</NUM>\\n    </BRIEF>\\n    <data/>\\n</RESOURCE>\\n\"\n" +
//                "    },\n" +
//                "    \"head\":{\n" +
//                "        \"businessNumber\":\"320600\",\n" +
//                "        \"cxqqdh\":\"20210316320600000005\",\n" +
//                "        \"msg\":\"司法判决信息回调成功\",\n" +
//                "        \"requestType\":\"GF99\",\n" +
//                "        \"status\":\"0200\"\n" +
//                "    }\n" +
//                "}";

//        String response = "{\n" +
//                "    \"data\":{\n" +
//                "        \"data\":\"\"\n" +
//                "    },\n" +
//                "    \"head\":{\n" +
//                "        \"businessNumber\":\"320600\",\n" +
//                "        \"cxqqdh\":\"20210316320600000012\",\n" +
//                "        \"msg\":\"司法判决信息回调成功\",\n" +
//                "        \"requestType\":\"GF99\",\n" +
//                "        \"status\":\"0200\"\n" +
//                "    }\n" +
//                "}";

        //String response = "{\"data\":{\"data\":\"\\\"<?xml version=\\\\\\\"1.0\\\\\\\" encoding=\\\\\\\"UTF-8\\\\\\\"?>\\\\n\\\\n<RESOURCE>\\\\n    <BRIEF>\\\\n        <NUM>1</NUM>\\\\n    </BRIEF>\\\\n    <data>\\\\n        <t_aj>\\\\n            <c_stm>08aa43dba0f739a380de8df5d2c933b245b3</c_stm>\\\\n            <c_ah>（2018）豫1502民初33号</c_ah>\\\\n            <c_gshah>2018豫1502民初33</c_gshah>\\\\n            <t_aj_dsr>\\\\n                <c_mc></c_mc>\\\\n                <c_zjhm></c_zjhm>\\\\n            </t_aj_dsr>\\\\n            <t_aj_dsr>\\\\n                <c_mc></c_mc>\\\\n                <c_zjhm></c_zjhm>\\\\n            </t_aj_dsr>\\\\n            <t_aj_dsr>\\\\n                <c_mc></c_mc>\\\\n                <c_zjhm></c_zjhm>\\\\n            </t_aj_dsr>\\\\n            <t_ws>\\\\n                <c_nr>PCFET0NUWVBFIEhUTUwgUFVCTElDIC0vL1czQy8vRFREIEhUTUwgNC4wIFRyYW5zaXRpb25hbC8vRU4nPjxIVE1MPjxIRUFEPjxUSVRMRT48L1RJVExFPjwvSEVBRD48Qk9EWT48ZGl2IHN0eWxlPSdURVhULUFMSUdOOiBjZW50ZXI7IExJTkUtSEVJR0hUOiAyNXB0OyBNQVJHSU46IDAuNXB0IDBjbTsgRk9OVC1GQU1JTFk6IOm7keS9kzsgRk9OVC1TSVpFOiAxOHB0Oyc+5rKz5Y2X55yB5L+h6Ziz5biC5rWJ5rKz5Yy65Lq65rCR5rOV6ZmiPC9kaXY+PGRpdiBzdHlsZT0nVEVYVC1BTElHTjogY2VudGVyOyBMSU5FLUhFSUdIVDogMjVwdDsgTUFSR0lOOiAwLjVwdCAwY207IEZPTlQtRkFNSUxZOiDpu5HkvZM7IEZPTlQtU0laRTogMThwdDsnPuawkSDkuosg5YikIOWGsyDkuaY8L2Rpdj48ZGl2IHN0eWxlPSdURVhULUFMSUdOOiByaWdodDsgTElORS1IRUlHSFQ6IDI1cHQ7IE1BUkdJTjogMC41cHQgMGNtOyAgRk9OVC1GQU1JTFk6IOWui+S9kztGT05ULVNJWkU6IDE1cHQ7ICc+77yIMjAxOO+8ieixqzE1MDLmsJHliJ0zM+WPtzwvZGl2PjxkaXYgc3R5bGU9J0xJTkUtSEVJR0hUOiAyNXB0OyBURVhULUlOREVOVDogMzBwdDsgTUFSR0lOOiAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+5Y6f5ZGK5byg5piO5omN77yM55S377yM5rGJ5peP77yMMTk3MeW5tDTmnIgyNOaXpeWHuueUn++8jOS9j+S/oemYs+W4gua1ieays+WMuuOAgjwvZGl2PjxkaXYgc3R5bGU9J0xJTkUtSEVJR0hUOiAyNXB0OyBURVhULUlOREVOVDogMzBwdDsgTUFSR0lOOiAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+6KKr5ZGK5byg5piO5b6377yM55S377yM5rGJ5peP77yMMTk2NeW5tDnmnIgyOeaXpeWHuueUn++8jOS9j+S/oemYs+W4gua1ieays+WMuuOAgjwvZGl2PjxkaXYgc3R5bGU9J0xJTkUtSEVJR0hUOiAyNXB0OyBURVhULUlOREVOVDogMzBwdDsgTUFSR0lOOiAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+6KKr5ZGK5byg5Zu95a+M77yM55S377yM5rGJ5peP77yMMTk0NOW5tDTmnIg05pel5Ye655Sf77yM5L2P5L+h6Ziz5biC5rWJ5rKz5Yy644CCPC9kaXY+PGRpdiBzdHlsZT0nTElORS1IRUlHSFQ6IDI1cHQ7IFRFWFQtSU5ERU5UOiAzMHB0OyBNQVJHSU46IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7ljp/lkYrlvKDmmI7miY3or4nooqvlkYrlvKDmmI7lvrfjgIHlvKDlm73lr4zkuI3liqjkuqfnmbvorrDnuqDnurfkuIDmoYjvvIzmnKzpmaLlj5fnkIblkI7vvIzpgILnlKjnroDmmJPnqIvluo/vvIzlhazlvIDlvIDluq3ov5vooYzkuoblrqHnkIbjgILljp/lkYrlvKDmmI7miY3jgIHooqvlkYrlvKDmmI7lvrfjgIHlvKDlm73lr4zliLDluq3lj4LliqDor4norrzvvIzmnKzmoYjnjrDlt7LlrqHnkIbnu4jnu5PjgII8L2Rpdj48ZGl2IHN0eWxlPSdMSU5FLUhFSUdIVDogMjVwdDsgVEVYVC1JTkRFTlQ6IDMwcHQ7IE1BUkdJTjogMC41cHQgMGNtO0ZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTVwdDsnPuWOn+WRiuW8oOaYjuaJjeivieensO+8jOWOn+WRiuaIv+WxizE5OTLlubTlu7rkvZPogrLlnLrooqvmi4bov4HvvIzlkI7nlLHmnZHph4znmoTmnZHmsJHkuIDoh7TlkIzmhI/nmoTmlrnms5XliIbnmoTlroXln7rlnLDvvIzljp/lkYrliIblvpfkuKTpl7TlroXln7rlnLDvvIznlLHljp/lkYrkuIDkurrlh7rotYTmib/mi4Xlu7rotbfvvIzlu7rlpb3miL/lrZDlkI7ljp/lkYrmiorniLbkurLlvKDlm73lr4zlkozmr43kurLogpbln7nlh6TmjqXmnaXlkIzkvY/vvIwyMDAy5bm05Y6f5ZGK5Zyo5aSW5omT5bel77yM6KKr5ZGK5byg5Zu95a+M6K+06KaB5Yqe5oi/5Lqn6K+B77yM5Y6f5ZGK5bCx6K6p6KKr5ZGK5byg5Zu95a+M5Zyo5Y6f5ZGK5aa75a2Q5omL6YeM5ou/6ZKx77yM6K+05aW95Yqe5oi/5Lqn6K+B5YaZ5Y6f5ZGK55qE5ZCN5a2X77yM5oi/5Lqn6K+B5Yqe5aW95ZCO5Y6f5ZGK5aSa5qyh6KaB5ou/5Zue77yM6KKr5ZGK5byg5Zu95a+M6K+05oi/5Lqn6K+B5YWI5pS+5Zyo5LuW5omL6YeM5L+d566h552A77yM5LuW5piv54i25Lqy5Y6f5ZGK5Lmf5rKh5aSa55aR77yMMjAwNOW5tO+8jOiiq+WRiuW8oOaYjuW+t+WSjOaxpOadsOWkq+Wmu+aLv+edgOaIv+S6p+ivgeadpeWIhuWOn+WRiueahOaIv+WtkO+8jOWOn+WRiuaJjeefpemBk+aIv+S6p+ivgeS4iuWGmeeahOaYr+iiq+WRiuW8oOWbveWvjOeahOWQjeWtl++8jOi/meS6m+W5tOaIv+S6p+ivgeS4gOebtOWcqOiiq+WRiuW8oOaYjuW+t+Wkq+Wmu+aJi+mHjOaLv+edgO+8jOS7luS7rOivtOaIv+S6p+ivgeS4iuaYr+iiq+WRiuW8oOWbveWvjOeahOWQjeWtl++8jOaIv+WtkOWwseaYr+W8oOWbveWvjOeahOayoeWOn+WRiueahOS7ve+8jOaIv+enn+enn+mHkeS7luS7rOS5n+W3suaUtuWkmuW5tO+8jOWcqOWkluWkmuasoeaJrOiogOimgeaKiuaIv+WtkOWNluaOie+8jOimgeaKiuWOn+WRiuS4gOWutuWbm+WPo+aStei1sO+8jOiiq+WRiuW8oOaYjuW+t+WPiOaAguaBv+eItuS6suW8oOWbveWvjOWkmuasoeWPkeeUn+WGsueqgeOAguWOn+WRiuS4jueItuS6suW8oOWbveWvjOWboOaIv+S6p+W9kuWxnuWPkeeUn+WGsueqge+8jOWQjumCgOivt+e6ouaYn+adkeWnlOS5puiusOadqOi2heOAgeWJr+S5puiusOadqOWutuaJjeWSjOacrOWnk+WutuaXj+iuqeiiq+WRiuW8oOWbveWvjOOAgeW8oOaYjuW+t+Wkq+Wmu++8jOaKiuaIv+S6p+ivgeaLv+WHuuadpeWKoOS4iuWOn+WRiuaYr+aIv+S6p+ivgeS4iuaIv+S6p+WFseacieS6uu+8jOi/m+ihjOWkmuasoeiwg+ino+mDveaXoOa1juS6juS6i++8jOiiq+WRiuW8oOaYjuW+t+Wkq+Wmu+aDs+S7juS4reWIhuW+l+WIqeebiuiAjOaLv+WOn+WRiuWHuui1hOWKnueahOaIv+S6p+ivgeS4jee7me+8jOS7luS7rOWkq+Wmu+S7peWQhOenjeWAn+WPo+mYu+atouiwg+ino+OAguiiq+WRiuW8oOWbveWvjO+8jOW8oOaYjuW+t+Wkq+Wmu+i/meenjeihjOS4uuS4jeS7hea1qui0ueS6huWOn+WRiuW3qOWkp+eahOeyvuWKm+WSjOaXtumXtO+8jOWQjOaXtuS5n+S4pemHjeaNn+Wus+S6huWOn+WRiueahOWIqeebiuOAgue7vOS4iu+8jOS4uue7tOaKpOWOn+WRiuWQiOazleadg+ebiu+8jOeJueivieiHs+azlemZou+8jOivt+axguWIpOS7pOiiq+WRiuS4pOS6uueri+WNs+S4uuWOn+WRiuWKnueQhua2ieivieaIv+S6p+eahOaIv+S6p+ivgeS4iuWKoOaIv+S6p+WFseacieS6uu+8jOW5tuaJv+aLhei0ueeUqDYwMDDlhYPvvJvmnKzmoYjor4norrzotLnnlLHooqvlkYrmib/mi4XjgII8L2Rpdj48ZGl2IHN0eWxlPSdMSU5FLUhFSUdIVDogMjVwdDsgVEVYVC1JTkRFTlQ6IDMwcHQ7IE1BUkdJTjogMC41cHQgMGNtO0ZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTVwdDsnPuiiq+WRiuW8oOaYjuW+t+OAgeW8oOWbveWvjOi+qeensO+8jOS4gOWOn+WRiuivieensO+8muKAnOWOn+WRiuaIv+WxizE5OTLlubTlu7rkvZPogrLlnLrooqvmi4bov4HvvIzlkI7mnInmnZHph4znmoTmnZHmsJHkuIDoh7TlkIzmhI/nmoTmlrnms5XliIbnmoTlroXln7rlnLDjgILljp/lkYrliIblvpfkuKTpl7TlroXln7rlnLDvvIznlLHljp/lkYrkuIDkurrlh7rotYTmib/mi4Xlu7rotbfjgILigJ3ov5nnp43or7Tms5XmmK/plJnor6/nmoTvvIwxOTky5bm05byg5piO5omN5YiaMjDlsoHvvIzlk6rmnInkvY/miL/kuKTpl7TvvJ/lnKgxOTky5bm05byg5piO5omN6L+Y6Z2g562U6L6p5Lq655Sf5rS777yM5ZOq5p2l55qE5pyJ6IO95Yqb5Y+C5LiO5bu65oi/77yf562U6L6p5Lq65byg5Zu95a+M5ZyoMTk5MuW5tOato+aYr+WjruW5tOaXtuacn++8jOWIhuW+l+WuheWfuuWcsOS4pOmXtO+8jOS4quS6uuWHuui1hOW7uuaIkOS6hueOsOWcqOeahOaIv+WtkOWFremXtOOAgjIwMDPlubTnrZTovqnkurrlvKDlm73lr4zkvp3ms5Xlip7nkIbkuobmiL/lsYvmiYDmnInmnYPor4HvvIzlkozlprvlrZDogpbln7nlh6Tns7vor6XmiL/lsYvnmoTlkIjms5XlhbHmnInkurrjgILov5nmnJ/pl7TnrZTovqnkurrlhajlrrbnmoTnlJ/mtLvmmK/lubPpnZnnmoTvvIwyMDA55bm05byg5piO5omN5L6d552A6Ieq5bex5bm06L275bCG562U6L6p5Lq65byg5Zu95a+M5pK15Ye65LqG5a626Zeo77yM5oqi5Y2g5LqG562U6L6p5Lq65byg5Zu95a+M55qE5oi/5a2Q77yM6L2v56aB5LqG6IKW5Z+55Yek44CCMjAxMeW5tDEw5pyIMzHml6XlvKDmmI7miY3kuI3orrLpgZPkuYnlkozoia/lv4PvvIzov5jmiornrZTovqnkurrlvKDlm73lr4zmiZPkvKTvvIjor6bop4HmtYnmsrPljLrkurrmsJHms5XpmaLvvIgyMDEy77yJ5L+h5rWJ5rCR5Yid5a2X56ysMTY5NeWPt+awkeS6i+WIpOWGs+S5pu+8ieOAgumaj+WQjuW8oOaYjuaJjeWSjOWmu+WtkOWGmeS4i+KAnOmBk+atieS5puKAnei/meS4gOezu+WIl+eahOS6i+WunuivgeaYjuS6huW8oOaYjuaJjeaYr+S4uuS6hui+vuWIsOaKouWNoOetlOi+qeS6uuW8oOWbveWvjOeahOaIv+S6p+eahOebrueahOOAgueOsOWcqOWPiOivtOetlOi+qeS6uuW8oOWbveWvjOeahOaIv+WtkOaYr+S7luS4gOS6uuaJgOW7uu+8jOecn+aYr+W8peWkqeWkp+iwjuOAguS6jOOAgeWOn+WRiui/mOivieensO+8muKAnDIwMDTlubTooqvlkYrlvKDmmI7lvrflkozmsaTmnbDlpKvlprvmi7/nnYDmiL/kuqfor4HmnaXliIbljp/lkYrnmoTmiL/lrZDjgILigJ3lvKDmmI7miY3kuLrkuoblvLrljaDliLDniLbkurLlvKDlm73lr4znmoTnp4HmnInmiL/kuqfvvIzku4DkuYjnno7or53pg73og73nvJbvvIHnrZTovqnkurrlvKDmmI7lvrflpKvlprvmnInoh6rlt7HnmoTmiL/kuqfvvIzku4DkuYjml7blgJnliIbov4fliKvkurrnmoTmiL/kuqc/5byg5piO5omN5piv5oqi5Y2g5Yir5Lq65oi/5Lqn55qE5YW35L2T5Lq677yMMjAwOeW5tOW8oOaYjuaJjeS4uuS6huWNoOacieetlOi+qeS6uueItuS6sueahOaIv+S6p+ebl+i1sOS6hueItuS6suW8oOWbveWvjOeahOKAnOWbveacieWcn+WcsOS9v+eUqOivgeKAne+8iOivpuingeS/oemYs+aXpeaKpeeahOWFrOWRiu+8ieOAguS4ieOAgeWbveWutuihjOaUv+acuuWFs+migeWPkeeahOKAnOWcn+WcsOS9v+eUqOivgeKAneWSjOKAnOaIv+S6p+ivgeKAneaYr+ivgeaYjuaJgOacieadg+eahOWQiOazleWHreivge+8jOWPl+azleW+i+S/neaKpO+8jOetlOi+qeS6uuW8oOWbveWvjOeahOaIv+S6p+ivgeiHs+S7iuW3sumihuWPljE15bm05pe26Ze077yM5YW35pyJ55u45a+555qE56iz5a6a5oCn77yM5Y+X5rOV5b6L5L+d5oqk77yM5LiN5a655Lu75L2V5Lq66Z2e5rOV5L6154qv44CC562U6L6p5Lq65byg5Zu95a+M5Li65LqG5Li75byg5p2D5Yip5L6d5rOV6LW36K+J5LqG5byg5piO5omN77yM5q2k5qGI5rWJ5rKz5Yy65Lq65rCR5rOV6Zmi5q2j5Zyo6YeN5a6h44CC562U6L6p5Lq65byg5Zu95a+M55qE5p2D55uK5Y+X5rOV5b6L5L+d5oqk77yM5LiN5a655byg5piO5omN6ZqP5oSP5L6154qv44CC57u85LiK77yM5bqU6amz5Zue5Y6f5ZGK55qE6K+J6K686K+35rGC44CCPC9kaXY+PGRpdiBzdHlsZT0nTElORS1IRUlHSFQ6IDI1cHQ7IFRFWFQtSU5ERU5UOiAzMHB0OyBNQVJHSU46IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7nu4/lrqHnkIbmn6XmmI7vvIzooqvlkYrlvKDlm73lr4zkuI7ljp/lkYrlvKDmmI7miY3jgIHooqvlkYrlvKDmmI7lvrfns7vniLblrZDlhbPns7vjgILooqvlkYrlvKDlm73lr4zkuI7moYjlpJbkurrogpbln7nlh6Tns7vlpKvlprvlhbPns7vvvIwyMDAz5bm0MeaciDbml6XooqvlkYrlvKDlm73lr4zlj5blvpflnZDokL3kuo7mtYnmtYnmsrPljLrDl8OXw5fDl+e7hOadg+ivgeWPt+S4uuS/oeaIv+adg+ivgea1iea1ieays+WMuuWtl+esrMOXw5flj7flsYvnmoTmiYDmnInmnYPor4HvvIzmiL/lsYvmiYDDl8OX6K6w5Li65byg5Zu95a+M77yM5YWx5pyJ5Lq66IKW5Z+55Yek77yM5bu6562R6Z2i56evMjg1Ljg45bmz5pa557Gz44CC5ZCO5Zug5Li65oi/5Lqn5b2S5bGe6Zeu6aKY5Y6f5ZGK5byg5piO5omN5ZKM6KKr5ZGK5byg5Zu95a+M44CB5byg5piO5b635aSa5qyh5Y+R55Sf57qg57q377yM5ZCO5Y6f5ZGK6K+J6Iez5rOV6Zmi77yM6K+35rGC5Zyo6K+l5oi/5Lqn6K+B5LiK5Yqg5LiK5Y6f5ZGK5byg5piO5omN5Li65YWx5pyJ5p2D5Lq644CC5Li65q2k77yM5Y6f5ZGK5o+Q5Lqk5LqG5L+h6Ziz5biC5oi/5Lqn5qGj5qGI6aaG5Ye65YW355qE5oi/5Lqn5p+l5qGj6KGo44CB5oi357GN6K+B5piO44CB6IKW5Z+55Yek55qE6K+B6KiA44CB6IOh5bu65Yab55qE6K+B6KiA44CB5L+h6Ziz5biC5rWJ5rKz5Yy65LqU5pif6KGX6YGT5Yqe5LqL5aSE57qi5pif56S+5Yy65bGF5rCR5aeU5ZGY5Lya55qE6K+B5piO5Y+K5rWJ5rKz5Yy65rOV6Zmi77yIMjAxN++8ieixqzE1MDLmsJHliJ0yNzI35Y+35rCR5LqL5Yik5Yaz5Lmm77yM55So5Lul6K+B5piO6K+l5oi/5bGL5piv5Y6f5ZGK5byg5piO5omN5Ye66LWE5bu65oiQ77yM5YW25pys5Lq65bqU5piv5oi/5bGL55qE5YWx5pyJ5p2D5Lq644CC6KKr5ZGK5o+Q5Lqk5LqG5oi/5Lqn6K+B44CB6KKr5ZGK5byg5Zu95a+M55uW5oi/5a2Q55qE56yU6K6w5pys44CB5L+h6Ziz5pel5oql55m75oql5aOw5piO44CB5byg5piO57qi55qE6K+B6KiA44CB5a2Z5paM55Sr55qE6K+B6KiA5Lul5Y+K6YGT5q2J5Lmm55So5Lul6K+B5piO5oi/5bGL5piv55Sx6KKr5ZGK5byg5Zu95a+M5Ye66LWE5bu65oiQ77yM5bqU6K+l6amz5Zue5Y6f5ZGK5byg5piO5omN55qE6K+J6K686K+35rGC44CCPC9kaXY+PGRpdiBzdHlsZT0nTElORS1IRUlHSFQ6IDI1cHQ7IFRFWFQtSU5ERU5UOiAzMHB0OyBNQVJHSU46IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7mnKzpmaLorqTkuLrvvIzkuI3liqjkuqfmnYPlsZ7or4HkuabmmK/mnYPliKnkurrkuqvmnInor6XkuI3liqjkuqfnianmnYPnmoTor4HmmI7jgILooqvlkYrlvKDlm73lr4zmi6XmnInkuonorq7miL/kuqfnmoTmiL/lsYvmiYDmnInmnYPor4HvvIzor4HkuabnmbvorrDooajmmI7mjIHor4HkurrvvIjmiYDDl8OX5Li65byg5Zu95a+M77yM5YWx5pyJ5p2D5Lq65Li66IKW5Z+55Yek77yM5Y6f5ZGK5byg5piO5omN6KaB5rGC5Zyo6K+l5oi/5Lqn6K+B5Yqg5LiK6Ieq5bex55qE5ZCN5a2X77yM5bqU5o+Q5Lqk6K+B5o2u6K+B5piO5YW25piv5YWx5pyJ5p2D5Lq65oiW5oyB6K+B5Lq677yI5omAw5fDl+W8oOWbveWvjOWQjOaEj++8jOS9huacrOahiOS4reiiq+WRiuW8oOWbveWvjOaLkue7neWOn+WRiuWKoOWQjeivt+axgu+8jOiAjOWOn+WRiuaPkOS6pOeahOivgeaNruW5tuS4jeiDveivgeaYjuWFtuaYr+a2ieahiOaIv+Wxi+eahOWFseacieadg+S6uu+8jOaVheWvueWOn+WRiueahOivieiuvOivt+axgu+8jOacrOmZouS4jeS6iOaUr+aMgeOAgue7vOS4iu+8jOS+neeFp+OAiuS4reWSjOS6uuawkeWFseWSjOWbveawkeS6i+ivieiuvOazleOAi+esrOWFreWNgeWbm+adoeeahOinhOWumu+8jOWIpOWGs+WmguS4i++8mjwvZGl2PjxkaXYgc3R5bGU9J0xJTkUtSEVJR0hUOiAyNXB0OyBURVhULUlOREVOVDogMzBwdDsgTUFSR0lOOiAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+6amz5Zue5Y6f5ZGK5byg5piO5omN55qE6K+J6K686K+35rGC44CCPC9kaXY+PGRpdiBzdHlsZT0nTElORS1IRUlHSFQ6IDI1cHQ7IFRFWFQtSU5ERU5UOiAzMHB0OyBNQVJHSU46IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7mnKzmoYjlj5fnkIbotLkxMDDlhYPvvIznlLHljp/lkYrlvKDmmI7miY3mib/mi4XjgII8L2Rpdj48ZGl2IHN0eWxlPSdMSU5FLUhFSUdIVDogMjVwdDsgVEVYVC1JTkRFTlQ6IDMwcHQ7IE1BUkdJTjogMC41cHQgMGNtO0ZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTVwdDsnPuWmguS4jeacjeacrOWIpOWGs++8jOWPr+S7peWcqOWIpOWGs+S5pumAgei+vuS5i+aXpei1t+WNgeS6lOaXpeWGhe+8jOWQkeacrOmZoumAkuS6pOS4iuivieeKtu+8jOW5tuaMieWvueaWueW9k+S6i+S6uueahOS6uuaVsOaPkOWHuuWJr+acrO+8jOS4iuivieS6juays+WNl+ecgeS/oemYs+W4guS4ree6p+S6uuawkeazlemZouOAgjwvZGl2PjxkaXYgc3R5bGU9J1RFWFQtQUxJR046IHJpZ2h0OyBMSU5FLUhFSUdIVDogMjVwdDsgTUFSR0lOOiAwLjVwdCAzNnB0IDAuNXB0IDBjbTtGT05ULUZBTUlMWTog5a6L5L2TOyBGT05ULVNJWkU6IDE1cHQ7Jz7lrqHliKTlkZjjgIDjgIDmtKrpn6w8L2Rpdj48ZGl2IHN0eWxlPSdURVhULUFMSUdOOiByaWdodDsgTElORS1IRUlHSFQ6IDI1cHQ7IE1BUkdJTjogMC41cHQgMzZwdCAwLjVwdCAwY207Rk9OVC1GQU1JTFk6IOWui+S9kzsgRk9OVC1TSVpFOiAxNXB0Oyc+5LqM44CH5LiA5YWr5bm05Zub5pyI5Y2B5LqM5pelPC9kaXY+PGRpdiBzdHlsZT0nVEVYVC1BTElHTjogcmlnaHQ7IExJTkUtSEVJR0hUOiAyNXB0OyBNQVJHSU46IDAuNXB0IDM2cHQgMC41cHQgMGNtO0ZPTlQtRkFNSUxZOiDlrovkvZM7IEZPTlQtU0laRTogMTVwdDsnPuS5puiusOWRmOOAgOOAgOm7hOS4uTwvZGl2PjwvQk9EWT48L0hUTUw+</c_nr>\\\\n            </t_ws>\\\\n        </t_aj>\\\\n    </data>\\\\n</RESOURCE>\\\\n\\\"\"},\"head\":{\"businessNumber\":\"320600\",\"cxqqdh\":\"20210316320600000017\",\"msg\":\"司法判决信息回调成功\",\"requestType\":\"GF99\",\"status\":\"0200\"}}\n";
        if (null == response) {
            return null;
        }
        JSONObject responseObject = JSON.parseObject(response);

        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("status") && StringUtils.equalsIgnoreCase(headObject.getString("status"), "0200")) {
            JSONObject dataObject = responseObject.getJSONObject("data");

            String xmlData = dataObject.getString("data");
            if (StringUtils.isBlank(xmlData)) {
                return null;
            }
            //去掉首尾双引号
            if (xmlData.startsWith("\"") && xmlData.endsWith("\"")) {
                xmlData = xmlData.substring(1, xmlData.length() - 1);
            }

            xmlData = StringEscapeUtils.unescapeJava(xmlData);

            LOGGER.info("司法判决信息结果反馈 xml 数据:{}", xmlData);

            SupremeCourtDecisionResponseResponseDTO responseResponseDTO = new SupremeCourtDecisionResponseResponseDTO();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(SupremeCourtDecisionResponseResponseDTO.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader sr = new StringReader(xmlData);
                responseResponseDTO = (SupremeCourtDecisionResponseResponseDTO) jaxbUnmarshaller.unmarshal(sr);
                SupremeCourtDecisionResponseDataDTO dataDTO = responseResponseDTO.getDataDTO();
                if (Objects.nonNull(dataDTO)) {
                    if (CollectionUtils.isNotEmpty(dataDTO.getAjDTOList())) {
                        dataDTO.getAjDTOList().forEach(supremeCourtDecisionResponseAjDTO -> {
                            SupremeCourtDecisionResponseWsDTO wsDTO = supremeCourtDecisionResponseAjDTO.getWsDTO();
                            if (Objects.nonNull(wsDTO) && Objects.nonNull(wsDTO.getNr())) {
                                String content = wsDTO.getNr();
                                LOGGER.info("司法判决信息结果反馈 content 数据:{}", content);
                                content = StringEscapeUtils.unescapeJava(content);
                                LOGGER.info("司法判决信息结果反馈 content 数据,去掉转义:{}", content);
                                byte[] bytes = Base64.getDecoder().decode(content);
                                LOGGER.info("base转码后的文书内容:{}", new String(bytes, StandardCharsets.UTF_8));
                                wsDTO.setNr(new String(bytes, StandardCharsets.UTF_8));
                            }
                        });
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

            return responseResponseDTO;
        } else {
            throw new AppException(headObject.getString("msg"));
        }
    }

    /**
     * @param info
     * @return PoliceIdentityCheckResponseDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 3.12 公安部-人口库基准信息查询接口
     */
    @Override
    @DsfLog(logEventName = "公安部-人口库基准信息查询", dsfFlag = "GongAn", requester = "BDC", responser = "ZGF")
    @OpenApi(apiDescription = "公安部-人口库基准信息查询", apiName = "", openLog = false)
    public CommonResponse<JSONArray> baseInfo(@RequestBody List<PoliceQueryBaseInfoRequestDTO> info) {
        LOGGER.info("人口库基准信息查询接口开始");
        if (CollectionUtils.isNotEmpty(info)) {
            info.forEach(PoliceQueryBaseInfoRequestDTO::checkParam);
            Map<String, Object> bodyParamMap = new HashMap<>(2);
            // 查询业务类别
            bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.POLICE_QUERY_BASEINFO.getCode());
            bodyParamMap.put("cxywcs", info);

            Map<String, Object> paramMap = new HashMap<>(2);
            paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
            paramMap.put("data", bodyParamMap);
            String response = sjptApiUtils.sendPostRequest(policeBaseInfoUrl, paramMap);
            LOGGER.info("人口库基准信息查询接口返回:{}", response);
            if (null == response) {
                return null;
            }
            JSONObject responseObject = JSON.parseObject(response);
            JSONObject headObject = responseObject.getJSONObject("head");
            if (headObject.containsKey("status") && StringUtil.equals(headObject.getString("status"), "0")) {
                JSONObject data = responseObject.getJSONObject("data");
                if (data.containsKey("cxjg")) {
                    JSONArray cxjg = data.getJSONArray("cxjg");
                    return CommonResponse.ok(cxjg);
                } else {
                    return CommonResponse.fail("无返回信息");
                }
            } else {
                return CommonResponse.fail(response);
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param info
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 3.13 公安部-人口库身份核查接口
     */
    @Override
    @DsfLog(logEventName = "公安部-人口库身份核查", dsfFlag = "GongAn", requester = "BDC", responser = "GongAn")
    @OpenApi(apiDescription = "公安部-人口库身份核查", apiName = "", openLog = false)
    public CommonResponse<JSONArray> idCheck(@RequestBody List<PoliceCheckIdRequestDTO> info) {
        LOGGER.info("人口库身份核查接口开始");
        if (CollectionUtils.isNotEmpty(info)) {
            info.forEach(PoliceCheckIdRequestDTO::checkParam);
            Map<String, Object> bodyParamMap = new HashMap<>(2);
            // 查询业务类别
            bodyParamMap.put("cxywlb", QueryBusinessCategoryEnum.POLICE_ID_CHECK.getCode());
            bodyParamMap.put("cxywcs", info);

            Map<String, Object> paramMap = new HashMap<>(2);
            paramMap.put("head", sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)));
            paramMap.put("data", bodyParamMap);
            String response = sjptApiUtils.sendPostRequest(policeIdCheckUrl, paramMap);
            LOGGER.info("人口库身份核查接口返回:{}", response);
            if (null == response) {
                return null;
            }
            JSONObject responseObject = JSON.parseObject(response);
            JSONObject headObject = responseObject.getJSONObject("head");
            if (headObject.containsKey("status") && StringUtil.equals(headObject.getString("status"), "0")) {
                JSONObject data = responseObject.getJSONObject("data");
                if (data.containsKey("cxjg")) {
                    JSONArray cxjg = data.getJSONArray("cxjg");
                    return CommonResponse.ok(cxjg);
                } else {
                    return CommonResponse.fail("无返回信息");
                }
            } else {
                return CommonResponse.fail(response);
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 海域统一请求接口
     */
    @Override
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/djxx/to/hy")
    public CommonResponse hyCommonRequest(@RequestParam(name = "processInsId") String gzlslid) {
        LOGGER.info("宗海推送信息接口开始:{}", gzlslid);
        if(StringUtils.isNotBlank(gzlslid)){
            Example example = new Example(BdcXmDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            List<BdcXmDO> bdcXmList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcXmList)){
                for (BdcXmDO bdcXmDO : bdcXmList) {
                    SendHyxxService service = sendHyxxServiceChoose.getService(sendHyxxServiceChoose.getHandleServiceKey(bdcXmDO));
                    if (service == null){
                        LOGGER.info("当前项目不需要处理:{}", JSON.toJSONString(bdcXmDO));
                        continue;
                    }
                    CommonResponse commonResponse = service.commonSendHyxx(bdcXmDO);
                    if (!commonResponse.isSuccess()){
                        throw new AppException(ErrorCode.ILLEGAL_STATE, commonResponse.getFail() != null ? commonResponse.getFail().getMessage() : null);
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.1宗海基本信息接口
     */
    @Override
    public CommonResponse seaZhjbxx(String gzlslid) {
        LOGGER.info("宗海基本信息接口开始:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhJbxxDTO> zhJbxxDTOS = bdcdjMapper.queryZhjbxxByGzlslid(gzlslid);
            LOGGER.info("查到的宗海基本信息数量为:{}", zhJbxxDTOS.size());

            if (CollectionUtils.isNotEmpty(zhJbxxDTOS)) {
                List<Map> zhJbxxJsonList = new ArrayList<>(zhJbxxDTOS.size());
                for (ZhJbxxDTO zhJbxxDTO : zhJbxxDTOS) {

                    Map zhJbxxJsonObject = new HashMap();
                    exchangeDozerMapper.map(zhJbxxDTO, zhJbxxJsonObject, "seaZhjbxx");
                    zhJbxxJsonList.add(zhJbxxJsonObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_JBXX.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhJbxxJsonList);
                LOGGER.info("宗海基本信息接口请求参数1:{}", paramMap.toString());
                String response = sjptApiUtils.sendPostRequest(zhjbxxUrl, paramMap);
                if (null == response) {
                    return null;
                }
                LOGGER.info("宗海基本信息接口开始返回:{}", response);
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.2 用海坐标接口
     */
    @Override
    public CommonResponse seaYhydzb(String gzlslid) {
        LOGGER.info("用海状况接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhYhzbDTO> zhYhzbDTOS = bdcdjMapper.queryZhYhzbByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhYhzbDTOS)) {
                List<HashMap> zhYhzbJsonList = new ArrayList<>(zhYhzbDTOS.size());
                for (ZhYhzbDTO zhYhzbDTO : zhYhzbDTOS) {
                    HashMap zhYhzbObject = new HashMap();
                    exchangeDozerMapper.map(zhYhzbDTO, zhYhzbObject, "seaYhydzb");
                    zhYhzbJsonList.add(zhYhzbObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_YHZB.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhYhzbJsonList);
                String response = sjptApiUtils.sendPostRequest(zhyhzbUrl, paramMap);
                LOGGER.info("用海状况接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.3 用海状况接口
     */
    @Override
    public CommonResponse seaYhzk(String gzlslid) {
        LOGGER.info("用海状况接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhYhzkDTO> zhYhzkDTOS = bdcdjMapper.queryZhYhzkByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhYhzkDTOS)) {
                List<HashMap> zhYhzkJsonList = new ArrayList<>(zhYhzkDTOS.size());
                for (ZhYhzkDTO zhYhzkDTO : zhYhzkDTOS) {
                    HashMap zhYhzkObject = new HashMap();
                    exchangeDozerMapper.map(zhYhzkDTO, zhYhzkObject, "seaYhzk");
                    zhYhzkJsonList.add(zhYhzkObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_YHZK.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhYhzkJsonList);
                String response = sjptApiUtils.sendPostRequest(zhyhzkUrl, paramMap);
                LOGGER.info("用海状况接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.4 宗海变化情况接口
     */
    @Override
    public CommonResponse seaZhbhqk(String gzlslid) {
        LOGGER.info("宗海变化情况接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhZhbhqkDTO> zhZhbhqkDTOS = bdcdjMapper.queryZhZhbhqkByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhZhbhqkDTOS)) {
                List<HashMap> zhZhbhqkJsonList = new ArrayList<>(zhZhbhqkDTOS.size());
                for (ZhZhbhqkDTO zhZhbhqkDTO : zhZhbhqkDTOS) {
                    HashMap zhZhbhqkObject = new HashMap();
                    exchangeDozerMapper.map(zhZhbhqkDTO, zhZhbhqkObject, "seaZhbhqk");
                    zhZhbhqkJsonList.add(zhZhbhqkObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_BHQK.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhZhbhqkJsonList);
                String response = sjptApiUtils.sendPostRequest(zhbhqkUrl, paramMap);
                LOGGER.info("宗海变化情况接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.5 海域使用权信息接口
     */
    @Override
    public CommonResponse seaHysyq(String gzlslid) {
        LOGGER.info("海域使用权信息接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhHysyqxxDTO> zhHysyqxxDTOS = bdcdjMapper.queryZhHysyqByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhHysyqxxDTOS)) {
                List<HashMap> zhHysyqxxJsonList = new ArrayList<>(zhHysyqxxDTOS.size());
                for (ZhHysyqxxDTO zhHysyqxxDTO : zhHysyqxxDTOS) {
                    HashMap zhHysyqxxObject = new HashMap();
                    exchangeDozerMapper.map(zhHysyqxxDTO, zhHysyqxxObject, "seaHysyq");
                    zhHysyqxxJsonList.add(zhHysyqxxObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_HYSYQXX.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhHysyqxxJsonList);
                String response = sjptApiUtils.sendPostRequest(zhhysyqUrl, paramMap);
                LOGGER.info("海域使用权信息接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.6 权利人信息接口
     */
    @Override
    public CommonResponse seaQlr(String gzlslid) {
        LOGGER.info("权利人信息接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhQlrDTO> zhQlrDTOS = bdcdjMapper.queryZhQlrByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhQlrDTOS)) {
                List<HashMap> zhQlrJsonList = new ArrayList<>(zhQlrDTOS.size());
                for (ZhQlrDTO zhQlrDTO : zhQlrDTOS) {
                    HashMap zhQlrObject = new HashMap();
                    exchangeDozerMapper.map(zhQlrDTO, zhQlrObject, "seaQlr");
                    zhQlrJsonList.add(zhQlrObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_QLRXX.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhQlrJsonList);
                String response = sjptApiUtils.sendPostRequest(zhqlrUrl, paramMap);
                LOGGER.info("权利人信息接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.8 抵押登记信息接口
     */
    @Override
    public CommonResponse seaDyaq(String gzlslid) {
        LOGGER.info("抵押登记信息接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhDydjxxDTO> zhDydjxxDTOS = bdcdjMapper.queryZhDydjxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhDydjxxDTOS)) {
                List<HashMap> zhDydjxxJsonList = new ArrayList<>(zhDydjxxDTOS.size());
                for (ZhDydjxxDTO zhDydjxxDTO : zhDydjxxDTOS) {
                    HashMap zhDydjxxObject = new HashMap();
                    exchangeDozerMapper.map(zhDydjxxDTO, zhDydjxxObject, "seaDyaq");
                    zhDydjxxJsonList.add(zhDydjxxObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_DYDJXX.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhDydjxxJsonList);
                String response = sjptApiUtils.sendPostRequest(zhdydjxxUrl, paramMap);
                LOGGER.info("抵押登记信息接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.9 查封登记信息接口
     */
    @Override
    public CommonResponse seaCfdj(String gzlslid) {
        LOGGER.info("查封登记信息接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhCfdjxxDTO> zhCfdjxxDTOS = bdcdjMapper.queryZhCfdjxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhCfdjxxDTOS)) {
                List<HashMap> zhCfdjxxJsonList = new ArrayList<>(zhCfdjxxDTOS.size());
                for (ZhCfdjxxDTO zhDydjxxDTO : zhCfdjxxDTOS) {
                    HashMap zhCfdjxxObject = new HashMap();
                    exchangeDozerMapper.map(zhDydjxxDTO, zhCfdjxxObject, "seaCfdj");
                    zhCfdjxxJsonList.add(zhCfdjxxObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_CFDJXX.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhCfdjxxJsonList);
                String response = sjptApiUtils.sendPostRequest(zhcfdjxxUrl, paramMap);
                LOGGER.info("查封登记信息接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.10 异议登记信息接口
     */
    @Override
    public CommonResponse seaYydj(String gzlslid) {
        LOGGER.info("异议登记信息接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhYydjxxDTO> zhYydjxxDTOS = bdcdjMapper.queryZhYydjxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhYydjxxDTOS)) {
                List<HashMap> zhYydjxxJsonList = new ArrayList<>(zhYydjxxDTOS.size());
                for (ZhYydjxxDTO zhYydjxxDTO : zhYydjxxDTOS) {
                    HashMap zhYydjxxObject = new HashMap();
                    exchangeDozerMapper.map(zhYydjxxDTO, zhYydjxxObject, "seaYydj");
                    zhYydjxxJsonList.add(zhYydjxxObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_YYDJXX.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhYydjxxJsonList);
                String response = sjptApiUtils.sendPostRequest(zhyydjxxUrl, paramMap);
                LOGGER.info("异议登记信息接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.11 扫描件信息接口
     */
    @Override
    public CommonResponse seaSmjxx(String gzlslid) {
        LOGGER.info("扫描件信息接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
            List<ZhSmjxxDTO> zhSmjxxDTOS = bdcdjMapper.queryZhsmjByGzlslid(gzlslid);
            List<ZhSmjxxDTO> smjxx = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOS) && CollectionUtils.isNotEmpty(zhSmjxxDTOS)){
                for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOS) {
                    if (StringUtils.isNotBlank(bdcSlSjclDO.getWjzxid())){
                        StorageDto storageDto = storageClient.findById(bdcSlSjclDO.getWjzxid());
                        if (storageDto != null){
                            ZhSmjxxDTO smjxxDTO = ZhSmjxxDTO.ZhSmjxxDTOBuilder.aZhSmjxxDTO()
                                    .withFilename(storageDto.getName())
                                    .withFilepath(storageDto.getDownUrl())
                                    .withFilesize(Long.toString(storageDto.getFileSize()))
                                    .withFiletype(storageDto.getFileType())
                                    .withFjtype(storageDto.getFileType())
                                    .withBdcdyh(zhSmjxxDTOS.get(0).getBdcdyh())
                                    .withZhdm(zhSmjxxDTOS.get(0).getZhdm())
                                    .withYwh(zhSmjxxDTOS.get(0).getYwh())
                                    .build();
                            smjxx.add(smjxxDTO);
                        }else {
                            return CommonResponse.fail("查询大云文件中心未返回相关内容,fileId:{}",bdcSlSjclDO.getWjzxid());
                        }
                    }else {
                        return CommonResponse.fail("BDC_SL_SJCL表未存储大云文件id,gzlslid:{}",gzlslid);
                    }
                }
            }
            List<HashMap> smjxxJsonList = new ArrayList<>(smjxx.size());
            if (CollectionUtils.isNotEmpty(smjxx)){
                for (ZhSmjxxDTO zhSmjxxDTO : smjxx) {
                    HashMap smjxxObject = new HashMap();
                    exchangeDozerMapper.map(zhSmjxxDTO, smjxxObject, "seaSmjxx");
                    smjxxJsonList.add(smjxxObject);
                }
            }
            // 查询业务类别
            Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_SMJXX.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), smjxxJsonList);
            String response = sjptApiUtils.sendPostRequest(zhsmjxxUrl, paramMap);
            LOGGER.info("扫描件信息接口:{}", response);
            if (null == response) {
                return null;
            }
            return dealWithZhResponse(response);
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.12 注销登记信息接口
     */
    @Override
    public CommonResponse seaZxdj(String gzlslid) {
        LOGGER.info("注销登记信息接口:{}", gzlslid);
        if (StringUtils.isNotBlank(gzlslid)) {
//            bodyParamMap.put("cxywcs", info);
            //查询相关参数
            List<ZhZxdjxxDTO> zhZxdjxxDTOS = bdcdjMapper.queryZhZxdjxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zhZxdjxxDTOS)) {
                List<HashMap> zhZxdjxxJsonList = new ArrayList<>(zhZxdjxxDTOS.size());
                for (ZhZxdjxxDTO zhZxdjxxDTO : zhZxdjxxDTOS) {
                    HashMap zhZxdjxxObject = new HashMap();
                    exchangeDozerMapper.map(zhZxdjxxDTO, zhZxdjxxObject, "seaZxdj");
                    zhZxdjxxJsonList.add(zhZxdjxxObject);
                }
                // 查询业务类别
                Map<String, Object> paramMap = initZhParamInfo(QueryBusinessCategoryEnum.ZH_ZXDJXX.getCode(), sjptApiUtils.handleRequestHead(IPUtils.getRequestClientRealIP(request)), zhZxdjxxJsonList);
                String response = sjptApiUtils.sendPostRequest(zhzxdjxxUrl, paramMap);
                LOGGER.info("注销登记信息接口:{}", response);
                if (null == response) {
                    return null;
                }
                return dealWithZhResponse(response);
            } else {
                return CommonResponse.fail("未查询到相关的宗海基本信息!");
            }
        } else {
            return CommonResponse.fail("入参缺失");
        }
    }

    @NotNull
    private CommonResponse<Object> dealWithZhResponse(String response) {
        JSONObject responseObject = JSON.parseObject(response);
        JSONObject headObject = responseObject.getJSONObject("head");
        if (headObject.containsKey("status") && StringUtil.equals(headObject.getString("status"), "0000")) {
            JSONObject data = responseObject.getJSONObject("data");
            if (data.containsKey("Result") && data.getBoolean("Result")) {
                return CommonResponse.ok();
            } else {
                return CommonResponse.fail("无返回信息");
            }
        } else {
            return CommonResponse.fail(response);
        }
    }

    private Map<String, Object> initZhParamInfo(String code, Map<String, Object> stringObjectMap, Object info) {
        Map<String, Object> bodyParamMap = new HashMap<>(2);
        Map<String, Object> paramMap = new HashMap<>(2);
        bodyParamMap.put("cxywlb", code);
        paramMap.put("head", stringObjectMap);
        paramMap.put("data", bodyParamMap);
        bodyParamMap.put("cxywcs", info);
        return paramMap;
    }

    /**
     * 转换字典值
     *
     * @param param
     * @param key
     * @return
     */
    private String zdDmToMcConvert(String param, String key) {
        List<Map> zdMapList = bdcZdFeignService.queryBdcZd(param.toLowerCase());
        if (CollectionUtils.isEmpty(zdMapList)) {
            return "";
        }
        for (Map map : zdMapList) {
            if (StringUtils.equals(key, MapUtils.getString(map, "DM"))) {
                if (Objects.nonNull(map.get("MC"))) {
                    return map.get("MC").toString();
                }
            }
        }
        return "";
    }
}
