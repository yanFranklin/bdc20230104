package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcYyAbstractService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/8
 * @description 从原异议项目加载数据到异议权
 */
@Service
public class InitYtqlxmToBdcYyServiceImpl extends InitBdcYyAbstractService {

    @Autowired
    private InitServiceQoService initServiceQoService;


    @Override
    public String getVal() {
        return CommonConstantUtils.QLSJLY_YTQL;
    }


    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcYyDO bdcYyDO=initFromYxm(initServiceQO,BdcYyDO.class,initServiceQoService.getYtqlXmid(initServiceQO));
        if(bdcYyDO!=null && bdcYyDO.getYydjksrq()!=null){
            bdcYyDO.setYydjjsrq(DateUtils.addDays(bdcYyDO.getYydjksrq(),15));
        }
        return bdcYyDO;
    }
}
