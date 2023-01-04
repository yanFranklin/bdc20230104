package cn.gtmap.realestate.certificate.core.support.aspect;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzLogService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.ExceptionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/14 统一日志管理
 */
@Aspect
@Component
// 标记切点的优先级,值越小,优先级越高
@Order(1)
public class AspectRecord {
    private static final Logger logger = LoggerFactory.getLogger(AspectRecord.class);
    @Autowired
    protected BdcDzzzLogService bdcDzzzLogService;
   /* @Autowired
    private ApplicationContext applicationContext;
    ThreadLocal<BdcDzzzLogDO> dzzzLogDOThreadLocal = new ThreadLocal<>();
    ThreadLocal<String> identification = new ThreadLocal<>();
    ThreadLocal<String> threadLocalParam = new ThreadLocal<>();*/

    // 申明一个切点 里面是 execution表达式
    /*@Pointcut("execution(public * cn.gtmap.realestate.certificate.web.rest.*.*(..)) " +
            "|| execution(public * cn.gtmap.realestate.certificate.web.maintain.BdcDzzzMaintainController.getToken(..))" +
            "|| execution(public * cn.gtmap.realestate.certificate.web.maintain.BdcDzzzMaintainController.createPdfBdcDzzzByZzid(..))")*/
    @Pointcut("@annotation(cn.gtmap.realestate.certificate.core.support.annotations.RecordLog)")
    private void point() {
    }

    // 请求method前记录请求内容
//    @Before(value = "recordControllerPointcut()")
//    public void methodBefore(JoinPoint joinPoint) {
//        String flag = StringUtil.getUUID32();
//        //identification.set(logFlag);
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        //BdcDzzzLogDO bdcDzzzLogDO = bdcDzzzLogService.getRequestData(request, logFlag, joinPoint);
//        //threadLocalParam.set(bdcDzzzLogDO.getParamJson());
//        //bdcDzzzLogService.insertBdcDzzzLog(bdcDzzzLogDO);
//        dzzzLogDOThreadLocal.set(bdcDzzzLogService.getRequestData(request, flag, joinPoint));
//    }


    // 在方法执行完结后记录返回内容
//    @AfterReturning(returning = "o", pointcut = "recordControllerPointcut()")
//    public void methodAfterReturing(Object o) {
//        //bdcDzzzLogService.insertResponseData(threadLocalParam.get(),identification.get(), JSON.toJSONString(o));
//        BdcDzzzLogDO bdcDzzzLogDO = dzzzLogDOThreadLocal.get();
//        bdcDzzzLogService.insertAsyncResponseData(bdcDzzzLogDO,JSON.toJSONString(o));
//        applicationContext.publishEvent(new RecordLogEvent(bdcDzzzLogDO));
//    }

    // 在方法抛异常时记录日志
//    @AfterThrowing(pointcut = "recordControllerPointcut()", throwing = "e")
//    public void methodAfterThrowing(JoinPoint point, Throwable e) {
//        //bdcDzzzLogService.insertResponseData(threadLocalParam.get(),identification.get(), JSON.toJSONString(ExceptionUtil.getExceptionInfo(e)));
//        BdcDzzzLogDO bdcDzzzLogDO = dzzzLogDOThreadLocal.get();
//        bdcDzzzLogService.insertAsyncResponseData(bdcDzzzLogDO,JSON.toJSONString(ExceptionUtil.getExceptionInfo(e)));
//        applicationContext.publishEvent(new RecordLogEvent(bdcDzzzLogDO));
//    }

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object result;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("AspectRecordAround:日志记录异常", throwable);
            result = ExceptionUtil.getExceptionInfo(throwable);
        }

        final Object obj = result;
        final HttpServletRequest request = requestAttributes.getRequest();
        final String url = request.getRequestURI();
        final String yymc = (String) request.getAttribute(Constants.YYMC);
        bdcDzzzLogService.insertBdcDzzzLog(joinPoint, url, yymc, obj);

        return result;
    }

}
