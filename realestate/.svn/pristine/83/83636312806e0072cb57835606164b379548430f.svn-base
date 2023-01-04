package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcWqbaLcGxDO;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: realestate
 * @description: 网签备案流程关系rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-16 09:32
 **/
public interface BdcWqbaLcGxRestService {


    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //查询网签备案
     * @date : 2021/3/16 9:53
     */
    @PostMapping("/realestate-accept/rest/v1.0/wqbagx")
    BdcWqbaLcGxDO queryWqbaLcGx(@RequestBody BdcWqbaLcGxDO bdcWqbaLcGxDO);

    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //新增网签备案信息
     * @date : 2021/3/16 9:53
     */
    @PostMapping("/realestate-accept/rest/v1.0/wqbagx/insert")
    BdcWqbaLcGxDO insertWqbaLcGx(@RequestBody BdcWqbaLcGxDO bdcWqbaLcGxDO);

    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //更新网签备案关系
     * @date : 2021/3/16 9:53
     */
    @PatchMapping("/realestate-accept/rest/v1.0/wqbagx/update")
    BdcWqbaLcGxDO updateWqbaLcGx(@RequestBody BdcWqbaLcGxDO bdcWqbaLcGxDO);
}
