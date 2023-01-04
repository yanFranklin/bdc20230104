package cn.gtmap.realestate.exchange.web.rest.yancheng;

import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.yancheng.bdcdj.BdcDjxxReqDto;
import cn.gtmap.realestate.exchange.core.dto.yancheng.bdcdj.BdcDjxxResDto;
import cn.gtmap.realestate.exchange.service.yancheng.BdcDjxxService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@OpenController(consumer = "(盐城) 不动产登记查询服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
@Api(tags = "(盐城) 不动产登记查询服务")
public class BdcDjRestController {

    @Autowired
    private BdcDjxxService bdcDjxxService;



    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //不动产登记信息查询（盐城）
     * @Date 2022/5/12 16:11
     **/
    @OpenApi(apiDescription = "不动产登记信息查询（盐城）",apiName = "",openLog = false)
    @PostMapping(value = "/yancheng/bdcdjxx")
    @DsfLog(logEventName = "不动产登记信息查询（盐城）",dsfFlag = "DJXX",requester = "DJXX",responser = "BDC")
    public BdcDjxxResDto listDjxx(@RequestBody BdcDjxxReqDto req){
        return bdcDjxxService.listDjxx(req);
    }

}
