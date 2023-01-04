package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcYyAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0  2018/11/6.
 * @description   从楼盘表加载数据到异议
 */
@Service
public class InitLpbToBdcYyServiceImpl extends InitBdcYyAbstractService {

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
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws InstantiationException, IllegalAccessException {
        BdcYyDO bdcYyDO=initFromLpbXm(initServiceQO,BdcYyDO.class,null);
        if(bdcYyDO!=null && bdcYyDO.getYydjksrq()!=null){
            bdcYyDO.setYydjjsrq(DateUtils.addDays(bdcYyDO.getYydjksrq(),15));
        }
        return bdcYyDO;
    }
}
