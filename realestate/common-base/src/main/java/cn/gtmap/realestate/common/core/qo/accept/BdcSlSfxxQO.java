package cn.gtmap.realestate.common.core.qo.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/21
 * @description 不动产受理收费页面类
 */
public class BdcSlSfxxQO extends BdcSlSfxxDO implements Serializable {
    private static final long serialVersionUID = 5863737481731574423L;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "宗地宗海面积")
    private Double zdzhmj;
    @ApiModelProperty(value = "定着物面积")
    private Double dzwmj;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;
    @ApiModelProperty(value = "代理人联系电话")
    private String dlrlxdh;
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;
    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;
    @ApiModelProperty(value = "义务人名称")
    private String ywrmc;
    @ApiModelProperty(value = "发票号")
    private String fph;
    @ApiModelProperty(value = "缴费人姓名")
    private List<String> jfrxmList;
    @ApiModelProperty(value = "开始时间")
    private String kssj;
    @ApiModelProperty(value = "截至时间")
    private String jzsj;
    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;
    @ApiModelProperty(value = "开票人姓名")
    private String kprxm;
    @ApiModelProperty(value = "缴费银行")
    private String jfyh;
    @ApiModelProperty(value = "缴费书编码(缴费清单单号)")
    private String jfsbm;
    @ApiModelProperty(value = "登记部门代码列表")
    @JsonProperty(value = "DepartmentList")
    private List<String> djbmdmList;
    @ApiModelProperty(value = "收费信息id列表")
    @JsonProperty(value = "sfxxidList")
    private List<String> sfxxidList;
    @ApiModelProperty(value = "导出模式")
    private String exportType;
    @ApiModelProperty(value = "收费项目代码")
    private String sfxmdm;
    @ApiModelProperty(value = "结算凭证发票号是否为空")
    private Boolean jspzFphSfwk;
    @ApiModelProperty(value = "非税收入发票号是否为空")
    private Boolean fsFphSfwk;

    @ApiModelProperty(value = "xmid列表")
    @JsonProperty(value = "xmidList")
    private List<String> xmidList;

    @ApiModelProperty(value = "gzlslid列表")
    @JsonProperty(value = "gzlslidList")
    private List<String> gzlslidList;

    @ApiModelProperty(value = "查询类型")
    private String cxlx;

    @ApiModelProperty(value = "缴费人姓名")
    private String jfrxm;

    @ApiModelProperty(value = "受理编号集合")
    private List<String> slbhList;

    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;

    @ApiModelProperty(value = "抵押权人")
    private String dyaqr;

    @ApiModelProperty(value = "查询开始时间")
    private String cxqssj;

    @ApiModelProperty(value = "查询结束时间")
    private String cxjssj;

    @ApiModelProperty(value = "更新开始时间")
    private String gxqssj;

    @ApiModelProperty(value = "更新结束时间")
    private String gxjssj;

    @ApiModelProperty(value = "区县代码")
    private List<String> qxdmList;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    @ApiModelProperty(value = "权属状态集合")
    private List<Integer> qsztList;

    @ApiModelProperty(value = "抵押批量缴费流程查询条件：受理开始时间")
    private String slkssj;

    @ApiModelProperty(value = "抵押批量缴费流程查询条件：受理结束时间")
    private String sljssj;

    @ApiModelProperty(value = "查询模糊类型 0:精确,1:模糊")
    private String mhlx;

    @ApiModelProperty(value = "减免原因,不等于查询")
    private String jmyynot;

    public String getCxlx() {
        return cxlx;
    }

    public void setCxlx(String cxlx) {
        this.cxlx = cxlx;
    }

    public List<String> getSfxxidList() {
        return sfxxidList;
    }

    public void setSfxxidList(List<String> sfxxidList) {
        this.sfxxidList = sfxxidList;
    }

    @Override
    public String getJfsbm() {
        return jfsbm;
    }

    @Override
    public void setJfsbm(String jfsbm) {
        this.jfsbm = jfsbm;
    }

    public List<String> getDjbmdmList() {
        return djbmdmList;
    }

    public void setDjbmdmList(List<String> djbmdmList) {
        this.djbmdmList = djbmdmList;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
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

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public Double getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(Double dzwmj) {
        this.dzwmj = dzwmj;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getDlrlxdh() {
        return dlrlxdh;
    }

    public void setDlrlxdh(String dlrlxdh) {
        this.dlrlxdh = dlrlxdh;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String ywrmc) {
        this.ywrmc = ywrmc;
    }
	
	public String getJfyh() {
		return jfyh;
	}
	
	public void setJfyh(String jfyh) {
		this.jfyh = jfyh;
	}
	
	@Override
    public String getFph() {
        return fph;
    }

    @Override
    public void setFph(String fph) {
        this.fph = fph;
    }
	
	public List<String> getJfrxmList() {
		return jfrxmList;
	}
	
	public void setJfrxmList(List<String> jfrxmList) {
		this.jfrxmList = jfrxmList;
	}
	
	public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }
	
	@Override
	public String getKprxm() {
		return kprxm;
	}
	
	@Override
	public void setKprxm(String kprxm) {
		this.kprxm = kprxm;
	}

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getSfxmdm() {
        return sfxmdm;
    }

    public void setSfxmdm(String sfxmdm) {
        this.sfxmdm = sfxmdm;
    }

    public Boolean getJspzFphSfwk() {
        return jspzFphSfwk;
    }

    public void setJspzFphSfwk(Boolean jspzFphSfwk) {
        this.jspzFphSfwk = jspzFphSfwk;
    }

    public Boolean getFsFphSfwk() {
        return fsFphSfwk;
    }

    public void setFsFphSfwk(Boolean fsFphSfwk) {
        this.fsFphSfwk = fsFphSfwk;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public List<String> getGzlslidList() {
        return gzlslidList;
    }

    public void setGzlslidList(List<String> gzlslidList) {
        this.gzlslidList = gzlslidList;
    }

    @Override
    public String getJfrxm() {
        return jfrxm;
    }

    @Override
    public void setJfrxm(String jfrxm) {
        this.jfrxm = jfrxm;
    }

    public List<String> getSlbhList() {
        return slbhList;
    }

    public void setSlbhList(List<String> slbhList) {
        this.slbhList = slbhList;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    public String getDyaqr() {
        return dyaqr;
    }

    public void setDyaqr(String dyaqr) {
        this.dyaqr = dyaqr;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getCxqssj() {
        return cxqssj;
    }

    public void setCxqssj(String cxqssj) {
        this.cxqssj = cxqssj;
    }

    public String getCxjssj() {
        return cxjssj;
    }

    public void setCxjssj(String cxjssj) {
        this.cxjssj = cxjssj;
    }

    public String getGxqssj() {
        return gxqssj;
    }

    public void setGxqssj(String gxqssj) {
        this.gxqssj = gxqssj;
    }

    public String getGxjssj() {
        return gxjssj;
    }

    public void setGxjssj(String gxjssj) {
        this.gxjssj = gxjssj;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getMhlx() {
        return mhlx;
    }

    public void setMhlx(String mhlx) {
        this.mhlx = mhlx;
    }

    public List<Integer> getQsztList() {
        return qsztList;
    }

    public void setQsztList(List<Integer> qsztList) {
        this.qsztList = qsztList;
    }

    public String getSlkssj() {
        return slkssj;
    }

    public void setSlkssj(String slkssj) {
        this.slkssj = slkssj;
    }

    public String getSljssj() {
        return sljssj;
    }

    public void setSljssj(String sljssj) {
        this.sljssj = sljssj;
    }

    public String getJmyynot() {
        return jmyynot;
    }

    public void setJmyynot(String jmyynot) {
        this.jmyynot = jmyynot;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcSlSfxxQO{");
        sb.append("djxl='").append(djxl).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", zdzhmj=").append(zdzhmj);
        sb.append(", dzwmj=").append(dzwmj);
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", dlrmc='").append(dlrmc).append('\'');
        sb.append(", dlrlxdh='").append(dlrlxdh).append('\'');
        sb.append(", qllx=").append(qllx);
        sb.append(", qlrmc='").append(qlrmc).append('\'');
        sb.append(", qlrzjh='").append(qlrzjh).append('\'');
        sb.append(", ywrmc='").append(ywrmc).append('\'');
        sb.append(", fph='").append(fph).append('\'');
        sb.append(", jfrxmList=").append(jfrxmList);
        sb.append(", kssj='").append(kssj).append('\'');
        sb.append(", jzsj='").append(jzsj).append('\'');
        sb.append(", sfxmmc='").append(sfxmmc).append('\'');
        sb.append(", kprxm='").append(kprxm).append('\'');
        sb.append(", jfyh='").append(jfyh).append('\'');
        sb.append(", jfsbm='").append(jfsbm).append('\'');
        sb.append(", djbmdmList=").append(djbmdmList);
        sb.append(", sfxxidList=").append(sfxxidList);
        sb.append(", exportType='").append(exportType).append('\'');
        sb.append(", sfxmdm='").append(sfxmdm).append('\'');
        sb.append(", jspzFphSfwk=").append(jspzFphSfwk);
        sb.append(", fsFphSfwk=").append(fsFphSfwk);
        sb.append(", xmidList=").append(xmidList);
        sb.append(", gzlslidList=").append(gzlslidList);
        sb.append(", cxlx='").append(cxlx).append('\'');
        sb.append(", jfrxm='").append(jfrxm).append('\'');
        sb.append(", slbhList=").append(slbhList);
        sb.append(", dyfs=").append(dyfs);
        sb.append(", dyaqr='").append(dyaqr).append('\'');
        sb.append(", cxqssj='").append(cxqssj).append('\'');
        sb.append(", cxjssj='").append(cxjssj).append('\'');
        sb.append(", gxqssj='").append(gxqssj).append('\'');
        sb.append(", gxjssj='").append(gxjssj).append('\'');
        sb.append(", qxdmList=").append(qxdmList);
        sb.append(", qszt=").append(qszt);
        sb.append(", qsztList=").append(qsztList);
        sb.append(", slkssj='").append(slkssj).append('\'');
        sb.append(", sljssj='").append(sljssj).append('\'');
        sb.append(", mhlx='").append(mhlx).append('\'');
        sb.append(", jmyynot='").append(jmyynot).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
