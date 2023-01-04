package cn.gtmap.realestate.common.core.service.rest.natural;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/13
 * @description 自然资源工作流事件
 */
public interface ZrzyWorkflowRestService {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 转发登簿事件
      */
    @PostMapping(value = "/realestate-natural/rest/v1.0/workflow/db")
    void zrzyDbEvent(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 转发办结事件
      */
    @PostMapping(value = "/realestate-natural/rest/v1.0/workflow/end")
    void endEvent(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);
}
