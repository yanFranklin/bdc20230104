package cn.gtmap.realestate.exchange.service.impl.inf.qj;

import cn.gtmap.realestate.exchange.core.domain.exchange.*;
import cn.gtmap.realestate.exchange.core.mapper.qj.AccessBuildingMapper;
import cn.gtmap.realestate.exchange.service.inf.qj.BuildingService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuildingServiceImpl implements BuildingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingServiceImpl.class);

    @Autowired
    private AccessBuildingMapper accessBuildingMapper;


    @Override
    public List<KttFwCDO> queryKttFwCList(String bdcdyh, String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)){
            return null;
        }
        Map map = initParamMap(bdcdyh);
        List<KttFwCDO> kttFwCDOS = accessBuildingMapper.queryKttFwCList(map);
        if (CollectionUtils.isNotEmpty(kttFwCDOS)){
            for (KttFwCDO kttFwCDO : kttFwCDOS) {
                kttFwCDO.setQxdm(bdcdyh.substring(0,6));
            }
        }
        return kttFwCDOS;
    }

    @Override
    public List<Map> queryKttFwZrzList(String bdcdyh, String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)){
            return null;
        }
        Map map = initParamMap(bdcdyh);
        List<Map> maps = accessBuildingMapper.queryKttFwZrzList(map);
        if (CollectionUtils.isNotEmpty(maps)){
            for (Map tempMap : maps) {
                tempMap.put("QXDM",bdcdyh.substring(0,6));
            }
        }
        return maps;
    }

    @Override
    public List<KttFwHDO> queryKttFwHList(String bdcdyh, boolean sfdz, String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)){
            return null;
        }
        if (sfdz){
            Map map = initParamMap(bdcdyh);
            List<KttFwHDO> kttFwHDOS = accessBuildingMapper.queryKttFwHListIsDz(map);
            return kttFwHDOS;
        }else {
            Map map = initParamMap(bdcdyh);
            List<KttFwHDO> kttFwHDOS = accessBuildingMapper.queryKttFwHListNotDz(map);
            return kttFwHDOS;
        }
    }

    @Override
    public List<KttGyJzdDO> queryKttGyJzdList(String bdcdyh, String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)){
            return null;
        }
        Map map = initParamMap(bdcdyh);
        List<KttGyJzdDO> kttGyJzdDOS = accessBuildingMapper.queryKttGyJzdList(map);
        return kttGyJzdDOS;
    }

    @Override
    public List<KttGyJzxDO> queryKttGyJzxList(String bdcdyh, String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)){
            return null;
        }
        Map map = initParamMap(bdcdyh);
        List<KttGyJzxDO> kttGyJzxDOS = accessBuildingMapper.queryKttGyJzxList(map);
        return kttGyJzxDOS;
    }

    @Override
    public List<KttFwLjzDO> queryKttFwLjzList(String bdcdyh, String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)){
            return null;
        }
        Map map = initParamMap(bdcdyh);
        map.put("ishs","true");
        List<KttFwLjzDO> kttFwLjzDOS = accessBuildingMapper.queryKttFwLjzList(map);
        if (CollectionUtils.isEmpty(kttFwLjzDOS)) {
            map = initParamMap(bdcdyh);
            kttFwLjzDOS = accessBuildingMapper.queryKttFwLjzList(map);
        }
        if (CollectionUtils.isNotEmpty(kttFwLjzDOS)){
            for (KttFwLjzDO kttFwLjzDO : kttFwLjzDOS) {
                kttFwLjzDO.setQxdm(bdcdyh.substring(0,6));
            }
        }
        return kttFwLjzDOS;
    }

    @Override
    public List<ZhKDO> queryZhkList(String bdcdyh, String qjgldm) {
        if (StringUtils.isEmpty(bdcdyh)){
            return null;
        }
        Map map = initParamMap(bdcdyh);
        List<ZhKDO> zhKDOS = accessBuildingMapper.queryZhkList(map);
        return zhKDOS;
    }

    private Map initParamMap(String bdcdyh){
        Map map = new HashMap();
        map.put("bdcdyh",bdcdyh);
        return map;
    }
}
