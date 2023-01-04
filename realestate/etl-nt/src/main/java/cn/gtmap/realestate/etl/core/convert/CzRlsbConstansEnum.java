package cn.gtmap.realestate.etl.core.convert;

public enum CzRlsbConstansEnum {

    MAN(1, "男"),
    WOMAN(2, "女"),
    UNKNOW(3, "不详");

    private Integer value;

    private String description;

    CzRlsbConstansEnum(Integer value, String description) {
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
