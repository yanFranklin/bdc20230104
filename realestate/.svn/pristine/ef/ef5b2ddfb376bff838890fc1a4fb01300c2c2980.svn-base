package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: realestate
 * @description: 业务办理核证信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-20 10:51
 **/
@Table(name = "BDC_YWBLHZXX")
public class BdcYwblhzxxDO {

    @Id
    @ApiModelProperty(value = "核证信息id")
    private String hzxxid;
    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;
    @ApiModelProperty(value = "原登记证明核实")
    private Integer ydjzmhs;
    @ApiModelProperty(value = "其他证明核实")
    private Integer qtzmhs;
    @ApiModelProperty(value = "证明原件核实")
    private Integer zmyjhs;
    @ApiModelProperty(value = "核证人")
    private String hzr;
    @ApiModelProperty(value = "核证日期")
    private Date hzrq;

    public String getHzxxid() {
        return hzxxid;
    }

    public void setHzxxid(String hzxxid) {
        this.hzxxid = hzxxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Integer getYdjzmhs() {
        return ydjzmhs;
    }

    public void setYdjzmhs(Integer ydjzmhs) {
        this.ydjzmhs = ydjzmhs;
    }

    public Integer getQtzmhs() {
        return qtzmhs;
    }

    public void setQtzmhs(Integer qtzmhs) {
        this.qtzmhs = qtzmhs;
    }

    public Integer getZmyjhs() {
        return zmyjhs;
    }

    public void setZmyjhs(Integer zmyjhs) {
        this.zmyjhs = zmyjhs;
    }

    public String getHzr() {
        return hzr;
    }

    public void setHzr(String hzr) {
        this.hzr = hzr;
    }

    public Date getHzrq() {
        return hzrq;
    }

    public void setHzrq(Date hzrq) {
        this.hzrq = hzrq;
    }

    @Override
    public String toString() {
        return "BdcYwblhzxxDO{" +
                "hzxxid='" + hzxxid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", ydjzmhs=" + ydjzmhs +
                ", qtzmhs=" + qtzmhs +
                ", zmyjhs=" + zmyjhs +
                ", hzr='" + hzr + '\'' +
                ", hzrq=" + hzrq +
                '}';
    }
}
