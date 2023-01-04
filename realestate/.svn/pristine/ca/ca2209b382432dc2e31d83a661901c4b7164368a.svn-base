package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 小微类型对照枚举值
 * @date : 2020/10/12 15:00
 */
public enum BdcSlXwlxEnum {
    XWLX_XX("12","小型"),
    XWLX_WX("11","微型"),
    XWLX_ZX("20","中型")
    ;

    private String xwlxdm;

    private String xwlxmc;

    BdcSlXwlxEnum(String xwlxdm, String xwlxmc) {
        this.xwlxdm = xwlxdm;
        this.xwlxmc = xwlxmc;
    }

    public String getXwlxdm() {
        return xwlxdm;
    }

    public void setXwlxdm(String xwlxdm) {
        this.xwlxdm = xwlxdm;
    }

    public String getXwlxmc() {
        return xwlxmc;
    }

    public void setXwlxmc(String xwlxmc) {
        this.xwlxmc = xwlxmc;
    }

    public static String queryXwlx(String xwlxdm) {
        String result = "";
        for(BdcSlXwlxEnum bdcSlXwlxEnum : BdcSlXwlxEnum.values()) {
            if(StringUtils.equals(bdcSlXwlxEnum.xwlxdm,xwlxdm)) {
                result = bdcSlXwlxEnum.getXwlxmc();
                return result;
            }
        }
        return result;
    }
}
