package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 房地产权（项目内多幢房屋）更正登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlFdcqDzGzdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataFdcqDzGzdjServiceSet;

    private Set<NationalAccessQjsjService> nationalAccessDataQjsjSet;

    public void setNationalAccessDataFdcqDzGzdjServiceSet(Set<NationalAccessDataService> nationalAccessDataFdcqDzGzdjServiceSet) {
        this.nationalAccessDataFdcqDzGzdjServiceSet = nationalAccessDataFdcqDzGzdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "5000401";
    }


    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataFdcqDzGzdjServiceSet;
    }

    @Override
    public Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet() {
        return nationalAccessDataQjsjSet;
    }

    public void setNationalAccessDataQjsjSet(Set<NationalAccessQjsjService> nationalAccessDataQjsjSet) {
        this.nationalAccessDataQjsjSet = nationalAccessDataQjsjSet;
    }
}
