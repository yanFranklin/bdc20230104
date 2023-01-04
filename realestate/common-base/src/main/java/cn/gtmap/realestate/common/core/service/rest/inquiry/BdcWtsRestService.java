package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcWtsDTO;
import org.springframework.web.bind.annotation.*;


/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/10/31
 * @description 出具委托书服务
 */
public interface BdcWtsRestService {
    /**
     * @param
     * @return
     * @description 新增委托书
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/saveWts")
    int saveWts(@RequestBody BdcWtsDTO bdcWtsDTO);

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 生成委托书编号
    */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/getWts")
    Object getWtsDatas();

    /**
     * 更新委托书状态
     */
    @PutMapping(value = "/realestate-inquiry/rest/v1.0/updateWts")
    Object updateWts(@RequestParam(value = "wtsbh") String wtsbh);
}
