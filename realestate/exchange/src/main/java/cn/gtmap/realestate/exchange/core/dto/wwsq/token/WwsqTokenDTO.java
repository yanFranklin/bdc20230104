package cn.gtmap.realestate.exchange.core.dto.wwsq.token;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/9/1
 * @description 外网申请token返回DTO
 */
public class WwsqTokenDTO {

    /**
     * data : {"time":"2022-08-28 15:44:41","token":"38716a85b4af3f751f04e4c3ea6cc9e1"}
     * head : {"access_token":"","code":"0000","msg":"成功","orgid":"","regionCode":"","sign":"C5DC4CAD31EB161215A362981B38F7BF","statusCode":"0000"}
     */

    private WwsqTokenData data;
    private WwsqTokenHead head;

    public WwsqTokenData getData() {
        return data;
    }

    public void setData(WwsqTokenData data) {
        this.data = data;
    }

    public WwsqTokenHead getHead() {
        return head;
    }

    public void setHead(WwsqTokenHead head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "WwsqTokenDTO{" +
                "data=" + data +
                ", head=" + head +
                '}';
    }
}
