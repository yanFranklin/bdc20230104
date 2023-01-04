package cn.gtmap.realestate.common.core.service.rest.init.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcYwblhzxxDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: realestate
 * @description: 业务办理核证信息restService
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 09:52
 **/
public interface BdcYwblHzxxRestService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务办理核证信息
     * @date : 2021/7/21 9:52
     */
    @GetMapping("/init/rest/v1.0/ywblhzxx/{gzlslid}")
    List<BdcYwblhzxxDO> listYwblHzxx(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcYwblhzxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增业务办理核证信息
     * @date : 2021/7/21 9:54
     */
    @PostMapping("/init/rest/v1.0/ywblhzxx")
    BdcYwblhzxxDO insertYwblHzxx(@RequestBody BdcYwblhzxxDO bdcYwblhzxxDO);
}
