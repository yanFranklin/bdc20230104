package cn.gtmap.realestate.common.core.service.rest.rules;/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/13 8:50
 * @description
 */

import cn.gtmap.realestate.common.core.dto.rules.BdcGzyzTsxxDTO;
import cn.gtmap.realestate.common.core.qo.rules.BdcGzQtYzQO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/5 17:50
 * @description 规则其他验证接口
 */
public interface BdcGzQtYzRestService {
    /**
     * @param bdcGzQtYzQO
     * @return List<BdcGzyzTsxxDTO>
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据BdcGzQtYzQO获取验证信息
     */
    @RequestMapping(path = "/realestate-rules/rest/v1.0/qtyz", method = RequestMethod.POST)
    BdcGzyzTsxxDTO queryBdcGzQtyzTsxx(@RequestBody BdcGzQtYzQO bdcGzQtYzQO);

}
