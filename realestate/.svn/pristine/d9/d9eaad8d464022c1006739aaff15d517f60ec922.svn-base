package cn.gtmap.realestate.common.core.service.rest.register;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/4/9
 * @description 数据源配置接口
 */
public interface BdcSjyPzRestService {
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 运行配置数据源
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/sjypz/pl", method = RequestMethod.POST)
    Map yxPzSjyPl(@RequestBody Map<String,List<Map>> formMap);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/sjypz", method = RequestMethod.POST)
    List<Map> yxPzSjy(@RequestBody Map<String,Object> param);
}
