package cn.gtmap.realestate.common.core.service.rest.init;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * @param
 * @return
 * @author <a href ="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @description 执行sql
 */
public interface BdcRunSqlRestService {
    /**
     * @param
     * @return
     * @author <a href ="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 执行sql
     */
    @GetMapping("/init/rest/v1.0/runsql")
    List<HashMap> runSql(@RequestParam("sql") String sql);
}
