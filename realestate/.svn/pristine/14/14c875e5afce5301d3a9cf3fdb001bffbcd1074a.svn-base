package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.support.annotations.RecordLog;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzTokenDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.down.DzzzDownRequestModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.token.DzzzTokenRequestModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.verify.DzzzVerifyRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzDownloadService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzQueryService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzVerifyService;
import cn.gtmap.realestate.certificate.core.support.annotations.CheckToken;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.util.JJWTUtil;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 电子证照共享模块 对外接口管理
 */
@Controller
@Api("电子证照共享模块对外接口")
@RequestMapping(value = "/realestate-e-certificate/rest/v1.0/zzgx")
public class BdcDzzzGxRestController extends DzzzController {

    @Autowired
    private BdcDzzzQueryService bdcDzzzQueryService;
    @Autowired
    private BdcDzzzDownloadService bdcDzzzDownloadService;
    @Autowired
    private BdcDzzzVerifyService bdcDzzzVerifyService;

    @ResponseBody
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ApiOperation(value = "不动产电子证照获取令牌接口", notes = "获取令牌")
    @RecordLog
    public DzzzResponseModel creatToken(@RequestBody String jsonString) {
        Map data = new HashMap(1);
        data.put("token","");
        DzzzTokenRequestModel dzzzTokenRequestModel = JSON.parseObject(jsonString, DzzzTokenRequestModel.class);
        if (null != dzzzTokenRequestModel) {
            String yymc = dzzzTokenRequestModel.getData().getYymc();
            if (StringUtils.isNotBlank(yymc)) {
                BdcDzzzTokenDo bdcDzzzTokenDo = bdcDzzzTokenService.queryTokenByTokenName(yymc);
                if (null != bdcDzzzTokenDo) {
                    Map map = new HashMap();
                    map.put(Constants.YYMC, yymc);
                    data.put("token", JJWTUtil.createJWT(map,null, tokenExpiryTime, base64Security));
                    return bdcDzzzService.dzzzResponseSuccess(data);
                } else {
                    return bdcDzzzService.dzzzResponseFalse(ResponseEnum.FALSE.getCode(), ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getCode());
                }
            }
        }

        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.FALSE.getCode(), ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode());
    }

    /**
     * @param jsonString 请求json
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照系统共享服务 电子证照检索接口
     */
    @ResponseBody
    @RequestMapping(value = "/zzjs", method = RequestMethod.POST)
    @ApiOperation(value = "证照检索接口", notes = "证照检索")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzjs(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照检索接口请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        return bdcDzzzQueryService.zzjs(jsonString);

    }

    @ResponseBody
    @RequestMapping(value = "/zzcx", method = RequestMethod.POST)
    @ApiOperation(value = "证照查询接口", notes = "证照查询")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzcx(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
       logger.info("电子证照共享模块-证照检索接口请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        return bdcDzzzQueryService.zzcx(jsonString);
    }


    /**
     * @param jsonString
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照系统共享服务 证照文件下载接口
     */
    @ResponseBody
    @RequestMapping(value = "/zzxxxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件下载接口", notes = "证照文件下载")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzxxxz(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块证照文件下载接口请求参数：{}，请求时间：{}", jsonString, DateUtil.now());

        DzzzDownRequestModel dzzzDownRequestModel = JSON.parseObject(jsonString, DzzzDownRequestModel.class);
        return bdcDzzzDownloadService.dzzzDownloadFile(dzzzDownRequestModel, request);
    }


    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param jsonString
     * @return
     * @description 获取电子证照文件中心地址
     */
    @ResponseBody
    @RequestMapping(value = "/zzdzxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件地址获取接口", notes = "获取文件地址功能")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzdzxz(HttpServletRequest request, @RequestBody String jsonString){
        logger.info("电子证照系统提供获取文件地址功能接口请求参数：{}，请求时间：{}", jsonString, DateUtil.now());

        DzzzDownRequestModel dzzzDownRequestModel = JSON.parseObject(jsonString, DzzzDownRequestModel.class);
        return bdcDzzzDownloadService.dzzzDownloadUrl(dzzzDownRequestModel, request);
    }

    @ResponseBody
    @RequestMapping(value = "/zzxzfile", method = RequestMethod.GET)
    @ApiOperation(value = "通过下载地址获取证照文件接口", notes = "通过下载地址获取证照文件功能")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzxzfile(HttpServletRequest request, @RequestParam String fid){
        logger.info("电子证照系统提供获取文件地址功能接口请求参数：{}，请求时间：{}", fid, DateUtil.now());

        return bdcDzzzDownloadService.dzzzDownloadFileByUrl(fid);
    }


    /**
     * @param jsonString
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">zhangyu</a>
     * @description 电子证照系统共享服务 证照信息获取接口
     */
    @ResponseBody
    @RequestMapping(value = "/zzysj", method = RequestMethod.POST)
    @ApiOperation(value = "证照元数据获取接口", notes = "证照元数据获取")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzysj(HttpServletRequest request, @RequestBody String jsonString) {
        /**
         * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
         * @description 预留保存调用日志方法
         */
        // 日志保存方法
        logger.info("电子证照共享模块证照元数据获取接口请求参数：{}，请求时间：{}", jsonString, DateUtil.now());
        return bdcDzzzQueryService.zzysj(jsonString);
    }


    /**
     * @param jsonString
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照系统共享服务 证照信息验证接口
     */
    @ResponseBody
    @RequestMapping(value = "/zzxxyz", method = RequestMethod.POST)
    @ApiOperation(value = "证照信息验证接口", notes = "证照信息验证")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzxxyz(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块证照信息验证接口请求参数：{}，请求时间：{}", jsonString, DateUtil.now());

        DzzzVerifyRequestModel dzzzVerifyRequestModel = null;
        try {
            dzzzVerifyRequestModel = JSON.parseObject(jsonString, DzzzVerifyRequestModel.class);
        } catch (JSONException e) {
            logger.error("BdcDzzzGxRestController:dzzzVerifyInfo:JSONException", e);
        }
        if (null == dzzzVerifyRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.FALSE.getCode(), ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode());
        }

        return bdcDzzzVerifyService.dzzzVerifyInfo(dzzzVerifyRequestModel);
    }


    /**
     * @param jsonString
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照系统共享服务 证照文件验证接口
     */
    @ResponseBody
    @RequestMapping(value = "/zzwjyz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件验证接口", notes = "证照文件验证")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzwjyz(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块证照文件验证接口请求参数：{}，请求时间：{}", jsonString, DateUtil.now());

        DzzzVerifyRequestModel dzzzVerifyRequestModel = JSON.parseObject(jsonString, DzzzVerifyRequestModel.class);
        return bdcDzzzVerifyService.dzzzVerifyFile(dzzzVerifyRequestModel);
    }


}
