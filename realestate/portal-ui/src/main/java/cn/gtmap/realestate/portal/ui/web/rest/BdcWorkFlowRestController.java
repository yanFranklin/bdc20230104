package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.dto.portal.WorkFlowCommonDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.portal.BdcWorkFlowRestService;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">zedq</a>
 * @version 1.3, 2019/5/13
 * @description 不动产系统打印数据源配置服务接口
 */
@RestController
@Api(tags = "不动产系统打印数据源配置服务接口")
public class BdcWorkFlowRestController extends BaseController implements BdcWorkFlowRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowRestController.class);

    @Autowired
    private BdcWorkFlowController bdcWorkFlowController;

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [workFlowCommonDTOList]
     * @return cn.gtmap.realestate.common.core.dto.BdcCommonResponse
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/rest/v1.0/workflow/process-instances/feign/delete")
    @LogNote(value = "删除任务", action = Constants.LOG_ACTION_TASK_DELETE, custom = LogConstans.LOG_TYPE_LCSC)
    public void feignDeleteTask(@RequestBody List<WorkFlowCommonDTO> workFlowCommonDTOList) {
        LOGGER.info("fegin服务调用删除任务开始");
        if (CollectionUtils.isEmpty(workFlowCommonDTOList)) {
            LOGGER.error("流程信息列表为空，无法执行删除任务！");
            throw new AppException("流程信息列表为空，无法执行删除任务！");
        }
        List<WorkFlowDTO> workFlowDTOList = new ArrayList<>();
        for (WorkFlowCommonDTO workFlowCommonDTO : workFlowCommonDTOList) {
            WorkFlowDTO workFlowDTO = new WorkFlowDTO();
            BeanUtils.copyProperties(workFlowCommonDTO, workFlowDTO);
            workFlowDTOList.add(workFlowDTO);
        }
        try{
            bdcWorkFlowController.deleteTask(workFlowDTOList);
        } catch (Exception e) {
            LOGGER.error("删除业务异常！", e);
            throw new AppException(e.getMessage());
        }
        LOGGER.info("删除成功，fegin服务调用删除任务结束");
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/rest/v1.0/workflow/process-instances/feign/claim")
    public void feignClaimTask(@RequestBody List<WorkFlowCommonDTO> workFlowCommonDTOList){
        LOGGER.info("fegin服务调用认领任务开始");
        if (CollectionUtils.isEmpty(workFlowCommonDTOList)) {
            LOGGER.error("流程信息列表为空，无法执行认领任务！");
            throw new AppException("流程信息列表为空，无法执行认领任务！");
        }
        List<WorkFlowDTO> workFlowDTOList = new ArrayList<>();
        for (WorkFlowCommonDTO workFlowCommonDTO : workFlowCommonDTOList) {
            WorkFlowDTO workFlowDTO = new WorkFlowDTO();
            BeanUtils.copyProperties(workFlowCommonDTO, workFlowDTO);
            workFlowDTOList.add(workFlowDTO);
        }
        try{
            bdcWorkFlowController.claimTask(workFlowDTOList);
        } catch (Exception e) {
            LOGGER.error("认领业务异常！", e);
            throw new AppException(e.getMessage());
        }
        LOGGER.info("认领成功，fegin服务调用认领任务结束");

    }


    /**
     * @param workFlowCommonDTO reason gzlslid 为必填项
     * @return
     * @author <a href ="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 撤销流程
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/rest/v1.0/workflow/process-instances/feign/abandon")
    public void abandonTask(@RequestBody WorkFlowCommonDTO workFlowCommonDTO) {
        if (StringUtils.isBlank(workFlowCommonDTO.getReason()) && StringUtils.isBlank(workFlowCommonDTO.getProcessInstanceId())) {
            throw new MissingArgumentException("撤销原因和流程实例 ID 不能为空！");
        }
        WorkFlowDTO workFlowDTO = new WorkFlowDTO();
        BeanUtils.copyProperties(workFlowCommonDTO, workFlowDTO);
        try{
            bdcWorkFlowController.abandonTask(workFlowDTO);
        } catch (Exception e) {
            LOGGER.error("撤销业务异常！", e);
            throw new AppException(e.getMessage());
        }
        LOGGER.info("工作流实例ID{}撤销成功，fegin服务调用认领任务结束", workFlowCommonDTO.getProcessInstanceId());
    }

    /**
     * @param gzlslid 为必填项
     * @return
     * @author <a href ="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 是否可以撤销
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/rest/v1.0/workflow/process-instances/feign/isabandon")
    public String isAbandon(@RequestParam(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("流程实例 ID 不能为空！");
        }
        String isAbandon = "";
        try{
            isAbandon = bdcWorkFlowController.isAbandon(gzlslid);
        } catch (Exception e) {
            LOGGER.error("撤销业务异常！", e);
            throw new AppException(e.getMessage());
        }
        return isAbandon;
    }
}
