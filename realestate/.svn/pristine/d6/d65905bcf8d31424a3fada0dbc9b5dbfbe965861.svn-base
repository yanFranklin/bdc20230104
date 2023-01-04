package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 建设用地使用权、宅基地使用权转移登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlJsydsyqZydjImpl extends NationalAccessXmlAbstractService{

    private Set<NationalAccessDataService> nationalAccessDataJsydsyqZydjServiceSet;

    public void setNationalAccessDataJsydsyqZydjServiceSet(Set<NationalAccessDataService> nationalAccessDataJsydsyqZydjServiceSet) {
        this.nationalAccessDataJsydsyqZydjServiceSet = nationalAccessDataJsydsyqZydjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "2000301";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataJsydsyqZydjServiceSet;
    }

    @Override
    public Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet() {
        return null;
    }
}
