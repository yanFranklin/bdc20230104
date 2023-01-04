package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.building.SDmXzdmDO;
import cn.gtmap.realestate.common.core.service.feign.building.BuildingZdConvertFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/6
 * @description 国家部级接口
 */
@RestController
@RequestMapping("/rest/v1.0/bjjk")
public class BdcGjbjjkController extends BaseController {
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceRestService;

    @Autowired
    BuildingZdConvertFeignService buildingZdConvertFeignService;

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 国家部级接口-婚姻登记信息
     */
    @PostMapping("/hydjxx")
    public Object getBjjkHydjxx(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("bjjk_hydjxx", requestObject);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 国家部级接口-婚姻登记信息
     */
    @PostMapping("/shzz")
    public Object getBjjkShzz(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("bjjk_shzz", requestObject);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 国家部级接口-公安身份核查接口
     */
    @PostMapping("/gasfhc")
    public Object getBjjkGasfhc(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("bjjk_gasfhc", requestObject);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 国家部级接口-司法判决查询
     */
    @PostMapping("/sfpjcx")
    public Object getSfpjcx(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("bjjk_sfpjcx", requestObject);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 国家部级接口-司法判决响应结果查询
     */
    @PostMapping("/sfpjcxfk")
    public Object getSfpjcxfk(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("bjjk_sfpjcxfk", requestObject);
    }


    /**
     * @param requestObject
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 国家部级接口-中编办
     */
    @PostMapping("/zbb")
    public Object getZbb(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("bjjk_zbb", requestObject);
    }

    /**
     * @param requestObject
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 金融许可证查询
     */
    @PostMapping("/jrxkz")
    public Object getJrxkz(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("bjjk_jrxkz", requestObject);
    }

    /**
     * @param requestObject
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 合肥卫计委出生证明查询接口
     */
    @PostMapping("/hfWjwcszmcx")
    public Object getWjwcszmcx(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("hf_wjwcszmcx", requestObject);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 卫计委死亡证明查询接口
     */
    @PostMapping("/swzm")
    public Object getSwzm(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("hf_wjwswzmcx", requestObject);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取权籍行政区字典
     */
    @GetMapping("/qjxzq")
    public List<Map> getQjxzq() {
        return buildingZdConvertFeignService.getZdTable("s_dm_xzdm", SDmXzdmDO.class);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 地名信息查询接口
     */
    @PostMapping("/dmxxcx")
    public Object getDmxx(@RequestBody Object requestObject) {
        return exchangeInterfaceRestService.requestInterface("bjjk_dmxxcx", requestObject);
    }
}
