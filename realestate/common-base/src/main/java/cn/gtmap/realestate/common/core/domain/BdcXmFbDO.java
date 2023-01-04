package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/10.
 * @description 不动产项目附表
 */
@Table(name = "BDC_XMFB")
@ApiModel(value = "BdcXmFbDO",description = "不动产项目附表")
public class BdcXmFbDO {
    @Id
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @Zd(tableClass = BdcZdZdsylxDO.class)
    @ApiModelProperty(value = "宗地所有类型")
    private Integer zdsylx;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "是否省直房改  0：否，1：是")
    private Integer sfszfgf;

    @ApiModelProperty(value = "权利拆分标示")
    private String  qlcfbs;

    @ApiModelProperty(value = "手动填写权利其他状况")
    private String qlqtzk;

    @ApiModelProperty(value = "手动填写附记")
    private String fj;

    @ApiModelProperty(value = "收费用途")
    private Integer sfyt;

    @ApiModelProperty(value = "地段级别")
    private Integer ddjb;

    @ApiModelProperty(value = "施工编号")
    private String fwsgbh;

    @ApiModelProperty(value = "是否代入交易信息")
    private Integer sfdrjyxx;

    @ApiModelProperty(value = "合肥特殊需求,用于政务关联项目")
    private String unid;

    @ApiModelProperty(value = "交地即发证 0：否，1：是")
    private Integer jdjfz;

    @ApiModelProperty(value = "使用期限")
    private String syqx;

    @ApiModelProperty(value = "是否按户登记")
    private Integer sfahdj;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty(value = "是否回收证书")
    private Integer sfhszs;

    @ApiModelProperty(value = "是否公告")
    private Integer sfgg;

    @ApiModelProperty(value = "产权来源")
    private String cqly;

    @ApiModelProperty(value = "是否资金监管")
    private Integer sfzjjg;

    @ApiModelProperty("是否共用宗")
    private Integer sfgyz;

    @ApiModelProperty(value = "缮证事项")
    private String szsx;

    @Zd(tableClass = BdcZdLzfsDO.class)
    @ApiModelProperty("领证方式")
    private Integer lzfs;

    @ApiModelProperty("发证意见")
    private String fzyj;

    @ApiModelProperty("是否超占")
    private Integer sfcz;

    @ApiModelProperty("超占面积")
    private Double czmj;

    @ApiModelProperty("有偿使用期限")
    private String ycsyqx;

    @ApiModelProperty("是否空户继承")
    private Integer sfkhjc;

    @ApiModelProperty("证书认领状态")
    private Integer zsrlzt;

    @ApiModelProperty("人脸识别图片文件id")
    private String xczpimg;

    @ApiModelProperty("土地使用权份额")
    private Double tdsyqfe;

    @ApiModelProperty(value = "是否最后一次量化")
    private Integer sfzhyclh;

    @ApiModelProperty(value = "预售许可证号")
    private String ysxkzh;

    @ApiModelProperty(value = "规划许可证号")
    private String ghxkzh;

    @ApiModelProperty(value = "规划验收证明编号")
    private String ghyszmbh;

    @ApiModelProperty(value = "规划验收日期")
    private Date ghyssj;

    @ApiModelProperty(value = "竣工验收备案编号")
    private String jgysbabh;

    @ApiModelProperty(value = "竣工验收备案时间")
    private Date jgysbasj;

    @ApiModelProperty(value = "上报情况")
    private Integer sbqk;

    @ApiModelProperty(value = "存量数据使用期限")
    private String clsjsyqx;

    @ApiModelProperty(value = "证书形态 1：电子 2：纸质")
    private Integer zsxt;

    @ApiModelProperty(value = "是否属于土地二级市场范畴 0：否 1：是 2：无")
    private Integer sftdejscfc;

    @ApiModelProperty(value = "档案归属地")
    private String dagsd;

    @ApiModelProperty(value = "档案位置")
    private String dawz;

    @ApiModelProperty(value = "不动产房产受理编号")
    private String bdcfcslbh;

    @ApiModelProperty(value = "是否税费托管 0：否 1：是 ")
    private Integer sfsftg;

    public String getDagsd() {
        return dagsd;
    }

    public void setDagsd(String dagsd) {
        this.dagsd = dagsd;
    }

    public Integer getSftdejscfc() {
        return sftdejscfc;
    }

    public void setSftdejscfc(Integer sftdejscfc) {
        this.sftdejscfc = sftdejscfc;
    }

    public Integer getSbqk() {
        return sbqk;
    }

    public void setSbqk(Integer sbqk) {
        this.sbqk = sbqk;
    }

    public String getXczpimg() {
        return xczpimg;
    }

    public void setXczpimg(String xczpimg) {
        this.xczpimg = xczpimg;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getZdsylx() {
        return zdsylx;
    }

    public void setZdsylx(Integer zdsylx) {
        this.zdsylx = zdsylx;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Integer getSfszfgf() {
        return sfszfgf;
    }

    public void setSfszfgf(Integer sfszfgf) {
        this.sfszfgf = sfszfgf;
    }

    public String getQlcfbs() {
        return qlcfbs;
    }

    public void setQlcfbs(String qlcfbs) {
        this.qlcfbs = qlcfbs;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Integer getSfyt() {
        return sfyt;
    }

    public void setSfyt(Integer sfyt) {
        this.sfyt = sfyt;
    }

    public Integer getDdjb() {
        return ddjb;
    }

    public void setDdjb(Integer ddjb) {
        this.ddjb = ddjb;
    }

    public String getFwsgbh() {
        return fwsgbh;
    }

    public void setFwsgbh(String fwsgbh) {
        this.fwsgbh = fwsgbh;
    }

    public Integer getSfdrjyxx() {
        return sfdrjyxx;
    }

    public void setSfdrjyxx(Integer sfdrjyxx) {
        this.sfdrjyxx = sfdrjyxx;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public Integer getJdjfz() {
        return jdjfz;
    }

    public void setJdjfz(Integer jdjfz) {
        this.jdjfz = jdjfz;
    }

    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }

    public Integer getSfahdj() {
        return sfahdj;
    }

    public void setSfahdj(Integer sfahdj) {
        this.sfahdj = sfahdj;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public Integer getSfhszs() {
        return sfhszs;
    }

    public void setSfhszs(Integer sfhszs) {
        this.sfhszs = sfhszs;
    }

    public Integer getSfgg() {
        return sfgg;
    }

    public void setSfgg(Integer sfgg) {
        this.sfgg = sfgg;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public Integer getSfzjjg() {
        return sfzjjg;
    }

    public void setSfzjjg(Integer sfzjjg) {
        this.sfzjjg = sfzjjg;
    }

    public Integer getSfgyz() {
        return sfgyz;
    }

    public void setSfgyz(Integer sfgyz) {
        this.sfgyz = sfgyz;
    }

    public String getSzsx() {
        return szsx;
    }

    public void setSzsx(String szsx) {
        this.szsx = szsx;
    }

    public Integer getLzfs() {
        return lzfs;
    }

    public void setLzfs(Integer lzfs) {
        this.lzfs = lzfs;
    }

    public String getFzyj() { return fzyj; }

    public void setFzyj(String fzyj) { this.fzyj = fzyj; }

    public Integer getSfcz() {
        return sfcz;
    }

    public void setSfcz(Integer sfcz) {
        this.sfcz = sfcz;
    }

    public Double getCzmj() {
        return czmj;
    }

    public void setCzmj(Double czmj) {
        this.czmj = czmj;
    }

    public String getYcsyqx() {
        return ycsyqx;
    }

    public void setYcsyqx(String ycsyqx) {
        this.ycsyqx = ycsyqx;
    }

    public Integer getSfkhjc() {
        return sfkhjc;
    }

    public void setSfkhjc(Integer sfkhjc) {
        this.sfkhjc = sfkhjc;
    }

    public Integer getZsrlzt() {
        return zsrlzt;
    }

    public void setZsrlzt(Integer zsrlzt) {
        this.zsrlzt = zsrlzt;
    }

    public Double getTdsyqfe() {
        return tdsyqfe;
    }

    public void setTdsyqfe(Double tdsyqfe) {
        this.tdsyqfe = tdsyqfe;
    }

    public Integer getSfzhyclh() {
        return sfzhyclh;
    }

    public void setSfzhyclh(Integer sfzhyclh) {
        this.sfzhyclh = sfzhyclh;
    }

    public String getYsxkzh() {
        return ysxkzh;
    }

    public void setYsxkzh(String ysxkzh) {
        this.ysxkzh = ysxkzh;
    }

    public String getGhxkzh() {
        return ghxkzh;
    }

    public void setGhxkzh(String ghxkzh) {
        this.ghxkzh = ghxkzh;
    }

    public String getGhyszmbh() {
        return ghyszmbh;
    }

    public void setGhyszmbh(String ghyszmbh) {
        this.ghyszmbh = ghyszmbh;
    }

    public Date getGhyssj() {
        return ghyssj;
    }

    public void setGhyssj(Date ghyssj) {
        this.ghyssj = ghyssj;
    }

    public String getJgysbabh() {
        return jgysbabh;
    }

    public void setJgysbabh(String jgysbabh) {
        this.jgysbabh = jgysbabh;
    }

    public Date getJgysbasj() {
        return jgysbasj;
    }

    public void setJgysbasj(Date jgysbasj) {
        this.jgysbasj = jgysbasj;
    }

    public String getClsjsyqx() {
        return clsjsyqx;
    }

    public void setClsjsyqx(String clsjsyqx) {
        this.clsjsyqx = clsjsyqx;
    }

    public Integer getZsxt() {
        return zsxt;
    }

    public void setZsxt(Integer zsxt) {
        this.zsxt = zsxt;
    }

    public String getDawz() {
        return dawz;
    }

    public void setDawz(String dawz) {
        this.dawz = dawz;
    }

    public String getBdcfcslbh() {
        return bdcfcslbh;
    }

    public void setBdcfcslbh(String bdcfcslbh) {
        this.bdcfcslbh = bdcfcslbh;
    }

    public Integer getSfsftg() {
        return sfsftg;
    }

    public void setSfsftg(Integer sfsftg) {
        this.sfsftg = sfsftg;
    }

    @Override
    public String toString() {
        return "BdcXmFbDO{" +
                "xmid='" + xmid + '\'' +
                ", zdsylx=" + zdsylx +
                ", gzlslid='" + gzlslid + '\'' +
                ", sfszfgf=" + sfszfgf +
                ", qlcfbs='" + qlcfbs + '\'' +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", fj='" + fj + '\'' +
                ", sfyt=" + sfyt +
                ", ddjb=" + ddjb +
                ", fwsgbh='" + fwsgbh + '\'' +
                ", sfdrjyxx=" + sfdrjyxx +
                ", unid='" + unid + '\'' +
                ", jdjfz=" + jdjfz +
                ", syqx='" + syqx + '\'' +
                ", sfahdj=" + sfahdj +
                ", qjgldm='" + qjgldm + '\'' +
                ", sfhszs=" + sfhszs +
                ", sfgg=" + sfgg +
                ", cqly='" + cqly + '\'' +
                ", sfzjjg=" + sfzjjg +
                ", sfgyz=" + sfgyz +
                ", szsx='" + szsx + '\'' +
                ", lzfs=" + lzfs +
                ", fzyj='" + fzyj + '\'' +
                ", sfcz=" + sfcz +
                ", czmj=" + czmj +
                ", ycsyqx='" + ycsyqx + '\'' +
                ", sfkhjc=" + sfkhjc +
                ", zsrlzt=" + zsrlzt +
                ", xczpimg='" + xczpimg + '\'' +
                ", tdsyqfe=" + tdsyqfe +
                ", sfzhyclh=" + sfzhyclh +
                ", ysxkzh='" + ysxkzh + '\'' +
                ", ghxkzh='" + ghxkzh + '\'' +
                ", ghyszmbh='" + ghyszmbh + '\'' +
                ", ghyssj=" + ghyssj +
                ", jgysbabh='" + jgysbabh + '\'' +
                ", jgysbasj=" + jgysbasj +
                ", sbqk=" + sbqk +
                ", clsjsyqx='" + clsjsyqx + '\'' +
                ", zsxt=" + zsxt +
                ", sftdejscfc=" + sftdejscfc +
                ", dagsd='" + dagsd + '\'' +
                ", dawz='" + dawz + '\'' +
                ", bdcfcslbh='" + bdcfcslbh + '\'' +
                ", sfsftg=" + sfsftg +
                '}';
    }
}
