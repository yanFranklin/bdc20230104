package cn.gtmap.realestate.common.core.qo.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
 * @version 1.0, 2021/10/30
 * @description 档案分配盒号
 */
@ApiModel(value = "BdcGdxxFphhQO",description = "不动产归档信息分配盒号查询对象")
public class BdcGdxxFphhQO implements Serializable {
    @ApiModelProperty(value = "盒号顺序号（按照乡镇、年份编）")
    private Integer sxh;

    @ApiModelProperty(value = "乡镇代码")
    private String xzdm;

    @ApiModelProperty(value = "年份")
    private String nf;

    // 盒号
    @ApiModelProperty(value = "目录号")
    private String mlh;

    // 档案号
    @ApiModelProperty(value = "档案号后半部分开始")
    private String ajhStart;

    // 档案号
    @ApiModelProperty(value = "档案号后半部分结束")
    private String ajhEnd;

    // 档案号
    @ApiModelProperty(value = "档案号前半部分")
    private String ajhFirstHalf;

    @ApiModelProperty(value = "档案号用于拼接档案号分页查询用")
    private String dahStart;

    @ApiModelProperty(value = "档案号用于拼接档案号分页查询用")
    private String dahEnd;

    @ApiModelProperty(value = "档案号类型")
    private String dahlx;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "档案号生成时间")
    private Date dahscsj;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getDahlx() {
        return dahlx;
    }

    public void setDahlx(String dahlx) {
        this.dahlx = dahlx;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Date getDahscsj() {
        return dahscsj;
    }

    public void setDahscsj(Date dahscsj) {
        this.dahscsj = dahscsj;
    }

    public String getDahStart() {
       if (StringUtils.isNotBlank(this.getAjhFirstHalf()) && StringUtils.isNotBlank(this.getAjhStart())) {
          return this.getAjhFirstHalf()+"-"+this.getAjhStart();
       }
       return "";
   }

   public String getDahEnd() {
       if (StringUtils.isNotBlank(this.getAjhFirstHalf()) && StringUtils.isNotBlank(this.getAjhEnd())) {
          return this.getAjhFirstHalf()+"-"+this.getAjhEnd();
       }
       return "";
   }

    public String getMlh() {
        return mlh;
    }

    public void setMlh(String mlh) {
        this.mlh = mlh;
    }

    public String getAjhStart() {
        return ajhStart;
    }

    public void setAjhStart(String ajhStart) {
        this.ajhStart = ajhStart;
    }

    public String getAjhEnd() {
        return ajhEnd;
    }

    public void setAjhEnd(String ajhEnd) {
        this.ajhEnd = ajhEnd;
    }

    public String getAjhFirstHalf() {
        return ajhFirstHalf;
    }

    public void setAjhFirstHalf(String ajhFirstHalf) {
        this.ajhFirstHalf = ajhFirstHalf;
    }

    public String getXzdm() {
        return xzdm;
    }

    public void setXzdm(String xzdm) {
        this.xzdm = xzdm;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public void setDahStart(String dahStart) {
        this.dahStart = dahStart;
    }

    public void setDahEnd(String dahEnd) {
        this.dahEnd = dahEnd;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    @Override
    public String toString() {
        return "BdcGdxxFphhQO{" +
                "sxh=" + sxh +
                ", xzdm='" + xzdm + '\'' +
                ", nf='" + nf + '\'' +
                ", mlh='" + mlh + '\'' +
                ", ajhStart='" + ajhStart + '\'' +
                ", ajhEnd='" + ajhEnd + '\'' +
                ", ajhFirstHalf='" + ajhFirstHalf + '\'' +
                ", dahStart='" + dahStart + '\'' +
                ", dahEnd='" + dahEnd + '\'' +
                ", dahlx='" + dahlx + '\'' +
                ", xmid='" + xmid + '\'' +
                ", dahscsj=" + dahscsj +
                ", gzlslid='" + gzlslid + '\'' +
                '}';
    }
}
