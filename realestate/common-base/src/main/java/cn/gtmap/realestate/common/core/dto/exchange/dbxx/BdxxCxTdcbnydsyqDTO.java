package cn.gtmap.realestate.common.core.dto.exchange.dbxx;

import cn.gtmap.realestate.common.core.domain.*;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class BdxxCxTdcbnydsyqDTO extends BdxxCxQlrDTO {
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "承包（使用权）面积")
    private Double cbmj;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "承包（使用）起始时间", example = "2018-10-01 12:18:48")
    private Date cbqssj;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "承包（使用）结束时间", example = "2018-10-01 12:18:48")
    private Date cbjssj;

    @ApiModelProperty(value = "不动产权证号")
    public String bdcqzh;

    @ApiModelProperty(value = "登记机构")
    private String djjg;

    @ApiModelProperty(value = "土地所有权性质")
    private String tdsyqxz;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    public Date djsj;

    @ApiModelProperty(value = "共有方式")
    public String gyfs;

    @ApiModelProperty(value = "权利情况")
    public String gyqk;

    @ApiModelProperty(value = "水域滩涂类型")
    private String syttlx;

    @ApiModelProperty(value = "权利类型")
    String qllx;

    @ApiModelProperty(value = "养殖业方式")
    private String yzyfs;

    @ApiModelProperty(value = "草原质量")
    private String cyzl;

    @ApiModelProperty(value = "适宜载畜量")
    private Integer syzcl;

    @ApiModelProperty(value = "发包方名称")
    private String fbfmc;

    @ApiModelProperty(value = "发包方代码")
    private String fbfdm;

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

    @ApiModelProperty(value = "权属状态")
    public String qszt;

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

    public Double getCbmj() {
        return cbmj;
    }

    public void setCbmj(Double cbmj) {
        this.cbmj = cbmj;
    }

    public Date getCbqssj() {
        return cbqssj;
    }

    public void setCbqssj(Date cbqssj) {
        this.cbqssj = cbqssj;
    }

    public Date getCbjssj() {
        return cbjssj;
    }

    public void setCbjssj(Date cbjssj) {
        this.cbjssj = cbjssj;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getTdsyqxz() {
        return tdsyqxz;
    }

    public void setTdsyqxz(String tdsyqxz) {
        this.tdsyqxz = tdsyqxz;
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

    public String getSyttlx() {
        return syttlx;
    }

    public void setSyttlx(String syttlx) {
        this.syttlx = syttlx;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getYzyfs() {
        return yzyfs;
    }

    public void setYzyfs(String yzyfs) {
        this.yzyfs = yzyfs;
    }

    public String getCyzl() {
        return cyzl;
    }

    public void setCyzl(String cyzl) {
        this.cyzl = cyzl;
    }

    public Integer getSyzcl() {
        return syzcl;
    }

    public void setSyzcl(Integer syzcl) {
        this.syzcl = syzcl;
    }

    public String getFbfmc() {
        return fbfmc;
    }

    public void setFbfmc(String fbfmc) {
        this.fbfmc = fbfmc;
    }

    public String getFbfdm() {
        return fbfdm;
    }

    public void setFbfdm(String fbfdm) {
        this.fbfdm = fbfdm;
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
}
