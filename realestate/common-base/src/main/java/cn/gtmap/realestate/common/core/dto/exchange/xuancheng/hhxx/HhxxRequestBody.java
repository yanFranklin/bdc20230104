package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx;

/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0  2022/12/19
 * @description
 */
public class HhxxRequestBody {
    /**
     * {
     *     "condition": {
     *         "SZXM": "",
     *         "SZZJH": ""
     *     },
     *     "requiredItems": "SZXM,SZZJH,SZXB,SZHJDZ,SWRQ,HHKSSJ"
     * }
     */

    /**
     * condition请求参数说明
     */
    private HhxxRequestCondition condition;

    /**
     *数据项集，要求返回的数据项集合，为空时返回所有,可选项为condition请求参数每个元素
     */
    private String requiredItems;

    public HhxxRequestCondition getCondition() {
        return condition;
    }

    public void setCondition(HhxxRequestCondition condition) {
        this.condition = condition;
    }

    public String getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(String requiredItems) {
        this.requiredItems = requiredItems;
    }
}
