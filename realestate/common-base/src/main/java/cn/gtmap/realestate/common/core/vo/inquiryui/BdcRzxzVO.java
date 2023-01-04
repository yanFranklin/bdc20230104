package cn.gtmap.realestate.common.core.vo.inquiryui;
/**
 * @program: realestate
 * @description: 日志下载功能保存选择的主机与服务名称
 * @author: <a href="mailto:hongqin@gtmap.cn">gaolining</a>
 * @create: 2022-09-02 16:26
 **/
public class BdcRzxzVO {
    /**
     * 选择的应用名称如：合肥登记中心
     */
    private String apply;
    /**
     * 具体的应用服务如：inquiry-ui
     */
    private String uiapply;
    /**
     * 开始时间
     */
    private String start_time;
    /**
     * 结束时间
     */
    private String end_time;


    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getUiapply() {
        return uiapply;
    }

    public void setUiapply(String uiapply) {
        this.uiapply = uiapply;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
