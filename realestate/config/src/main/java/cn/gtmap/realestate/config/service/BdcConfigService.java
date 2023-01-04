package cn.gtmap.realestate.config.service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/20
 * @description
 */
public interface BdcConfigService {

    /**
      * @param param 配置sql等参数
      * @return 执行结果
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 执行配置sql
      */
    List<Map> executeConfigSql(Map param);
}
