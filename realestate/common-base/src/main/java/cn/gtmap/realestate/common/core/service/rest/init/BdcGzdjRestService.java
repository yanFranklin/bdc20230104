package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcGzdjDO;
import cn.gtmap.realestate.common.core.qo.init.BdcGzdjQO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/13
 * @description 更正登记REST服务
 */
public interface BdcGzdjRestService {

    /**
     * @param xmid 项目ID
     * @return 不动产更正登记集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid查询不动产更正信息
     */
    @GetMapping(path = "/init/rest/v1.0/gzdj/list/{xmid}")
    List<BdcGzdjDO> listBdcGzdjByXmid(@PathVariable("xmid") String xmid);

    /**
     * @param bdcGzdjQO 更正登记查询对象
     * @return 不动产更正登记集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询不动产更正信息
     */
    @PostMapping(path = "/init/rest/v1.0/gzdj/list")
    List<BdcGzdjDO> listBdcGzdj(@RequestBody BdcGzdjQO bdcGzdjQO);
}
