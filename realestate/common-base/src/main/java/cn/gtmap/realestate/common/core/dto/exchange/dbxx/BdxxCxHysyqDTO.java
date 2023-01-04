package cn.gtmap.realestate.common.core.dto.exchange.dbxx;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class BdxxCxHysyqDTO  extends BdxxCxQlrDTO{
    @ApiModelProperty(value = "不动产单元号")
    public String bdcdyh;

    @ApiModelProperty(value = "项目名称")
    public String xmmc;

    @ApiModelProperty(value = "用海位置说明")
    public String yhwzsm;

    @ApiModelProperty(value = "用海类型a")
    public String yhlxa;

    @ApiModelProperty(value = "用海类型b")
    public String yhlxb;

    @ApiModelProperty(value = "海岛名称")
    public String hdmc;

    @ApiModelProperty(value = "海岛位置")
    public String hdwz;

    @ApiModelProperty(value = "海岛用途")
    public String hdyt;

    @ApiModelProperty(value = "使用权面积")
    public String syqmj;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "使用权起始时间 格式 yyyy-MM-dd")
    public Date syqqssj;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "使用权结束时间 格式 yyyy-MM-dd")
    public Date syqjssj;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记机构")
    public String djjg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    public Date djsj;

    @ApiModelProperty(value = "共有方式")
    public String gyfs;

    @ApiModelProperty(value = "权利情况")
    public String gyqk;

    @ApiModelProperty(value = "权属状态")
    public String qszt;

    @ApiModelProperty(value = "权利类型")
    String qllx;

    @ApiModelProperty(value = "不动产权证号")
    public String bdcqzh;

    @ApiModelProperty(value = "注销业务号")
    String zxywh;

    @ApiModelProperty(value = "注销原因")
    String zxyy;

    @ApiModelProperty(value = "注销登簿人")
    String zxdbr;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注销时间")
    Date zxsj;

    @ApiModelProperty(value = "使用金总额")
    Double syzje;

    @ApiModelProperty(value = "使用金缴纳情况")
    String syjjnqk;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getYhwzsm() {
        return yhwzsm;
    }

    public void setYhwzsm(String yhwzsm) {
        this.yhwzsm = yhwzsm;
    }

    public String getYhlxa() {
        return yhlxa;
    }

    public void setYhlxa(String yhlxa) {
        this.yhlxa = yhlxa;
    }

    public String getYhlxb() {
        return yhlxb;
    }

    public void setYhlxb(String yhlxb) {
        this.yhlxb = yhlxb;
    }

    public String getHdmc() {
        return hdmc;
    }

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }

    public String getHdwz() {
        return hdwz;
    }

    public void setHdwz(String hdwz) {
        this.hdwz = hdwz;
    }

    public String getHdyt() {
        return hdyt;
    }

    public void setHdyt(String hdyt) {
        this.hdyt = hdyt;
    }

    public String getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(String syqmj) {
        this.syqmj = syqmj;
    }

    public Date getSyqqssj() {
        return syqqssj;
    }

    public void setSyqqssj(Date syqqssj) {
        this.syqqssj = syqqssj;
    }

    public Date getSyqjssj() {
        return syqjssj;
    }

    public void setSyqjssj(Date syqjssj) {
        this.syqjssj = syqjssj;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZxywh() {
        return zxywh;
    }

    public void setZxywh(String zxywh) {
        this.zxywh = zxywh;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }

    public String getZxdbr() {
        return zxdbr;
    }

    public void setZxdbr(String zxdbr) {
        this.zxdbr = zxdbr;
    }

    public Date getZxsj() {
        return zxsj;
    }

    public void setZxsj(Date zxsj) {
        this.zxsj = zxsj;
    }

    public Double getSyzje() {
        return syzje;
    }

    public void setSyzje(Double syzje) {
        this.syzje = syzje;
    }

    public String getSyjjnqk() {
        return syjjnqk;
    }

    public void setSyjjnqk(String syjjnqk) {
        this.syjjnqk = syjjnqk;
    }
}
