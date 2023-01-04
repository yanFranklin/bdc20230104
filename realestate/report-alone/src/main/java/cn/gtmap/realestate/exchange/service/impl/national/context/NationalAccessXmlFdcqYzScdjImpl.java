package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 房地产权(独幢、层、间、套、房屋)首次登记
 *
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/11/16
 */

public class NationalAccessXmlFdcqYzScdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataFdcqYzScdjServiceSet;

    public void setNationalAccessDataFdcqYzScdjServiceSet(Set<NationalAccessDataService> nationalAccessDataFdcqYzScdjServiceSet) {
        this.nationalAccessDataFdcqYzScdjServiceSet = nationalAccessDataFdcqYzScdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "1000402";
    }



    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataFdcqYzScdjServiceSet;
    }
}
