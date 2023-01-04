package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/11
 * @description 综合屏数据处理
 */
public interface BdcZhpSjclRestService {
    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合屏队列信息处理
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zhp/dlxx")
    void dlxx(@RequestBody String json);

    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合屏队列信息处理
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zhp/xxcl")
    void xxcl(@RequestBody String json);
}
