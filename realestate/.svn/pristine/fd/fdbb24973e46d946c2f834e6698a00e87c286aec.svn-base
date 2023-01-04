package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.BgbhMapper;
import cn.gtmap.realestate.building.core.service.BgbhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/19
 * @description
 */
@Service
public class BgbhServiceImpl implements BgbhService {
    @Autowired
    private BgbhMapper bgbhMapper;
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return java.lang.String
     * @description 获取变更编号
     */
    @Override
    public String maxBgbh() {
        return bgbhMapper.maxBgbh();
    }
}