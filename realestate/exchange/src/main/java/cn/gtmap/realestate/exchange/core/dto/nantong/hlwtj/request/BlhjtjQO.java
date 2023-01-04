package cn.gtmap.realestate.exchange.core.dto.nantong.hlwtj.request;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/5/18
 * @description 公积金查询对象——南通
 */
public class BlhjtjQO {

    private BlhjtjRequestHead head;
    private BlhjtjRequestData data;

    public BlhjtjRequestHead getHead() {
        return head;
    }

    public void setHead(BlhjtjRequestHead head) {
        this.head = head;
    }

    public BlhjtjRequestData getData() {
        return data;
    }

    public void setData(BlhjtjRequestData data) {
        this.data = data;
    }
}
