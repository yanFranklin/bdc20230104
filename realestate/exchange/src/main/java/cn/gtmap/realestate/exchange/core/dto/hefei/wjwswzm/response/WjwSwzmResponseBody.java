package cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description
 */
@XStreamAlias("body")
public class WjwSwzmResponseBody {

    private String errorMsg;

    @XStreamImplicit
    private List<WjwSwzmResponseData> data;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<WjwSwzmResponseData> getData() {
        return data;
    }

    public void setData(List<WjwSwzmResponseData> data) {
        this.data = data;
    }
}
