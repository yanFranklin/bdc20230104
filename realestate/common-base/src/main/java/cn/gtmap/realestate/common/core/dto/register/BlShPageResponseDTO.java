package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/10/11
 * @description 分页查询补录审核信息页面的返回对象
 */
@ApiModel(value = "BlShPageResponseDTO",description = "分页查询补录审核信息页面的返回对象")
public class BlShPageResponseDTO {
    @ApiModelProperty(value = "补录审核id")
    private String blshid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;
    @ApiModelProperty(value = "顺序号")
    private String sxh;
    @ApiModelProperty(value = "受理人ID")
    private String slrid;
    @ApiModelProperty(value = "审核人员id")
    private String shryid;
    @ApiModelProperty(value = "审核人员姓名")
    private String shryxm;
    @ApiModelProperty(value = "补录审核类型")
    private String blshlx;
    @ApiModelProperty(value = "补录类型")
    private String bllx;
    @ApiModelProperty(value = "补录状态")
    private String blzt;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "权利人")
    private String qlr;
    @ApiModelProperty(value = "流程名称")
    private String gzldymc;
    @Override
    public String toString() {
        return "BlShPageResponseDTO{" +
                "blshid='" + blshid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", shryid='" + shryid + '\'' +
                ", shryxm='" + shryxm + '\'' +
                ", blshlx='" + blshlx + '\'' +
                ", bllx='" + bllx + '\'' +
                ", blzt='" + blzt + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", qlr='" + qlr + '\'' +
                ", slrid='" + slrid + '\'' +
                ", sxh='" + sxh + '\'' +
                ", gzldymc='" + gzldymc + '\'' +
                '}';
    }

    public String getSlrid() {
        return slrid;
    }

    public void setSlrid(String slrid) {
        this.slrid = slrid;
    }

    public String getSxh() {
        return sxh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getBlshid() {
        return blshid;
    }

    public void setBlshid(String blshid) {
        this.blshid = blshid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getShryid() {
        return shryid;
    }

    public void setShryid(String shryid) {
        this.shryid = shryid;
    }

    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    public String getBlshlx() {
        return blshlx;
    }

    public void setBlshlx(String blshlx) {
        this.blshlx = blshlx;
    }

    public String getBllx() {
        return bllx;
    }

    public void setBllx(String bllx) {
        this.bllx = bllx;
    }

    public String getBlzt() {
        return blzt;
    }

    public void setBlzt(String blzt) {
        this.blzt = blzt;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }
}
