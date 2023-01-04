package cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcxfk.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-04
 * @description
 */
@XStreamAlias("t_aj")
public class SfpjcxfkResponseAj {

    // 实体码
    @XStreamAlias("c_stm")
    private String cStm;

    // 案号
    @XStreamAlias("c_ah")
    private String cAh;

    @XStreamAlias("c_gshah")
    private String cGshah;

    @XStreamImplicit
    private List<SfpjcxfkResponseAjDsr> dsrList;

    @XStreamImplicit
    private List<SfpjcxfkResponseWs> ws;

    public String getcStm() {
        return cStm;
    }

    public void setcStm(String cStm) {
        this.cStm = cStm;
    }

    public String getcAh() {
        return cAh;
    }

    public void setcAh(String cAh) {
        this.cAh = cAh;
    }

    public List<SfpjcxfkResponseAjDsr> getDsrList() {
        return dsrList;
    }

    public void setDsrList(List<SfpjcxfkResponseAjDsr> dsrList) {
        this.dsrList = dsrList;
    }

    public List<SfpjcxfkResponseWs> getWs() {
        return ws;
    }

    public void setWs(List<SfpjcxfkResponseWs> ws) {
        this.ws = ws;
    }

    public String getcGshah() {
        return cGshah;
    }

    public void setcGshah(String cGshah) {
        this.cGshah = cGshah;
    }
}
