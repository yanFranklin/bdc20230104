package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 其他相关权登记更正
 *
 * @author hj
 * @version 1.0, 2021/10/23
 */
public class NationalAccessXmlQtxgqldjGzdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataQtxgqlGzdjServiceSet;

    public void setNationalAccessDataQtxgqlGzdjServiceSet(Set<NationalAccessDataService> nationalAccessDataQtxgqlGzdjServiceSet) {
        this.nationalAccessDataQtxgqlGzdjServiceSet = nationalAccessDataQtxgqlGzdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "5009901";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataQtxgqlGzdjServiceSet;
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
