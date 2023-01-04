package cn.gtmap.realestate.exchange.core.dto.wwsq.jtBdcxx.response;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2022/8/3 19:59
 * @description 家庭不动产信息对外返回
 */
public class JtBdcxxRespDTO {

    private String bdcdyh;
    private String syqzh;
    private String fwzl;
    private Double jzmj;
    private String ghyt;
    private String ghytmc;
    private String djlx;

    private List<JtQlrRespDTO> qlrlist;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getSyqzh() {
        return syqzh;
    }

    public void setSyqzh(String syqzh) {
        this.syqzh = syqzh;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getGhytmc() {
        return ghytmc;
    }

    public void setGhytmc(String ghytmc) {
        this.ghytmc = ghytmc;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public List<JtQlrRespDTO> getQlrlist() {
        return qlrlist;
    }

    public void setQlrlist(List<JtQlrRespDTO> qlrlist) {
        this.qlrlist = qlrlist;
    }

    @Override
    public String toString() {
        return "JtBdcxxRespDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", syqzh='" + syqzh + '\'' +
                ", fwzl='" + fwzl + '\'' +
                ", jzmj=" + jzmj +
                ", ghyt='" + ghyt + '\'' +
                ", ghytmc='" + ghytmc + '\'' +
                ", djlx='" + djlx + '\'' +
                ", qlrlist=" + qlrlist +
                '}';
    }
}
