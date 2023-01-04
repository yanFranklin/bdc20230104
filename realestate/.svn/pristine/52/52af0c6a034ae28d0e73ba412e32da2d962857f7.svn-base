package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 互联网审核状态枚举类
 *
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/5/14
 */
public enum HlwShztEnum {

    ONGOING("1", "审核中"),
    ABANDON("2", "审核不通过"),
    PASS("3", "审核通过"),
    CANCEL("4", "线上撤销");


    private String shzt;

    private String description;

    HlwShztEnum(String shzt, String description) {
        this.shzt = shzt;
        this.description = description;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescriptionByShzt(String shzt) {
        String description = "";
        if (StringUtils.isNotBlank(shzt)) {
            for (HlwShztEnum c : HlwShztEnum.values()) {
                //获得版本号与申请类型都符合的查封类型
                if (StringUtils.equals(shzt, c.getShzt())) {
                    description = c.getDescription();
                    break;
                }
            }
        }
        return description;
    }
}
