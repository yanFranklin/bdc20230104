package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.support.annotations.RecordLog;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.query.DzzzQueryRequestModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.sign.DzzzSignRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzDigitalService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzDownloadService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzQueryService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzzxConfigService;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzCityService;
import cn.gtmap.realestate.certificate.core.support.annotations.CheckToken;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019-9-18
 */
@Controller
@Api("电子证照上报-市级")
@RequestMapping(value = "/realestate-e-certificate/rest")
public class BdcDzzzAppearCityController extends DzzzController {

    @Autowired
    private BdcDzzzCityService bdcDzzzCityService;
    @Autowired
    private BdcDzzzDigitalService bdcDzzzDigitalService;
    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Autowired
    private BdcDzzzQueryService bdcDzzzQueryService;
    @Autowired
    private BdcDzzzDownloadService bdcDzzzDownloadService;

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/zzpdf", method = RequestMethod.POST)
    @ApiOperation(value = "市级生成PDF2.0", notes = "市级生成PDF2.0")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzpdf(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理模块-市级生成PDF2.0请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

        /*boolean signBool = bdcDzzzDigitalService.verifySign((String) request.getAttribute(Constants.YYMC), dzzzRequestModel.getHead().getSign(), jsonString);
        if (!signBool) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SIGNTURE_ERROR.getCode(), null);
        }*/

        // 验证
        List<String> resultList = new ArrayList<>(2);
        resultList.add("zzbh");
        resultList.add("zzbs");
        DzzzResponseModel checkBdcDzzzZzxxModel = bdcDzzzZzxxService.checkBdcDzzzZzxxCreate(dzzzRequestModel.getData(), resultList);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkBdcDzzzZzxxModel.getHead().getStatus())) {
            return checkBdcDzzzZzxxModel;
        }
        return bdcDzzzCityService.zzpdf(jsonString, request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/zzzt", method = RequestMethod.POST)
    @ApiOperation(value = "证照注销接口2.0", notes = "证照注销功能")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzzx(HttpServletRequest request, @RequestBody String jsonString) {
        logger.info("电子证照管理模块-证照注销接口2.0请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        DzzzResponseModel beforeCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxBeforeCheck(JSON.parseObject(jsonString, BdcDzzzZzxx.class));
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), beforeCheckModel.getHead().getStatus())) {
            return beforeCheckModel;
        }
        return bdcDzzzCityService.zzzx(jsonString, request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzxxxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件下载接口2.0", notes = "证照文件下载")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzxxxz(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照文件下载接口2.0请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        DzzzResponseModel checkModel = bdcDzzzDownloadService.dzzzDownloadCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        return bdcDzzzCityService.dzzzDownloadFile(jsonString, request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzdzxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件地址获取接口2.0", notes = "获取文件地址功能")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzdzxz(HttpServletRequest request, @RequestBody String jsonString){
        logger.info("电子证照共享模块-证照文件地址获取接口2.0请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        DzzzResponseModel checkModel = bdcDzzzDownloadService.dzzzDownloadCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        return bdcDzzzCityService.dzzzDownloadUrl(jsonString, request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzcx", method = RequestMethod.POST)
    @ApiOperation(value = "证照查询接口2.0", notes = "证照查询")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzcx(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照查询接口2.0请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        DzzzResponseModel checkModel = bdcDzzzQueryService.zzcxParamCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        return bdcDzzzCityService.zzcx(JSON.parseObject(jsonString, DzzzQueryRequestModel.class), request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzjs", method = RequestMethod.POST)
    @ApiOperation(value = "证照检索接口2.0", notes = "证照检索")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzjs(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照检索接口2.0请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        DzzzResponseModel checkModel = bdcDzzzQueryService.zzjsParamCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        return bdcDzzzCityService.zzjs(JSON.parseObject(jsonString, DzzzQueryRequestModel.class), request);

    }
}
