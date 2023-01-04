package cn.gtmap.realestate.register.core.aop;

import cn.gtmap.realestate.common.core.dto.register.BdcGzlSjJkDTO;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.service.workflow.BdcGzlsjLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: realestate
 * @description: 工作流执行接口切面保存日志
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-04-15 15:31
 **/
@Component
@Aspect
public class WorkflowLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowLogAspect.class);

    @Autowired
    BdcGzlsjLogService bdcGzlsjLogService;
    @Autowired
    UserManagerUtils userManagerUtils;


    //拦截注解切点
    @Pointcut(value = "@annotation(cn.gtmap.realestate.common.core.annotations.WokrFlowLog)")
    public void cut() {
    }


    @Around("cut()")
    public Object saveLog(ProceedingJoinPoint joinPoint) {
        LOGGER.warn("开始记录流程事件执行日志");
        Object[] args = joinPoint.getArgs();
        BdcGzlSjJkDTO bdcGzlSjJkDTO = (BdcGzlSjJkDTO) args[0];
        Map jkcs = (Map) args[1];
        LOGGER.warn("当前接口执行参数{}", bdcGzlSjJkDTO);
        Object result;
        try {
            result = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            result = throwable.getMessage();
        }
        bdcGzlsjLogService.saveBdcGzlsjLog(bdcGzlSjJkDTO, jkcs, result);
        return result;
    }

}
