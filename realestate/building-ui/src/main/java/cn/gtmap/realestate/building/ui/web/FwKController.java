package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwKDO;
import cn.gtmap.realestate.common.core.service.feign.building.FwKFeignService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-28
 * @description 自然幢维度
 */
@Controller
@RequestMapping("/fwk")
public class FwKController extends BaseController{
    @Autowired
    private FwKFeignService fwKFeignService;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param lszd
     * @param zrzh
     * @return cn.gtmap.realestate.common.core.domain.building.FwKDO
     * @description 根据LSZD 和 ZRZH  查询 FW_K表
     */
    @ResponseBody
    @RequestMapping("/querybylszdzrzh")
    public FwKDO queryFwKByLszdZrzh(@NotBlank(message = "隶属宗地") String lszd,
                          @NotBlank(message = "自然幢号") String zrzh){
        return fwKFeignService.queryFwKByLszdAndZrzh(lszd,zrzh,"");
    }


}
