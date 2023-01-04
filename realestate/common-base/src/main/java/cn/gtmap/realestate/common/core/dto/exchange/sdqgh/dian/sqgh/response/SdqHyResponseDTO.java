package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response;


/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/8/12
 * @description 水电气过户前核验结果
 */
public class SdqHyResponseDTO {

    private String code;

    private String msg;

    private SdqHyDataDTO data;

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

    public SdqHyDataDTO getData() {
        return data;
    }

    public void setData(SdqHyDataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SdqHyResponseDTO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
