package cn.gtmap.realestate.inquiry.ui.web.rest;


import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhShDTO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcTddysfDyhFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/12
 * @description 土地抵押释放
 */
@RestController
@RequestMapping("/rest/v1.0/tddysf")
public class BdcTddysfController {

    @Autowired
    BdcTddysfDyhFeignService bdcTddysfDyhFeignService;


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 例外审核
     */
    @PostMapping("/lwsh")
    public void lwsh(@RequestBody BdcTddysfDyhShDTO bdcTddysfDyhShDTO){
        bdcTddysfDyhFeignService.updateBdcTddysfDyhShxx(bdcTddysfDyhShDTO);
    }




}
