package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlDyaqDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/30
 * @description 受理抵押rest服务
 */
public interface BdcSlDyaqRestService {

    /**
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据项目ID查询受理抵押信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/dyaq/{xmid}")
    BdcSlDyaqDO queryBdcSlDyaqByXmid(@PathVariable(value = "xmid") String xmid);
}
