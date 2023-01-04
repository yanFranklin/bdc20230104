package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 建设用地使用权、宅基地使用权变更登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlJsydsyqBgdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataJsydsyqBgdjServiceSet;

    public void setNationalAccessDataJsydsyqBgdjServiceSet(Set<NationalAccessDataService> nationalAccessDataJsydsyqBgdjServiceSet) {
        this.nationalAccessDataJsydsyqBgdjServiceSet = nationalAccessDataJsydsyqBgdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "3000301";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataJsydsyqBgdjServiceSet;
    }
}
