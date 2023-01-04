package cn.gtmap.realestate.accept.ui.web.utils;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/23
 * @description
 */
@Controller
@RequestMapping("/uuid")
public class UuidController extends BaseController {
    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取UUID
     */
    @ResponseBody
    @GetMapping("")
    public String getUUid() {
        return UUIDGenerator.generate16();
    }
}
