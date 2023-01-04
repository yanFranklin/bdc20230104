package cn.gtmap.realestate.exchange.core.domain.sjpt;

import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "gx_pe_cxqq_xm")
public class GxPeCxqqXm {
    @Id
    private String xmid; //项目id
    private String qlrmc; //权利人名称
    private String qlrzjh; //权利人证件号
    private String qlrzjlx; //权利人证件类型
    private String wsbh; //文书编号
    private String cxsqdh; //查询申请单号
    private String cxzt;//查询状态 0：或者空，未查询，1：已查询
    private String cxqy;//查询区域 （行政区代码）
    private String cxfw;//查询范围, 0-历史+现势 ; 1-现势

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrzjlx() {
        return qlrzjlx;
    }

    public void setQlrzjlx(String qlrzjlx) {
        this.qlrzjlx = qlrzjlx;
    }

    public String getWsbh() {
        return wsbh;
    }

    public void setWsbh(String wsbh) {
        this.wsbh = wsbh;
    }

    public String getCxsqdh() {
        return cxsqdh;
    }

    public void setCxsqdh(String cxsqdh) {
        this.cxsqdh = cxsqdh;
    }

    public String getCxzt() {
        return cxzt;
    }

    public void setCxzt(String cxzt) {
        this.cxzt = cxzt;
    }

    public String getCxqy() {
        return cxqy;
    }

    public void setCxqy(String cxqy) {
        this.cxqy = cxqy;
    }

    public String getCxfw() {
        return cxfw;
    }

    public void setCxfw(String cxfw) {
        this.cxfw = cxfw;
    }
}
