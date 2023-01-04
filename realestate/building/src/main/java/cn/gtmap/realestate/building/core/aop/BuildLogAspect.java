package cn.gtmap.realestate.building.core.aop;

import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.building.util.BuildingUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-21
 * @description 记录 所有REST 服务日志 包含 请求参数 和 返回结果 和异常信息
 */
//@Aspect
//@Component
public class BuildLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildLogAspect.class);

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Pointcut("execution(public * cn.gtmap.realestate.building.web.rest.*.*(..))")
    public void saveLogPc(){}


    @AfterReturning(value="saveLogPc()",returning="result")
    public void afterReturn(JoinPoint joinPoint,Object result){
        if(BuildingUtils.AOP_LOG){
            Map<String, Object> data = new HashMap<>();
            if (null != result) {
                data.put("response", result);
            }
            saveLogEvent(joinPoint,data);
        }
    }

    @AfterThrowing(value="saveLogPc()",throwing="e")
    public void afterThrow(JoinPoint joinPoint,Exception e){
        if(BuildingUtils.AOP_LOG) {
            Map<String, Object> data = new HashMap<>();
            if (null != e) {
                data.put("response", e);
            }
            saveLogEvent(joinPoint, data);
        }
    }

    private void saveLogEvent(JoinPoint joinPoint, Map<String, Object> data){
        String principal = getPrincipal();
        String eventName = getEventName(joinPoint);
        data.putAll(getArgMap(joinPoint));
        AuditEvent event = new AuditEvent(principal,eventName,data);
        try {
            zipkinAuditEventRepository.add(event);
        }catch (Exception e){
            LOGGER.error("AOP记录日志异常",e);
        }
    }

    private String getEventName(JoinPoint joinPoint){
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        return className + "_" + methodName;
    }

    private Map<String,Object> getArgMap(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Map<String, Object> data = new HashMap();
        if(null != args && args.length > 0){
            for(int i = 0; i < args.length; i++){
                Object o = args[i];
                if (!(o instanceof HttpServletRequest) && !(o instanceof HttpServletResponse) && !(o instanceof Model) && !(o instanceof ModelAndView) && !(o instanceof Authentication) && !(o instanceof OAuth2Authentication)) {
                    data.put("arg["+i+"]", o);
                }
            }
        }
        return data;
    }

    private String getPrincipal(){
        String principal = "unknown";
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            principal = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return principal;
    }
}
