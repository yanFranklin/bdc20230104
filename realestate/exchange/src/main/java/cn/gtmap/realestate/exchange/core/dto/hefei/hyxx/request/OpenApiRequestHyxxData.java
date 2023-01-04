package cn.gtmap.realestate.exchange.core.dto.hefei.hyxx.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-06
 * @description 婚姻信息 请求参数
 */
public class OpenApiRequestHyxxData {

    private String name;

    private String card_no;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }
}
