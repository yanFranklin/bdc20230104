package cn.gtmap.realestate.init.service.qlxx.impl;

import cn.gtmap.realestate.init.service.qlxx.InitBdcQlflAbstractService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcNydjyqAbstractService;
import org.springframework.stereotype.Service;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/10.
 * @description
 */
@Service
public class InitBdcQlflNydjyqServiceImpl extends InitBdcQlflAbstractService {
    @Override
    public String getVal() {
        return "BDC_NYDJYQ";
    }


    @Override
    public Class getQlzl() {
        return InitBdcNydjyqAbstractService.class;
    }
}
