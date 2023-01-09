package cn.gtmap.realestate.inquiry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogRecordConfig {

    /**
     * 日志精简模式， true:开启精简模式， false:关闭精简模式
     * <p>精简模式，日志内容会减少，不在根据gzlslid、xmid等信息查询项目内容。</p>
     */
    @Value("${log.compact:false}")
    private boolean compactMode;

    public boolean isCompactMode() {
        return compactMode;
    }

    public void setCompactMode(boolean compactMode) {
        this.compactMode = compactMode;
    }
}
