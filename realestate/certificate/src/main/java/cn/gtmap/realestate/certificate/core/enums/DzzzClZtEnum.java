package cn.gtmap.realestate.certificate.core.enums;

public enum DzzzClZtEnum {

    DZZZ_CL_QF(1, "存量电子证照签发"), DZZZ_CL_XZ(2, "存量电子证照 pdf 下载");

    /**
     * code 值，页面传递过来的值
     */
    private Integer code;

    /**
     * 存量状态操作内容
     */
    private String value;

    /**
     * 是否生成 pdf
     */
    private String sfscpdf;
    /**
     * 是否生成证照标示
     */
    private String sfsczzbs;


    DzzzClZtEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
        if (code == 1) {
            this.sfscpdf = "0";
            this.sfsczzbs = "0";
        } else if (code == 2) {
            this.sfscpdf = "0";
            this.sfsczzbs = "1";
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getSfscpdf() {
        return sfscpdf;
    }

    public String getSfsczzbs() {
        return sfsczzbs;
    }

    public String getValue() {
        return value;
    }
}
