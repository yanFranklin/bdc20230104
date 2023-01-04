package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @version 1.0  2022/04/07
 * @description 不动产综合查询台账查询和打印操作日志表
 */
@Table(name = "BDC_GZYZ_LOG")
public class BdcGzyzLogDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String rzid;

    @ApiModelProperty(value = "验证人id")
    private String yzrid;

    @ApiModelProperty(value = "验证人账号")
    private String yzrzh;

    @ApiModelProperty(value = "验证时间", example = "2018-10-01 12:18:48")
    private Date yzsj;

    @ApiModelProperty(value = "组合标示")
    private String zhbs;

    @ApiModelProperty(value = "组合名称")
    private String zhmc;

    @ApiModelProperty(value = "子规则id")
    private String gzid;

    @ApiModelProperty(value = "子规则名称")
    private String gzmc;

    @ApiModelProperty(value = "验证参数")
    private String yzcs;

    @ApiModelProperty(value = "验证结果")
    private String yzjg;

    @ApiModelProperty(value = "是否通过")
    private Integer sftg;

    @ApiModelProperty(value = "验证标识：用于识别同一个组合规则下验证的子规则")
    private String yzbs;

    public String getRzid() {
        return rzid;
    }

    public void setRzid(String rzid) {
        this.rzid = rzid;
    }

    public String getYzrid() {
        return yzrid;
    }

    public void setYzrid(String yzrid) {
        this.yzrid = yzrid;
    }

    public String getYzrzh() {
        return yzrzh;
    }

    public void setYzrzh(String yzrzh) {
        this.yzrzh = yzrzh;
    }

    public Date getYzsj() {
        return yzsj;
    }

    public void setYzsj(Date yzsj) {
        this.yzsj = yzsj;
    }

    public String getZhbs() {
        return zhbs;
    }

    public void setZhbs(String zhbs) {
        this.zhbs = zhbs;
    }

    public String getZhmc() {
        return zhmc;
    }

    public void setZhmc(String zhmc) {
        this.zhmc = zhmc;
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getGzmc() {
        return gzmc;
    }

    public void setGzmc(String gzmc) {
        this.gzmc = gzmc;
    }

    public String getYzcs() {
        return yzcs;
    }

    public void setYzcs(String yzcs) {
        this.yzcs = yzcs;
    }

    public String getYzjg() {
        return yzjg;
    }

    public void setYzjg(String yzjg) {
        this.yzjg = yzjg;
    }

    public Integer getSftg() {
        return sftg;
    }

    public void setSftg(Integer sftg) {
        this.sftg = sftg;
    }

    public String getYzbs() {
        return yzbs;
    }

    public void setYzbs(String yzbs) {
        this.yzbs = yzbs;
    }

    @Override
    public String toString() {
        return "BdcGzyzLogDO{" +
                "rzid='" + rzid + '\'' +
                ", yzrid='" + yzrid + '\'' +
                ", yzrzh='" + yzrzh + '\'' +
                ", yzsj=" + yzsj +
                ", zhbs='" + zhbs + '\'' +
                ", zhmc='" + zhmc + '\'' +
                ", gzid='" + gzid + '\'' +
                ", gzmc='" + gzmc + '\'' +
                ", yzcs='" + yzcs + '\'' +
                ", yzjg='" + yzjg + '\'' +
                ", sftg=" + sftg +
                ", yzbs='" + yzbs + '\'' +
                '}';
    }

}
