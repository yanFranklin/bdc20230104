package cn.gtmap.realestate.common.core.enums;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/4/20
 * @description 不动产抵押变更类型枚举
 * 根据蚌埠的需要，暂时确定一种变更类型，后续根据需求，继续完善该类信息
 */
public enum BdcDyabgTypeEnum {
    DYABG_TYPE1(1, "当前流程为抵押部分注销，变更后的信息为原流程现势的信息");


    BdcDyabgTypeEnum(Integer dm, String description) {
        this.dm = dm;
        this.description = description;
    }

    /**
     * 枚举类代码
     */
    private Integer dm;
    /**
     * 枚举类说明
     */
    private String description;

    public Integer getDm() {
        return dm;
    }

    public void setDm(Integer dm) {
        this.dm = dm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
