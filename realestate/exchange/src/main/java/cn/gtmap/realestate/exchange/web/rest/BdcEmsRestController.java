package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.huaian.sfm.ems.Cargo;
import cn.gtmap.realestate.common.core.dto.exchange.huaian.sfm.ems.OrderNormal;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlYjxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYjxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.BdcEmsRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/11/02  09:39
 * @description 淮安，中国邮政
 */
@OpenController(consumer = "淮安中国邮政相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/ems")
@Api(tags = "淮安中国邮政相关服务")
public class BdcEmsRestController implements BdcEmsRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcEmsRestController.class);

    @Autowired
    private BdcSlYjxxFeginService bdcSlYjxxFeginService;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    // ems下单取号地址
    @Value("${ems.xdqh.url:}")
    private String xdqhUrl;

    // ems电商标识
    @Value("${ems.dsbs:}")
    private String dsbs;

    // ems客户代码
    @Value("${ems.senderNo:}")
    private String senderNo;

    // ems签名私钥
    @Value("${ems.secrectkey:}")
    private String secrectKey;


    @Override
    @OpenApi(apiDescription = "下单取号接口")
    @PostMapping("/xdqh")
    public BdcCommonResponse emsXdqh(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName) {
        BdcCommonResponse bdcCommonResponse = new BdcCommonResponse();
        bdcCommonResponse.setCode(BdcCommonResponse.ERROR_CODE);
        bdcCommonResponse.setMessage("下单取号失败");
        if (StringUtils.isBlank(processInsId)) {
            return BdcCommonResponse.fail("中国邮政下单取号请求缺少参数工作流实例id");
        }
        Boolean tsEms = false;
        // 领证方式是ems
        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
        bdcXmFbQO.setGzlslid(processInsId);
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
            for (BdcXmFbDO xmFbDO : bdcXmFbDOList) {
                if (CommonConstantUtils.LZFS_EMS.equals(xmFbDO.getLzfs())) {
                    tsEms = true;
                    break;
                }
            }
        }
        if (tsEms) {
            BdcSlYjxxQO bdcSlYjxxQO = new BdcSlYjxxQO();
            bdcSlYjxxQO.setGzlslid(processInsId);
            List<BdcSlYjxxDO> bdcSlYjxxDOS = bdcSlYjxxFeginService.listBdcSlYjxx(bdcSlYjxxQO);
            if (CollectionUtils.isEmpty(bdcSlYjxxDOS)) {
                return BdcCommonResponse.fail("中国邮政下单取号请求,没有邮寄信息，工作流实例id：" + processInsId);
            }
            for (BdcSlYjxxDO slYjxxDO : bdcSlYjxxDOS) {
                OrderNormal orderNormal = new OrderNormal();
                orderNormal.setCreated_time(DateUtils.getCurrentTimeStr());
                orderNormal.setEcommerce_user_id(dsbs);
                orderNormal.setSender_no(senderNo);
                orderNormal.setContents_attribute("1");
                orderNormal.setBase_product_no("3");
                orderNormal.setPickup_notes(slYjxxDO.getSlbh());
                orderNormal.setPayment_mode("2");
                // 寄件人取缮证人地址
                if (StringUtils.isBlank(slYjxxDO.getJjrmc())) {
                    if (StringUtils.isBlank(currentUserName)) {
                        currentUserName = userManagerUtils.getCurrentUserName();
                    }
                    UserDto userDto = null;
                    if (StringUtils.isNotBlank(currentUserName)) {
                        userDto = userManagerUtils.getUserByName(currentUserName);
                        slYjxxDO.setJjrmc(userDto.getAlias());
                        slYjxxDO.setJjrlxdh(userDto.getMobile());
                        slYjxxDO.setJjrszp("江苏省");
                        slYjxxDO.setJjrszc("淮安市");
                        String bmmc = userDto.getOrgRecordList().get(0).getName();
                        String regionName = userDto.getOrgRecordList().get(0).getRegionName();
                        slYjxxDO.setJjrszx(regionName);
                        slYjxxDO.setJjrxxdz(bmmc);
                    }
                }
                dozerBeanMapper.map(slYjxxDO, orderNormal, "ems_yjxx");
                List<Cargo> cargos = new ArrayList<>();
                Cargo cargo = new Cargo();
                cargo.setCargo_name("不动产登记权属证书");
                cargos.add(cargo);
                orderNormal.setCargos(cargos);
                JSONObject orderJson = new JSONObject();
                orderJson.put("OrderNormal", orderNormal);
                LOGGER.info("中国邮政下单取号请求,orderNormal:{}", orderJson.toString());
                String serialNo = UUID.randomUUID().toString();
                String body = JSONObject.toJSONString(orderJson, SerializerFeature.WriteNullStringAsEmpty);
                String mysign = "";
                try {
                     mysign = Base64.encodeBase64String(getSHA256(body + secrectKey).getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, Object> param = new HashMap<>();
                param.put("apiCode","oms_ordercreate_waybillno");
                param.put("senderNo",senderNo);
                param.put("serialNo",serialNo);
                param.put("signature",mysign);
                param.put("msgType","0");
                param.put("logistics_interface",body);
                String response = httpClientUtils.sendFormRequest(xdqhUrl, param, null);
                LOGGER.info("中国邮政下单取号返回值：{}", response);
                JSONObject responseJson = JSONObject.parseObject(response);
                if ("S00".equals(responseJson.getString("code")) && Objects.nonNull(responseJson.get("body"))) {
                    JSONObject bodyJson = responseJson.getJSONObject("body");
                    // 保存单号到邮寄信息 waybill_no
                    String wlydh = bodyJson.getString("waybill_no");
                    if (StringUtils.isNotBlank(wlydh)) {
                        slYjxxDO.setWlydh(wlydh);
                        bdcSlYjxxFeginService.updateByJsonEntity(JSONObject.toJSONString(slYjxxDO));
                    }
                    return BdcCommonResponse.ok("下单取号成功");
                }
            }
        } else {
            bdcCommonResponse.setCode(BdcCommonResponse.SUCCESS_CODE);
            bdcCommonResponse.setMessage("领证方式不是ems，无需推送");
        }
        return bdcCommonResponse;
    }

    /**
     * @description: ems签名加密
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/12/27 14:56
     * @param str
     * @return: java.lang.String
     **/
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * @description: ems签名加密
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/12/27 14:57
     * @param bytes
     * @return: java.lang.String
     **/
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();

    }

}
