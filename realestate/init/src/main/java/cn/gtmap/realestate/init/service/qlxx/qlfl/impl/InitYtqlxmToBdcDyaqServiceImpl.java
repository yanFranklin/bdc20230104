package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcDyaqAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从原抵押项目加载数据到抵押权
 */
@Service
public class InitYtqlxmToBdcDyaqServiceImpl extends InitBdcDyaqAbstractService {
    @Autowired
    private InitServiceQoService initServiceQoService;

    @Override
    public String getVal() {
        return CommonConstantUtils.QLSJLY_YTQL;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcDyaqDO bdcDyaqDO=initFromYxm(initServiceQO,BdcDyaqDO.class,initServiceQoService.getYtqlXmid(initServiceQO));
        return  dealDyaq(initServiceQO,bdcDyaqDO);
    }
}
