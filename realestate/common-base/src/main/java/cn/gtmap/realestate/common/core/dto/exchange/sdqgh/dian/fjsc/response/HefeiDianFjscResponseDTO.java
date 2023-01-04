package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjsc.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-23
 * @description 合肥 附件删除接口 返回值
 */
public class HefeiDianFjscResponseDTO {

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
