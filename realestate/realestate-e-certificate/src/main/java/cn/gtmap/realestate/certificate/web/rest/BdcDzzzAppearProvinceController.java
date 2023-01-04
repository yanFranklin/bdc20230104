package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.support.annotations.RecordLog;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.sign.DzzzSignRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzDigitalService;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzAppearService;
import cn.gtmap.realestate.certificate.core.support.annotations.CheckToken;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019-9-18
 */
@Controller
@Api("电子证照上报-省级")
@RequestMapping(value = "/realestate-e-certificate/rest")
public class BdcDzzzAppearProvinceController extends DzzzController {

    @Autowired
    private BdcDzzzAppearService bdcDzzzAppearService;
    @Autowired
    private BdcDzzzDigitalService bdcDzzzDigitalService;

    @ResponseBody
    @RequestMapping(value = "/v1.1/zzgl/zzpdf", method = RequestMethod.POST)
    @ApiOperation(value = "省级生成证照1.1", notes = "省级生成证照1.1")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzpdf(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-省级生成证照1.1：{}，请求时间：{}", jsonString, DateUtil.now());

        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

        boolean signBool = bdcDzzzDigitalService.verifySign((String) request.getAttribute(Constants.YYMC), dzzzRequestModel.getHead().getSign(), jsonString);
        if (!signBool) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SIGNTURE_ERROR.getCode(), null);
        }

        return bdcDzzzAppearService.zzpdf(dzzzRequestModel.getData());
    }
}
