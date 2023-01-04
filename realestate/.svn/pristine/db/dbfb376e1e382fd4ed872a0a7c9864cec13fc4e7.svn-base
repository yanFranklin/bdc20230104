package cn.gtmap.realestate.common.core.enums;
import lombok.Getter;
/**
 * 汇总维度
 */
public enum SummaryDimension {
    DAY("DAY", "日"),
    MONTH("MONTH", "月"),
    YEAR("YEAR", "年"),
    TENYEAR("TENYEAR", "10年"),
    QXDM("QXDM", "区县");

    private final String code;
    private final String disc;

    public String getCode() {
        return code;
    }

    public String getDisc() {
        return disc;
    }

    SummaryDimension(String code, String disc) {
        this.code = code;
        this.disc = disc;
    }

    public static String code2desc(String status) {
        SummaryDimension[] values = SummaryDimension.values();
        for (SummaryDimension value : values) {
            if (value.getCode().equals(status)) {
                return value.getDisc();
            }
        }
        return null;
    }
}
