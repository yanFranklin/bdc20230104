package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/11
 * @description 综合小屏-中心不同业务的排号情况
 */
public interface BdcZhxpCxRestService {
    /**
     * @return 当前呼叫信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合小屏当前呼叫信息查询
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zhxp/dqhj/{zxmc}")
    List<Map> listBdcZhxpDqhj(@PathVariable("zxmc")String zxmc);

    /**
     * @return 等待呼叫信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 综合小屏等待呼叫信息查询
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zhxp/ddhj/{zxmc}")
    List<Map> listBdcZhxpDdhj(@PathVariable("zxmc")String zxmc);
}
