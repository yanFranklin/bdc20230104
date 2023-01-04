package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/16
 * @description 查询子系统证书证明查询DTO定义
 */
public class BdcZszmDTO {
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "证书证明ID")
    private String zsid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "原土地地证号")
    private String ytdzh;

    @ApiModelProperty(value = "原不动产单元号")
    private String ybdcdyh;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    @ApiModelProperty(value = "案件状态")
    private Integer ajzt;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "义务人名称")
    private String ywrmc;

    @ApiModelProperty(value = "义务人证件号")
    private String ywrzjh;

    @ApiModelProperty(value = "幢号")
    private String zh;

    @ApiModelProperty(value = "房间号")
    private String fjh;

    @ApiModelProperty(value = "缮证人")
    private String szr;

    @ApiModelProperty(value = "登记原因")
    private String djyy;

    @ApiModelProperty(value = "登记时间（缮证时间）")
    private Date djsj;

    @ApiModelProperty(value = "缮证时间")
    private Date szsj;

    @ApiModelProperty(value = "状态信息")
    private BdcdyhZtResponseDTO bdcdyZtDTO;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "房屋编号")
    private String fwbh;

    @ApiModelProperty(value = "证号流水号")
    private String zhlsh;

    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    @ApiModelProperty(value = "原系统产权证号")
    private String yxtcqzh;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "房屋价值")
    private Double jyjg;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty(value = "宗地宗海用途")
    private String zdzhyt;

    @ApiModelProperty(value = "宗地宗海面积")
    private String zdzhmj;

    @ApiModelProperty(value = "档案号")
    private String ajh;

    @ApiModelProperty(value = "项目来源")
    private String xmly;

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getXmly() {
        return xmly;
    }

    public void setXmly(String xmly) {
        this.xmly = xmly;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getAjzt() {
        return ajzt;
    }

    public void setAjzt(Integer ajzt) {
        this.ajzt = ajzt;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getYxtcqzh() {
        return yxtcqzh;
    }

    public void setYxtcqzh(String yxtcqzh) {
        this.yxtcqzh = yxtcqzh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

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

    public String getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String ywrmc) {
        this.ywrmc = ywrmc;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getSzr() {
        return szr;
    }

    public void setSzr(String szr) {
        this.szr = szr;
    }

    public Date getSzsj() {
        return szsj;
    }

    public void setSzsj(Date szsj) {
        this.szsj = szsj;
    }

    public BdcdyhZtResponseDTO getBdcdyZtDTO() {
        return bdcdyZtDTO;
    }

    public void setBdcdyZtDTO(BdcdyhZtResponseDTO bdcdyZtDTO) {
        this.bdcdyZtDTO = bdcdyZtDTO;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getFwbh() {
        return fwbh;
    }

    public void setFwbh(String fwbh) {
        this.fwbh = fwbh;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getYtdzh() {
        return ytdzh;
    }

    public void setYtdzh(String ytdzh) {
        this.ytdzh = ytdzh;
    }

    public String getYbdcdyh() {
        return ybdcdyh;
    }

    public void setYbdcdyh(String ybdcdyh) {
        this.ybdcdyh = ybdcdyh;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getZdzhyt() { return zdzhyt; }

    public void setZdzhyt(String zdzhyt) { this.zdzhyt = zdzhyt; }

    public String getAjh() {
        return ajh;
    }

    public void setAjh(String ajh) {
        this.ajh = ajh;
    }

    public String getZdzhmj() { return zdzhmj; }

    public void setZdzhmj(String zdzhmj) { this.zdzhmj = zdzhmj; }

    @Override
    public String toString() {
        return "BdcZszmDTO{" +
                "xmid='" + xmid + '\'' +
                ", zsid='" + zsid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", ytdzh='" + ytdzh + '\'' +
                ", ybdcdyh='" + ybdcdyh + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                ", zslx=" + zslx +
                ", qxdm='" + qxdm + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qszt=" + qszt +
                ", ajzt=" + ajzt +
                ", zl='" + zl + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", ywrmc='" + ywrmc + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", zh='" + zh + '\'' +
                ", fjh='" + fjh + '\'' +
                ", szr='" + szr + '\'' +
                ", djyy='" + djyy + '\'' +
                ", djsj=" + djsj +
                ", szsj=" + szsj +
                ", bdcdyZtDTO=" + bdcdyZtDTO +
                ", bdclx=" + bdclx +
                ", slbh='" + slbh + '\'' +
                ", fwbh='" + fwbh + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", yxtcqzh='" + yxtcqzh + '\'' +
                ", fj='" + fj + '\'' +
                ", jyjg=" + jyjg +
                ", qllx=" + qllx +
                ", djxl='" + djxl + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", zdzhyt='" + zdzhyt + '\'' +
                ", zdzhmj='" + zdzhmj + '\'' +
                ", ajh='" + ajh + '\'' +
                ", xmly='" + xmly + '\'' +
                '}';
    }
}
