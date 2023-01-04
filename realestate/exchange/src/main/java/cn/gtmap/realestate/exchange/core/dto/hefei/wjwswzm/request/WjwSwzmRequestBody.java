package cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description
 */
@XStreamAlias("body")
public class WjwSwzmRequestBody {

    @XStreamImplicit
    private List<WjwSwzmRequestData> data;

    public List<WjwSwzmRequestData> getData() {
        return data;
    }

    public void setData(List<WjwSwzmRequestData> data) {
        this.data = data;
    }
}
