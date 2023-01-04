package cn.gtmap.realestate.common.core.dto.exchange.wwsq;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-05
 * @description
 */
public class FjclDTOForZhlc {

    /** 材料名称*/
    private String clmc;
    /** 份数*/
    private Integer fs;
    /** 序号*/
    private Integer xh;
    /** 页数*/
    private Integer ys;

    // 默认附件页数
    private Integer mrfjys;

    // 附件类型
    private String fjlx;

    private List<FjclmxDTO> clnr;

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public Integer getFs() {
        return fs;
    }

    public void setFs(Integer fs) {
        this.fs = fs;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getYs() {
        return ys;
    }

    public void setYs(Integer ys) {
        this.ys = ys;
    }

    public List<FjclmxDTO> getClnr() {
        return clnr;
    }

    public void setClnr(List<FjclmxDTO> clnr) {
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
