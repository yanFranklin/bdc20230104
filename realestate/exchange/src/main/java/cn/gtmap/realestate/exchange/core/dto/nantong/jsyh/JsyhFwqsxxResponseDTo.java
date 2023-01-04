package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/05/12,1.0
 * @description
 */
@XStreamAlias("message")
public class JsyhFwqsxxResponseDTo {

    @XStreamAlias("body")
    private JsyhFwqsxxBodyResponseDTo body;
    @XStreamAlias("head")
    private JsyhFwqsxxHeadResponseDTo head;

    public JsyhFwqsxxBodyResponseDTo getBody() {
        return body;
    }

    public void setBody(JsyhFwqsxxBodyResponseDTo body) {
        this.body = body;
    }

    public JsyhFwqsxxHeadResponseDTo getHead() {
        return head;
    }

    public void setHead(JsyhFwqsxxHeadResponseDTo head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "JsyhFwqsxxResponseDTo{" +
                "body=" + body +
                ", head=" + head +
                '}';
    }
}
