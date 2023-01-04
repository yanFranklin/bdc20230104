package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-11-13
 * @description 修改不动产单元状态的DTO
 */
@ApiModel(value = "BdcdyhZtRequestDTO", description = "不动产单元状态返回实体")
public class BdcdyhZtRequestDTO {
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    @Id
    private String bdcdyh;


    @ApiModelProperty(value = "登记状态")
    private Integer djzt;
    /**
     * 在建工程抵押
     */
    @ApiModelProperty(value = "在建工程抵押")
    private Integer zjgcdy;
    /**
     * 预告
     */
    @ApiModelProperty(value = "预告")
    private Integer yg;
    /**
     * 预抵押
     */
    @ApiModelProperty(value = "预抵押")
    private Integer ydya;
    /**
     * 抵押
     */
    @ApiModelProperty(value = "抵押")
    private Integer dya;
    /**
     * 预查封
     */
    @ApiModelProperty(value = "预查封")
    private Integer ycf;
    /**
     * 查封
     */
    @ApiModelProperty(value = "查封")
    private Integer cf;
    /**
     * 异议
     */
    @ApiModelProperty(value = "异议")
    private Integer yy;
    /**
     * 地役
     */
    @ApiModelProperty(value = "地役")
    private Integer dyi;
    /**
     * 锁定
     */
    @ApiModelProperty(value = "锁定")
    private Integer sd;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Integer getZjgcdy() {
        return zjgcdy;
    }

    public void setZjgcdy(Integer zjgcdy) {
        this.zjgcdy = zjgcdy;
    }

    public Integer getYg() {
        return yg;
    }

    public void setYg(Integer yg) {
        this.yg = yg;
    }

    public Integer getYdya() {
        return ydya;
    }

    public void setYdya(Integer ydya) {
        this.ydya = ydya;
    }

    public Integer getDya() {
        return dya;
    }

    public void setDya(Integer dya) {
        this.dya = dya;
    }

    public Integer getYcf() {
        return ycf;
    }

    public void setYcf(Integer ycf) {
        this.ycf = ycf;
    }

    public Integer getCf() {
        return cf;
    }

    public void setCf(Integer cf) {
        this.cf = cf;
    }

    public Integer getYy() {
        return yy;
    }

    public void setYy(Integer yy) {
        this.yy = yy;
    }

    public Integer getDyi() {
        return dyi;
    }

    public void setDyi(Integer dyi) {
        this.dyi = dyi;
    }

    public Integer getSd() {
        return sd;
    }

    public void setSd(Integer sd) {
        this.sd = sd;
    }

    public Integer getDjzt() {
        return djzt;
    }

    public void setDjzt(Integer djzt) {
        this.djzt = djzt;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcdyhZtRequestDTO{");
        sb.append("bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", djzt=").append(djzt);
        sb.append(", zjgcdy=").append(zjgcdy);
        sb.append(", yg=").append(yg);
        sb.append(", ydya=").append(ydya);
        sb.append(", dya=").append(dya);
        sb.append(", ycf=").append(ycf);
        sb.append(", cf=").append(cf);
        sb.append(", yy=").append(yy);
        sb.append(", dyi=").append(dyi);
        sb.append(", sd=").append(sd);
        sb.append('}');
        return sb.toString();
    }
}
