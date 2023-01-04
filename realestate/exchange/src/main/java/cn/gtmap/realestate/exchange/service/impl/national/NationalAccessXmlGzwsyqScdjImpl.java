package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 构（建）筑物所有权首次登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlGzwsyqScdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataGzwsyqScdjServiceSet;


    private Set<NationalAccessQjsjService> nationalAccessDataQjsjSet;

    public void setNationalAccessDataGzwsyqScdjServiceSet(Set<NationalAccessDataService> nationalAccessDataGzwsyqScdjServiceSet) {
        this.nationalAccessDataGzwsyqScdjServiceSet = nationalAccessDataGzwsyqScdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "1001601";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataGzwsyqScdjServiceSet;
    }

    @Override
    public Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet() {
        return nationalAccessDataQjsjSet;
    }

    public void setNationalAccessDataQjsjSet(Set<NationalAccessQjsjService> nationalAccessDataQjsjSet) {
        this.nationalAccessDataQjsjSet = nationalAccessDataQjsjSet;
    }
}
