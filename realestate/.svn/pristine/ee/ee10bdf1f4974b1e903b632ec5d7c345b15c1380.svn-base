package cn.gtmap.realestate.exchange.core.aop;

import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.service.impl.inf.build.BuildLogServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-24
 * @description 拦截 DsfLog注解 记录日志
 */
@Aspect
@Component
public class DsfLogAspect {

    protected static Logger LOGGER = LoggerFactory.getLogger(DsfLogAspect.class);

    @Autowired
    private BuildLogServiceImpl buildLogService;

    @Pointcut("@annotation(cn.gtmap.realestate.exchange.core.bo.anno.DsfLog)")
    public void doLog() {
    }

    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint pjp)
            throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        DsfLog annotation = signature.getMethod().getAnnotation(DsfLog.class);
        AuditEventBO auditEventBO = null;
        try {
            if (annotation != null) {
                // 获取日志 实体
                LogBO logBO = getLogBOFromDsfLog(annotation);
                // 获取请求地址
                auditEventBO = new AuditEventBO(logBO);
                setRequest(auditEventBO, pjp);
            }
        } catch (Exception e) {
            LOGGER.error("构造日志实体异常", e);
        }
        Object data = pjp.proceed();
        try {
            if (data != null) {
                auditEventBO.setResponse(JSONObject.toJSONString(data));
            }
            buildLogService.saveAuditLog(auditEventBO);
        } catch (Exception e) {
            LOGGER.error("保存平台日志异常", e);
        }
        return data;
    }

    /**
     * @param auditEventBO
     * @param pjp
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存请求信息
     */
    private void setRequest(AuditEventBO auditEventBO, ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        if (args.length == 1 && args[0] instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) args[0];
            try {
                auditEventBO.setRequest(IOUtils.toString(request.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                LOGGER.warn("HttpServletRequest在切面获取入参失败，接口名：{}", auditEventBO.getViewTypeName());
                e.printStackTrace();
            }
        } else if (args.length > 0) {
            Map data = new HashMap();
            for (int i = 0; i < args.length; i++) {
                Object o = args[i];
                // 如果请求参数 为 HttpServletRequest，从中获取参数
                if (!(o instanceof HttpServletResponse)
                        && !(o instanceof Model)
                        && !(o instanceof ModelAndView)
                        && !(o instanceof Authentication)
                        && !(o instanceof OAuth2Authentication)) {
                    if (o instanceof HttpServletRequest) {
                        try {
                            data.put("arg[" + i + "]", IOUtils.toString(((HttpServletRequest) o).getInputStream(), "UTF-8"));
                        } catch (IOException e) {
                            LOGGER.warn("切面日志获取入参失败,方法名为,：{}", auditEventBO.getViewTypeName());
                            e.printStackTrace();
                        }
                    } else {
                        data.put("arg[" + i + "]", o);
                    }

                }
            }
            try {
                auditEventBO.setRequest(JSONObject.toJSONString(data));
            } catch (Exception e) {
                LOGGER.info("构造Request异常：{}", e.getMessage());
                auditEventBO.setRequest(data.toString());
            }
        }
    }


    private LogBO getLogBOFromDsfLog(DsfLog annotation) {
        LogBO logBO = new LogBO();
        logBO.setResponser(annotation.responser());
        logBO.setResponser(annotation.requester());
        logBO.setDsfFlag(annotation.dsfFlag());
        logBO.setLogEventName(annotation.logEventName());
        logBO.setLogService(annotation.logService());
        return logBO;
    }

}
