package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 实体类｛@code BdcSlYjxxDO｝ 邮寄信息DO
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/12.
 * @description <p>不动产受理邮寄信息DO</p>
 */
@Table(name = "BDC_SL_YJXX")
@ApiModel(value ="BdcSlYjxxDO", description = "不动产受理邮寄信息DO")
public class BdcSlYjxxDO {

    @Id
    @ApiModelProperty(value = "邮寄信息id")
    private String yjxxid;
    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "收件人名称")
    private String sjrmc;
    @ApiModelProperty(value = "收件人邮编")
    private String sjryb;
    @ApiModelProperty(value = "收件人证件号")
    private String sjrzjh;
    @ApiModelProperty(value = "收件人联系方式")
    private String sjrlxdh;
    @ApiModelProperty(value = "收件人详细地址")
    private String sjrxxdz;
    @ApiModelProperty(value = "收件人所在省")
    private String sjrszp;
    @ApiModelProperty(value = "收件人所在市")
    private String sjrszc;
    @ApiModelProperty(value = "收件人所在县")
    private String sjrszx;
    @ApiModelProperty(value = "寄件人名称")
    private String jjrmc;
    @ApiModelProperty(value = "寄件人邮编")
    private String jjryb;
    @ApiModelProperty(value = "寄件人联系电话")
    private String jjrlxdh;
    @ApiModelProperty(value = "寄件人详细地址")
    private String jjrxxdz;
    @ApiModelProperty(value = "寄件人所在省")
    private String jjrszp;
    @ApiModelProperty(value = "寄件人所在市")
    private String jjrszc;
    @ApiModelProperty(value = "寄件人所在县")
    private String jjrszx;
    @ApiModelProperty(value = "推送EMS状态")
    private Integer tszt;
    @ApiModelProperty(value = "推送时间")
    private Date tssj;
    @ApiModelProperty(value = "物流订单号")
    private String wlddh;
    @ApiModelProperty(value = "物流运单号")
    private String wlydh;
    @ApiModelProperty(value = "区县代码")
    @Transient
    private String qxdm;
    @ApiModelProperty(value = "电子邮箱")
    private String dzyx;

    public String getDzyx() {
        return dzyx;
    }

    public void setDzyx(String dzyx) {
        this.dzyx = dzyx;
    }

    public String getYjxxid() {
        return yjxxid;
    }

    public void setYjxxid(String yjxxid) {
        this.yjxxid = yjxxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSjrmc() {
        return sjrmc;
    }

    public void setSjrmc(String sjrmc) {
        this.sjrmc = sjrmc;
    }

    public String getSjryb() {
        return sjryb;
    }

    public void setSjryb(String sjryb) {
        this.sjryb = sjryb;
    }

    public String getSjrlxdh() {
        return sjrlxdh;
    }

    public void setSjrlxdh(String sjrlxdh) {
        this.sjrlxdh = sjrlxdh;
    }

    public String getSjrxxdz() {
        return sjrxxdz;
    }

    public void setSjrxxdz(String sjrxxdz) {
        this.sjrxxdz = sjrxxdz;
    }

    public String getSjrszp() {
        return sjrszp;
    }

    public void setSjrszp(String sjrszp) {
        this.sjrszp = sjrszp;
    }

    public String getSjrszc() {
        return sjrszc;
    }

    public void setSjrszc(String sjrszc) {
        this.sjrszc = sjrszc;
    }

    public String getSjrszx() {
        return sjrszx;
    }

    public void setSjrszx(String sjrszx) {
        this.sjrszx = sjrszx;
    }

    public String getJjrmc() {
        return jjrmc;
    }

    public void setJjrmc(String jjrmc) {
        this.jjrmc = jjrmc;
    }

    public String getJjryb() {
        return jjryb;
    }

    public void setJjryb(String jjryb) {
        this.jjryb = jjryb;
    }

    public String getJjrlxdh() {
        return jjrlxdh;
    }

    public void setJjrlxdh(String jjrlxdh) {
        this.jjrlxdh = jjrlxdh;
    }

    public String getJjrxxdz() {
        return jjrxxdz;
    }

    public void setJjrxxdz(String jjrxxdz) {
        this.jjrxxdz = jjrxxdz;
    }

    public String getJjrszp() {
        return jjrszp;
    }

    public void setJjrszp(String jjrszp) {
        this.jjrszp = jjrszp;
    }

    public String getJjrszc() {
        return jjrszc;
    }

    public void setJjrszc(String jjrszc) {
        this.jjrszc = jjrszc;
    }

    public String getJjrszx() {
        return jjrszx;
    }

    public void setJjrszx(String jjrszx) {
        this.jjrszx = jjrszx;
    }

    public Integer getTszt() {
        return tszt;
    }

    public void setTszt(Integer tszt) {
        this.tszt = tszt;
    }

    public Date getTssj() {
        return tssj;
    }

    public void setTssj(Date tssj) {
        this.tssj = tssj;
    }

    public String getWlddh() {
        return wlddh;
    }

    public void setWlddh(String wlddh) {
        this.wlddh = wlddh;
    }

    public String getWlydh() {
        return wlydh;
    }

    public void setWlydh(String wlydh) {
        this.wlydh = wlydh;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getSjrzjh() {
        return sjrzjh;
    }

    public void setSjrzjh(String sjrzjh) {
        this.sjrzjh = sjrzjh;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    @Override
    public String toString() {
        return "BdcSlYjxxDO{" +
                "yjxxid='" + yjxxid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", djxl='" + djxl + '\'' +
                ", sjrmc='" + sjrmc + '\'' +
                ", sjryb='" + sjryb + '\'' +
                ", sjrzjh='" + sjrzjh + '\'' +
                ", sjrlxdh='" + sjrlxdh + '\'' +
                ", sjrxxdz='" + sjrxxdz + '\'' +
                ", sjrszp='" + sjrszp + '\'' +
                ", sjrszc='" + sjrszc + '\'' +
                ", sjrszx='" + sjrszx + '\'' +
                ", jjrmc='" + jjrmc + '\'' +
                ", jjryb='" + jjryb + '\'' +
                ", jjrlxdh='" + jjrlxdh + '\'' +
                ", jjrxxdz='" + jjrxxdz + '\'' +
                ", jjrszp='" + jjrszp + '\'' +
                ", jjrszc='" + jjrszc + '\'' +
                ", jjrszx='" + jjrszx + '\'' +
                ", tszt=" + tszt +
                ", tssj=" + tssj +
                ", wlddh='" + wlddh + '\'' +
                ", wlydh='" + wlydh + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", dzyx='" + dzyx + '\'' +
                '}';
    }

    public BdcSlYjxxDO withSlbh(final String slbh){
        this.setSlbh(slbh);
        return this;
    }
    public BdcSlYjxxDO withGzlslid(final String gzlslid){
        this.setGzlslid(gzlslid);
        return this;
    }
    public BdcSlYjxxDO withQxdm(final String qxdm){
        this.setQxdm(qxdm);
        return this;
    }

    public BdcSlYjxxDO withSjrxx(final String sjrmc, final String sjrlxdh,
                                 final String sjryb, final String sjrxxdz){
        this.setSjrmc(sjrmc);
        this.setSjrlxdh(sjrlxdh);
        this.setSjryb(sjryb);
        this.setSjrxxdz(sjrxxdz);
        return this;
    }

}
