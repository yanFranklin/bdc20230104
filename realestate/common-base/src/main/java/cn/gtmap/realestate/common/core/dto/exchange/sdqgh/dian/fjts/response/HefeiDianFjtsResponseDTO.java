package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjts.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-19
 * @description
 */
public class HefeiDianFjtsResponseDTO {

    private String timeStamp;

    // 0000->保存成功;
    //0001->保存失败，系统异常;
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
