package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/5/2 11:30
 */
@Table(name = "BDC_XT_JG")
public class BdcXtJgDO {
    /**
     * 机构id
     */
    @Id
    @ApiModelProperty(value = "机构id")
    private String jgid;

    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称")
    private String jgmc;

    /**
     * 机构证件种类
     */
    @ApiModelProperty(value = "机构证件种类")
    private Integer jgzjzl;

    /**
     * 机构证件编号
     */
    @ApiModelProperty(value = "机构证件编号")
    private String jgzjbh;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    /**
     * 机构类别
     */
    @ApiModelProperty(value = "机构类别 1:银行 2：法院 3：小微企业")
    private Integer jglb;

    /**
     * 是否按月结算
     */
    @ApiModelProperty(value = "是否按月结算")
    private String sfayjs;

    /**
     * 法人名称
     */
    @ApiModelProperty(value = "法人名称")
    private String frmc;

    /**
     * 法人证件种类
     */
    @ApiModelProperty(value = "法人证件种类")
    private String frzjzl;

    /**
     * 法人证件号
     */
    @ApiModelProperty(value = "法人证件号")
    private String frzjh;

    /**
     * 法人电话
     */
    @ApiModelProperty(value = "法人电话")
    private String frdh;

    /**
     * 代理人名称
     */
    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;

    /**
     * 代理人电话
     */
    @ApiModelProperty(value = "代理人电话")
    private String dlrdh;

    /**
     * 代理人证件种类
     */
    @ApiModelProperty(value = "代理人证件种类")
    private String dlrzjzl;

    /**
     * 代理人证件号
     */
    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;

    /**
     * 是否接入互联网
     */
    @ApiModelProperty(value = "是否接入互联网")
    private String sfjrhlw;

    /**
     * 是否可用 1：可用 0：不可用
     */
    @ApiModelProperty(value = "是否可用")
    private String sfky;

    /**
     * 是否协议机构 1：是 0：否
     */
    @ApiModelProperty(value = "是否协议机构")
    private Integer sfxyjg;

    /**
     * 是否备案机构 1：是 0：否
     */
    @ApiModelProperty(value = "是否备案机构")
    private Integer sfbajg;

    /**
     * 是否打证 1：是 0：否
     */
    @ApiModelProperty(value = "是否打证")
    private Integer sfdz;

    @ApiModelProperty(value = "文件中心ID")
    private String wjzxid;

    @ApiModelProperty(value = "年份")
    private String nf;

    @ApiModelProperty(value = "权利人类型")
    private Integer qlrlx;

    public String getJgid() {
        return jgid;
    }

    public void setJgid(String jgid) {
        this.jgid = jgid;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public Integer getJgzjzl() {
        return jgzjzl;
    }

    public void setJgzjzl(Integer jgzjzl) {
        this.jgzjzl = jgzjzl;
    }

    public String getJgzjbh() {
        return jgzjbh;
    }

    public void setJgzjbh(String jgzjbh) {
        this.jgzjbh = jgzjbh;
    }

    public Integer getJglb() {
        return jglb;
    }

    public void setJglb(Integer jglb) {
        this.jglb = jglb;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSfayjs() {
        return sfayjs;
    }

    public void setSfayjs(String sfayjs) {
        this.sfayjs = sfayjs;
    }

    public String getFrmc() {
        return frmc;
    }

    public void setFrmc(String frmc) {
        this.frmc = frmc;
    }

    public String getFrzjzl() {
        return frzjzl;
    }

    public void setFrzjzl(String frzjzl) {
        this.frzjzl = frzjzl;
    }

    public String getFrzjh() {
        return frzjh;
    }

    public void setFrzjh(String frzjh) {
        this.frzjh = frzjh;
    }

    public String getFrdh() {
        return frdh;
    }

    public void setFrdh(String frdh) {
        this.frdh = frdh;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getDlrdh() {
        return dlrdh;
    }

    public void setDlrdh(String dlrdh) {
        this.dlrdh = dlrdh;
    }

    public String getDlrzjzl() {
        return dlrzjzl;
    }

    public void setDlrzjzl(String dlrzjzl) {
        this.dlrzjzl = dlrzjzl;
    }

    public String getDlrzjh() {
        return dlrzjh;
    }

    public void setDlrzjh(String dlrzjh) {
        this.dlrzjh = dlrzjh;
    }

    public String getSfjrhlw() {
        return sfjrhlw;
    }

    public void setSfjrhlw(String sfjrhlw) {
        this.sfjrhlw = sfjrhlw;
    }

    public String getSfky() {
        return sfky;
    }

    public void setSfky(String sfky) {
        this.sfky = sfky;
    }

    public String getWjzxid() {
        return wjzxid;
    }

    public void setWjzxid(String wjzxid) {
        this.wjzxid = wjzxid;
    }

    public Integer getSfxyjg() {
        return sfxyjg;
    }

    public void setSfxyjg(Integer sfxyjg) {
        this.sfxyjg = sfxyjg;
    }

    public Integer getSfbajg() {
        return sfbajg;
    }

    public void setSfbajg(Integer sfbajg) {
        this.sfbajg = sfbajg;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public Integer getSfdz() {
        return sfdz;
    }

    public void setSfdz(Integer sfdz) {
        this.sfdz = sfdz;
    }

    public Integer getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(Integer qlrlx) {
        this.qlrlx = qlrlx;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcXtJgDO.class.getSimpleName() + "[", "]")
                .add("jgid='" + jgid + "'")
                .add("jgmc='" + jgmc + "'")
                .add("jgzjzl=" + jgzjzl)
                .add("jgzjbh='" + jgzjbh + "'")
                .add("bz='" + bz + "'")
                .add("jglb=" + jglb)
                .add("sfayjs='" + sfayjs + "'")
                .add("frmc='" + frmc + "'")
                .add("frzjzl='" + frzjzl + "'")
                .add("frzjh='" + frzjh + "'")
                .add("frdh='" + frdh + "'")
                .add("dlrmc='" + dlrmc + "'")
                .add("dlrdh='" + dlrdh + "'")
                .add("dlrzjzl='" + dlrzjzl + "'")
                .add("dlrzjh='" + dlrzjh + "'")
                .add("sfjrhlw='" + sfjrhlw + "'")
                .add("sfky='" + sfky + "'")
                .add("sfxyjg=" + sfxyjg)
                .add("sfbajg=" + sfbajg)
                .add("wjzxid='" + wjzxid + "'")
                .add("nf='" + nf + "'")
                .add("sfdz='" + sfdz + "'")
                .toString();
    }
}
