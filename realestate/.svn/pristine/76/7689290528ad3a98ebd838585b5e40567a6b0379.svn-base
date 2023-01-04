package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/8/6
 * @description 受理选择逻辑幢查询QO对象
 */
@ApiModel(value = "LjzQO", description = "受理选择逻辑幢查询QO对象")
public class LjzQO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产单元号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String bdcdyhmh;

    @ApiModelProperty(value = "自然幢号")
    private String zrzh;

    @ApiModelProperty(value = "隶属宗地")
    private String lszd;

    @ApiModelProperty(value = "隶属宗地模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String lszdmh;

    @ApiModelProperty(value = "房屋名称")
    private String fwmc;

    @ApiModelProperty(value = "房屋名称模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String fwmcmh;

    @ApiModelProperty(value = "逻辑幢号")
    private String ljzh;

    @ApiModelProperty(value = "坐落地址")
    private String zldz;

    @ApiModelProperty(value = "坐落地址模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String zldzmh;

    @ApiModelProperty(value = "幢号")
    private String dh;

    @ApiModelProperty(value = "幢号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String dhmh;

    @ApiModelProperty(value = "隶属宗地列表（用于查询GX和GB数据）")
    private List<String> lszdList;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty("是否查询锁定状态")
    private String sfsdzt;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getFwmc() {
        return fwmc;
    }

    public void setFwmc(String fwmc) {
        this.fwmc = fwmc;
    }

    public String getLjzh() {
        return ljzh;
    }

    public void setLjzh(String ljzh) {
        this.ljzh = ljzh;
    }

    public String getZldz() {
        return zldz;
    }

    public void setZldz(String zldz) {
        this.zldz = zldz;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public List<String> getLszdList() {
        return lszdList;
    }

    public void setLszdList(List<String> lszdList) {
        this.lszdList = lszdList;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getBdcdyhmh() {
        if (StringUtils.isBlank(bdcdyhmh)){
            return "4";
        }else {
            return bdcdyhmh;
        }
    }

    public void setBdcdyhmh(String bdcdyhmh) {
        this.bdcdyhmh = bdcdyhmh;
    }

    public String getLszdmh() {
        if (StringUtils.isBlank(lszdmh)){
            return "4";
        }else {
            return lszdmh;
        }
    }

    public void setLszdmh(String lszdmh) {
        this.lszdmh = lszdmh;
    }

    public String getFwmcmh() {
        if (StringUtils.isBlank(fwmcmh)){
            return "4";
        }else {
            return fwmcmh;
        }
    }

    public void setFwmcmh(String fwmcmh) {
        this.fwmcmh = fwmcmh;
    }

    public String getZldzmh() {
        if (StringUtils.isBlank(zldzmh)){
            return "4";
        }else {
            return zldzmh;
        }
    }

    public void setZldzmh(String zldzmh) {
        this.zldzmh = zldzmh;
    }

    public String getDhmh() {
        return dhmh;
    }

    public void setDhmh(String dhmh) {
        this.dhmh = dhmh;
    }

    public String getSfsdzt() {
        return sfsdzt;
    }

    public void setSfsdzt(String sfsdzt) {
        this.sfsdzt = sfsdzt;
    }
}
