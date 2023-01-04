package cn.gtmap.realestate.common.core.service.feign.rules;

        import cn.gtmap.realestate.common.core.service.rest.rules.BdcGzQtYzRestService;
        import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href ="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.3, 2018/12/5
 * @description 规则其他验证接口
 */
@FeignClient("rules-app")
public interface BdcGzQtYzFeignService extends BdcGzQtYzRestService {
}
