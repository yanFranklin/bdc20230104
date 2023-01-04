package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 农用地使用权（非林地）更正登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlNydsyqGzdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataNydsyqGzdjServiceSet;

    public void setNationalAccessDataNydsyqGzdjServiceSet(Set<NationalAccessDataService> nationalAccessDataNydsyqGzdjServiceSet) {
        this.nationalAccessDataNydsyqGzdjServiceSet = nationalAccessDataNydsyqGzdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "5000901";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataNydsyqGzdjServiceSet;
    }

    @Override
    public Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet() {
        return null;
    }
}
