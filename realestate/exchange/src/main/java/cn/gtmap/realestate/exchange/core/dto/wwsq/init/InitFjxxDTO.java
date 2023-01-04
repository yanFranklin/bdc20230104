package cn.gtmap.realestate.exchange.core.dto.wwsq.init;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-06
 * @description
 */
public class InitFjxxDTO {

    private String clmc;

    private String ys;

    private String fs;

    private String cldm;

    // 默认附件页数
    private Integer mrfjys;

    // 附件类型
    private String fjlx;

    private List<InitFjxxClnr> clnr;

    public String getCldm() {
        return cldm;
    }

    public void setCldm(String cldm) {
        this.cldm = cldm;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public String getYs() {
        return ys;
    }

    public void setYs(String ys) {
        this.ys = ys;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public List<InitFjxxClnr> getClnr() {
        return clnr;
    }

    public void setClnr(List<InitFjxxClnr> clnr) {
        this.clnr = clnr;
    }

    public Integer getMrfjys() {
        return mrfjys;
    }

    public void setMrfjys(Integer mrfjys) {
        this.mrfjys = mrfjys;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }
}
