package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/29
 * @description 默认意见接口
 */
public interface BdcXtMryjRestService {
    /**
     * @param cjrid 创建人id
     * @return List<BdcXtMryjDO> 默认意见列表
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据用户获取可选意见
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/kxyj/gzldy/{gzldyKey}", method = RequestMethod.GET)
    List<BdcXtMryjDO> listBdcXtKxyj(@RequestParam(value = "cjrid", required = false) String cjrid, @PathVariable(value = "gzldyKey") String gzldyKey, @RequestParam(value = "jdmc") String jdmc);

    /**
     * @param cjrid 创建人id
     * @param gzldyKey 工作流定义key
     * @param jdmc 节点名称
     * @return BdcXtMryjDO 默认意见列表
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取当前用户当前流程当前节点对应的默认意见
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/mryj/gzldy/{gzldyKey}/jd/{jdmc}", method = RequestMethod.GET)
    BdcXtMryjDO queryMryj(@RequestParam(value = "cjrid", required = false) String cjrid, @PathVariable(value = "gzldyKey") String gzldyKey, @PathVariable(value = "jdmc") String jdmc);
}
