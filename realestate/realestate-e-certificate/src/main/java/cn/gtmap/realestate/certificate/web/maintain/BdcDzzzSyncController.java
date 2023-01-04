package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzDownloadService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzYwxxService;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzAppearService;
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
public class BdcDzzzSyncController extends DzzzController {

    @Autowired
    private BdcDzzzAppearService bdcDzzzAppearService;
    @Autowired
    private BdcDzzzYwxxService bdcDzzzYwxxService;
    @Autowired
    private BdcDzzzDownloadService bdcDzzzDownloadService;

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/syncDzzz", method = RequestMethod.POST)
    @ApiOperation(value = "市级同步省级证照信息2.0", notes = "市级同步省级证照信息")
    public DzzzResponseModel syncDzzz(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-市级同步省级证照信息2.0：{}，请求时间：{}", jsonString, DateUtil.now());

        BdcDzzzZzxx bdcDzzzZzxx = JSON.parseObject(jsonString, BdcDzzzZzxx.class);
        if (null == bdcDzzzZzxx) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        return bdcDzzzAppearService.syncDzzz(bdcDzzzZzxx.getZzbs());
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/syncDzzzPdf", method = RequestMethod.POST)
    @ApiOperation(value = "市级同步省级证照文件2.0", notes = "市级同步省级证照文件")
    public DzzzResponseModel syncDzzzPdf(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-市级同步省级证照文件2.0：{}，请求时间：{}", jsonString, DateUtil.now());

        return bdcDzzzAppearService.syncDzzzPdf(jsonString);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/syncDzzzInfo", method = RequestMethod.POST)
    @ApiOperation(value = "省市级同步-查询同步数据2.0", notes = "省市级同步-查询同步数据")
    public DzzzResponseModel syncDzzzInfo(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("省市级同步-查询同步数据2.0-市级单位代码：{}，请求时间：{}", jsonString, DateUtil.now());

        return bdcDzzzAppearService.syncDzzzInfo(jsonString);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/syncDzzzTbzt", method = RequestMethod.POST)
    @ApiOperation(value = "省市级同步-同步状态更改2.0", notes = "省市级同步-同步状态更改")
    public DzzzResponseModel syncDzzzTbzt(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("省市级同步-同步状态更改2.0：{}，请求时间：{}", jsonString, DateUtil.now());
        bdcDzzzYwxxService.updateYwxxTbzt(jsonString, Constants.DZZZ_TBZT_YTB);
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/syncDzzzDownloadInfo", method = RequestMethod.POST)
    @ApiOperation(value = "省市级同步-同步下载信息2.0", notes = "省市级同步-同步下载信息")
    public DzzzResponseModel syncDzzzDownloadInfo(HttpServletRequest request, @RequestBody String jsonString) {
        logger.info("省市级同步-同步下载信息2.0：{}，请求时间：{}", jsonString, DateUtil.now());
        return bdcDzzzAppearService.syncDzzzDownloadInfo(jsonString);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/zzxzfile", method = RequestMethod.POST)
    @ApiOperation(value = "通过下载地址获取证照文件接口2.0", notes = "通过下载地址获取证照文件接口")
    public DzzzResponseModel zzxzfile(HttpServletRequest request, @RequestBody String jsonString){
       logger.info("通过下载地址获取证照文件接口2.0请求参数：{}，请求时间：{}", jsonString, DateUtil.now());

        return bdcDzzzDownloadService.dzzzDownloadFileByUrl(jsonString);
    }
}
