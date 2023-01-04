package cn.gtmap.realestate.portal.ui.config;

import cn.gtmap.realestate.portal.ui.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/28.
 * @description
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyWebSocketHandler.class);
    /**
     * 在线用户列表
     */
    private static final ConcurrentHashMap<String, WebSocketSession> USER_MAP = new ConcurrentHashMap<>(512);

    /**
     * 消息发送处理共享线程池定义
     */
    private final ExecutorService executor = new ThreadPoolExecutor(
            // 核心线程数量
            4,
            // 最大线程数
            6,
            // 超时30秒
            30, TimeUnit.SECONDS,
            // 最大允许等待200个任务
            new ArrayBlockingQueue<>(200),
            // 过多任务直接抛弃
            new ThreadPoolExecutor.DiscardPolicy()
    );

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        Map<String, Object> map = webSocketSession.getAttributes();
        String username = (String) map.get("username");
        if (StringUtils.isNoneBlank(username)) {
            USER_MAP.put(username, webSocketSession);
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        Map<String, Object> map = webSocketSession.getAttributes();
        String username = (String) map.get("username");
        if (StringUtils.isNoneBlank(username)) {
            USER_MAP.remove(username);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送消息到 HTML
     *
     * @param message 消息内容
     * @param type    消息接收客户端
     */
    public void sendMsgToHtml(final TextMessage message, String type) {
        // type 不是以 _ 开头则 属于用户踢出功能，消息通知需要全部匹配再发送
        if (!type.startsWith(Constants.UNDERLINE)) {
            executeSend(message, USER_MAP.get(type));
        } else {
            for (Map.Entry<String, WebSocketSession> entry : USER_MAP.entrySet()) {
                if (null != entry && null != entry.getValue()
                        && entry.getKey().endsWith(type) && entry.getValue().isOpen()) {
                    executeSend(message, entry.getValue());
                }
            }
        }
    }

    /**
     * 使用线程池发送信息给指定用户
     *
     * @param message          发送的信息
     * @param webSocketSession webSocketSession
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private void executeSend(TextMessage message, WebSocketSession webSocketSession) {
        if (null == webSocketSession || !webSocketSession.isOpen()) {
            return;
        }
        executor.submit(() -> {
            // 确保连接未断开
            if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(message);
                } catch (IOException e) {
                    LOGGER.error("消息发送错误", e);
                }
            }
        });
    }
}