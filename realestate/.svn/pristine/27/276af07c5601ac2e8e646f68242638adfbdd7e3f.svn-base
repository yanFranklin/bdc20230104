package cn.gtmap.realestate.exchange.service.impl.inf.dy;

import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.BackTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.exchange.core.dto.wwsq.yhxt.YhxtResponseDTO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.dy.DyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-08
 * @description
 */
@Service
public class DyServiceImpl implements DyService {

    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private CommonService commonService;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(DyServiceImpl.class);

    @Override
    public YhxtResponseDTO zdthBySlbh(String slbh) {
        YhxtResponseDTO yhxtResponseDTO = new YhxtResponseDTO();
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("受理编号不能为空");
        }
        List<BdcXmDO> xmDOList = commonService.listBdcXmBySlbh(slbh);
        if (CollectionUtils.isEmpty(xmDOList) || StringUtils.isBlank(xmDOList.get(0).getGzlslid())) {
            throw new AppException("找不到当前受理编号对应的项目");
        }
        List<TaskData> listTask = processTaskClient.processRunningTasks(xmDOList.get(0).getGzlslid());
        if (CollectionUtils.isNotEmpty(listTask)) {
            List<BackTaskDto> backTaskDtoList = flowableNodeClient.getBackUserTasks(listTask.get(0).getTaskId());
            if (CollectionUtils.isNotEmpty(backTaskDtoList)) {
                taskHandleClient.back(backTaskDtoList);
            } else {
                yhxtResponseDTO.setCode("500");
                yhxtResponseDTO.setMsg("无可退回节点");
            }
        } else {
            yhxtResponseDTO.setCode("500");
            yhxtResponseDTO.setMsg("无活跃节点");
        }
        return yhxtResponseDTO;
    }
}
