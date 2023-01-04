package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjts.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-19
 * @description 合肥 电  附件推送请求参数实体
 */
public class HefeiDianFjtsRequestDTO {

    // 工单编号，与过户申请接口的工单编号保持一致
    private String appNo;

    // 附件类型
    //001:身份证
    //002:房产证（合同、登记簿等）
    private String attachType;

    // 附件内容,以base64编码
    private String attachFile;

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }
}
