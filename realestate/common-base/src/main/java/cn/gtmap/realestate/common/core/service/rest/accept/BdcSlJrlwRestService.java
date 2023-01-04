package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJrLwDO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: bdcdj3.0
 * @description: 接入例外restservice
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-10-05 16:04
 **/
public interface BdcSlJrlwRestService {

    /**
     * @param bdcSlJrLwDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增或者保存例外信息
     * @date : 2022/10/5 16:05
     */
    @PostMapping("/realestate-accept/rest/v1.0/jrlw")
    int saveOrUpdateJrlw(@RequestBody List<BdcSlJrLwDO> bdcSlJrLwDOList);
}
