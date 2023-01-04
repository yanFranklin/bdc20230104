package cn.gtmap.realestate.common.core.dto.register;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2019/1/16
 * @description 不动产登记簿权利目录
 */
public class BdcDjbQlDTO {
    /**
     * 房屋产权
     */
    private String fwCq;
    /**
     * 房屋权利类型
     */
    private Integer fwQllx;
    /**
     * 土地、林权、海域等非房屋产权
     */
    private String qtCq;
    /**
     * 土地、林权、海域等非房屋产权权利类型
     */
    private Integer qtCqQllx;
    /**
     * 预告
     */
    private String yg;
    /**
     * 异议
     */
    private String yy;
    /**
     * 抵押
     */
    private String dya;
    /**
     * 地役
     */
    private String dyi;
    /**
     * 查封
     */
    private String cf;
    /**
     * 是否房地产权多幢（仅用于房屋产权存在时）
     */
    private String sffdcqdz;
    /**
     * 是否有业主共有信息
     */
    private Boolean yzgy;
    /**
     * 经营权
     */
    private String jyq;
    /**
     * 居住权
     */
    private String jzq;

    public String getSffdcqdz() {
        return sffdcqdz;
    }

    public String getFwCq() {
        return fwCq;
    }

    public void setFwCq(String fwCq) {
        this.fwCq = fwCq;
    }

    public Integer getFwQllx() {
        return fwQllx;
    }

    public void setFwQllx(Integer fwQllx) {
        this.fwQllx = fwQllx;
    }

    public String getQtCq() {
        return qtCq;
    }

    public void setQtCq(String qtCq) {
        this.qtCq = qtCq;
    }

    public Boolean getYzgy() {
        return yzgy;
    }

    public void setYzgy(Boolean yzgy) {
        this.yzgy = yzgy;
    }

    public Integer getQtCqQllx() {
        return qtCqQllx;
    }

    public void setQtCqQllx(Integer qtCqQllx) {
        this.qtCqQllx = qtCqQllx;
    }

    public void setSffdcqdz(String sffdcqdz) {
        this.sffdcqdz = sffdcqdz;
    }

    public String getYg() {
        return yg;
    }

    public void setYg(String yg) {
        this.yg = yg;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public String getDya() {
        return dya;
    }

    public void setDya(String dya) {
        this.dya = dya;
    }

    public String getDyi() {
        return dyi;
    }

    public void setDyi(String dyi) {
        this.dyi = dyi;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getJyq() {
        return jyq;
    }

    public void setJyq(String jyq) {
        this.jyq = jyq;
    }

    public String getJzq() {
        return jzq;
    }

    public void setJzq(String jzq) {
        this.jzq = jzq;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcDjbQlDTO{");
        sb.append("fwCq='").append(fwCq).append('\'');
        sb.append(", fwQllx=").append(fwQllx);
        sb.append(", qtCq='").append(qtCq).append('\'');
        sb.append(", qtCqQllx=").append(qtCqQllx);
        sb.append(", yg='").append(yg).append('\'');
        sb.append(", yy='").append(yy).append('\'');
        sb.append(", dya='").append(dya).append('\'');
        sb.append(", dyi='").append(dyi).append('\'');
        sb.append(", cf='").append(cf).append('\'');
        sb.append(", sffdcqdz='").append(sffdcqdz).append('\'');
        sb.append(", yzgy=").append(yzgy);
        sb.append(", jyq='").append(jyq).append('\'');
        sb.append(", jzq='").append(jzq).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
