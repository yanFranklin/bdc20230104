package cn.gtmap.realestate.common.core.service.rest.rules;
/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/26 8:50
 * @description
 */

import cn.gtmap.realestate.common.core.domain.rules.BdcGzYwgzDO;
import cn.gtmap.realestate.common.core.domain.rules.BdcGzZhDO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/26 8:50
 * @description 规则检验接口
 */
public interface BdcGzCheckRestService {

    /**
     * @param bdcGzYwgzDO
     * @return map
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 检验规则合法性
     */
    @PutMapping("/realestate-rules/rest/v1.0/checkgz")
    Map checkBdcGzRules(@RequestBody BdcGzYwgzDO bdcGzYwgzDO);

    /**
     * @param bdcGzZhDO
     * @return map
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 检验规则组合合法性
     */
    @PutMapping("/realestate-rules/rest/v1.0/checkgzzh")
    Map checkBdcGzZhRules(@RequestBody BdcGzZhDO bdcGzZhDO);
}
