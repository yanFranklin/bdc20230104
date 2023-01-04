package cn.gtmap.realestate.portal.ui.config;

import cn.gtmap.realestate.common.util.IPPortUtils;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/28.
 * @description
 */
@Component
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Resource
    private MyWebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(handler, "/wsInfo").addInterceptors(new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
                HttpServletRequest httpServletRequest=((ServletServerHttpRequest) request).getServletRequest();
                String username = httpServletRequest.getParameter("username");
                String type = httpServletRequest.getParameter("type");
                String ip= IPPortUtils.getClientIp(httpServletRequest);
                if (StringUtils.isNotBlank(username)) {
                    if(StringUtils.isBlank(type)){
                        map.put("username", ip+ Constants.UNDERLINE+username);
                    }else{
                        map.put("username", ip+ Constants.UNDERLINE+username+ Constants.UNDERLINE+type);
                    }
                } else {
                    return false;
                }
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

            }
        });
    }
}