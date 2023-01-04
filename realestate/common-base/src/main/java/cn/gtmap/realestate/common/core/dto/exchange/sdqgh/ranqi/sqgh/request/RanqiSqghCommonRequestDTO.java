package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request;

import lombok.Data;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description 燃气 申请过户 接口 通用请求参数
 */
@Data
public class RanqiSqghCommonRequestDTO {

    private String slbh;

    private String xmid;

    private String qjgldm;

    private String rqfwbsm;

    private String requestContent;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getRqfwbsm() {
        return rqfwbsm;
    }

    public void setRqfwbsm(String rqfwbsm) {
        this.rqfwbsm = rqfwbsm;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }
}
