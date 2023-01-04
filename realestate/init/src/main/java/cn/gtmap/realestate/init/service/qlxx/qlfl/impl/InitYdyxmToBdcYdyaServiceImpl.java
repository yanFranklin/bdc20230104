package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.qlxx.qlfl.InitBdcYgAbstractService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/9
 * @description 预抵押转预抵押
 */
@Service
public class InitYdyxmToBdcYdyaServiceImpl extends InitBdcYgAbstractService {
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    private BdcXmService bdcXmService;

    @Override
    public String getVal() {
        return Constants.QLSJLY_YDY_YDY;
    }

    @Override
    public BdcQl initQlxx(InitServiceQO initServiceQO) throws Exception {
        BdcYgDO bdcYgDO = null;
        List<BdcXmLsgxDO> listLsgx = null;
        if (initServiceQO != null) {
            //获取历史关系
            if (CollectionUtils.isNotEmpty(initServiceQO.getBdcXmLsgxList())) {
                listLsgx = initServiceQO.getBdcXmLsgxList();
            } else {
                listLsgx = bdcXmLsgxService.queryBdcXmLsgxByXmid(initServiceQO.getXmid(), null);
            }
            if (CollectionUtils.isNotEmpty(listLsgx)) {
                for (BdcXmLsgxDO bdcXmLsgxDO : listLsgx) {
                    if (StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid()) && bdcXmLsgxDO.getWlxm() == 1) {
                        BdcXmDO yxm = bdcXmService.queryBdcXmByPrimaryKey(bdcXmLsgxDO.getYxmid());
                        //读取抵押项目的数据
                        if (yxm != null && CommonConstantUtils.QLLX_YG_DM.equals(yxm.getQllx())) {
                            bdcYgDO = initFromYxm(initServiceQO, BdcYgDO.class, bdcXmLsgxDO.getYxmid());
                            break;
                        }
                    }
                }
            }
        }
        return dealYg(initServiceQO, bdcYgDO);
    }
}
