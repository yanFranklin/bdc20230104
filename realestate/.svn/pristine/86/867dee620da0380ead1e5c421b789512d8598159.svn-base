package cn.gtmap.realestate.common.core.service.rest.inquiry.xuancheng;

import cn.gtmap.realestate.common.core.dto.inquiry.rugao.BdcCqxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcLqtjDTO;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcLqtjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/11/21
 * @description 宣城林权类查询
 */
public interface BdcLqCxRestService {

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param bdcLqtjQO
     * @return
     * @description 林权统计查询
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/lqcx/tjcx")
    List<BdcLqtjDTO> listTjCx(@RequestBody BdcLqtjQO bdcLqtjQO);

}
