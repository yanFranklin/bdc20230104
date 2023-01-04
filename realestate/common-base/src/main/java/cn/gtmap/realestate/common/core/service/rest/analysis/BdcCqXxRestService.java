package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.dto.analysis.BdcResponseDTO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcCqXzztQO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcXxCxQO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description 产权信息查询
 */
public interface BdcCqXxRestService {


    /**
     * version 1.0
     *
     * @param cxQO
     * @description 产权信息查询
     * @date 2018/12/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/bdc/cqcx")
    BdcResponseDTO queryCqXxList(@RequestBody BdcXxCxQO cxQO);


    /**
     * version 1.0
     *
     * @param qo
     * @return
     * @description 产权限制状态查询
     *     批量查询则使用英文逗号隔开，但是一次只能传入一种参数
     * @date 2018/12/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/realestate-analysis/rest/v1.0/bdc/xzzt")
    BdcResponseDTO queryCqXzzt(BdcCqXzztQO qo);
}
