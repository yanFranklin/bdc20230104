package cn.gtmap.realestate.building.ui.util;

import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import cn.hutool.core.map.MapUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Comparator;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-02
 * @description 为 fwhsList 排序
 */
public class FwhsResComparator implements Comparator<ResourceDTO> {

    private Map<String, String> sortMap;

    public FwhsResComparator(Map<String, String> sortMap) {
        this.sortMap = sortMap;
    }

    @Override
    public int compare(ResourceDTO o1, ResourceDTO o2) {
        int result = 0;
        if (MapUtil.isEmpty(sortMap)) {
            sortMap.put("sxh", "asc");
        }
        for (String sort : sortMap.keySet()) {
            String sx = sortMap.get(sort);
            if (o1 != null && o2 != null && result == 0) {
                String o1Val = MapUtils.getString(MapUtils.getMap(o1.getInfo(), sort), "value", "1");
                String o2Val = MapUtils.getString(MapUtils.getMap(o2.getInfo(), sort), "value", "1");
                if (StringUtils.isBlank(o1Val) && StringUtils.isBlank(o2Val)) {
                    result = 0;
                }
                if (StringUtils.equals("desc", sx)) {
                    if (StringUtils.isBlank(o1Val)) {
                        result = 1;
                    }
                    if (StringUtils.isBlank(o2Val)) {
                        result = -1;
                    }
                    if (StringUtils.isNotBlank(o1Val)
                            && StringUtils.isNotBlank(o2Val)
                            && NumberUtils.isNumber(o1Val)
                            && NumberUtils.isNumber(o2Val)) {
                        result = Integer.parseInt(o2Val) - Integer.parseInt(o1Val);
                    } else if (StringUtils.isNotBlank(o1Val)
                            && StringUtils.isNotBlank(o2Val)
                            && !NumberUtils.isNumber(o1Val)
                            && !NumberUtils.isNumber(o2Val)) {
                        result = o2Val.compareTo(o1Val);
                    }
                } else {
                    if (StringUtils.isBlank(o1Val)) {
                        result = -1;
                    }
                    if (StringUtils.isBlank(o2Val)) {
                        result = 1;
                    }
                    if (StringUtils.isNotBlank(o1Val)
                            && StringUtils.isNotBlank(o2Val)
                            && NumberUtils.isNumber(o1Val)
                            && NumberUtils.isNumber(o2Val)) {
                        result = Integer.parseInt(o1Val) - Integer.parseInt(o2Val);
                    } else if (StringUtils.isNotBlank(o1Val)
                            && StringUtils.isNotBlank(o2Val)
                            && !NumberUtils.isNumber(o1Val)
                            && !NumberUtils.isNumber(o2Val)) {
                        result = o1Val.compareTo(o2Val);
                    }
                }
            }
            if (result != 0) {
                return result;
            }
        }
        return result;
    }
}
