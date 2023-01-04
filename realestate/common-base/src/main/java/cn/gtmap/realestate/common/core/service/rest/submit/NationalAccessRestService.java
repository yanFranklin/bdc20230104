package cn.gtmap.realestate.common.core.service.rest.submit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther xutao
 * @Date 2018-12-12
 * @Description
 */
public interface NationalAccessRestService {

    /**
     * @param xmid 项目编码
     * @author <a href="mailto:xutao@gtmap.cn">xutao</a>
     * @description 数据汇交
     */
    @GetMapping("/realestate-submit/rest/v1.0/access")
    void access(@RequestParam(name = "xmid") String xmid);

}
