package cn.gtmap.realestate.common.core.service.rest.electronic;

import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzZzxxDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 电子证照信息相关服务
 */
public interface BdcDzzzZzxxRestService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param pageable
     * @param paramJson
     * @return
     * @description 1.0，2019/1/29
     */
    @PostMapping("/building/rest/v1.0/zzxx/listbypage")
    Page<BdcDzzzZzxxDO> listDzzzByPageJson(Pageable pageable,
                                           @RequestParam(value = "paramJson", required = false) String paramJson);
}
