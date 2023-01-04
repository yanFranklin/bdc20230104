package cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj;

import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.zydy.InitZyDyRequestQlrxx;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class InitCfjfdjRequestCfxxDTO {

    /**
     * cffw : 延安南路76号民政家园B楼2单元29层3号
     * cfjg : 蚌埠市蚌山区人民法院
     * cfjsrq :
     * cfksrq :
     * cflx : 3
     * cfwh : (2017)皖0303民初932号
     * cfwj : 协助执行通知书
     * djyydm : 6414431
     * djyymc : 续查封
     * fjxx : [{"cldm":"20210201","clmc":"人民法院工作人员的工作证和执行公务证","clnr":[],"fs":"1"},{"cldm":"20210202","clmc":"裁定书","clnr":[],"fs":"1"},{"cldm":"20210203","clmc":"协助执行通知书","clnr":[],"fs":"1"},{"cldm":"20210204","clmc":"人像","clnr":[],"fs":"1"}]
     * fysdrlxdh : 18075330562
     * qlrxx : [{"fczh":"2014021686","gybl":"","qlrlx":"qlr","qlrmc":"18625162058","qlrsfzjzl":"7","qlrzjh":"23309012222222","qlrzldm":"2","qlrzlmc":"2","sxh":"1"},{"fczh":"2014021686","gybl":"","gyfs":"0","qlrlx":"ywr","qlrmc":"闫志兰","qlrsfzjzl":"1","qlrzjh":"340321196912197680","qlrzldm":"1","qlrzlmc":"1","sxh":"1"}]
     * xmid : 1BNF4718A57UF190
     */

    @JsonProperty("cffw")
    private String cffw;
    @JsonProperty("cfjg")
    private String cfjg;
    @JsonProperty("cfjsrq")
    private String cfjsrq;
    @JsonProperty("cfksrq")
    private String cfksrq;
    @JsonProperty("cflx")
    private String cflx;
    @JsonProperty("cfwh")
    private String cfwh;
    @JsonProperty("cfwj")
    private String cfwj;
    @JsonProperty("djyydm")
    private String djyydm;
    @JsonProperty("djyymc")
    private String djyymc;
    @JsonProperty("fjxx")
//    private List<InitFjxxDTO> fjxx;
    private List<FjclDTOForZhlc> fjxx;

    @JsonProperty("fysdrlxdh")
    private String fysdrlxdh;
    @JsonProperty("qlrxx")
    private List<InitZyDyRequestQlrxx> qlrxx;
    @JsonProperty("xmid")
    private String xmid;
    @ApiModelProperty(value = "解封文号")
    private String jfwh;

    @ApiModelProperty(value = "解封机关")
    private String jfjg;

    @ApiModelProperty(value = "解封文件")
    private String jfwj;

    @ApiModelProperty(value = "解封原因")
    private String jfyy;

    @ApiModelProperty(value = "（法院）查封送达人")
    private String fysdr;

    @ApiModelProperty(value = "解封送达人联系电话")
    private String jfsdrlxdh;

    @ApiModelProperty(value = "解封送达人")
    private String jfsdr;

    @ApiModelProperty(value = "查封申请人")
    private String cfsqr;


    public String getCfsqr() {
        return cfsqr;
    }

    public void setCfsqr(String cfsqr) {
        this.cfsqr = cfsqr;
    }

    public String getFysdr() {
        return fysdr;
    }

    public void setFysdr(String fysdr) {
        this.fysdr = fysdr;
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

    public String getCffw() {
        return cffw;
    }

    public void setCffw(String cffw) {
        this.cffw = cffw;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCfjsrq() {
        return cfjsrq;
    }

    public void setCfjsrq(String cfjsrq) {
        this.cfjsrq = cfjsrq;
    }

    public String getCfksrq() {
        return cfksrq;
    }

    public void setCfksrq(String cfksrq) {
        this.cfksrq = cfksrq;
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

    public String getCfwj() {
        return cfwj;
    }

    public void setCfwj(String cfwj) {
        this.cfwj = cfwj;
    }

    public String getDjyydm() {
        return djyydm;
    }

    public void setDjyydm(String djyydm) {
        this.djyydm = djyydm;
    }

    public String getDjyymc() {
        return djyymc;
    }

    public void setDjyymc(String djyymc) {
        this.djyymc = djyymc;
    }

    public List<FjclDTOForZhlc> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<FjclDTOForZhlc> fjxx) {
        this.fjxx = fjxx;
    }

    public String getFysdrlxdh() {
        return fysdrlxdh;
    }

    public void setFysdrlxdh(String fysdrlxdh) {
        this.fysdrlxdh = fysdrlxdh;
    }

    public List<InitZyDyRequestQlrxx> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<InitZyDyRequestQlrxx> qlrxx) {
        this.qlrxx = qlrxx;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }


    @Override
    public String toString() {
        return "InitCfjfdjRequestCfxxDTO{" +
                "cffw='" + cffw + '\'' +
                ", cfjg='" + cfjg + '\'' +
                ", cfjsrq='" + cfjsrq + '\'' +
                ", cfksrq='" + cfksrq + '\'' +
                ", cflx='" + cflx + '\'' +
                ", cfwh='" + cfwh + '\'' +
                ", cfwj='" + cfwj + '\'' +
                ", djyydm='" + djyydm + '\'' +
                ", djyymc='" + djyymc + '\'' +
                ", fjxx=" + fjxx +
                ", fysdrlxdh='" + fysdrlxdh + '\'' +
                ", qlrxx=" + qlrxx +
                ", xmid='" + xmid + '\'' +
                ", jfwh='" + jfwh + '\'' +
                ", jfjg='" + jfjg + '\'' +
                ", jfwj='" + jfwj + '\'' +
                ", jfyy='" + jfyy + '\'' +
                ", fysdr='" + fysdr + '\'' +
                ", jfsdrlxdh='" + jfsdrlxdh + '\'' +
                ", jfsdr='" + jfsdr + '\'' +
                ", cfsqr='" + cfsqr + '\'' +
                '}';
    }
}
