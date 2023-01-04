package cn.gtmap.realestate.exchange.core.dto.wwsq.jssfsszt.request;

import cn.gtmap.realestate.exchange.core.dto.wwsq.sfssxx.response.BdcSfSsxxResponseDTO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-10
 * @description 接收外网缴费状态接口请求参数
 */
public class BdcJsSfssztRequestDTO extends BdcSfSsxxResponseDTO {

    // 缴费状态 0为未缴费 1为缴费
    private String jfzt;

    /**
     * DK：德宽，空：银行
     */
    private String jfType;


    @Override
    public String getJfzt() {
        return jfzt;
    }

    @Override
    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }

    public String getJfType() {
        return jfType;
    }

    public void setJfType(String jfType) {
        this.jfType = jfType;
    }
}
