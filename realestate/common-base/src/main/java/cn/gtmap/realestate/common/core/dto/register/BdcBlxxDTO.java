package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 补录信息 DTO
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/10/11
 */
@ApiModel(value = "BdcBlxxDTO",description = "补录信息 DTO")
public class BdcBlxxDTO {
    /**
     * 补录审核类型，市级/县级
     */
    @ApiModelProperty(value = "补录审核类型")
    private Integer blshlx;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "平台工作流定义 ID")
    private String ptgzldyid;
    @ApiModelProperty(value = "平台流程名称")
    private String ptlcmc;
    @ApiModelProperty(value = "工作流定义 ID")
    private String gzldyid;
    @ApiModelProperty(value = "流程名称")
    private String lcmc;
    @ApiModelProperty(value = "原工作流 ID")
    private String ygzlslid;
    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;
    @ApiModelProperty(value = "权利数据来源 1：权籍 2：上一手  可组合(1,2)")
    private String qlsjly;
    @ApiModelProperty(value = "锁定原因")
    private String sdyy;
    @ApiModelProperty(value = "查封文号")
    private String cfwh;
    @ApiModelProperty(value = "原项目id")
    private String yxmid;
    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;
    @ApiModelProperty(value = "不动产单元房屋类型")
    private Integer bdcdyfwlx;
    @ApiModelProperty(value = "权利类型")
    private Integer yqllx;
    @ApiModelProperty
    private List<Map> plxxblParams;

    public List<Map> getPlxxblParams() {
        return plxxblParams;
    }

    public void setPlxxblParams(List<Map> plxxblParams) {
        this.plxxblParams = plxxblParams;
    }

    public String getQlsjly() {
        return qlsjly;
    }

    public void setQlsjly(String qlsjly) {
        this.qlsjly = qlsjly;
    }

    public Integer getBlshlx() {
        return blshlx;
    }

    public void setBlshlx(Integer blshlx) {
        this.blshlx = blshlx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getPtgzldyid() {
        return ptgzldyid;
    }

    public void setPtgzldyid(String ptgzldyid) {
        this.ptgzldyid = ptgzldyid;
    }

    public String getPtlcmc() {
        return ptlcmc;
    }

    public void setPtlcmc(String ptlcmc) {
        this.ptlcmc = ptlcmc;
    }

    public String getYgzlslid() {
        return ygzlslid;
    }

    public void setYgzlslid(String ygzlslid) {
        this.ygzlslid = ygzlslid;
    }

    public String getSdyy() {
        return sdyy;
    }

    public void setSdyy(String sdyy) {
        this.sdyy = sdyy;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public Integer getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(Integer bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public Integer getYqllx() {
        return yqllx;
    }

    public void setYqllx(Integer yqllx) {
        this.yqllx = yqllx;
    }

    @Override
    public String toString() {
        return "BdcBlxxDTO{" +
                "blshlx=" + blshlx +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", ptgzldyid='" + ptgzldyid + '\'' +
                ", ptlcmc='" + ptlcmc + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", ygzlslid='" + ygzlslid + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                ", qlsjly='" + qlsjly + '\'' +
                ", sdyy='" + sdyy + '\'' +
                ", cfwh='" + cfwh + '\'' +
                ", yxmid='" + yxmid + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", bdcdyfwlx=" + bdcdyfwlx +
                ", yqllx=" + yqllx +
                '}';
    }
}
