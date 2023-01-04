package cn.gtmap.realestate.building.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @program: realestate
 * @description: 楼盘表页面户室数据排序功能
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-02-23 16:49
 **/
@Component
@ConfigurationProperties(prefix = "lpb.view")
public class LpbViewSortConfig {
    private LinkedHashMap<String, String> sortmap = new LinkedHashMap<>();

    public LinkedHashMap<String, String> getSortmap() {
        return sortmap;
    }

    public void setSortmap(LinkedHashMap<String, String> sortmap) {
        this.sortmap = sortmap;
    }
}
