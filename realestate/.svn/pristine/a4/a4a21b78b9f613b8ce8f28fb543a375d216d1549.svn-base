package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcGjzwsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcGjzwsyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/16
 * @description 从原项目加载数据到构建筑物所有权
 */
@Service
public class InitYxmToBdcGjzwsyqServiceImpl extends InitBdcGjzwsyqAbstractService {
    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        return initFromYxm(initServiceQO,BdcGjzwsyqDO.class);
    }
}
