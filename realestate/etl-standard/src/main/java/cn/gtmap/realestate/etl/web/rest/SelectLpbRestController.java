package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.core.qo.accept.LjzQO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.etl.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: realestate
 * @description: 查询楼盘表Rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-16 14:15
 **/
@Controller
@RequestMapping("/realestate-etl/rest/v1.0/lpb")
public class SelectLpbRestController extends BaseController {

    @Autowired
    FwLjzFeginService fwLjzFeginService;
    @Autowired
    BdcdyFeignService bdcdyFeignService;
    /**
     * @param ljzQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询展示楼盘表
     * @date : 2020/12/16 14:16
     */
    @GetMapping()
    @ResponseBody
    public Object listLpbByPage(@LayuiPageable Pageable pageable, LjzQO ljzQO) {
        ljzQO.setBdcdyh(StringUtils.deleteWhitespace(ljzQO.getBdcdyh()));
        ljzQO.setFwmc(StringUtils.deleteWhitespace(ljzQO.getFwmc()));
        ljzQO.setLszd(StringUtils.deleteWhitespace(ljzQO.getLszd()));
        ljzQO.setZldz(StringUtils.deleteWhitespace(ljzQO.getZldz()));
        return addLayUiCode(fwLjzFeginService.listLjzByPageJson(pageable, JSON.toJSONString(ljzQO)));
    }

    /**
     * @param bdcSlBdcdyhQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询户室信息
     * @date : 2020/12/17 8:56
     */
    @GetMapping("/hs")
    @ResponseBody
    public Object listHsByPage(@LayuiPageable Pageable pageable, BdcSlBdcdyhQO bdcSlBdcdyhQO) {
        bdcSlBdcdyhQO.setBdcdyh(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getBdcdyh()));
        bdcSlBdcdyhQO.setFjh(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getFjh()));
        bdcSlBdcdyhQO.setZl(StringUtils.deleteWhitespace(bdcSlBdcdyhQO.getZl()));
        return addLayUiCode(bdcdyFeignService.listScYcHsxxByPage(pageable, JSON.toJSONString(bdcSlBdcdyhQO)));
    }
}
