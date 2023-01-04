package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021-01-28
 * @description 民政婚姻信息比对
 */
public class CompareHyxxQO {

    @NotBlank(message = "申请人id不能为空!")
    @ApiModelProperty(value = "申请人id")
    private String sqrid;

    @NotBlank(message = "申请人姓名不能为空!")
    @ApiModelProperty(value = "申请人姓名")
    private String sqrxm;

    @ApiModelProperty(value = "申请人证件种类")
    private String sqlzjzl;

    @NotBlank(message = "申请人证件号不能为空!")
    @ApiModelProperty(value = "申请人证件号")
    private String sqrzjh;

    @NotBlank(message = "婚姻状态不能为空!")
    @ApiModelProperty(value = "婚姻状态")
    private String hyzt;

    @ApiModelProperty(value = "配偶姓名")
    private String poxm;

    @ApiModelProperty(value = "配偶证件种类")
    private String pozjzl;

    @ApiModelProperty(value = "配偶证件号")
    private String pozjh;

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getSqrxm() {
        return sqrxm;
    }

    public void setSqrxm(String sqrxm) {
        this.sqrxm = sqrxm;
    }

    public String getSqlzjzl() {
        return sqlzjzl;
    }

    public void setSqlzjzl(String sqlzjzl) {
        this.sqlzjzl = sqlzjzl;
    }

    public String getSqrzjh() {
        return sqrzjh;
    }

    public void setSqrzjh(String sqrzjh) {
        this.sqrzjh = sqrzjh;
    }

    public String getHyzt() {
        return hyzt;
    }

    public void setHyzt(String hyzt) {
        this.hyzt = hyzt;
    }

    public String getPoxm() {
        return poxm;
    }

    public void setPoxm(String poxm) {
        this.poxm = poxm;
    }

    public String getPozjzl() {
        return pozjzl;
    }

    public void setPozjzl(String pozjzl) {
        this.pozjzl = pozjzl;
    }

    public String getPozjh() {
        return pozjh;
    }

    public void setPozjh(String pozjh) {
        this.pozjh = pozjh;
    }
}
