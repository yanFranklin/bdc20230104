package cn.gtmap.realestate.common.config.interceptor;

import cn.gtmap.realestate.common.config.LogSwitchConfig;
import cn.gtmap.server.core.send.SendRedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(LogSwitchConfig.getInstance().isSpringSwitch()){
            try {
                SendRedisFactory.sendMsgToRedis(request, handler, "INTERCEPTOR", this.appName);
            } catch (Exception var5) {
                LOGGER.error("日志拦截器<WebInterceptor>异常:{}", var5);
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
