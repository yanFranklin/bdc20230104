package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 构（建）筑物 所有权转移登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlGzwsyqZydjImpl extends NationalAccessXmlAbstractService{

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
