package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 建筑物区分所有权业主共有部分换证登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlJzwHzdjImpl extends NationalAccessXmlAbstractService {
    private Set<NationalAccessDataService> nationalAccessDataJzwHzdjServiceSet;

    public void setNationalAccessDataJzwHzdjServiceSet(Set<NationalAccessDataService> nationalAccessDataJzwHzdjServiceSet) {
        this.nationalAccessDataJzwHzdjServiceSet = nationalAccessDataJzwHzdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "9000403";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataJzwHzdjServiceSet;
    }
}
