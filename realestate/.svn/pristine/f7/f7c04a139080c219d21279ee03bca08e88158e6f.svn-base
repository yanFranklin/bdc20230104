package cn.gtmap.realestate.common.core.service.rest.certificate;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/20
 * @description 不动产归档配置
 */
public interface BdcGdsjPzRestService {
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdpz/{gzlslid}", method = RequestMethod.GET)
    String bdcXxGd(@PathVariable("gzlslid") String gzlslid, @RequestParam(value = "xmid",required = false)String xmid);
}
