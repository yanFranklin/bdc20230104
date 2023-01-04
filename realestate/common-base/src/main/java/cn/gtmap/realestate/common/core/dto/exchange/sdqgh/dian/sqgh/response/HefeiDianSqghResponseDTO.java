package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-19
 * @description 合肥 电 申请过户接口响应参数实体
 */
public class HefeiDianSqghResponseDTO {

    private String timeStamp;

    private String resultCode;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
