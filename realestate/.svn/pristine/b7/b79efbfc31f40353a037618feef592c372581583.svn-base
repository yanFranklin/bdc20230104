package cn.gtmap.realestate.common.config.logaop;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.config.mq.sender.LogRecordMsgSender;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.enums.LogKeyEnum;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.IPUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/9/25.
 * @description 自定义日志收集切面，对使用@LogNote注解的方法进行日志采集。value:功能描述, action:日志事件类型, custom:自定义日志记录实现类
 */
@Component
@Aspect
public class LogNoteAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogNoteAspect.class);

    /**
     * 需要记录为查询参数的值
     */
    private static final String[] ADD_PARAM_NAME = {"gzlslid", "processInstanceId", "bdcdyh", "slbh", "taskId", "reason", "xmid"};

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private LogRecordMsgSender logRecordMsgSender;

    private ExecutorService executor = new ThreadPoolExecutor(
            // 核心线程数量
            1,
            // 最大线程数
            10,
            // 超时30秒
            30, TimeUnit.SECONDS,
            // 最大允许等待200个任务
            new ArrayBlockingQueue<>(200),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Pointcut(value = "@annotation(logNote)")
    public void pointCut(LogNote logNote){
    }

    @AfterReturning(
            returning = "response",
            pointcut = "pointCut(logNote)"
    )
    public void doRecordLog(JoinPoint point, LogNote logNote, Object response){
        String userName = userManagerUtils.getCurrentUserName();
        String ip = IPUtils.getClientRealIP();
        executor.submit(new LogRecordThread(point, logNote, response, userName, ip));
    }

    class LogRecordThread implements Runnable{

        private JoinPoint point;
        private LogNote logNote;
        // 方法返回值
        private Object response;
        private String userName;
        private String ip;

        public LogRecordThread(JoinPoint point, LogNote logNote, Object response, String userName, String ip){
            this.point = point;
            this.logNote = logNote;
            this.response = response;
            this.userName = userName;
            this.ip = ip;
        }

        @Override
        public void run() {
            logRecord(point, logNote, response, userName, ip);
        }
    }

    /**
     * 记录日志内容方法
     */
    private void logRecord(JoinPoint point, LogNote logNote, Object response, String userName, String ip){
        try{
            // 包装AuditEvent需要的动作数据
            Map<String, Object> data = new HashMap<String, Object>(3);
            data.put(LogKeyEnum.VIEW_TYPE_NAME.getKey(), logNote.value());
            // 记录请求参数
            data.put(LogKeyEnum.METHOD_ARGS.getKey(), JSON.toJSONString(point.getArgs()));
            // 记录方法名
            String methodName = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
            data.put(LogKeyEnum.METHOD_NAME.getKey(), methodName);
            // 记录方法返回值
            data.put(LogKeyEnum.METHOD_RESPONSE.getKey(), JSON.toJSONString(response));
            // 记录请求IP
            data.put(LogKeyEnum.IP.getKey(), ip);
            // 记录日志操作时间
            data.put(LogKeyEnum.TIME.getKey(), System.currentTimeMillis());
            // 获取请求参数中的 gzlslid、bdcdyh、slbh 信息用于搜索
            Map<String,Object> paramMap = this.resolveRequestParam(point);
            if(null != paramMap && !paramMap.isEmpty()){
                data.putAll(paramMap);
            }

            // 获取当前操作人名称
            UserDto userDto = userManagerUtils.getUserByName(userName);
            if (userDto != null) {
                data.put(LogKeyEnum.ALIAS.getKey(), userDto.getAlias());
            }

            // 自定义日志记录，采用方式
            if(StringUtils.isNotBlank(logNote.custom())){
                LogRecordDTO logRecordDTO = new LogRecordDTO();
                logRecordDTO.setLogType(logNote.custom());
                logRecordDTO.setLogAction(logNote.action());
                logRecordDTO.setUserName(userName);
                logRecordDTO.setParamMap(data);
                logRecordMsgSender.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.LOGRECORDQUEUE.getCode(), JSONObject.toJSONString(logRecordDTO));
            }else{
                // 记录ES日志
                String logType = StringUtils.isEmpty(logNote.action())? "OTHER" : logNote.action();
                zipkinAuditEventRepository.add(new AuditEvent(userName, logType, data));
            }
        }catch(Exception e){
            LOGGER.error("@LogNote切面记录日志信息出错，{}", e.getMessage());
        }
    }

    /**
     * 获取入参解析入参内容，将入参中存在 gzlslid,bdcdyh,slbh 等信息，添加至AuditEvent 的dataMap中，用于支持后续日志查询条件
     */
    private Map<String, Object> resolveRequestParam(JoinPoint point){
        Map<String, Object> paramMap = new HashMap<>();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String[] paramNames = methodSignature.getParameterNames();
        try{
            Object[] args =  point.getArgs();
            for (int i = 0; i < args.length; i++) {
                // 参数值为String类型，添加到dataMap 中
                if(args[i] instanceof String){
                    paramMap.put(paramNames[i], args[i].toString());
                    continue;
                }
                if(args[i] instanceof Integer){
                    paramMap.put(paramNames[i], ((Integer) args[i]).intValue());
                    continue;
                }
                if(args[i] instanceof  Boolean){
                    paramMap.put(paramNames[i], ((Boolean) args[i]).booleanValue());
                    continue;
                }
                if(args[i] instanceof Double){
                    paramMap.put(paramNames[i], ((Double) args[i]).doubleValue());
                    continue;
                }
                if(!(args[i] instanceof List)){
                    try{
                        JSONObject argsJson = (JSONObject) JSON.parse(JSON.toJSONString(args[i]));
                        if (null != argsJson) {
                            for(String paramName : ADD_PARAM_NAME){
                                if(argsJson.containsKey(paramName)){
                                    paramMap.put(paramName, argsJson.get(paramName));
                                }
                            }
                        }
                    }catch(Exception e){}
                }
            }
        }catch(Exception e){
            LOGGER.error("@LogNote切面解析入参出错，{}", e.getMessage());
        }
        return paramMap;
    }

}
