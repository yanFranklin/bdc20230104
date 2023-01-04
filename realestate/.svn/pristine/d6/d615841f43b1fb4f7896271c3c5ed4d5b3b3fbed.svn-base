package cn.gtmap.realestate.exchange.config;

import cn.gtmap.realestate.common.core.dto.RequestIpDTO;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/1/3
 * @description
 */
@Component
public class GetIpInterceptor implements HandlerInterceptor {
    @Autowired
    private PathLimitConfig pathLimitConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetIpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前请求的url是否需要呗拦截，需要在做操作
        PathMatcher matcher = new AntPathMatcher();
        boolean interceptFlag = false;
        if (pathLimitConfig.getLimit() == null) {
            return true;
        }
        for (Map.Entry<String, Map<String, List>> entry : pathLimitConfig.getLimit().entrySet()) {
            List<String> pathList = entry.getValue().get("path");
            if (CollectionUtils.isEmpty(pathList)){
                pathList = new ArrayList<>();
            }
            List<String> ignorePathList = entry.getValue().get("ignore");
            if (CollectionUtils.isEmpty(ignorePathList)){
                ignorePathList = new ArrayList<>();
            }
            Integer trytimes = 10;
            if (entry.getValue().get("trytimes").size() > 0) {
                trytimes = (Integer) entry.getValue().get("trytimes").get(0);
            }
            Integer trytime = 3;
            if (entry.getValue().get("trytime").size() > 0) {
                trytime = (Integer) entry.getValue().get("trytime").get(0);
            }

            for (String path : pathList) {
                if (matcher.match(path, request.getRequestURI())) {
                    interceptFlag = true;
                    for (String ignorepath : ignorePathList) {
                        if (matcher.match(ignorepath, request.getRequestURI())) {
                            interceptFlag = false;
                            break;
                        }
                    }
                    break;
                }
            }
            // 当在拦截列表中时
            if (interceptFlag) {
                //返回给页面显示
                PrintWriter out;
                Map<String, Object> resultMap = new HashMap();
                //取用户的真实IP
                String ip = request.getHeader("x-forwarded-for");

                if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
                    ip = request.getHeader(" Proxy-Client-IP ");
                }
                if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
                    ip = request.getHeader(" WL-Proxy-Client-IP ");
                }
                if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
                ip += request.getRequestURI();
                //取session中的IP对象
                RequestIpDTO re = (RequestIpDTO) request.getSession().getAttribute(ip);
                //第一次请求
                if (null == re) {
                    //放入到session中
                    RequestIpDTO reIp = new RequestIpDTO();
                    reIp.setCreateTime(System.currentTimeMillis());
                    reIp.setReCount(1);
                    request.getSession().setAttribute(ip, reIp);
                    return true;
                } else {
                    Long createTime = re.getCreateTime();
                    if (null == createTime) {
                        //时间请求为空
                        resultMap.put("code", 503);
                        resultMap.put("message", "请求太快，请稍后再试！");
                        out = response.getWriter();
                        JSONObject json = (JSONObject) JSONObject.toJSON(resultMap);
                        out.append(json.toString());
                        return false;
                    } else {
                        if (((System.currentTimeMillis() - createTime) / 1000) >= trytime) {
                            LOGGER.info("通过请求：" + request.getRequestURI() + " 距首次请求时长：" + ((System.currentTimeMillis() - createTime) / 1000));
                            //当前时间离上一次请求时间大于限定时间，可以直接通过,保存这次的请求
                            RequestIpDTO reIp = new RequestIpDTO();
                            reIp.setCreateTime(System.currentTimeMillis());
                            reIp.setReCount(1);
                            request.getSession().setAttribute(ip, reIp);
                            return true;
                        } else {
                            //小于限定时间，并且限定时间之内请求超过了限定次数，返回提示
                            if (re.getReCount() >= trytimes) {
                                resultMap.put("code", 504);
                                resultMap.put("message", "请求太快，请稍后再试！");
                                out = response.getWriter();
                                JSONObject json = (JSONObject) JSONObject.toJSON(resultMap);
                                //以json形式返回给页面，也可以直接返回提示信息
                                out.append(json.toString());
                                return false;
                            } else {
                                //小于限定时间，但请求数小于限定次数，给对象添加次数
                                re.setCreateTime(System.currentTimeMillis());
                                re.setReCount(re.getReCount() + 1);
                                request.getSession().setAttribute(ip, re);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
