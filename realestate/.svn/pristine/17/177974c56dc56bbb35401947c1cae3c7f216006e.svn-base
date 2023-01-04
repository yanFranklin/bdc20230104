package cn.gtmap.realestate.etl.web.forward;

import cn.gtmap.realestate.etl.service.HtbaService;
import cn.gtmap.realestate.etl.web.main.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: realestate
 * @description: 合同备案页面打开前置处理，url跳转
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-16 09:22
 **/
@Controller
@RequestMapping("/realestate-etl/rest/v1.0/htba")
public class HtbaForwardRestController extends BaseController {
    @Autowired
    HtbaService htbaService;

    /**
     * @param fwhsindex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 楼盘表页面打开前置处理
     * @date : 2020/12/16 9:33
     */

    @GetMapping("")
    @ResponseBody
    public String forwardHtbaHtml(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            return htbaService.forwardHtbaHtml(bdcdyh);
        }
        return null;
    }
}
