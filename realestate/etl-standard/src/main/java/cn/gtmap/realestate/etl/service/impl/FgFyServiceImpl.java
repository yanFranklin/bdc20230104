package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.vo.etl.FgHtfyVO;
import cn.gtmap.realestate.etl.core.mapper.exchange.FgFyMapper;
import cn.gtmap.realestate.etl.service.FgFyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-17 17:47
 **/
@Service
public class FgFyServiceImpl implements FgFyService {
    @Autowired
    FgFyMapper fgFyMapper;

    /**
     * @param map
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询Fg_fy数据
     * @date : 2020/12/17 17:47
     */
    @Override
    public List<FgHtfyVO> selectFgHtfyxx(Map map) {
        return fgFyMapper.selectFgHtfyxx(map);
    }
}
