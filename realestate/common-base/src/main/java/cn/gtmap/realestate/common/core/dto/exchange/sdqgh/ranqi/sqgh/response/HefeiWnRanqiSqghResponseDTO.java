package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response;

import cn.gtmap.realestate.common.core.enums.BdcSdqWnRqStatusEnum;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description
 */
public class HefeiWnRanqiSqghResponseDTO {

    // 响应码
    private String echoCode;

    // 响应描述
    private String echoDes;

    /**
     * 返回提交结果,
     * 皖能需要等待带三方回调,所以不返回true,返回success,防止更新状态
     *
     * @return
     */
    public String getResult() {
        if (BdcSdqWnRqStatusEnum.NOUSER.code().equals(echoCode)
                || BdcSdqWnRqStatusEnum.ERROR.code().equals(echoCode)) {
            return "false";
        } else {
            return "success";
        }
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
