package cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-29
 * @description
 */
public class SjptCxjgRequestData {

    private String cxsqdh;

    private List<PeCommitCxsqjg> cxsqjg;

    public String getCxsqdh() {
        return cxsqdh;
    }

    public void setCxsqdh(String cxsqdh) {
        this.cxsqdh = cxsqdh;
    }

    public List<PeCommitCxsqjg> getCxsqjg() {
        return cxsqjg;
    }

    public void setCxsqjg(List<PeCommitCxsqjg> cxsqjg) {
        this.cxsqjg = cxsqjg;
    }
}
