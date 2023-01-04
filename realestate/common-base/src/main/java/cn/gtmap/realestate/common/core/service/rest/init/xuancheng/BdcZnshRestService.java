package cn.gtmap.realestate.common.core.service.rest.init.xuancheng;

import cn.gtmap.realestate.common.core.dto.init.BdcBjbhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcZwQhResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.znsh.BdcSjjyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/5/12
 * @description 智能审核
 */
public interface BdcZnshRestService {

    /**
     * 智能审核
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param xmid 项目id
     * @return BdcBjbhDTO
     */
    @GetMapping("/init/rest/v1.0/znsh")
    BdcSjjyDTO znsh(@RequestParam(name = "xmid", required = true) String xmid);


}
