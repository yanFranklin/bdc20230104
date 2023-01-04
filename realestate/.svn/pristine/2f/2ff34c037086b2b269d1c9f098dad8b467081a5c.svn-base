package cn.gtmap.realestate.portal.ui.service.impl.workflow;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.NodeVariableDTO;
import cn.gtmap.gtc.workflow.domain.manage.OpinionDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.enums.task.CommentType;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYjxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbCommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbDeleteRequestParam;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYjxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.SjrptFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.hefei.BdcBjbhFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.portal.ui.core.dto.EventDTO;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@ConfigurationProperties("forward")
public class BdcWorkFlowAbstactServiceImpl_hefei extends BdcWorkFlowAbstactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowAbstactServiceImpl_hefei.class);

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;

    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;

    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    private BdcBjbhFeignService bdcBjbhFeignService;

    @Autowired
    private SjrptFeignService sjrptFeignService;

    /**
     * 转发节点配置
     */
    private Map<String, String> defalutNode;

    @PostConstruct
    public void register() {
        BdcWorkFlowServiceFactory.registerService("hefei", this);
    }

    @Override
    public String isAbandon(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOS)) {
            for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
                // 查封、解封类流程不做撤销申请业务
                if (CommonConstantUtils.QLLX_CF.equals(bdcXmDTO.getQllx())) {
                    return StringUtils.EMPTY;
                }
            }
            return "success";
        } else {
            // 一窗受理没有登记数据
            return StringUtils.EMPTY;
        }
    }

    /**
     * 组织转发参数
     *
     * @param taskId
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     * @version 10:56 上午 2020/8/10
     */
    @Override
    public List<NodeVariableDTO> getNodeVariableDTOS(String taskId) {
        List<NodeVariableDTO> params = Lists.newArrayList();
        TaskData taskData = processTaskClient.getTaskById(taskId);
        List<Map<String, Object>> processInsCustomExtends = processInsCustomExtendClient.getProcessInsCustomExtend(taskData.getProcessInstanceId());
        // 从大云回写字段中获取 sfzdzfdb
        if (CollectionUtils.isNotEmpty(processInsCustomExtends) && null != processInsCustomExtends.get(0)) {
            Map<String, Object> stringObjectMap = processInsCustomExtends.get(0);
            Object sfzdzfdb = stringObjectMap.get("SFZDZFDB");
            if (sfzdzfdb instanceof String && StringUtils.isNotBlank((String) sfzdzfdb)) {
                NodeVariableDTO nodeVariableDTO = new NodeVariableDTO();
                nodeVariableDTO.setKey("SFZDZFDB");
                nodeVariableDTO.setValue(sfzdzfdb);
                params.add(nodeVariableDTO);
            }
        }
        // 其他件转发，走默认配置
        if (CollectionUtils.isEmpty(processInsCustomExtends) || CollectionUtils.isEmpty(params)) {
            String param = MapUtils.getString(defalutNode, taskData.getProcessKey());
            if (StringUtils.isNotBlank(param)) {
                String[] values = StringUtils.split(param, CommonConstantUtils.ZF_YW_DH);
                for (String value : values) {
                    NodeVariableDTO nodeVariableDTO = new NodeVariableDTO();
                    nodeVariableDTO.setKey("SFZDZFDB");
                    nodeVariableDTO.setValue(value);
                    params.add(nodeVariableDTO);
                }
            }
        }
        LOGGER.info("组织获取下个节点信息网关所需参数：{}", JSON.toJSONString(params));
        return params;
    }


    /**
     * 删除前执行事件
     * @param eventDTO
     * @param bdcXmDO
     * @param userName
     */
    @Override
    public void processBeforeDelete(EventDTO eventDTO, BdcXmDO bdcXmDO, String userName) {
        // 删件时新增接口调用：4.3.4受理，推送不予受理信息给政务网,删件是blzt传0
        if (Objects.isNull(bdcXmDO)) {
            throw new AppException("合肥删除前执行事件，不动产项目为空");
        }
        //  政务取号成功推送,取号不成功不推送
        if (StringUtils.isNotBlank(bdcXmDO.getZfxzspbh())) {
            LOGGER.info("合肥删除前执行申报登记_删除信息推送，工作流实例id：{}，政府行政审批编号：{}", bdcXmDO.getGzlslid(), bdcXmDO.getZfxzspbh());
            bdcBjbhFeignService.sbdjSlxx(bdcXmDO.getGzlslid(), "0");
        } else {
            LOGGER.info("合肥删除前未政务推送，工作流实例id：{}", bdcXmDO.getGzlslid());
        }
        // 若金融平台推送的业务被认领删除后主动调“驳回互联网申请”接口，若领证方式是EMS的，登记系统获取到ems订单号，则调“推送ems信息”接口推送数据
        if (CommonConstantUtils.SPLY_SJYPT.equals(bdcXmDO.getSply()) && StringUtils.isNotBlank(bdcXmDO.getSpxtywh())){
            LOGGER.info("合肥删除前驳回互联网申请，工作流实例id：{}", bdcXmDO.getGzlslid());
            sjrptDeleteMethod(eventDTO,bdcXmDO,userName);
        }

    }

    /**
     * 省金融平台业务删除时接口调用
     *
     * @param eventDTO
     * @param bdcXmDO
     * @param userName
     */
    private void sjrptDeleteMethod(EventDTO eventDTO, BdcXmDO bdcXmDO, String userName) {
        // 驳回互联网申请
        sjrptFeignService.bhHlwSq(bdcXmDO.getSpxtywh(),eventDTO.getReason(),bdcXmDO.getGzlslid());
    }

    /**
     * 删除后执行事件
     *
     *
     *
     * @param bdcXmDO
     * @param userName
     * @param reason
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     * @version 10:56 上午 2020/8/10
     */
    @Override
    public void processAfterDelete(BdcXmDO bdcXmDO, String userName, String reason) {
        LOGGER.info("合肥删除操作日志保存处理");
        // 获取流程审批意见
        StringJoiner joiner = new StringJoiner(";");
        joiner.add(userName + ":" + reason);
        bdcCzrzFeignService.addScCzrzWithOpinionWithXmxx(bdcXmDO.getGzldyid(), joiner.toString(),bdcXmDO);
        LOGGER.info("合肥删除操作日志保存处理结束");
        LOGGER.info("删除任务回调合肥一窗通办-驳回网厅申请件接口开始");
        if (bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getSpxtywh()) && (CommonConstantUtils.SPLY_YCTB.equals( bdcXmDO.getSply()) || CommonConstantUtils.SPLY_YCTB_SS.equals( bdcXmDO.getSply()))){
            YctbDeleteRequestParam yctbDeleteRequestParam = YctbDeleteRequestParam.YctbDeleteRequestParamBuilder.anYctbDeleteRequestParam().withYwh(bdcXmDO.getSpxtywh())
                    .withBhr(userName)
                    .withBhyy(reason)
                    .withQxdm(bdcXmDO.getQxdm())
                    .withBhsj(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                            .format(new Date())).build();
            LOGGER.info("删除任务回调合肥一窗通办-驳回网厅申请件接口开始");
            Object response = exchangeInterfaceFeignService.requestInterface("yctb_delete_task", yctbDeleteRequestParam);
            LOGGER.info("删除任务回调合肥一窗通办-驳回网厅申请件接口返回结果：{}", response);
            if(response != null){
                YctbCommonResponse<Object> yctbResponseDTO  = JSON.parseObject(JSON.toJSONString(response), new TypeReference<YctbCommonResponse<Object>>() {});
                if(!yctbResponseDTO.getCode().equals(200)){
                    throw new MissingArgumentException("调用合肥一窗通办-驳回网厅申请件接口失败:" + yctbResponseDTO.toString());
                }
            }else {
                throw new MissingArgumentException("调用合肥一窗通办-驳回网厅申请件接口失败:" + JSON.toJSONString(response));
            }
        }
    }

    public Map<String, String> getDefalutNode() {
        return defalutNode;
    }

    public void setDefalutNode(Map<String, String> defalutNode) {
        this.defalutNode = defalutNode;
    }
}
