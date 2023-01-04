package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcJzqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcJzqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/1/11
 * @description 从原项目加载数据到居住权
 */
@Service
public class InitYxmToBdcJzqServiceImpl extends InitBdcJzqAbstractService {

    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcJzqDO bdcJzqDO=initFromYxm(initServiceQO,BdcJzqDO.class);
        return  dealJzq(initServiceQO,bdcJzqDO);
    }
}
