package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 其他相关权登记换证
 *
 * @author hj
 * @version 1.0, 2021/10/23
 */
public class NationalAccessXmlQtxgqldjHzdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataQtxgqlHzdjServiceSet;

    public void setNationalAccessDataQtxgqlHzdjServiceSet(Set<NationalAccessDataService> nationalAccessDataQtxgqlHzdjServiceSet) {
        this.nationalAccessDataQtxgqlHzdjServiceSet = nationalAccessDataQtxgqlHzdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "9009901";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataQtxgqlHzdjServiceSet;
    }

}
