package cn.gtmap.realestate.common.config.logaop;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.enums.LogKeyEnum;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.MapUtils;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/6/3
 * <p>
 *     LogApiAspect 对 @ApiImplicitParams 注解的方法进行日志记录
 * </p>
 */
@Component
@Aspect
public class LogApiAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogApiAspect.class);

    private static final String UNKNOWN = "unknown";

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Pointcut("@annotation(apiImplicitParams)")
    public void logApiPointCut(ApiImplicitParams apiImplicitParams) {

    }

    @AfterReturning(
            returning = "response",
            pointcut = "logApiPointCut(apiImplicitParams)"
    )

    /**
     *  记录日志切面
     */
    public void doAfter(JoinPoint joinPoint, ApiImplicitParams apiImplicitParams, Object response) {
        if(MapUtils.isEmpty(LogCommonCacheMap.methodLogEntity)){
            return;
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        /**
         * 1、当方法上拥有@LogNote自定义日志注解时，不记录日志
         * 2、没有 @ApiOpration 注解或注解描述为空，不记录日志
         * 3、@ApiOpration 中 extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false") 不记录日志
         */
        if(!this.checkRecordLod(joinPoint, methodSignature)){
            return;
        }

        // 解析查询参数
        JSONObject paramObj = analysisMethodParam(methodSignature.getParameterNames(), joinPoint.getArgs());
        Map<String, Object> data = getAuditEventParam(new String[0], paramObj);

        // 方法名-->资源台账实体
        try{
            data.put(LogKeyEnum.METHOD_NAME.getKey(), joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            data.put(CommonConstantUtils.CONTROLLER_METHOD_NAME, methodSignature.getName());
        }catch (Exception e){
            data.put(LogKeyEnum.METHOD_NAME.getKey(), "");
            data.put(CommonConstantUtils.CONTROLLER_METHOD_NAME, UNKNOWN);
        }

        // 模块代码
        try {
            String mkdm = methodSignature.toString().split("\\.")[3].toUpperCase();
            data.put(CommonConstantUtils.VIEW_TYPE, mkdm);
        } catch (Exception e) {
            data.put(CommonConstantUtils.VIEW_TYPE, UNKNOWN);
        }
        // 操作机器 ip
        data.put(CommonConstantUtils.IP, getRealIPAddress());

        // 模块名称
        String mkmc = LogCommonCacheMap.methodLogEntity.get(methodSignature.getName());
        if(StringUtils.isNotBlank(mkmc)){
            data.put(CommonConstantUtils.VIEW_TYPE_NAME, mkmc);
        }

        // 日志类型： 根据描述解析调用者的行为动作
        String action = StringUtils.isEmpty(mkmc) ? "OTHER" : analysisEventAction(mkmc);

        // 用户真实姓名
        String userName = userManagerUtils.getCurrentUserName();
        UserDto userDto = userManagerUtils.getUserByName(userName);
        if(null != userDto){
            data.put(CommonConstantUtils.ALIAS, userDto.getAlias());
        }

        // 方法返回结果 不在记录返回值
        // data.put(CommonConstantUtils.RESPONSE, response);

        try {
            AuditEvent auditEvent = new AuditEvent(userName, action, data);
            zipkinAuditEventRepository.add(auditEvent);
        }catch(Exception e){
            LOGGER.error("日志记录接口出错，异常信息：{} ", e.getMessage());
        }finally {
            LogCommonCacheMap.methodLogEntity.clear();
        }
    }


    /**
     * 判断是否进行日志记录(true:记录日志， false:不记录日志)
     * 1、当方法上拥有@LogNote自定义日志注解时，不在对@ApiImplicitParams注解进行日志记录的行为
     */
    private boolean checkRecordLod(JoinPoint joinPoint, MethodSignature methodSignature){
        //当方法上拥有@LogNote自定义日志注解时，不在对@ApiImplicitParams注解进行日志记录的行为
        try {
            Object target = joinPoint.getTarget();
            Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            if(null != method.getAnnotation(LogNote.class)){
                return false;
            }
        } catch (NoSuchMethodException e) {
            LOGGER.error("未获取到当前签名: {} 的方法", methodSignature.getName());
            return false;
        }

        try{
            // 当没有apiOpration注解 或者注解描述为空，就不记录日志
            if (!LogCommonCacheMap.methodLogEntity.containsKey(methodSignature.getName()) ||
                    StringUtils.isEmpty(LogCommonCacheMap.methodLogEntity.get(methodSignature.getName()))) {
                LOGGER.debug("{},方法缺少日志注解!" , methodSignature.getName());
                LogCommonCacheMap.methodLogEntity.clear();
                return false;
            }
            // 当apiOpration注解的宽展属性 记录日志的配置 为false 则不记录日志
            if (LogCommonCacheMap.methodLogEntity.containsKey(methodSignature.getName()+"_saveLog") &&
                    LogCommonCacheMap.methodLogEntity.get(methodSignature.getName()+"_saveLog").equals("false")) {
                return false;
            }
        }catch(Exception e){
            LOGGER.error("判断是否记录日志信息时异常，方法名为：{}", methodSignature.getName());
            return false;
        }
        return true;
    }

    /**
     * version 1.0
     *
     * @param
     * @return
     * @description 获取平台日志保存参数
     * @date 2019/3/14
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private Map<String, Object> getAuditEventParam(String[] names, JSONObject param) {
        Map<String, Object> map = Maps.newHashMap();
        // 配置文件中配置的需要保存到日志中的参数
        Map<String, Object> tempMap = JSONObject.parseObject(param.toJSONString(), Map.class);
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                if (!tempMap.containsKey(names[i])) {
                    tempMap.remove(names[i]);
                }
            }
        }
        for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
            map.put(entry.getKey(), tempMap.get(entry.getKey()));
        }
        if (param.containsKey(CommonConstantUtils.VIEW_TYPE)) {
            map.put(CommonConstantUtils.VIEW_TYPE, tempMap.get(CommonConstantUtils.VIEW_TYPE));
        }
        if (param.containsKey(CommonConstantUtils.CLIENT_IP)) {
            map.put(CommonConstantUtils.CLIENT_IP, tempMap.get(CommonConstantUtils.CLIENT_IP));
        }
        return map;
    }

    /**
     * version 1.0
     *
     * @param paramNames 请求参数名数组
     * @param paramValue 请求参数值数组
     * @date 2019/3/15
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private JSONObject analysisMethodParam(String[] paramNames, Object[] paramValue) {
        JSONObject param = new JSONObject();
        if (paramNames != null && paramValue != null) {
            for (int i = 0; i < paramValue.length; i++) {
                if (paramValue[i] != null
                        && !(paramValue[i] instanceof HttpServletRequest)
                        && !(paramValue[i] instanceof HttpServletResponse)
                        && !(paramValue[i] instanceof Model)
                        && !(paramValue[i] instanceof ModelAndView)
                        && !(paramValue[i] instanceof Authentication)
                        && !(paramValue[i] instanceof OAuth2Authentication)) {
                    if (CommonUtil.isJSONObject(JSONObject.toJSONString(paramValue[i]))) {
                        recursiveMethodParam(param, paramNames[i], JSONObject.toJSONString(paramValue[i]));
                    } else {
                        recursiveMethodParam(param, paramNames[i], paramValue[i].toString());
                    }
                }
            }
        }
        return param;
    }

    /**
     * version 1.0
     *
     * @return
     * @description 递归查询封装参数 去掉空值
     * @date 2019/3/14
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    private void recursiveMethodParam(JSONObject result, String paramName, String jsonStr) {
        if (result == null) {
            result = new JSONObject();
        }
        if (StringUtils.isNotBlank(jsonStr)) {
            if (CommonUtil.isJSONObject(jsonStr)) {
                JSONObject temp = JSONObject.parseObject(jsonStr);
                if (temp != null) {
                    for (Map.Entry<String, Object> entry : temp.entrySet()) {
                        if(Objects.nonNull(entry.getKey()) && Objects.nonNull(temp.get(entry.getKey()))){
                            recursiveMethodParam(result, entry.getKey(), temp.get(entry.getKey()).toString());
                        }
                    }
                }
            } else if (CommonUtil.isJSONOArray(jsonStr)) {
                List tempList = JSONArray.parseArray(jsonStr);
                // 标记为查询参数 数组参数
                result.put(paramName, StringUtils.join(tempList, ","));
            } else {
                result.put(paramName, jsonStr);
            }
        }
    }

    /**
     * 获取本机的ip
     */
    private static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (!netInterface.isLoopback() && !netInterface.isVirtual() && !netInterface.isPointToPoint() &&
                        netInterface.isUp()) {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取本机ip方法异常！");
        }
        return "";
    }

    /**
     * 获取真实ip
     */
    private static String getRealIPAddress() {
        String ip = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
            // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
            ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp =  ips[index];
                    if (!(UNKNOWN.equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
        }
        return ip;
    }

    /**
     * 根据方法描述 分析动作类型
     */
    private static String analysisEventAction(String description) {
        String action = "";
        if (description.indexOf("查询") != -1 || description.indexOf("获取") != -1) {
            action = "QUERY";
        } else if (description.indexOf("导出") != -1 || description.indexOf("下载") != -1) {
            action = "EXPORT";
        } else if (description.indexOf("上传") != -1) {
            action = "UPLOAD";
        } else if (description.indexOf("保存") != -1 || description.indexOf("记录") != -1) {
            action = "SAVE";
        } else if (description.indexOf("删除") != -1) {
            action = "DELETE";
        } else if (description.indexOf("新增") != -1 || description.indexOf("生成") != -1 || description.indexOf("创建") != -1 ||
                description.indexOf("新建") != -1) {
            action = "CREATE";
        } else if (description.indexOf("打印") != -1) {
            action = "PRINT";
        } else if (description.indexOf("更新") != -1 || description.indexOf("同步") != -1 || description.indexOf("修改") != -1) {
            action = "UPDATE";
        } else if(description.indexOf("初始化") != -1){
            action = "INIT";
        }else {
            action = "OTHER";
        }
        return action;
    }

}

