package cn.gtmap.realestate.common.config.interceptor;

import cn.gtmap.realestate.common.config.LogSwitchConfig;
import cn.gtmap.server.core.dto.BaseLogMessage;
import cn.gtmap.server.core.dto.RunLogMessage;
import cn.gtmap.server.core.factory.MessageAppenderFactory;
import cn.gtmap.server.core.send.SendRedisFactory;
import cn.gtmap.server.logback.utils.LogMessageUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 日志采集 Web请求拦截器
 * @version 1.0, 2022/4/11
 */
public class WebLogInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogInterceptor.class);

    private String appName;

    public WebLogInterceptor() {
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(LogSwitchConfig.getInstance().isSpringSwitch()){
            try {
//                SendRedisFactory.sendMsgToRedis(request, handler, "INTERCEPTOR", this.appName);
                HandlerMethod method = null;
                if (handler instanceof HandlerMethod) {
                    method = (HandlerMethod)handler;
                }
                BaseLogMessage logMessage = LogMessageUtil.getLogMessage(appName, request, method, "INTERCEPTOR");
                RunLogMessage runLogMessage = (RunLogMessage)logMessage;
                if (StringUtils.isNotBlank(runLogMessage.getContent()) && !StringUtils.equals(runLogMessage.getContent(), "{}")) {
                    MessageAppenderFactory.pushRundataQueue(JSONObject.toJSONString(getMDC(logMessage)));
                }
            } catch (Exception var5) {
                LOGGER.error("日志拦截器<WebInterceptor>异常:{}", var5);
            }
        }
        return true;
    }

    private static Map<String, String> getMDC(BaseLogMessage logMessage) {
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        Map<String, String> map = (Map)JSONObject.parseObject(JSONObject.toJSONString(logMessage), Map.class);
        if (mdc != null) {
            mdc.putAll(map);
        }

        return mdc;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
