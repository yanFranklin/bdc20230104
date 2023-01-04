package cn.gtmap.realestate.common.core.service.rest.engine;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0  2018-12-14
 * @description 不动产规则引擎字典表服务
 */
public interface BdcGzZdbRestService {

    /**
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @param  cacheTable
     * @param cacheClass
     * @return java.util.List<java.util.Map>
     * @description 获取字典表中的数据
     */
    @PutMapping("/realestate-engine/rest/v1.0/zdb/getZdTable")
    List<Map> getZdTable(@RequestParam("cacheTable") String cacheTable, @RequestBody Class cacheClass);

    /**
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @param cacheTableMap
     * @return java.util.Map<java.lang.String,java.util.List<java.util.Map>>
     * @description 批量获取字典表中的数据
     */
    @PutMapping("/realestate-engine/rest/v1.0/zdb/listZdTable")
    Map<String,List<Map>> listZdTable(@RequestBody Map<String, Class> cacheTableMap);
}
