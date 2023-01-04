package cn.gtmap.realestate.init.service.sendMsg;

import java.util.HashMap;
import java.util.List;

public interface BdcRunSqlService {

    /**
     * @param
     * @return
     * @author <a href ="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 执行sql
     */
    List<HashMap> runSql(String sql);
}
