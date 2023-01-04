package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLcpzDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/26
 * @description 自然资源配置
 */
public interface ZrzyPzRestService {

    /**
     * @param gzldyid 工作流定义ID
     * @return 流程基本配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID查询流程配置
     */
    @GetMapping(value = "/realestate-natural/rest/v1.0/pz/{gzldyid}")
    ZrzyXtLcpzDO queryZrzyXtLcpz(@PathVariable(value = "gzldyid") String gzldyid);
}
