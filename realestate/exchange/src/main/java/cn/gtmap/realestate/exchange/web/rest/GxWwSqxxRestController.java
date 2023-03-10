package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.KttZhjbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSjclDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.FwxxbybdcdyhResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.dbxx.*;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfDaCheckLog;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.bo.wwsq.ParamBody;
import cn.gtmap.realestate.exchange.core.bo.wwsq.ParamBodyList;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ResponseHead;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.request.DycxcqRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxx.response.GetWwsqCqzxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequest;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxResponse;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxResponseMsg;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxSqxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfdpdf.request.SfdPdfRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.spfBaxx.response.SpfBaxxQlrResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.spfBaxx.response.SpfBaxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.request.ZsyzRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.response.ZsyzResponseData;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.respones.YthCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestQlr;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttZhjbxxMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GrdacxService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GxWwSqxxService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.ZsyzService;
import cn.gtmap.realestate.exchange.util.BodyUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.MsgEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

/**
 * ??????????????????????????????
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/18 15:38
 */
@OpenController(consumer = "??????????????????????????????")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/gxww")
@Api(tags = "??????????????????????????????")
public class GxWwSqxxRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GxWwSqxxRestController.class);

    @Autowired
    private GxWwSqxxService gxWwSqxxService;

    @Autowired
    private BodyUtil bodyUtil;

    @Autowired
    private GrdacxService grdacxService;

    @Autowired
    private ZsyzService zsyzService;

    @Autowired
    private BdcDjbxxFeignService bdcDjbxxFeignService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    private DjxxFeignService djxxFeignService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private KttZhjbxxMapper zhjbxxMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Value("${spf.xc: false}")
    private Boolean xcSpfbaxx;

    /**
     * 4.4.1 ?????????????????????
     *
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @OpenApi(apiDescription = "?????????????????????", apiName = "", openLog = false)
    @PostMapping("/getWwsqCqzxx")
    @DsfLog(logEventName = "?????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    @DsfDaCheckLog(logEventName = "?????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC", interfaceFlagCode = "03", checkFlagIndex = 0, checkFlagClassName = "javax.servlet.http.HttpServletRequest", checkFlagLever = "0", interfaceName = "getWwsqCqzxx", interfaceUrl = "/realestate-exchange/rest/v1.0/gxww/getWwsqCqzxx")
    @ApiImplicitParams({@ApiImplicitParam(name = "param", value = "???????????????", required = true, dataType = "String", paramType = "body")})
    public Map getWwsqCqzxx(HttpServletRequest request) {
        //??????????????????
        ParamBody paramBody = bodyUtil.getWwsqParamBody(request, "getWwsqCqzxx");
        //returncode  ???????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        //?????????????????????????????????
        GetWwsqCqzxxResponseDTO responseDTO = null;


        if (paramBody != null && paramBody.getData() != null) {
            Map<String, Object> paramMap = paramBody.getData();

            // ???????????? ????????????
            if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "gxrmc"))
                    || StringUtils.isNotBlank(MapUtils.getString(paramMap, "djslbh"))
                    || StringUtils.isNotBlank(MapUtils.getString(paramMap, "xmid"))
                    || StringUtils.isNotBlank(MapUtils.getString(paramMap, "houseid"))) {
                try {
                    //?????????cqzh???cqzhmh????????????????????????
                    if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "cqzh")) && StringUtils.isNotBlank(MapUtils.getString(paramMap, "cqzhmh"))) {
                        paramMap.remove("cqzhmh");
                    }
                    responseDTO = exchangeBeanRequestService.request("getWwsqCqzxx", paramMap, GetWwsqCqzxxResponseDTO.class);
                    // ?????????????????????????????????
                    gxWwSqxxService.setGetWwsqCqzxxCqzhByQlrxx(responseDTO, MapUtils.getString(paramMap, "gxrzjh")
                            , MapUtils.getString(paramMap, "gxrmc"));
                    returncode = Constants.CODE_SUCCESS;
                    msg = MsgEnum.SUCCESS.getMsg();
                } catch (Exception e) {
                    msg = e.getMessage();
                }
            }
        }
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        if (paramBody != null && MapUtils.isNotEmpty(paramBody.getHead())) {
            map.putAll(paramBody.getHead());
        }
        return bodyUtil.getReturnBody(map, responseDTO);
    }

    /**
     * ???????????????????????????????????????
     *
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @OpenApi(apiDescription = "???????????????????????????????????????", apiName = "", openLog = false)
    @PostMapping("/bdczscx")
    @DsfLog(logEventName = "???????????????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    @DsfDaCheckLog(logEventName = "???????????????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC", interfaceFlagCode = "04", checkFlagIndex = 0, checkFlagClassName = "javax.servlet.http.HttpServletRequest", checkFlagLever = "0", interfaceName = "bdczscx", interfaceUrl = "/realestate-exchange/rest/v1.0/gxww/bdczscx")
    @ApiImplicitParams({@ApiImplicitParam(name = "param", value = "???????????????", required = true, dataType = "String", paramType = "body")})
    public void bdczscx(HttpServletRequest request, HttpServletResponse response) {
        //??????????????????
        ParamBody paramBody = bodyUtil.getWwsqParamBody(request, "bdczscx");
        //returncode  ???????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        List<Map> resultList = null;
        // ?????????????????????????????????
        if (paramBody != null && paramBody.getData() != null) {
            Map<String, Object> paramMap = paramBody.getData();
            // ???????????? ????????????
            if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "dyr"))
                    || StringUtils.isNotBlank(MapUtils.getString(paramMap, "cqzh"))
                    || StringUtils.isNotBlank(MapUtils.getString(paramMap, "xmid"))) {
                try {
                    resultList = gxWwSqxxService.getBdczsxx(MapUtils.getString(paramMap, "cqzh"),
                            MapUtils.getString(paramMap, "dyr"), MapUtils.getString(paramMap, "zl"),
                            MapUtils.getString(paramMap, "dyrzjh"), MapUtils.getString(paramMap, "xmid"));
                    returncode = Constants.CODE_SUCCESS;
                    msg = MsgEnum.SUCCESS.getMsg();
                } catch (Exception e) {
                    LOGGER.error("??????bdczscx?????????", e);
                    msg = e.getMessage();
                }
            }
        }
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        if (paramBody != null && MapUtils.isNotEmpty(paramBody.getHead())) {
            map.putAll(paramBody.getHead());
        }
        Map returnMap = bodyUtil.getReturnBody(map, resultList);
        if (Objects.nonNull(returnMap)) {
            String mapStr = JSONObject.toJSONString(returnMap);
            String s1 = mapStr.replace("\\\\", "\\");
            LOGGER.info("bdczscx???????????????{}", s1);
            response.setContentType("application/json;charset=utf-8");
            try (PrintWriter writer = response.getWriter()) {
                writer.println(s1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @OpenApi(apiDescription = "????????????????????????????????????????????????", apiName = "", openLog = false)
    @PostMapping("/bdczmcx")
    @DsfLog(logEventName = "????????????????????????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    @ApiImplicitParams({@ApiImplicitParam(name = "param", value = "???????????????", required = true, dataType = "String", paramType = "body")})
    public Map bdczmcx(HttpServletRequest request) {
        //??????????????????
        ParamBodyList paramBodyList = bodyUtil.getWwsqParamBodyList(request, "bdczmcx");
        //returncode  ???????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        List<Map> returnList = new ArrayList();
        //?????????????????????????????????
        if (paramBodyList != null && CollectionUtils.isNotEmpty(paramBodyList.getData())) {
            List<Map<String, Object>> paramMapList = paramBodyList.getData();
            for (Map<String, Object> paramMap : paramMapList) {
                if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "bdcdjzmh"))
                        || StringUtils.isNotBlank(MapUtils.getString(paramMap, "dyqr"))
                        || StringUtils.isNotBlank(MapUtils.getString(paramMap, "dyrmc"))) {
                    try {
                        List resultList = gxWwSqxxService.getBdczmxx(MapUtils.getString(paramMap, "bdcdjzmh"),
                                MapUtils.getString(paramMap, "dyqr"),
                                MapUtils.getString(paramMap, "dyrmc"),
                                MapUtils.getString(paramMap, "dyqrzjh"),
                                MapUtils.getString(paramMap, "dyrzjh"));
                        //?????????????????????
                        if (CollectionUtils.isNotEmpty(resultList)) {
                            returnList.addAll(resultList);
                        }
                    } catch (Exception e) {
                        msg = e.getMessage();
                    }
                }
            }
            //??????zmxmid??????????????????
            if (CollectionUtils.isNotEmpty(returnList)) {
                returnList = returnList.stream().collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(p -> (String) p.get("zmxmid")))),
                        ArrayList::new));
            }
            returncode = Constants.CODE_SUCCESS;
            msg = MsgEnum.SUCCESS.getMsg();
        }
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        if (paramBodyList != null && MapUtils.isNotEmpty(paramBodyList.getHead())) {
            map.putAll(paramBodyList.getHead());
        }
        return bodyUtil.getReturnBody(map, returnList);
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @OpenApi(apiDescription = "?????????????????????????????????????????????", apiName = "", openLog = false)
    @PostMapping("/querycqzdyxx")
    @DsfLog(logEventName = "?????????????????????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    @ApiImplicitParams({@ApiImplicitParam(name = "param", value = "???????????????", required = true, dataType = "String", paramType = "body")})
    public Map querycqzdyxx(HttpServletRequest request) {
        //?????????
        List<Map> list = new ArrayList<>();
        //??????????????????
        ParamBody paramBody = bodyUtil.getWwsqParamBody(request, "querycqzdyxx");
        //returncode  ???????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        //?????????????????????????????????
        if (paramBody != null && paramBody.getData() != null) {
            Map<String, Object> paramMap = paramBody.getData();
            if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "cqzh"))) {
                try {
                    list = gxWwSqxxService.querycqzdyxx(MapUtils.getString(paramMap, "cqzh"));
                    returncode = Constants.CODE_SUCCESS;
                    msg = MsgEnum.SUCCESS.getMsg();
                } catch (Exception e) {
                    msg = e.getMessage();
                }
            }
        }
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        if (paramBody != null && MapUtils.isNotEmpty(paramBody.getHead())) {
            map.putAll(paramBody.getHead());
        }
        return bodyUtil.getReturnBody(map, list);
    }

    /**
     * @param request
     * @return cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxResponse
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????
     */
    @OpenApi(apiDescription = "??????????????????", apiName = "", openLog = false)
    @PostMapping("/grdacx")
    @DsfLog(logEventName = "??????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    @DsfDaCheckLog(logEventName = "??????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC", interfaceFlag = "grdacx", interfaceFlagCode = "02", checkFlagIndex = 0, checkFlagClassName = "cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequest,cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody", checkFlagLever = "1", interfaceUrl = "/realestate-exchange/rest/v1.0/gxww/grdacx")
    @ApiImplicitParams({@ApiImplicitParam(name = "param", value = "???????????????", required = true, dataType = "String", paramType = "body")})
    public Object grdacx(@RequestBody GrdacxRequest request) {
        //returncode  ???????????????
        LOGGER.info("???????????????{}", JSON.toJSONString(request));
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        GrdacxResponseData responseData = new GrdacxResponseData();
        if (request != null && request.getData() != null) {
            List<GrdacxSqxx> grdacxSqxxList = grdacxService.getSqxx(request.getData());
            responseData.setSqxx(grdacxSqxxList);
            returncode = Constants.CODE_SUCCESS;
            msg = MsgEnum.SUCCESS.getMsg();
        }
        ResponseHead responseHead = new ResponseHead();
        responseHead.setMsg(msg);
        responseHead.setStatusCode(returncode);
        responseHead.setReturncode(returncode);
        GrdacxResponse response = new GrdacxResponse();
        response.setData(responseData);
        response.setHead(responseHead);
        return response;
    }

    /**
     * @param request
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 4.4.8 ????????????ID?????????????????? ????????????????????????????????????
     */
    @OpenApi(apiDescription = "????????????ID??????????????????????????????", apiName = "", openLog = false)
    @PostMapping("/queryZs")
    @DsfLog(logEventName = "????????????ID??????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    public Map queryZs(HttpServletRequest request) {
        //?????????
        List<ZsyzResponseData> responseList = Collections.emptyList();

        //??????????????????
        ParamBody paramBody = bodyUtil.getWwsqParamBody(request, "queryZs");
        //returncode  ???????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        //?????????????????????????????????
        if (paramBody != null && paramBody.getData() != null) {
            ZsyzRequestBody body = JSONObject.parseObject(JSONObject.toJSONString(paramBody.getData()), ZsyzRequestBody.class);
            if (CheckParameter.checkAnyParameter(body)) {
                responseList = zsyzService.queryZs(body);
                returncode = Constants.CODE_SUCCESS;
                msg = MsgEnum.SUCCESS.getMsg();
            }
        }
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        if (paramBody != null && MapUtils.isNotEmpty(paramBody.getHead())) {
            map.putAll(paramBody.getHead());
        }
        request.setAttribute("paramBody", paramBody);
        return bodyUtil.getReturnBody(map, responseList);
    }

    /**
     * @param request
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????
     */
    @OpenApi(apiDescription = "???????????????????????????", apiName = "", openLog = false)
    @PostMapping("/bdcspfbacx")
    @DsfLog(logEventName = "???????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    public Map bdcspfbacx(HttpServletRequest request) {
        //returncode  ???????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        //??????????????????
        ParamBody paramBody = bodyUtil.getWwsqParamBody(request, "bdcspfbacx");
        Object baxxResponse = new Object();
        SpfBaxxResponseData responseData = null;
        //??????????????????
        if (xcSpfbaxx){
            responseData = gxWwSqxxService.xcBdcspfbacx(paramBody);
        }else {
            baxxResponse = exchangeBeanRequestService.request("wwsqSpfBaxx", JSONObject.parseObject(JSONObject.toJSONString(paramBody)));
        }
        if (baxxResponse != null || xcSpfbaxx) {
            LOGGER.info("???????????????????????????,????????????{}???", JSON.toJSONString(baxxResponse));
            if (Objects.isNull(responseData)) {
                responseData = JSONObject.parseObject(JSONObject.toJSONString(baxxResponse), SpfBaxxResponseData.class);
            }
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(CommonConstantUtils.QSZT_VALID);
            if (StringUtils.isNotBlank(responseData.getBdcdyh())) {
                String bdcdyh = responseData.getBdcdyh();
                //????????????
                List<BdcQl> listDyaq = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString(), qsztList);
                if (CollectionUtils.isNotEmpty(listDyaq)) {
                    responseData.setDyzt(Constants.WWSQ_SF_S.toString());
                } else {
                    responseData.setDyzt(Constants.WWSQ_SF_F.toString());
                }

                //????????????
                List<BdcQl> listCf = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, CommonConstantUtils.QLLX_CF.toString(), qsztList);
                if (CollectionUtils.isNotEmpty(listCf)) {
                    responseData.setCfzt(Constants.WWSQ_SF_S.toString());
                } else {
                    responseData.setCfzt(Constants.WWSQ_SF_F.toString());
                }

                //??????????????????????????????????????????--zsxmid????????????,?????????????????????
                List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listBdcQlrByXmidList(Arrays.asList(responseData.getZsxmid()), "1");
                if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                    //???????????????????????????????????????????????????????????????????????????????????????
                    List<SpfBaxxQlrResponseData> qlr = responseData.getQlr()
                            .stream()
                            .filter(spfBaxxQlrResponseData -> Objects.nonNull(spfBaxxQlrResponseData.getQlrlx()))
                            .filter(spfBaxxQlrResponseData -> !StringUtils.equals("ywr", spfBaxxQlrResponseData.getQlrlx()))
                            .collect(Collectors.toList());
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                        SpfBaxxQlrResponseData spfBaxxQlrResponseData = new SpfBaxxQlrResponseData();
                        spfBaxxQlrResponseData.setQlrmc(bdcQlrDO.getQlrmc());
                        spfBaxxQlrResponseData.setQlrlx("ywr");
                        spfBaxxQlrResponseData.setQlrzjh(bdcQlrDO.getZjh());
                        spfBaxxQlrResponseData.setQlrzjzl(bdcQlrDO.getZjzl().toString());
                        //???????????????,??????????????????????????????0
                        spfBaxxQlrResponseData.setGyrbj("0");
                        qlr.add(spfBaxxQlrResponseData);
                    }

                    responseData.setQlr(qlr);
                }
            }
            returncode = Constants.CODE_SUCCESS;
            msg = MsgEnum.SUCCESS.getMsg();
        }
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        return bodyUtil.getReturnBody(map, responseData);
    }


    /**
     * @param requestDTO
     * @return java.util.List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????PDF????????????
     */
    @OpenApi(apiDescription = "??????????????????PDF????????????", apiName = "", openLog = false)
    @PostMapping("/sfxxpdf")
    @DsfLog(logEventName = "??????????????????PDF????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    public List<Map> wwsqSfxxPdf(@RequestBody SfdPdfRequestDTO requestDTO) {
        if (CheckParameter.checkAllParameter(requestDTO)) {
            return gxWwSqxxService.sfxxPdf(requestDTO);
        } else {
            throw new MissingArgumentException("????????????????????????PDF??????");
        }
    }


    /**
     * 4.4.1 ?????????????????????-????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @OpenApi(apiDescription = "?????????????????????", apiName = "", openLog = false)
    @PostMapping("/getWwsqCqzxxV2")
    @DsfLog(logEventName = "?????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    @ApiImplicitParams({@ApiImplicitParam(name = "param", value = "???????????????", required = true, dataType = "String", paramType = "body")})
    public Map getWwsqCqzxxV2(HttpServletRequest request) {
        //??????????????????
        ParamBody paramBody = bodyUtil.getWwsqParamBody(request, "getWwsqCqzxx");
        //returncode  ???????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        //?????????????????????????????????
        GetWwsqCqzxxResponseDTO responseDTO = null;

        if (paramBody != null && paramBody.getData() != null) {
            Map<String, Object> paramMap = paramBody.getData();

            // ???????????? ????????????
            if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "gxrmc"))
                    || StringUtils.isNotBlank(MapUtils.getString(paramMap, "xmid"))) {
                try {
                    responseDTO = exchangeBeanRequestService.request("getWwsqCqzxx", paramMap, GetWwsqCqzxxResponseDTO.class);
                    // ?????????????????????????????????
                    gxWwSqxxService.setGetWwsqCqzxxCqzhByQlrxx(responseDTO, MapUtils.getString(paramMap, "gxrzjh")
                            , MapUtils.getString(paramMap, "gxrmc"));

                    FwqlCxRequestQlr fwqlCxRequestQlr = new FwqlCxRequestQlr();
                    fwqlCxRequestQlr.setQlr(MapUtils.getString(paramMap, "gxrmc"));
                    fwqlCxRequestQlr.setBdcdyh(MapUtils.getString(paramMap, "gxrmc"));
                    fwqlCxRequestQlr.setCqzh(MapUtils.getString(paramMap, "cqzh"));
                    fwqlCxRequestQlr.setZl(MapUtils.getString(paramMap, "zl"));
                    fwqlCxRequestQlr.setSfzh(MapUtils.getString(paramMap, "gxrzjh"));

                    String xzzt = gxWwSqxxService.getWwsqCqzxxXzxx(fwqlCxRequestQlr);
                    responseDTO.setXzzt(xzzt);
                    returncode = Constants.CODE_SUCCESS;
                    msg = MsgEnum.SUCCESS.getMsg();
                } catch (Exception e) {
                    msg = e.getMessage();
                }
            }
        }
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        if (paramBody != null && MapUtils.isNotEmpty(paramBody.getHead())) {
            map.putAll(paramBody.getHead());
        }
        return bodyUtil.getReturnBody(map, responseDTO);
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????? ??????????????????????????????
     *
     * @param request ??????
     * @return GrdacxResponse
     * @Date 2021/3/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */

    @PostMapping("/grdacx/withoutYg")
    @DsfLog(logEventName = "??????????????????_?????????", dsfFlag = "WWSQ", responser = "BDC")
    @ApiImplicitParams({@ApiImplicitParam(name = "param", value = "???????????????", required = true, dataType = "String", paramType = "body")})
    public GrdacxResponseMsg gedacxWithoutYg(@RequestBody GrdacxRequest request) {
        LOGGER.info("???????????????{}", request.toString());
        //??????????????????
        String code = Constants.FAIL_CODE;
        String msg = MsgEnum.ERROR.getMsg();
        GrdacxResponseData responseData = new GrdacxResponseData();
        if (request != null && request.getData() != null) {
            try {
                List<GrdacxSqxx> grdacxSqxxList = grdacxService.getSqxxWithoutYg(request.getData());
                responseData.setSqxx(grdacxSqxxList);
                code = Constants.SUCCESS_CODE;
                msg = MsgEnum.SUCCESS.getMsg();
            } catch (Exception e) {
                code = Constants.FAIL_CODE;
                msg = e.getMessage();
            }
        }
        GrdacxResponseMsg grdacxResponseMsg = new GrdacxResponseMsg();
        grdacxResponseMsg.setCode(code);
        grdacxResponseMsg.setMsg(msg);
        grdacxResponseMsg.setData(responseData);
        return grdacxResponseMsg;
    }

    /**
     * @param slbh
     * @return json ????????????????????????
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description ????????????, ????????????????????????
     */
    @OpenApi(apiDescription = "??????????????????", apiName = "", openLog = false)
    @GetMapping("/zmfb")
    public JSONObject getZmfb(@RequestParam("slbh") String slbh) throws Exception {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        JSONObject json = null;
        try {
            json = gxWwSqxxService.getZmfb(slbh);
        } catch (Exception e) {
            throw e;
        }
        return json;

    }

    /**
     * @param requestDTO
     * @return json
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
     * @description ??????????????????????????????
     */
    @OpenApi(apiDescription = "??????????????????????????????", apiName = "", openLog = false)
    @PostMapping("/dycxcq")
    @DsfLog(logEventName = "??????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    public Map dycxcq(@RequestBody DycxcqRequestDTO requestDTO) throws Exception {

        if (StringUtils.isBlank(requestDTO.getDyqr()) && StringUtils.isBlank(requestDTO.getDyzmh())) {
            throw new MissingArgumentException("dyqr???dyzmh??????????????????");
        }
        //??????????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.PARAM_ERROR.getMsg();
        List returnList = new ArrayList();
        try {
            returnList = gxWwSqxxService.getWwsqDyCqxx(requestDTO);
            returncode = Constants.CODE_SUCCESS;
            msg = MsgEnum.SUCCESS.getMsg();
        } catch (Exception e) {
            msg = e.getMessage();
        }
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        return bodyUtil.getReturnBody(map, returnList);
    }


    /**
     * @param slbh
     * @return boolean
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description ??????????????????????????????
     */
    @OpenApi(apiDescription = "????????????????????????????????????", apiName = "", openLog = false)
    @GetMapping("/deleteFjBySlbh")
    @DsfLog(logEventName = "????????????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    public Map deleteFjclBySlbh(@RequestParam("slbh") String slbh) throws Exception {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        LOGGER.info("????????????????????????????????????,????????????{}", slbh);
        //??????????????????
        String returncode = Constants.CODE_SEARCH_ERROR;
        String msg = MsgEnum.ERROR.getMsg();

        //?????????????????????
        int count = 0;
        // ???????????????????????????????????????????????????
        List<BdcSlSjclDTO> listSjclBySlbh = bdcSlSjclFeignService.listSjclBySlbh(slbh);
        if (CollectionUtils.isEmpty(listSjclBySlbh)) {
            msg = "???????????????";
        } else {
            try {
                for (BdcSlSjclDTO bdcSlSjclDTO : listSjclBySlbh) {
                    // ??????????????????id??????????????????
                    bdcSlSjclFeignService.deleteBdcSlSjclBySjclid(bdcSlSjclDTO.getSjclid());
                    if (CollectionUtils.isNotEmpty(bdcSlSjclDTO.getChildrenList())) {
                        for (BdcSlSjclDTO bdcSlSjclChildrenDTO : bdcSlSjclDTO.getChildrenList()) {
                            bdcSlSjclFeignService.deleteBdcSlSjclBySjclid(bdcSlSjclChildrenDTO.getSjclid());
                            count++;
                        }
                    }
                    count++;
                }
                returncode = Constants.CODE_SUCCESS;
                msg = MsgEnum.SUCCESS.getMsg();
            } catch (Exception e) {
                msg = e.getMessage();
            }
        }
        LOGGER.info("????????????????????????????????????,????????????{},????????????{}???????????????", slbh, count);
        //??????????????????
        Map map = new HashMap();
        map.put(Constants.RETURNCODE, returncode);
        map.put(Constants.STATUSCODE, returncode);
        map.put(Constants.MSG, msg);
        return bodyUtil.getReturnBody(map, null);
    }

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description ??????houseid????????????????????????????????????????????????
     */
    @OpenApi(apiDescription = "??????houseid????????????????????????????????????????????????", apiName = "", openLog = false)
    @GetMapping("/getFwxx")
    @DsfLog(logEventName = "??????houseid????????????????????????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    public List<FwxxbybdcdyhResponseDTO> getFwxx(@RequestParam(name = "houseid", required = false) String houseid,
                                                 @RequestParam(name = "zl", required = false) String zl,
                                                 @RequestParam(name = "bdcdyh", required = false) String bdcdyh) {
        List<BdcdyResponseDTO> bdcdyResponseDTOS = bdcdyFeignService.queryBdcdyByHouseId(houseid, bdcdyh, zl);
        List<Map> djDcbResponseMaps = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcdyResponseDTOS)) {
            //???????????????????????????????????????
            bdcdyResponseDTOS = bdcdyResponseDTOS.stream().filter(o -> StringUtils.isNotBlank(o.getBdcdyh())).collect(Collectors.toList());
            for (BdcdyResponseDTO dto : bdcdyResponseDTOS) {
                DjDcbResponseDTO djDcbResponseDTO = djxxFeignService.queryDjDcbByBdcdyh(dto.getBdcdyh(), null);
                Map<String, String> djDcbResponseMap = new HashMap<>();
                djDcbResponseMap.put("bdcdyh", dto.getBdcdyh());
                if (djDcbResponseDTO != null) {
                    djDcbResponseMap.put("scmj", String.valueOf(((ZdDjdcbResponseDTO) djDcbResponseDTO).getScmj()));
                }
                djDcbResponseMaps.add(djDcbResponseMap);
            }
            List<FwxxbybdcdyhResponseDTO> list = gxWwSqxxService.covertDate(bdcdyResponseDTOS, djDcbResponseMaps);
            List<FwxxbybdcdyhResponseDTO> retrunList = new ArrayList<>();
            for (FwxxbybdcdyhResponseDTO dto : list) {
                FwxxbybdcdyhResponseDTO fwxxbybdcdyhResponseDTO = gxWwSqxxService.setZtTofwxxByBdcdyhResponse(dto);
                retrunList.add(fwxxbybdcdyhResponseDTO);
            }
            return retrunList;
        }
        return new ArrayList<FwxxbybdcdyhResponseDTO>();
    }

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description ??????houseid????????????????????????????????????????????????
     */
    @OpenApi(apiDescription = "??????houseid????????????????????????????????????????????????", apiName = "", openLog = false)
    @GetMapping("/zdbjByslbhList")
    @DsfLog(logEventName = "??????houseid????????????????????????????????????????????????", dsfFlag = "WWSQ", requester = "WWSQ", responser = "BDC")
    public void zdbjByslbhList(@RequestParam(name = "slbhStr", required = false) String slbhStr, @RequestParam(name = "slr", required = false) String slr) {
        if (StringUtils.isNotBlank(slbhStr)) {
            List<String> slbhList = Arrays.asList(slbhStr.split(","));
            if (CollectionUtils.isNotEmpty(slbhList)) {
                for (String slbh : slbhList) {
                    Map map = new HashMap();
                    map.put("slbh", slbh);
                    map.put("slr", slr);
                    exchangeBeanRequestService.request("zdbjbyslbh", map);
                }

            }
        }
    }


    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ???????????????fdcq??????
     * ????????????????????????jsydsyq??????
     * ???????????????cfdj??????
     * ???????????????dyaq??????
     * ????????????????????????gjzwsyq??????
     * ??????????????????hysyq??????
     * ?????????lq??????
     * ?????????????????????nydsyq??????
     * ??????????????????tdsyq??????
     * ???????????????ygdj??????
     * ???????????????yydj??????
     * ????????????????????????????????????????????????????????????
     * ???????????????????????????
     *
     * @param bdcdyh
     * @return
     */
    @PostMapping("/djbxxCx")
    public BdcxxCxDataDTO djbxxCx(@RequestParam(name = "bdcdyh", required = false) String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("??????????????????");
        }
        BdcxxCxDataDTO bdxxCxDataDTO = new BdcxxCxDataDTO();
        bdxxCxDataDTO.setBdcdyh(bdcdyh);
        List<BdcQl> bdcQls = new ArrayList<>();
        List<Integer> qsztList = new ArrayList<>();
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        qsztList.add(CommonConstantUtils.QSZT_HISTORY);
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLXX_QLLX_TDSYQ,qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLXX_QLLX_JSYDSYQ,qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_FDCQ_DM.toString(),qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_CF.toString(),qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_DYAQ_DM.toString(),qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_HY_DM.toString(),qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_GYJSYDSYQ_DM.toString(),qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLXX_QLLX_LQ,qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_YG_DM.toString(),qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_TDCBNYDSYQ.toString(),qsztList));
        bdcQls.addAll(commonService.listQlByBdcdyh(bdcdyh,CommonConstantUtils.QLLX_YY.toString(),qsztList));

        if (CollectionUtils.isEmpty(bdcQls)) {
            bdxxCxDataDTO.setIsSuccessful("fail");
            bdxxCxDataDTO.setMessage("??????????????????");
            return bdxxCxDataDTO;
        }
        try {
            bdxxCxDataDTO.setIsSuccessful("success");
            BdcxxCxDataDTO.BdcxxCxData bdxxCxData = new BdcxxCxDataDTO.BdcxxCxData();
            List<BdxxCxTdsyqDTO> tdsyqList = new ArrayList<>();
            bdxxCxData.setTdsyqxx(tdsyqList);
            List<BdxxCxJsydsyqDTO> jsydsyqList = new ArrayList<>();
            bdxxCxData.setJsydsyqxx(jsydsyqList);
            List<BdxxCxFdcqDTO> fdcqList = new ArrayList<>();
            bdxxCxData.setFdcqxx(fdcqList);
            List<BdxxCxHysyqDTO> hysyqList = new ArrayList<>();
            bdxxCxData.setHysyqxx(hysyqList);
            List<BdxxCxGjzwsyqDTO> gjzwsyqList = new ArrayList<>();
            bdxxCxData.setGjzwsyqxx(gjzwsyqList);
            List<BdxxCxLqDTO> lqList = new ArrayList<>();
            bdxxCxData.setLqxx(lqList);
            List<BdxxCxDyaqDTO> dyaqList = new ArrayList<>();
            bdxxCxData.setDyaqxx(dyaqList);
            List<BdxxCxYgDTO> ygdjList = new ArrayList<>();
            bdxxCxData.setYgdjxx(ygdjList);
            List<BdxxCxCfDTO> cfdjList = new ArrayList<>();
            bdxxCxData.setCfdjxx(cfdjList);
            List<BdxxCxTdcbnydsyqDTO> tdcbnydsyqList = new ArrayList<>();
            bdxxCxData.setNydsyqxx(tdcbnydsyqList);
            List<BdxxCxYyDTO> yydjList = new ArrayList<>();
            bdxxCxData.setYydjxx(yydjList);
            List<BdxxCxQlrDTO> bdxxCxQlrDTOS = new ArrayList<>();


            //??????????????????????????????
            List<BdcQlrDO> bdcQlrDOs = new ArrayList<>();
            List<String> allQlXmids = bdcQls.stream().map(BdcQl::getXmid).collect(Collectors.toList());
            List<List<String>> partition = Lists.partition(allQlXmids, 500);
            for (List<String> xmids : partition) {
                bdcQlrDOs.addAll(bdcQlrFeignService.listBdcQlrByXmidList(xmids, ""));
            }
            Map<String, List<BdcQlrDO>> qlrMap = bdcQlrDOs
                    .stream()
                    .collect(Collectors.groupingBy(BdcQlrDO::getXmid));
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            for (BdcQl bdcQl : bdcQls) {
                BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(bdcQl.getXmid());
                if (Objects.isNull(bdcXmDO)) {
                    continue;
                }
                String slbh = bdcXmDO.getSlbh();
                if (bdcQl instanceof BdcTdsyqDO) {
                    BdcTdsyqDO bdcTdsyqDO = (BdcTdsyqDO) bdcQl;
                    BdxxCxTdsyqDTO tdsyq = new BdxxCxTdsyqDTO();
                    BeanUtils.copyProperties(bdcTdsyqDO, tdsyq);
                    tdsyq.setZdmj(DoubleUtils.doubleToString(bdcXmDO.getZdzhmj(),DoubleUtils.df2));
                    tdsyq.setQlxz(bdcXmDO.getQlxz());
                    tdsyq.setBdcqzh(bdcXmDO.getBdcqzh());
                    tdsyq.setDjjg(bdcTdsyqDO.getDjjg());
                    tdsyq.setYwh(slbh);
                    //????????????
                    tdsyq.setMjdw(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getMjdw(), zdMap.get("mjdw")));
                    tdsyq.setYt(StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDO.getZdzhyt(), zdMap.get("tdyt")));
                    tdsyq.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    tdsyq.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    tdsyq.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx")));
                    tdsyqList.add(tdsyq);
                    bdxxCxQlrDTOS.add(tdsyq);
                } else if (bdcQl instanceof BdcJsydsyqDO) {
                    BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                    BdxxCxJsydsyqDTO jsydsyq = new BdxxCxJsydsyqDTO();
                    BeanUtils.copyProperties(bdcJsydsyqDO, jsydsyq);
                    jsydsyq.setBdcqzh(bdcXmDO.getBdcqzh());
                    jsydsyq.setDjjg(bdcJsydsyqDO.getDjjg());
                    jsydsyq.setYwh(slbh);

                    //????????????
                    jsydsyq.setYt(StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDO.getZdzhyt(), zdMap.get("tdyt")));
                    jsydsyq.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    jsydsyq.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    jsydsyq.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx")));
                    jsydsyqList.add(jsydsyq);
                    bdxxCxQlrDTOS.add(jsydsyq);
                } else if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    BdxxCxFdcqDTO fdcq = new BdxxCxFdcqDTO();
                    BeanUtils.copyProperties(bdcFdcqDO, fdcq);
                    fdcq.setBdcqzh(bdcXmDO.getBdcqzh());
                    fdcq.setDjjg(bdcFdcqDO.getDjjg());
                    fdcq.setYwh(slbh);
                    //????????????
                    fdcq.setGhyt(StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDO.getZdzhyt(), zdMap.get("tdyt")));
                    fdcq.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    fdcq.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    fdcq.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx")));
                    fdcq.setFwlx(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwlx(), zdMap.get("fwlx")));
                    fdcq.setFwjg(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwjg(), zdMap.get("fwjg")));
                    fdcq.setFwxz(StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwxz(), zdMap.get("fwxz")));
                    fdcq.setTdqlxz(StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDO.getQlxz(), zdMap.get("fwxz")));
                    fdcqList.add(fdcq);
                    bdxxCxQlrDTOS.add(fdcq);
                } else if (bdcQl instanceof BdcHysyqDO) {
                    BdcHysyqDO bdcHysyqDO = (BdcHysyqDO) bdcQl;
                    BdxxCxHysyqDTO hysyq = new BdxxCxHysyqDTO();
                    BeanUtils.copyProperties(bdcHysyqDO, hysyq);
                    hysyq.setBdcqzh(bdcXmDO.getBdcqzh());
                    hysyq.setDjjg(bdcHysyqDO.getDjjg());
                    hysyq.setYwh(slbh);
                    Map<String, String> map = new HashMap();
                    map.put("ywh", bdcXmDO.getXmid());
                    List<KttZhjbxxDO> kttZhjbxxDOS = zhjbxxMapper.queryKttZhjbxxList(map);
                    if (CollectionUtils.isNotEmpty(kttZhjbxxDOS)) {
                        hysyq.setHdmc(kttZhjbxxDOS.get(0).getHdmc());
                        hysyq.setHdyt(kttZhjbxxDOS.get(0).getHdyt());
                        hysyq.setHdwz(kttZhjbxxDOS.get(0).getHdwz());
                    }
                    //????????????
                    hysyq.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    hysyq.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    hysyq.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx")));
                    hysyq.setYhlxa(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getYhlxa(), zdMap.get("hysylxa")));
                    hysyq.setYhlxb(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getYhlxb(), zdMap.get("hysylxb")));
                    hysyqList.add(hysyq);
                    bdxxCxQlrDTOS.add(hysyq);
                } else if (bdcQl instanceof BdcGjzwsyqDO) {
                    BdcGjzwsyqDO bdcGjzwsyqDO = (BdcGjzwsyqDO) bdcQl;
                    BdxxCxGjzwsyqDTO gjzwsyq = new BdxxCxGjzwsyqDTO();
                    BeanUtils.copyProperties(bdcGjzwsyqDO, gjzwsyq);
                    gjzwsyq.setBdcqzh(bdcXmDO.getBdcqzh());
                    gjzwsyq.setYwh(slbh);
                    //????????????
                   // gjzwsyq.setGjzwghyt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    gjzwsyq.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    gjzwsyq.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    gjzwsyq.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx")));
                    gjzwsyqList.add(gjzwsyq);
                    bdxxCxQlrDTOS.add(gjzwsyq);
                } else if (bdcQl instanceof BdcLqDO) {
                    BdcLqDO bdcLqDO = (BdcLqDO) bdcQl;
                    BdxxCxLqDTO lq = new BdxxCxLqDTO();
                    BeanUtils.copyProperties(bdcLqDO, lq);
                    lq.setBdcqzh(bdcXmDO.getBdcqzh());
                    lq.setDjjg(bdcXmDO.getDjjg());
                    lq.setYwh(slbh);

                    //????????????
                    lq.setLdsyqxz(bdcLqDO.getLdsyqxzmc());
                    lq.setLz(bdcLqDO.getLzmc());
                    lq.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    lq.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    lq.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx")));
                    lqList.add(lq);
                    bdxxCxQlrDTOS.add(lq);
                } else if (bdcQl instanceof BdcDyaqDO) {
                    BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                    BdxxCxDyaqDTO dyaq = new BdxxCxDyaqDTO();
                    BeanUtils.copyProperties(bdcDyaqDO, dyaq);
                    dyaq.setDyr(bdcDyaqDO.getZwr());
                    dyaq.setBdcdjzmh(bdcXmDO.getBdcqzh());
                    dyaq.setDyqr(bdcXmDO.getQlr());
                    dyaq.setDjjg(bdcDyaqDO.getDjjg());
                    dyaq.setYwh(slbh);

                    //????????????
                    dyaq.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    dyaq.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    dyaq.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx")));
                    dyaqList.add(dyaq);
                    bdxxCxQlrDTOS.add(dyaq);
                } else if (bdcQl instanceof BdcYgDO) {
                    BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                    if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
                        continue;
                    }
                    BdxxCxYgDTO yg = new BdxxCxYgDTO();
                    BeanUtils.copyProperties(bdcYgDO, yg);
                    yg.setBdcdjzmh(bdcXmDO.getBdcqzh());
                    yg.setDjjg(bdcYgDO.getDjjg());
                    yg.setYwh(slbh);
                    if (qlrMap.containsKey(slbh)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(slbh);
                        List<BdcQlrDO> ywrs = bdcQlrDOS
                                .stream()
                                .filter(bdcQlrDO -> CommonConstantUtils.QLRLB_YWR.equals(bdcQlrDO.getQlrlb()))
                                .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(ywrs)) {
                            String ywrmc = ywrs.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.joining(","));
                            String zjh = ywrs.stream().map(BdcQlrDO::getZjh).collect(Collectors.joining(","));
                            String dh = ywrs.stream().map(BdcQlrDO::getDh).collect(Collectors.joining(","));
                            yg.setYwr(ywrmc);
                            yg.setYwrzjzl(zjh);
                            yg.setYwrdh(dh);
                        }
                    }

                    //????????????
                    yg.setGhyt(StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDO.getZdzhyt(), zdMap.get("tdyt")));
                    yg.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    yg.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    ygdjList.add(yg);
                    bdxxCxQlrDTOS.add(yg);
                } else if (bdcQl instanceof BdcCfDO) {
                    BdcCfDO bdcCfDO = (BdcCfDO) bdcQl;
                    BdxxCxCfDTO cf = new BdxxCxCfDTO();
                    BeanUtils.copyProperties(bdcCfDO, cf);
                    cf.setDjjg(bdcXmDO.getDjjg());
                    cf.setYwh(slbh);
                    //????????????
                    cf.setCflx(StringToolUtils.convertBeanPropertyValueOfZd(bdcCfDO.getCflx(), zdMap.get("cflx")));
                    cf.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    cfdjList.add(cf);
                    bdxxCxQlrDTOS.add(cf);
                } else if (bdcQl instanceof BdcTdcbnydsyqDO) {
                    BdcTdcbnydsyqDO bdcTdcbnydsyqDO = (BdcTdcbnydsyqDO) bdcQl;
                    BdxxCxTdcbnydsyqDTO bdxxCxTdcbnydsyq = new BdxxCxTdcbnydsyqDTO();
                    BeanUtils.copyProperties(bdcTdcbnydsyqDO, bdxxCxTdcbnydsyq);
                    bdxxCxTdcbnydsyq.setYwh(slbh);

                    //????????????
                    bdxxCxTdcbnydsyq.setSyttlx(StringToolUtils.convertBeanPropertyValueOfZd(bdcTdcbnydsyqDO.getSyttlx(), zdMap.get("syttlx")));
                    bdxxCxTdcbnydsyq.setGyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getGyfs(), zdMap.get("gyfs")));
                    bdxxCxTdcbnydsyq.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    bdxxCxTdcbnydsyq.setYzyfs(StringToolUtils.convertBeanPropertyValueOfZd(bdcTdcbnydsyqDO.getYzyfs(), zdMap.get("yzyfs")));
                    tdcbnydsyqList.add(bdxxCxTdcbnydsyq);
                    bdxxCxQlrDTOS.add(bdxxCxTdcbnydsyq);
                } else if (bdcQl instanceof BdcYyDO) {
                    BdcYyDO bdcYyDO = (BdcYyDO) bdcQl;
                    BdxxCxYyDTO yy = new BdxxCxYyDTO();
                    BeanUtils.copyProperties(bdcYyDO, yy);
                    yy.setYwh(slbh);
                    //????????????
                    yy.setQszt(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQszt(), zdMap.get("qszt")));
                    yydjList.add(yy);
                    bdxxCxQlrDTOS.add(yy);
                }
            }
            if (CollectionUtils.isNotEmpty(bdxxCxQlrDTOS)) {
                for (BdxxCxQlrDTO bdxxCxQlrDTO : bdxxCxQlrDTOS) {
                    if (qlrMap.containsKey(bdxxCxQlrDTO.getYwh())) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(bdxxCxQlrDTO.getYwh());
                        List<BdcQlrDO> qlrs = bdcQlrDOS
                                .stream()
                                .filter(bdcQlrDO -> CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb()))
                                .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(qlrs)) {
                            String qlrmc = qlrs.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.joining(","));
                            String zjh = qlrs.stream().map(BdcQlrDO::getZjh).collect(Collectors.joining(","));
                            String dh = qlrs.stream().map(BdcQlrDO::getDh).collect(Collectors.joining(","));
                            bdxxCxQlrDTO.setQlr(qlrmc);
                            bdxxCxQlrDTO.setQlrzjh(zjh);
                            bdxxCxQlrDTO.setQlrdh(dh);
                        }
                    } else {
                        bdxxCxQlrDTO.setQlr("");
                        bdxxCxQlrDTO.setQlrzjh("");
                        bdxxCxQlrDTO.setQlrdh("");
                    }
                }
            }
            bdxxCxDataDTO.setData(bdxxCxData);
        } catch (BeansException e) {
            e.printStackTrace();
            LOGGER.info("????????????????????????{}", e.getMessage());
            bdxxCxDataDTO.setIsSuccessful("fail");
            bdxxCxDataDTO.setMessage(e.getMessage());
        }
        return bdxxCxDataDTO;
    }


    /**
     *    "KTT_ZDJBXX":[],//??????????????????
     *    "KTT_FW_ZRZ":[],//???????????????
     *    "KTT_FW_LJZ":[],//???????????????
     *    "KTT_FW_C":[],//?????????
     *    "KTT_FW_H":[],//?????????
     *    "QLT_FW_FDCQ_YZ":[],//????????????_????????????????????????????????????
     *    "QLF_QL_DYAQ":[],//??????????????????
     *    "QLF_QL_YGDJ":[],//??????????????????
     *    "QLF_QL_YYDJ":[],//??????????????????
     *    "QLF_QL_CFDJ":[],//??????????????????
     *    "QLF_QL_XZXZ":[],//??????????????????
     *    "QLF_QL_JSYDSYQ":[],//????????????????????????????????????????????????
     *    "ZTT_GY_QLR":[],//??????????????????????????????
     * @param qlrmc
     * @param zjh
     * @return
     */
    @GetMapping("/fwqsxx")
    public JSONObject fwqsxx(
            @RequestParam(name = "QLRMC", required = true) String qlrmc,
            @RequestParam(name = "ZJH", required = true) String zjh,
            @RequestParam(name = "PAGENUM", required = true) Integer pageNum,
            @RequestParam(name = "PAGESIZE", required = true) Integer pageSize

    ) {
        if (StringUtils.isBlank(qlrmc) || StringUtils.isBlank(zjh))
        {
            LOGGER.error("???????????????????????????");
            throw new MissingArgumentException("????????????????????????");
        }
        JSONObject response = new JSONObject();
        try {
            JSONArray fwxx = gxWwSqxxService.queryFwqsxx(qlrmc,zjh,pageNum,pageSize);
            response.put("type","success");
            HashMap<String, Object> fwxxData = new HashMap();
            fwxxData.put("fwxx",fwxx);
            response.put("data",fwxxData);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.put("type","fail");
            response.put("data",e.getMessage());
        }

        return response;
    }


}
