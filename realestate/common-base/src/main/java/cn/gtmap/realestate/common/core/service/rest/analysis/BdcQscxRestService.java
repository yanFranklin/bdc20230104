package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.dto.analysis.BdcResponseDTO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcXxCxQO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description 不动产信息公开（权属信息）查询
 */
public interface BdcQscxRestService {


    /**
     * version 1.0
     *
     * @param qo
     * @description 不动产权属信息获取
     * @date 2018/12/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/bdc/qscx")
    BdcResponseDTO queryQscxList(@RequestBody BdcXxCxQO qo);


}
