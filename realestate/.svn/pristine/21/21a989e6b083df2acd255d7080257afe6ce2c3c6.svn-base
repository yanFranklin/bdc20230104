package cn.gtmap.realestate.exchange.core.aop;

import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.exchange.util.CommonUtil;
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
 * @version 1.0  2019-08-15
 * @description 记录所有exchange 服务日志  包括 请求参数、响应结果、异常信息
 */
//@Aspect
//@Component
public class RestLogAspect {


    private static final Logger LOGGER = LoggerFactory.getLogger(RestLogAspect.class);

    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Pointcut("execution(public * cn.gtmap.realestate.exchange.web.rest.*.*(..))")
    public void restSaveLogPc(){}


    @AfterReturning(value="restSaveLogPc()",returning="result")
    public void afterReturn(JoinPoint joinPoint, Object result){
        Map<String, Object> data = new HashMap<>();
        if (null != result) {
            data.put("response", result);
        }
        saveLogEvent(joinPoint,data);
    }

    @AfterThrowing(value="restSaveLogPc()",throwing="e")
    public void afterThrow(JoinPoint joinPoint,Exception e){
        Map<String, Object> data = new HashMap<>();
        if (null != e) {
            data.put("response", e);
        }
        saveLogEvent(joinPoint, data);
    }

    private void saveLogEvent(JoinPoint joinPoint, Map<String, Object> data){
        String principal = CommonUtil.aopLogGetPrincipal();
        String eventName = getEventName(joinPoint);
        data.putAll(getArgMap(joinPoint));
        AuditEvent event = new AuditEvent(principal,eventName,data);
        try {
            zipkinAuditEventRepository.add(event);
        }catch (Exception e){
            LOGGER.error("AOP记录日志异常",e);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param joinPoint
     * @return java.lang.String
     * @description 获取 事件名称
     */
    private String getEventName(JoinPoint joinPoint){
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        if(null != args && args.length > 0){
            // 如果只有一个参数 并且是 HttpServletRequest
            if(args.length == 1 && args[0] instanceof HttpServletRequest){
                return methodName;
            }
            // 如果第一个参数是 STRING 默认认为是 beanName参数
            if(args[0] instanceof String){
                return args[0].toString();
            }
        }
        return className + "_" + methodName;
    }

    private Map<String,Object> getArgMap(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Map<String, Object> data = new HashMap();
        if(null != args && args.length > 0){
            for(int i = 0; i < args.length; i++){
                Object o = args[i];

                // 如果请求参数 为 HttpServletRequest，从中获取参数
                if(o instanceof HttpServletRequest){
                    if(((HttpServletRequest)o).getAttribute("paramBody") != null){
                        data.put("arg["+i+"]", ((HttpServletRequest)o).getAttribute("paramBody"));
                    }
                }else if (!(o instanceof HttpServletResponse)
                        && !(o instanceof Model)
                        && !(o instanceof ModelAndView)
                        && !(o instanceof Authentication)
                        && !(o instanceof OAuth2Authentication)) {
                    data.put("arg["+i+"]", o);
                }
            }
        }
        return data;
    }
}
