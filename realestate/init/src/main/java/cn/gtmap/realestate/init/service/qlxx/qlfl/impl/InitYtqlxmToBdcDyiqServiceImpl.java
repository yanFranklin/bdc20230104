package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyiqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcDyiqAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/8
 * @description 从原同权利到地役权
 */
@Service
public class InitYtqlxmToBdcDyiqServiceImpl extends InitBdcDyiqAbstractService {

    @Autowired
    private InitServiceQoService initServiceQoService;


    @Override
    public String getVal() {
        return CommonConstantUtils.QLSJLY_YTQL;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        return initFromYxm(initServiceQO, BdcDyiqDO.class,initServiceQoService.getYtqlXmid(initServiceQO));
    }
}
