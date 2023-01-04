package cn.gtmap.realestate.register.core.aop;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.register.RegisterWorkflowRestService;
import cn.gtmap.realestate.register.service.BdcDbxxService;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-07-24
 * @description 切面
 */
@Component
@Aspect
public class RegisterAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterAspect.class);
    private static final String CLASS_NAME = RegisterAspect.class.getName();
    @Autowired
    RegisterWorkflowRestService registerWorkflowRestService;
    @Autowired
    BdcDbxxService bdcDbxxService;

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 定义需要验证的登簿更新切点
     */
    @Pointcut("execution(* cn.gtmap.realestate.register.service.BdcDbxxService.updateDbxxQsztGzyzAOP(..))")
    public void updateDbxxQsztGzyzAOP() {
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @Around("updateDbxxQsztGzyzAOP()")
    public Object beforeDbGzyz(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object gzlslId = args[0];
        if (gzlslId == null) {
            LOGGER.debug("{}：登簿规则验证 ：缺少参数gzlslid{}", CLASS_NAME, gzlslId);
            throw new MissingArgumentException("gzlslid");
        }
        LOGGER.debug("{}：登簿规则验证 ：{}", CLASS_NAME, gzlslId);
        String processInsId = String.valueOf(gzlslId);
        Object bdcGzyzVOS = bdcDbxxService.beforeDbGzyz(processInsId);
        if (null != bdcGzyzVOS && CollectionUtils.isNotEmpty((List) bdcGzyzVOS)) {
            return bdcGzyzVOS;
        }

        return joinPoint.proceed(args);
    }
}
