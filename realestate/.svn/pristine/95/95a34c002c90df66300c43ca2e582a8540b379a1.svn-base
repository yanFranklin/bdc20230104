package cn.gtmap.realestate.exchange.core.aop;

import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-15
 * @description 接口服务转换器中的日志记录
 * eventName 为 beanId + 实现类名  例如“wwsqHtqlrxx_buildRequest”
 */
@Aspect
@Component
public class BuildLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestLogAspect.class);

    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Pointcut("execution(public * cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService.build(..))")
    public void builderLog() {
    }

    //    暂时关掉build 日志
    @AfterReturning(value = "builderLog()")
    public void builderLogAfterReturn(JoinPoint joinPoint) {
        InterfaceRequestBuilder builder = getBuilder(joinPoint);
        if (builder == null) {
            LOGGER.error("记录日志builder为null");
            return;
        }
        String principal = CommonUtil.aopLogGetPrincipal();
        if (StringUtils.isNotBlank(principal)) {
            String eventName = getEventName(joinPoint, builder);
            Map<String, Object> logData = new HashMap<>();
            logData.put(eventName + "_requestBody", builder.getRequestBody());
            logData.put(eventName + "_responseBody", builder.getResponseBody());
            if (eventName.contains("swTsFjcl_dk") || eventName.contains("hfDealDaFjxx")
                    || eventName.contains("dzzz_zzqz")) {

            } else {
                AuditEvent event = new AuditEvent(principal, eventName, logData);
                try {
                    if (principal.equals("unknown")) {
                        zipkinAuditEventRepository.newSpanTag(event, eventName);
                    } else {
                        zipkinAuditEventRepository.add(event);
                    }

                } catch (Exception e) {
                    LOGGER.error("AOP记录日志异常", e);
                }
            }
        }
    }

    private String getEventName(JoinPoint joinPoint, InterfaceRequestBuilder builder) {
        ExchangeBean exchangeBean = builder.getExchangeBean();
        Class t = joinPoint.getSignature().getDeclaringType();
        Service serviceAnno = (Service) t.getAnnotation(Service.class);
        String serviceName = serviceAnno.value();
        return exchangeBean.getId() + "_" + serviceName;
    }

    private InterfaceRequestBuilder getBuilder(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        InterfaceRequestBuilder builder = null;
        if (args.length > 0 && args[0] instanceof InterfaceRequestBuilder) {
            builder = (InterfaceRequestBuilder) args[0];
        }
        return builder;
    }

}
