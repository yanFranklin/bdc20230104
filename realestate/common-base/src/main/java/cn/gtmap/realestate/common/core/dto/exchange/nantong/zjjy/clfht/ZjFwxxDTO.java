package cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.clfht;

import io.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/17
 * @description 住建房屋信息DTO
 */
@ApiModel(value = "ZjFwxxDTO", description = "住建房屋信息DTO")
public class ZjFwxxDTO {


    /**
     * FH : 101
     * FWYZ : 住宅
     * FWMJ : 113.22
     * XZZT : 0
     * DYZT : 0
     */

    private String FH;
    private String FWYZ;
    private String FWMJ;
    private String XZZT;
    private String DYZT;

    public String getFH() {
        return FH;
    }

    public void setFH(String FH) {
        this.FH = FH;
    }

    public String getFWYZ() {
        return FWYZ;
    }

    public void setFWYZ(String FWYZ) {
        this.FWYZ = FWYZ;
    }

    public String getFWMJ() {
        return FWMJ;
    }

    public void setFWMJ(String FWMJ) {
        this.FWMJ = FWMJ;
    }

    public String getXZZT() {
        return XZZT;
    }

    public void setXZZT(String XZZT) {
        this.XZZT = XZZT;
    }

    public String getDYZT() {
        return DYZT;
    }

    public void setDYZT(String DYZT) {
        this.DYZT = DYZT;
    }

    @Override
    public String toString() {
        return "ZjFwxxDTO{" +
                "FH='" + FH + '\'' +
                ", FWYZ='" + FWYZ + '\'' +
                ", FWMJ='" + FWMJ + '\'' +
                ", XZZT='" + XZZT + '\'' +
                ", DYZT='" + DYZT + '\'' +
                '}';
    }
}
