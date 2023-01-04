package cn.gtmap.realestate.common.core.service.rest.rules;

import cn.gtmap.realestate.common.core.domain.rules.BdcGzXzyzlwDO;
import cn.gtmap.realestate.common.core.dto.rules.BdcGzXzYzLwResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/16,1.0
 * @description
 */
public interface BdcGzXzYzLwRestService {

    /**
     * @param czry 操作人员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询例外信息
     */
    @PostMapping("/realestate-rules/rest/v1.0/yzlw/page")
    Page<BdcGzXzYzLwResponseDTO> listBdcXzyzLwPage(@RequestParam(name = "qlr", required = false) String czry,
                                                   @RequestParam(name = "ip", required = false) String ip,
                                                   @RequestParam(name = "lwwh", required = false)String lwwh,
                                                   Pageable pageable);

    /**
     * @param bdcGzXzyzlwDOList 例外信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 增加例外信息
     */
    @PostMapping(path = "/realestate-rules/rest/v1.0/yzlw")
    void insertBdcLwYz(@RequestBody List<BdcGzXzyzlwDO> bdcGzXzyzlwDOList);

    /**
     * @param lwid 例外id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除例外信息
     */
    @DeleteMapping(path = "/realestate-rules/rest/v1.0/yzlw")
    void deleteBdcLwYz(@RequestParam(name = "lwid", required = true) String lwid);
}
