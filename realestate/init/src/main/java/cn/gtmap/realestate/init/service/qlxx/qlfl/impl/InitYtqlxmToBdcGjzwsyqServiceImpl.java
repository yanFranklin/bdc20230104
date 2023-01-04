package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcGjzwsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcGjzwsyqAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/8
 * @description 从原同权利到构建筑物所有权
 */
@Service
public class InitYtqlxmToBdcGjzwsyqServiceImpl extends InitBdcGjzwsyqAbstractService {

    @Autowired
    private InitServiceQoService initServiceQoService;


    @Override
    public String getVal() {
        return CommonConstantUtils.QLSJLY_YTQL;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        return initFromYxm(initServiceQO, BdcGjzwsyqDO.class,initServiceQoService.getYtqlXmid(initServiceQO));
    }
}
