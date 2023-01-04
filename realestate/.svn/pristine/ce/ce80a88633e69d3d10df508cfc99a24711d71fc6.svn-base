package cn.gtmap.realestate.etl.core.dozer;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //字典组件工具
 * @Date 2022/5/17 11:09
 **/
@Component
public class BdcDmToDsfZdComponent {
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //字典代码转成字典名称
     * @Date 2022/5/17 11:09
     **/
    public Object conver(String zdTable, String xtbs, String bdczdz) {
        Object bean = Container.getBean(BdcZdFeignService.class);
        if (bean != null) {
            BdcZdFeignService zdService = (BdcZdFeignService) bean;
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs(zdTable);
            bdcZdDsfzdgxDO.setBdczdz(bdczdz);
            bdcZdDsfzdgxDO.setDsfxtbs(xtbs);
            BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
            if (result != null) {
                return result.getDsfzdz();
            }
        }
        return bdczdz;
    }

    private String getZdKeyByZdTable(String zdTable){
        String[] arr = zdTable.split("_");
        String zdkey = arr[arr.length-1];
        return StringUtils.lowerCase(zdkey);
    }
}
