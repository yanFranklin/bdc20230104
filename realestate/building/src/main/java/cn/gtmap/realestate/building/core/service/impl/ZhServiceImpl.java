package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.DjxxMapper;
import cn.gtmap.realestate.building.core.service.ZhService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
@Service
public class ZhServiceImpl implements ZhService {
    @Autowired
    private BdcdyService bdcdyService;
    @Autowired
    private DjxxMapper djxxMapper;

    @Override
    public ZhDjdcbDO queryZhDjdcbByZhdm(String zhdm) {
        return bdcdyService.queryDjxxByZhdm(zhdm, ZhDjdcbDO.class);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询宗海
     */
    @Override
    public ZhDjdcbDO queryZhDjdcbByBdcdyh(String bdcdyh) {
        return bdcdyService.queryDjxxByBdcdyh(bdcdyh, ZhDjdcbDO.class);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询备份宗海
     */
    @Override
    public HZhDjdcbDO queryHZhDjdcbByBdcdyh(String bdcdyh) {
        return bdcdyService.queryDjxxByBdcdyhWithOrder(bdcdyh, HZhDjdcbDO.class,"gxrq desc");
    }

    /**
     * @param dcdcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗海及内部单元记录表属性结构描述(调用查询权利人方法，实现相同)
     */
    @Override
    public List<ZhZhjnbdyjlb> listZhZhjnbdyjlb(String dcdcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(dcdcbIndex,ZhZhjnbdyjlb.class);
    }

    /**
     * @param dcdcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗海及内部单元记录表属性结构描述 备份
     */
    @Override
    public List<HZhZhjnbdyjlb> listHZhZhjnbdyjlb(String dcdcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(dcdcbIndex,HZhZhjnbdyjlb.class);
    }

    /**
     * @param dcdcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗海界址标示表(调用查询权利人方法，实现相同)
     */
    @Override
    public List<ZhJzbsb> listZhJzbsb(String dcdcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(dcdcbIndex,ZhJzbsb.class);
    }

    /**
     * @param dcdcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗海界址标示表 备份
     */
    @Override
    public List<HZhJzbsbDO> listHZhJzbsb(String dcdcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(dcdcbIndex,HZhJzbsbDO.class);
    }
    
    /**
     * @param zhdm
     * @return cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 宗海权属调查表
     */
    @Override
    public ZhQsdcDO queryZhQsdcDO(String zhdm) {
        if (StringUtils.isNotBlank(zhdm)) {
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("zhdm", zhdm);
            List<Map<String, Object>> mapList = djxxMapper.queryZhQsdcList(paramMap);
            if(CollectionUtils.isNotEmpty(mapList)){
                return (ZhQsdcDO) BuildingUtils.map2Bean(mapList.get(0),ZhQsdcDO.class);
            }
        }
        return null;
    }
    
}