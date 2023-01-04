package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 其他相关权登记首次
 *
 * @author hj
 * @version 1.0, 2021/10/23
 */
public class NationalAccessXmlQtxgqldjScdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataQtxgqlScdjServiceSet;

    public void setNationalAccessDataQtxgqlScdjServiceSet(Set<NationalAccessDataService> nationalAccessDataQtxgqlScdjServiceSet) {
        this.nationalAccessDataQtxgqlScdjServiceSet = nationalAccessDataQtxgqlScdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "1009901";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataQtxgqlScdjServiceSet;
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
