package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.common.core.dto.BdcXxbdDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcXxbdQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXxbdFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/26
 * @description 信息比对
 */
@RestController
@RequestMapping("/rest/v1.0/xxbd")
public class BdcXxbdController {

    @Autowired
    BdcXxbdFeignService bdcXxbdFeignService;

    /**
     * @param  bdcXxbdQO 信息比对查询参数
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取信息比对结果
     */
    @GetMapping("")
    public BdcXxbdDTO xxbd(BdcXxbdQO bdcXxbdQO){
        return bdcXxbdFeignService.generateXxbdDTO(bdcXxbdQO);

    }
}
