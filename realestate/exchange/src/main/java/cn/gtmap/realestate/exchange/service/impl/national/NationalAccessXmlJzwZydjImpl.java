package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;

import java.util.Set;

/**
 * 建筑物区分所有权业主共有部分转移登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlJzwZydjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataJzwZydjServiceSet;

    public void setNationalAccessDataJzwZydjServiceSet(Set<NationalAccessDataService> nationalAccessDataJzwZydjServiceSet) {
        this.nationalAccessDataJzwZydjServiceSet = nationalAccessDataJzwZydjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "2000403";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataJzwZydjServiceSet;
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