package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.qxzf;
/*
 * @author <a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 * @version 1.0, 2018/11/28
 * @description 税务 取消作废 请求头信息
 */
public class QxZfSwxxRequestMode {
    private  QxZfSwxxRequestBody  body;
    private  QxZfSwxxRequestHead head;

    public QxZfSwxxRequestBody getBody() {
        return body;
    }

    public void setBody(QxZfSwxxRequestBody body) {
        this.body = body;
    }

    public QxZfSwxxRequestHead getHead() {
        return head;
    }

    public void setHead(QxZfSwxxRequestHead head) {
        this.head = head;
    }
}
