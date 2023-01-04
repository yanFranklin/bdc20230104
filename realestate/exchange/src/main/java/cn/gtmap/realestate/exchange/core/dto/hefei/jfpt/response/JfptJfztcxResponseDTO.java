package cn.gtmap.realestate.exchange.core.dto.hefei.jfpt.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-13
 * @description
 */
public class JfptJfztcxResponseDTO {

    private String msg;

    private JfptJfztcxResponseData data;

    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JfptJfztcxResponseData getData() {
        return data;
    }

    public void setData(JfptJfztcxResponseData data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
