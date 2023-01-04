package cn.gtmap.realestate.common.core.service.rest.register;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href ="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0, 2021/7/1
 * @description 盐城评价短信下发
 */
public interface BdcPjdxRestService{
    /**
     * 盐城评价短信下发
     *
     * @param processInsId 工作流实例ID
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/yc/pjdx", method = RequestMethod.POST)
    void queryPjdxMsg(@RequestParam(value = "processInsId") String processInsId,@RequestParam(value = "taskId") String taskId);
}
