package cn.gtmap.realestate.natural.ui.service;

import java.util.List;
import java.util.Map;

/**
 * 动态查询，数据导出时，数据处理接口类
 */
public interface ZrzyDtcxDataFilterService {

    /**
     * 动态查询数据处理
     */
    void filterData(List<Map> dataMapList);
}
