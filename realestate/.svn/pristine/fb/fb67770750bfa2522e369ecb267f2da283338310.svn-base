package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.register.core.mapper.HtbaSpfMapper;
import cn.gtmap.realestate.register.service.HtbaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 备案状态实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-17 20:02
 **/
@Service
public class HtbaServiceImpl implements HtbaService {

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    HtbaSpfMapper htbaSpfMapper;
    /**
     * @param bdcdyh
     * @param bazt
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合同备案状态
     * @date : 2020/12/17 20:03
     */
    @Override
    public List<HtbaSpfDO> listHtbaSpf(HtbaxxQO htbaxxQO) {
        return htbaSpfMapper.listHtbaSpf(htbaxxQO);
    }
}
