package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.init.core.mapper.BdcXtBbMapper;
import cn.gtmap.realestate.init.core.service.BdcXtBbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/5/21 14:27
 */
@Service
public class BdcXtBbServiceImpl implements BdcXtBbService {

    @Autowired
    private BdcXtBbMapper bdcXtBbMapper;

    /**
     * 获取更新日志列表
     *
     * @return 版本更新日志列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<Map> listGxrzList() {
        return bdcXtBbMapper.listGxrzList();
    }
}
