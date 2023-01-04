package cn.gtmap.realestate.exchange.config.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:jpzhong1994@gmail.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/7 16:13
 * @description
 */
@Component
@WebFilter(urlPatterns = "/realestate-exchange/rest/v1.0/*", filterName = "logFilter")
public class LogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);
        StringUtils.equals("","");
        long startTime = System.currentTimeMillis();

        chain.doFilter(requestWrapper, responseWrapper);

        long endTime = System.currentTimeMillis();

        String requestUrl = httpServletRequest.getRequestURI();
        String requestMethod = httpServletRequest.getMethod();
        String responseTime = String.valueOf((endTime - startTime) / 1000);
        String clientIp = httpServletRequest.getRemoteAddr();
        String responseStatus = String.valueOf(httpServletResponse.getStatus());
        String requestParam = "";
        String responseResult = "";

        if ("POST".equals(requestMethod) || "DELETE".equals(requestMethod) || "PUT".equals(requestMethod)) {
            requestParam = requestWrapper.getRequestBody();
        } else {
            requestParam = JSONObject.toJSONString(httpServletRequest.getParameterMap());
        }
        byte[] bytes = responseWrapper.getBytes();
        responseResult = JSONObject.toJSONString(new String(bytes, StandardCharsets.UTF_8));

//        logger.info("------请求url:{},请求入参:{},客户端ip:{},请求方式:{},响应状态:{},响应结果:{},响应时长:{}",requestUrl,requestParam,clientIp,requestMethod,responseStatus,responseResult,responseTime);

        //将数据 再写到 response 中
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }

    @Override
    public void destroy() {
    }
}
