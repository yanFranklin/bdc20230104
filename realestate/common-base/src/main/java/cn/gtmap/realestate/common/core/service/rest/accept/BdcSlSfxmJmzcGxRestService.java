package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmJmzcGxDO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: realestate
 * @description: 收费项目减免政策关系rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-09 17:06
 **/
public interface BdcSlSfxmJmzcGxRestService {

    /**
     * @param bdcSlSfxmJmzcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/9/9 17:20
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxmjmzcgz")
    List<BdcSlSfxmJmzcGxDO> listSfxmJmzcGx(@RequestBody BdcSlSfxmJmzcGxDO bdcSlSfxmJmzcGxDO);
}
