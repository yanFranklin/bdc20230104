package cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcxfk.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-04
 * @description
 */
@XStreamAlias("BRIEF")
public class SfpjcxfkResponseBrief {

    @XStreamAlias("NUM")
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
