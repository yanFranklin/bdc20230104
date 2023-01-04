package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.QszdService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.common.core.domain.building.HQszdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
@Service
public class QszdServiceImpl implements QszdService {
    @Autowired
    private BdcdyService bdcdyService;
    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询权属宗地
     */
    @Override
    public QszdDjdcbDO queryQszdDjdcbByBdcdyh(String bdcdyh) {
        return bdcdyService.queryDjxxByBdcdyh(bdcdyh, QszdDjdcbDO.class);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询QSZD
     */
    @Override
    public QszdDjdcbDO queryQszdDjdcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjh(djh, QszdDjdcbDO.class);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询备份QSZD
     */
    @Override
    public HQszdDjdcbDO queryHQszdDjdcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjhWithOrder(djh, HQszdDjdcbDO.class,"gxrq desc");
    }

}