package cn.gtmap.realestate.common.core.dto.exchange.changzhou.ydjxt;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2019/12/30,1.0
 * @description
 */
public class DvHlwQlrResponseDto {

    /**
     * 权利人名称
     */
    private String qlrmc;
    /**
     * 权利人证件类型
     */
    private String zjlx;
    /**
     * 权利人证件号
     */
    private String qlrzjh;
    /**
     * 共有方式
     */
    private String gyfs;
    /**
     * 共有比例
     */
    private String gybl;
    /**
     * 产权证号
     */
    private String cqzh;

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGybl() {
        return gybl;
    }

    public void setGybl(String gybl) {
        this.gybl = gybl;
    }

    @Override
    public String toString() {
        return "DvHlwQlrResponseDto{" +
                "qlrmc='" + qlrmc + '\'' +
                ", zjlx='" + zjlx + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", gyfs='" + gyfs + '\'' +
                ", gybl='" + gybl + '\'' +
                '}';
    }
}
