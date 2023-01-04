package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 房地产权（项目内多幢房屋）首次登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlFdcqDzScdjImpl extends NationalAccessXmlAbstractService {
    private Set<NationalAccessDataService> nationalAccessDataFdcqDzScdjServiceSet;

    public void setNationalAccessDataFdcqDzScdjServiceSet(Set<NationalAccessDataService> nationalAccessDataFdcqDzScdjServiceSet) {
        this.nationalAccessDataFdcqDzScdjServiceSet = nationalAccessDataFdcqDzScdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "1000401";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataFdcqDzScdjServiceSet;
    }
}
