package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 地役权登记换证
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlDyiqHzdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataDyiqHzdjServiceSet;

    public void setNationalAccessDataDyiqHzdjServiceSet(Set<NationalAccessDataService> nationalAccessDataDyiqHzdjServiceSet) {
        this.nationalAccessDataDyiqHzdjServiceSet = nationalAccessDataDyiqHzdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "9002101";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataDyiqHzdjServiceSet;
    }
}
