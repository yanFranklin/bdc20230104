package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;

import cn.gtmap.realestate.common.core.service.feign.register.RegisterWorkflowFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/03/17/11:13
 * @Description: 档案交接流程Controller
 */
@RestController
@RequestMapping("/rest/v1.0/changzhou/dajjlc")
public class BdcDajjLcController extends BaseController {

    @Autowired
    RegisterWorkflowFeignService registerWorkflowFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @PostMapping("/cjdajj")
    public void cjdajjlc(@RequestParam(name = "gzlslid") String gzlslid) throws Exception {
        String currentUserName = userManagerUtils.getCurrentUserName();
        registerWorkflowFeignService.cjDajjLc(gzlslid,currentUserName);
    }
}
