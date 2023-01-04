package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.portal.BdcZdpjDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.portal.ui.service.BdcPjgzService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BdcPjgzServiceImpl implements BdcPjgzService {

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    /**
     * 角色分组标识,默认筛选人员同组织下的角色
     */
    @Value("${forward.group:true}")
    private String forwardGroup;

    /**
     * 日志服务
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcPjgzServiceImpl.class);

    /**
     * 派件规则处理 <br/>
     * 根据平台参数处理，返回派件用户名
     *
     * @param bdcZdpjDTOS 自动派件对象
     * @return {String} 用户名，未匹配到对应用户返回 null
     */
    @Override
    public String pjgz(List<BdcZdpjDTO> bdcZdpjDTOS,String processInsId,String currentNodeName) {
        if (CollectionUtils.isEmpty(bdcZdpjDTOS)) {
            throw new NullPointerException("派件规则传入参数为空");
        }

        LOGGER.info("自动派件传入数据如下：自动派件对象{}工作流实例ID{}派件节点名称{}", JSON.toJSONString(bdcZdpjDTOS),processInsId,currentNodeName);
        //优先获取退回件原审核人员
        String username =getWwsqYshry(processInsId,currentNodeName);
        List<String> zdpjYhList =bdcZdpjDTOS.stream().filter(bdcZdpjDTO -> !StringUtils.equals(bdcZdpjDTO.getEnable(),"0")).map(BdcZdpjDTO::getUsername).collect(Collectors.toList());
        //如果自动派件人员包含对应人员，直接返回
        if(zdpjYhList.contains(username)){
            return username;
        }
        bdcZdpjDTOS.sort(Comparator.comparing(BdcZdpjDTO::getWeight).reversed());
        // 传入参数已经按照 sort 排序，故直接遍历
        for (BdcZdpjDTO bdcZdpjDTO : bdcZdpjDTOS) {
            // enable 0 表示请假，跳过请假人员
            if (StringUtils.equals(bdcZdpjDTO.getEnable(), "0")) {
                continue;
            }

            Integer dispatchNumber = bdcZdpjDTO.getDispatchNumber();
            Integer weight = bdcZdpjDTO.getWeight();
            // 权重数 > 已派件数，继续派件，否则按序遍历下个集合
            if (dispatchNumber < weight) {
                return bdcZdpjDTO.getUsername();
            }
        }

        // 未匹配到对应用户返回 null
        return null;
    }

    /**
      * @param processInsId 工作流实例ID
      * @param currentNodeName 派件节点名称
      * @return 获取外网申请退回件原审核人员
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    private String getWwsqYshry(String processInsId,String currentNodeName){
        if(StringUtils.isNotBlank(processInsId) &&StringUtils.isNotBlank(currentNodeName)){
            //获取同外网受理编号的其他项目
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listOtherXmWithSameSpxtywh(processInsId);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                //根据受理时间排序
                bdcXmDOList.sort(Comparator.comparing(BdcXmDO::getSlsj));
                String ygzlslid =bdcXmDOList.get(bdcXmDOList.size()-1).getGzlslid();

                if(StringUtils.isNotBlank(ygzlslid)) {
                    List<TaskData> listTask = processTaskClient.listProcessTask(ygzlslid);
                    //匹配节点,找到办理人员
                    for(TaskData taskData:listTask){
                        if(StringUtils.equals(taskData.getTaskName(),currentNodeName)){
                            LOGGER.info("自动派件gzlslid：{}获取外网申请退回件原审核人员：{}",processInsId,taskData.getTaskAssigin());
                            return taskData.getTaskAssigin();
                        }
                    }
                }
            }
        }
        return null;
    }
}
