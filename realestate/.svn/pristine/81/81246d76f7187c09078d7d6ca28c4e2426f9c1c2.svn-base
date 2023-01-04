package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlYcslDjywDzbDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/5
 * @description
 */
public interface BdcYcslDjywDzbRestService {

    /**
     * @param bdcSlYcslDjywDzbDO 查询对象
     * @return 对照关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询一窗受理与登记业务对照关系
     */
    @PostMapping("/realestate-accept/rest/v1.0/ycsldjywdzb")
    BdcSlYcslDjywDzbDO queryYcslDjywDzb(@RequestBody BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbDO);

    /**
     * @param gzldyid 工作流定义ID
     * @return true为是
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 判断是否为一窗受理流程
     */
    @GetMapping("/realestate-accept/rest/v1.0/ycsldjywdzb/{gzldyid}")
    Boolean checkIsYcslLc(@PathVariable(value = "gzldyid") String gzldyid);
}
