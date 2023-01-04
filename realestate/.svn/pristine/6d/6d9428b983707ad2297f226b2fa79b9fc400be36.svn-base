package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.util.IPPortUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/11
 * @description
 */
@Controller
@RequestMapping("/realestate-accept-ui/index")
public class IndexController extends BaseController {

    /**
     * @param
     * @return
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description
     */
    @GetMapping("")
    public String index(Model model) {
        return "selectedInfo";
    }

    @GetMapping("/ip")
    @ResponseBody
    public String queryIp(HttpServletRequest httpServletRequest) {
        LOGGER.info("clientIp{}", IPPortUtils.getClientIp(httpServletRequest));
        LOGGER.info("localIp{}", IPPortUtils.getLocalIP());
        LOGGER.info("serverPath{}", IPPortUtils.serverPath());
        return "clientIp" + IPPortUtils.getClientIp(httpServletRequest) + "localIp" + IPPortUtils.getLocalIP() + "serverPath" + IPPortUtils.serverPath();
    }

}
