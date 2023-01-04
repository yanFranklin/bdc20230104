package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/05/12/17:37
 * @Description: 省级质量评分responseDTO
 */
public class SjzlpfResponseDTO {
    @ApiModelProperty("序号")
    String xh;
    @ApiModelProperty("行政区划名称")
    String qhmc;
    @ApiModelProperty("本期接入业务量（笔）")
    String jrywl;
    @ApiModelProperty("本期接入登簿量（次）")
    String jrdbl;
    @ApiModelProperty("本期登薄日志反映出的登薄量(条)")
    String dbrzdbl;
    @ApiModelProperty("未上报登薄日志县区数(个)")
    String wscdbqxsl;
    @ApiModelProperty("未上报数据县区数(个)")
    String wscsjsl;
    @ApiModelProperty("未成功接入登薄量")
    String wcgjrdbl;
    @ApiModelProperty("未成功接入占比")
    String wcgjrzb;
    @ApiModelProperty("24小时接入登薄量")
    String jrdbl1;
    @ApiModelProperty("24小时接入登薄量(占比%)")
    String jrdbl1zb;
    @ApiModelProperty("48小时接入登薄量")
    String jrdbl2;
    @ApiModelProperty("48小时接入登薄量(占比%)")
    String jrdbl2zb;
    @ApiModelProperty("超过48小时接入登薄量")
    String jrdbl3;
    @ApiModelProperty("超过48小时接入登薄量(占比%)")
    String jrdbl3zb;
    @ApiModelProperty("综合评分")
    String df;
    @ApiModelProperty("区划级别")
    String qhjb;
    @ApiModelProperty("父代码")
    String fdm;
    @ApiModelProperty("区划代码")
    String qhdm;

    @ApiModelProperty("子集")
    List<SjzlpfResponseDTO> children;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getDbrzdbl() {
        return dbrzdbl;
    }

    public void setDbrzdbl(String dbrzdbl) {
        this.dbrzdbl = dbrzdbl;
    }

    public String getWscdbqxsl() {
        return wscdbqxsl;
    }

    public void setWscdbqxsl(String wscdbqxsl) {
        this.wscdbqxsl = wscdbqxsl;
    }

    public String getWscsjsl() {
        return wscsjsl;
    }

    public void setWscsjsl(String wscsjsl) {
        this.wscsjsl = wscsjsl;
    }

    public String getJrdbl1() {
        return jrdbl1;
    }

    public void setJrdbl1(String jrdbl1) {
        this.jrdbl1 = jrdbl1;
    }

    public String getJrdbl1zb() {
        return jrdbl1zb;
    }

    public void setJrdbl1zb(String jrdbl1zb) {
        this.jrdbl1zb = jrdbl1zb;
    }

    public String getJrdbl2() {
        return jrdbl2;
    }

    public void setJrdbl2(String jrdbl2) {
        this.jrdbl2 = jrdbl2;
    }

    public String getJrdbl2zb() {
        return jrdbl2zb;
    }

    public void setJrdbl2zb(String jrdbl2zb) {
        this.jrdbl2zb = jrdbl2zb;
    }

    public String getJrdbl3() {
        return jrdbl3;
    }

    public void setJrdbl3(String jrdbl3) {
        this.jrdbl3 = jrdbl3;
    }

    public String getJrdbl3zb() {
        return jrdbl3zb;
    }

    public void setJrdbl3zb(String jrdbl3zb) {
        this.jrdbl3zb = jrdbl3zb;
    }

    public String getQhjb() {
        return qhjb;
    }

    public void setQhjb(String qhjb) {
        this.qhjb = qhjb;
    }

    public String getFdm() {
        return fdm;
    }

    public void setFdm(String fdm) {
        this.fdm = fdm;
    }

    public String getQhmc() {
        return qhmc;
    }

    public void setQhmc(String qhmc) {
        this.qhmc = qhmc;
    }

    public String getJrywl() {
        return jrywl;
    }

    public void setJrywl(String jrywl) {
        this.jrywl = jrywl;
    }

    public String getJrdbl() {
        return jrdbl;
    }

    public void setJrdbl(String jrdbl) {
        this.jrdbl = jrdbl;
    }

    public String getWcgjrdbl() {
        return wcgjrdbl;
    }

    public void setWcgjrdbl(String wcgjrdbl) {
        this.wcgjrdbl = wcgjrdbl;
    }

    public String getWcgjrzb() {
        return wcgjrzb;
    }

    public void setWcgjrzb(String wcgjrzb) {
        this.wcgjrzb = wcgjrzb;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public String getQhdm() {
        return qhdm;
    }

    public void setQhdm(String qhdm) {
        this.qhdm = qhdm;
    }

    public List<SjzlpfResponseDTO> getChildren() {
        return children;
    }

    public void setChildren(List<SjzlpfResponseDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "SjzlpfResponseDTO{" +
                "xh='" + xh + '\'' +
                ", qhmc='" + qhmc + '\'' +
                ", jrywl='" + jrywl + '\'' +
                ", jrdbl='" + jrdbl + '\'' +
                ", dbrzdbl='" + dbrzdbl + '\'' +
                ", wscdbqxsl='" + wscdbqxsl + '\'' +
                ", wscsjsl='" + wscsjsl + '\'' +
                ", wcgjrdbl='" + wcgjrdbl + '\'' +
                ", wcgjrzb='" + wcgjrzb + '\'' +
                ", jrdbl1='" + jrdbl1 + '\'' +
                ", jrdbl1zb='" + jrdbl1zb + '\'' +
                ", jrdbl2='" + jrdbl2 + '\'' +
                ", jrdbl2zb='" + jrdbl2zb + '\'' +
                ", jrdbl3='" + jrdbl3 + '\'' +
                ", jrdbl3zb='" + jrdbl3zb + '\'' +
                ", df='" + df + '\'' +
                ", qhjb='" + qhjb + '\'' +
                ", fdm='" + fdm + '\'' +
                ", qhdm='" + qhdm + '\'' +
                ", children=" + children +
                '}';
    }
}
