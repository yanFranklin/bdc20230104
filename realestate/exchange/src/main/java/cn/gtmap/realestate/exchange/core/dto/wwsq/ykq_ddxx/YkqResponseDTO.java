package cn.gtmap.realestate.exchange.core.dto.wwsq.ykq_ddxx;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-01-16
 * @description 响应结果
 */
public class YkqResponseDTO {

    private String msg;

    private String success;

    public YkqResponseDTO() {
        this.msg = "成功";
        this.success = "true";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
