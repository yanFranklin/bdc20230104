package cn.gtmap.realestate.portal.ui.service.impl.workflow;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.NodeVariableDTO;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.core.dto.EventDTO;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowAbstactService;
import cn.gtmap.realestate.portal.ui.service.impl.BdcWorkFlowServiceFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * nantong工作流实现类
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 11:09 上午 2020/6/12
 */
@Service
public class BdcWorkFlowAbstactServiceImpl_nantong extends BdcWorkFlowAbstactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWorkFlowAbstactServiceImpl_nantong.class);
    private static final String ZSYHXX_DELETE = "zsyhxx_delete";
    private static final String ZSYH = "ZSYH";
    private static final String SUCCESS_CODE = "1";
    private static final String SF_F = "0";
    private static final String VERSION = "nantong";
    private static final String HAIMEN = "haimen";

    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;
    /**
     * 南通抵押权人接入互联网+ 后认领需要提示信息
     */
    @Value("#{'${forward.sfbj.gzldyids:}'.split(',')}")
    private List<String> sfbjProcessKeys;
    /**
     * 南通特殊流程转发是否办结，验证节点
     */
    @Value("#{'${forward.sfbj.yzjd:}'.split(',')}")
    private List<String> yzjds;

    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private BdcShxxFeignService bdcShxxFeignService;

    @PostConstruct
    public void register() {
        BdcWorkFlowServiceFactory.registerService(VERSION, this);
        BdcWorkFlowServiceFactory.registerService(HAIMEN, this);
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
        NodeVariableDTO nodeVariableDTO = new NodeVariableDTO();
        nodeVariableDTO.setKey("sfzdbj");
        nodeVariableDTO.setValue(this.sfJrHlw(taskId));
        params.add(nodeVariableDTO);
        LOGGER.info("组织获取下个节点信息网关所需参数：{}", JSON.toJSONString(params));
        return params;
    }

    /**
     * 判断流程的权利人是否接入互联网+
     *
     * @param taskId taskId
     * @return 0：未接入；1：接入
     */
    public String sfJrHlw(String taskId) {
        if (CollectionUtils.isEmpty(sfbjProcessKeys)) {
            return SF_F;
        }

        TaskData taskData = processTaskClient.getTaskById(taskId);

        if (!yzjds.contains(taskData.getTaskName())) {
            return SF_F;
        }

        if (!sfbjProcessKeys.contains(taskData.getProcessKey())) {
            return SF_F;
        }

        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(taskData.getProcessInstanceId());
        if (CollectionUtils.isEmpty(bdcXmDTOS) || null == bdcXmDTOS.get(0)) {
            return SF_F;
        }

        List<BdcQlrDO> qlrDOList = Lists.newArrayList();
        for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmDTO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> qlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(qlrDOS)) {
                qlrDOList.addAll(qlrDOS);
            }
        }

        if (CollectionUtils.isEmpty(qlrDOList)) {
            return SF_F;
        }

        for (BdcQlrDO bdcQlrDO : qlrDOList) {
            BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcQlrDO.getQlrmc(), null);
            if (bdcXtJgDO != null && StringUtils.equals(bdcXtJgDO.getSfjrhlw(), "1")) {
                return bdcXtJgDO.getSfjrhlw();
            }
        }
        return SF_F;
    }


    /**
     * 判断流程的权利人是否接入互联网+
     *
     * @param processInsId processInsId
     * @return 0：未接入；1：接入
     */
    public String sfJrHlwProcessInsId(String processInsId) {
        if (CollectionUtils.isEmpty(sfbjProcessKeys)) {
            return SF_F;
        }

        // 获取审核信息，判断当前节点是否需要验证
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setGzlslid(processInsId);
        List<BdcShxxDO> bdcShxxDOS = bdcShxxFeignService.queryBdcShxx(bdcShxxQO);
        if (CollectionUtils.isEmpty(bdcShxxDOS) || bdcShxxDOS.get(0) == null
                || !yzjds.contains(bdcShxxDOS.get(0).getJdmc())) {
            return SF_F;
        }

        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);

        if (CollectionUtils.isEmpty(bdcXmDTOS) || null == bdcXmDTOS.get(0)) {
            return SF_F;
        }

        for (BdcXmDTO bdcXmDTO : bdcXmDTOS) {
            if (!sfbjProcessKeys.contains(bdcXmDTO.getGzldyid())) {
                continue;
            }

            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmDTO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> qlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);

            if (CollectionUtils.isEmpty(qlrDOS)) {
                continue;
            }
            for (BdcQlrDO bdcQlrDO : qlrDOS) {
                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcQlrDO.getQlrmc(), null);
                if (bdcXtJgDO != null && StringUtils.equals(bdcXtJgDO.getSfjrhlw(), "1")) {
                    return bdcXtJgDO.getSfjrhlw();
                }
            }
        }
        return SF_F;
    }

    @Override
    public void processBeforeDelete(EventDTO eventDTO,BdcXmDO bdcXmDO, String userName) {
        String gzlslid = eventDTO.getGzlslid();
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("删除前方法未传入 gzlslid");
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOS) || null == bdcXmDTOS.get(0)) {
            throw new AppException("数据异常，gzlslid：" + gzlslid + "未查询到对应的项目数据");
        }

        String slbh = bdcXmDTOS.get(0).getSlbh();
        if (StringUtils.startsWith(slbh, ZSYH)) {
            // 南通招商银行删除流程特殊处理
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("processInsId", gzlslid);
            jsonObject.put("msg", eventDTO.getReason());
            LOGGER.warn("南通删除招商银行流程, 调用 exchange 传递 beanName:[{}], requestObject:[{}]", ZSYHXX_DELETE, jsonObject);
            Object response = exchangeInterfaceFeignService.requestInterface(ZSYHXX_DELETE, jsonObject);
            // 处理返回结果
            this.dealResponse(response);
        }
    }

    private void dealResponse(Object response) {
        // 现场配置 URL 错误可能导致返回值为 null
        if (null == response) {
            throw new AppException("删除数据异常，检查 exchange 相关接口配置");
        }

        LOGGER.info("南通删除招商银行流程，返回结果：{}", response);

        // 返回值进行 JSONObject 和 LinkedHashMap 的处理
        if (response instanceof JSONObject) {
            JSONObject json = (JSONObject) response;
            if (json.size() == 0) {
                throw new AppException("exchange 接口返回对象为空");
            }

            String result = json.getString("result");

            if (!StringUtils.equals(result, SUCCESS_CODE)) {
                throw new AppException(json.getString("message"));
            }
        } else if (response instanceof LinkedHashMap) {
            HashMap map = (LinkedHashMap) response;
            if (map.size() == 0) {
                throw new AppException("exchange 接口返回对象为空");
            }

            String result = map.get("result").toString();
            if (!StringUtils.equals(result, SUCCESS_CODE)) {
                throw new AppException(map.get("message").toString());
            }
        } else {
            // 其他类型暂时不处理
            throw new AppException("外网请求结果返回类型，类转换异常");
        }
    }
}
