package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/17
 * @description 不动产单元权利状态DTO
 */
@ApiModel(value = "BdcdyZtDTO", description = "不动产单元状态实体")
public class BdcdyZtDTO {
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    /**
     * 登记状态
     */
    @ApiModelProperty(value = "登记状态")
    private Boolean dj;

    /**
     * 注销状态
     */
    @ApiModelProperty(value = "注销状态")
    private Boolean zx;

    /**
     * 现势在建工程抵押
     */
    @ApiModelProperty(value = "现势在建工程抵押")
    private Boolean zjgcdy;
    /**
     * 现势预告
     */
    @ApiModelProperty(value = "现势预告")
    private Boolean yg;
    /**
     * 现势预抵押
     */
    @ApiModelProperty(value = "现势预抵押")
    private Boolean ydya;

    /**
     * 现势抵押
     */
    @ApiModelProperty(value = "现势抵押")
    private Boolean dya;
    /**
     * 现势预查封
     */
    @ApiModelProperty(value = "现势预查封")
    private Boolean ycf;
    /**
     * 现势查封
     */
    @ApiModelProperty(value = "现势查封")
    private Boolean cf;
    /**
     * 现势异议
     */
    @ApiModelProperty(value = "现势异议")
    private Boolean yy;
    /**
     * 现势地役
     */
    @ApiModelProperty(value = "现势地役")
    private Boolean dyi;

    @ApiModelProperty(value = "现势锁定")
    private Boolean sd;

    @ApiModelProperty(value = "备案状态")
    private Boolean ba;

    @ApiModelProperty(value = "现势居住权")
    private Boolean jzq;

    public Boolean getBa() {
        return ba;
    }

    public void setBa(Boolean ba) {
        this.ba = ba;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Boolean getDj() {
        return dj;
    }

    public void setDj(Boolean dj) {
        this.dj = dj;
    }

    public Boolean getZx() {
        return zx;
    }

    public void setZx(Boolean zx) {
        this.zx = zx;
    }

    public Boolean getZjgcdy() {
        return zjgcdy;
    }

    public void setZjgcdy(Boolean zjgcdy) {
        this.zjgcdy = zjgcdy;
    }

    public Boolean getYg() {
        return yg;
    }

    public void setYg(Boolean yg) {
        this.yg = yg;
    }

    public Boolean getYdya() {
        return ydya;
    }

    public void setYdya(Boolean ydya) {
        this.ydya = ydya;
    }

    public Boolean getDya() {
        return dya;
    }

    public void setDya(Boolean dya) {
        this.dya = dya;
    }

    public Boolean getYcf() {
        return ycf;
    }

    public void setYcf(Boolean ycf) {
        this.ycf = ycf;
    }

    public Boolean getCf() {
        return cf;
    }

    public void setCf(Boolean cf) {
        this.cf = cf;
    }

    public Boolean getYy() {
        return yy;
    }

    public void setYy(Boolean yy) {
        this.yy = yy;
    }

    public Boolean getDyi() {
        return dyi;
    }

    public void setDyi(Boolean dyi) {
        this.dyi = dyi;
    }

    public Boolean getSd() {
        return sd;
    }

    public void setSd(Boolean sd) {
        this.sd = sd;
    }

    public Boolean getJzq() {
        return jzq;
    }

    public void setJzq(Boolean jzq) {
        this.jzq = jzq;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcdyZtDTO{");
        sb.append("bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", dj=").append(dj);
        sb.append(", zx=").append(zx);
        sb.append(", zjgcdy=").append(zjgcdy);
        sb.append(", yg=").append(yg);
        sb.append(", ydya=").append(ydya);
        sb.append(", dya=").append(dya);
        sb.append(", ycf=").append(ycf);
        sb.append(", cf=").append(cf);
        sb.append(", yy=").append(yy);
        sb.append(", dyi=").append(dyi);
        sb.append(", sd=").append(sd);
        sb.append(", ba=").append(ba);
        sb.append(", jzq=").append(jzq);
        sb.append('}');
        return sb.toString();
    }
}
