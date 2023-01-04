package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.service.feign.exchange.DianFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * *
 *
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2019/12/25
 * @description 南通  省级平台接口
 */
@RestController
@RequestMapping("/rest/v1.0/nantong/sjpt")
public class BdcNtSjptController extends BaseController {
    @Autowired
    ExchangeInterfaceRestService exchangeInterfaceRestService;
    @Autowired
    DianFeignService dianFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
     * @param requestObject 查询参数
     * @description 户籍查询申请
     */
    @PostMapping("/hjxxcxsq")
    public Object getXgbmcxHjxxcxsq(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.sjptRequestInterface("xgbmcx_hjxxcxsq", requestObject);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
     * @param requestObject 查询参数
     * @description 户籍查询结果反馈 （需要先通过户籍查询申请，然后等待一分钟获取到反馈后再获取结果）
     */
    @PostMapping("/hjxxcxfk")
    public Object getXgbmcxHjxxcxfk(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.sjptRequestInterface("xgbmcx_hjxxcxfk", requestObject);
    }

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
     * @param contractId 受理编号
     * @description 电过户结果查询
     */
    @GetMapping("/dianghxx")
    public Object getDianGhxxCx(@RequestParam(name="contractId") String contractId){
        Object response = dianFeignService.queryDianGhxx(contractId,userManagerUtils.getCurrentUserName());
        LOGGER.error(JSON.toJSONString(response));
        return response;
    }



}
