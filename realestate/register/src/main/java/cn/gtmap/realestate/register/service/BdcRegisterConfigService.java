package cn.gtmap.realestate.register.service;

import java.util.List;
import java.util.Map;
/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/4/11
 * @description 默认意见业务类
 */
public interface BdcRegisterConfigService {
    /**
     * @param param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 执行 配置 sql
     */
    List<Map> executeConfigSql(Map param);
}
