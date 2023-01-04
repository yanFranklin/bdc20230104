package cn.gtmap.realestate.exchange.core.domain.sjpt;

import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "gx_pe_wsxx")
public class GxPeWsxx {
    @Id
    private String wsxxid;
    private String cxsqdh;
    private String wsbh;


    public String getWsxxid() {
        return wsxxid;
    }

    public void setWsxxid(String wsxxid) {
        this.wsxxid = wsxxid;
    }

    public String getCxsqdh() {
        return cxsqdh;
    }

    public void setCxsqdh(String cxsqdh) {
        this.cxsqdh = cxsqdh;
    }

    public String getWsbh() {
        return wsbh;
    }

    public void setWsbh(String wsbh) {
        this.wsbh = wsbh;
    }


}
