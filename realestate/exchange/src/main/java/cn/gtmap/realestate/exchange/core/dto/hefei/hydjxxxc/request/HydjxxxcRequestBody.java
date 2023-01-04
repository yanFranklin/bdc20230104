package cn.gtmap.realestate.exchange.core.dto.hefei.hydjxxxc.request;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/3/18 17:01
 * @description
 */
public class HydjxxxcRequestBody {

    private HydjxxxcRequestCondition condition;

    private String requiredItems;

    public HydjxxxcRequestCondition getCondition() {
        return condition;
    }

    public void setCondition(HydjxxxcRequestCondition condition) {
        this.condition = condition;
    }

    public String getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(String requiredItems) {
        this.requiredItems = requiredItems;
    }
}
