package cn.gtmap.realestate.exchange.core.dto.bjjk.gasfhc.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-06
 * @description
 */
public class GasfhcRequestConditions {

    @JSONField(name = "CheckCondition")
    private Map CheckCondition;

    @JSONField(name = "QueryCondition")
    private String QueryCondition = "ORG_CODE='010000'";

    public Map getCheckCondition() {
        return CheckCondition;
    }

    public void setCheckCondition(Map checkCondition) {
        CheckCondition = checkCondition;
    }

    public String getQueryCondition() {
        return QueryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        QueryCondition = queryCondition;
    }
}
