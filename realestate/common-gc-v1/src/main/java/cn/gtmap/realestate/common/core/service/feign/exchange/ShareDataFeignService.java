package cn.gtmap.realestate.common.core.service.feign.exchange;

import cn.gtmap.realestate.common.core.service.rest.exchange.ShareDataRestService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-04-26
 * @description 共享服务
 */
@FeignClient(name = "${app.services.exchange-app:exchange-app}")
public interface ShareDataFeignService extends ShareDataRestService {
    /**
     * （常州）查询原系统预测房屋数据 （这里定义在Feign中只供客户端调用）
     * @see conf/spring/exchange/changzhou/wwsq.xml:232
     * @param param 查询参数
     * @return 预测房屋数据
     */
    @PostMapping("/realestate-exchange/rest/v1.0/wwsqinterface/ychsbyysfwbm")
    JSONObject ycfwxxCx(@RequestBody JSONObject param);
}
