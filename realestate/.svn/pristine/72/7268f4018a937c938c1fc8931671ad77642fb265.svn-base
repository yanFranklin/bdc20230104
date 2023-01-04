package cn.gtmap.realestate.building.ui.config;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 楼盘表颜色配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-04-25 13:51
 **/
@Component
@ConfigurationProperties(prefix = "lpb.color")
public class LpbCololrConfig {

    private List<ColorCofig> colorConfigStatus;

    private Map<String, Object> htmlState;

    public static class ColorCofig {

        private String title;

        private String order;

        private boolean background;

        private Map<String, Object> status;

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public boolean isBackground() {
            return background;
        }

        public void setBackground(boolean background) {
            this.background = background;
        }

        public Map<String, Object> getStatus() {
            return status;
        }

        public void setStatus(Map<String, Object> status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void order(ColorCofig colorCofig) {
            if (StringUtils.isNotBlank(colorCofig.order)) {
                LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>(1);
                for (String order : colorCofig.order.split(CommonConstantUtils.ZF_YW_DH)) {
                    resultMap.put(order, colorCofig.getStatus().get(order));
                }
                colorCofig.status = resultMap;
            }
        }
    }

    public List<ColorCofig> getColorConfigStatus() {
        if (CollectionUtils.isNotEmpty(colorConfigStatus)) {
            this.colorConfigStatus.forEach(colorCofig -> colorCofig.order(colorCofig));
        }
        return colorConfigStatus;
    }

    public void setColorConfigStatus(List<ColorCofig> colorConfigStatus) {
        this.colorConfigStatus = colorConfigStatus;
    }

    public Map<String, Object> getHtmlState() {
        return htmlState;
    }

    public void setHtmlState(Map<String, Object> htmlState) {
        this.htmlState = htmlState;
    }
}
