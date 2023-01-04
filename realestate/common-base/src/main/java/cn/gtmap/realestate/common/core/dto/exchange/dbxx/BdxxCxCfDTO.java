package cn.gtmap.realestate.common.core.dto.exchange.dbxx;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class BdxxCxCfDTO extends BdxxCxQlrDTO {
    @ApiModelProperty(value = "不动产单元号")
    public String bdcdyh;

    @ApiModelProperty(value = "坐落")
    public String zl;

    @ApiModelProperty(value = "查封机关")
    public String cfjg;

    @ApiModelProperty(value = "查封类型")
    public String cflx;

    @ApiModelProperty(value = "权属状态")
    public String qszt;

    @ApiModelProperty(value = "查封文号")
    public String cfwh;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查封期限起")
    public Date cfqssj;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "查封期限止")
    public Date cfjssj;

    @ApiModelProperty(value = "登记机构")
    public String djjg;

    @ApiModelProperty(value = "序号")
    public String xh;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    public Date djsj;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "解封登记时间")
    public Date jfdjsj;

    @ApiModelProperty(value = "查封范围")
    public String cffw;

    @ApiModelProperty(value = "解封文号")
    public String jfwh;

    @ApiModelProperty(value = "解封业务号")
    public String jfywh;

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
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

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
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

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public Date getJfdjsj() {
        return jfdjsj;
    }

    public void setJfdjsj(Date jfdjsj) {
        this.jfdjsj = jfdjsj;
    }

    public String getCffw() {
        return cffw;
    }

    public void setCffw(String cffw) {
        this.cffw = cffw;
    }

    public String getJfwh() {
        return jfwh;
    }

    public void setJfwh(String jfwh) {
        this.jfwh = jfwh;
    }

    public String getJfywh() {
        return jfywh;
    }

    public void setJfywh(String jfywh) {
        this.jfywh = jfywh;
    }
}
