package cn.gtmap.realestate.exchange.core.dto.hefei.sdkApiHyxx.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-09
 * @description
 */
public class SdkApiHyxxResponseDTO {

    private String spouseName;

    private String success;

    private String stateCode;

    private String date;

    private String spouseCardId;

    private String errorMsg;

    // 婚姻状态
    private String hyzt;

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getHyzt() {
        return hyzt;
    }

    public void setHyzt(String hyzt) {
        this.hyzt = hyzt;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSpouseCardId() {
        return spouseCardId;
    }

    public void setSpouseCardId(String spouseCardId) {
        this.spouseCardId = spouseCardId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
