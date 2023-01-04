package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.SZdBdcdyFwlxDO;
import cn.gtmap.realestate.common.core.domain.building.SZdDldmDO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLxBgFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwhsBgFeignService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/16
 * @description 不动产单元房屋类型变更
 */
@Controller
@Validated
@RequestMapping("bdcdyfwlxbg")
public class BdcdyFwlxBgController extends BaseController {

    @Autowired
    private FwLjzFeginService fwLjzFeginService;
    @Autowired
    private FwLxBgFeignService fwLxBgFeignService;
    @Autowired
    private FwhsBgFeignService fwhsBgFeignService;

    /**
     * @param model
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 不动产单元房屋类型变更页面
     */
    @RequestMapping("/view")
    public String list(Model model,String fwDcbIndex,String qjgldm) {
        FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwDcbIndex,qjgldm);
        if (fwLjzDO != null && StringUtils.isNotBlank(fwLjzDO.getBdcdyfwlx())) {
            model.addAttribute("yfwlx", fwLjzDO.getBdcdyfwlx());
            model.addAttribute("fwDcbIndex", fwDcbIndex);
            model.addAttribute("lszd",fwLjzDO.getLszd());
            model.addAttribute("fwXmxxIndex",fwLjzDO.getFwXmxxIndex());
            model.addAttribute("qjgldm",qjgldm);
        }
        return "fwlx/bdcdyFwlxBg";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwlxBgRequestDTO
     * @param check
     * @return void
     * @description
     */
    @RequestMapping("/fwlxbg")
    public void fwLxBg(FwlxBgRequestDTO fwlxBgRequestDTO, boolean check) {
        if (check) {
            String bgbh = fwhsBgFeignService.maxBgbh();
            if (StringUtils.isNotBlank(bgbh)) {
                fwlxBgRequestDTO.setBgbh(bgbh);
            }
        }
        fwLxBgFeignService.fwLxBg(fwlxBgRequestDTO);
    }

    /**
     * 获得房屋户室信息所需要的字典项
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mapszd")
    public Map mapsFwhsZd() {
        return getZdList(new Class[]{SZdBdcdyFwlxDO.class, SZdDldmDO.class,});
    }

}