package cn.gtmap.realestate.exchange.core.dto.hefei.wjwcszm.response;

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
public class WjwCszmResponseDTO {

    @XStreamAsAttribute
    private String templateVersion = "1.0";

    @XStreamImplicit
    private List<WjwCszmResponseInfo> resultinfo;
    @XStreamImplicit
    private List<WjwCszmResponseBody> body;

    public List<WjwCszmResponseInfo> getResultinfo() {
        return resultinfo;
    }

    public void setResultinfo(List<WjwCszmResponseInfo> resultinfo) {
        this.resultinfo = resultinfo;
    }

    public List<WjwCszmResponseBody> getBody() {
        return body;
    }

    public void setBody(List<WjwCszmResponseBody> body) {
        this.body = body;
    }
}
