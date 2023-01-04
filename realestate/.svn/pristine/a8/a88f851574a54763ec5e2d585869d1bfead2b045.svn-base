package cn.gtmap.realestate.natural.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author by hy.
 * @version 1.0, 2018/8/30
 * @description
 */
public interface DtcxViewService {

    /**
     * hy 通过查询信息生成SQL
     *
     * @param mapCxxx
     * @return
     */
    String getSqlByCxxx(Map mapCxxx);

    /**
     * 通过excel查询条件生成sql
     *
     * @param mapCxxx 查询条件数据
     * @return
     */
    String getSqlByExcelCxxx(Map mapCxxx);

    /**
     * 查询操作获取结果
     *
     * @param pageable
     * @param sql
     * @param dataMap  查询条件集和查询sql
     * @return org.springframework.data.domain.Page
     * @date 2019.03.27 16:50
     * @author hanyaning
     */
    Page listResultByPage(Pageable pageable, String sql, Map dataMap);

    /**
     * 查询操作获取结果
     *
     * @param dataMap 查询条件集和查询sql
     * @return List
     * @date 2019.03.27 16:50
     * @author hanyaning
     */
    List<Map> listResultData(Map dataMap);

    String getSqlBycxid(String cxid);

    List<Map> listResultData(@Param("sql") String sql);
}
