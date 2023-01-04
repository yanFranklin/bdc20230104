package cn.gtmap.realestate.common.core.service.rest.portal;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.portal.WorkFlowCommonDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程相关服务
 *
 * @author <a href="mailto:zedeqiang@gtmap.cn>zedq</a>"
 * @version 1.0, 2020/12/02
 */
public interface BdcWorkFlowRestService {

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @return java.lang.String
     * @description 删除业务
     */
    @PostMapping("/realestate-portal-ui/rest/v1.0/workflow/process-instances/feign/delete")
    void feignDeleteTask(@RequestBody List<WorkFlowCommonDTO> workFlowCommonDTOList);

    /**
     * @param workFlowCommonDTOList
     * taskId,processInstanceId必传
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 认领任务
     */
    @PostMapping("/realestate-portal-ui/rest/v1.0/workflow/process-instances/feign/claim")
    void feignClaimTask(@RequestBody List<WorkFlowCommonDTO> workFlowCommonDTOList);

    /**
     * @param workFlowCommonDTO
     * reason,processInstanceId必传
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 撤销任务
     */
    @PostMapping("/realestate-portal-ui/rest/v1.0/workflow/process-instances/feign/abandon")
    void abandonTask(@RequestBody WorkFlowCommonDTO workFlowCommonDTO);

    /**
     * @param gzlslid
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 是否可以撤销
     */
    @GetMapping("/realestate-portal-ui/rest/v1.0/workflow/process-instances/feign/isabandon")
    String isAbandon(@RequestParam(name = "gzlslid") String gzlslid);
}
