package cn.gtmap.realestate.common.core.domain.accept;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/22/9:14
 * @Description:
 */
@Table(name = "BDC_SL_FGYHSF")
@ApiModel(value = "BdcSlFgyhsfDO", description = "不动产受理房改优惠售房")
public class BdcSlFgyhsfDO {
    @Id
    @ApiModelProperty("房改优惠售房id")
    private String fgyhsfid;

    @ApiModelProperty("工作流实例id")
    private String gzlslid;

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("接轨原因")
    private String jgyy;

    @ApiModelProperty("接轨日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date jgrq;

    @ApiModelProperty("新证编号")
    private String xzbh;

    @ApiModelProperty("备注")
    private String bz;

    @ApiModelProperty("原房屋所有权证号")
    private String yfwsyzh;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("房屋所有权人")
    private String fwsyqr;

    @ApiModelProperty("原项目Id")
    private String yxmid;

    public String getFgyhsfid() {
        return fgyhsfid;
    }

    public void setFgyhsfid(String fgyhsfid) {
        this.fgyhsfid = fgyhsfid;
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

    public String getJgyy() {
        return jgyy;
    }

    public void setJgyy(String jgyy) {
        this.jgyy = jgyy;
    }

    public Date getJgrq() {
        return jgrq;
    }

    public void setJgrq(Date jgrq) {
        this.jgrq = jgrq;
    }

    public String getXzbh() {
        return xzbh;
    }

    public void setXzbh(String xzbh) {
        this.xzbh = xzbh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getYfwsyzh() {
        return yfwsyzh;
    }

    public void setYfwsyzh(String yfwsyzh) {
        this.yfwsyzh = yfwsyzh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getFwsyqr() {
        return fwsyqr;
    }

    public void setFwsyqr(String fwsyqr) {
        this.fwsyqr = fwsyqr;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    @Override
    public String toString() {
        return "BdcSlFgyhsfDO{" +
                "fgyhsfid='" + fgyhsfid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", jgyy='" + jgyy + '\'' +
                ", jgrq=" + jgrq +
                ", xzbh='" + xzbh + '\'' +
                ", bz='" + bz + '\'' +
                ", yfwsyzh='" + yfwsyzh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", fwsyqr='" + fwsyqr + '\'' +
                ", yxmid='" + yxmid + '\'' +
                '}';
    }
}
