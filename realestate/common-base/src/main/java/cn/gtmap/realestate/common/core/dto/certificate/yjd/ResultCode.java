package cn.gtmap.realestate.common.core.dto.certificate.yjd;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description  交接单操作结果状态码实体
 */
public class ResultCode {
    /**
     * 状态码：0 成功  1 未定义要操作的数据或数据格式不对  2 登记服务器内部错误
     */
    private int code;

    /**
     * 具体信息内容
     */
    private String msg;

    public ResultCode() {

    }

    public ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
