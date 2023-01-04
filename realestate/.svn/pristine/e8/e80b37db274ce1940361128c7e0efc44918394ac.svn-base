package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyiqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcDyiqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/16
 * @description 从原项目加载数据到地役权
 */
@Service
public class InitYxmToBdcDyiqServiceImpl extends InitBdcDyiqAbstractService {
    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        return initFromYxm(initServiceQO,BdcDyiqDO.class);
    }
}
