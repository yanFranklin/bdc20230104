package cn.gtmap.realestate.common.core.domain.building;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/30
 * @description
 */
@Table(name = "fw_hsbgxxb")
@ApiModel(value = "FwHsBgxxDO", description = "户室变更信息表")
public class FwHsBgxxDO {

    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更编号")
    private String bgbh;
    /**
     * 变更类型
     */
    @ApiModelProperty(value = "变更类型")
    private String bglx;
    /**
     * 户室主键
     */
    @RequiredFk
    @ApiModelProperty(value = "户室主键")
    private String fwHsIndex;
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(value = "主键")
    private String hsBgIndex;
    /**
     * 开发企业名称
     */
    @ApiModelProperty(value = "开发企业名称")
    private String kfqymc;
    /**
     * 房屋坐落
     */
    @ApiModelProperty(value = "房屋坐落")
    private String zl;
    /**
     * 变更明细
     */
    @ApiModelProperty(value = "变更明细")
    private String bgmx;
    /**
     * 测绘审核意见
     */
    @ApiModelProperty(value = "测绘审核意见")
    private String chshyj;
    /**
     * 市场科意见
     */
    @ApiModelProperty(value = "市场科意见")
    private String sckyj;
    /**
     * 分管领导意见
     */
    @ApiModelProperty(value = "分管领导意见")
    private String fgldyj;
    /**
     * 主任审批意见
     */
    @ApiModelProperty(value = "主任审批意见")
    private String zrspyj;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getBglx() {
        return bglx;
    }

    public void setBglx(String bglx) {
        this.bglx = bglx;
    }

    public String getFwHsIndex() {
        return fwHsIndex;
    }

    public void setFwHsIndex(String fwHsIndex) {
        this.fwHsIndex = fwHsIndex;
    }

    public String getHsBgIndex() {
        return hsBgIndex;
    }

    public void setHsBgIndex(String hsBgIndex) {
        this.hsBgIndex = hsBgIndex;
    }

    public String getKfqymc() {
        return kfqymc;
    }

    public void setKfqymc(String kfqymc) {
        this.kfqymc = kfqymc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBgmx() {
        return bgmx;
    }

    public void setBgmx(String bgmx) {
        this.bgmx = bgmx;
    }

    public String getChshyj() {
        return chshyj;
    }

    public void setChshyj(String chshyj) {
        this.chshyj = chshyj;
    }

    public String getSckyj() {
        return sckyj;
    }

    public void setSckyj(String sckyj) {
        this.sckyj = sckyj;
    }

    public String getFgldyj() {
        return fgldyj;
    }

    public void setFgldyj(String fgldyj) {
        this.fgldyj = fgldyj;
    }

    public String getZrspyj() {
        return zrspyj;
    }

    public void setZrspyj(String zrspyj) {
        this.zrspyj = zrspyj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FwHsBgxxDO{");
        sb.append("bgbh='").append(bgbh).append('\'');
        sb.append(", bglx='").append(bglx).append('\'');
        sb.append(", fwHsIndex='").append(fwHsIndex).append('\'');
        sb.append(", hsBgIndex='").append(hsBgIndex).append('\'');
        sb.append(", kfqymc='").append(kfqymc).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", bgmx='").append(bgmx).append('\'');
        sb.append(", chshyj='").append(chshyj).append('\'');
        sb.append(", sckyj='").append(sckyj).append('\'');
        sb.append(", fgldyj='").append(fgldyj).append('\'');
        sb.append(", zrspyj='").append(zrspyj).append('\'');
        sb.append(", bz='").append(bz).append('\'');
        sb.append('}');
        return sb.toString();
    }
}