package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcJzqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcJzqAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/1/14
 * @description  从原居住权项目加载数据到居住权
 */
@Service
public class InitYtqlxmToBdcJzqServiceImpl extends InitBdcJzqAbstractService {
    @Autowired
    private InitServiceQoService initServiceQoService;

    @Override
    public String getVal() {
        return CommonConstantUtils.QLSJLY_YTQL;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcJzqDO bdcJzqDO =initFromYxm(initServiceQO,BdcJzqDO.class,initServiceQoService.getYtqlXmid(initServiceQO));
        return  dealJzq(initServiceQO,bdcJzqDO);
    }
}
