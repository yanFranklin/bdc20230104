package cn.gtmap.realestate.exchange.core.dto.yancheng.court;
/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class SdJsBaseResponseDTO {

    private String msg;

    private String success;

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

    @Override
    public String toString() {
        return "SdJsBaseResponseDTO{" +
                "msg='" + msg + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
