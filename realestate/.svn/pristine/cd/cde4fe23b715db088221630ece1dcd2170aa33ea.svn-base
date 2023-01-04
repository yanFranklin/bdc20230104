package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 抵押权登记 添加户室信息
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlDyqdjWithHImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataDyqdjServiceSet;

    private Set<NationalAccessQjsjService> nationalAccessDataQjsjSet;

    public void setNationalAccessDataDyqdjServiceSet(Set<NationalAccessDataService> nationalAccessDataDyqdjServiceSet) {
        this.nationalAccessDataDyqdjServiceSet = nationalAccessDataDyqdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "9000101";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataDyqdjServiceSet;
    }

    @Override
    public Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet() {
        return nationalAccessDataQjsjSet;
    }

    public void setNationalAccessDataQjsjSet(Set<NationalAccessQjsjService> nationalAccessDataQjsjSet) {
        this.nationalAccessDataQjsjSet = nationalAccessDataQjsjSet;
    }
}
