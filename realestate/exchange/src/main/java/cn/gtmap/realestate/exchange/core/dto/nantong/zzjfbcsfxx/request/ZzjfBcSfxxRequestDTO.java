package cn.gtmap.realestate.exchange.core.dto.nantong.zzjfbcsfxx.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-01
 * @description 自助缴费机  保存收费信息
 */
public class ZzjfBcSfxxRequestDTO {

    private ZzjfBcSfxxRequestHead head;

    private ZzjfBcSfxxRequestData data;

    public ZzjfBcSfxxRequestHead getHead() {
        return head;
    }

    public void setHead(ZzjfBcSfxxRequestHead head) {
        this.head = head;
    }

    public ZzjfBcSfxxRequestData getData() {
        return data;
    }

    public void setData(ZzjfBcSfxxRequestData data) {
        this.data = data;
    }
}
