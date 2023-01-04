package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcYgAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从楼盘表加载数据到预告
 */
@Service
public class InitLpbToBdcYgServiceImpl extends InitBdcYgAbstractService {

    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcYgDO bdcYgDO=initFwFromLpb(initServiceQO,BdcYgDO.class);
        return dealYg(initServiceQO,bdcYgDO);
    }
}
