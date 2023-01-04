package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeRz;
import cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.request.SjptSscxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.response.SjptSscxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.response.SjptSscxResponseHead;
import cn.gtmap.realestate.exchange.core.ex.ValidException;
import cn.gtmap.realestate.exchange.quartz.SjptQuarzService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.CxjgService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.GxRzService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.OpenIdService;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import cn.gtmap.realestate.exchange.util.enums.SjptRzEnum;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-29
 * @description
 */
@OpenController(consumer = "省级平台相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/sjpt")
@Api(tags = "省级平台相关服务")
public class SjptRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SjptRestController.class);

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private OpenIdService openIdService;

    @Autowired
    private GxRzService gxRzService;

    @Autowired
    private CxjgService cxjgService;

    @Autowired
    private SjptQuarzService sjptQuarzService;

    @Value("${sjpt.sscx.openid.filter.flag:true}")
    private Boolean openIdFilterFlag;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return java.lang.Object
     * @description 为省级平台提供 实时查询不动产权利信息
     */
    @OpenApi(apiDescription = "实时查询不动产权利信息",apiName = "",openLog = false)
    @PostMapping("/sscx")
    public Object sjptSscxBdc(@RequestBody SjptSscxRequestDTO requestDTO){
        boolean openid = false;
        SjptSscxResponseDTO responseDTO;
        LOGGER.info("实时查询(总)开始---------------------");
        try {
            if (openIdFilterFlag && requestDTO != null && StringUtils.isNotBlank(requestDTO.getHead().getOpenid())) {
                openid = openIdService.valOpenID(requestDTO.getHead());
            }
            if ((openid || !openIdFilterFlag) && CollectionUtils.isNotEmpty(requestDTO.getData())) {
                responseDTO = exchangeBeanRequestService.request("sjpt_sscx",requestDTO.getData(),SjptSscxResponseDTO.class);
                if(responseDTO == null){
                    responseDTO = responseError("4444", "区县系统异常");
                }
            } else {
                responseDTO = responseError("2020", "OpenId不合法");
            }
        }catch (ValidException e){
            responseDTO = responseError("1001", "查询条件为空");
            GxPeRz gxPeRz = new GxPeRz();
            gxPeRz.setCzlx(SjptRzEnum.SSCX.getCzlx());
            gxRzService.saveFailedRz(gxPeRz, e);
        }catch (Exception e) {
            LOGGER.error("实时接口异常：", e);
            responseDTO = responseError("4444", "区县系统异常");
            GxPeRz gxPeRz = new GxPeRz();
            gxPeRz.setCzlx(SjptRzEnum.SSCX.getCzlx());
            gxRzService.saveFailedRz(gxPeRz, e);
        } finally {
            LOGGER.info("实时查询(总)结束---------------------");
        }
        return responseDTO;
    }

    /**
     * 省级平台查询申请接口
     *
     * @return
     * @author <a href="mailto:zedq@gtmap.cn">zedq</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/execute/query/cxsq")
    public CommonResponse executeQueryCxsq() {
        try {
            if(StringUtils.equals(EnvironmentConfig.getEnvironment()
                    .getProperty("sjpt.queryCxjg.switch"),"true")
                    && cxjgService.checkLastQuarzFinished()){
                cxjgService.executeQueryCxsq(SjptConstants.SJPT_QUARTZ_INTERFACE_CALL_METHOD_POSTMAN);
            }
        }catch (Exception e){
            LOGGER.info("手动执行省级平台查询申请接口报错");
            return CommonResponse.fail("手动执行省级平台查询申请接口报错");
        }
        return CommonResponse.ok();
    }

    /**
     * 省级平台上报申请接口
     *
     * @return
     * @author <a href="mailto:zedq@gtmap.cn">zedq</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/commit/cxjg")
    public CommonResponse commitCxjg() {
        try {
            cxjgService.executeCommitCxsq(SjptConstants.SJPT_QUARTZ_INTERFACE_CALL_METHOD_POSTMAN);
        }catch (Exception e){
            LOGGER.info("手动执行省级平台上报申请接口报错");
            return CommonResponse.fail("手动执行省级平台上报申请接口报错");
        }
        return CommonResponse.ok();
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param errorCode
     * @param errorMsg
     * @return cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.response.SjptSscxResponseDTO
     * @description 异常信息填充
     */
    private SjptSscxResponseDTO responseError(String errorCode, String errorMsg) {
        SjptSscxResponseDTO response = new SjptSscxResponseDTO();
        SjptSscxResponseHead head = new SjptSscxResponseHead();
        head.setXzqdm(EnvironmentConfig.getEnvironment().getProperty("sjpt.xzqdm"));
        head.setCode(errorCode);
        head.setMsg(errorMsg);
        response.setHead(head);
        return response;
    }
}
