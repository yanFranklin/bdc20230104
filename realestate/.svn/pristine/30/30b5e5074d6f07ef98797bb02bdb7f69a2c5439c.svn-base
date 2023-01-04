package cn.gtmap.realestate.portal.ui.core;

import cn.gtmap.realestate.portal.ui.service.GeneralForwardRule;
import cn.gtmap.realestate.portal.ui.service.impl.AutoForwardRule;
import cn.gtmap.realestate.portal.ui.service.impl.NormalForwardRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForwardRuleFactory {
    @Autowired
    private AutoForwardRule autoForwardRule;

    @Autowired
    private NormalForwardRule normalForwardRule;

    public GeneralForwardRule getGeneralForwardRule(boolean dispatchEnable) {
        return dispatchEnable ? autoForwardRule : normalForwardRule;
    }
}
