package cn.gtmap.realestate.common.core.dao;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/17
 * @description 执行配置sql
 */
public interface BdcConfigMapper {
    /**
     * @param param
     * @return List<Map>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 执行配置sql
     */
    List<Map> executeConfigSql(Map param);
}
