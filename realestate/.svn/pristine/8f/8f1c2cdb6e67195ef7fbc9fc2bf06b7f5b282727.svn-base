package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 地役权登记首次
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlDyiqScdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataDyiqScdjServiceSet;

    public void setNationalAccessDataDyiqScdjServiceSet(Set<NationalAccessDataService> nationalAccessDataDyiqScdjServiceSet) {
        this.nationalAccessDataDyiqScdjServiceSet = nationalAccessDataDyiqScdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "1002101";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataDyiqScdjServiceSet;
    }
}
