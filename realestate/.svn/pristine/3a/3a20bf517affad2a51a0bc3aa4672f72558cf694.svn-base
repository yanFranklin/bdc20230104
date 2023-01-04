package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcLqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcLqAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/15
 * @description 从权籍加载数据到林权
 */
@Service
public class InitLpbToBdcLqServiceImpl extends InitBdcLqAbstractService {
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
        BdcLqDO bdcLqDO = initTdFromLpb(initServiceQO, BdcLqDO.class);
        if (CollectionUtils.isNotEmpty(initServiceQO.getDjxxResponseDTO().getLmsuqrList())) {
            bdcLqDO.setSllmsyqr1(StringToolUtils.resolveBeanToAppendStr(initServiceQO.getDjxxResponseDTO().getLmsuqrList(),
                    "getQlrmc", " "));
        }
        if (CollectionUtils.isNotEmpty(initServiceQO.getDjxxResponseDTO().getLmsyqrList())) {
            bdcLqDO.setSllmsyqr2(StringToolUtils.resolveBeanToAppendStr(initServiceQO.getDjxxResponseDTO().getLmsyqrList(),
                    "getQlrmc", " "));
        }else if(CollectionUtils.isNotEmpty(initServiceQO.getDjxxResponseDTO().getQlrList()) && initServiceQO.getBdcXm() != null &&CommonConstantUtils.QLLX_SLLMSYQ.equals(initServiceQO.getBdcXm().getQllx())){
            bdcLqDO.setSllmsyqr2(StringToolUtils.resolveBeanToAppendStr(initServiceQO.getDjxxResponseDTO().getQlrList(),
                    "getQlrmc", " "));
        }
        return bdcLqDO;
    }
}
