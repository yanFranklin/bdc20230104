package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response;

import lombok.Data;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description 燃气 接口 通用返回参数
 */
@Data
public class RanqiSqghCommonResponseDTO {

    // 用户户号
    private String userhuhao;

    // 用户户号
    private String yhh;

    private String slbh;

    private String xmid;

    private String qxdm;

    private String rqfwbsm;

    private String responseContent;

    public String getUserhuhao() {
        return userhuhao;
    }

    public void setUserhuhao(String userhuhao) {
        this.userhuhao = userhuhao;
    }

    public String getYhh() {
        return yhh;
    }

    public void setYhh(String yhh) {
        this.yhh = yhh;
    }

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

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getRqfwbsm() {
        return rqfwbsm;
    }

    public void setRqfwbsm(String rqfwbsm) {
        this.rqfwbsm = rqfwbsm;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
}
