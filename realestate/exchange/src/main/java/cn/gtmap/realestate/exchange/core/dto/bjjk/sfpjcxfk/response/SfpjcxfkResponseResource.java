package cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcxfk.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-04
 * @description
 */
@XStreamAlias("RESOURCE")
public class SfpjcxfkResponseResource {

    @XStreamImplicit
    private List<SfpjcxfkResponseBrief> brief;

    @XStreamAlias("data")
    private List<SfpjcxfkResponseAj> data;

    public List<SfpjcxfkResponseBrief> getBrief() {
        return brief;
    }

    public void setBrief(List<SfpjcxfkResponseBrief> brief) {
        this.brief = brief;
    }

    public List<SfpjcxfkResponseAj> getData() {
        return data;
    }

    public void setData(List<SfpjcxfkResponseAj> data) {
        this.data = data;
    }
}
