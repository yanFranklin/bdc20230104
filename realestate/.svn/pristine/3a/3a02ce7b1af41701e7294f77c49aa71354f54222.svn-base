package cn.gtmap.realestate.accept.ui.config;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/4/28
 * @description 南通获取水电气配置
 */
@Component
@ConfigurationProperties(prefix = "sdq")
public class SdqConfig {
    /**
     * 供水
     */
    private Map<String,Map<String,UnitInfo>> gsdwxx;
    /**
     * 供气
     */
    private Map<String,Map<String,UnitInfo>> gqdwxx;


    public static class UnitInfo{
        /**
         * 单位名称
         */
        private String dwmc;
        /**
         * 是否核验
         */
        private boolean sfhy;
        /**
         * 推送参数方式
         */
        private Integer pushStyle;


        public String getDwmc() {
            return dwmc;
        }

        public void setDwmc(String dwmc) {
            this.dwmc = dwmc;
        }

        public boolean isSfhy() {
            return sfhy;
        }

        public void setSfhy(boolean sfhy) {
            this.sfhy = sfhy;
        }

        public Integer getPushStyle() {
            return pushStyle;
        }

        public void setPushStyle(Integer pushStyle) {
            this.pushStyle = pushStyle;
        }

        @Override
        public String toString() {
            return "UnitInfo{" +
                    "dwmc='" + dwmc + '\'' +
                    ", sfhy=" + sfhy +
                    ", pushStyle=" + pushStyle +
                    '}';
        }
    }

    public Map<String, Map<String, UnitInfo>> getGsdwxx() {
        return gsdwxx;
    }

    public void setGsdwxx(Map<String, Map<String, UnitInfo>> gsdwxx) {
        this.gsdwxx = gsdwxx;
    }

    public Map<String, Map<String, UnitInfo>> getGqdwxx() {
        return gqdwxx;
    }

    public void setGqdwxx(Map<String, Map<String, UnitInfo>> gqdwxx) {
        this.gqdwxx = gqdwxx;
    }

    @Override
    public String toString() {
        return "SdqConfig{" +
                "gsdwxx=" + gsdwxx +
                ", gqdwxx=" + gqdwxx +
                '}';
    }
}
