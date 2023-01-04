package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 宣城摩科评价器推送附件签名实体
 * @author: <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @create: 2022-12-12 11:11
 **/
@ApiModel(value = "BdcMkqmDTO", description = "宣城摩科评价器")
public class BdcMkqmDTO implements Serializable {
    @ApiModelProperty(value = "系统的本地Ip")
    private String sysIp;

    @ApiModelProperty(value = "附件类型（1.不动产登记申请书；2.买卖合同；3.询问笔录；4.发证记录；5. 领不动产登记收费单；6. 不动产登记税务统一缴纳通知单；7.不动产登记资料查询申请书）")
    private String fjlx;

    @ApiModelProperty(value = "受理号")
    private String slid;

    @ApiModelProperty(value = "文件的Pdf表格转换的base64码")
    private String tablePdf;

    @ApiModelProperty(value = "是否开启静默打印")
    private String hidemodel;

    @ApiModelProperty(value = "权利人申请人名称")
    private List<String> sq_qlrsqr;

    @ApiModelProperty(value = "权利人代理人名称")
    private List<String> sq_qlrdlr;

    @ApiModelProperty(value = "义务人申请人名称")
    private List<String> sq_ywrsqr;

    @ApiModelProperty(value = "义务人代理人名称")
    private List<String> sq_ywrdlr;

    @ApiModelProperty(value = "合同转让人（甲方）")
    private List<String> ht_jf;

    @ApiModelProperty(value = "合同转让人（乙方）")
    private List<String> ht_yf;

    @ApiModelProperty(value = "询问笔录被询问人")
    private List<String> xwbl_bxwr;

    @ApiModelProperty(value = "发证记录领证人")
    private List<String> fzjl_lzr;

    @ApiModelProperty(value = "查询申请人")
    private List<String> cxsqr;

    public String getSysIp() {
        return sysIp;
    }

    public void setSysIp(String sysIp) {
        this.sysIp = sysIp;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getSlid() {
        return slid;
    }

    public void setSlid(String slid) {
        this.slid = slid;
    }

    public String getTablePdf() {
        return tablePdf;
    }

    public void setTablePdf(String tablePdf) {
        this.tablePdf = tablePdf;
    }

    public String getHidemodel() {
        return hidemodel;
    }

    public void setHidemodel(String hidemodel) {
        this.hidemodel = hidemodel;
    }

    public List<String> getSq_qlrsqr() {
        return sq_qlrsqr;
    }

    public void setSq_qlrsqr(List<String> sq_qlrsqr) {
        this.sq_qlrsqr = sq_qlrsqr;
    }

    public List<String> getSq_qlrdlr() {
        return sq_qlrdlr;
    }

    public void setSq_qlrdlr(List<String> sq_qlrdlr) {
        this.sq_qlrdlr = sq_qlrdlr;
    }

    public List<String> getSq_ywrsqr() {
        return sq_ywrsqr;
    }

    public void setSq_ywrsqr(List<String> sq_ywrsqr) {
        this.sq_ywrsqr = sq_ywrsqr;
    }

    public List<String> getSq_ywrdlr() {
        return sq_ywrdlr;
    }

    public void setSq_ywrdlr(List<String> sq_ywrdlr) {
        this.sq_ywrdlr = sq_ywrdlr;
    }

    public List<String> getHt_jf() {
        return ht_jf;
    }

    public void setHt_jf(List<String> ht_jf) {
        this.ht_jf = ht_jf;
    }

    public List<String> getHt_yf() {
        return ht_yf;
    }

    public void setHt_yf(List<String> ht_yf) {
        this.ht_yf = ht_yf;
    }

    public List<String> getXwbl_bxwr() {
        return xwbl_bxwr;
    }

    public void setXwbl_bxwr(List<String> xwbl_bxwr) {
        this.xwbl_bxwr = xwbl_bxwr;
    }

    public List<String> getFzjl_lzr() {
        return fzjl_lzr;
    }

    public void setFzjl_lzr(List<String> fzjl_lzr) {
        this.fzjl_lzr = fzjl_lzr;
    }

    public List<String> getCxsqr() {
        return cxsqr;
    }

    public void setCxsqr(List<String> cxsqr) {
        this.cxsqr = cxsqr;
    }

    @Override
    public String toString() {
        return "BdcMkqmDTO{" +
                "sysIp='" + sysIp + '\'' +
                ", fjlx='" + fjlx + '\'' +
                ", slid='" + slid + '\'' +
                ", tablePdf='" + tablePdf + '\'' +
                ", hidemodel='" + hidemodel + '\'' +
                ", sq_qlrsqr=" + sq_qlrsqr +
                ", sq_qlrdlr=" + sq_qlrdlr +
                ", sq_ywrsqr=" + sq_ywrsqr +
                ", sq_ywrdlr=" + sq_ywrdlr +
                ", ht_jf=" + ht_jf +
                ", ht_yf=" + ht_yf +
                ", xwbl_bxwr=" + xwbl_bxwr +
                ", fzjl_lzr=" + fzjl_lzr +
                ", cxsqr=" + cxsqr +
                '}';
    }
}
