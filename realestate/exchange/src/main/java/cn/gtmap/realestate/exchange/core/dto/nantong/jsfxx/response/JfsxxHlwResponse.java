package cn.gtmap.realestate.exchange.core.dto.nantong.jsfxx.response;

import cn.gtmap.realestate.exchange.core.dto.wwsq.ResponseHead;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/8/5
 * @description 缴费书信息接口返回给互联网实体结构
 */
public class JfsxxHlwResponse {

    private ResponseHead head ;

    private JfsxxResponseData data;

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public JfsxxResponseData getData() {
        return data;
    }

    public void setData(JfsxxResponseData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JfsxxHlwResponse{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
