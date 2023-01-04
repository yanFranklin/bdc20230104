package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 构（建）筑物 所有权转移登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlGzwsyqZydjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataGzwsyqZydjServiceSet;

    public void setNationalAccessDataGzwsyqZydjServiceSet(Set<NationalAccessDataService> nationalAccessDataGzwsyqZydjServiceSet) {
        this.nationalAccessDataGzwsyqZydjServiceSet = nationalAccessDataGzwsyqZydjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "2001601";
    }


    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataGzwsyqZydjServiceSet;
    }
}
