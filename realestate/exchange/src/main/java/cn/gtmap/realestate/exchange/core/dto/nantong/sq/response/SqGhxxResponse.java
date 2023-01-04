package cn.gtmap.realestate.exchange.core.dto.nantong.sq.response;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/12/30  10:13
 * @description 南通水气返回值
 */
public class SqGhxxResponse {
    private String code;
    private String msg;
    private SqGhxxDataDTO data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SqGhxxDataDTO getData() {
        return data;
    }

    public void setData(SqGhxxDataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SqGhxxResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
