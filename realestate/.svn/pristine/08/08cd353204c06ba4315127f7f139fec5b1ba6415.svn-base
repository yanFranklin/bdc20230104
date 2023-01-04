package cn.gtmap.realestate.common.core.dto.exchange.court.kz;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * 法院提供的司法控制请求-不动产权利信息
 *
 * @author wangyinghao
 */
@XStreamAlias("BDCQL")
public class CourtKzBdcQlItem {
    @ApiModelProperty(value = "控制请求单号")
    private String KZQQDH;

    @ApiModelProperty(value = "控制措施")
    private String KZCS;

    @ApiModelProperty(value = "不动产权利类型")
    private String BDCQLLX;

    @ApiModelProperty(value = "不动产单元号")
    private String BDCDYH;

    @ApiModelProperty(value = "坐落")
    private String ZL;

    @ApiModelProperty(value = "不动产权证号")
    private String BDCQZH;

    @ApiModelProperty(value = "裁定书文号")
    private String CKWH;

    @ApiModelProperty(value = "申请控制开始时间")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @JSONField(format = "yyyy/MM/dd")
    private String KSRQ;

    @ApiModelProperty(value = "申请控制结束时间")
    @JsonFormat(pattern = "yyyy/MM/dd")
    @JSONField(format = "yyyy/MM/dd")
    private String JSRQ;

    @ApiModelProperty(value = "原查封案号")
    private String YDJAH;

    @ApiModelProperty(value = "原查封请求单号")
    private String YDJDH;

    @ApiModelProperty(value = "业务号")
    private String YWH;

    @ApiModelProperty(value = "原查封业务号")
    private String YCFYWH;

    @ApiModelProperty(value = "登记机构")
    private String DJJG;

    @ApiModelProperty(value = "过户对象")
    @XmlElement(name = "GHDXLIST")
    @XStreamAlias("GHDXLIST")
    List<CourtKzBdcQlGhdxItem> ghdxList;

    public String getKZQQDH() {
        return KZQQDH;
    }

    public void setKZQQDH(String KZQQDH) {
        this.KZQQDH = KZQQDH;
    }

    public String getKZCS() {
        return KZCS;
    }

    public void setKZCS(String KZCS) {
        this.KZCS = KZCS;
    }

    public String getBDCQLLX() {
        return BDCQLLX;
    }

    public void setBDCQLLX(String BDCQLLX) {
        this.BDCQLLX = BDCQLLX;
    }

    public String getBDCDYH() {
        return BDCDYH;
    }

    public void setBDCDYH(String BDCDYH) {
        this.BDCDYH = BDCDYH;
    }

    public String getZL() {
        return ZL;
    }

    public void setZL(String ZL) {
        this.ZL = ZL;
    }

    public String getBDCQZH() {
        return BDCQZH;
    }

    public void setBDCQZH(String BDCQZH) {
        this.BDCQZH = BDCQZH;
    }

    public String getCKWH() {
        return CKWH;
    }

    public void setCKWH(String CKWH) {
        this.CKWH = CKWH;
    }

    public String getKSRQ() {
        return KSRQ;
    }

    public void setKSRQ(String KSRQ) {
        this.KSRQ = KSRQ;
    }

    public String getJSRQ() {
        return JSRQ;
    }

    public void setJSRQ(String JSRQ) {
        this.JSRQ = JSRQ;
    }

    public String getYDJAH() {
        return YDJAH;
    }

    public void setYDJAH(String YDJAH) {
        this.YDJAH = YDJAH;
    }

    public String getYDJDH() {
        return YDJDH;
    }

    public void setYDJDH(String YDJDH) {
        this.YDJDH = YDJDH;
    }

    public String getYWH() {
        return YWH;
    }

    public void setYWH(String YWH) {
        this.YWH = YWH;
    }

    public String getYCFYWH() {
        return YCFYWH;
    }

    public void setYCFYWH(String YCFYWH) {
        this.YCFYWH = YCFYWH;
    }

    public String getDJJG() {
        return DJJG;
    }

    public void setDJJG(String DJJG) {
        this.DJJG = DJJG;
    }

    public List<CourtKzBdcQlGhdxItem> getGhdxList() {
        return ghdxList;
    }

    public void setGhdxList(List<CourtKzBdcQlGhdxItem> ghdxList) {
        this.ghdxList = ghdxList;
    }
}
