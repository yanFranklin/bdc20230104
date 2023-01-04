package cn.gtmap.realestate.certificate.core.model.dzzzgl;

import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/4 权限实体对象 spring容器管理 无数据库对应表
 */
public class InterfacePermission {

    private Map<String,String> yyqxMap;

    private Map<String,String> standingBookMap;

    private Map<String,String> regionUseMap;

    public Map<String, String> getYyqxMap() {
        return yyqxMap;
    }

    public void setYyqxMap(Map<String, String> yyqxMap) {
        this.yyqxMap = yyqxMap;
    }

    public Map<String, String> getStandingBookMap() {
        return standingBookMap;
    }

    public void setStandingBookMap(Map<String, String> standingBookMap) {
        this.standingBookMap = standingBookMap;
    }

    public Map<String, String> getRegionUseMap() {
        return regionUseMap;
    }

    public void setRegionUseMap(Map<String, String> regionUseMap) {
        this.regionUseMap = regionUseMap;
    }

    public List<Map> getYyqxLst() {
        List<Map> result = new ArrayList<>();
        if (MapUtils.isNotEmpty(yyqxMap)) {
            for (Map.Entry<String, String> entry : yyqxMap.entrySet()) {
                Map map = new HashMap<>(4);
                map.put("key",entry.getKey());
                map.put("value",entry.getValue());
                result.add(map);
            }
        }
        return result;
    }
}
