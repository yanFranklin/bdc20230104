package cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.response;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/13
 * @description 关联土地证信息DTO
 */
public class WwsqGltdzxxDTO {


    /**
     * tdzl :
     * tdzh :
     * xmid :
     * qlr : []
     */

    private String tdzl;
    private String tdzh;
    private String xmid;
    private List<GltdzxxQlrDTO> qlr;

    public String getTdzl() {
        return tdzl;
    }

    public void setTdzl(String tdzl) {
        this.tdzl = tdzl;
    }

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public List<GltdzxxQlrDTO> getQlr() {
        return qlr;
    }

    public void setQlr(List<GltdzxxQlrDTO> qlr) {
        this.qlr = qlr;
    }
}
