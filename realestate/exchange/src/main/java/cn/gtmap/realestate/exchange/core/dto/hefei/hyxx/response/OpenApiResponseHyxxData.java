package cn.gtmap.realestate.exchange.core.dto.hefei.hyxx.response;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019-7-4
 * @description 民政返回数据实体
 */
public class OpenApiResponseHyxxData {
    /**
    * 身份证件号码
    */
    private String card_no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 婚姻状态
     */
    private String marital_status;
    /**
     * 配偶身份证件号码
     */
    private String s_card_no;
    /**
     * 配偶姓名
     */
    private String s_name;
    /**
     * 结婚登记时间
     */
    private String jhdjrq;
    /**
     * 原配偶身份证件号码
     */
    private String q_card_no;
    /**
     * 原配偶姓名
     */
    private String q_name;
    /**
     * 离婚登记时间
     */
    private String lhdjrq;

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getS_card_no() {
        return s_card_no;
    }

    public void setS_card_no(String s_card_no) {
        this.s_card_no = s_card_no;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getJhdjrq() {
        return jhdjrq;
    }

    public void setJhdjrq(String jhdjrq) {
        this.jhdjrq = jhdjrq;
    }

    public String getQ_card_no() {
        return q_card_no;
    }

    public void setQ_card_no(String q_card_no) {
        this.q_card_no = q_card_no;
    }

    public String getQ_name() {
        return q_name;
    }

    public void setQ_name(String q_name) {
        this.q_name = q_name;
    }

    public String getLhdjrq() {
        return lhdjrq;
    }

    public void setLhdjrq(String lhdjrq) {
        this.lhdjrq = lhdjrq;
    }
}
