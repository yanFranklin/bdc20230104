package cn.gtmap.realestate.exchange.core.dto.hefei.jfpt.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description 缴费平台 推送代缴费信息 返回值
 */
public class JfptTsDjfxxResponseDTO {

    private String msg;

    private JfptTsDjfxxResponseData data;

    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JfptTsDjfxxResponseData getData() {
        return data;
    }

    public void setData(JfptTsDjfxxResponseData data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
