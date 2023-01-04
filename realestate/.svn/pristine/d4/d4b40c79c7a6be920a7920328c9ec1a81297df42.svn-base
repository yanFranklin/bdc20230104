package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0
 * @date 2022/7/18 16:25
 * @description 省级平台接口调用
 */
public interface BdcSjptRestService {

    /**
     * 根据起始时间查询省级查询申请的查询量
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/querySjsjlcxCount")
    Integer querySjsjlcxCount(@RequestParam(value = "kssj") String kssj, @RequestParam(value = "jssj") String jssj);

}
