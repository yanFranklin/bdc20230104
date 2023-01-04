package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response;

import lombok.Data;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description 合肥皖能燃气 申请过户结果更新返回
 */
@Data
public class HefeiWnRanqiGhJgResponseDTO {
    /**
     * 业务号(slbh)
     */
    private String ywh;

    /**
     * 用户号
     */
    private String yhh;

    /**
     * 响应码
     */
    private String echoCode;

    /**
     * 响应描述
     */
    private String echoDes;

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getYhh() {
        return yhh;
    }

    public void setYhh(String yhh) {
        this.yhh = yhh;
    }

    public String getEchoCode() {
        return echoCode;
    }

    public void setEchoCode(String echoCode) {
        this.echoCode = echoCode;
    }

    public String getEchoDes() {
        return echoDes;
    }

    public void setEchoDes(String echoDes) {
        this.echoDes = echoDes;
    }
}
