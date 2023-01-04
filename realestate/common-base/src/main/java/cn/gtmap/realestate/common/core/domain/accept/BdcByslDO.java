package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: realestate
 * @description: 不予受理信息实体
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-22 17:17
 **/
@Table(name = "BDC_BYSL")
@ApiModel(value = "BdcByslDO", description = "不动产不予受理信息")
public class BdcByslDO {

    @ApiModelProperty("主键不予受理id")
    @Id
    private String byslid;

    @ApiModelProperty("工作流实例id")
    private String gzlslid;

    @ApiModelProperty("否定事项申请人")
    private String fdsxsqr;

    @ApiModelProperty("联系电话")
    private String lxdh;

    @ApiModelProperty("登记原因")
    private String djyy;

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    @ApiModelProperty("退回时间")
    private Date thsj;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("办理事项")
    private String blsx;

    @ApiModelProperty("否定理由依据")
    private String fdlyyj;

    @ApiModelProperty("具体情况")
    private String jtqk;

    @ApiModelProperty("承办科室意见")
    private String cbksyj;

    @ApiModelProperty("政策法规意见")
    private String zcfgyj;

    @ApiModelProperty("分管领导意见")
    private String fgldyj;

    @ApiModelProperty("备注")
    private String bz;

    @ApiModelProperty("不予受理类型 1：不予受理 2 不予登记")
    private String lx;

    @ApiModelProperty("告知书编号")
    private String gzsbh;

    public String getByslid() {
        return byslid;
    }

    public void setByslid(String byslid) {
        this.byslid = byslid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getFdsxsqr() {
        return fdsxsqr;
    }

    public void setFdsxsqr(String fdsxsqr) {
        this.fdsxsqr = fdsxsqr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public Date getThsj() {
        return thsj;
    }

    public void setThsj(Date thsj) {
        this.thsj = thsj;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBlsx() {
        return blsx;
    }

    public void setBlsx(String blsx) {
        this.blsx = blsx;
    }

    public String getFdlyyj() {
        return fdlyyj;
    }

    public void setFdlyyj(String fdlyyj) {
        this.fdlyyj = fdlyyj;
    }

    public String getJtqk() {
        return jtqk;
    }

    public void setJtqk(String jtqk) {
        this.jtqk = jtqk;
    }

    public String getCbksyj() {
        return cbksyj;
    }

    public void setCbksyj(String cbksyj) {
        this.cbksyj = cbksyj;
    }

    public String getZcfgyj() {
        return zcfgyj;
    }

    public void setZcfgyj(String zcfgyj) {
        this.zcfgyj = zcfgyj;
    }

    public String getFgldyj() {
        return fgldyj;
    }

    public void setFgldyj(String fgldyj) {
        this.fgldyj = fgldyj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getGzsbh() {
        return gzsbh;
    }

    public void setGzsbh(String gzsbh) {
        this.gzsbh = gzsbh;
    }

    @Override
    public String toString() {
        return "BdcByslDO{" +
                "byslid='" + byslid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", fdsxsqr='" + fdsxsqr + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", thsj=" + thsj +
                ", zl='" + zl + '\'' +
                ", blsx='" + blsx + '\'' +
                ", fdlyyj='" + fdlyyj + '\'' +
                ", jtqk='" + jtqk + '\'' +
                ", cbksyj='" + cbksyj + '\'' +
                ", zcfgyj='" + zcfgyj + '\'' +
                ", fgldyj='" + fgldyj + '\'' +
                ", bz='" + bz + '\'' +
                ", lx='" + lx + '\'' +
                ", gzsbh='" + gzsbh + '\'' +
                ", djyy='" + djyy + '\'' +
                '}';
    }
}
