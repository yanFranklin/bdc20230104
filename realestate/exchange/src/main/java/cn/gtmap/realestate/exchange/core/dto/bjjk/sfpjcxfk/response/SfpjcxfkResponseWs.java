package cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcxfk.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-04
 * @description
 */
@XStreamAlias("t_ws")
public class SfpjcxfkResponseWs {

    // base64位 html 格式文书内容
    @XStreamAlias("c_nr")
    private String cNr;

    public String getcNr() {
        return cNr;
    }

    public void setcNr(String cNr) {
        this.cNr = cNr;
    }
}
