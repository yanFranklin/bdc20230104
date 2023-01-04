package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcNydjyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcNydjyqAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/8
 * @description 从原同权利到经营权
 */
@Service
public class InitYtqlxmToBdcNydjyqServiceImpl extends InitBdcNydjyqAbstractService {

    @Autowired
    private InitServiceQoService initServiceQoService;


    @Override
    public String getVal() {
        return CommonConstantUtils.QLSJLY_YTQL;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        return initFromYxm(initServiceQO, BdcNydjyqDO.class,initServiceQoService.getYtqlXmid(initServiceQO));
    }
}
