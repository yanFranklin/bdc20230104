package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcGjzwsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcGjzwsyqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/20
 * @description 从地籍数据 加载数据到构建筑物所有权
 */
@Service
public class InitLpbToBdcGjzwsyqServiceImpl extends InitBdcGjzwsyqAbstractService {
    @Override
    public String getVal() {
        return Constants.QLSJLY_LPB;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        BdcGjzwsyqDO bdcGjzwsyqDO = initFwFromLpb(initServiceQO, BdcGjzwsyqDO.class);
        if (CollectionUtils.isNotEmpty(initServiceQO.getDjxxResponseDTO().getQlrList())) {
            bdcGjzwsyqDO.setTdhysyqr(StringToolUtils.resolveBeanToAppendStr(initServiceQO.getDjxxResponseDTO().getQlrList(),
                    "getQlrmc"," "));
        }
        return bdcGjzwsyqDO;
    }
}
