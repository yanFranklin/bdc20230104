package cn.gtmap.realestate.common.core.service;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/3/30
 * @description
 */
public interface BdcDyConfigService {
    List<Map> executeConfigSql(Map configParam, String dbsource);
}
