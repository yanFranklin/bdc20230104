package cn.gtmap.realestate.exchange.core.dto.wwsq.querySpfHtbaxx.response;

/**
 * @description 商品房合同备案信息权利人信息
 * @author:  <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date: 2022/10/26 15:59
 **/
public class SpfHtbaxxQlrxxDTO {

    /**
     * 权利人名称
     */
    private String qlrmc;

    /**
     * 权利人证件号
     */
    private String qlrzjh;

    /**
     * 权利人联系电话
     */
    private String qlrlxdh;

    /**
     * 权利人证件种类
     */
    private String qlrzjzl;

    /**
     * 权利人类型
     */
    private String qlrlx;

    /**
     * 共有人
     */
    private String gyrbj;

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

    public String getQlrlxdh() {
        return qlrlxdh;
    }

    public void setQlrlxdh(String qlrlxdh) {
        this.qlrlxdh = qlrlxdh;
    }

    public String getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(String qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getGyrbj() {
        return gyrbj;
    }

    public void setGyrbj(String gyrbj) {
        this.gyrbj = gyrbj;
    }

    @Override
    public String toString() {
        return "SpfHtbaxxQlrxxDTO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrlxdh='" + qlrlxdh + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", gyrbj='" + gyrbj + '\'' +
                '}';
    }
}
