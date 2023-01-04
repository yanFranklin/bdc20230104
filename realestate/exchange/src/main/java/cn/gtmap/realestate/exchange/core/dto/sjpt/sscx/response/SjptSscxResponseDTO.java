package cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.response;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2019/1/10
 * @description
 */

import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.SjptCxjgRequestData;

import java.util.List;

public class SjptSscxResponseDTO {
    private SjptSscxResponseHead head;
    private List<SjptCxjgRequestData> data;

    public SjptSscxResponseHead getHead() {
        return head;
    }

    public void setHead(SjptSscxResponseHead head) {
        this.head = head;
    }

    public List<SjptCxjgRequestData> getData() {
        return data;
    }

    public void setData(List<SjptCxjgRequestData> data) {
        this.data = data;
    }
}
