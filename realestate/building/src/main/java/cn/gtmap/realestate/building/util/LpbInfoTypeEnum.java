package cn.gtmap.realestate.building.util;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-10-31
 * @description 楼盘表展现页面配置 信息类型 枚举值
 */
public enum LpbInfoTypeEnum {

    CONSTANT("CONSTANT",Constants.LPBCONFIG_INFO_SERVICE_CONSTANT),
    ZDCOLUMN("ZDCOLUMN",Constants.LPBCONFIG_INFO_SERVICE_ZDCOLUMN),
    SHOWCOLUMN("SHOWCOLUMN",Constants.LPBCONFIG_INFO_SERVICE_CONSTANT),
    BUTTON("BUTTON",Constants.LPBCONFIG_INFO_SERVICE_BUTTON),
    COLUMN("COLUMN", Constants.LPBCONFIG_INFO_SERVICE_COLUMN),
    JOINCOLUMN("JOINCOLUMN",Constants.LPBCONFIG_INFO_SERVICE_JOINCOLUMN),
    NVLCOLUMN("NVLCOLUMN",Constants.LPBCONFIG_INFO_SERVICE_NVLCOLUMN);

    public String type;

    public String beanName;

    LpbInfoTypeEnum(String type,String beanName){
        this.type = type;
        this.beanName = beanName;
    }


}
