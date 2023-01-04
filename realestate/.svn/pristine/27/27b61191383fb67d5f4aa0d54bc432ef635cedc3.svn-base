package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 抵押权登记转移
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlDyqZydjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataDyqZydjServiceSet;

    public void setNationalAccessDataDyqZydjServiceSet(Set<NationalAccessDataService> nationalAccessDataDyqZydjServiceSet) {
        this.nationalAccessDataDyqZydjServiceSet = nationalAccessDataDyqZydjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "2003701";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataDyqZydjServiceSet;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 只获取权籍数据的service集合
     * @date : 2022/11/22 8:37
     */
    @Override
    public Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet() {
        return null;
    }
}
