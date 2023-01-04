package cn.gtmap.realestate.common.core.service.rest.building;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/8/3
 * @description 配置服务
 */
public interface BuildingConfigRestService {

    /**
     * @param param SQL以及参数(多数据源需要传qjgldm)
     * @return 执行结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 执行配置sql
     */
    @PostMapping("/building/rest/v1.0/config/sql")
    List<Map> executeConfigSql(@RequestBody Map param);
}
