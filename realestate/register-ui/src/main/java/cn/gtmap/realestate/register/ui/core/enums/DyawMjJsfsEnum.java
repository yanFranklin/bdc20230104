package cn.gtmap.realestate.register.ui.core.enums;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/3/23
 * @description
 */
public enum DyawMjJsfsEnum {
    TYPE1("不区分bdclx，分别累计所有单元的土地抵押面积、房屋抵押面积", "1"),
    TYPE2("区分bdclx，累计bdclx为房屋的房屋抵押面积，累计bdclx为土地的土地抵押面积", "2"),
    TYPE3("根据djh分组，计算土地抵押面积和房屋抵押面积", "3"),
    TYPE4("区分bdclx，房屋抵押权不计算土地面积，土地抵押权不计算房屋面积", "4");

    private String mc;
    private String dm;

    DyawMjJsfsEnum(String mc, String dm) {
        this.dm = dm;
        this.mc = mc;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }


}
