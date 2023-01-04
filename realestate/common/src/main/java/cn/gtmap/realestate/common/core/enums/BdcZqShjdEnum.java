package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/16
 * @description 盐城征迁审核阶段枚举
 */
public enum BdcZqShjdEnum {
    QRJD(1, "确认阶段"),
    SHJD(2, "审核阶段");


    BdcZqShjdEnum(Integer code, String mc){
        this.code = code;
        this.mc = mc;
    }

    /**
     * 编码
     */
    private Integer code;

    /**
     * 名称含义
     */
    private String mc;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}
