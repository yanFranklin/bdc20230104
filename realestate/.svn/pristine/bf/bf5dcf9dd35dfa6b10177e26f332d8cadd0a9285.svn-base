package cn.gtmap.realestate.building.core.aop;

import cn.gtmap.realestate.building.core.dbs.SwitchDB;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/4
 * @description 动态数据源切面
 */
@ConditionalOnProperty("dynamic.enable")
@Aspect
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  动态数据源参数-权籍管理代码
      */
     private static final String DYNAMIC_DB_PARAM ="qjgldm";

    @Autowired
    SwitchDB switchDB;

    @Autowired
    UserManagerUtils userManagerUtils;

    //自动注入环境类，用于获取配置文件的属性值
    @Autowired
    private Environment evn;

    @Pointcut("execution(public * cn.gtmap.realestate.building.web.rest.*.*(..))"+
    "|| execution(public * cn.gtmap.realestate.building.core.service.BdcdyZtService.saveZtWithDTO(..))" +
    "|| execution(public * cn.gtmap.realestate.building.service.BdcdyxxService.updateBdcdyxxPl(..))" +
    "|| execution(public * cn.gtmap.realestate.building.core.service.BdcdyZtService.saveSdZtWithDTO(..))"
    )
    public void aspect(){}

    @Before("aspect()")
    public void switchDataSource(JoinPoint point) {
        switchDB.change(getDbName(point));
    }

//    @After("aspect())")
//    public void restoreDataSource(JoinPoint point) {
//    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  根据区县代码获取当前数据源key,区县代码优先读取当前用户的组织qxdm,获取不到根据入参获取
      */
    private String getDbName(JoinPoint point){
        //根据入参获取权籍管理代码
        String qjgldm =getQjgldmByParam(point);

        //根据权籍管理代码对照获取对应数据源
        return evn.getProperty("datasources."+qjgldm);

    }

    /**
      * @return 权籍管理代码
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 通过入参获取权籍管理代码,1:入参为字符串且参数名为qjgldm,2:入参为对象,且存在key为qjgldm的
      */
    private String getQjgldmByParam(JoinPoint point){
        String qjgldm ="";
        //1.这里获取到全部的参数值的数组
        Object[] args = point.getArgs();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取参数名称
        String[] parameterNames = methodSignature.getParameterNames();
        try{
            int qjgldmIndex = ArrayUtils.indexOf(parameterNames, DYNAMIC_DB_PARAM);
            if(qjgldmIndex >-1 &&args[qjgldmIndex] instanceof String){
                qjgldm =(String) args[qjgldmIndex];
            }else{
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null
                            && !(args[i] instanceof HttpServletRequest)
                            && !(args[i] instanceof HttpServletResponse)
                            && !(args[i] instanceof Model)
                            && !(args[i] instanceof ModelAndView)
                            && !(args[i] instanceof Authentication)
                            && !(args[i] instanceof OAuth2Authentication)) {
                        if(CommonUtil.isJSONObject(JSONObject.toJSONString(args[i]))) {
                            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(args[i]));
                            if(jsonObject.containsKey(DYNAMIC_DB_PARAM)){
                                qjgldm =jsonObject.getString(DYNAMIC_DB_PARAM);
                                break;
                            }
                        }else if(args[i] instanceof String &&CommonUtil.isJSONObject((String) args[i])){
                            JSONObject jsonObject = JSONObject.parseObject((String) args[i]);
                            if(jsonObject.containsKey(DYNAMIC_DB_PARAM)){
                                qjgldm =jsonObject.getString(DYNAMIC_DB_PARAM);
                                break;
                            }
                        }
                    }
                }
            }

        }catch (Exception e) {
            logger.error("AOP拦截获取入参权籍管理代码异常",e);
        }
        return qjgldm;

    }
}
