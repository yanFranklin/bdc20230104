package cn.gtmap.realestate.exchange.util;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionapply.SupremeCourtDecisionApplyParamDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhongjinpeng@gtmao.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/4 11:32
 * @description
 */
@Component
public class SjptApiUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SjptApiUtils.class);

    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    private static final String PATTERN1 = "yyyyMMddHHmmss";

    private static final String BHSERVICE_BJSFPJXXSQ_YWLX = "bjsfpjxxsq";

    // 自增时间范围
    private static final String BHSERVICE_ZZSJFW = "DAY";

    /**
     * 区县代码
     */
    @Value("${sjpt.xzqdm:}")
    private String xzqdm;

    /**
     * 用户名
     */
    @Value("${sjpt.username:}")
    private String username;

    /**
     * 密码
     */
    @Value("${sjpt.password:}")
    private String password;

    /**
     * 5获取用户tokenurl
     */
    @Value("${sjpt.token.url:}")
    private String tokenUrl;

    /**
     * 超时时长设置
     */
    @Value("${sjpt.soTimeout:}")
    private String soTimeout;

    /**
     * 获取token
     *
     * @return
     */
    public String getToken() {
        Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> headParam = new HashMap<>();
        Map<String, Object> dataParam = new HashMap<>();
        headParam.put("xzqdm", xzqdm);
        dataParam.put("username", username);
        dataParam.put("password", password);

        paramMap.put("head", headParam);
        paramMap.put("data", dataParam);

        String response = this.sendPostRequest(tokenUrl, paramMap);

        JSONObject responseObject = JSONObject.parseObject(response);
        JSONObject headObject = responseObject.getJSONObject("head");
        if (StringUtil.equals(headObject.getString("code"), "0000")) {
            JSONObject dataObject = responseObject.getJSONObject("data");
            return dataObject.getString("token");
        } else {
            throw new AppException("获取token失败!");
        }
    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public String sendPostRequest(String url, Map<String,Object> param){
        LOGGER.info("---sendPostRequest参数:{}",JSONObject.toJSONString(param));
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("gxData", JSON.toJSONString(param)));
        LOGGER.info("---http请求参数:{},请求地址:{}", JSONObject.toJSONString(parameters), url);

        // 发送请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
        httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        httpPost.setHeader("unitCode", "LandResourcesBureau");
        httpPost.setHeader("contentType", "application/x-www-form-urlencoded");

        if (StringUtils.isNotBlank(soTimeout)) {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(NumberUtils.toInt(soTimeout)).build();
            LOGGER.error("省级平台 请求参数配置:{}", requestConfig.toString());
            httpPost.setConfig(requestConfig);
        }
        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("---请求异常:{},请求url:{},请求param:{}", url, param, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("---请求成功,响应结果:{}", response);
        return response;
    }

    /**
     * 生成请求头
     *
     * @param ip
     * @return TODO 查询请求单号 6位流水号生成
     */
    public Map<String, Object> handleRequestHead(String ip) {
        Map<String, Object> requestHeadParam = new HashMap<>(16);
        // 行政区代码
        requestHeadParam.put("xzqdm", xzqdm);
        requestHeadParam.put("token", getToken());
        // 登记部门名称 例：XX不动产登记机构
        requestHeadParam.put("deptName", xzqdm);
        // 登记人员名称
        requestHeadParam.put("userName", xzqdm);
        // 查询请求单号 例：20190823110102000001；八位日期+六位行政区代码+六位流水号；共20位数
        requestHeadParam.put("cxqqdh", DateUtil.formatDate("yyyyMMdd") + xzqdm + getBjsfpjxxsqLsh());
        // 业务号 业务受理编号
        requestHeadParam.put("businessNumber", xzqdm);
        // 查询客户端ip
        requestHeadParam.put("ip", ip);
        return requestHeadParam;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @return java.lang.String
     * @description 部级司法判决信息申请流水号获取
     */
    public String getBjsfpjxxsqLsh() {
        String xlhStr = "";
        Integer xlh = bdcBhFeignService.queryLsh(BHSERVICE_BJSFPJXXSQ_YWLX, BHSERVICE_ZZSJFW);
        if (xlh != null) {
            // 0补齐六位流水号
            xlhStr = String.format("%06d", xlh);
        }
        return xlhStr;
    }

    /**
     * @return java.lang.String
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @description 部级司法判决信息申请流水号获取-15位
     */
    public String getBjsfpjxxsqLsh15() {
        String xlhStr = "";
        Integer xlh = bdcBhFeignService.queryLsh(BHSERVICE_BJSFPJXXSQ_YWLX, BHSERVICE_ZZSJFW);
        if (xlh != null) {
            // 0补齐15位流水号
            xlhStr = String.format("%07d", xlh);
        }
        return xlhStr;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理司法裁决查询申请公共请求参数
     */
    public SupremeCourtDecisionApplyParamDTO handleSupremeCourtDecisionApplyParamDTO(SupremeCourtDecisionApplyParamDTO paramDTO) {
        paramDTO.setCxbm(xzqdm);
        paramDTO.setCxbmbh(xzqdm);
        UserDto userDto =userManagerUtils.getCurrentUser();
        if(userDto != null) {
            paramDTO.setCxr(userDto.getAlias());
        }else{
            paramDTO.setCxr(xzqdm);
        }
        paramDTO.setCxrbh(xzqdm);
        paramDTO.setBusinum(DateUtil.formatDate("yyyyMMdd") + getBjsfpjxxsqLsh15());
        return  paramDTO;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取流水号
     */
    public static String getMessageLsh(Object object){
        return DateUtil.formatDate(PATTERN1) + "0001";
    }




}
