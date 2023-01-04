package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.core.service.feign.building.BdcdyhFeignService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/11
 * @description 生成不动产单元号
 */
@Controller
@RequestMapping("bdcdyh")
public class BdcdyhController{

    @Autowired
    BdcdyhFeignService bdcdyhFeignService;

    /**
     * 获得逻辑幢主键获取不动产单元号
     *
     * @param fwDcbIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createbdcdyhbyfwdcbindex")
    public String creatFwBdcdyhByFwDcbIndex(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex) {
        return bdcdyhFeignService.creatFwBdcdyhByFwDcbIndex(fwDcbIndex);
    }

    /**
     * 获得房屋信息主键获取不动产单元号
     *
     * @param fwXmxxIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createbdcdyhbyfwxmxxindex")
    public String createBdcdyhByFwxmXxIndex(@NotBlank(message = "项目信息主键不能为空") String fwXmxxIndex) {
        return bdcdyhFeignService.creatXmxxBdcdyhByFwXmxxIndex(fwXmxxIndex);
    }

    /**
     * 获得房屋信息主键获取不动产单元号
     *
     * @param djh
     * @param zrzh
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/creatfwbdcdyhbydjhandzrzh")
    public String creatFwBdcdyhByDjhAndZrzh(@NotBlank(message = "地籍号不能为空") String djh,
                                            @NotBlank(message = "自然幢号不能为空") String zrzh) {
        return bdcdyhFeignService.creatFwBdcdyhByDjhAndZrzh(djh, zrzh);
    }

}
