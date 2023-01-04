package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.exchange.RequestHead;
import cn.gtmap.realestate.common.core.dto.exchange.ResponseHead;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.tsztjs.YrbSwTsztjs;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.tsztjs.response.YrbTsswztResponse;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.*;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.jsfxx.request.JfsxxHlwParam;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.jsfxx.request.JfsxxHlwRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.jsfxx.response.JfsxxHlwResponse;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.jsfxx.response.JfsxxResponseData;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.jsxxzt.JfxmlistBean;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.jsxxzt.JfxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.jsxxzt.JfxxDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.jsxxzt.JfxxlistBean;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.NantongFsxtzfRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.XmlEntityCommonConvertUtil;
import cn.gtmap.realestate.exchange.config.filter.RequestWrapper;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.Md5Util;
import cn.gtmap.realestate.exchange.util.TokenUtil;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.MsgEnum;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2019-09-12
 * @description 南通市财政局非税收入系统支付接口
 */
@OpenController(consumer = "南通市财政局非税收入系统支付服务")
@RestController
@Api(tags = "南通市财政局非税收入系统支付服务")
public class NantongFsxtzfRestController implements NantongFsxtzfRestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NantongFsxtzfRestController.class);

    @Autowired
    private HttpClientService httpClientService;

    @Value("${nantong.ntfssr.qxdm:045022}")
    private String ucode;

    @Value("${nantong.ntfssr.secret:688407410A7259D92A75316F8F8321759F89158ECCA927A1608135019EB757CB}")
    private String secret;

    @Value("${nantong.ntfssr.baseurl:http://101.201.50.247:8400/payment}")
    private String baseurl;

    @Autowired
    BdcSlSfxmFeignService bdcSlSfxmFeignService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private BdcSlHsxxFeignService bdcSlHsxxFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcSlSfxmPzFeignService bdcSlSfxmPzFeignService;
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private BdcZdFeignService zdFeignService;

    @Autowired
    private BdcSwFeignService bdcSwFeignService;

    /**
     * 2.1 获取token
     *
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    public void getToken() {
        Map<String, Object> paramMap = new HashMap<>();
        // 接口版本
        String ver = "1.0";
        paramMap.put("ver", ver);
        // 区县代码
        paramMap.put("ucode", ucode);
        // 秘钥
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 时间戳
        long datetime = System.currentTimeMillis();
        String timestamp = simpleDateFormat.format(new Date(datetime));
        paramMap.put("timestamp", timestamp);
        // 加密签名
        String sign = Md5Util.StringInMd5(ucode + ver + secret + timestamp);
        paramMap.put("sign", sign);
        // 发送请求
        String urlParam = CommonUtil.mapToUrlParam(paramMap);
        HttpGet httpGet = new HttpGet(baseurl + "/api/gettoken?" + urlParam);
        httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpGet.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        httpGet.setHeader("unitCode", "LandResourcesBureau");
        LOGGER.info("非税获取token接口请求参数：{}", urlParam);
        String response = "";
        try {
            response = httpClientService.doGet(httpGet);
            LOGGER.info("非税获取token接口响应数据：{}", response);
        } catch (IOException e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", baseurl + "/api/gettoken", paramMap, e);
            throw new AppException("httpGet 请求异常");
        }

        JSONObject responseObject = JSONObject.parseObject(response);

        if (StringUtil.equals(responseObject.getString("errcode"), "0")
                && responseObject.get("result") instanceof Map
                && StringUtil.isNotEmpty(MapUtils.getString((Map) responseObject.get("result"), "access_token"))) {
            TokenUtil.addToken("ntfssr_token", MapUtils.getString((Map) responseObject.get("result"), "access_token"));
            datetime += (MapUtils.getInteger((Map) responseObject.get("result"), "expires_in") - 60) * 1000;
            TokenUtil.addToken("ntfssr_token_timestamp", simpleDateFormat.format(new Date(datetime)));
        } else {
            throw new AppException("获取南通市财政局非税收入系统token失败");
        }
    }

    /**
     * 2.3 互联网-创建缴费书
     *
     * @param requestDTO@return
     * @Date 2021/8/5
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public JfsxxHlwResponse createBillForHlw(@RequestBody JfsxxHlwRequestDTO requestDTO) {
        JfsxxHlwParam param = requestDTO.getData();
        if (StringUtils.isBlank(param.getSfxxid()) || StringUtils.isBlank(param.getSlbh()) || StringUtils.isBlank(param.getCreater())) {
            throw new MissingArgumentException("收费信息id,受理编号,财政分配的登陆用户编码不能为空！");
        }
        // 查询收费信息
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(param.getSfxxid());

        if(Objects.isNull(bdcSlSfxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息数据，收费信息id为：" + param.getSfxxid());
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(param.getSlbh());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产项目数据，受理编号为：" + param.getSlbh());
        }

        CommonResponse commonResponse = this.bdcSlSfxxFeignService.rgtsdjfxx(param.getSfxxid(), bdcSlSfxxDO.getQlrlb(), param.getCreater(),
                bdcXmDOList.get(0).getGzlslid());
        LOGGER.info("调用受理rgtsdjfxx接口返回值为：{}", JSON.toJSONString(commonResponse));
        JfsxxHlwResponse response = new JfsxxHlwResponse();
        ResponseHead head = new ResponseHead();
        JfsxxResponseData data = new JfsxxResponseData();

        if(commonResponse.isSuccess()){
            head.setStatusCode(Constants.CODE_SUCCESS);
            head.setReturncode(Constants.CODE_SUCCESS);
            head.setMsg(MsgEnum.SUCCESS.getMsg());
            Map<String, String> result = (Map<String, String>) commonResponse.getData();
            data.setUrl(result.get("jfsewmurl"));
        }else{
            head.setStatusCode(Constants.CODE_SEARCH_ERROR);
            head.setReturncode(Constants.CODE_SEARCH_ERROR);
            head.setMsg(MsgEnum.ERROR.getMsg());
        }
        response.setHead(head);
        response.setData(data);
        LOGGER.info("互联网创建缴费书返回值为：{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 2.3 单位新增缴费书接口
     *
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @OpenApi(apiDescription = "单位新增缴费书接口", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "单位新增缴费书接口")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "JfsxxRequestDTO", paramType = "body")})
    @DsfLog(logEventName = "单位新增缴费书接口", dsfFlag = "CZJ", requester = "BDC", responser = "CZJ")
    public String createBill(@RequestBody JfsxxRequestDTO info) {
        Map<String, Object> paramMap = new HashMap<>();
        if (!checkToken()) {
            getToken();
        }
        // 接口版本
        String ver = "1.0";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 时间戳
        long datetime = System.currentTimeMillis();
        String timestamp = simpleDateFormat.format(new Date(datetime));

        // 加密签名
        String sign = Md5Util.StringInMd5(TokenUtil.getToken("ntfssr_token") + JSONObject.toJSONString(info) + ucode + ver + secret + timestamp);
        paramMap.put("ver", ver);
        paramMap.put("ucode", ucode);
        paramMap.put("access_token", TokenUtil.getToken("ntfssr_token"));
        paramMap.put("info", JSONObject.toJSONString(info));
        paramMap.put("timestamp", timestamp);
        paramMap.put("sign", sign);

        List<NameValuePair> parameters = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramMap.keySet())) {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value);
                }
                parameters.add(new BasicNameValuePair(key, (String) value));
            }
        }
        HttpPost httpPost = new HttpPost(baseurl + "/api/createbill");
        httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("unitCode", "LandResourcesBureau");

        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
            LOGGER.info("单位新增缴费书接口返回：{}", response);
        } catch (IOException e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", baseurl + "/api/createbill", paramMap, e);
            throw new AppException("httpPost 请求异常");
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        if (StringUtil.equals(jsonObject.getString("errcode"), "0")) {
            return jsonObject.getString("result");
        } else if (StringUtil.equals(jsonObject.getString("errcode"), "4003")) {
            getToken();
        }

        return null;
    }

    /**
     * 2.5支付结果通知接口
     *
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @OpenApi(apiDescription = "支付结果通知接口", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "支付结果通知接口")
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "支付结果通知接口", dsfFlag = "CZJ", requester = "CZJ", responser = "BDC")
    @ApiImplicitParams({@ApiImplicitParam(name = "MSG", value = "请求参数体", required = true, dataType = "String", paramType = "query")})
    public Object payResult(@RequestParam(value = "MSG") String msg) {
        LOGGER.info("支付结果通知接口，解密前数据 ：{}", msg);
        Map resultMap = new HashMap();
        if (StringUtils.isNotBlank(msg)) {
            // base64解密
            String str = new String(Base64.decodeBase64(msg), Charset.defaultCharset());
            LOGGER.info("支付结果通知接口，解密后数据 ：{}", str);
            // 转化数据
            Map<String, String> data = JSONObject.parseObject(str, Map.class);
            // 重组数据，并加入单位密钥
            Map<String, String> requestParams = new LinkedHashMap<>();
            requestParams.put("billCodeNumb", data.get("billCodeNumb"));
            requestParams.put("code", data.get("code"));
            requestParams.put("dateTime", data.get("dateTime"));
            requestParams.put("fundSerialNumber", data.get("fundSerialNumber"));
            requestParams.put("orderSn", data.get("orderSn"));
            requestParams.put("secret", secret);
            if (StringUtils.isNotBlank(data.get("specialCode"))) {
                requestParams.put("specialCode", data.get("specialCode"));
            }
            requestParams.put("status", data.get("status"));
            requestParams.put("totalAmount", data.get("totalAmount"));
            if (StringUtils.isNotBlank(data.get("tradeUniqueCode"))) {
                requestParams.put("tradeUniqueCode", data.get("tradeUniqueCode"));
            }
            LOGGER.info("支付结果通知接口，处理签名用map ：{}", JSON.toJSONString(requestParams));
            // 生成签名
            String sign = Base64.encodeBase64String(DigestUtils.md5(JSONUtils.toJSONString(requestParams).getBytes(Charset.defaultCharset())));
            LOGGER.info("支付结果通知接口，生成签名 ：{}", sign);
//            Long sysTime = System.currentTimeMillis() / 1000;
//            Long beginTime = sysTime - 3600;
//            Long endTime = sysTime + 43200;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date dateTime;
            try {
                dateTime = simpleDateFormat.parse(MapUtils.getString(data, "dateTime"));
            } catch (ParseException ex) {
                throw new AppException("日期时间不正确");
            }
//            Long dateTimeLong = dateTime == null ? NumberUtils.createLong("0") : dateTime.getTime() / 1000;

//            if (dateTimeLong < beginTime || endTime < dateTimeLong) {
//                throw new AppException("交易时间非法");
//            }

            // 验证签名是否正确
            if (StringUtil.equals(sign, data.get("sign"))) {

                // 调用保存信息
                BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                bdcSlSfxxDO.setSfxxid(MapUtils.getString(data, "tradeUniqueCode"));
                bdcSlSfxxDO.setJfsbm(MapUtils.getString(data, "billCodeNumb"));
                bdcSlSfxxDO.setSfztczsj(dateTime);
                LOGGER.info("当前流程{}支付更新收费状态,时间为{}", bdcSlSfxxDO.getGzlslid(), dateTime);
                bdcSlSfxxDO.setSfzt(MapUtils.getInteger(data, "status"));
                bdcSlSfxxDO.setSfdwdm(MapUtils.getString(data, "code"));
                // added by cyc at 2020/11/16
                bdcSlSfxxDO.setSfxsjf(CommonConstantUtils.SF_S_DM);
                Integer gxFlag = bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);

                if (gxFlag != null && gxFlag > 0) {
                    String sfxxid = MapUtils.getString(data, "tradeUniqueCode");
                    resultMap.put("errcode", 0);
                    resultMap.put("errmsg", "Success");
                    //通知互联网
                    if (Objects.equals(CommonConstantUtils.SFZT_YJF, bdcSlSfxxDO.getSfzt())) {
                        updateJfztForHlw(sfxxid);
                        LOGGER.info("通知了互联网,{}", bdcSlSfxxDO.toString());
                    } else {
                        LOGGER.info("没有通知互联网,sfzt为{}", bdcSlSfxxDO.getSfzt());
                    }

                } else {
                    resultMap.put("errcode", 1);
                    resultMap.put("errmsg", "更新数据失败");
                    LOGGER.info("没有通知互联网1,更新数据失败{}", bdcSlSfxxDO.toString());

                }
            } else {
                resultMap.put("errcode", 2);
                resultMap.put("errmsg", "签名信息错误");
                LOGGER.info("没有通知互联网1,签名信息错误{}", data);

            }
        } else {
            resultMap.put("errcode", 3);
            resultMap.put("errmsg", "MSG为空，无法更新");
            LOGGER.info("没有通知互联网1,MSG为空，无法更新{}", msg);

        }

        return JSONUtils.toJSONString(resultMap);
    }

    /**
     * 财政通知我们，更新成功后，我们通知互联网
     *
     * @param sfxxid
     * @return
     * @Date 2021/8/7
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void updateJfztForHlw(@RequestParam(value = "sfxxid") String sfxxid) {
        //更新成功时，需要通知互联网更新缴费信息
        JfxxDTO jfxxDTO = new JfxxDTO();
        RequestHead head = new RequestHead();
        jfxxDTO.setHead(head);

        JfxxDataDTO jfxxDataDTO = new JfxxDataDTO();
        List<JfxxlistBean> jfxxlistBeanList = new ArrayList<>();
        //查询收费信息
        BdcSlSfxxDO slSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        //判断缴费状态
        String jfzt = "";
        if (CommonConstantUtils.SFZT_YJF.equals(slSfxxDO.getSfzt())) {
            jfzt = "1";
        } else {
            jfzt = "0";
        }
        //查询项目信息
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setXmid(slSfxxDO.getXmid());
        List<BdcXmDO> xmDOList = bdcXmFeignService.listBdcXm(xmQO);
        if (CollectionUtils.isNotEmpty(xmDOList)) {
            Integer sply = xmDOList.get(0).getSply();
            jfxxDataDTO.setWwslbh(xmDOList.get(0).getSlbh());

            // 查询收费项目
            List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(sfxxid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList) && null != slSfxxDO) {
                LOGGER.info("收费项目和收费信息不为空");
                JfxxlistBean jfxxlistBean = new JfxxlistBean();
                jfxxlistBean.setSfxxid(sfxxid);
                List<JfxmlistBean> sfxmlist = new ArrayList<>();
                for (BdcSlSfxmDO slSfxxDO1 : bdcSlSfxmDOList) {
                    JfxmlistBean jfxmlistBean = new JfxmlistBean();
                    jfxmlistBean.setJfxxid(slSfxxDO1.getSfxmid());
                    jfxmlistBean.setJfzt(jfzt);
                    sfxmlist.add(jfxmlistBean);
                }
                jfxxlistBean.setSfxmlist(sfxmlist);
                jfxxlistBeanList.add(jfxxlistBean);
                jfxxDataDTO.setSfxxlist(jfxxlistBeanList);
            }
            jfxxDTO.setData(jfxxDataDTO);
            //调用通知互联网接口 sply =11
            if (Objects.equals(sply, CommonConstantUtils.SPLY_YCTB_SS)) {
                Object response = exchangeBeanRequestService.request("wwsqupdatejfzt_hlw", jfxxDTO);
                LOGGER.info("通知互联网缴费信息结果：{}", response.toString());

            }
            LOGGER.info("sply:{},不通知通知互联网缴费信息结果", sply);
        }
    }

    /**
     * 3.1获取电子票据信息接口
     *
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     */
    @OpenApi(apiDescription = "获取电子票据信息接口", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "获取电子票据信息接口")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "InvoiceDownRequestDTO", paramType = "body")})
    @DsfLog(logEventName = "获取电子票据信息接口", dsfFlag = "CZJ", requester = "BDC", responser = "CZJ")
    public Object invoiceBeforIssue(@RequestBody InvoiceRequestDTO info) {
        Map<String, Object> paramMap = getPublicParams(info, "invoiceBeforIssue");
        paramMap.put("billno", info.getBillno());
        paramMap.put("drawer", info.getDrawer());
        paramMap.put("serialNumber", info.getSerialNumber());
        paramMap.put("mobile", info.getMobile());
        paramMap.put("email", info.getEmail());
        paramMap.put("note", info.getNote());
        return invoicePublic(paramMap, "/elect-invoice/before-issue");
    }

    /**
     * 3.2 提交电子票据信息接口
     *
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     */
    @OpenApi(apiDescription = "提交电子票据信息接口", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "提交电子票据信息接口")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "InvoiceDownRequestDTO", paramType = "body")})
    @DsfLog(logEventName = "提交电子票据信息接口", dsfFlag = "CZJ", requester = "BDC", responser = "CZJ")
    public Object invoiceHandleIssue(@RequestBody InvoiceRequestDTO info) {
        Map<String, Object> paramMap = getPublicParams(info, "invoiceHandleIssue");
        paramMap.put("billno", info.getBillno());
        paramMap.put("drawer", info.getDrawer());
        paramMap.put("serialNumber", info.getSerialNumber());
        paramMap.put("mobile", info.getMobile());
        paramMap.put("email", info.getEmail());
        paramMap.put("note", info.getNote());
        paramMap.put("invoiceCode", info.getInvoiceCode());
        paramMap.put("invoiceNumber", info.getInvoiceNumber());
        paramMap.put("invoiceData", info.getInvoiceData());
        paramMap.put("signData", info.getSignData());
        return invoicePublic(paramMap, "/elect-invoice/handle-issue");
    }

    /**
     * 3.3下载电子票据信息接口
     *
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     */
    @OpenApi(apiDescription = "下载电子票据信息接口", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "下载电子票据信息接口")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "InvoiceDownRequestDTO", paramType = "body")})
    @DsfLog(logEventName = "下载电子票据信息接口", dsfFlag = "CZJ", requester = "BDC", responser = "CZJ")
    public Object invoiceDownload(@RequestBody InvoiceRequestDTO info) {
        Map<String, Object> paramMap = getPublicParams(info, "invoiceDownload");
        paramMap.put("billno", info.getBillno());
        return invoicePublic(paramMap, "/elect-invoice/download");
    }

    /**
     * 2.8作废缴费书
     *
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @OpenApi(apiDescription = "作废缴费书", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "作废缴费书")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "InvoiceDownRequestDTO", paramType = "body")})
    @DsfLog(logEventName = "作废缴费书", dsfFlag = "CZJ", requester = "BDC", responser = "CZJ")
    public Object voidPaymentForm(@RequestBody VoidPaymentFormRequestDTO voidPaymentFormRequestDTO) {
        Map<String, Object> paramMap = new HashMap<>();
        if (!checkToken()) {
            getToken();
        }
        // 接口版本
        String ver = "1.0";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 时间戳
        long datetime = System.currentTimeMillis();
        String timestamp = simpleDateFormat.format(new Date(datetime));

        // 加密签名
        String sign = Md5Util.StringInMd5(TokenUtil.getToken("ntfssr_token")
                + voidPaymentFormRequestDTO.getBillno()
                + voidPaymentFormRequestDTO.getReason()
                + ucode + ver + secret + timestamp);
        paramMap.put("ver", ver);
        paramMap.put("ucode", ucode);
        paramMap.put("billno", voidPaymentFormRequestDTO.getBillno());
        paramMap.put("reason", voidPaymentFormRequestDTO.getReason());
        paramMap.put("timestamp", timestamp);
        paramMap.put("access_token", TokenUtil.getToken("ntfssr_token"));
        paramMap.put("sign", sign);

        List<NameValuePair> parameters = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramMap.keySet())) {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value);
                }
                parameters.add(new BasicNameValuePair(key, (String) value));
            }
        }
        HttpPost httpPost = new HttpPost(baseurl + "/api/invalidate");
        httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("unitCode", "LandResourcesBureau");

        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
            LOGGER.info("作废缴费书,返回信息:{}", response);
        } catch (IOException e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", baseurl + "/api/invalidate", paramMap, e);
            throw new AppException("httpPost 请求异常");
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        if (StringUtil.equals(jsonObject.getString("errcode"), "0")) {
//            return jsonObject.getString("result");
            return jsonObject;
        } else if (StringUtil.equals(jsonObject.getString("errcode"), "4003")) {
            getToken();
        }
        return null;
    }

    /**
     * 3.3获取冲红票据信息
     *
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @OpenApi(apiDescription = "获取冲红票据信息", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "获取冲红票据信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "ObtainRedemptionTicketInfoRequestDTO", paramType = "body")})
    @DsfLog(logEventName = "获取冲红票据信息", dsfFlag = "CZJ", requester = "BDC", responser = "CZJ")
    public Object obtainRedemptionTicketInfo(@RequestBody ObtainRedemptionTicketInfoRequestDTO obtainRedemptionTicketInfoRequestDTO) {
        Map<String, Object> paramMap = new HashMap<>();
        if (!checkToken()) {
            getToken();
        }
        // 接口版本
        String ver = "1.0";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 时间戳
        long datetime = System.currentTimeMillis();
        String timestamp = simpleDateFormat.format(new Date(datetime));

        // 加密签名
        String sign = Md5Util.StringInMd5(TokenUtil.getToken("ntfssr_token")
                + obtainRedemptionTicketInfoRequestDTO.getBillno()
                + obtainRedemptionTicketInfoRequestDTO.getDrawer()
                + obtainRedemptionTicketInfoRequestDTO.getSerialNumber()
                + ucode + ver + secret + timestamp);
        paramMap.put("ver", ver);
        paramMap.put("access_token", TokenUtil.getToken("ntfssr_token"));
        paramMap.put("billno", obtainRedemptionTicketInfoRequestDTO.getBillno());
        paramMap.put("serialNumber", obtainRedemptionTicketInfoRequestDTO.getSerialNumber());
        paramMap.put("ucode", ucode);
        paramMap.put("drawer", obtainRedemptionTicketInfoRequestDTO.getDrawer());
        paramMap.put("timestamp", timestamp);
        paramMap.put("sign", sign);

        List<NameValuePair> parameters = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramMap.keySet())) {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value);
                }
                parameters.add(new BasicNameValuePair(key, (String) value));
            }
        }
        HttpPost httpPost = new HttpPost(baseurl + "/api/elect-invoice/before-revoke");
        httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("unitCode", "LandResourcesBureau");

        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
            LOGGER.info("获取冲红票据信息,返回信息:{}", response);
        } catch (IOException e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", baseurl + "/api/elect-invoice/before-revoke", paramMap, e);
            throw new AppException("httpPost 请求异常");
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        if (StringUtil.equals(jsonObject.getString("errcode"), "0")) {
//            return jsonObject.getString("result");
            return jsonObject;
        } else if (StringUtil.equals(jsonObject.getString("errcode"), "4003")) {
            getToken();
        }
        return null;
    }

    /**
     * 3.4提交冲红票据开具
     *
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @OpenApi(apiDescription = "提交冲红票据开具", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "提交冲红票据开具")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "HandleRedemptionTicketInfoRequestDTO", paramType = "body")})
    @DsfLog(logEventName = "提交冲红票据开具", dsfFlag = "CZJ", requester = "BDC", responser = "CZJ")
    @ResponseBody
    public Object handleRedemptionTicketInfo(@RequestBody HandleRedemptionTicketInfoRequestDTO handleRedemptionTicketInfoRequestDTO) {
        Map<String, Object> paramMap = new HashMap<>();
        if (!checkToken()) {
            getToken();
        }
        // 接口版本
        String ver = "1.0";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 时间戳
        long datetime = System.currentTimeMillis();
        String timestamp = simpleDateFormat.format(new Date(datetime));

        // 加密签名
        String sign = Md5Util.StringInMd5(TokenUtil.getToken("ntfssr_token")
                + handleRedemptionTicketInfoRequestDTO.getBillno()
                + handleRedemptionTicketInfoRequestDTO.getDrawer()
                + handleRedemptionTicketInfoRequestDTO.getInvoiceCode()
                + handleRedemptionTicketInfoRequestDTO.getInvoiceData()
                + handleRedemptionTicketInfoRequestDTO.getInvoiceNumber()
                + handleRedemptionTicketInfoRequestDTO.getSerialNumber()
                + handleRedemptionTicketInfoRequestDTO.getSignData()
                + ucode + ver + secret + timestamp);
        paramMap.put("ver", ver);
        paramMap.put("access_token", TokenUtil.getToken("ntfssr_token"));
        paramMap.put("billno", handleRedemptionTicketInfoRequestDTO.getBillno());
        paramMap.put("serialNumber", handleRedemptionTicketInfoRequestDTO.getSerialNumber());
        paramMap.put("drawer", handleRedemptionTicketInfoRequestDTO.getDrawer());
        paramMap.put("invoiceCode", handleRedemptionTicketInfoRequestDTO.getInvoiceCode());
        paramMap.put("invoiceNumber", handleRedemptionTicketInfoRequestDTO.getInvoiceNumber());
        paramMap.put("invoiceData", handleRedemptionTicketInfoRequestDTO.getInvoiceData());
        paramMap.put("signData", handleRedemptionTicketInfoRequestDTO.getSignData());
        paramMap.put("ucode", ucode);
        paramMap.put("timestamp", timestamp);
        paramMap.put("sign", sign);

        List<NameValuePair> parameters = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramMap.keySet())) {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value);
                }
                parameters.add(new BasicNameValuePair(key, (String) value));
            }
        }
        HttpPost httpPost = new HttpPost(baseurl + "/api/elect-invoice/handle-revoke");
        httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("unitCode", "LandResourcesBureau");

        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
            LOGGER.info("提交冲红票据开具,返回信息:{}", response);
        } catch (IOException e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", baseurl + "/api/elect-invoice/handle-revoke", paramMap, e);
            throw new AppException("httpPost 请求异常");
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        if (StringUtil.equals(jsonObject.getString("errcode"), "0")) {
//            return jsonObject.getString("result");
            return jsonObject;
        } else if (StringUtil.equals(jsonObject.getString("errcode"), "4003")) {
            getToken();
        }
        return null;
    }

    /**
     * 电子票据接口公共部分
     *
     * @param paramMap
     * @param interfaceUrl
     * @return
     */
    private JSONObject invoicePublic(Map<String, Object> paramMap, String interfaceUrl) {
        List<NameValuePair> parameters = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramMap.keySet())) {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key) == null ? "" : paramMap.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value == null ? "" : value);
                }
                parameters.add(new BasicNameValuePair(key, (String) value));
            }
        }
        LOGGER.info("下载电子票据信息接口入参：{}", JSONObject.toJSONString(parameters));
        HttpPost httpPost = new HttpPost(baseurl + "/api" + interfaceUrl);
        httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("unitCode", "LandResourcesBureau");
        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (IOException e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", baseurl + "/api" + interfaceUrl, paramMap, e);
            throw new AppException("httpPost 请求异常");
        }
        return JSONObject.parseObject(response);
    }

    /**
     * 发票接口公共参数
     *
     * @return
     */
    private Map<String, Object> getPublicParams(InvoiceRequestDTO info, String interfaceName) {
        if (!checkToken()) {
            getToken();
        }
        Map<String, Object> paramMap = new HashMap<>();
        // 接口版本
        String ver = "1.0";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 时间戳
        long datetime = System.currentTimeMillis();
        String timestamp = simpleDateFormat.format(new Date(datetime));
        String token = TokenUtil.getToken("ntfssr_token");
        paramMap.put("ver", ver);
        paramMap.put("access_token", token);
        paramMap.put("ucode", ucode);
        paramMap.put("timestamp", timestamp);
        String head = token + info.getBillno();
        String middle = "";
        switch (interfaceName) {
            case "invoiceBeforIssue":
                middle = info.getDrawer() + info.getEmail() + info.getMobile() + info.getNote() + info.getSerialNumber();
                break;
            case "invoiceHandleIssue":
                middle = info.getDrawer() + info.getEmail() + info.getInvoiceCode() + info.getInvoiceData() + info.getInvoiceNumber() + info.getMobile() + info.getNote() + info.getSerialNumber() + info.getSignData();
                break;
            case "invoiceDownload":
                break;
        }
        String end = ucode + ver + secret + timestamp;
        // 加密签名
        LOGGER.info("加密前：{}", JSONObject.toJSONString(head + middle + end));
        paramMap.put("sign", Md5Util.StringInMd5(head + middle + end));
        LOGGER.info("加密后：{}", JSONObject.toJSONString(paramMap.get("sign")));
        return paramMap;
    }

    /**
     * 确认token是否有效
     *
     * @return
     */
    private boolean checkToken() {
        boolean tokenCheck = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        if (StringUtil.isNotEmpty(TokenUtil.getToken("ntfssr_token"))
                && StringUtil.isNotEmpty(TokenUtil.getToken("ntfssr_token_timestamp"))) {
            try {
                Date datetime = simpleDateFormat.parse(TokenUtil.getToken("ntfssr_token_timestamp"));
                Date timestamp = new Date(System.currentTimeMillis());
                if (timestamp.compareTo(datetime) < 0) {
                    tokenCheck = true;
                }
            } catch (ParseException e) {
                throw new AppException("南通市财政局非税收入系统token判断失败");
            }
        }
        return tokenCheck;
    }


    @PostMapping("/realestate-exchange/rest/v1.0/sw/rwztts")
    public void rwztts(HttpServletRequest request, HttpServletResponse response) {
        RequestWrapper requestWrapper = null;
        YrbTsswztResponse fhmResponse = new YrbTsswztResponse();

        try {
            requestWrapper = new RequestWrapper(request);
            LOGGER.info("南通税务状态，接受请求参数为：{}", requestWrapper.getRequestBody());
            if (StringUtils.isNotBlank(requestWrapper.getRequestBody())) {
                String replace = "<?XML VERSION=\"1.0\" ENCODING=\"UTF-8\"?>";
                String requestString = requestWrapper.getRequestBody().substring(requestWrapper.getRequestBody().indexOf(">") + 1);

                String json = XmlEntityCommonConvertUtil.xmlToJson(requestString);
                JSONObject jsonObject = JSONObject.parseObject(json);

                YrbSwTsztjs yrbSwTsztjs = new YrbSwTsztjs();
                yrbSwTsztjs = JSONObject.parseObject(jsonObject.getJSONObject("TAXBIZML").getString("SBZTTSXXLIST"), YrbSwTsztjs.class);
                BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                bdcZdDsfzdgxDO.setZdbs("SW_SBZT");
                bdcZdDsfzdgxDO.setDsfzdz(yrbSwTsztjs.getShzt());
                bdcZdDsfzdgxDO.setDsfxtbs("SW");
                BdcZdDsfzdgxDO zdDsfzdgxDO = zdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                if (null != zdDsfzdgxDO) {
                    Map map = bdcSwFeignService.updateWsztByHtbh(yrbSwTsztjs.getHtbh(), zdDsfzdgxDO.getBdczdz());
                    LOGGER.info("更新完税信息返回：{}", map.toString());
                    if (map.get("msg").equals("success")) {
                        fhmResponse.setFhm(Constants.SUCCESS_CODE_0);
                        fhmResponse.setFhxx("更新完税状态成功！");
                    } else {
                        fhmResponse.setFhm(Constants.FAIL_CODE_1);
                        fhmResponse.setFhxx(map.get("msg").toString());
                    }
                } else {
                    fhmResponse.setFhm(Constants.FAIL_CODE_1);
                    fhmResponse.setFhxx("审核状态未配置对照！请检查第三方字典标识SW_SBZT，系统标识SW相关对照配置！");
                }
            } else {
                fhmResponse.setFhm(Constants.FAIL_CODE);
                fhmResponse.setFhxx("接受状态信息为空！");
            }

        } catch (Exception e) {
            fhmResponse.setFhm(Constants.FAIL_CODE);
            fhmResponse.setFhxx("南通接受税务状态返回异常！");
            LOGGER.info("南通接受税务状态返回转码异常：{}", e);

        }
        try {
            String dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(fhmResponse, YrbTsswztResponse.class);
            byte[] bytes = dataXml.getBytes();
            String data = new String(bytes, "UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream outputStream;

            outputStream = response.getOutputStream();
            outputStream.write(dataXml.getBytes("utf-8"));
            outputStream.close();
        } catch (Exception e) {
            LOGGER.info("南通接受税务状态返回转码异常：{}", e);
        }


    }

    /**
     * @description 1.2 税费同缴新增缴费书接口
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/26 17:58
     * @param info
     * @return String
     */
    @OpenApi(apiDescription = "税费同缴新增缴费书接口", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "税费同缴新增缴费书接口")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "info", value = "请求参数体", required = true, dataType = "JfsxxRequestDTO", paramType = "body")})
    @DsfLog(logEventName = "税费同缴新增缴费书接口", dsfFlag = "CZJ", requester = "BDC", responser = "CZJ")
    public String createSftjBill(@RequestBody JfsxxRequestDTO info) {
        Map<String, Object> paramMap = new HashMap<>();
        if (!checkToken()) {
            getToken();
        }
        // 接口版本
        String ver = "1.0";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 时间戳
        long datetime = System.currentTimeMillis();
        String timestamp = simpleDateFormat.format(new Date(datetime));

        // 加密签名
        String sign = Md5Util.StringInMd5(TokenUtil.getToken("ntfssr_token") + JSONObject.toJSONString(info, SerializerFeature.WriteMapNullValue) + ucode + ver + secret + timestamp);
        paramMap.put("ver", ver);
        paramMap.put("ucode", ucode);
        paramMap.put("access_token", TokenUtil.getToken("ntfssr_token"));
        paramMap.put("info", JSONObject.toJSONString(info, SerializerFeature.WriteMapNullValue));
        paramMap.put("timestamp", timestamp);
        paramMap.put("sign", sign);

        List<NameValuePair> parameters = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramMap.keySet())) {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value, SerializerFeature.WriteMapNullValue);
                }
                parameters.add(new BasicNameValuePair(key, (String) value));
            }
        }
        HttpPost httpPost = new HttpPost(baseurl + "/api/createTaxBill");
        httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("unitCode", "LandResourcesBureau");
        LOGGER.info("税费同缴新增缴费书接口请求参数：{}", parameters.toString());
        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
            LOGGER.info("税费同缴新增缴费书接口响应数据：{}", response);
        } catch (IOException e) {
            LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", baseurl + "/api/createTaxBill", paramMap, e);
            throw new AppException("httpPost 请求异常");
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        if (StringUtil.equals(jsonObject.getString("errcode"), "0")) {
            return jsonObject.getString("result");
        } else if (StringUtil.equals(jsonObject.getString("errcode"), "4003")) {
            getToken();
        }

        return null;
    }

    /**
     * 1.3 税费同缴支付结果通知接口
     *
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @OpenApi(apiDescription = "税费同缴支付结果通知接口", apiName = "", openLog = false)
    @Override
    @ApiOperation(value = "税费同缴支付结果通知接口")
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "税费同缴支付结果通知接口", dsfFlag = "CZJ", requester = "CZJ", responser = "BDC")
    @ApiImplicitParams({@ApiImplicitParam(name = "MSG", value = "请求参数体", required = true, dataType = "String", paramType = "query")})
    public Object sftjPayResult(@RequestParam(value = "MSG") String msg) {
        LOGGER.info("税费同缴支付结果通知接口，解密前数据 ：{}", msg);
        Map resultMap = new HashMap();
        if (StringUtils.isNotBlank(msg)) {
            // base64解密
            String str = new String(Base64.decodeBase64(msg), Charset.defaultCharset());
            LOGGER.info("税费同缴支付结果通知接口，解密后数据 ：{}", str);
            // 转化数据
            Map<String, String> data = JSONObject.parseObject(str, Map.class);
            // 重组数据，并加入单位密钥
            Map<String, String> requestParams = new LinkedHashMap<>();
            requestParams.put("billCodeNumb", data.get("billCodeNumb"));
            requestParams.put("code", data.get("code"));
            requestParams.put("dateTime", data.get("dateTime"));
            requestParams.put("fundSerialNumber", data.get("fundSerialNumber"));
            requestParams.put("orderSn", data.get("orderSn"));
            requestParams.put("secret", secret);
            if (StringUtils.isNotBlank(data.get("specialCode"))) {
                requestParams.put("specialCode", data.get("specialCode"));
            }
            requestParams.put("status", data.get("status"));
            requestParams.put("totalAmount", data.get("totalAmount"));
            if (StringUtils.isNotBlank(data.get("tradeUniqueCode"))) {
                requestParams.put("tradeUniqueCode", data.get("tradeUniqueCode"));
            }
            LOGGER.info("税费同缴支付结果通知接口，处理签名用map ：{}", JSON.toJSONString(requestParams));
            // 生成签名
            String sign = Base64.encodeBase64String(DigestUtils.md5(JSONUtils.toJSONString(requestParams).getBytes(Charset.defaultCharset())));
            LOGGER.info("税费同缴支付结果通知接口，生成签名 ：{}", sign);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date dateTime;
            try {
                dateTime = simpleDateFormat.parse(MapUtils.getString(data, "dateTime"));
            } catch (ParseException ex) {
                throw new AppException("日期时间不正确");
            }


            // 验证签名是否正确
            if (StringUtil.equals(sign, data.get("sign"))) {
                // 更新完税状态
                BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                bdcSlHsxxDO.setHyzfjfsbm(MapUtils.getString(data, "billCodeNumb"));
                // 缴款完成
                LOGGER.info("当前核税信息{}支付更新完税状态,时间为{}", bdcSlHsxxDO.getHsxxid(), dateTime);
                Integer status = MapUtils.getInteger(data, "status");
                if (status != null && status == 2) {
                    bdcSlHsxxDO.setWszt(CommonConstantUtils.SF_S_DM);
                } else {
                    bdcSlHsxxDO.setWszt(CommonConstantUtils.SF_F_DM);
                }
                Integer swgxFlag = bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);

                if (swgxFlag != null && swgxFlag > 0) {
                    resultMap.put("errcode", 0);
                    resultMap.put("errmsg", "Success");
                } else {
                    resultMap.put("errcode", 1);
                    resultMap.put("errmsg", "更新数据失败");
                    LOGGER.info("税费同缴更新税费状态失败{}", bdcSlHsxxDO.toString());
                }
            } else {
                resultMap.put("errcode", 2);
                resultMap.put("errmsg", "签名信息错误");
                LOGGER.info("税费同缴更新税费状态失败，签名信息错误{}", data);

            }
        } else {
            resultMap.put("errcode", 3);
            resultMap.put("errmsg", "MSG为空，无法更新");
            LOGGER.info("税费同缴更新税费状态失败，MSG为空，无法更新{}", msg);

        }

        return JSONUtils.toJSONString(resultMap);
    }

}
