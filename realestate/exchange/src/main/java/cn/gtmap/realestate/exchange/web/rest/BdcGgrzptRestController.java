package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonMsgResponse;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.ColumnDTO;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.GgxyptDataResponse;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.qy.QyCqDTO;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.qy.QyDyaDTO;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.zrr.ZrrCqDTO;
import cn.gtmap.realestate.exchange.core.dto.ggxypt.zrr.ZrrDyaDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.qy.GgxyptQyBdccqQO;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.qy.GgxyptQyDyQO;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.zrr.ZrrBdcCqQO;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.zrr.ZrrBdcDyaQO;
import cn.gtmap.realestate.exchange.util.SM.EncryptDecryptRemote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/29
 * @description 江苏公共信用信息平台
 * 融资平台接口
 */
@OpenController(consumer = "江苏公共信用信息平台服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/ggxypt")
@Api(tags = "江苏公共信用信息平台")
public class BdcGgrzptRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGgrzptRestController.class);
    private static final String USER_ID = "xmsme";

    private EncryptDecryptRemote encryptDecryptRemote;


    @Autowired
    BdcdjMapper bdcdjMapper;

    @Autowired
    BdcXmMapper bdcXmMapper;

    /**
     * 对方sm2私钥
     */
    @Value("${ggxypt.lydw:盐城不动产登记中心}")
    public static String ggxyptLydw;
    /**
     * 对方sm2私钥
     */
    @Value("${ggxypt.remotePrivateKey:00AA2D52D13806429FF80794ACF9A9B10FE664A9096E38FA843ACFA821759B011F}")
    private String remotePrivateKey;

    /**
     * 对方sm2公钥
     */
    @Value("${ggxypt.remotePublicKey:04A01FD39F4D09231001D52AB8CE5AD8A697859A6FE6F81D4F4544A0474F38F53B863D4FC79D98878DD1D1027775F24D6B0553CF0559ABC7CF88F3D7CCE6F98897}")
    private String remotePublicKey;

    /**
     * 本地sm2私钥
     */
    @Value("${ggxypt.localPrivateKey:00F42F54CED342B2314B395AE86A15442FD89D8AD3209CDA8CC15F8FCA71A9551F}")
    private String localPrivateKey;

    /**
     * 本地sm2公钥
     */
    @Value("${ggxypt.localPublicKey:048001F249B2643E8145A031DEDA8B888A2B0355D760E4041C99DF2AFF075900519D6F9A569977EA409BC61882DDAA1C93C47E005690C73DCBD832659740FF267A}")
    private String localPublicKey;

    /**
     * 3.	区市企业名下不动产登记情况接口
     *
     * @param
     * @return
     * @Date 2022/8/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/query/frBdcdj")
    @DsfLog(logEventName = "区市企业名下不动产登记情况接口", dsfFlag = "XXB", requester = "XXB", responser = "BDC")
    public ExchangeDsfCommonMsgResponse frBdcdj(@RequestBody GgxyptQyBdccqQO ggxyptQyBdccqQO) {
        GgxyptDataResponse<List<QyCqDTO>> response = new GgxyptDataResponse();

               /* String decryptData1 = "{\n" +
                        "    \"QYMC\":\"桐城市华猫塑料有限公司\",\n" +
                        "    \"QLRZJH\":\"3408810000****9\",\n" +
                        "    \"BDCQZSH\":\"\",\n" +
                        "    \"AREACODE\":\"\",\n" +
                        "    \"Bearer\":\"\"\n" +
                        "}";*/
        LOGGER.info("正常请求:{}", ggxyptQyBdccqQO.toString());
        if (CheckParameter.checkAllParameter(ggxyptQyBdccqQO)) {
            //查询产权
            List<QyCqDTO> qyCqDTOList = bdcXmMapper.queryQycqList(ggxyptQyBdccqQO);
            if (CollectionUtils.isNotEmpty(qyCqDTOList)) {
                for (QyCqDTO qyCqDTO : qyCqDTOList) {
                    qyCqDTO.setLydw(ggxyptLydw);
                    String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(qyCqDTO.getBdcdyh(), "");
                    if (CommonConstantUtils.BDCLX_TD_DM.equals(bdclx)) {
                        //查建设用地使用权的土地使用截止日期
                        GrdacxRequestBody grdacxRequestBody = new GrdacxRequestBody();
                        grdacxRequestBody.setBdcdyh(qyCqDTO.getBdcdyh());
                        List<BdcJsydsyqDO> jsydsyqDOList = bdcdjMapper.queryBdcJsydsyq(grdacxRequestBody);
                        if (CollectionUtils.isNotEmpty(jsydsyqDOList)) {
                            qyCqDTO.setSyqx(jsydsyqDOList.get(0).getSyqjssj());
                        }
                    }
                }
            }
            LOGGER.info("区市企业名下不动产登记接口data:{}", qyCqDTOList.toString());
            QyCqDTO qyCqDTO = new QyCqDTO();
            List<ColumnDTO> columnDTOList = columnList(qyCqDTO);
            LOGGER.info(columnDTOList.toString());
            response.setData(qyCqDTOList);
            response.setColumn(columnDTOList);

//                    String JmResponse = EncryptDecryptRemote.generateEncryptToDsf(JSONObject.toJSONString(response),remotePublicKey,localPublicKey).get("requestData");

            return ExchangeDsfCommonMsgResponse.ok(response);
        } else {
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
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
    @PostMapping("/query/frfcDyxx")
    @DsfLog(logEventName = "区市企业名下不动产登记抵押情况接口", dsfFlag = "XXB", requester = "XXB", responser = "BDC")
    public ExchangeDsfCommonMsgResponse frfcDyxx(@RequestBody GgxyptQyDyQO ggxyptQyDyQO) {


        GgxyptDataResponse<List<QyDyaDTO>> response = new GgxyptDataResponse();

//
        LOGGER.info("正常请求:{}", ggxyptQyDyQO.toString());
        if (CheckParameter.checkAllParameter(ggxyptQyDyQO)) {
            //查询产权
            List<QyDyaDTO> qyDyaList = bdcXmMapper.queryQyDyaList(ggxyptQyDyQO);
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

            return ExchangeDsfCommonMsgResponse.ok(response);
        } else {
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
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
    @PostMapping("/query/zrrBdcxx")
    @DsfLog(logEventName = "设区市自然人不动产信息接口", dsfFlag = "XXB", requester = "XXB", responser = "BDC")
    public ExchangeDsfCommonMsgResponse zrrBdcxx(@RequestBody ZrrBdcCqQO zrrBdcCqQO) {


        GgxyptDataResponse<List<ZrrCqDTO>> response = new GgxyptDataResponse();

        if (CheckParameter.checkAllParameter(zrrBdcCqQO)) {
            //查询产权
            List<ZrrCqDTO> zrrCqDTOList = bdcXmMapper.queryZrrCqList(zrrBdcCqQO);
            if (CollectionUtils.isNotEmpty(zrrCqDTOList)) {
                for (ZrrCqDTO zrrCqDTO : zrrCqDTOList) {
                    zrrCqDTO.setLydw(ggxyptLydw);
                }
            }
            LOGGER.info("区市个人名下不动产抵押登记接口data:{}", zrrCqDTOList.toString());

            ZrrCqDTO zrrCqDTO = new ZrrCqDTO();
            List<ColumnDTO> columnDTOList = columnList(zrrCqDTO);
            LOGGER.info(columnDTOList.toString());
            response.setData(zrrCqDTOList);
            response.setColumn(columnDTOList);

            return ExchangeDsfCommonMsgResponse.ok(response);
        } else {
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
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
    @PostMapping("/query/zrrBdcDyxx")
    @DsfLog(logEventName = "区市自然人不动产抵押信息接口", dsfFlag = "XXB", requester = "XXB", responser = "BDC")
    public ExchangeDsfCommonMsgResponse zrrBdcDyxx(@RequestBody ZrrBdcDyaQO zrrBdcDyaQO) {

        GgxyptDataResponse<List<ZrrDyaDTO>> response = new GgxyptDataResponse();

//
        LOGGER.info("正常请求:{}", zrrBdcDyaQO.toString());
        if (CheckParameter.checkAllParameter(zrrBdcDyaQO)) {
            //查询产权
            List<ZrrDyaDTO> zrrDyaDTOList = bdcXmMapper.queryZrrDyaList(zrrBdcDyaQO);
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
            return ExchangeDsfCommonMsgResponse.ok(response);
        } else {
            return ExchangeDsfCommonMsgResponse.fail(CommonConstantUtils.RESPONSE_FAIL, "入参不可为空！");
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


}
