package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.ZhQlrService;
import cn.gtmap.realestate.building.core.service.ZhService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.common.core.domain.building.HZhQlrDO;
import cn.gtmap.realestate.common.core.domain.building.ZhDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZhQlrDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-12
 * @description
 */
@Service
public class ZhQlrServiceImpl implements ZhQlrService {

    @Autowired
    private BdcdyService bdcdyService;

    @Autowired
    private ZhService zhService;

    /**
     * @param zhDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据zhDcbIndex查询宗海权利人信息
     */
    @Override
    public List<ZhQlrDO> listZhQlrByZhDcbIndex(String zhDcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(zhDcbIndex, ZhQlrDO.class);
    }

    /**
     * @param zhDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据zhDcbIndex查询备份宗海权利人信息
     */
    @Override
    public List<HZhQlrDO> listHZhQlrByZhDcbIndex(String zhDcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(zhDcbIndex, HZhQlrDO.class);
    }

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据bdcdyh查询宗海权利人信息
     */
    @Override
    public List<ZhQlrDO> listZhQlrByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            ZhDjdcbDO zhDjdcbDO = zhService.queryZhDjdcbByBdcdyh(bdcdyh);
            if (zhDjdcbDO != null && StringUtils.isNotBlank(zhDjdcbDO.getZhDjdcbIndex())) {
                return bdcdyService.listQlrByDjDcbIndex(zhDjdcbDO.getZhDjdcbIndex(), ZhQlrDO.class);
            }
        }
        return new ArrayList<>(0);
    }
}
