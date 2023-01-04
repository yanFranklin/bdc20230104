package cn.gtmap.realestate.init.service.qlxx.impl;

import cn.gtmap.realestate.init.service.qlxx.InitBdcQlflAbstractService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcJzqAbstractService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/1/11
 * @description 居住权
 */
@Service
public class InitBdcQlflJzqServiceImpl extends InitBdcQlflAbstractService {


    @Override
    public String getVal() {
        return "BDC_JZQ";
    }


    @Override
    public Class getQlzl() {
        return InitBdcJzqAbstractService.class;
    }
}
