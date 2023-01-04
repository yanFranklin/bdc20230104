package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2021/2/23
 * @description 受理流程修正信息DO
 */
@Table(name = "BDC_SL_XZXX")
@ApiModel(value = "BdcSlXzxxDO", description = "受理流程修正信息DO")
public class BdcSlXzxxDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String xzxxid;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "gzlslid")
    private String gzlslid;

    @ApiModelProperty(value = "被修正的xmid")
    private String yxmid;

    @ApiModelProperty(value = "补录状态")
    private Integer blfs;

    @ApiModelProperty(value = "修正内容")
    private String xznr;

    @ApiModelProperty(value = "修改人")
    private String xgr;

    @ApiModelProperty(value = "修改日期")
    private Date xgrq;

    @ApiModelProperty(value = "修正来源 1-选择台账创建 2-流程创建 3-无数据创建")
    private Integer xzly;

    @ApiModelProperty(value = "修正审核意见")
    private String xzyj;

    @ApiModelProperty("原流程节点名称")
    private String ylcjdmc;

    public String getXzxxid() {
        return xzxxid;
    }

    public void setXzxxid(String xzxxid) {
        this.xzxxid = xzxxid;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public Integer getBlfs() {
        return blfs;
    }

    public void setBlfs(Integer blfs) {
        this.blfs = blfs;
    }

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    public Date getXgrq() {
        return xgrq;
    }

    public void setXgrq(Date xgrq) {
        this.xgrq = xgrq;
    }

    public String getXznr() {
        return xznr;
    }

    public void setXznr(String xznr) {
        this.xznr = xznr;
    }

    public Integer getXzly() {
        return xzly;
    }

    public void setXzly(Integer xzly) {
        this.xzly = xzly;
    }

    public String getXzyj() {
        return xzyj;
    }

    public void setXzyj(String xzyj) {
        this.xzyj = xzyj;
    }

    public String getYlcjdmc() {
        return ylcjdmc;
    }

    public void setYlcjdmc(String ylcjdmc) {
        this.ylcjdmc = ylcjdmc;
    }

    @Override
    public String toString() {
        return "BdcSlXzxxDO{" +
                "xzxxid='" + xzxxid + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", yxmid='" + yxmid + '\'' +
                ", blfs=" + blfs +
                ", xznr='" + xznr + '\'' +
                ", xgr='" + xgr + '\'' +
                ", xgrq=" + xgrq +
                ", xzly=" + xzly +
                ", xzyj='" + xzyj + '\'' +
                ", ylcjdmc='" + ylcjdmc + '\'' +
                '}';
    }
}
