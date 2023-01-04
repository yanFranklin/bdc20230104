package cn.gtmap.realestate.exchange.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: realestate
 * @description: 上报接入操作日志记录
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-05 09:44
 **/
@Table(name = "BDC_JR_CZRZ")
public class BdcJrCzrzDO {

    @Id
    @ApiModelProperty("日志id")
    private String rzid;

    @ApiModelProperty("受理编号")
    private String slbh;
    @ApiModelProperty("工作流实例id")
    private String gzlslid;
    @ApiModelProperty("项目id")
    private String xmid;
    @ApiModelProperty("报文id")
    private String bwid;
    @ApiModelProperty("操作类型")
    private Integer czlx;
    @ApiModelProperty("操作时间")
    private Date czsj;
    @ApiModelProperty("操作内容")
    private String cznr;
    @ApiModelProperty("流程名称")
    private String lcmc;
    @ApiModelProperty("操作结果")
    private String czjg;

    public String getRzid() {
        return rzid;
    }

    public void setRzid(String rzid) {
        this.rzid = rzid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBwid() {
        return bwid;
    }

    public void setBwid(String bwid) {
        this.bwid = bwid;
    }

    public Integer getCzlx() {
        return czlx;
    }

    public void setCzlx(Integer czlx) {
        this.czlx = czlx;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getCznr() {
        return cznr;
    }

    public void setCznr(String cznr) {
        this.cznr = cznr;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getCzjg() {
        return czjg;
    }

    public void setCzjg(String czjg) {
        this.czjg = czjg;
    }

    @Override
    public String toString() {
        return "BdcJrCzrzDO{" +
                "rzid='" + rzid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", bwid='" + bwid + '\'' +
                ", czlx=" + czlx +
                ", czsj=" + czsj +
                ", cznr='" + cznr + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", czjg='" + czjg + '\'' +
                '}';
    }
}
