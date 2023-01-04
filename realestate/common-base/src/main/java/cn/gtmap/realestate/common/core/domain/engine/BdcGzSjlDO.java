package cn.gtmap.realestate.common.core.domain.engine;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description 不动产规则数据流实体DO定义
 */
@Table(name = "BDC_GZ_SJL")
public class BdcGzSjlDO {
    /**
     * 数据流ID
     */
    @Id
    @ApiModelProperty(value = "数据流ID")
    private String sjlid;
    /**
     * 数据流顺序
     */
    @Id
    @ApiModelProperty(value = "数据流顺序")
    private Integer sjlsx;
    /**
     * 规则ID
     */
    @ApiModelProperty(value = "规则ID")
    private String gzid;
    /**
     * 数据流名称
     */
    @ApiModelProperty(value = "数据流名称")
    private String sjlmc;
    /**
     * 数据来源 (1、SQL 2、服务)
     */
    @ApiModelProperty(value = "数据来源")
    private String sjly;
    /**
     * 数据内容
     */
    @ApiModelProperty(value = "数据内容")
    private String sjnr;
    /**
     * 请求应用（用于调用服务时）
     */
    @ApiModelProperty(value = "请求应用")
    private String qqyy;
    /**
     * HTTP方法（1、GET 2、POST）
     */
    @ApiModelProperty(value = "服务方法")
    private String fwff;
    /**
     * 结果变量名称
     */
    @ApiModelProperty(value = "结果变量名称")
    private String jgblmc;
    /**
     * 结果变量标识
     */
    @ApiModelProperty(value = "结果变量标识")
    private String jgblbs;


    public String getSjlid() {
        return sjlid;
    }

    public void setSjlid(String sjlid) {
        this.sjlid = sjlid;
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getSjlmc() {
        return sjlmc;
    }

    public void setSjlmc(String sjlmc) {
        this.sjlmc = sjlmc;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getSjnr() {
        return sjnr;
    }

    public void setSjnr(String sjnr) {
        this.sjnr = sjnr;
    }

    public String getQqyy() {
        return qqyy;
    }

    public void setQqyy(String qqyy) {
        this.qqyy = qqyy;
    }

    public String getJgblmc() {
        return jgblmc;
    }

    public void setJgblmc(String jgblmc) {
        this.jgblmc = jgblmc;
    }

    public String getJgblbs() {
        return jgblbs;
    }

    public void setJgblbs(String jgblbs) {
        this.jgblbs = jgblbs;
    }

    public String getFwff() {
        return fwff;
    }

    public void setFwff(String fwff) {
        this.fwff = fwff;
    }

    public Integer getSjlsx() {
        return sjlsx;
    }

    public void setSjlsx(Integer sjlsx) {
        this.sjlsx = sjlsx;
    }

    @Override
    public String toString() {
        return "BdcGzSjlDO{" +
                "sjlid='" + sjlid + '\'' +
                ", sjlsx=" + sjlsx +
                ", gzid='" + gzid + '\'' +
                ", sjlmc='" + sjlmc + '\'' +
                ", sjly='" + sjly + '\'' +
                ", sjnr='" + sjnr + '\'' +
                ", qqyy='" + qqyy + '\'' +
                ", fwff='" + fwff + '\'' +
                ", jgblmc='" + jgblmc + '\'' +
                ", jgblbs='" + jgblbs + '\'' +
                '}';
    }
}
