package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcByslDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlBysldjFeginService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlxxHxFeignService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/08/24.
 * @description Accept不予登记/不予受理服务
 */
@Controller
@Validated
@RequestMapping("/bysl")
public class BdcSlBysldjController extends BaseController {

    @Autowired
    BdcSlBysldjFeginService bdcSlBysldjFeginService;
    @Autowired
    BdcSlxxHxFeignService bdcSlxxHxFeignService;


    /**
     * @description 根据工作流实例ID获取不予受理信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    @ResponseBody
    @GetMapping("/byslxx")
    public Object queryBdcByslxx(@RequestParam("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失参数工作流实例ID");
        }
        return bdcSlBysldjFeginService.queryBdcByslDOByGzlslid(gzlslid);
    }


    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例ID删除不予受理信息
     */
    @ResponseBody
    @DeleteMapping("/byslxx/delbygzl")
    public int deleteBdcByslxxByGzlslid(@RequestParam("gzlslid") String gzlslid){
        if (StringUtils.isBlank(gzlslid)){
            throw new AppException("缺失参数工作流实例ID");
        }
        return bdcSlBysldjFeginService.deleteBdcByslDOByGzlslid(gzlslid);
    }

    /**
     * @param byslid 不予受理ID
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据不予受理ID删除不予受理信息
     */
    @ResponseBody
    @DeleteMapping("/byslxx/delbybysl")
    public int deleteBdcByslxxByByslid(@RequestParam("byslid") String byslid){
        if (StringUtils.isBlank(byslid)){
            throw new AppException("缺失参数工作流实例ID");
        }
        return bdcSlBysldjFeginService.deleteBdcByslDOByByslid(byslid);
    }

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例ID删除不予受理信息
     */
    @ResponseBody
    @PutMapping("/byslxx/savebygzl")
    public int saveBdcByslxxByGzlslid(@RequestBody String json, @RequestParam(name = "gzlslid") String gzlslid) throws Exception {
        int count = 0;
        if (StringUtils.isBlank(json)) {
            throw new AppException("缺失参数");
        }
        count = bdcSlBysldjFeginService.saveBdcByslDOByGzlslid(json);
        // 回写受理信息
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcSlxxHxFeignService.hxBdcSlxx(gzlslid);
        }
        return count;
    }
}
