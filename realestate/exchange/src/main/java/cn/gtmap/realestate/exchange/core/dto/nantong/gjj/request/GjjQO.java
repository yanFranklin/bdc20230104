package cn.gtmap.realestate.exchange.core.dto.nantong.gjj.request;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/5/18
 * @description 公积金查询对象——南通
 */
public class GjjQO {


    /**
     * head : {"appid":"","transcode":"","trantime":"","iseqno":""}
     * data : {"cqzh":"产权证号","xm":"产权人姓名","zjhm":"证件号码"}
     */

    private GjjRequestHead head;
    private GjjRequestData data;

    public GjjRequestHead getHead() {
        return head;
    }

    public void setHead(GjjRequestHead head) {
        this.head = head;
    }

    public GjjRequestData getData() {
        return data;
    }

    public void setData(GjjRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GjjQO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
