package cn.gtmap.realestate.exchange.web.rest.shucheng;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPjqFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.util.LogActionConstans;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.hefei.hcp.HcpHlwResponseDTO;
import cn.gtmap.realestate.exchange.service.inf.hefei.HcpService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @Date 2022/8/25
 * @description 舒城好差评
 */
@RestController
@Api(tags = "好差评相关服务请求")
@RequestMapping("/realestate-exchange/rest/v1.0")
public class HcpRestController {
    private static Logger LOGGER = LoggerFactory.getLogger(HcpRestController.class);
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlPjqFeignService bdcSlPjqFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    private ExchangeInterfaceRestService exchangeInterfaceRestService;

    @Autowired
    private HcpService hcpService;

    /**
     * @param projNo 办件编号，格式projNo_status 例：34xxxxxx_2
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @version 1.0, 2022/7/22
     * @description “好差评”系统调用登记，告诉登记已经评价，登记记录评价结果
     */
    @ApiOperation(value = "更新办件状态")
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "更新办件状态", dsfFlag = "HCP", requester = "BDC", responser = "HCP")
    @ResponseBody
    @PostMapping("/{version}/hcp/updateEvaluateStatus")
    public Object updateEvaluateStatus(@PathVariable (value = "version")String version,@RequestParam(value = "projNo") String projNo) {
        LOGGER.info("开始更新办件状态，请求参数projNo:{},地区版本:{}",projNo,version);
        if (StringUtils.isNotBlank(projNo)) {
            List<String> params = Arrays.asList(projNo.split("_"));
            // 办件编号
            String bjbh = params.get(0);
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setZfxzspbh(bjbh);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                List<TaskData> taskDataList = processTaskClient.processRunningTasks(bdcXmDO.getGzlslid());
                String jdmc = taskDataList.get(0).getTaskName();
                try {
                    // okhttp3 请求头不能为中文，进行编码
                    jdmc = URLEncoder.encode(jdmc, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LOGGER.error("好差评更新评价失败，节点名称解码失败：{}，传参projNo:{}", jdmc, projNo);
                }
                // 更新或保存评价
                BdcSlPjqDO bdcSlPjqDO = bdcSlPjqFeignService.queryBdcSlPjqByYwbh(bjbh, jdmc);
                if (Objects.nonNull(bdcSlPjqDO)) {
                    bdcSlPjqDO.setPjsj(new Date());
                    bdcSlPjqFeignService.updateBdcSlPjq(bdcSlPjqDO);
                } else {
                    UserDto userDto = userManagerUtils.getCurrentUser();
                    bdcSlPjqDO = new BdcSlPjqDO();
                    bdcSlPjqDO.setYwmc(bdcXmDO.getGzldymc());
                    bdcSlPjqDO.setSqrxm(bdcXmDO.getQlr());
                    bdcSlPjqDO.setPjsj(new Date());
                    bdcSlPjqDO.setBlry(userManagerUtils.getCurrentUserName());
                    bdcSlPjqDO.setBlryid(userDto.getId());
                    bdcSlPjqDO.setYwbh(bjbh);
                    bdcSlPjqDO.setJdmc(jdmc);
                    bdcSlPjqFeignService.insertBdcSlPjq(bdcSlPjqDO);
                }
                // 调用省平台接口，告知已完成评价
                Map<String, String> map = new HashMap<>();
                map.put("projNo", projNo);
                Object response = exchangeInterfaceRestService.requestInterface("hcp_gxzt", map);
                LOGGER.info("好差评系统调用省里更新评价状态接口，返回参数为：{}", JSONObject.toJSONString(response));
                return response;
            }
        }
        return null;
    }

    /**
     * @param param
     * @description: 互联网查询不动产好差评相关信息
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @date: 2022/11/9 17:10
     * @return: BdcCommonResponse
     **/
    @LogNote(value = "互联网查询不动产好差评相关信息", action = LogActionConstans.QUERY)
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "互联网查询不动产好差评相关信息", dsfFlag = "HCP", requester = "BDC", responser = "HCP")
    @ResponseBody
    @PostMapping("/hcp/getHcpInfo")
    public HcpHlwResponseDTO getHcpInfo(@RequestBody JSONObject param) {
        String slbh = "";
        if (param!=null){
            JSONObject data  = param.getJSONObject("data");
            slbh = data.getString("nwslbh");
        }
        return hcpService.getHcpParamsBySlbh(slbh);
    }
}
