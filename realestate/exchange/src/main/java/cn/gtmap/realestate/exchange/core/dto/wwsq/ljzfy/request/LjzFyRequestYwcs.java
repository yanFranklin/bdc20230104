package cn.gtmap.realestate.exchange.core.dto.wwsq.ljzfy.request;


import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-03
 * @description
 */
public class LjzFyRequestYwcs {

    private String zldz;

    private String dh;

    private String lszd;

//    @NotBlank
    private String zdqlr;

    //盐城-逻辑幢房屋名称模糊查询
    private String fwmc;

    private String qjgldm;

    //自然幢号
    private String zrzh;

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getFwmc() {
        return fwmc;
    }

    public void setFwmc(String fwmc) {
        this.fwmc = fwmc;
    }

    public String getZldz() {
        return zldz;
    }

    public void setZldz(String zldz) {
        this.zldz = zldz;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getZdqlr() {
        return zdqlr;
    }

    public void setZdqlr(String zdqlr) {
        this.zdqlr = zdqlr;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }
}
