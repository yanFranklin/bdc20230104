package cn.gtmap.realestate.exchange.core.dto.nantong.sw.tsswxx.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-06-11
 * @description 南通如东推送存量房税务信息权利人实体
 */
public class RdTsswxxQlrResponseDTO {

    @ApiModelProperty("配偶姓名")
    private String poxm;
    @ApiModelProperty("配偶证件号")
    private String pozjhm;
    @ApiModelProperty("配偶国籍代码")
    private String pogjdm;
    @ApiModelProperty("配偶证件类型")
    private String pozjlxdm;
    @ApiModelProperty("家庭成员套次信息")
    private String jtcytcxx;

    @ApiModelProperty("纳税人名称")
    private String nsrmc;
    @ApiModelProperty("证件号码")
    private String zjhm;
    @ApiModelProperty("家庭成员套次信息")
    private String dh;
    @ApiModelProperty("证件类型代码")
    private String zjlxdm;
    @ApiModelProperty("家庭成员套次信息")
    private String nsrdz;

    @ApiModelProperty("所占份额")
    private String szfe;
    @ApiModelProperty("变动份额")
    private String bdfe2;
    @ApiModelProperty("国籍代码")
    private String gjdm;
    @ApiModelProperty("上次取得房屋方式")
    private String scqdfwfs;
    @ApiModelProperty("上次取得房屋时间")
    private String scqdfwsj;
    @ApiModelProperty("上次取得房屋成本")
    private String scqdfwcb;

    @ApiModelProperty("婚姻标志")
    private String hybz;
    @ApiModelProperty("卖方共有方式")
    private String zrfgyfsdm;
    @ApiModelProperty("自然人标志")
    private String zrrbz;

    @ApiModelProperty("主权人标志")
    private String zqrbz;

    @ApiModelProperty("未成年子女")
    private List<RdTsswxxWcnzn> wcnzn;

    private String bz;

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public List<RdTsswxxWcnzn> getWcnzn() {
        return wcnzn;
    }

    public void setWcnzn(List<RdTsswxxWcnzn> wcnzn) {
        this.wcnzn = wcnzn;
    }


    public String getPoxm() {
        return poxm;
    }

    public void setPoxm(String poxm) {
        this.poxm = poxm;
    }

    public String getPozjhm() {
        return pozjhm;
    }

    public void setPozjhm(String pozjhm) {
        this.pozjhm = pozjhm;
    }

    public String getPogjdm() {
        return pogjdm;
    }

    public void setPogjdm(String pogjdm) {
        this.pogjdm = pogjdm;
    }

    public String getPozjlxdm() {
        return pozjlxdm;
    }

    public void setPozjlxdm(String pozjlxdm) {
        this.pozjlxdm = pozjlxdm;
    }

    public String getJtcytcxx() {
        return jtcytcxx;
    }

    public void setJtcytcxx(String jtcytcxx) {
        this.jtcytcxx = jtcytcxx;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getZjlxdm() {
        return zjlxdm;
    }

    public void setZjlxdm(String zjlxdm) {
        this.zjlxdm = zjlxdm;
    }

    public String getNsrdz() {
        return nsrdz;
    }

    public void setNsrdz(String nsrdz) {
        this.nsrdz = nsrdz;
    }

    public String getSzfe() {
        return szfe;
    }

    public void setSzfe(String szfe) {
        this.szfe = szfe;
    }

    public String getBdfe2() {
        return bdfe2;
    }

    public void setBdfe2(String bdfe2) {
        this.bdfe2 = bdfe2;
    }

    public String getGjdm() {
        return gjdm;
    }

    public void setGjdm(String gjdm) {
        this.gjdm = gjdm;
    }

    public String getScqdfwfs() {
        return scqdfwfs;
    }

    public void setScqdfwfs(String scqdfwfs) {
        this.scqdfwfs = scqdfwfs;
    }

    public String getScqdfwsj() {
        return scqdfwsj;
    }

    public void setScqdfwsj(String scqdfwsj) {
        this.scqdfwsj = scqdfwsj;
    }

    public String getScqdfwcb() {
        return scqdfwcb;
    }

    public void setScqdfwcb(String scqdfwcb) {
        this.scqdfwcb = scqdfwcb;
    }

    public String getHybz() {
        return hybz;
    }

    public void setHybz(String hybz) {
        this.hybz = hybz;
    }

    public String getZrfgyfsdm() {
        return zrfgyfsdm;
    }

    public void setZrfgyfsdm(String zrfgyfsdm) {
        this.zrfgyfsdm = zrfgyfsdm;
    }

    public String getZrrbz() {
        return zrrbz;
    }

    public void setZrrbz(String zrrbz) {
        this.zrrbz = zrrbz;
    }

    public String getZqrbz() {
        return zqrbz;
    }

    public void setZqrbz(String zqrbz) {
        this.zqrbz = zqrbz;
    }

    @Override
    public String toString() {
        return "RdTsswxxQlrResponseDTO{" +
                "poxm='" + poxm + '\'' +
                ", pozjhm='" + pozjhm + '\'' +
                ", pogjdm='" + pogjdm + '\'' +
                ", pozjlxdm='" + pozjlxdm + '\'' +
                ", jtcytcxx='" + jtcytcxx + '\'' +
                ", nsrmc='" + nsrmc + '\'' +
                ", zjhm='" + zjhm + '\'' +
                ", dh='" + dh + '\'' +
                ", zjlxdm='" + zjlxdm + '\'' +
                ", nsrdz='" + nsrdz + '\'' +
                ", szfe='" + szfe + '\'' +
                ", bdfe2='" + bdfe2 + '\'' +
                ", gjdm='" + gjdm + '\'' +
                ", scqdfwfs='" + scqdfwfs + '\'' +
                ", scqdfwsj='" + scqdfwsj + '\'' +
                ", scqdfwcb=" + scqdfwcb +
                ", hybz='" + hybz + '\'' +
                ", zrfgyfsdm='" + zrfgyfsdm + '\'' +
                ", zrrbz='" + zrrbz + '\'' +
                ", zqrbz='" + zqrbz + '\'' +
                ", wcnzn=" + wcnzn +
                ", bz='" + bz + '\'' +
                '}';
    }
}
