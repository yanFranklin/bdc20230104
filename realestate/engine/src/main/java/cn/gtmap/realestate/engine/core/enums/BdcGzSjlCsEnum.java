package cn.gtmap.realestate.engine.core.enums;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/12
 * @description 数据流参数种类
 */
public enum BdcGzSjlCsEnum {
    CL("1", "常量"),
    RC("2", "入参"),
    DC("3", "动参"),
    ST("4", "实体");

    /**
     * 标识
     */
    private String code;
    /**
     * 名称
     */
    private String name;


    BdcGzSjlCsEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
