package cn.gtmap.realestate.common.core.domain.engine;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description  不动产子规则实体DO定义
 */
@Table(name = "BDC_GZ_ZGZ")
public class BdcGzZgzDO {
    /**
     * 规则ID
     */
    @Id
    @ApiModelProperty(value = "规则ID")
    private String gzid;
    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称")
    private String gzmc;
    /**
     * 用途说明
     */
    @ApiModelProperty(value = "用途说明")
    private String ytsm;
    /**
     * 配置人
     */
    @ApiModelProperty(value = "配置人")
    private String pzr;
    /**
     * 配置日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "配置日期" ,example = "2018-10-01 12:18:48")
    private Date pzrq;
    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private Integer yxj;
    /**
     * 校验代码
     */
    @ApiModelProperty(value = "校验代码")
    private String jydm;

    @ApiModelProperty(value = "排除区划")
    private String pcqh;


    public String getPcqh() {
        return pcqh;
    }

    public void setPcqh(String pcqh) {
        this.pcqh = pcqh;
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

    public String getYtsm() {
        return ytsm;
    }

    public void setYtsm(String ytsm) {
        this.ytsm = ytsm;
    }

    public String getPzr() {
        return pzr;
    }

    public void setPzr(String pzr) {
        this.pzr = pzr;
    }

    public Date getPzrq() {
        return pzrq;
    }

    public void setPzrq(Date pzrq) {
        this.pzrq = pzrq;
    }

    public Integer getYxj() {
        return yxj;
    }

    public void setYxj(Integer yxj) {
        this.yxj = yxj;
    }

    public String getJydm() {
        return jydm;
    }

    public void setJydm(String jydm) {
        this.jydm = jydm;
    }

    @Override
    public String toString() {
        return "BdcGzZgzDO{" +
                "gzid='" + gzid + '\'' +
                ", gzmc='" + gzmc + '\'' +
                ", ytsm='" + ytsm + '\'' +
                ", pzr='" + pzr + '\'' +
                ", pzrq=" + pzrq +
                ", yxj=" + yxj +
                ", jydm='" + jydm + '\'' +
                ", pcqh='" + pcqh + '\'' +
                '}';
    }
}
