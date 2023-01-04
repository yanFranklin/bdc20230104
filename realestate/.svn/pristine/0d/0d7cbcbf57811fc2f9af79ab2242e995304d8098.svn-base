package cn.gtmap.realestate.common.core.service.rest.portal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/10/23
 * @description
 */
public interface BdcCheckRestService {
    /**
     * @param processKey 流程定义key
     * @param gzlslid    工作流实例ID
     * @param mbType     模板类型
     * @param taskid     任务ID
     * @return Object  验证结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 转发/退回规则验证
     */
    @PostMapping("/realestate-portal-ui/rest/v1.0/check/feign/gzyz/{processKey}/{gzlslid}")
    Object feignBdcgz(@PathVariable(name = "processKey") String processKey, @PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "mbtype") String mbType, @RequestParam(name = "taskid") String taskid);

    /**
     * 转发表单必填项验证
     *
     * @param formViewKey 表单formKey
     * @param gzlslid     工作流实例ID
     * @param taskId      任务实例ID
     * @return 验证结果
     */
    @PostMapping("/realestate-portal-ui/rest/v1.0/check/feign/gzyz/btxyz/{formViewKey}/{gzlslid}")
    Object feignBtxyz(@PathVariable(name = "formViewKey") String formViewKey, @PathVariable(name = "gzlslid") String gzlslid,
                      @RequestParam(name = "taskId", required = false) String taskId);

    /**
     * 南通特殊业务判断是否生成电子证照
     *
     * @param processInsId
     * @return 处理结果
     * @author <a href="mailto:lixin1@gtmap.cn>lixin</a>"
     */
    @GetMapping("/realestate-portal-ui/rest/v1.0/check/feign/gzyz/sfbj/dzzz/{processInsId}")
    boolean sfHyDzzz(@PathVariable("processInsId") String processInsId);
}
