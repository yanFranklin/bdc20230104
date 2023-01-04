package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/18
 * @description  工程进度枚举类
 */
public enum  GcjdEnum {

    NJ("1", "拟建"),
    ZJ("2", "在建"),
    JG("3", "竣工"),
    SD("4", "首登"),
    YRW("5", "已入网");

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  代码
     */
    private String dm;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 名称
     */
    private String mc;

    private String getDm() {
        return dm;
    }

    private void setDm(String dm) {
        this.dm = dm;
    }

    private String getMc() {
        return mc;
    }

    private void setMc(String mc) {
        this.mc = mc;
    }

    GcjdEnum(String dm, String mc) {
        this.dm = dm;
        this.mc = mc;
    }

    public static String getMcByDm(String dm) {
        String mc = "";
        if (StringUtils.isNotBlank(dm)) {
            for (GcjdEnum gcjdEnum : GcjdEnum.values()) {
                if (StringUtils.equals(dm, gcjdEnum.getDm())) {
                    mc = gcjdEnum.getMc();
                    break;
                }
            }
        }
        return mc;
    }
}
