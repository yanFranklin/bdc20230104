package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.sign.DzzzSignRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.signature.BdcDzzzSignaTure;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzCreateConfigService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzDigitalService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzzxConfigService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzzzSignCzService;
import cn.gtmap.realestate.certificate.core.support.annotations.CheckToken;
import cn.gtmap.realestate.certificate.core.support.annotations.RecordLog;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
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
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, 2019/01/19
 * @description 电子证照管理模块 对外接口管理
 */
@Controller
@Api("电子证照管理对外接口")
@RequestMapping(value = "/realestate-e-certificate/rest")
public class BdcDzzzGlRestController extends DzzzController {
    @Autowired
    private BdcDzzzSignCzService bdcDzzzSignCzService;
    @Autowired
    private BdcDzzzDigitalService bdcDzzzDigitalService;
    @Autowired
    private BdcDzzzCreateConfigService bdcDzzzCreateConfigService;
    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    /**
     * @param jsonString
     * @return 返回状态（状态成功  0，状态失败 1）
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 插入电子证照信息+生成电子证照PDF+附件上传文件中心
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgl/zzpdf", method = RequestMethod.POST)
    @ApiOperation(value = "插入证照信息+生成证照PDF+附件上传文件中心", notes = "不动产电子证照生成（包含PDF）接口")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzpdf(HttpServletRequest request, @RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-插入电子证照信息并且生成电子证照PDF接口：{}，请求时间：{}", jsonString, DateUtil.now());

        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

        boolean signBool = bdcDzzzDigitalService.verifySign((String) request.getAttribute(Constants.YYMC), dzzzRequestModel.getHead().getSign(), jsonString);
        if (!signBool) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SIGNTURE_ERROR.getCode(), null);
        }
        return bdcDzzzCreateConfigService.getCreateDzzzService(BdcDzzzPdfUtil.DZZZ_DWDM).createDzzz(dzzzRequestModel.getData());
    }


    /**
     * @param jsonString
     * @return 返回状态（状态成功  0，状态失败 1）
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 删除电子证照信息
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgl/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除证照信息", notes = "删除电子证照信息接口")
    @CheckToken
    @RecordLog
    public DzzzResponseModel delete(@RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-删除电子证照信息接口：{}，请求时间：{}", jsonString, DateUtil.now());

        BdcDzzzZzxx bdcDzzzZzxx = JSON.parseObject(jsonString, BdcDzzzZzxx.class);
        //删除证照信息
        return bdcDzzzZzxxService.deleteBdcDzzz(bdcDzzzZzxx);

    }


    /**
     * @param jsonString
     * @return 返回插入状态（插入成功，插入失败）
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 插入电子证照信息到证照信息库  生成元数据xml
     */
    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgl/zzxx", method = RequestMethod.POST)
    @ApiOperation(value = "插入证照信息到证照信息库", notes = "不动产电子证照生成（不生成PDF）接口")
    @RecordLog
    public DzzzResponseModel zzxx(@RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-插入电子证照信息到证照信息库接口请求参数：{}，请求时间：{}", jsonString, DateUtil.now());

        BdcDzzzZzxx bdcDzzzZzxx = JSON.parseObject(jsonString, BdcDzzzZzxx.class);
        //转发生成的电子证照不应该添加加注件信息
        if (null != bdcDzzzZzxx) {
            bdcDzzzZzxx.setJzjzzsj(null);
            bdcDzzzZzxx.setJzjzzz(null);
            bdcDzzzZzxx.setJzjyxqjzsj(null);
            bdcDzzzZzxx.setJzjzzsy(null);
        }
        return bdcDzzzZzxxService.insertBdcDzzzZzxx(bdcDzzzZzxx);
    }


    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgl/clzhdzzz", method = RequestMethod.POST)
    @ApiOperation(value = "存量数据转换到证照库", notes = "存量数据转换到证照库V2.0")
    @RecordLog
    public DzzzResponseModel clzhdzzz(@RequestBody String jsonString) {
        return new DzzzResponseModel(new ResponseHead(ResponseEnum.FALSE.getCode(), ResponseEnum.RESPONSE_UNAUTHORIZED_ERROR.getCode()), null);
    }

    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgl/zzzt", method = RequestMethod.POST)
    @ApiOperation(value = "证照状态注销接口", notes = "证照状态注销功能")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzzt(@RequestBody String jsonString) {
        logger.info("电子证照管理-证照注销接口：{}，请求时间：{}", jsonString, DateUtil.now());

        BdcDzzzZzxx bdcDzzzZzxx = JSON.parseObject(jsonString, BdcDzzzZzxx.class);
        return bdcDzzzZzzxConfigService.getCreateDzzzService(BdcDzzzPdfUtil.DZZZ_DWDM).dzzzZzzx(bdcDzzzZzxx);
    }

    @ResponseBody
    @RequestMapping(value = "/v1.0/zzgl/zzqz", method = RequestMethod.POST)
    @ApiOperation(value = "证照签章接口", notes = "证照签章规范")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzqz(@RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-证照签章接口：{}，请求时间：{}", jsonString, DateUtil.now());

        BdcDzzzSignaTure bdcDzzzSignaTure = JSON.parseObject(jsonString, BdcDzzzSignaTure.class);

        return bdcDzzzSignCzService.sign(bdcDzzzSignaTure);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/zzzx", method = RequestMethod.POST)
    @ApiOperation(value = "证照注销接口2.0", notes = "证照注销功能")
    @CheckToken
    @RecordLog
    public DzzzResponseModel zzzx(@RequestBody String jsonString) {
        logger.info("电子证照管理-证照注销接口2.0：{}，请求时间：{}", jsonString, DateUtil.now());

        BdcDzzzZzxx bdcDzzzZzxx = JSON.parseObject(jsonString, BdcDzzzZzxx.class);
        return bdcDzzzZzzxConfigService.dzzzZzzx(bdcDzzzZzxx);
    }

    @ResponseBody
    @RequestMapping(value = "/v2.0/zzgl/mbpzpdf", method = RequestMethod.POST)
    @ApiOperation(value = "按照配置模板生成PDF2.0", notes = "不动产电子证照生成（包含PDF）接口")
    @CheckToken
    @RecordLog
    public DzzzResponseModel mbpzpdf(@RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-插入电子证照信息并且生成电子证照PDF接口2.0：{}，请求时间：{}", jsonString, DateUtil.now());

        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        return bdcDzzzCreateConfigService.createDzzz(dzzzRequestModel.getData());
    }
}
