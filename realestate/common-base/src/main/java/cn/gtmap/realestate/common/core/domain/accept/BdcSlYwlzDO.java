package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 受理业务流转数据对象
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-22 10:47
 **/
@Table(name = "bdc_sl_ywlz")
public class BdcSlYwlzDO {

    @Id
    @ApiModelProperty("业务流转id")
    private String ywlzid;

    @ApiModelProperty("工作流实例id")
    private String gzlslid;

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("业务流转编号")
    private String ywlzbh;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("预约人")
    private String yyr;

    @ApiModelProperty("预约人电话")
    private String yyrdh;

    @ApiModelProperty("被继承人")
    private String bjcr;

    @ApiModelProperty("预约情况")
    private String yyqk;

    @ApiModelProperty("调查情况")
    private String dcqk;

    @ApiModelProperty("备注")
    private String bz;

    @ApiModelProperty("业务来源")
    private Integer ywly;

    public String getYwlzid() {
        return ywlzid;
    }

    public void setYwlzid(String ywlzid) {
        this.ywlzid = ywlzid;
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

    public String getYwlzbh() {
        return ywlzbh;
    }

    public void setYwlzbh(String ywlzbh) {
        this.ywlzbh = ywlzbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getYyr() {
        return yyr;
    }

    public void setYyr(String yyr) {
        this.yyr = yyr;
    }

    public String getYyrdh() {
        return yyrdh;
    }

    public void setYyrdh(String yyrdh) {
        this.yyrdh = yyrdh;
    }

    public String getBjcr() {
        return bjcr;
    }

    public void setBjcr(String bjcr) {
        this.bjcr = bjcr;
    }

    public String getYyqk() {
        return yyqk;
    }

    public void setYyqk(String yyqk) {
        this.yyqk = yyqk;
    }

    public String getDcqk() {
        return dcqk;
    }

    public void setDcqk(String dcqk) {
        this.dcqk = dcqk;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getYwly() {
        return ywly;
    }

    public void setYwly(Integer ywly) {
        this.ywly = ywly;
    }

    @Override
    public String toString() {
        return "BdcSlYwlzDO{" +
                "ywlzid='" + ywlzid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", ywlzbh='" + ywlzbh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", yyr='" + yyr + '\'' +
                ", yyrdh='" + yyrdh + '\'' +
                ", bjcr='" + bjcr + '\'' +
                ", yyqk='" + yyqk + '\'' +
                ", dcqk='" + dcqk + '\'' +
                ", bz='" + bz + '\'' +
                ", ywly=" + ywly +
                '}';
    }
}
