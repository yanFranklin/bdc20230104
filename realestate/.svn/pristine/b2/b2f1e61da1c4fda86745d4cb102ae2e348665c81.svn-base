package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcDjxlPzService;
import cn.gtmap.realestate.accept.core.service.BdcSlXztzPzService;
import cn.gtmap.realestate.accept.service.BdcQllxService;
import cn.gtmap.realestate.accept.service.BdcSlBdclxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2019/3/4,1.0
 * @description
 */
@Service
public class BdcSlBdclxServiceImpl implements BdcSlBdclxService {
    @Autowired
    BdcQllxService bdcQllxService;
    @Autowired
    BdcSlXztzPzService bdcSlXztzPzService;
    @Autowired
    BdcDjxlPzService bdcDjxlPzService;

    /**
     * @param processDefId 不动产受理项目前台
     * @param bdcdyh       基本信息ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 初始化受理项目信息
     */
    @Override
    public Integer queryBdclx(String processDefId, String bdcdyh) {
        Integer qllx = null;
        Integer bdclx = null;
        BdcSlXztzPzDO bdcSlXztzPzDO = bdcSlXztzPzService.queryBdcSlXztzPzDOByGzldyid(processDefId);
        if (bdcSlXztzPzDO != null) {
            qllx = bdcQllxService.getQllxByBdcdyh(bdcdyh, bdcSlXztzPzDO.getDyhcxlx().toString());
        }
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzService.listBdcDjxlPzByGzldyid(processDefId, qllx,null);
        if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            bdclx = bdcDjxlPzDOList.get(0).getBdclx();
        }
        return bdclx;
    }

}
