package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.dto.BdcXxbdDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcXxbdQO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/28
 * @description 信息比对接口
 */
public interface BdcXxbdRestService {

    /**
     * @param bdcXxbdQO 信息比对查询参数
     * @return 信息比对数据结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成信息比对结果
     */
    @PostMapping(value = "/realestate-config/rest/v1.0/xxbd")
    BdcXxbdDTO generateXxbdDTO(@RequestBody BdcXxbdQO bdcXxbdQO);
}
