package cn.gtmap.realestate.common.core.service.rest.inquiry.bengbu;

import cn.gtmap.realestate.common.core.dto.exchange.openapi.BdcOpenApiDetailInfoDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcNwCjRzDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href ="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021/01/25
 * @description 蚌埠_互联网+业务内网创建日志查询
 */
public interface BdcNwCjRzRestService {

    /**
     * 分页查询日志
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcNwCjRzQOStr
     * @return BdcNwCjRzDTO
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/nwcjrz/page")
    Page<BdcNwCjRzDTO> listNwCjRz(Pageable pageable, @RequestParam(name = "bdcNwCjRzQOStr", required = false) String bdcNwCjRzQOStr);

    /**
     * 统计失败记录
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @return Integer
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/nwcjrz/count")
    Integer countFailedRecord();
}
