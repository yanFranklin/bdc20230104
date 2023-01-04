package cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description
 */
@XStreamAlias("data")
public class WjwSwzmRequestData {

    @XStreamAlias("GMSFZH")
    private String gmsfzh;

    @XStreamAlias("XM")
    private String xm;

    public String getGmsfzh() {
        return gmsfzh;
    }

    public void setGmsfzh(String gmsfzh) {
        this.gmsfzh = gmsfzh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }
}
