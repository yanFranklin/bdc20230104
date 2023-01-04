package cn.gtmap.realestate.exchange.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 建设银行受理状态枚举类
 *
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/5/14
 */
public enum CCbSlztEnum {

   /* DELETE("1", "删除"),
    REJECT("3", "未通过"),
    PASS("4", "预审核通过"),
    ABANDON("5", "预审核不通过"),
    SZDB("7", "审核登簿"),
    TERMINATE("8", "终止"),
    FJERROR("9", "附件错误");*/

    DELETE("1", "-1"),
    REJECT("3", "0"),
    PASS("4", "3"),
    ABANDON("5", "0"),
    SZDB("7", "9"),
    TERMINATE("8", "-1"),
    FJERROR("9", "0");

    private String slzt;

    private String description;

    CCbSlztEnum(String slzt, String description) {
        this.slzt = slzt;
        this.description = description;
    }

    public String getSlzt() {
        return slzt;
    }

    public void setSlzt(String slzt) {
        this.slzt = slzt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescriptionBySlzt(String slzt) {
        String description = "";
        if (StringUtils.isNotBlank(slzt)) {
            for (CCbSlztEnum c : CCbSlztEnum.values()) {
                //获得版本号与申请类型都符合的查封类型
                if (StringUtils.equals(slzt, c.getSlzt())) {
                    description = c.getDescription();
                    break;
                }
            }
        }
        return description;
    }
}
