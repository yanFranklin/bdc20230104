package cn.gtmap.realestate.init.service.qlxx.impl;

import cn.gtmap.realestate.init.service.qlxx.InitBdcQlflAbstractService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcJsydsyqAbstractService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description
 */
@Service
public class InitBdcQlflJsydsyqServiceImpl extends InitBdcQlflAbstractService {


    @Override
    public String getVal() {
        return "BDC_JSYDSYQ";
    }


    @Override
    public Class getQlzl() {
        return InitBdcJsydsyqAbstractService.class;
    }
}
