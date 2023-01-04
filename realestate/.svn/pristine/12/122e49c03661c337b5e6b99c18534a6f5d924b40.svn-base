package cn.gtmap.realestate.common.core.service.rest.accept;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2022/05/23
 * @description Accept受理工作流rest服务接口
 */
public interface AcceptWorkFlowRestService {

    /**
     * 更新需要收费的月结收费信息中的发票号为“月结”
     * @param processInsId    工作流实例id
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @RequestMapping(value = "/realestate-accept/rest/v1.0/workflow/sfxx/gxyjfph", method = RequestMethod.POST)
    void updateYjjffph(@RequestParam(value = "processInsId") String processInsId);

}
