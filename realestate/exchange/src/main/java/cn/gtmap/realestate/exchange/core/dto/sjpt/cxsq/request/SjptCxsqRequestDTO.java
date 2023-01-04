package cn.gtmap.realestate.exchange.core.dto.sjpt.cxsq.request;

import cn.gtmap.realestate.exchange.core.dto.sjpt.SjptCxRequestHead;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-21
 * @description 查询申请 下发接口 请求参数
 */
public class SjptCxsqRequestDTO {

    private SjptCxRequestHead head;

    private Map data;

    public SjptCxRequestHead getHead() {
        return head;
    }

    public void setHead(SjptCxRequestHead head) {
        this.head = head;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }
}
