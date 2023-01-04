package cn.gtmap.realestate.exchange.core.aop;

import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDaCxLog;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.config.filter.RequestWrapper;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfDaCheckLog;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.wwsq.ParamBody;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.service.inf.BdcDaCxService;
import cn.gtmap.realestate.exchange.service.rabbitmq.InsertAuditLogMQSender;
import cn.gtmap.realestate.exchange.util.BodyUtil;
import cn.gtmap.realestate.exchange.util.ClassUtil;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-04-22
 * @description 拦截 DsfDaCheckLogAspect注解 记录日志
 */
@Aspect
@Component
public class DsfDaCheckLogAspect {

    protected static Logger LOGGER = LoggerFactory.getLogger(DsfDaCheckLogAspect.class);

    @Pointcut("@annotation(cn.gtmap.realestate.exchange.core.bo.anno.DsfDaCheckLog)")
    public void doLog() {
    }

    private final static HashMap<String, Class<?>> ADD_MAP = new HashMap<String, Class<?>>(1);

    private final static String QUERY_ID_NAME = "queryId";

    private final static String QUERY_ID_TYPE = "java.lang.String";

//    private final static String INTERFACE_URL_NAME = "interfaceUrl";

//    private final static String INTERFACE_URL_TYPE = "java.lang.String";

    private final static String TIEM_FORMAT = "yyMMddHHmmss";

    static {
        try {
            ADD_MAP.put(QUERY_ID_NAME, Class.forName(QUERY_ID_TYPE));
//            ADD_MAP.put(INTERFACE_URL_NAME, Class.forName(INTERFACE_URL_TYPE));
        } catch (ClassNotFoundException e) {
            LOGGER.error("初始化DsfDaCheckLogAspect查询id字段名失败");
        }
    }

    @Autowired
    private InsertAuditLogMQSender insertAuditLogMQSender;

    @Autowired
    private BdcDaCxService bdcDaCxService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BodyUtil bodyUtil;

    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug("开始档案日志保存切面处理");
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        DsfDaCheckLog annotation = signature.getMethod().getAnnotation(DsfDaCheckLog.class);
        Object[] args = pjp.getArgs();
        //获取是否验证标识
        Object data = pjp.proceed();
        if (annotation != null && StringUtils.isNotBlank(annotation.checkFlagClassName()) && StringUtils.isNotBlank(annotation.checkFlagLever()) && annotation.checkFlagIndex()!= 99 ) {
            String[] clazzs = annotation.checkFlagClassName().split(",");
            if (clazzs.length > 0 && clazzs.length > Integer.parseInt(annotation.checkFlagLever())) {
                String daCxFlag;
                try {
                    daCxFlag = getDaCxFlag(args[annotation.checkFlagIndex()], clazzs, 0, Integer.parseInt(annotation.checkFlagLever()),annotation.interfaceName());
                } catch (Exception e) {
                    LOGGER.error("获取是否校验档案查询报错", e);
                    return data;
                }
                if (daCxFlag != null && "true".equals(daCxFlag)) {
                    LOGGER.debug("当前接口:{}", annotation.interfaceFlagCode());
                    LOGGER.debug("当前接口需要保存档案查询日志");
                    //获取接口的归属方，生成唯一序列
                    String queryId = createQueryId(annotation.interfaceFlagCode());
                    String rzid = UUIDGenerator.generate16();
                    //返回查询标识
                    Object newData = initNewResponseData(data, queryId,annotation.interfaceUrl());
                    //保存日志 和 日志记录
                    try {
                        // 获取日志 实体
                        LogBO logBO = getLogBOFromDsfLog(annotation);
                        // 获取请求地址
                        AuditEventBO auditEventBO = new AuditEventBO(logBO);
                        auditEventBO.setRzid(rzid);
                        //指定新的es存储目录和日志类型
                        auditEventBO.setRzlx(Constants.EXCHANGE_ES_DACX_RZLX);
                        setRequest(auditEventBO, pjp,annotation.interfaceName());
                        if (newData != null) {
                            LOGGER.debug("添加查询id成功");
                            auditEventBO.setResponse(JSONObject.toJSONString(newData));
                            if (auditEventBO != null && auditEventBO.getLogBO() != null) {
                                //发送消息异步处理保存请求日志
                                LOGGER.debug("开始保存DsfDaCheckLogAspect注解请求日志");
                                String requestPath = CommonUtil.getCurrentRequestPath();
                                auditEventBO.getLogBO().setBdcdz(requestPath);
                                String userName = userManagerUtils.getCurrentUserName();
                                if (StringUtils.isNotBlank(userName)) {
                                    auditEventBO.setUsername(userName);
                                } else {
                                    auditEventBO.setUsername(CommonUtil.aopLogGetPrincipal());
                                }
                                Message<AuditEventBO> message = MessageBuilder.createMessage(auditEventBO, new MessageHeaders(new HashMap<>()));
                                //发送日志记录保存消息
                                insertAuditLogMQSender.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDJINSERTAUDITLOGQUEUE.getCode(), message);
                                BdcDaCxLog bdcDaCxLog = BdcDaCxLog.BdcDaCxLogBuilder.aBdcDaCxLog().withCjsj(new Date())
                                        .withDacxloglx("1")
                                        .withDacxlogzt("0")
                                        .withForeign(rzid)
                                        .withId(queryId)
                                        .withDags(auditEventBO.getUsername()).build();
                                bdcDaCxService.saveBdcDaCxLog(bdcDaCxLog);
                                LOGGER.debug("保存DsfDaCheckLogAspect注解请求日志成功");
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("构造日志实体异常", e);
                    }

                    return newData;
                }
            }
        }

        return data;
    }

    private Object initNewResponseData(Object data, String queryId,String interfaceUrl) throws Exception {
        Object newData;
        if (data instanceof Map) {
            ((Map) data).put(QUERY_ID_NAME, queryId);
//            ((Map) data).put(INTERFACE_URL_NAME, interfaceUrl);
            newData = data;
        } else if (data instanceof JSONObject) {
            ((JSONObject) data).put(QUERY_ID_NAME, queryId);
//            ((JSONObject) data).put(INTERFACE_URL_NAME, interfaceUrl);
            newData = data;
        } else {
            HashMap<String, String> addValMap = new HashMap<>(1);
            addValMap.put(QUERY_ID_NAME, queryId);
//            addValMap.put(INTERFACE_URL_NAME, interfaceUrl);
            newData = new ClassUtil().dynamicClass(data, ADD_MAP, addValMap);
        }
        return newData;
    }

    private String getDaCxFlag(Object fatherInstance, String[] clazzs, int i, int lever,String interfaceName) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (fatherInstance instanceof HttpServletRequest) {
            if (StringUtils.isNotBlank(interfaceName)){
                ParamBody paramBody = bodyUtil.getWwsqParamBody((HttpServletRequest) fatherInstance, interfaceName);
                if (paramBody != null && paramBody.getData() != null) {
                    Map<String, Object> paramMap = paramBody.getData();
                    return (String) paramMap.get("dacxFlag");
                }
            }
            return "false";
        } else {
            Class<?> clazz = Class.forName(clazzs[i]);
            int childrenIndex = i + 1;
            if (lever == i) {
                Method getDacxFlag = clazz.getMethod("getDacxFlag");
                return (String) getDacxFlag.invoke(fatherInstance);
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Class<?> returnType = method.getReturnType();
                String returnTypeName = returnType.getName();
                if (clazzs[childrenIndex].contains(returnTypeName)) {
                    Class<?> aClass = Class.forName(clazzs[childrenIndex]);
                    Object childInstance = method.invoke(fatherInstance);
                    if (childrenIndex < lever) {
                        return getDaCxFlag(childInstance, clazzs, childrenIndex, lever,interfaceName);
                    } else {
                        Method getDacxFlag = aClass.getMethod("getDacxFlag");
                        return (String) getDacxFlag.invoke(childInstance);
                    }
                }
            }
            return "false";
        }
    }

    private static String createQueryId(String dags) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIEM_FORMAT);
        StringBuffer stringBuffer = new StringBuffer();
        return stringBuffer.append(dags).append(UUIDGenerator.generate6()).append(simpleDateFormat.format(System.currentTimeMillis())).toString();
    }

    /**
     * @param auditEventBO
     * @param pjp
     * @return void
     * @description 保存请求信息
     */
    private void setRequest(AuditEventBO auditEventBO, ProceedingJoinPoint pjp,String interfaceName) {
        Object[] args = pjp.getArgs();
        if (args.length == 1 && args[0] instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) args[0];
            ParamBody paramBody = bodyUtil.getWwsqParamBody(request, interfaceName);
            auditEventBO.setRequest(JSON.toJSONString(paramBody));
//            try {
//                auditEventBO.setRequest(IOUtils.toString(request.getInputStream(),"UTF-8"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else if (args.length > 0) {
            Map data = new HashMap();
            for (int i = 0; i < args.length; i++) {
                Object o = args[i];
                // 如果请求参数 为 HttpServletRequest，从中获取参数
                if (!(o instanceof HttpServletResponse)
                        && !(o instanceof Model)
                        && !(o instanceof ModelAndView)
                        && !(o instanceof Authentication)
                        && !(o instanceof OAuth2Authentication)
                        && !(o instanceof RequestWrapper)) {
                    data.put("arg[" + i + "]", o);
                }
                if (o instanceof RequestWrapper) {
                    RequestWrapper requestWrapper = (RequestWrapper) o;
                    data.put("arg[" + i + "]", requestWrapper.getRequestBody());
                }
            }
            auditEventBO.setRequest(JSONObject.toJSONString(data));
        }
    }


    private LogBO getLogBOFromDsfLog(DsfDaCheckLog annotation) {
        LogBO logBO = new LogBO();
        logBO.setResponser(annotation.responser());
        logBO.setResponser(annotation.requester());
        logBO.setDsfFlag(annotation.dsfFlag());
        logBO.setLogEventName(annotation.logEventName());
        logBO.setLogService(annotation.logService());
        return logBO;
    }


}
