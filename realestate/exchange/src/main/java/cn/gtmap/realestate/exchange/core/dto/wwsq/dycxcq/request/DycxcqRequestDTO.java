package cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.request;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
 * @version 1.0  2022-05-31
 * @description
 */
@IgnoreCast
public class DycxcqRequestDTO {

    /**
     * 抵押证明号
     */
    private String dyzmh;

    /**
     * 抵押权人
     */
    private String dyqr;

    /**
     * 抵押证明号模糊类型
     */
    private String dyzmhmhlx;

    /**
     * 抵押权人模糊类型
     */
    private String dyqrmhlx;

    public String getDyzmh() { return dyzmh; }

    public void setDyzmh(String dyzmh) { this.dyzmh = dyzmh; }

    public String getDyqr() { return dyqr; }

    public void setDyqr(String dyqr) { this.dyqr = dyqr; }

    public String getDyzmhmhlx() { return dyzmhmhlx; }

    public void setDyzmhmhlx(String dyzmhmhlx) { this.dyzmhmhlx = dyzmhmhlx; }

    public String getDyqrmhlx() { return dyqrmhlx; }

    public void setDyqrmhlx(String dyqrmhlx) { this.dyqrmhlx = dyqrmhlx; }

    @Override
    public String toString() {
        return "DycxcqRequestDTO{" +
                "dyzmh='" + dyzmh + '\'' +
                ", dyqr='" + dyqr + '\'' +
                ", dyzmhmhlx='" + dyzmhmhlx + '\'' +
                ", dyqrmhlx='" + dyqrmhlx + '\'' +
                '}';
    }
}
