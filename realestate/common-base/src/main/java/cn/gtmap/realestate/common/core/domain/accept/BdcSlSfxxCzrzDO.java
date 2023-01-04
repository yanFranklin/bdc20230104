package cn.gtmap.realestate.common.core.domain.accept;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/12/9
 * @description 不动产受理收费信息
 */
@Table(name = "BDC_SL_SFXX_CZRZ")
@ApiModel(value = "BdcSlSfxxCzrzDO", description = "不动产受理收费信息操作日志")
public class BdcSlSfxxCzrzDO implements Serializable {

    private static final long serialVersionUID = 6039520263725678523L;
    @Id
    @ApiModelProperty(value = "日志ID")
    private String id;

    @ApiModelProperty(value = "收费信息id")
    private String sfxxid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "取消收费时间")
    private Date qxsfsj;

    @ApiModelProperty(value = "收费时间")
    private Date sfsj;

    @ApiModelProperty(value = "操作人")
    private String czr;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "修改缴费原因")
    private String xgjfyy;

    @ApiModelProperty(value = "修改缴费人")
    private String xgjfr;

    @ApiModelProperty(value = "修改缴费人id")
    private String xgjfrid;

    @ApiModelProperty(value = "修改缴费时间")
    private Date xgjfsj;

    @ApiModelProperty(value = "修改缴费时间")
    private String qxjfyy;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Date getQxsfsj() {
        return qxsfsj;
    }

    public void setQxsfsj(Date qxsfsj) {
        this.qxsfsj = qxsfsj;
    }

    public Date getSfsj() {
        return sfsj;
    }

    public void setSfsj(Date sfsj) {
        this.sfsj = sfsj;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXgjfyy() {
        return xgjfyy;
    }

    public void setXgjfyy(String xgjfyy) {
        this.xgjfyy = xgjfyy;
    }

    public String getXgjfr() {
        return xgjfr;
    }

    public void setXgjfr(String xgjfr) {
        this.xgjfr = xgjfr;
    }

    public String getXgjfrid() {
        return xgjfrid;
    }

    public void setXgjfrid(String xgjfrid) {
        this.xgjfrid = xgjfrid;
    }

    public Date getXgjfsj() {
        return xgjfsj;
    }

    public void setXgjfsj(Date xgjfsj) {
        this.xgjfsj = xgjfsj;
    }

    public String getQxjfyy() {
        return qxjfyy;
    }

    public void setQxjfyy(String qxjfyy) {
        this.qxjfyy = qxjfyy;
    }

    @Override
    public String toString() {
        return "BdcSlSfxxCzrzDO{" +
                "id='" + id + '\'' +
                ", sfxxid='" + sfxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qxsfsj=" + qxsfsj +
                ", sfsj=" + sfsj +
                ", czr='" + czr + '\'' +
                ", slbh='" + slbh + '\'' +
                ", xgjfyy='" + xgjfyy + '\'' +
                ", xgjfr='" + xgjfr + '\'' +
                ", xgjfrid='" + xgjfrid + '\'' +
                ", xgjfsj=" + xgjfsj +
                ", qxjfyy=" + qxjfyy +
                '}';
    }
}
