package cn.gtmap.realestate.check.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author ccx 2019/1/14
 * @description
 */
public interface GzxxService {
    /**
     * 获取分组规则信息
     * @param map
     * @return
     */
    List<Map<String,Object>> queryGzxxList(Map<String, Object> map);

    /**
     * 导出查询结果
     * @param list
     */
    void export(List<Map<String, Object>> list, HttpServletResponse response);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 批量更新规则是否忽略字段
      */
    void updateBatchCheckGzxxSfhl(Integer sfhl,List<String> gzidList);
}
