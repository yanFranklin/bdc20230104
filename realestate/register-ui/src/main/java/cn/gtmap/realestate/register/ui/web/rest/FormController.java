package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.clients.ElementClient;
import cn.gtmap.gtc.formclient.common.client.FormThirdResourceClient;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">gaolining</a>
 * @version 2019/3/13,1.0
 * @description 表单中心controller
 */
@RestController
@RequestMapping("/rest/v1.0/form")
public class FormController extends BaseController {
    @Autowired
    FormThirdResourceClient formThirdResourceClient;

    @Autowired
    ElementClient elementCient;
    /**
     * @param formStateId 表单ID
     * @return Object 表单权限状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取表单中心的权限状态
     */
    @ResponseBody
    @GetMapping("/{formStateId}/{resourceName}")
    public Object queryFormState(@PathVariable(name = "formStateId") String formStateId, @PathVariable(name = "resourceName") String resourceName) {
        if (StringUtils.isBlank(formStateId) || StringUtils.isBlank(resourceName)) {
            throw new MissingArgumentException("缺失formStateId或resourceName值!");
        }
        return formThirdResourceClient.getElementWithChildConfig(formStateId, resourceName);

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
