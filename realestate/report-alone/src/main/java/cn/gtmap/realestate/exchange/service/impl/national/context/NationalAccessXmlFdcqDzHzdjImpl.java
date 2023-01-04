package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 房地产（项目内多幢房屋）换证登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlFdcqDzHzdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataFdcqDzHzdjServiceSet;

    public Set<NationalAccessDataService> getNationalAccessDataFdcqDzHzdjServiceSet() {
        return nationalAccessDataFdcqDzHzdjServiceSet;
    }

    public void setNationalAccessDataFdcqDzHzdjServiceSet(Set<NationalAccessDataService> nationalAccessDataFdcqDzHzdjServiceSet) {
        this.nationalAccessDataFdcqDzHzdjServiceSet = nationalAccessDataFdcqDzHzdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "9000401";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataFdcqDzHzdjServiceSet;
    }
}
