package cn.gtmap.realestate.exchange.core.enums;

/**
 * 和法院交互方式
 */
public enum BdcCourtKzcsEnum {
    CF("11", "查封"),
    XF("12", "继续查封"),
    JF("14", "解除查封"),
    WSSD("15", "产权过户文书送达"),
    ;

    private String code;

    private String description;

    BdcCourtKzcsEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
