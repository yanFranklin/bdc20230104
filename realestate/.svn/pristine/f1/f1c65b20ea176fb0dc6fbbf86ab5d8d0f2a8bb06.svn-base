package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.AccessBuildingMapper;
import cn.gtmap.realestate.building.core.service.AccessBuildingService;
import cn.gtmap.realestate.common.core.domain.exchange.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021/07/14
 * @description
 */
@Service
public class AccessBuildingServiceImpl implements AccessBuildingService {

    @Autowired
    private AccessBuildingMapper accessBuildingMapper;

    @Override
    public List<KttFwCDO> queryKttFwCList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<KttFwCDO> kttFwCDOS = accessBuildingMapper.queryKttFwCList(map);
        if (CollectionUtils.isNotEmpty(kttFwCDOS)) {
            for (KttFwCDO kttFwCDO : kttFwCDOS) {
                kttFwCDO.setQxdm(bdcdyh.substring(0, 6));
            }
        }
        return kttFwCDOS;
    }

    /**
     * 独幢层数据
     *
     * @param bdcdyh
     * @return
     * @Date 2022/6/7
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public List<KttFwCDO> queryKttFwCListDz(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        map.put("ishs", "2");
        List<KttFwCDO> kttFwCDOS = accessBuildingMapper.queryKttFwCListDz(map);
        if (CollectionUtils.isEmpty(kttFwCDOS)) {
            map = initParamMap(bdcdyh);
            map.put("ishs", "1");
            kttFwCDOS = accessBuildingMapper.queryKttFwCListDz(map);

        }
        if (CollectionUtils.isNotEmpty(kttFwCDOS)) {
            for (KttFwCDO kttFwCDO : kttFwCDOS) {
                kttFwCDO.setQxdm(bdcdyh.substring(0, 6));
            }
        }
        return kttFwCDOS;
    }

    @Override
    public List<Map> queryKttFwZrzList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<Map> maps = accessBuildingMapper.queryKttFwZrzList(map);
        if (CollectionUtils.isNotEmpty(maps)) {
            for (Map tempMap : maps) {
                tempMap.put("QXDM", bdcdyh.substring(0, 6));
            }
        }
        return maps;
    }

    @Override
    public List<KttFwHDO> queryKttFwHListNotDz(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<KttFwHDO> kttFwHDOS = accessBuildingMapper.queryKttFwHListNotDz(map);
        return kttFwHDOS;
    }

    @Override
    public List<KttFwHDO> queryKttFwHListIsDz(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        map.put("ishs", "2");
        List<KttFwHDO> kttFwHDOS = accessBuildingMapper.queryKttFwHListIsDz(map);
        if (CollectionUtils.isEmpty(kttFwHDOS)) {
            map = initParamMap(bdcdyh);
            map.put("ishs", "1");
            kttFwHDOS = accessBuildingMapper.queryKttFwHListIsDz(map);

        }
        return kttFwHDOS;
    }

    @Override
    public List<KttGyJzdDO> queryKttGyJzdList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<KttGyJzdDO> kttGyJzdDOS = accessBuildingMapper.queryKttGyJzdList(map);
        return kttGyJzdDOS;
    }

    @Override
    public List<KttGyJzxDO> queryKttGyJzxList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<KttGyJzxDO> kttGyJzxDOS = accessBuildingMapper.queryKttGyJzxList(map);
        return kttGyJzxDOS;
    }

    @Override
    public List<KttFwLjzDO> queryKttFwLjzList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        map.put("ishs", "4");
        List<KttFwLjzDO> kttFwLjzDOS = accessBuildingMapper.queryKttFwLjzList(map);
        if (CollectionUtils.isEmpty(kttFwLjzDOS)) {
            map = initParamMap(bdcdyh);
            kttFwLjzDOS = accessBuildingMapper.queryKttFwLjzList(map);
        }
        if (CollectionUtils.isEmpty(kttFwLjzDOS)) {
            map = initParamMap(bdcdyh);
            map.put("ishs", "1");
            kttFwLjzDOS = accessBuildingMapper.queryKttFwLjzList(map);

        }
        if (CollectionUtils.isNotEmpty(kttFwLjzDOS)) {
            for (KttFwLjzDO kttFwLjzDO : kttFwLjzDOS) {
                kttFwLjzDO.setQxdm(bdcdyh.substring(0, 6));
            }
        }
        return kttFwLjzDOS;
    }

    @Override
    public List<ZhKDO> queryZhkList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<ZhKDO> zhKDOS = accessBuildingMapper.queryZhkList(map);
        return zhKDOS;
    }

    /**
     * 根据宗地代码查询宗地变更记录表，按照更新时间倒序
     *
     * @param bh 宗地代码
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public List<Map> queryZdbgjlbList(String bh) {
        Map map = new HashMap(12);
        map.put("bh", bh);

        return accessBuildingMapper.queryZdbgjlbList(map);
    }

    /**
     * 根据宗地代码查询宗地宗地空间属性
     *
     * @param bdcdyh
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public List<ZdKDO> queryZdkList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<ZdKDO> zdKDOList = accessBuildingMapper.queryZdkList(map);
        if (CollectionUtils.isNotEmpty(zdKDOList)) {
            for (ZdKDO zdKDO : zdKDOList) {
                zdKDO.setBdcdyh(bdcdyh);
            }
        }
        return zdKDOList;
    }

    private Map initParamMap(String bdcdyh) {
        Map map = new HashMap();
        map.put("bdcdyh", bdcdyh);
        return map;
    }
}