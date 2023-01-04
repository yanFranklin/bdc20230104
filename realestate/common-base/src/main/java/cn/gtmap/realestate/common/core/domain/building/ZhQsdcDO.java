package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2019-11-18
 * @description 宗海权属调查
 */
@ApiModel(value = "ZhQsdcDO", description = "权属调查信息表")
@Table(name = "ZH_QSDC")
public class ZhQsdcDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String zhQszdIndex;
    
    @ApiModelProperty(value = "宗海代码")
    private String zhdm;

    @ApiModelProperty(value = "地籍调查表主键")
    private String djdcbIndex;
    
    @ApiModelProperty(value = "使用期限")
    private String syqx;
    
    @ApiModelProperty(value = "宗海四至东")
    private String zhszd;
    
    @ApiModelProperty(value = "宗海四至东使用人")
    private String zhszdsyr;
    
    @ApiModelProperty(value = "宗海四至南")
    private String zhszn;
    
    @ApiModelProperty(value = "宗海四至南使用人")
    private String zhsznsyr;
    
    @ApiModelProperty(value = "宗海四至西")
    private String zhszx;
    
    @ApiModelProperty(value = "宗海四至西使用人")
    private String zhszxsyr;
    
    @ApiModelProperty(value = "宗海四至北")
    private String zhszb;
    
    @ApiModelProperty(value = "宗海四至北使用人")
    private String zhszbsyr;
    
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    
    @ApiModelProperty(value = "权属核查记事")
    private String qshcjs;
    
    @ApiModelProperty(value = "权属核查人")
    private String qshcr;
    
    @ApiModelProperty(value = "权属核查日期 ")
    private Date qshcrq;
    
    @ApiModelProperty(value = "海籍测量记事")
    private String hjcljs;
    
    @ApiModelProperty(value = "海籍测量人")
    private String hjclr;
    
    @ApiModelProperty(value = "海籍测量日期")
    private Date hjclrq;
    
    @ApiModelProperty(value = "海籍调查结果审核意见")
    private String hjdcjgshjy;
    
    @ApiModelProperty(value = "海籍调查结果审核人")
    private String hjdcjgshr;
    
    @ApiModelProperty(value = "海籍调查结果审核日期")
    private Date hjdcjgshrq;
    
    @ApiModelProperty(value = "备注")
    private String bz;
    
    @ApiModelProperty(value = "调查机构")
    private String dcjg;
    
    @ApiModelProperty(value = "变更编号")
    private String bgbh;
    
    public String getZhQszdIndex() {
        return zhQszdIndex;
    }
    
    public void setZhQszdIndex(String zhQszdIndex) {
        this.zhQszdIndex = zhQszdIndex;
    }
    
    public String getZhdm() {
        return zhdm;
    }
    
    public void setZhdm(String zhdm) {
        this.zhdm = zhdm;
    }
    
    public String getDjdcbIndex() {
        return djdcbIndex;
    }
    
    public void setDjdcbIndex(String djdcbIndex) {
        this.djdcbIndex = djdcbIndex;
    }
    
    public String getBz() {
        return bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }
    
    public String getSyqx() {
        return syqx;
    }
    
    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }
    
    public String getZhszd() {
        return zhszd;
    }
    
    public void setZhszd(String zhszd) {
        this.zhszd = zhszd;
    }
    
    public String getZhszdsyr() {
        return zhszdsyr;
    }
    
    public void setZhszdsyr(String zhszdsyr) {
        this.zhszdsyr = zhszdsyr;
    }
    
    public String getZhszn() {
        return zhszn;
    }
    
    public void setZhszn(String zhszn) {
        this.zhszn = zhszn;
    }
    
    public String getZhsznsyr() {
        return zhsznsyr;
    }
    
    public void setZhsznsyr(String zhsznsyr) {
        this.zhsznsyr = zhsznsyr;
    }
    
    public String getZhszx() {
        return zhszx;
    }
    
    public void setZhszx(String zhszx) {
        this.zhszx = zhszx;
    }
    
    public String getZhszxsyr() {
        return zhszxsyr;
    }
    
    public void setZhszxsyr(String zhszxsyr) {
        this.zhszxsyr = zhszxsyr;
    }
    
    public String getZhszb() {
        return zhszb;
    }
    
    public void setZhszb(String zhszb) {
        this.zhszb = zhszb;
    }
    
    public String getZhszbsyr() {
        return zhszbsyr;
    }
    
    public void setZhszbsyr(String zhszbsyr) {
        this.zhszbsyr = zhszbsyr;
    }
    
    public String getGyqk() {
        return gyqk;
    }
    
    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }
    
    public String getQshcjs() {
        return qshcjs;
    }
    
    public void setQshcjs(String qshcjs) {
        this.qshcjs = qshcjs;
    }
    
    public String getQshcr() {
        return qshcr;
    }
    
    public void setQshcr(String qshcr) {
        this.qshcr = qshcr;
    }
    
    public Date getQshcrq() {
        return qshcrq;
    }
    
    public void setQshcrq(Date qshcrq) {
        this.qshcrq = qshcrq;
    }
    
    public String getHjcljs() {
        return hjcljs;
    }
    
    public void setHjcljs(String hjcljs) {
        this.hjcljs = hjcljs;
    }
    
    public String getHjclr() {
        return hjclr;
    }
    
    public void setHjclr(String hjclr) {
        this.hjclr = hjclr;
    }
    
    public String getHjdcjgshjy() {
        return hjdcjgshjy;
    }
    
    public void setHjdcjgshjy(String hjdcjgshjy) {
        this.hjdcjgshjy = hjdcjgshjy;
    }
    
    public String getHjdcjgshr() {
        return hjdcjgshr;
    }
    
    public void setHjdcjgshr(String hjdcjgshr) {
        this.hjdcjgshr = hjdcjgshr;
    }
    
    public Date getHjclrq() {
        return hjclrq;
    }
    
    public void setHjclrq(Date hjclrq) {
        this.hjclrq = hjclrq;
    }
    
    public Date getHjdcjgshrq() {
        return hjdcjgshrq;
    }
    
    public void setHjdcjgshrq(Date hjdcjgshrq) {
        this.hjdcjgshrq = hjdcjgshrq;
    }
    
    public String getDcjg() {
        return dcjg;
    }
    
    public void setDcjg(String dcjg) {
        this.dcjg = dcjg;
    }
    
    public String getBgbh() {
        return bgbh;
    }
    
    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }
    
    @Override
    public String toString() {
        return "ZhQsdcDO{" +
                "zhQszdIndex='" + zhQszdIndex + '\'' +
                ", zhdm='" + zhdm + '\'' +
                ", djdcbIndex='" + djdcbIndex + '\'' +
                ", syqx='" + syqx + '\'' +
                ", zhszd='" + zhszd + '\'' +
                ", zhszdsyr='" + zhszdsyr + '\'' +
                ", zhszn='" + zhszn + '\'' +
                ", zhsznsyr='" + zhsznsyr + '\'' +
                ", zhszx='" + zhszx + '\'' +
                ", zhszxsyr='" + zhszxsyr + '\'' +
                ", zhszb='" + zhszb + '\'' +
                ", zhszbsyr='" + zhszbsyr + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", qshcjs='" + qshcjs + '\'' +
                ", qshcr='" + qshcr + '\'' +
                ", qshcrq=" + qshcrq +
                ", hjcljs='" + hjcljs + '\'' +
                ", hjclr='" + hjclr + '\'' +
                ", hjclrq=" + hjclrq +
                ", hjdcjgshjy='" + hjdcjgshjy + '\'' +
                ", hjdcjgshr='" + hjdcjgshr + '\'' +
                ", hjdcjgshrq=" + hjdcjgshrq +
                ", bz='" + bz + '\'' +
                ", dcjg='" + dcjg + '\'' +
                ", bgbh='" + bgbh + '\'' +
                '}';
    }
}
