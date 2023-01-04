package cn.gtmap.realestate.etl.service;



import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/27
 * @description 配置服务
 */
public interface EtlConfigService {

    /**
     * @param param SQL以及参数
     * @return 执行结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 执行配置sql
     */
    List<Map> executeConfigSql(Map param);
}
