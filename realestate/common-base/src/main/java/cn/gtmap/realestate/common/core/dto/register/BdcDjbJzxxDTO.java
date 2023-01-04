package cn.gtmap.realestate.common.core.dto.register;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu/a>"
 * @version 3.0, 2022/3/14
 * @description 常州自助查询机查询登记簿结果-自然状况-居住权信息
 */
public class BdcDjbJzxxDTO {
    /**
     * 不动产证明号
     */
    private String bdczmh;

    /**
     * 居住权人
     */
    private String jzr;

    /**
     * 居住权期限
     */
    private String jzqx;

    /**
     * 总层数
     */
    private String zcs;

    /**
     * 房屋结构
     */
    private String fwjg;

    /**
     * 建筑面积
     */
    private String jzmj;

    public String getBdczmh() {
        return bdczmh;
    }

    public void setBdczmh(String bdczmh) {
        this.bdczmh = bdczmh;
    }

    public String getJzr() {
        return jzr;
    }

    public void setJzr(String jzr) {
        this.jzr = jzr;
    }

    public String getJzqx() {
        return jzqx;
    }

    public void setJzqx(String jzqx) {
        this.jzqx = jzqx;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    @Override
    public String toString() {
        return "BdcDjbJzxxDTO{" +
                "bdczmh='" + bdczmh + '\'' +
                ", jzr='" + jzr + '\'' +
                ", jzqx='" + jzqx + '\'' +
                ", zcs='" + zcs + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", jzmj='" + jzmj + '\'' +
                '}';
    }
}
