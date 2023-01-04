package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/12/16
 * @description 盐城征迁审核信息表
 */
@Api(value = "BdcZqShxxDO", description = "盐城征迁审核信息表")
@Table(name = "BDC_ZQ_SHXX")
public class BdcZqShxxDO {
    @Id
    @ApiModelProperty(value = "审核信息ID")
    private String shxxid;

    @ApiModelProperty(value = "申请信息ID")
    private String sqxxid;

    @ApiModelProperty(value = "审核阶段")
    private Integer shjd;

    @ApiModelProperty(value = "审核人员姓名")
    private String shryxm;

    @ApiModelProperty(value = "审核人员ID")
    private String shryid;

    @ApiModelProperty(value = "审核时间", example = "2018-10-01 12:18:48")
    private Date shsj;

    @ApiModelProperty(value = "审核意见")
    private String shyj;

    @ApiModelProperty(value = "签名ID")
    private String qmid;

    @ApiModelProperty(value = "备注")
    private String bz;


    public String getShxxid() {
        return shxxid;
    }

    public void setShxxid(String shxxid) {
        this.shxxid = shxxid;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public Integer getShjd() {
        return shjd;
    }

    public void setShjd(Integer shjd) {
        this.shjd = shjd;
    }

    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    public String getShryid() {
        return shryid;
    }

    public void setShryid(String shryid) {
        this.shryid = shryid;
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getQmid() {
        return qmid;
    }

    public void setQmid(String qmid) {
        this.qmid = qmid;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        return "BdcZqShxxDO{" +
                "shxxid='" + shxxid + '\'' +
                ", sqxxid='" + sqxxid + '\'' +
                ", shjd=" + shjd +
                ", shryxm='" + shryxm + '\'' +
                ", shryid='" + shryid + '\'' +
                ", shsj=" + shsj +
                ", shyj='" + shyj + '\'' +
                ", qmid='" + qmid + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }
}
