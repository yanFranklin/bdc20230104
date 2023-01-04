package cn.gtmap.realestate.exchange.core.dto.hefei.fw;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-10-19
 * @description 法院查档请求参数
 */
public class FycdRequest {
    /**
     * 权利人名称 必填
     */
    private String qlrmc;
    /**
     * 权利人证件号 必填
     */
    private String qlrzjh;

    /**
     * 项目主键
     */
    private String xmid;

    /**
     * 行政区代码
     */
    private String xzqdm;



    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }


    @Override
    public String toString() {
        return "FycdRequest{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", xzqdm='" + xzqdm + '\'' +
                '}';
    }
}
