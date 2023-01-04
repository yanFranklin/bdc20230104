package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/27
 * @description 受理核税信息明细rest服务
 */
public interface BdcSlHsxxMxRestService {

    /**
     * @param json 集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新受理核税信息明细
     */
    @PutMapping("/realestate-accept/rest/v1.0/hsxxmx/list/")
    int batchUpdateBdcSlHsxxMx(@RequestBody String json);


    /**
     * @param hsxxid 核税信息ID
     * @return 受理核税信息明细
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据核税信息ID查询受理核税信息明细
     */
    @GetMapping("/realestate-accept/rest/v1.0/hsxxmx/list/{hsxxid}")
    List<BdcSlHsxxMxDO> bdcSlHsxxMx(@PathVariable("hsxxid") String hsxxid);
}
