package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.gasfhc.request;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/1/27
 * @description 公安身份核查查询业务参数
 */
public class GaSfhcCxywcsDTO {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 姓名
     */
    private String xm;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 证件号
     */
    private String zjh;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }
}
