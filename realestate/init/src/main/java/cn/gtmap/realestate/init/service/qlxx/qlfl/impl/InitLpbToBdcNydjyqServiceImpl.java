package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcNydjyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcNydjyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/6.
 * @description
 */
@Service
public class InitLpbToBdcNydjyqServiceImpl extends InitBdcNydjyqAbstractService {

    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        return initTdFromLpb(initServiceQO,BdcNydjyqDO.class);
    }
}
