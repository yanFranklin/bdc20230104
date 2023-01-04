package cn.gtmap.realestate.exchange.service.impl.inf.build;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.service.inf.log.LogService;
import cn.gtmap.realestate.exchange.service.rabbitmq.InsertAuditLogMQSender;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.LogUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-24
 * @description 记录日志的实现 对应配置中的ex:log标签
 */
@Service(value = "buildLog")
public class BuildLogServiceImpl extends BuildAbstractServiceImpl {

    @Autowired
    private InsertAuditLogMQSender insertAuditLogMQSender;

    private static final Logger logger = LoggerFactory.getLogger(BuildLogServiceImpl.class);

    @Autowired
    private LogUtil logUtil;


    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    BdcZdFeignService bdcZdFeignService;


    /**
     * @param builder
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过builder 处理 请求和响应
     */
    @Override
    public void build(InterfaceRequestBuilder builder) {
        if(builder.getExchangeBean() != null
                && builder.getExchangeBean().getLogBO() != null){
            LogBO logBO = builder.getExchangeBean().getLogBO();
            // 记录 请求日志 初始化日志实体，如果原始参数中包含SLBH 则记录SLBH
            AuditEventBO auditEventBO = new AuditEventBO(logBO,builder);
            auditEventBO.setRequest(JSONObject.toJSONString(builder.getOriginalRequestObject()));
            if(builder.getResponseBody() != null){
                if(builder.getResponseBody() instanceof String){
                    auditEventBO.setResponse((String)builder.getResponseBody());
                }else{
                    auditEventBO.setResponse(JSONObject.toJSONString(builder.getResponseBody()));
                }
            }
            saveAuditLog(auditEventBO);
        }
    }

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param auditEventBO
     * @return void
     * @description 保存请求日志-手动设置地址
     */
    public void saveAuditLog(AuditEventBO auditEventBO,String url){
        // 判断当前接口需要记录那种日志
        if(auditEventBO != null && auditEventBO.getLogBO() != null) {
            //发送消息异步处理保存请求日志
            logger.debug("开始保存请求日志");
            auditEventBO.getLogBO().setBdcdz(url);
            String userName = userManagerUtils.getCurrentUserName();
            if (StringUtils.isNotBlank(userName)) {
                auditEventBO.setUsername(userName);
            } else {
                auditEventBO.setUsername(CommonUtil.aopLogGetPrincipal());
            }
            UserDto currentUser = userManagerUtils.getCurrentUser();
            if(Objects.nonNull(currentUser) && StringUtils.isNotBlank(currentUser.getAlias())){
                auditEventBO.setAlias(currentUser.getAlias());
            }

            //尝试查找区县代码
            if(Objects.nonNull(auditEventBO.getBuilder())
                    && Objects.nonNull(auditEventBO.getBuilder().getOriginalRequestObject())
                    && !(auditEventBO.getBuilder().getOriginalRequestObject() instanceof String)
            ){
                String originalRequest = JSONObject.toJSONString(auditEventBO.getBuilder().getOriginalRequestObject());
                List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");
                for (Map map : qxZdmap) {
                    String dm = MapUtils.getString(map, "DM");
                    if(originalRequest.contains(dm)){
                        auditEventBO.setQxdm(dm);
                        break;
                    }
                }
            }

            Message<AuditEventBO> message = MessageBuilder.createMessage(auditEventBO, new MessageHeaders(new HashMap<>()));
            insertAuditLogMQSender.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDJINSERTAUDITLOGQUEUE.getCode(), message);
            // 读取当前请求地址 用于读取配置
//            String requestPath = CommonUtil.getCurrentRequestPath();
//            auditEventBO.getLogBO().setBdcdz(requestPath);
//            List<LogService> logServiceList = logUtil.getServiceList(auditEventBO.getLogBO());
//            if (CollectionUtils.isNotEmpty(logServiceList)) {
//                for (LogService logService : logServiceList) {
//                    try {
//                        logService.saveAuditLog(auditEventBO);
//                    } catch (Exception e) {
//                        LOGGER.error("记录日志异常：logservice:{},auditEventBO:{}", logService.getClass().getSimpleName(), JSONObject.toJSONString(auditEventBO));
//                    }
//                }
//            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param auditEventBO
     * @return void
     * @description 保存请求日志
     */
    public void saveAuditLog(AuditEventBO auditEventBO){
        // 判断当前接口需要记录那种日志
        if(auditEventBO != null && auditEventBO.getLogBO() != null) {
            //发送消息异步处理保存请求日志
            logger.debug("开始保存请求日志");
            String requestPath = CommonUtil.getCurrentRequestPath();
            auditEventBO.getLogBO().setBdcdz(requestPath);
            String userName = userManagerUtils.getCurrentUserName();
            if (StringUtils.isNotBlank(userName)) {
                auditEventBO.setUsername(userName);
            } else {
                auditEventBO.setUsername(CommonUtil.aopLogGetPrincipal());
            }
            UserDto currentUser = userManagerUtils.getCurrentUser();
            if(Objects.nonNull(currentUser) && StringUtils.isNotBlank(currentUser.getAlias())){
                auditEventBO.setAlias(currentUser.getAlias());
            }
            //尝试查找区县代码
            if(Objects.nonNull(auditEventBO.getBuilder())
                    && Objects.nonNull(auditEventBO.getBuilder().getOriginalRequestObject())
                    && !(auditEventBO.getBuilder().getOriginalRequestObject() instanceof String)
            ){
                String originalRequest = JSONObject.toJSONString(auditEventBO.getBuilder().getOriginalRequestObject());
                List<Map> qxZdmap = bdcZdFeignService.queryBdcZd("qx");
                for (Map map : qxZdmap) {
                    String dm = MapUtils.getString(map, "DM");
                    if(originalRequest.contains(dm)){
                        auditEventBO.setQxdm(dm);
                        break;
                    }
                }
            }
            Message<AuditEventBO> message = MessageBuilder.createMessage(auditEventBO, new MessageHeaders(new HashMap<>()));
            insertAuditLogMQSender.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDJINSERTAUDITLOGQUEUE.getCode(), message);
            // 读取当前请求地址 用于读取配置
//            String requestPath = CommonUtil.getCurrentRequestPath();
//            auditEventBO.getLogBO().setBdcdz(requestPath);
//            List<LogService> logServiceList = logUtil.getServiceList(auditEventBO.getLogBO());
//            if (CollectionUtils.isNotEmpty(logServiceList)) {
//                for (LogService logService : logServiceList) {
//                    try {
//                        logService.saveAuditLog(auditEventBO);
//                    } catch (Exception e) {
//                        LOGGER.error("记录日志异常：logservice:{},auditEventBO:{}", logService.getClass().getSimpleName(), JSONObject.toJSONString(auditEventBO));
//                    }
//                }
//            }
        }
    }
}
