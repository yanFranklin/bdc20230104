package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.SDmXzdmDO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-14
 * @description 楼盘表字典项转换服务
 */
public interface BuildingZdConvertRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param  cacheTable
     * @param cacheClass
     * @return java.util.List<java.util.Map>
     * @description 获取字典表中的数据
     */
    @PutMapping("/building/rest/v1.0/zd/getzdtable")
    List<Map> getZdTable(@RequestParam("cacheTable") String cacheTable, @RequestBody Class cacheClass);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cacheTableMap
     * @return java.util.Map<java.lang.String,java.util.List<java.util.Map>>
     * @description 批量获取字典表中的数据
     */
    @PutMapping("/building/rest/v1.0/zd/listzdtable")
    Map<String,List<Map>> listZdTable(@RequestBody Map<String,Class> cacheTableMap);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sDmXzdmDO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SDmXzdmDO>
     * @description  获取地籍行政代码
     */
    @PostMapping("/building/rest/v1.0/zd/listxzdm")
    List<SDmXzdmDO> listXzdm(@RequestBody SDmXzdmDO sDmXzdmDO);
}
