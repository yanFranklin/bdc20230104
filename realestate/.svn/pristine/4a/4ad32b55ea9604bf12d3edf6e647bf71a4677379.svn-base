package cn.gtmap.realestate.inquiry.ui.core.aop;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.enums.GxEnum;
import cn.gtmap.realestate.common.core.service.feign.exchange.BdcShijiFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.core.annotation.GxInterfaceLog;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-08-18
 * @description 拦截 GxInterfaceLog注解 记录日志
 */
@Aspect
@Component
public class GxInterfaceLogAspect {

    protected static Logger LOGGER = LoggerFactory.getLogger(GxInterfaceLogAspect.class);

    @Pointcut("@annotation(cn.gtmap.realestate.inquiry.ui.core.annotation.GxInterfaceLog)")
    public void doLog() {
    }

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcShijiFeignService bdcShijiFeignService;

    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug("开始档案日志保存切面处理");
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        GxInterfaceLog annotation = signature.getMethod().getAnnotation(GxInterfaceLog.class);
        Object[] args = pjp.getArgs();
        //获取是否验证标识
        Object data = pjp.proceed();
        try {
            LOGGER.info("共享接口保存日志开始");
            UserDto userDto = userManagerUtils.getUserDto();
            String department = userManagerUtils.getOrganizationByUserName(userDto.getUsername());
            bdcShijiFeignService.insertShijiInterfaceLog(annotation.interfaceCode(), StringUtils.isNotBlank(userDto.getAlias()) ? userDto.getAlias() : userDto.getUsername(), department, JSON.toJSONString(args), JSON.toJSONString(data), GxEnum.getBeanName(annotation.interfaceCode()));
            LOGGER.info("共享接口保存日志结束");
        } catch (Exception e) {
            LOGGER.error("共享接口保存日志异常:", e);
        }
        return data;
    }


}
