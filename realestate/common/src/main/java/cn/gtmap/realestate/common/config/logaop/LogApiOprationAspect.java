package cn.gtmap.realestate.common.config.logaop;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/6/3
 * <p>
 *      将 @ApiOperation 注解的 value 值缓存至 LogCommonCacheMap.methodLogEntity 中，
 *      在 LogApiAspect 中用于日志记录时，获取对应的日志类型
 * </p>
 */
@Component
@Aspect
public class LogApiOprationAspect {

    @Pointcut("@annotation(apiOperation)")
    public void logApiPointCut(ApiOperation apiOperation){

    }

    @AfterReturning(
            returning = "response",
            pointcut = "logApiPointCut(apiOperation)"
    )

    /**
     *  记录日志切面
     */
    public void doAfter(JoinPoint joinPoint, ApiOperation apiOperation, Object response) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        if(apiOperation.extensions() != null && apiOperation.extensions().length > 0){
           Extension est = apiOperation.extensions()[0];
           if(est.properties() != null && est.properties().length >0){
               String name = est.properties()[0].name();
               String value = est.properties()[0].value();
               if("saveLog".equals(name) ){
                   LogCommonCacheMap.methodLogEntity.put(methodSignature.getName()+"_saveLog",value);
               }
           }
       }
        // 记录该方法的功能描述
        LogCommonCacheMap.methodLogEntity.put(methodSignature.getName(),apiOperation.value());
    }

}

