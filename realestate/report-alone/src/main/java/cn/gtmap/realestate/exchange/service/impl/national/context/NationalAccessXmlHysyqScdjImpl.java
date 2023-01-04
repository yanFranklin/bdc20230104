package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 海域（含无居民海岛）使用权首次登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlHysyqScdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataHysyqScdjServiceSet;

    public void setNationalAccessDataHysyqScdjServiceSet(Set<NationalAccessDataService> nationalAccessDataHysyqScdjServiceSet) {
        this.nationalAccessDataHysyqScdjServiceSet = nationalAccessDataHysyqScdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "1001501";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataHysyqScdjServiceSet;
    }
}
