package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/10.
 * @description 不动产受理银行月结缴费DTO
 */
@ApiModel(value = "BdcYjSfxxDTO", description = "不动产银行月结收费信息DTO")
public class BdcYjSfxxDTO {

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "收费信息ID")
    private String sfxxid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "银行名称")
    private String yhmc;
    @ApiModelProperty(value = "不动产产权证明")
    private String bdcqzh;
    @ApiModelProperty(value = "义务人")
    private String ywr;
    @ApiModelProperty(value = "义务人证件号，多个采用逗号拼接")
    private String ywrzjh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "单价")
    private String dj;
    @ApiModelProperty(value = "用途")
    private String yt;
    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;
    @ApiModelProperty(value = "收费项目数量")
    private String sfxmsl;
    @ApiModelProperty(value = "收费项目合计")
    private Double sfxmhj;
    @ApiModelProperty(value = "缴费书编码")
    private String jfsewmurl;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "收费状态")
    private Integer sfzt;
    @ApiModelProperty(value = "月结单号")
    private String yjdh;
    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;
    @ApiModelProperty(value = "交易合同号")
    private String jyhth;
    @ApiModelProperty(value = "收费项目总数量")
    private Integer sfxmzsl;
    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;
    @ApiModelProperty(value = "流程名称")
    private String lcmc;

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }

    public String getSfxmsl() {
        return sfxmsl;
    }

    public void setSfxmsl(String sfxmsl) {
        this.sfxmsl = sfxmsl;
    }

    public Double getSfxmhj() {
        return sfxmhj;
    }

    public void setSfxmhj(Double sfxmhj) {
        this.sfxmhj = sfxmhj;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getJfsewmurl() {
        return jfsewmurl;
    }

    public void setJfsewmurl(String jfsewmurl) {
        this.jfsewmurl = jfsewmurl;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Integer getSfzt() {
        return sfzt;
    }

    public void setSfzt(Integer sfzt) {
        this.sfzt = sfzt;
    }

    public String getYjdh() {
        return yjdh;
    }

    public void setYjdh(String yjdh) {
        this.yjdh = yjdh;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getJyhth() {
        return jyhth;
    }

    public void setJyhth(String jyhth) {
        this.jyhth = jyhth;
    }

    public BdcYjSfxxDTO withXmxx(String slbh, String zl, String bdcqzh, String yt, String qxdm){
        this.slbh = slbh;
        this.zl = zl;
        this.bdcqzh = bdcqzh;
        this.yt = yt;
        this.qxdm = qxdm;
        return this;
    }

    public BdcYjSfxxDTO withYwrxx(String ywr, String ywrzjh){
        this.ywr = ywr;
        this.ywrzjh = ywrzjh;
        return this;
    }

    public BdcYjSfxxDTO addGzldymc(String gzldymc){
        this.gzldymc = gzldymc;
        return this;
    }

    public Integer getSfxmzsl() {
        return sfxmzsl;
    }

    public void setSfxmzsl(Integer sfxmzsl) {
        this.sfxmzsl = sfxmzsl;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }
}
