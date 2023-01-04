package cn.gtmap.realestate.exchange.core.dto.wwsq.jfxx;

import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSfxxDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.updateslzt.UpdateSlztRequestHead;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/16
 * @description 推送收费信息 请求体
 */
public class TsjfxxRequestBody {
    private UpdateSlztRequestHead head;
    private BdcDsfSfxxDTO data;

    public UpdateSlztRequestHead getHead() {
        return head;
    }

    public void setHead(UpdateSlztRequestHead head) {
        this.head = head;
    }

    public BdcDsfSfxxDTO getData() {
        return data;
    }

    public void setData(BdcDsfSfxxDTO data) {
        this.data = data;
    }
}
