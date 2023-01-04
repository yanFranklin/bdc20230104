package cn.gtmap.realestate.exchange.core.dto.hefei.wjwcszm.request;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description 卫计委 出生证明请求接口xml实体
 */
@XStreamAlias("MDEML")
public class WjwCszmRequestDTO {

    @XStreamAsAttribute
    private String templateVersion = "1.0";

    @XStreamImplicit
    private List<WjwCszmRequestInfo> requestinfo;

    @XStreamImplicit
    private List<WjwCszmRequestBody> body;



    public List<WjwCszmRequestInfo> getRequestinfo() {
        return requestinfo;
    }

    public void setRequestinfo(List<WjwCszmRequestInfo> requestinfo) {
        this.requestinfo = requestinfo;
    }

    public List<WjwCszmRequestBody> getBody() {
        return body;
    }

    public void setBody(List<WjwCszmRequestBody> body) {
        this.body = body;
    }
}
