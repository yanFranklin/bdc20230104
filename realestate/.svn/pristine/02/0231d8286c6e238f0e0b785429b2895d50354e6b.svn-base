package cn.gtmap.realestate.register.web;

import cn.gtmap.gtc.starter.gscas.annotation.ModuleAuthority;
import cn.gtmap.realestate.register.web.main.BaseController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * .IndexController
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2018/6/22 14:36
 */
@Controller
@RequestMapping
public class IndexController extends BaseController {
    /**
     * @return String
     */
    @RequestMapping(value = "/abc/index", method = {RequestMethod.GET})
    @ModuleAuthority(path="/abc/index")
    public String index() {
        LOGGER.info("index....");
        return "index";
    }

    /**
     * @param authentication Authentication 认证
     * @return Object
     */
    @RequestMapping(value = "/checkUser", method = {RequestMethod.GET})
    @ResponseBody
    public Object getAuthUrl(Authentication authentication) {
        return authentication;
    }


}
