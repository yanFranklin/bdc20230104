package cn.gtmap.realestate.common.core.domain.accept;

import cn.gtmap.realestate.common.util.UUIDGenerator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目历史关系
 */
@Table(name = "BDC_SL_XM_LSGX")
@ApiModel(value = "BdcSlXmLsgxDO", description = "不动产受理项目历史关系")
public class BdcSlXmLsgxDO implements Serializable {
    private static final long serialVersionUID = -8591658788845778792L;
    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "原项目ID")
    private String yxmid;
    @ApiModelProperty(value = "是否注销原权利  0:否  1：是")
    private Integer zxyql;
    @ApiModelProperty(value = "是否外联证书  0:否  1：是")
    private Integer sfwlzs;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "权利人")
    private String qlr;

    public BdcSlXmLsgxDO(String xmid, String yxmid,String bdcdyh,String zl,String qlr) {
        this.gxid = UUIDGenerator.generate16();
        this.xmid = xmid;
        this.yxmid = yxmid;
        this.bdcdyh = bdcdyh;
        this.zl = zl;
        this.qlr = qlr;
        this.sfwlzs = 0;
    }

    public BdcSlXmLsgxDO() {
        super();
    }

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public Integer getZxyql() {
        return zxyql;
    }

    public void setZxyql(Integer zxyql) {
        this.zxyql = zxyql;
    }

    public Integer getSfwlzs() {
        return sfwlzs;
    }

    public void setSfwlzs(Integer sfwlzs) {
        this.sfwlzs = sfwlzs;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    @Override
    public String toString() {
        return "BdcSlXmLsgxDO{" +
                "gxid='" + gxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", yxmid='" + yxmid + '\'' +
                ", zxyql=" + zxyql +
                ", sfwlzs=" + sfwlzs +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", qlr='" + qlr + '\'' +
                '}';
    }
}
