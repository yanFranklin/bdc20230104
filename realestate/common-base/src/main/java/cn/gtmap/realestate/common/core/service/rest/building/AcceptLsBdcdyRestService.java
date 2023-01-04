package cn.gtmap.realestate.common.core.service.rest.building;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 为受理子系统提供
 * 根据BDCLX分页查询BDCDY服务
 */
public interface AcceptLsBdcdyRestService {

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页或列表查询不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/accept/ls/bdcdy/common")
    Object listLsBdcdyhByPageOrList(Pageable pageable,
                                    @RequestParam(name = "paramJson", required = false) String paramJson,
                                    @RequestParam(name = "loadpage", required = false) Boolean loadpage);

}
