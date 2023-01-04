package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.down.DzzzDownRequestModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.query.DzzzQueryRequestModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.sign.DzzzSignRequestModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.verify.DzzzVerifyRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzCityService;
import cn.gtmap.realestate.certificate.core.support.annotations.RecordLog;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.SyncDzzzClxxDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 电子证照共享模块 对外接口管理
 */
@Controller
@Api("电子证照共享模块对外接口")
@RequestMapping(value = "/realestate-e-certificate/feign")
public class BdcDzzzFeignRestController{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BdcDzzzQueryService bdcDzzzQueryService;
    @Autowired
    private BdcDzzzDownloadService bdcDzzzDownloadService;
    @Autowired
    private BdcDzzzVerifyService bdcDzzzVerifyService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzDigitalService bdcDzzzDigitalService;
    @Autowired
    private BdcDzzzCreateConfigService bdcDzzzCreateConfigService;
    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Autowired
    private BdcDzzzCityService bdcDzzzCityService;
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;

    /**
     * @param jsonString
     * @return 返回状态（状态成功  0，状态失败 1）
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 插入电子证照信息+生成电子证照PDF+附件上传文件中心
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgl/zzpdf", method = RequestMethod.POST)
    @ApiOperation(value = "插入证照信息+生成证照PDF+附件上传文件中心", notes = "不动产电子证照生成（包含PDF）接口")
    @RecordLog
    public DzzzResponseModel zzpdf(HttpServletRequest request, @RequestParam String yymc,  @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-插入电子证照信息并且生成电子证照PDF接口：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

        boolean signBool = bdcDzzzDigitalService.verifySign(yymc
                , dzzzRequestModel.getHead().getSign(), jsonString);
        if (!signBool) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SIGNTURE_ERROR.getCode(), null);
        }
        return bdcDzzzCreateConfigService.getCreateDzzzService(BdcDzzzPdfUtil.DZZZ_DWDM).createDzzz(dzzzRequestModel.getData());
    }

    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgl/zzzt", method = RequestMethod.POST)
    @ApiOperation(value = "证照状态注销接口", notes = "证照状态注销功能")
    @RecordLog
    public DzzzResponseModel zzzt(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        logger.info("电子证照管理-证照注销接口：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        BdcDzzzZzxx bdcDzzzZzxx = JSON.parseObject(jsonString, BdcDzzzZzxx.class);
        return bdcDzzzZzzxConfigService.getCreateDzzzService(BdcDzzzPdfUtil.DZZZ_DWDM).dzzzZzzx(bdcDzzzZzxx);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/zzzx", method = RequestMethod.POST)
    @ApiOperation(value = "证照注销接口2.0", notes = "证照注销功能")
    @RecordLog
    public DzzzResponseModel zzzx(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        logger.info("电子证照管理-证照注销接口2.0：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        BdcDzzzZzxx bdcDzzzZzxx = JSON.parseObject(jsonString, BdcDzzzZzxx.class);
        return bdcDzzzZzzxConfigService.dzzzZzzx(bdcDzzzZzxx);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/mbpzpdf", method = RequestMethod.POST)
    @ApiOperation(value = "按照配置模板生成PDF2.0", notes = "不动产电子证照生成（包含PDF）接口")
    @RecordLog
    public DzzzResponseModel mbpzpdf(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-插入电子证照信息并且生成电子证照PDF接口2.0：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        return bdcDzzzCreateConfigService.createDzzz(dzzzRequestModel.getData());
    }

    /**
     * @param jsonString 请求json
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照系统共享服务 电子证照检索接口
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgx/zzjs", method = RequestMethod.POST)
    @ApiOperation(value = "证照检索接口", notes = "证照检索")
    @RecordLog
    public DzzzResponseModel zzjs(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照检索接口请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        return bdcDzzzQueryService.zzjs(jsonString);

    }

    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgx/zzcx", method = RequestMethod.POST)
    @ApiOperation(value = "证照查询接口", notes = "证照查询")
    @RecordLog
    public DzzzResponseModel zzcx(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
       logger.info("电子证照共享模块-证照检索接口请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        return bdcDzzzQueryService.zzcx(jsonString);
    }


    /**
     * @param jsonString
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照系统共享服务 证照文件下载接口
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgx/zzxxxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件下载接口", notes = "证照文件下载")
    @RecordLog
    public DzzzResponseModel zzxxxz(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块证照文件下载接口请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzDownRequestModel dzzzDownRequestModel = JSON.parseObject(jsonString, DzzzDownRequestModel.class);
        return bdcDzzzDownloadService.dzzzDownloadFile(dzzzDownRequestModel, request);
    }

    /**
     * 电子证照系统共享服务 证照文件下载接口，区别在与 dzzz 库 zzxx 如果为空，先进行同步再判断是否错误
     *
     * @param jsonString
     * @return 电子证照信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseBody
    @PostMapping(value = "/v1.0/zzgx/cl/zzxxxz")
    @ApiOperation(value = "证照文件下载接口", notes = "证照文件下载")
    @RecordLog
    public DzzzResponseModel clzzxxxz(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照存量证照文件下载接口请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzDownRequestModel dzzzDownRequestModel = JSON.parseObject(jsonString, DzzzDownRequestModel.class);
        return bdcDzzzDownloadService.dzzzDownloadClFile(dzzzDownRequestModel, request);
    }

    /**
     * @param jsonString
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 获取电子证照文件中心地址
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgx/zzdzxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件地址获取接口", notes = "获取文件地址功能")
    @RecordLog
    public DzzzResponseModel zzdzxz(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString){
        logger.info("电子证照系统提供获取文件地址功能接口请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzDownRequestModel dzzzDownRequestModel = JSON.parseObject(jsonString, DzzzDownRequestModel.class);
        return bdcDzzzCityService.zzdzxzReplaceRestToFeign(bdcDzzzDownloadService.dzzzDownloadUrl(dzzzDownRequestModel, request));
    }

    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgx/zzxzfile", method = RequestMethod.GET)
    @ApiOperation(value = "通过下载地址获取证照文件接口", notes = "通过下载地址获取证照文件功能")
    @RecordLog
    public DzzzResponseModel zzxzfile(HttpServletRequest request, @RequestParam String yymc, @RequestParam String fid){
        logger.info("电子证照系统提供获取文件地址功能接口请求参数：yymc: {}，fid: {}, 请求时间：{}", yymc, fid, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        return bdcDzzzDownloadService.dzzzDownloadFileByUrl(fid);
    }


    /**
     * @param jsonString
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">zhangyu</a>
     * @description 电子证照系统共享服务 证照信息获取接口
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgx/zzysj", method = RequestMethod.POST)
    @ApiOperation(value = "证照元数据获取接口", notes = "证照元数据获取")
    @RecordLog
    public DzzzResponseModel zzysj(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块证照元数据获取接口请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        return bdcDzzzQueryService.zzysj(jsonString);
    }


    /**
     * @param jsonString
     * @return 电子证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照系统共享服务 证照信息验证接口
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgx/zzxxyz", method = RequestMethod.POST)
    @ApiOperation(value = "证照信息验证接口", notes = "证照信息验证")
    @RecordLog
    public DzzzResponseModel zzxxyz(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块证照信息验证接口请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzVerifyRequestModel dzzzVerifyRequestModel = null;
        try {
            dzzzVerifyRequestModel = JSON.parseObject(jsonString, DzzzVerifyRequestModel.class);
        } catch (JSONException e) {
            logger.error("BdcDzzzFeignRestController:zzxxyz", e);
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
    @RequestMapping(value = "/v1.0/zzgx/zzwjyz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件验证接口", notes = "证照文件验证")
    @RecordLog
    public DzzzResponseModel zzwjyz(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块证照文件验证接口请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzVerifyRequestModel dzzzVerifyRequestModel = JSON.parseObject(jsonString, DzzzVerifyRequestModel.class);
        return bdcDzzzVerifyService.dzzzVerifyFile(dzzzVerifyRequestModel);
    }


    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/zzpdf", method = RequestMethod.POST)
    @ApiOperation(value = "市级生成PDF2.0", notes = "市级生成PDF2.0")
    @RecordLog
    public DzzzResponseModel zzpdf2(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理模块-市级生成PDF2.0请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            logger.info("位置1");
            logger.info("生成电子证照返回结果：{}", JSON.toJSONString(dzzzRequestModel));
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

        // 验证
        List<String> resultList = new ArrayList<>(2);
        resultList.add("zzbh");
        resultList.add("zzbs");
        DzzzResponseModel checkBdcDzzzZzxxModel = bdcDzzzZzxxService.checkBdcDzzzZzxxCreate(dzzzRequestModel.getData(), resultList);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkBdcDzzzZzxxModel.getHead().getStatus())) {
            logger.info("位置2");
            logger.info("生成电子证照返回结果：{}", JSON.toJSONString(checkBdcDzzzZzxxModel));
            return checkBdcDzzzZzxxModel;
        }
        logger.info("位置3");
        DzzzResponseModel result =  bdcDzzzCityService.zzpdf(jsonString, request);
        logger.info("生成电子证照返回结果：{}", JSON.toJSONString(result));
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/clzzpdf", method = RequestMethod.POST)
    @ApiOperation(value = "市级生成证照(存量)2.0", notes = "市级生成证照(存量)2.0")
    @RecordLog
    public DzzzResponseModel clzzpdf(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理模块-市级生成证照(存量)2.0请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

        // 验证
        List<String> resultList = new ArrayList<>(2);
        resultList.add("zzbh");
        resultList.add("zzbs");
        DzzzResponseModel checkBdcDzzzZzxxModel = bdcDzzzZzxxService.checkBdcDzzzZzxxCreate(dzzzRequestModel.getData(), resultList);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkBdcDzzzZzxxModel.getHead().getStatus())) {
            return checkBdcDzzzZzxxModel;
        }
        return bdcDzzzCityService.clzzpdf(jsonString, request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/zzzt", method = RequestMethod.POST)
    @ApiOperation(value = "证照注销接口2.0", notes = "证照注销功能")
    @RecordLog
    public DzzzResponseModel zzzt2(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        logger.info("电子证照管理模块-证照注销接口2.0请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzResponseModel beforeCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxBeforeCheck(JSON.parseObject(jsonString, BdcDzzzZzxx.class));
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), beforeCheckModel.getHead().getStatus())) {
            return beforeCheckModel;
        }
        return bdcDzzzCityService.zzzx(jsonString, request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzxxxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件下载接口2.0", notes = "证照文件下载")
    @RecordLog
    public DzzzResponseModel zzxxxz2(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照文件下载接口2.0请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzResponseModel checkModel = bdcDzzzDownloadService.dzzzDownloadCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        return bdcDzzzCityService.dzzzDownloadFile(jsonString, request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzdzxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照文件地址获取接口2.0", notes = "获取文件地址功能")
    @RecordLog
    public DzzzResponseModel zzdzxz2(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString){
        logger.info("电子证照共享模块-证照文件地址获取接口2.0请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzResponseModel checkModel = bdcDzzzDownloadService.dzzzDownloadCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        return bdcDzzzCityService.zzdzxzReplaceRestToFeign(bdcDzzzCityService.dzzzDownloadUrl(jsonString, request));
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzcx", method = RequestMethod.POST)
    @ApiOperation(value = "证照查询接口2.0", notes = "证照查询")
    @RecordLog
    public DzzzResponseModel zzcx2(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照查询接口2.0请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzResponseModel checkModel = bdcDzzzQueryService.zzcxParamCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        return bdcDzzzCityService.zzcx(JSON.parseObject(jsonString, DzzzQueryRequestModel.class), request);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzcxxz", method = RequestMethod.POST)
    @ApiOperation(value = "证照查询下载接口2.0", notes = "证照查询下载")
    @RecordLog
    public DzzzResponseModel zzcxxz(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照查询接口2.0请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzResponseModel checkModel = bdcDzzzQueryService.zzcxParamCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        DzzzResponseModel queryResult = bdcDzzzCityService.zzcx(JSON.parseObject(jsonString, DzzzQueryRequestModel.class), request);
        if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), queryResult.getHead().getStatus())) {
            JSONArray queryResultData= (JSONArray) queryResult.getData();
            String zzbs = "";
            if(null != queryResultData && queryResultData.size() > 0){
                zzbs = queryResultData.getJSONObject(0).getString("zzbs");
            }
            if (StringUtils.isBlank(zzbs)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode()
                        , "获取zzbs失败");
            }
            JSONObject object = JSONObject.parseObject(jsonString);
            JSONObject dataObject = object.getJSONObject("data");
            dataObject.put("zzbs",zzbs);
            dataObject.remove("bdcqzh");
            dataObject.remove("zzlxdm");
            jsonString = object.toJSONString();
            logger.info("电子证照共享模块-证照文件下载接口2.0请求参数：yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());
            return bdcDzzzCityService.dzzzDownloadFile(jsonString, request);
        }
        return queryResult;
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/zzjs", method = RequestMethod.POST)
    @ApiOperation(value = "证照检索接口2.0", notes = "证照检索")
    @RecordLog
    public DzzzResponseModel zzjs2(HttpServletRequest request, @RequestParam String yymc, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照共享模块-证照检索接口2.0请求参数： yymc: {}，jsonString: {}, 请求时间：{}", yymc, jsonString, DateUtil.now());

        request.setAttribute(Constants.YYMC, yymc);

        DzzzResponseModel checkModel = bdcDzzzQueryService.zzjsParamCheck(jsonString);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkModel.getHead().getStatus())) {
            return checkModel;
        }
        return bdcDzzzCityService.zzjs(JSON.parseObject(jsonString, DzzzQueryRequestModel.class), request);

    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgx/syncclxx", method = RequestMethod.POST)
    @ApiOperation(value = "同步电子证照材料信息到市级证照库", notes = "同步电子证照材料信息到市级证照库")
    public void syncDzzzClxx(@RequestBody List<SyncDzzzClxxDTO> syncDzzzClxxDTOList){
        bdcDzzzCityService.syncDzzzClxx(syncDzzzClxxDTOList);

    }


}
