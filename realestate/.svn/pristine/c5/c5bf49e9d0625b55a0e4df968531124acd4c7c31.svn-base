package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/30
 * @description 盐城征迁退回信息表
 */
@Api(value = "BdcZqThxxDO", description = "盐城征迁退回信息表")
@Table(name = "BDC_ZQ_THXX")
public class BdcZqThxxDO {
    @Id
    @ApiModelProperty(value = "退回信息ID")
    private String thxxid;

    @ApiModelProperty(value = "申请信息ID")
    private String sqxxid;

    @ApiModelProperty(value = "退回人员姓名")
    private String thryxm;

    @ApiModelProperty(value = "退回人员ID")
    private String thryid;

    @ApiModelProperty(value = "退回时间", example = "2018-10-01 12:18:48")
    private Date thsj;

    @ApiModelProperty(value = "退回原因")
    private String thyy;

    @ApiModelProperty(value = "备注")
    private String bz;


    public String getThxxid() {
        return thxxid;
    }

    public void setThxxid(String thxxid) {
        this.thxxid = thxxid;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public String getThryxm() {
        return thryxm;
    }

    public void setThryxm(String thryxm) {
        this.thryxm = thryxm;
    }

    public String getThryid() {
        return thryid;
    }

    public void setThryid(String thryid) {
        this.thryid = thryid;
    }

    public Date getThsj() {
        return thsj;
    }

    public void setThsj(Date thsj) {
        this.thsj = thsj;
    }

    public String getThyy() {
        return thyy;
    }

    public void setThyy(String thyy) {
        this.thyy = thyy;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        return "BdcZqThxxDO{" +
                "thxxid='" + thxxid + '\'' +
                ", sqxxid='" + sqxxid + '\'' +
                ", thryxm='" + thryxm + '\'' +
                ", thryid='" + thryid + '\'' +
                ", thsj=" + thsj +
                ", thyy='" + thyy + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }
}
