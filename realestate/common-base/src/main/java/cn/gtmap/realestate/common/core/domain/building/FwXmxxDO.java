package cn.gtmap.realestate.common.core.domain.building;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import cn.gtmap.realestate.common.core.annotations.SaveBdcdyhZt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description
 */
@Table(name = "fw_xmxx")
@ApiModel(value = "FwXmxxDO", description = "项目信息返回实体")
public class FwXmxxDO {
    @Id
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String fwXmxxIndex;
    /**
     * 隶属宗地
     */
    @RequiredFk
    @ApiModelProperty(value = "隶属宗地")
    private String lszd;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    @SaveBdcdyhZt
    private String zl;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String xmmc;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    @SaveBdcdyhZt
    private String bdcdyh;
    /**
     * 独用土地面积
     */
    @ApiModelProperty(value = "独用土地面积")
    private Double dytdmj;
    /**
     * 分摊土地面积
     */
    @ApiModelProperty(value = "分摊土地面积")
    private Double fttdmj;

    /**
     * 批准建筑面积
     */
    @ApiModelProperty(value = "批准建筑面积")
    private Double pzjzmj;

    /**
     * 交易价格
     */
    @ApiModelProperty(value = "交易价格")
    private Double jyjg;
    /**
     * 产权来源
     */
    @ApiModelProperty(value = "产权来源")
    private String cqly;
    /**
     * 房屋类型
     */
    @ApiModelProperty(value = "房屋类型")
    private String fwlx;
    /**
     * 共有情况
     */
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    /**
     * 房屋性质
     */
    @ApiModelProperty(value = "房屋性质")
    private String fwxz;
    /**
     * 不动产状态
     */
    @ApiModelProperty(value = "不动产状态")
    @SaveBdcdyhZt("bdcdyzt")
    private String bdczt;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
    /**
     * 产别
     */
    @ApiModelProperty(value = "产别")
    private String cb;

    @ApiModelProperty(value = "调查者")
    private String dcz;

    @ApiModelProperty(value = "调查时间",example = "2018-10-01 12:18:21")
    private Date dcsj;

    @ApiModelProperty(value = "调查意见")
    private String dcyj;

    @ApiModelProperty(value = "附加说明")
    private String fjsm;

    @ApiModelProperty(value = "项目编码")
    private String xmbm;

    @ApiModelProperty(value = "所属区域代码")
    private String xzssqydm;

    public String getDcz() {
        return dcz;
    }

    public void setDcz(String dcz) {
        this.dcz = dcz;
    }

    public Date getDcsj() {
        return dcsj;
    }

    public void setDcsj(Date dcsj) {
        this.dcsj = dcsj;
    }

    public String getDcyj() {
        return dcyj;
    }

    public void setDcyj(String dcyj) {
        this.dcyj = dcyj;
    }

    public String getFjsm() {
        return fjsm;
    }

    public void setFjsm(String fjsm) {
        this.fjsm = fjsm;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getFwXmxxIndex() {
        return fwXmxxIndex;
    }

    public void setFwXmxxIndex(String fwXmxxIndex) {
        this.fwXmxxIndex = fwXmxxIndex;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getBdczt() {
        return bdczt;
    }

    public void setBdczt(String bdczt) {
        this.bdczt = bdczt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getXmbm() {
        return xmbm;
    }

    public void setXmbm(String xmbm) {
        this.xmbm = xmbm;
    }

    public String getXzssqydm() {
        return xzssqydm;
    }

    public void setXzssqydm(String xzssqydm) {
        this.xzssqydm = xzssqydm;
    }

    public Double getPzjzmj() {
        return pzjzmj;
    }

    public void setPzjzmj(Double pzjzmj) {
        this.pzjzmj = pzjzmj;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FwXmxxDO{");
        sb.append("fwXmxxIndex='").append(fwXmxxIndex).append('\'');
        sb.append(", lszd='").append(lszd).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", xmmc='").append(xmmc).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", dytdmj=").append(dytdmj);
        sb.append(", fttdmj=").append(fttdmj);
        sb.append(", jyjg=").append(jyjg);
        sb.append(", cqly='").append(cqly).append('\'');
        sb.append(", fwlx='").append(fwlx).append('\'');
        sb.append(", gyqk='").append(gyqk).append('\'');
        sb.append(", fwxz='").append(fwxz).append('\'');
        sb.append(", bdczt='").append(bdczt).append('\'');
        sb.append(", bz='").append(bz).append('\'');
        sb.append(", cb='").append(cb).append('\'');
        sb.append(", dcz='").append(dcz).append('\'');
        sb.append(", dcsj=").append(dcsj);
        sb.append(", dcyj='").append(dcyj).append('\'');
        sb.append(", fjsm='").append(fjsm).append('\'');
        sb.append(", xmbm='").append(xmbm).append('\'');
        sb.append(", xzssqydm='").append(xzssqydm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
