package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyiqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcDyiqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/20
 * @description 从权籍加载数据到地役权利
 */
@Service
public class InitLpbToBdcDyiqServiceImpl extends InitBdcDyiqAbstractService{
    /**
     * 抽象方法 设置对照开关值
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
     * @param initServiceQO 初始化所需数据结构
     * @return 返回权利信息
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description
     */
    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        return initFromLpbXm(initServiceQO,BdcDyiqDO.class,null);
    }
}
