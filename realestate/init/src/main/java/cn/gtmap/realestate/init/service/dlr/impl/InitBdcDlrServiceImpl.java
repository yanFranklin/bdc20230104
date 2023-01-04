package cn.gtmap.realestate.init.service.dlr.impl;

import cn.gtmap.realestate.init.service.dlr.InitBdcDlrAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/14
 * @description 初始化代理人的服务实现
 */
@Service
public class InitBdcDlrServiceImpl extends InitBdcDlrAbstractService {

    @Override
    public String getVal() {
        return Constants.DEFAULT;
    }
}
