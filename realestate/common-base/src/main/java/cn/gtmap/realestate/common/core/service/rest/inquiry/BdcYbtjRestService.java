package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/12/28
 * @description 月报统计查询服务
 */
public interface BdcYbtjRestService {
    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param bdcYbtjQO
     * @description 月报统计查询
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/ybtj/bdcywtj")
    List bdcywtj(@RequestBody BdcYbtjQO bdcYbtjQO);

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param bdcYbtjQO
     * @description 月报统计查询
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/ybtj/bdcywzbtj")
    List bdcyzbwtj(@RequestBody BdcYbtjQO bdcYbtjQO);

}