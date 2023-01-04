package cn.gtmap.realestate.init.service.qlxx.impl;

import cn.gtmap.realestate.init.service.qlxx.InitBdcQlflAbstractService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcQtxgqlAbstractService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description
 */
@Service
public class InitBdcQlflQtxgqlServiceImpl extends InitBdcQlflAbstractService {


    @Override
    public String getVal() {
        return "BDC_QTXGQL";
    }


    @Override
    public Class getQlzl() {
        return InitBdcQtxgqlAbstractService.class;
    }
}
