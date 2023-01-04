package cn.gtmap.realestate.exchange.web.rest.yancheng;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.enums.GxEnum;
import cn.gtmap.realestate.common.core.enums.ShijgxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.config.BdcZdSsjGxQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcZdSsjgxFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.BdcShijiRestService;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.core.vo.BdcShijiStatisticsVO;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcShijiService;
import cn.gtmap.realestate.exchange.service.rabbitmq.InsertAuditLogMQSender;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
 * @version 1.0  2020-11-23
 * @description (盐城) 一体化相关服务
 */
@OpenController(consumer = "(盐城) 市级接口相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
@Api(tags = "(盐城) 市级接口相关服务")
public class BdcShijiRestController implements BdcShijiRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcShijiRestController.class);

    @Autowired
    private BdcShijiService bdcShijiService;

    @Autowired
    private InsertAuditLogMQSender insertAuditLogMQSender;

    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;

    @Autowired
    private BdcZdSsjgxFeignService bdcZdSsjgxFeignService;

    /**
     * 盐城_一体化撤件请求
     *
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @Override
    @OpenApi(apiDescription = "盐城_市级接口统计")
    @GetMapping("/yancheng/shiji/list/query/log")
    public Object listQueryLog(@LayuiPageable Pageable pageable, @RequestParam(value = "kssj", required = false) String kssj, @RequestParam(value = "jssj", required = false) String jssj, @RequestParam(value = "bmmc", required = false) String bmmc, @RequestParam(value = "bjry", required = false) String bjry, @RequestParam(value = "jkmc", required = false) String jkmc) throws Exception {
        if (pageable != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(bmmc)) {
                List<OrganizationDto> organizationDtos = organizationManagerClient.listOrgs(1);
                String finalDepartment = bmmc;
                Optional<OrganizationDto> any = organizationDtos.stream().filter(organizationDto -> finalDepartment.equals(organizationDto.getId())).findAny();
                if (any.isPresent()) {
                    bmmc = any.get().getName();
                }
            }
            BdcShijiStatisticsVO bdcShijiStatisticsVO = BdcShijiStatisticsVO.BdcShijiStatisticsVOBuilder.aBdcShijiStatisticsVO().withCreater(bjry).withDepartment(bmmc).withInterfaceName(jkmc).build();
            LOGGER.info("盐城_市级接口统计入参:{}", JSON.toJSONString(bdcShijiStatisticsVO));
            if (StringUtils.isNotBlank(kssj)) {
                bdcShijiStatisticsVO.setStartTime(simpleDateFormat.parse(kssj));
            }
            if (StringUtils.isNotBlank(jssj)) {
                bdcShijiStatisticsVO.setEndTime(simpleDateFormat.parse(jssj));
            }
            return bdcShijiService.listQueryLog(bdcShijiStatisticsVO, pageable.getPageNumber(), pageable.getPageSize());
        } else {
            throw new RuntimeException("入参缺失分页参数");
        }
    }

    /**
     * 保存市级接口日志信息
     *
     * @param interfaceName
     * @param creater
     * @param department
     * @param request
     * @param response
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @OpenApi(apiDescription = "盐城_市级日志保存")
    @GetMapping("/yancheng/shiji/save/log")
    public void insertShijiInterfaceLog(@RequestParam(value = "interfaceName", required = false) String interfaceName, @RequestParam(value = "creater", required = false) String creater, @RequestParam(value = "department", required = false) String department, @RequestParam(value = "request", required = false) String request, @RequestParam(value = "response", required = false) String response, @RequestParam(value = "url", required = false) String url) {
        LOGGER.info("市级接口查询日志存储");
        try {
            LogBO logBO = new LogBO();
            logBO.setRequester("BDCDJ");
            logBO.setResponser("ZWGX");
            logBO.setDsfFlag(department);
            BdcZdSsjGxDO ssjgxByZmldm = bdcZdSsjgxFeignService.getSsjgxByZmldm(interfaceName);
            if (ssjgxByZmldm != null) {
                logBO.setLogEventName(ssjgxByZmldm.getJkmc());
            } else {
                logBO.setLogEventName(StringUtils.isNotBlank(ShijgxEnum.getInterfaceNameByBeanName(interfaceName)) ? ShijgxEnum.getInterfaceNameByBeanName(interfaceName) : GxEnum.getInterfaceUrl(interfaceName));
            }
//            logBO.setLogEventName(interfaceName);
            AuditEventBO auditEventBO = new AuditEventBO(logBO);
            auditEventBO.setRzid(UUIDGenerator.generate16());
            //指定新的es存储目录和日志类型
            auditEventBO.setRzlx(Constants.EXCHANGE_ES_SHIJI_GX_RZLX);
            auditEventBO.setRequest(request);
            auditEventBO.setResponse(response);
            if (ssjgxByZmldm != null && StringUtils.isNotBlank(ssjgxByZmldm.getJkdz())) {
                auditEventBO.getLogBO().setBdcdz(ssjgxByZmldm.getJkdz());
            } else {
                auditEventBO.getLogBO().setBdcdz(url);
            }
            auditEventBO.setUsername(creater);
            Message<AuditEventBO> message = MessageBuilder.createMessage(auditEventBO, new MessageHeaders(new HashMap<>()));
            LOGGER.info("市级接口查询日志存储入参:{}", message.toString());
            //发送日志记录保存消息
            insertAuditLogMQSender.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDJINSERTAUDITLOGQUEUE.getCode(), message);
        } catch (Exception e) {
            LOGGER.error("市级接口查询日志存储异常", e);
        }
        LOGGER.info("市级接口查询日志存储结束");
    }

    @GetMapping("/listOrgs")
    @ApiOperation(value = "获取所有组织")
    @ResponseStatus(HttpStatus.OK)
    public Object queryAllOrgs() {
        return organizationManagerClient.listOrgs(1);
    }

    /**
     * 3、根据组织id获取人员信息
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: id 组织id
     * @return: Object
     */
    @GetMapping("/users/{id}")
    @ApiOperation(value = "根据组织id获取人员信息")
    @ResponseStatus(HttpStatus.OK)
    public Object queryUsersByOrgId(@PathVariable(value = "id") String id) {
        if (StringUtils.isEmpty(id)) {
            return Collections.EMPTY_LIST;
        }
        return this.organizationManagerClient.listUsersByOrg(id);
    }

    /**
     * 获取接口信息
     *
     * @author: <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @return: Object
     */
    @GetMapping("/interface/info")
    @ApiOperation(value = "获取市级接口信息")
    @ResponseStatus(HttpStatus.OK)
    public Object queryShijiInterfaceNames() {
        List<BdcZdSsjGxDO> bdcZdSsjGxDOS = bdcZdSsjgxFeignService.listSsjgx(new BdcZdSsjGxQO());
        if (CollectionUtils.isNotEmpty(bdcZdSsjGxDOS)) {
            JSONArray result = new JSONArray(bdcZdSsjGxDOS.size());
            for (BdcZdSsjGxDO bdcZdSsjGxDO : bdcZdSsjGxDOS) {
                JSONObject jsonObject = new JSONObject(2);
                jsonObject.put("name", bdcZdSsjGxDO.getZmlmc());
                jsonObject.put("value", bdcZdSsjGxDO.getZmlmc());
                result.add(jsonObject);
            }
            return result;
        } else {
            JSONArray result = new JSONArray(ShijgxEnum.values().length + GxEnum.values().length);
            for (ShijgxEnum value : ShijgxEnum.values()) {
                JSONObject jsonObject = new JSONObject(2);
                jsonObject.put("name", "市级-" + value.getInterfaceName());
                jsonObject.put("value", "市级-" + value.getInterfaceName());
                result.add(jsonObject);
            }
            for (GxEnum value : GxEnum.values()) {
                JSONObject jsonObject = new JSONObject(2);
                jsonObject.put("name", "省级-" + value.getInterfaceName());
                jsonObject.put("value", "省级-" + value.getInterfaceName());
                result.add(jsonObject);
            }
            return result;
        }
    }

    /**
     * 盐城_一体化撤件请求
     *
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "盐城_市级接口统计")
    @GetMapping("/yancheng/export/query/log")
    public void exportQueryLog(@RequestParam(value = "kssj", required = false) String kssj, @RequestParam(value = "jssj", required = false) String jssj, HttpServletResponse response) throws Exception {
        BdcShijiStatisticsVO bdcShijiStatisticsVO = new BdcShijiStatisticsVO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(kssj)) {
            bdcShijiStatisticsVO.setStartTime(simpleDateFormat.parse(kssj));
        }
        if (StringUtils.isNotBlank(jssj)) {
            bdcShijiStatisticsVO.setEndTime(simpleDateFormat.parse(jssj));
        }
        bdcShijiService.exportStaticLog(bdcShijiStatisticsVO, response);
    }

    /**
     * 手动同步市级共享Es数据至DB中
     * @param kssj 开始时间
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/yancheng/shijigx/estodb")
    public void exportQueryLog(@RequestParam(value = "kssj", required = true) String kssj){
        if(StringUtils.isBlank(kssj)){
            throw new AppException(ErrorCode.MISSING_ARG, "起始时间不能为空");
        }
        bdcShijiService.syncShijiEsLogToDB(kssj);
    }

}
