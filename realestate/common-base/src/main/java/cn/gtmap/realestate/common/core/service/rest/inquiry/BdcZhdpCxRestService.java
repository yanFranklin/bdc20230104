package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/11
 * @description 综合大屏-所有中心办理业务的集合展示
 */
public interface BdcZhdpCxRestService {
    /**
     * @return 综合大屏查询结果
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合大屏所有中心办理业务查询
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zhdp")
    List<Map> listBdcZhdp();
}
