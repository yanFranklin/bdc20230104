package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.etl.util.EncryptDecryptReSM2;
import cn.gtmap.realestate.etl.core.dto.ggxypt.ColumnDTO;
import cn.gtmap.realestate.etl.core.dto.ggxypt.ExchangeDsfCommonMsgResponse;
import cn.gtmap.realestate.etl.core.dto.ggxypt.GgxyptDataResponse;
import cn.gtmap.realestate.etl.core.dto.ggxypt.GrdacxRequestBody;
import cn.gtmap.realestate.etl.core.dto.ggxypt.qy.QyCqDTO;
import cn.gtmap.realestate.etl.core.dto.ggxypt.qy.QyDyaDTO;
import cn.gtmap.realestate.etl.core.dto.ggxypt.zrr.ZrrCqDTO;
import cn.gtmap.realestate.etl.core.dto.ggxypt.zrr.ZrrDyaDTO;
import cn.gtmap.realestate.etl.core.mapper.GgxypyMapper;
import cn.gtmap.realestate.etl.core.qo.ggxypt.GgxyptCommonQO;
import cn.gtmap.realestate.etl.core.qo.ggxypt.qy.GgxyptQyBdccqQO;
import cn.gtmap.realestate.etl.core.qo.ggxypt.qy.GgxyptQyDyQO;
import cn.gtmap.realestate.etl.core.qo.ggxypt.zrr.ZrrBdcCqQO;
import cn.gtmap.realestate.etl.core.qo.ggxypt.zrr.ZrrBdcDyaQO;
import com.alibaba.fastjson.JSONObject;
import com.xmsme.national.secrets.SM2Utils;
import com.xmsme.national.secrets.SM4Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/29
 * @description 江苏公共信用信息平台
 * 融资平台接口
 */
@RestController
@RequestMapping("/realestate-etl/rest/v1.0/ggxypt")
@Api(tags = "江苏公共信用信息平台")
public class BdcGgrzptRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGgrzptRestController.class);
    private static final String USER_ID = "xmsme";

    private EncryptDecryptReSM2 encryptDecryptReSM2;

    @Autowired
    GgxypyMapper ggxypyMapper;

    /**
     * 对方sm2私钥
     */
    @Value("${ggxypt.lydw:盐城不动产登记中心}")
    public static String ggxyptLydw;


    /**
     * 对方sm2私钥
     */
    @Value("${ggxypt.remotePrivateKey:}")
    public String remotePrivateKey;

    /**
     * 对方sm2公钥
     */
    @Value("${ggxypt.remotePublicKey:}")
    public String remotePublicKey;

    /**
     * 本地sm2私钥
     */
    @Value("${ggxypt.localPrivateKey:}")
    public String localPrivateKey;

    /**
     * 本地sm2公钥
     */
    @Value("${ggxypt.localPublicKey:}")
    public String localPublicKey;


    /**
     * 3.	区市企业名下不动产登记情况接口
     *
     * @param
     * @return
     * @Date 2022/8/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/V1/query/frBdcdj")
    public ExchangeDsfCommonMsgResponse<String> frBdcdj(@RequestBody GgxyptCommonQO ggxyptCommonQO) {
        if (Objects.isNull(ggxyptCommonQO)) {
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
        }

        GgxyptDataResponse<List<QyCqDTO>> response = new GgxyptDataResponse();
        try {

            String decryptSm4Key = SM2Utils.decrypt(localPrivateKey, ggxyptCommonQO.getKey());
            LOGGER.info("区市企业名下不动产登记情况接口请求key解密结果:{}", decryptSm4Key);
            SM4Utils decryptSm4 = new SM4Utils(decryptSm4Key);
            String decryptData = decryptSm4.decryptData_CBC(ggxyptCommonQO.getRequestData());
            LOGGER.info("区市企业名下不动产登记情况接口请求data解密结果:{}", decryptData);
            boolean verified = EncryptDecryptReSM2.verifiedSign(decryptData, ggxyptCommonQO.getSignatureData(), ggxyptCommonQO.getPublicKey());
            LOGGER.info("请求验签结果:{}", verified);
            boolean verified1 = true;
            if (verified) {
               /* String decryptData1 = "{\n" +
                        "    \"QYMC\":\"桐城市华猫塑料有限公司\",\n" +
                        "    \"QLRZJH\":\"3408810000****9\",\n" +
                        "    \"BDCQZSH\":\"\",\n" +
                        "    \"AREACODE\":\"\",\n" +
                        "    \"Bearer\":\"\"\n" +
                        "}";*/
                LOGGER.info("正常请求:{}", decryptData);
                GgxyptQyBdccqQO qyBdccqQO = JSONObject.parseObject(decryptData, GgxyptQyBdccqQO.class);
                if (CheckParameter.checkAllParameter(qyBdccqQO)) {
                    //查询产权
                    List<QyCqDTO> qyCqDTOList = ggxypyMapper.queryQycqList(qyBdccqQO);
                    if (CollectionUtils.isNotEmpty(qyCqDTOList)) {
                       /* for (QyCqDTO qyCqDTO : qyCqDTOList) {
                            qyCqDTO.setLydw(ggxyptLydw);
                            String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(qyCqDTO.getBdcdyh(), "");
                            if (CommonConstantUtils.BDCLX_TD_DM.equals(bdclx)) {
                                //查建设用地使用权的土地使用截止日期
                                GrdacxRequestBody grdacxRequestBody = new GrdacxRequestBody();
                                grdacxRequestBody.setBdcdyh(qyCqDTO.getBdcdyh());
                                List<BdcJsydsyqDO> jsydsyqDOList = ggxypyMapper.queryBdcJsydsyq(grdacxRequestBody);
                                if (CollectionUtils.isNotEmpty(jsydsyqDOList)) {
                                    qyCqDTO.setSyqx(jsydsyqDOList.get(0).getSyqjssj());
                                }
                            }
                        }*/
                    }
                    LOGGER.info("区市企业名下不动产登记接口data:{}", qyCqDTOList.toString());
                    QyCqDTO qyCqDTO = new QyCqDTO();
                    List<ColumnDTO> columnDTOList = columnList(qyCqDTO);
                    LOGGER.info(columnDTOList.toString());
                    response.setData(qyCqDTOList);
                    response.setColumn(columnDTOList);
                    //对返回进行加密
                    Map<String, String> encryptResponse = EncryptDecryptReSM2.generateEncryptToDsf(JSONObject.toJSONString(response), ggxyptCommonQO.getPublicKey(), localPublicKey, localPrivateKey);

//                    String JmResponse = EncryptDecryptRemote.generateEncryptToDsf(JSONObject.toJSONString(response),remotePublicKey,localPublicKey).get("requestData");

                    return ExchangeDsfCommonMsgResponse.ok(JSONObject.toJSONString(encryptResponse));
                } else {
                    return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
                }
            } else {
                LOGGER.info("验签错误");
                return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "验签错误");

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, e.getMessage());

        }

    }

    /**
     * 4.	区市企业名下不动产登记抵押情况接口
     *
     * @param
     * @return
     * @Date 2022/8/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/V1/query/frfcDyxx")
    public ExchangeDsfCommonMsgResponse<String> frfcDyxx(@RequestBody GgxyptCommonQO ggxyptCommonQO) {
        if (Objects.isNull(ggxyptCommonQO)) {
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
        }

        GgxyptDataResponse<List<QyDyaDTO>> response = new GgxyptDataResponse();
        try {
//            encryptDecryptRemote.test_response_decrypt();
            //服务端加密返回解密示例
//            Map<String, Object> encryptResponse = generateEncryptResponse("test response");
            String decryptSm4Key = SM2Utils.decrypt(localPrivateKey, ggxyptCommonQO.getKey());
            LOGGER.info("区市企业名下不动产抵押登记情况接口请求key解密结果:{}", decryptSm4Key);
            SM4Utils decryptSm4 = new SM4Utils(decryptSm4Key);
            String decryptData = decryptSm4.decryptData_CBC(ggxyptCommonQO.getRequestData());
            LOGGER.info("区市企业名下不动产抵押登记情况接口请求data解密结果:{}", decryptData);
            boolean verified = EncryptDecryptReSM2.verifiedSign(decryptData, ggxyptCommonQO.getSignatureData(), ggxyptCommonQO.getPublicKey());
            LOGGER.info("请求验签结果:{}", verified);
            if (verified) {
                LOGGER.info("正常请求:{}", decryptData);
                GgxyptQyDyQO qyDyQO = JSONObject.parseObject(decryptData, GgxyptQyDyQO.class);
                if (CheckParameter.checkAllParameter(qyDyQO)) {
                    //查询产权
                    List<QyDyaDTO> qyDyaList = ggxypyMapper.queryQyDyaList(qyDyQO);
                    if (CollectionUtils.isNotEmpty(qyDyaList)) {
                        for (QyDyaDTO qyDyaDTO : qyDyaList) {
                            qyDyaDTO.setLydw(ggxyptLydw);
                        }
                    }
                    LOGGER.info("区市企业名下不动产抵押登记接口data:{}", qyDyaList.toString());
                    QyDyaDTO qyDyaDTO = new QyDyaDTO();
                    List<ColumnDTO> columnDTOList = columnList(qyDyaDTO);
                    LOGGER.info(columnDTOList.toString());
                    response.setData(qyDyaList);
                    response.setColumn(columnDTOList);
                    //对返回进行加密
                    Map<String, String> encryptResponse = EncryptDecryptReSM2.generateEncryptToDsf(JSONObject.toJSONString(response), ggxyptCommonQO.getPublicKey(), localPublicKey, localPrivateKey);

                    return ExchangeDsfCommonMsgResponse.ok(JSONObject.toJSONString(encryptResponse));
                } else {
                    return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
                }
            } else {
                LOGGER.info("验签错误");
                return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "验签错误");

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, e.getMessage());

        }

    }


    /**
     * 5.	设区市自然人不动产信息接口
     *
     * @param
     * @return
     * @Date 2022/8/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/V1/query/zrrBdcxx")
    public ExchangeDsfCommonMsgResponse<String> zrrBdcxx(@RequestBody GgxyptCommonQO ggxyptCommonQO) {
        if (Objects.isNull(ggxyptCommonQO)) {
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
        }

        GgxyptDataResponse<List<ZrrCqDTO>> response = new GgxyptDataResponse();
        try {
//            encryptDecryptRemote.test_response_decrypt();
            //服务端加密返回解密示例
//            Map<String, Object> encryptResponse = generateEncryptResponse("test response");
            String decryptSm4Key = SM2Utils.decrypt(localPrivateKey, ggxyptCommonQO.getKey());
            LOGGER.info("区市个人名下不动产抵押登记情况接口请求key解密结果:{}", decryptSm4Key);
            SM4Utils decryptSm4 = new SM4Utils(decryptSm4Key);
            String decryptData = decryptSm4.decryptData_CBC(ggxyptCommonQO.getRequestData());
            LOGGER.info("区市个人名下不动产抵押登记情况接口请求data解密结果:{}", decryptData);
            boolean verified = EncryptDecryptReSM2.verifiedSign(decryptData, ggxyptCommonQO.getSignatureData(), ggxyptCommonQO.getPublicKey());
//            boolean verified = true;
            LOGGER.info("请求验签结果:{}", verified);
            if (verified) {
                LOGGER.info("正常请求:{}", decryptData);
                ZrrBdcCqQO zrrBdcCqQO = JSONObject.parseObject(decryptData, ZrrBdcCqQO.class);
                if (CheckParameter.checkAllParameter(zrrBdcCqQO)) {
                    //查询产权
                    List<ZrrCqDTO> zrrCqDTOList = ggxypyMapper.queryZrrCqList(zrrBdcCqQO);
                    if (CollectionUtils.isNotEmpty(zrrCqDTOList)) {
                        for (ZrrCqDTO zrrCqDTO : zrrCqDTOList) {
                            zrrCqDTO.setLydw(ggxyptLydw);
                            String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(zrrCqDTO.getBdcdyh(), "");
                            if (CommonConstantUtils.BDCLX_TD_DM.equals(bdclx)) {
                                //查建设用地使用权的土地使用截止日期
                                GrdacxRequestBody grdacxRequestBody = new GrdacxRequestBody();
                                grdacxRequestBody.setBdcdyh(zrrCqDTO.getBdcdyh());
                                List<BdcJsydsyqDO> jsydsyqDOList = ggxypyMapper.queryBdcJsydsyq(grdacxRequestBody);
                                if (CollectionUtils.isNotEmpty(jsydsyqDOList)) {
                                    zrrCqDTO.setSyqx(jsydsyqDOList.get(0).getSyqjssj());
                                }
                            }
                        }
                    }
                    LOGGER.info("区市个人名下不动产抵押登记接口data:{}", zrrCqDTOList.toString());

                    ZrrCqDTO zrrCqDTO = new ZrrCqDTO();
                    List<ColumnDTO> columnDTOList = columnList(zrrCqDTO);
                    LOGGER.info(columnDTOList.toString());
                    response.setData(zrrCqDTOList);
                    response.setColumn(columnDTOList);
                    //对返回进行加密

                    Map<String, String> encryptResponse = EncryptDecryptReSM2.generateEncryptToDsf(JSONObject.toJSONString(response), ggxyptCommonQO.getPublicKey(), localPublicKey, localPrivateKey);

                    return ExchangeDsfCommonMsgResponse.ok(JSONObject.toJSONString(encryptResponse));

                } else {
                    return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
                }
            } else {
                LOGGER.info("验签错误");
                return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "验签错误");

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, e.getMessage());

        }

    }

    /**
     * 6.	区市自然人不动产抵押信息接口
     *
     * @param
     * @return
     * @Date 2022/8/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/V1/query/zrrBdcDyxx")
    public ExchangeDsfCommonMsgResponse<String> zrrBdcDyxx(@RequestBody GgxyptCommonQO ggxyptCommonQO) {
        if (Objects.isNull(ggxyptCommonQO)) {
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
        }

        GgxyptDataResponse<List<ZrrDyaDTO>> response = new GgxyptDataResponse();
        try {
//            encryptDecryptRemote.test_response_decrypt();
            //服务端加密返回解密示例
//            Map<String, Object> encryptResponse = generateEncryptResponse("test response");
            String decryptSm4Key = SM2Utils.decrypt(localPrivateKey, ggxyptCommonQO.getKey());
            LOGGER.info("区市企业名下不动产抵押登记情况接口请求key解密结果:{}", decryptSm4Key);
            SM4Utils decryptSm4 = new SM4Utils(decryptSm4Key);
            String decryptData = decryptSm4.decryptData_CBC(ggxyptCommonQO.getRequestData());
            LOGGER.info("区市企业名下不动产抵押登记情况接口请求data解密结果:{}", decryptData);
            boolean verified = EncryptDecryptReSM2.verifiedSign(decryptData, ggxyptCommonQO.getSignatureData(), ggxyptCommonQO.getPublicKey());
            LOGGER.info("请求验签结果:{}", verified);
            if (verified) {
                LOGGER.info("正常请求:{}", decryptData);
                ZrrBdcDyaQO zrrBdcDyaQO = JSONObject.parseObject(decryptData, ZrrBdcDyaQO.class);
                if (CheckParameter.checkAllParameter(zrrBdcDyaQO)) {
                    //查询产权
                    List<ZrrDyaDTO> zrrDyaDTOList = ggxypyMapper.queryZrrDyaList(zrrBdcDyaQO);
                    if (CollectionUtils.isNotEmpty(zrrDyaDTOList)) {
                        for (ZrrDyaDTO zrrDyaDTO : zrrDyaDTOList) {
                            zrrDyaDTO.setLydw(ggxyptLydw);
                        }
                    }
                    LOGGER.info("区市个人名下不动产抵押登记接口data:{}", zrrDyaDTOList.toString());

                    QyDyaDTO qyDyaDTO = new QyDyaDTO();
                    List<ColumnDTO> columnDTOList = columnList(qyDyaDTO);
                    LOGGER.info(columnDTOList.toString());
                    response.setData(zrrDyaDTOList);
                    response.setColumn(columnDTOList);
                    //对返回进行加密

                    Map<String, String> encryptResponse = EncryptDecryptReSM2.generateEncryptToDsf(JSONObject.toJSONString(response), ggxyptCommonQO.getPublicKey(), localPublicKey, localPrivateKey);

                    return ExchangeDsfCommonMsgResponse.ok(JSONObject.toJSONString(encryptResponse));
                } else {
                    return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
                }
            } else {
                LOGGER.info("验签错误");
                return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "验签错误");

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, e.getMessage());

        }

    }


    /**
     * 组织对象字段名和类型，注释
     *
     * @param obj
     * @return
     * @Date 2022/8/31
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public static List<ColumnDTO> columnList(Object obj) {

        List<ColumnDTO> columnDTOList = new ArrayList<>();
        List<Field> fieldList = AnnotationsUtils.getAnnotationField(obj, ApiModelProperty.class);
        for (Field field : fieldList) {
            ColumnDTO columnDTO = new ColumnDTO();
            ApiModelProperty entityFieldId = field.getAnnotation(ApiModelProperty.class);
            columnDTO.setColumnname(field.getName());
            columnDTO.setDataitemname(entityFieldId.value());
            if (field.getType() == Double.class) {
                columnDTO.setDatatype("数值型");
            } else if (field.getType() == String.class) {
                columnDTO.setDatatype("字符型");

            } else if (field.getType() == Integer.class) {
                columnDTO.setDatatype("数值型");

            } else if (field.getType() == Date.class) {
                columnDTO.setDatatype("日期型");
            }
            LOGGER.info(field.getName());
            LOGGER.info(field.getType().toString());
            LOGGER.info(entityFieldId.value());
            columnDTOList.add(columnDTO);
        }
        return columnDTOList;
    }


    @Test
    public void test_response_decrypt1() throws Exception {
        String h = "{\n" +
                "    \"publicKey\": \"040469D4B903EF4985E932A9ABE5D33A35FA38CDC4DFE83B94B2D1B4BF6F0B1115CBAF4CA9BE42E6444DE8F4AC637B3BFBFE695D6E9634DFD5D5E900916C4AAD6C\",\n" +
                "    \"key\": \"0481833F9ADB55D363637FC2A086D83F7561790032F53EDCD23AB13CC514E23DFD03387776CDBC50AA183CECC352365410874D2FA4552E74795267726BBDE4EA6E7BE5C8AB67A1F85C4485FC9B392181B6731934B3DCBE4E3125273046ABA3953AFF98EDE2FE66F50B53DAC1724E8153CF\",\n" +
                "    \"signatureData\": \"30460221009E8FE9176F11BD1C17378670AB0EC3FE3CCFFED5B2D2C298E2E4D88F2076410E022100F5CAF9157763C350B3D2B5EEAB0AE1B4A4818EB6155FDD45F2ECB096F2BD776D\",\n" +
                "    \"requestData\": \"KZzNz0tmAQYTK7fV69rmUY0g2sVTiFEJyPNttEjWsCUnN2/YcNEowQupla86VwNYNBLSveVOMWyUUJ4Lz+lJrgRebNftP//O00+ABBmdU8ZzDZxYxrsZH6Qi26js/bP8PlUW8G9OZD1x0rjbWWkN6w==\"\n" +
                "}";
        Map<String, Object> encryptResponse = JSONObject.parseObject(h);
        String decryptSm4Key = SM2Utils.decrypt("052D4FD7552D238EE8E379579B874235757E05877673F182C46F6A5FE472CFA2", encryptResponse.get("key").toString());
        LOGGER.info("请求key解密结果:{}", decryptSm4Key);

        SM4Utils decryptSm4 = new SM4Utils(decryptSm4Key);
        String decryptData = decryptSm4.decryptData_CBC(encryptResponse.get("requestData").toString());
        LOGGER.info("请求data解密结果:{}", decryptData);

        boolean verified = EncryptDecryptReSM2.verifiedSign(decryptData, encryptResponse.get("signatureData").toString(), remotePublicKey);
        LOGGER.info("请求验签结果:{}", verified);
        if (verified) {
            LOGGER.info("正常请求:{}", decryptData);
        } else {
            LOGGER.info("验签错误");
        }
    }


}
