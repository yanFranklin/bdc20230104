package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.core.dto.building.TuxknrResponseDTO;
import cn.gtmap.realestate.common.core.service.feign.building.TuxknrZdtFeignService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-24
 * @description 南通 处理宗地图
 */
@Controller
@RequestMapping("/tuxknrzdt")
public class TuxknrZdtController {

    @Autowired
    private TuxknrZdtFeignService tuxknrZdtFeignService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return map
     * @description 宗地图
     */
    @ResponseBody
    @RequestMapping("/listbybdcdyh")
    public List<TuxknrResponseDTO> listbybdcdyh(@NotBlank String bdcdyh) {
        // todo 待确认 是否可以使用BDCDYH截取获取DJH
        String djh="";
        if(StringUtils.isNotBlank(bdcdyh)){
            djh=bdcdyh.substring(0,19);
        }
        return tuxknrZdtFeignService.listTuxknrByDjh(djh);
    }
}
