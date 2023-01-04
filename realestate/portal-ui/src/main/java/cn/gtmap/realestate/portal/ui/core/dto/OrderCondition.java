package cn.gtmap.realestate.portal.ui.core.dto;

public class OrderCondition {
    /**
     * 排序 key 值
     */
    private String requestKey;

    /**
     * 排序方式
     */
    private Object requestValue;

    /**
     * 排序
     */
    private Integer sort;

    public OrderCondition() {
    }

    public OrderCondition(String requestKey, Object requestValue, Integer sort) {
        this.requestKey = requestKey;
        this.requestValue = requestValue;
        this.sort = sort;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public Object getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(Object requestValue) {
        this.requestValue = requestValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
