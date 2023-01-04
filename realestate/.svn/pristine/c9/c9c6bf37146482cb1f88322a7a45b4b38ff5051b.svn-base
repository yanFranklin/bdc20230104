package cn.gtmap.realestate.exchange.web.rest.yancheng;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.enums.GjjActionTypeEnum;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcGjjService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
 * @version 1.0  2021-08-09
 * @description (盐城) 公积金接口相关服务
 */
@OpenController(consumer = "(盐城) 公积金接口相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/gjj")
@Api(tags = "(盐城) 公积金接口相关服务")
public class BdcGjjRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGjjRestController.class);

    @Autowired
    private BdcGjjService bdcGjjService;

    /**
     * 盐城_公积金双预告回调接口
     *
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "盐城_公积金双预告抵押回调接口")
    @GetMapping("/sygdy/dj/{actionType}")
    @DsfLog(logEventName = "盐城_公积金预抵押成功回调接口", dsfFlag = "GJJ", requester = "BDC", responser = "GJJ")
    public CommonResponse ydySendSuccessToGjj(@RequestParam(name = "processInsId") String processInsId,@PathVariable(value = "actionType")String actionType,@RequestParam(name = "opinion",required = false) String opinion ) {
        String reason = "";
        if (StringUtils.isNotBlank(actionType) && actionType.equals(GjjActionTypeEnum.RETURN.getCode()) && StringUtils.isNotBlank(opinion)){
            actionType = GjjActionTypeEnum.DELETE.getCode();
            reason = opinion;
        }
        if (StringUtils.isNotBlank(processInsId) && StringUtils.isNotBlank(actionType)){
            return bdcGjjService.sygdyGjjCallback(processInsId,actionType,reason);
        }else {
            throw new RuntimeException("入参processInsId为空");
        }
    }

    /**
     * 盐城_公积金抵押成功回调接口
     *
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "盐城_公积金首次抵押回调接口")
    @GetMapping("/scdy/dj/{actionType}")
    @DsfLog(logEventName = "盐城_公积金首次抵押回调接口", dsfFlag = "GJJ", requester = "BDC", responser = "GJJ")
    public CommonResponse dySendSuccessToGjj(@RequestParam(name = "processInsId") String processInsId,@PathVariable(value = "actionType")String actionType,@RequestParam(name = "opinion",required = false) String opinion) {
        String reason = "";
        if (StringUtils.isNotBlank(actionType) && actionType.equals(GjjActionTypeEnum.RETURN.getCode()) && StringUtils.isNotBlank(opinion)){
            actionType = GjjActionTypeEnum.DELETE.getCode();
            reason = opinion;
        }
        if (StringUtils.isNotBlank(processInsId) && StringUtils.isNotBlank(actionType)){
            return bdcGjjService.scdyGjjCallback(processInsId,actionType,reason);
        }else {
            throw new RuntimeException("入参processInsId为空");
        }
    }

    /**
     * 盐城_公积金注销押回调接口
     *
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "盐城_公积金注销押回调接口")
    @GetMapping("/zxdy/dj/{actionType}")
    @DsfLog(logEventName = "盐城_公积金注销押回调接口", dsfFlag = "GJJ", requester = "BDC", responser = "GJJ")
    public CommonResponse sendDeleteInfoToGjj(@RequestParam(name = "processInsId") String processInsId,@PathVariable(value = "actionType")String actionType,@RequestParam(name = "opinion",required = false) String opinion) {
        String reason = "";
        if (StringUtils.isNotBlank(actionType) && actionType.equals(GjjActionTypeEnum.RETURN.getCode()) && StringUtils.isNotBlank(opinion)){
            actionType = GjjActionTypeEnum.DELETE.getCode();
            reason = opinion;
        }
        if (StringUtils.isNotBlank(processInsId) && StringUtils.isNotBlank(actionType)){
            return bdcGjjService.zxdyGjjCallback(processInsId,actionType,reason);
        }else {
            throw new RuntimeException("入参processInsId为空");
        }
    }


}
