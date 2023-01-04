package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

import java.util.Set;

/**
 * 房地产权（项目内多幢房屋）转移登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlFdcqDzZydjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataFdcqDzZydjServiceSet;

    public void setNationalAccessDataFdcqDzZydjServiceSet(Set<NationalAccessDataService> nationalAccessDataFdcqDzZydjServiceSet) {
        this.nationalAccessDataFdcqDzZydjServiceSet = nationalAccessDataFdcqDzZydjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "2000401";
    }


    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataFdcqDzZydjServiceSet;
    }
}
