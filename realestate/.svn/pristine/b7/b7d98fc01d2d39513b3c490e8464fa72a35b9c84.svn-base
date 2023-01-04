package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-01
 * @description 不动产单元查询信息服务
 */
public interface BdcdyXxCxRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param bdcdyxxQOJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 分页查询已登记不动产单元
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/bdcdyxx/page")
    Page<BdcXmDO> listBdcdyByPage(@RequestBody Pageable pageable,
                                  @RequestParam(name = "bdcdyxxQOJson",required = false) String bdcdyxxQOJson);

}
