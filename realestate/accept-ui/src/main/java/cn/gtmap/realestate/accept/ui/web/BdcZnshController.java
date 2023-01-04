package cn.gtmap.realestate.accept.ui.web;


import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.init.znsh.BdcSjjyDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZjjgFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.xuancheng.BdcZnshFeignService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/28
 * @description 智能审核
 */
@RestController
@RequestMapping("/rest/v1.0/znsh")
public class BdcZnshController {

    @Autowired
    BdcZnshFeignService bdcZnshFeignService;

    /**
      * @param  xmid 项目ID
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 生成智能审核结果
      */
    @GetMapping("/{xmid}")
    public BdcSjjyDTO znsh(@PathVariable(value = "xmid") String xmid){
        return bdcZnshFeignService.znsh(xmid);
    }



}
