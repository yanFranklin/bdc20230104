package cn.gtmap.realestate.building.ui.config;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @program: realestate
 * @description: 导出楼盘表自定义字段配置和读取值
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-01-27 14:54
 **/
@Component
@ConfigurationProperties(prefix = "lpb")
public class LpbExportColumConfig {
    private Logger LOGGER = LoggerFactory.getLogger(LpbExportColumConfig.class);
    /**
     * @modify apollo无序，改成字符串内部处理
     */
    private String exportColum;

    //如果为空需要默认值为0 的字段
    private String default0Column;

    public LinkedHashMap<String, String> getExportColumMap() {
        LinkedHashMap<String, String> exportColumMap = new LinkedHashMap<>();
        try {
            if (StrUtil.isNotEmpty(exportColum)) {
                String[] columItemStrings = exportColum.split(",");
                for (String columItemString : columItemStrings) {
                    String[] colum = columItemString.split(":");
                    if (columItemString.indexOf(':') >= 0 && (!columItemString.endsWith(":"))) {
                        exportColumMap.put(StrUtil.trim(colum[0]), colum[1]);
                    } else if (columItemString.indexOf(':') >= 0) {
                        exportColumMap.put(StrUtil.trim(colum[0]), "");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.warn("exportColum:{} error：{}", exportColum, e.getMessage());
        }
        return exportColumMap;
    }

    public void setExportColum(String exportColum) {
        this.exportColum = exportColum;
    }

    public String getDefault0Column() {
        return default0Column;
    }

    public void setDefault0Column(String default0Column) {
        this.default0Column = default0Column;
    }
}
