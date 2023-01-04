package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 房地产权(独幢、层、间、套、房屋)换证登记
 *
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/11/16
 */

public class NationalAccessXmlFdcqYzHzdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataFdcqYzHzdjServiceSet;

    private Set<NationalAccessQjsjService> nationalAccessDataQjsjSet;

    public void setNationalAccessDataFdcqYzHzdjServiceSet(Set<NationalAccessDataService> nationalAccessDataFdcqYzHzdjServiceSet) {
        this.nationalAccessDataFdcqYzHzdjServiceSet = nationalAccessDataFdcqYzHzdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "9000402";
    }


    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataFdcqYzHzdjServiceSet;
    }

    @Override
    public Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet() {
        return nationalAccessDataQjsjSet;
    }

    public void setNationalAccessDataQjsjSet(Set<NationalAccessQjsjService> nationalAccessDataQjsjSet) {
        this.nationalAccessDataQjsjSet = nationalAccessDataQjsjSet;
    }
}
