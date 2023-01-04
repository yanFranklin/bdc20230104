package cn.gtmap.realestate.common.core.domain.accept;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/10
 * @description 受理查封解封信息
 */
@Table(name = "BDC_SL_CFJF")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcSlCfjfDO.class)
@ApiModel(value = "BdcSlCfjfDO", description = "不动产受理查封解封信息")
public class BdcSlCfjfDO implements BdcSlQl{

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "项目ID")
    private String cfwh;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "查封范围")
    private String cffw;

    @ApiModelProperty(value = "查封开始时间 格式 yyyy-MM-dd")
    private Date cfqssj;

    @ApiModelProperty(value = "查封结束时间 格式 yyyy-MM-dd")
    private Date cfjssj;

    @ApiModelProperty(value = "查封文件")
    private String cfwj;

    @ApiModelProperty(value = "查封机关")
    private String cfjg;

    @ApiModelProperty(value = "查封原因")
    private String cfyy;

    @ApiModelProperty(value = "解封文号")
    private String jfwh;

    @ApiModelProperty(value = "解封机关")
    private String jfjg;

    @ApiModelProperty(value = "解封文件")
    private String jfwj;

    @ApiModelProperty(value = "解封原因")
    private String jfyy;

    @ApiModelProperty(value = "查封类型")
    private String cflx;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "查封时间 格式 yyyy-MM-dd")
    private Date cfsj;

    @ApiModelProperty(value = "执行申请人")
    private String zxsqr;

    @ApiModelProperty(value = "（法院）查封送达人")
    private String fysdr;

    @ApiModelProperty(value = "法院（查封）送达人联系电话")
    private String fysdrlxdh;

    @ApiModelProperty(value = "解封送达人联系电话")
    private String jfsdrlxdh;

    @ApiModelProperty(value = "解封送达人")
    private String jfsdr;

    public Date getCfsj() {
        return cfsj;
    }

    public void setCfsj(Date cfsj) {
        this.cfsj = cfsj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getXmid() {
        return xmid;
    }
    @Override
    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public Date getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(Date cfqssj) {
        this.cfqssj = cfqssj;
    }

    public Date getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(Date cfjssj) {
        this.cfjssj = cfjssj;
    }

    public String getCfwj() {
        return cfwj;
    }

    public void setCfwj(String cfwj) {
        this.cfwj = cfwj;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCfyy() {
        return cfyy;
    }

    public void setCfyy(String cfyy) {
        this.cfyy = cfyy;
    }

    public String getJfwh() {
        return jfwh;
    }

    public void setJfwh(String jfwh) {
        this.jfwh = jfwh;
    }

    public String getJfjg() {
        return jfjg;
    }

    public void setJfjg(String jfjg) {
        this.jfjg = jfjg;
    }

    public String getJfwj() {
        return jfwj;
    }

    public void setJfwj(String jfwj) {
        this.jfwj = jfwj;
    }

    public String getJfyy() {
        return jfyy;
    }

    public void setJfyy(String jfyy) {
        this.jfyy = jfyy;
    }

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
    }

    public String getCffw() {
        return cffw;
    }

    public void setCffw(String cffw) {
        this.cffw = cffw;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getZxsqr() {
        return zxsqr;
    }

    public void setZxsqr(String zxsqr) {
        this.zxsqr = zxsqr;
    }

    public String getFysdr() {
        return fysdr;
    }

    public void setFysdr(String fysdr) {
        this.fysdr = fysdr;
    }

    public String getFysdrlxdh() {
        return fysdrlxdh;
    }

    public void setFysdrlxdh(String fysdrlxdh) {
        this.fysdrlxdh = fysdrlxdh;
    }

    public String getJfsdrlxdh() {
        return jfsdrlxdh;
    }

    public void setJfsdrlxdh(String jfsdrlxdh) {
        this.jfsdrlxdh = jfsdrlxdh;
    }

    public String getJfsdr() {
        return jfsdr;
    }

    public void setJfsdr(String jfsdr) {
        this.jfsdr = jfsdr;
    }
}
