package cn.gtmap.realestate.inquiry.service.huaian.thread;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcCstjxxVO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 不动产流程超时件统计任务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/11/11
 */
public class BdcLcCsjTjTask implements Runnable{

    /**
     * 超时件信息
     */
    private List<BdcCstjxxVO> bdcCstjxxVOList;
    /**
     * 查询参数信息
     */
    private List<RequestCondition> requestConditionList;
    /**
     * 组织信息
     */
    private OrganizationDto organizationDto;
    /**
     * 大云流程超时统计接口
     */
    private ProcessTaskCustomExtendClient processTaskCustomExtendClient;


    public BdcLcCsjTjTask(List<BdcCstjxxVO> bdcCstjxxVOList, List<RequestCondition> requestConditionList, OrganizationDto organizationDto,
                          ProcessTaskCustomExtendClient processTaskCustomExtendClient) {
        this.bdcCstjxxVOList = bdcCstjxxVOList;
        this.requestConditionList = requestConditionList;
        this.organizationDto = organizationDto;
        this.processTaskCustomExtendClient = processTaskCustomExtendClient;
    }

    @Override
    public void run() {
        RequestCondition orgCondition = new RequestCondition();
        orgCondition.setRequestKey("startUserDepId");
        orgCondition.setRequestJudge("in");
        orgCondition.setRequestValue(Arrays.asList(organizationDto.getId()));

        List<RequestCondition> param = new ArrayList<>(requestConditionList.size());
        param.addAll(requestConditionList);
        param.add(orgCondition);
        Page<Map<String, Object>> pagelist = processTaskCustomExtendClient.queryProcessInsWithProject(param, 0,  10, 0);
        BdcCstjxxVO bdcCstjxxVO = new BdcCstjxxVO();
        bdcCstjxxVO.setBmid(organizationDto.getId());
        bdcCstjxxVO.setBmmc(organizationDto.getName());
        if(pagelist.hasContent()){
            bdcCstjxxVO.setCsjsl(pagelist.getTotalElements());
        }else{
            bdcCstjxxVO.setCsjsl(0L);
        }
        bdcCstjxxVOList.add(bdcCstjxxVO);
    }
}
