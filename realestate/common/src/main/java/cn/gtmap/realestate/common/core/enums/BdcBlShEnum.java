package cn.gtmap.realestate.common.core.enums;

/**
 * 补录审核状态枚举类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 3:40 下午 2020/4/17
 */
public enum BdcBlShEnum {

    ONGOING(0, "正在审核"),
    NOT(1, "未在审核"),
    ONGOING_PERSONAL(2, "正在审核"),
    MODIFY(3, "正在审核");

    private Integer value;

    private String description;

    BdcBlShEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
