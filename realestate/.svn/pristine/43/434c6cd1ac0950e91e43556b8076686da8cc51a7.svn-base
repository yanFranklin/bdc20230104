package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.clients.ElementClient;
import cn.gtmap.gtc.clients.OauthManagerClient;
import cn.gtmap.gtc.formclient.common.client.FormThirdResourceClient;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2019/3/5,1.0
 * @description 获取表单中心权限
 */
@Controller
@RequestMapping("/formCenter")
public class FormController extends BaseController {
    @Autowired
    FormThirdResourceClient formThirdResourceClient;
    @Autowired
    ElementClient elementCient;
    @Autowired
    OauthManagerClient oauthManagerClient;

    /**
     * @param formStateId 表单状态id
     * @param resourceName 资源名称
     * @return 表单元素权限
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取表单元素权限
     */
    @ResponseBody
    @PostMapping("")
    public Object queryFormState(String formStateId,String resourceName) {
        if(StringUtils.isBlank(formStateId)) {
            return null;
        } else {
            return formThirdResourceClient.getElementWithChildConfig(formStateId, resourceName);
        }
    }

    /**
     * @param moduleCode 页面编号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 获取模块的权限状态
     */
    @ResponseBody
    @GetMapping("/moduleAuthority/{moduleCode}")
    public Object queryModuleState(@PathVariable(name = "moduleCode") String moduleCode) {
        String currentUserName = userManagerUtils.getCurrentUserName();
        if (StringUtils.isBlank(currentUserName)) {
            throw new MissingArgumentException("无法获取到当前用户信息");
        }
        return elementCient.getAuthorities(currentUserName, moduleCode);
    }
}
