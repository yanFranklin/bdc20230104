package cn.gtmap.realestate.init.service.qlxx.impl;

import cn.gtmap.realestate.init.service.qlxx.InitBdcQlflAbstractService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcDyiqAbstractService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description
 */
@Service
public class InitBdcQlflDyiqServiceImpl extends InitBdcQlflAbstractService {


    @Override
    public String getVal() {
        return "BDC_DYIQ";
    }


    @Override
    public Class getQlzl() {
        return InitBdcDyiqAbstractService.class;
    }
}
