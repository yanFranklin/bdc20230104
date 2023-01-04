package cn.gtmap.realestate.exchange.core.dto.bjjk.gasfhc.request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-06
 * @description
 */
public class GasfhcRequestRequestParam {

    private List<GasfhcRequestConditions> Conditions ;

    public  GasfhcRequestRequestParam(){
        Conditions = new ArrayList<>();
        Conditions.add(new GasfhcRequestConditions());
    }

    public List<GasfhcRequestConditions> getConditions() {
        return Conditions;
    }

    public void setConditions(List<GasfhcRequestConditions> conditions) {
        Conditions = conditions;
    }
}
