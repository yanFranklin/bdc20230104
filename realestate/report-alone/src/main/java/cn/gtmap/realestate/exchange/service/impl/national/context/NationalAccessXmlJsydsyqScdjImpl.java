package cn.gtmap.realestate.exchange.service.impl.national.context;

import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlAbstractService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 建设用地使用权、宅基地使用权首次登记
 *
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/11/16
 */
@Service
public class NationalAccessXmlJsydsyqScdjImpl extends NationalAccessXmlAbstractService {

    private Set<NationalAccessDataService> nationalAccessDataJsydsyqScdjServiceSet;

    public void setNationalAccessDataJsydsyqScdjServiceSet(Set<NationalAccessDataService> nationalAccessDataJsydsyqScdjServiceSet) {
        this.nationalAccessDataJsydsyqScdjServiceSet = nationalAccessDataJsydsyqScdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "1000301";
    }


    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataJsydsyqScdjServiceSet;
    }
}
