package cn.gtmap.realestate.portal.ui.service.impl.workflow;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.NodeVariableDTO;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDsfdjJhxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZjjgFeignService;
import cn.gtmap.realestate.common.core.service.feign.etl.HlwYzFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.etl.HxdjxxbVO;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.portal.ui.core.dto.EventDTO;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 常州工作流实现类
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 11:09 上午 2020/6/12
 */
@Service
public class BdcWorkFlowAbstactServiceImpl_changzhou extends BdcWorkFlowAbstactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowAbstactServiceImpl_changzhou.class);
    private static final String SUCCESS = "success";
    private static final String WW_UPDATE_SLZT = "wwsqupdateslzt";
    private static final String NOTICE_YDJX = "noticeydjxt";
    // 更新法院子系统创建状态
    private static final String UPDATEFYCJZT = "updateCjywzt";

    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private BdcDsfdjJhxxFeignService bdcDsfdjJhxxFeignService;
    @Autowired
    private HlwYzFeignService hlwYzFeignService;

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    BdcZjjgFeignService bdcZjjgFeignService;
    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    //配置办结不需要推送的工作流定义id
    @Value("#{'${fdjywlc.processEnd.noPush.gzldyid:}'.split(',')}")
    private List<String> noPushProcessDefKey;


    @PostConstruct
    public void register() {
        BdcWorkFlowServiceFactory.registerService("changzhou", this);
    }

    @Override
    public String isAbandon(String gzlslid) {
        // 常州首节点可以显示撤销按钮
        return StringUtils.EMPTY;
    }

    @Override
    public void abandonTask(WorkFlowDTO workFlowDTO) {
        // 预审核流程不通过需要将状态和关键字推送到互联网 +
//        JSONObject jsonObject = createRequestParam(workFlowDTO.getProcessInstanceId(), workFlowDTO.getTaskId(),
//                workFlowDTO.getReason(), HlwSlztEnum.ABANDON.getSlzt());
        LOGGER.warn("预审核不通过, 工作流实例Id:[{}], 退回原因:[{}], 退回人:[{}]", workFlowDTO.getProcessInstanceId(), workFlowDTO.getReason(), HlwSlztEnum.ABANDON.getSlzt());
//        exchangeInterfaceFeignService.requestInterface(WW_UPDATE_SLZT, jsonObject);
        HxdjxxbVO hxdjxxbVO = new HxdjxxbVO();
        hxdjxxbVO.setGzlslid(workFlowDTO.getProcessInstanceId());
        hxdjxxbVO.setThyy(workFlowDTO.getReason());
        hxdjxxbVO.setThrmc(userManagerUtils.getUserAlias());
        hxdjxxbVO.setSlzt(HlwSlztEnum.ABANDON.getSlzt());
        hlwYzFeignService.hxDjxx(hxdjxxbVO);
        if (StringUtils.isNotBlank(workFlowDTO.getProcessInstanceId())) {
            //第三方件,记录外网退件日志(方法内有判断是否为外部件)
            bdcCzrzFeignService.initBdcCzrz(workFlowDTO.getProcessInstanceId(), workFlowDTO.getReason(), CommonConstantUtils.CZRZ_CZLX_WWTJ, userManagerUtils.getCurrentUserName());
        }


    }

    @Override
    public void processEnd(String taskId) {
        TaskData taskData = processTaskClient.getTaskById(taskId);
        if (StringUtils.isBlank(taskData.getProcessInstanceId())) {
            throw new AppException("未获取到 taskid:" + taskId + "对应的 processinsid");
        }
        List<String> fdjywlcList = bdcZjjgFeignService.getFdjywlcDyidList("");
        if (CollectionUtils.isNotEmpty(fdjywlcList) && fdjywlcList.contains(taskData.getProcessKey()) || CollectionUtils.isNotEmpty(noPushProcessDefKey) && noPushProcessDefKey.contains(taskData.getProcessKey())) {
            //非登记业务无需执行通知接口
            return;

        }
        // 常州目前流程就存量房转移 和 商品房转移，所以默认全部调用接口
        JSONObject jsonObject = createRequestParam(taskData.getProcessInstanceId(), taskData.getTaskId(),
                taskData.getReason(), HlwSlztEnum.PASS.getSlzt());
        // 先生成数据
        bdcDsfdjJhxxFeignService.initDsfdjJhxx(taskData.getProcessInstanceId());
        LOGGER.warn("预审核通过, 推送数据到互联网+beanName:[{}], requestObject:[{}]", WW_UPDATE_SLZT, jsonObject);
        // 预审核流程通过需要将状态和关键字推送到互联网 +
        exchangeInterfaceFeignService.requestInterface(WW_UPDATE_SLZT, jsonObject);

        LOGGER.warn("调用 Exchange 通知原登记系统 beanName:[{}], requestObject:[{}]", NOTICE_YDJX, jsonObject);
        exchangeInterfaceFeignService.requestInterface(NOTICE_YDJX, jsonObject);
    }

    /**
     * 生成请求需要的参数
     *
     * @param processInsId processInsId
     * @param taskId       taskid
     * @param opinion      原因「删除，废弃等原因」
     * @param isDelete     互联网受理状态
     * @return JSONObject 请求参数
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private JSONObject createRequestParam(String processInsId, String taskId, String opinion, String isDelete) {
        JSONObject object = new JSONObject();
        object.put("processInsId", processInsId);
        object.put("taskId", taskId);
        object.put("opinion", opinion);
        object.put("isDelete", isDelete);
        object.put("czrxm", userManagerUtils.getUserAlias());
        return object;
    }

    /**
     * 组织转发参数
     *
     * @param taskId 任务信息
     * @author <a href="mailto:yaoyi@gtmap.com">yaoyi</a>
     * @version 10:56 上午 2021/5/21
     */
    @Override
    public List<NodeVariableDTO> getNodeVariableDTOS(String taskId) {
        List<NodeVariableDTO> params = Lists.newArrayList();

        String redisKey = Constants.TASK_ZFCS_REDIS_PREFIX + taskId;
        // 获取页面设置在redis中的转发参数
        Map paramsMap = redisUtils.getHash(redisKey);
        NodeVariableDTO nodeVariableDTO = new NodeVariableDTO();
        nodeVariableDTO.setKey("SFSQSC");
        nodeVariableDTO.setValue("0");
        params.add(nodeVariableDTO);
        if (MapUtils.isNotEmpty(paramsMap)) {
            try {
                if (paramsMap.containsKey("SFSQSC")) {
                    nodeVariableDTO.setValue(paramsMap.get("SFSQSC"));
                }
            } finally {
                redisUtils.deleteKey(redisKey);
            }
        }
        return params;
    }


    @Override
    public void processBeforeDelete(EventDTO eventDTO, BdcXmDO bdcXmDO, String userName) {
        if (StringUtils.isBlank(eventDTO.getGzlslid())) {
            throw new MissingArgumentException("删除前方法未传入 gzlslid");
        }
        LOGGER.info("常州删除操作日志保存处理");
        // 获取流程审批意见
        StringJoiner joiner = new StringJoiner(";");
        joiner.add(userName + ":" + eventDTO.getReason());
        if (bdcXmDO != null) {
            bdcCzrzFeignService.addScCzrzWithOpinionWithXmxx(eventDTO.getGzlslid(), joiner.toString(), bdcXmDO);
            LOGGER.info("常州删除操作日志保存处理结束");
        } else {
            LOGGER.info("数据异常，gzlslid：" + eventDTO.getGzlslid() + "未查询到对应的项目数据");
        }
        //判断法院的件
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setGzlslid(eventDTO.getGzlslid());
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(xmQO);

        Integer sply = 0;
        if (CollectionUtils.isNotEmpty(bdcXmDOS)){
            sply = bdcXmDOS.get(0).getSply();
        }

        if (CommonConstantUtils.SPLY_FY.equals(sply) && StringUtils.isNotBlank(bdcXmDOS.get(0).getSpxtywh())) {
            HashMap<String, String> ywcjzt = new HashMap<>();
            ywcjzt.put("cxkzdh", bdcXmDOS.get(0).getSpxtywh());
            ywcjzt.put("processId", eventDTO.getGzlslid());
            exchangeInterfaceFeignService.requestInterface(UPDATEFYCJZT, ywcjzt);
        }
    }
}
