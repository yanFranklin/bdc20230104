package cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.request;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description 卫计委 出生证明请求接口xml实体
 */
@XStreamAlias("MDEML")
public class WjwSwzmRequestDTO {

    @XStreamAsAttribute
    private String templateVersion = "1.0";

    @XStreamImplicit
    private List<WjwSwzmRequestInfo> requestinfo;

    @XStreamImplicit
    private List<WjwSwzmRequestBody> body;



    public List<WjwSwzmRequestInfo> getRequestinfo() {
        return requestinfo;
    }

    public void setRequestinfo(List<WjwSwzmRequestInfo> requestinfo) {
        this.requestinfo = requestinfo;
    }

    public List<WjwSwzmRequestBody> getBody() {
        return body;
    }

    public void setBody(List<WjwSwzmRequestBody> body) {
        this.body = body;
    }
}
