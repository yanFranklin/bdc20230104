package cn.gtmap.realestate.init.mapper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行sql用Mapper文件
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/13.
 * @description
 */
public interface RunSqlMapper {
    /**
     * 运行sql
     *
     * @param paramMap
     * @return
     */
    List<HashMap> runSql(Map paramMap);
}
