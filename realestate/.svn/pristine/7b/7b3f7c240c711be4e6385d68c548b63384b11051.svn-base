package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/1/21
 * @description 证书系统用于工作流转发的服务
 */
public interface CertificateWorkflowRestService {

    /**
     * @param processInsId  工作流实例
     * @param currentUserName 当前用户名/账户名
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证人信息
     */

    @RequestMapping(value = "/realestate-certificate/rest/v1.0/workflow/fzr", method = RequestMethod.PUT)
    int updateFzr(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param processInsId    工作流实例ID
     * @param currentUserName 当前用户名/账户名
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存缮证人，缮证时间
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/workflow/szr", method = RequestMethod.PUT)
    BdcSzxxVO updateSzr(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param processInsId
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 归档不动产信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gd/gdBdcXx", method = RequestMethod.POST)
    void gdBdcXx(@RequestParam(value = "processInsId") String processInsId,@RequestParam(value="currentUserName")String currentUserName);
}
