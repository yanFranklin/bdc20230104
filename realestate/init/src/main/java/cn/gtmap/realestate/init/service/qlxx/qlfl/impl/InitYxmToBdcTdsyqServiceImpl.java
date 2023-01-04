package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcTdsyqDO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcTdsyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * 土地所有权的实现  读取原项目
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/15.
 * @description
 */
@Service
public class InitYxmToBdcTdsyqServiceImpl  extends InitBdcTdsyqAbstractService {
    /**
     * 抽象方法 设置对照开关值
     *
     * @return 对照开关值
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public String getVal() {
        return Constants.QLSJLY_YXM;
    }

    /**
     * 初始化权利信息接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回权利信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        return initFromYxm(initServiceQO,BdcTdsyqDO.class);
    }
}
