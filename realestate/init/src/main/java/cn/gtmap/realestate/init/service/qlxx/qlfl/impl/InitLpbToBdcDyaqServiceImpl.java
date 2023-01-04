package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcDyaqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/6.
 * @description 从楼盘表加载数据到抵押权
 */
@Service
public class InitLpbToBdcDyaqServiceImpl extends InitBdcDyaqAbstractService {


    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcDyaqDO bdcDyaqDO=initFwFromLpb(initServiceQO,BdcDyaqDO.class);
        return  dealDyaq(initServiceQO,bdcDyaqDO);
    }
}
