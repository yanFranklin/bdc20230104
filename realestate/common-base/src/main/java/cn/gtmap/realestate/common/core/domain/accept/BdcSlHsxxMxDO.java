package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/11
 * @description 不动产受理核税信息明细
 */
@Table(name = "BDC_SL_HSXX_MX")
@ApiModel(value = "BdcSlHsxxMxDO", description = "不动产受理核税信息明细")
public class BdcSlHsxxMxDO {

    @Id
    @ApiModelProperty(value = "核税信息明细ID")
    private String hsxxmxid;

    @ApiModelProperty(value = "核税信息ID")
    private String hsxxid;

    @ApiModelProperty(value = "明细种类")
    private String mxzl;

    @ApiModelProperty(value = "应纳税额")
    private Double ynse;

    @ApiModelProperty(value = "减免税额")
    private Double jmse;

    @ApiModelProperty(value = "实际纳税额")
    private Double sjnse;

    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    @ApiModelProperty(value = "纳税人名称")
    private String nsrmc;

    @ApiModelProperty(value = "征税项目")
    private String zsxm;

    @ApiModelProperty(value = "计税依据")
    private Double jsyj;

    @ApiModelProperty(value = "税率")
    private String sl;

    @ApiModelProperty(value = "拆迁补偿折算减免税款")
    private Double cqbczsjmsk;

    @ApiModelProperty(value = "增值税小规模纳税人减征比例")
    private String zzsxgmnsrjzbl;

    @ApiModelProperty(value = "增值税小规模纳税人减征额")
    private Double zzsxgmnsrjze;

    @ApiModelProperty(value = "课税数量")
    private Double kssl;

    @ApiModelProperty(value = "税款所属时期起始时间")
    private Date sksssqqssj;

    @ApiModelProperty(value = "税款所属时期结束时间")
    private Date sksssqjssj;

    @ApiModelProperty("应税项")
    private Double ysx;

    @ApiModelProperty("减除项")
    private Double jcx;

    @ApiModelProperty("应补（退）税额")
    private Double ybtse;

    @ApiModelProperty("纳税人识别号")
    private String nsrsbh;

    @ApiModelProperty("完税附件类型")
    private String fjlx;

    @ApiModelProperty("完税附件ID")
    private String fjid;

    @ApiModelProperty("房屋编码")
    private String fwuuid;

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }

    public String getHsxxmxid() {
        return hsxxmxid;
    }

    public void setHsxxmxid(String hsxxmxid) {
        this.hsxxmxid = hsxxmxid;
    }

    public String getHsxxid() {
        return hsxxid;
    }

    public void setHsxxid(String hsxxid) {
        this.hsxxid = hsxxid;
    }

    public String getMxzl() {
        return mxzl;
    }

    public void setMxzl(String mxzl) {
        this.mxzl = mxzl;
    }

    public Double getYnse() {
        return ynse;
    }

    public void setYnse(Double ynse) {
        this.ynse = ynse;
    }

    public Double getJmse() {
        return jmse;
    }

    public void setJmse(Double jmse) {
        this.jmse = jmse;
    }

    public Double getSjnse() {
        return sjnse;
    }

    public void setSjnse(Double sjnse) {
        this.sjnse = sjnse;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getZsxm() {
        return zsxm;
    }

    public void setZsxm(String zsxm) {
        this.zsxm = zsxm;
    }

    public Double getJsyj() {
        return jsyj;
    }

    public void setJsyj(Double jsyj) {
        this.jsyj = jsyj;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public Double getCqbczsjmsk() {
        return cqbczsjmsk;
    }

    public void setCqbczsjmsk(Double cqbczsjmsk) {
        this.cqbczsjmsk = cqbczsjmsk;
    }

    public String getZzsxgmnsrjzbl() {
        return zzsxgmnsrjzbl;
    }

    public void setZzsxgmnsrjzbl(String zzsxgmnsrjzbl) {
        this.zzsxgmnsrjzbl = zzsxgmnsrjzbl;
    }

    public Double getZzsxgmnsrjze() {
        return zzsxgmnsrjze;
    }

    public void setZzsxgmnsrjze(Double zzsxgmnsrjze) {
        this.zzsxgmnsrjze = zzsxgmnsrjze;
    }

    public Double getKssl() {
        return kssl;
    }

    public void setKssl(Double kssl) {
        this.kssl = kssl;
    }

    public Date getSksssqqssj() {
        return sksssqqssj;
    }

    public void setSksssqqssj(Date sksssqqssj) {
        this.sksssqqssj = sksssqqssj;
    }

    public Date getSksssqjssj() {
        return sksssqjssj;
    }

    public void setSksssqjssj(Date sksssqjssj) {
        this.sksssqjssj = sksssqjssj;
    }

    public Double getYsx() {
        return ysx;
    }

    public void setYsx(Double ysx) {
        this.ysx = ysx;
    }

    public Double getJcx() {
        return jcx;
    }

    public void setJcx(Double jcx) {
        this.jcx = jcx;
    }

    public Double getYbtse() {
        return ybtse;
    }

    public void setYbtse(Double ybtse) {
        this.ybtse = ybtse;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }

    @Override
    public String toString() {
        return "BdcSlHsxxMxDO{" +
                "hsxxmxid='" + hsxxmxid + '\'' +
                ", hsxxid='" + hsxxid + '\'' +
                ", mxzl='" + mxzl + '\'' +
                ", ynse=" + ynse +
                ", jmse=" + jmse +
                ", sjnse=" + sjnse +
                ", sxh=" + sxh +
                ", nsrmc='" + nsrmc + '\'' +
                ", zsxm='" + zsxm + '\'' +
                ", jsyj=" + jsyj +
                ", sl='" + sl + '\'' +
                ", cqbczsjmsk=" + cqbczsjmsk +
                ", zzsxgmnsrjzbl='" + zzsxgmnsrjzbl + '\'' +
                ", zzsxgmnsrjze=" + zzsxgmnsrjze +
                ", kssl=" + kssl +
                ", sksssqqssj=" + sksssqqssj +
                ", sksssqjssj=" + sksssqjssj +
                ", ysx=" + ysx +
                ", jcx=" + jcx +
                ", ybtse=" + ybtse +
                ", nsrsbh='" + nsrsbh + '\'' +
                ", fjlx='" + fjlx + '\'' +
                ", fjid='" + fjid + '\'' +
                ", fwuuid='" + fwuuid + '\'' +
                '}';
    }
}
