package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.register.RegisterWorkflowFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/15
 * @description 发证记录补打
 */
@RestController
@RequestMapping(value = "/rest/v1.0/fzjlbd")
public class BdcFzjlbdController extends BaseController {
    @Autowired
    private RegisterWorkflowFeignService registerWorkflowFeignService;
    
    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>/dbxx/ajzt
     * 更新案件状态为2已完成状态
     * @param gzlslid
     */
    @GetMapping(value = "/bj")
    @ResponseStatus(HttpStatus.OK)
    public void changeAjzt(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)){
            throw new AppException("工作流实例id不能为空");
        }
        registerWorkflowFeignService.changeAjzt(gzlslid);
    }
}
