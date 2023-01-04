package cn.gtmap.realestate.engine.core.enums;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/19
 * @description 数据流元素类型
 */
public enum BdcGzSjlYslxEnum {
    LIST_MAP("1", "List<Map>"),
    LIST("2", "List"),
    MAP("3", "MAP"),
    OBJ("4", "OBJ");

    /**
     * 编码
     */
    private String code;
    /**
     * 类型
     */
    private String yslx;


    BdcGzSjlYslxEnum(String code, String yslx){
        this.code = code;
        this.yslx = yslx;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYslx() {
        return yslx;
    }

    public void setYslx(String yslx) {
        this.yslx = yslx;
    }
}
