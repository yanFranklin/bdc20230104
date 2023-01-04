package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcJsydsyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/15
 * @description 从权籍加载数据到建设用地使用权
 */
@Service
public class InitLpbToBdcJsydsyqServiceImpl extends InitBdcJsydsyqAbstractService {
    /**
     * @return 对照开关值
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description
     */
    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }

    /**
     * 初始化权利信息接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回权利信息
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description
     */
    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        return initTdFromLpb(initServiceQO,BdcJsydsyqDO.class);
    }
}
