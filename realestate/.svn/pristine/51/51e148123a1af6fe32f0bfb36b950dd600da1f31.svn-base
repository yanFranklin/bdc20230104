package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.model.dzzzgx.sign.DzzzSignRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzCreateConfigService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzzxConfigService;
import cn.gtmap.realestate.certificate.core.support.annotations.RecordLog;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate.BdcDzqzRestService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: realestate
 * @description: 常州电子签章服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 09:52
 **/
@RestController
@Api("常州电子签章服务")
public class BdcDzqzCzRestController implements BdcDzqzRestService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Autowired
    private BdcDzzzCreateConfigService bdcDzzzCreateConfigService;

    /**
     * @param yymc
     * @param jsonString
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州电子签章接口
     * @date : 2022/3/10 9:42
     */
    @ResponseBody
    @ApiOperation(value = "常州生成电子签章", notes = "不动产电子证照生成（包含PDF）接口")
    @RecordLog
    @Override
    public DzzzResponseModel czdzqz(@RequestBody String jsonString) {
        // 日志保存方法
        logger.info("电子证照管理-插入电子证照信息并且生成电子证照PDF接口2.0：jsonString: {}, 请求时间：{}", jsonString, DateUtil.now());

        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        return bdcDzzzCreateConfigService.createDzqz(dzzzRequestModel.getData());
    }

    /**
     * @param jsonString
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州公告推送电子签章
     * @date : 2022/3/14 15:13
     */
    @ResponseBody
    @ApiOperation(value = "常州公告推送电子签章", notes = "常州公告推送电子签章接口")
//    @RecordLog
    @Override
    public DzzzResponseModel czggdzqz(@RequestBody String jsonString) {
        logger.info("常州公告推送电子签章：jsonString: {}, 请求时间：{}", jsonString, DateUtil.now());
        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        return bdcDzzzCreateConfigService.createGgDzqz(dzzzRequestModel.getData());
    }

    /**
     * @param jsonString 推送的信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送pdf 数据到签章信息
     * @date : 2022/8/24 9:40
     */
    @Override
    @ResponseBody
    @ApiOperation(value = "非登记正常数据推送电子签章", notes = "非登记正常数据推送电子签章接口")
    public DzzzResponseModel pushDzqz(@RequestBody String jsonString) {
        logger.warn("推送文件信息到电子签章表详细信息为{}", jsonString);
        DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(jsonString, DzzzSignRequestModel.class);
        if (null == dzzzRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }
        return bdcDzzzCreateConfigService.createFdjywDzqz(dzzzRequestModel.getData());
    }


    /**
     * @param qzbs
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据签章标识获取签章的文件下载地址
     * @date : 2022/3/15 14:29
     */
    @Override
    @ApiOperation("根据签章标识获取签章的文件下载地址")
    @ApiImplicitParams({@ApiImplicitParam(name = "qzbs", value = "qzbs", dataType = "String", paramType = "query")})
    public Object queryQzfj(@RequestParam(name = "qzbs") String qzbs) {
        return bdcDzzzCreateConfigService.queryDzqzfj(qzbs);
    }


    /**
     * @param zzid
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 常州电子签章查询签章信息
     * @date : 2022/3/10 9:42
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "常州电子签章查询签章信息", notes = "常州电子签章查询签章信息")
    @RecordLog
    @Override
    public String getQzxx(@RequestParam(name = "zzid") String zzid) {
        return bdcDzzzCreateConfigService.getQzxx(zzid);
    }
}
