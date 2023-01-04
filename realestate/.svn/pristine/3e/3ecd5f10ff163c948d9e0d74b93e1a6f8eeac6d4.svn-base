package cn.gtmap.realestate.exchange.web.main;

import cn.gtmap.gtc.clients.ElementClient;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-15
 * @description  获取页面权限
 */
@Controller
@RequestMapping("/realestate-exchange/authority")
public class AuthorityController {

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private ElementClient elementCient;

    /**
     * @param moduleCode 页面编号
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取模块的权限状态
     */
    @ResponseBody
    @GetMapping("/{moduleCode}")
    public Object queryModuleState(@PathVariable(name = "moduleCode") String moduleCode) {
        String currentUserName = userManagerUtils.getCurrentUserName();
        if (StringUtils.isBlank(currentUserName)) {
            throw new MissingArgumentException("无法获取到当前用户信息");
        }
        return elementCient.getAuthorities(currentUserName, moduleCode);
    }

}
