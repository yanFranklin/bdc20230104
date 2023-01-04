package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcWtsDTO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtZsyzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcWtsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 连云港委托书台账控制器
 * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcWtsController extends BaseController {
    /**
     * 字典服务
     */
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    /**
     * 用户信息
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcWtsFeignService bdcWtsFeignService;

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 保存委托书数据
     */
    @PostMapping("/saveWts")
    public int saveWts(@RequestBody BdcWtsDTO bdcWtsDTO) {
       return bdcWtsFeignService.saveWts(bdcWtsDTO);
    }

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 生成委托书编号
    */
    @GetMapping("/getWts")
    public Object getWts() {
        return bdcWtsFeignService.getWtsDatas();
    }

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description
    */
    @PutMapping("/updateWts")
    public Object updateWts(@RequestParam(value = "wtsbh") String wtsbh) {
        return bdcWtsFeignService.updateWts(wtsbh);
    }
}
