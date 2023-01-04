package cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description 卫计委 出生证明 反馈实体
 */
@XStreamAlias("MDEML")
public class WjwSwzmResponseDTO {

    @XStreamAsAttribute
    private String templateVersion = "1.0";

    @XStreamImplicit
    private List<WjwSwzmResponseInfo> resultinfo;
    @XStreamImplicit
    private List<WjwSwzmResponseBody> body;

    public List<WjwSwzmResponseInfo> getResultinfo() {
        return resultinfo;
    }

    public void setResultinfo(List<WjwSwzmResponseInfo> resultinfo) {
        this.resultinfo = resultinfo;
    }

    public List<WjwSwzmResponseBody> getBody() {
        return body;
    }

    public void setBody(List<WjwSwzmResponseBody> body) {
        this.body = body;
    }
}
