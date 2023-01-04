package cn.gtmap.realestate.accept.core.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @注释
 * @作者 gln
 * @创建日期 2019/5/27
 * @创建时间 17:08
 * @版本号 V 1.0
 */
@Repository
public interface BdcSlYzSqlMapper {
    /**
     * @param checkMap 验证的sql和参数
     * @return false/true
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据参数执行sql
     */
    List<Map> runConfigSql(Map<String,String> checkMap);
}
