package cn.gtmap.realestate.common.core.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/10/31
 * @description
 */
@ApiModel(value = "BdcWtsDTO", description = "委托书")
public class BdcWtsDTO implements Serializable {
    private static final long serialVersionUID = -6839679500536760564L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "委托书编号")
    private String wtsbh;

    @ApiModelProperty(value = "委托人姓名")
    private String wtrxm;

    @ApiModelProperty(value = "委托人性别")
    private String wtrxb;

    @ApiModelProperty(value = "委托人证件号")
    private String wtrzjh;

    @ApiModelProperty(value = "委托人电话")
    private String wtrdh;

    @ApiModelProperty(value = "受托人姓名")
    private String strxm;

    @ApiModelProperty(value = "受托人性别")
    private String strxb;

    @ApiModelProperty(value = "受托人证件号")
    private String strzjh;

    @ApiModelProperty(value = "受托人电话")
    private String strdh;

    @ApiModelProperty(value = "委托事项")
    private String wtsx;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "见证人")
    private String jzr;

    @ApiModelProperty(value = "出具状态")
    private Integer zt;

    @ApiModelProperty(value = "委托时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wtsj;

    @ApiModelProperty(value = "委托期间")
    private String wtqj;

    @ApiModelProperty(value = "委托原因")
    private String wtyy;

    @ApiModelProperty(value = "委托开始日期")
    @JSONField(format = "yyyy-MM-dd")
    private String wtksrq;

    public String getWtksrq() {
        return wtksrq;
    }

    public void setWtksrq(String wtksrq) {
        this.wtksrq = wtksrq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWtsbh() {
        return wtsbh;
    }

    public void setWtsbh(String wtsbh) {
        this.wtsbh = wtsbh;
    }

    public String getWtrxm() {
        return wtrxm;
    }

    public void setWtrxm(String wtrxm) {
        this.wtrxm = wtrxm;
    }

    public String getWtrxb() {
        return wtrxb;
    }

    public void setWtrxb(String wtrxb) {
        this.wtrxb = wtrxb;
    }

    public String getWtrzjh() {
        return wtrzjh;
    }

    public void setWtrzjh(String wtrzjh) {
        this.wtrzjh = wtrzjh;
    }

    public String getStrxm() {
        return strxm;
    }

    public void setStrxm(String strxm) {
        this.strxm = strxm;
    }

    public String getStrxb() {
        return strxb;
    }

    public void setStrxb(String strxb) {
        this.strxb = strxb;
    }

    public String getStrzjh() {
        return strzjh;
    }

    public void setStrzjh(String strzjh) {
        this.strzjh = strzjh;
    }

    public String getWtsx() {
        return wtsx;
    }

    public void setWtsx(String wtsx) {
        this.wtsx = wtsx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getJzr() {
        return jzr;
    }

    public void setJzr(String jzr) {
        this.jzr = jzr;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Date getWtsj() {
        return wtsj;
    }

    public void setWtsj(Date wtsj) {
        this.wtsj = wtsj;
    }

    public String getWtqj() {
        return wtqj;
    }

    public void setWtqj(String wtqj) {
        this.wtqj = wtqj;
    }

    public String getWtyy() {
        return wtyy;
    }

    public void setWtyy(String wtyy) {
        this.wtyy = wtyy;
    }

    public String getWtrdh() {
        return wtrdh;
    }

    public void setWtrdh(String wtrdh) {
        this.wtrdh = wtrdh;
    }

    public String getStrdh() {
        return strdh;
    }

    public void setStrdh(String strdh) {
        this.strdh = strdh;
    }

    @Override
    public String toString() {
        return "BdcWtsDO{" +
                "id='" + id + '\'' +
                ", wtsbh='" + wtsbh + '\'' +
                ", wtrxm='" + wtrxm + '\'' +
                ", wtrxb=" + wtrxb +
                ", wtrzjh='" + wtrzjh + '\'' +
                ", strxm='" + strxm + '\'' +
                ", strxb=" + strxb +
                ", strzjh='" + strzjh + '\'' +
                ", wtsx='" + wtsx + '\'' +
                ", zl='" + zl + '\'' +
                ", jzr='" + jzr + '\'' +
                ", zt=" + zt +
                ", wtsj=" + wtsj +
                ", wtqj='" + wtqj + '\'' +
                ", wtyy='" + wtyy + '\'' +
                ", wtrdh='" + wtrdh + '\'' +
                ", strdh='" + strdh + '\'' +
                '}';
    }
}
