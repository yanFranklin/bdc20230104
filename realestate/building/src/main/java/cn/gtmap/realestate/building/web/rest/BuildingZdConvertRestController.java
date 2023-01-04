package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.building.SDmXzdmDO;
import cn.gtmap.realestate.common.core.service.rest.building.BuildingZdConvertRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import io.swagger.annotations.Api;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-14
 * @description 楼盘表字典项转换服务
 */
@RestController
@Api(tags = "楼盘表字典项转换服务")
public class BuildingZdConvertRestController implements BuildingZdConvertRestService{


    @Autowired
    private BdcZdCache bdcZdCache;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param cacheTable
     * @param cacheClass
     * @return java.util.List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取字典表中的数据
     */
    @Override
    public List<Map> getZdTable(@RequestParam("cacheTable") String cacheTable, @RequestBody Class cacheClass) {
        return bdcZdCache.getZdTableList(cacheTable,cacheClass);
    }

    /**
     * @param cacheTableMap
     * @return java.util.Map<java.lang.String , java.util.List < java.util.Map>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量获取字典表中的数据
     */
    @Override
    public Map<String, List<Map>> listZdTable(@RequestBody Map<String, Class> cacheTableMap) {
        if(MapUtils.isNotEmpty(cacheTableMap)){
            Map<String, List<Map>> resultMap = new HashMap<>();
            Iterator<Map.Entry<String, Class>> iterator = cacheTableMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Class> entry = iterator.next();
                String cacheTable = entry.getKey();
                Class cacheClass = entry.getValue();
                resultMap.put(cacheTable,bdcZdCache.getZdTableList(cacheTable,cacheClass));
            }
            return resultMap;
        }
        return null;
    }

    /**
     * @param sDmXzdmDO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SDmXzdmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取地籍行政代码
     */
    @Override
    public List<SDmXzdmDO> listXzdm(@RequestBody SDmXzdmDO sDmXzdmDO) {
        List<SDmXzdmDO> list = entityMapper.selectByObj(sDmXzdmDO);
        return list;
    }
}
