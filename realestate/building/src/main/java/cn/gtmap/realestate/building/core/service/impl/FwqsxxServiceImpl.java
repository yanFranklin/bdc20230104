package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.FwqsxxMapper;
import cn.gtmap.realestate.building.core.service.FwqsxxService;
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
public class FwqsxxServiceImpl implements FwqsxxService {

    @Autowired
    private FwqsxxMapper fwqsxxMapper;

    @Override
    public List<KttFwCDO> queryKttFwCList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<KttFwCDO> kttFwCDOS = fwqsxxMapper.queryKttFwCList(map);
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
        List<KttFwCDO> kttFwCDOS = fwqsxxMapper.queryKttFwCListDz(map);
        if (CollectionUtils.isEmpty(kttFwCDOS)) {
            map = initParamMap(bdcdyh);
            map.put("ishs", "1");
            kttFwCDOS = fwqsxxMapper.queryKttFwCListDz(map);

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
        List<Map> maps = fwqsxxMapper.queryKttFwZrzList(map);
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
        List<KttFwHDO> kttFwHDOS = fwqsxxMapper.queryKttFwHListNotDz(map);
        return kttFwHDOS;
    }

    @Override
    public List<KttFwHDO> queryKttFwHListIsDz(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        map.put("ishs", "2");
        List<KttFwHDO> kttFwHDOS = fwqsxxMapper.queryKttFwHListIsDz(map);
        if (CollectionUtils.isEmpty(kttFwHDOS)) {
            map = initParamMap(bdcdyh);
            map.put("ishs", "1");
            kttFwHDOS = fwqsxxMapper.queryKttFwHListIsDz(map);

        }
        return kttFwHDOS;
    }

    @Override
    public List<KttGyJzdDO> queryKttGyJzdList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<KttGyJzdDO> kttGyJzdDOS = fwqsxxMapper.queryKttGyJzdList(map);
        return kttGyJzdDOS;
    }

    @Override
    public List<KttGyJzxDO> queryKttGyJzxList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        List<KttGyJzxDO> kttGyJzxDOS = fwqsxxMapper.queryKttGyJzxList(map);
        return kttGyJzxDOS;
    }

    @Override
    public List<KttFwLjzDO> queryKttFwLjzList(String bdcdyh) {
        Map map = initParamMap(bdcdyh);
        map.put("ishs", "4");
        List<KttFwLjzDO> kttFwLjzDOS = fwqsxxMapper.queryKttFwLjzList(map);
        if (CollectionUtils.isEmpty(kttFwLjzDOS)) {
            map = initParamMap(bdcdyh);
            kttFwLjzDOS = fwqsxxMapper.queryKttFwLjzList(map);
        }
        if (CollectionUtils.isEmpty(kttFwLjzDOS)) {
            map = initParamMap(bdcdyh);
            map.put("ishs", "1");
            kttFwLjzDOS = fwqsxxMapper.queryKttFwLjzList(map);

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
        List<ZhKDO> zhKDOS = fwqsxxMapper.queryZhkList(map);
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

        return fwqsxxMapper.queryZdbgjlbList(map);
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
        List<ZdKDO> zdKDOList = fwqsxxMapper.queryZdkList(map);
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