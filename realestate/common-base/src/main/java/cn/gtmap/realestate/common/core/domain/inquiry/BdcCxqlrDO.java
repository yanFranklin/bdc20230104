package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/8/26
 * @description 不动产查询权利人信息
 */
@Table(name = "bdc_cxqlr")
public class BdcCxqlrDO {
    @Id
    @ApiModelProperty("查询权利人主键ID")
    private String qlrid;
    @ApiModelProperty("查询申请书ID")
    private String sqsid;
    @ApiModelProperty("权利人名称")
    private String qlrmc;
    @ApiModelProperty("权利人证件号")
    private String zjh;
    @ApiModelProperty("证件种类")
    private Integer zjzl;

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getSqsid() {
        return sqsid;
    }

    public void setSqsid(String sqsid) {
        this.sqsid = sqsid;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public Integer getZjzl() {
        return zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }

    @Override
    public String toString() {
        return "BdcCxqlrDO{" +
                "qlrid='" + qlrid + '\'' +
                ", sqsid='" + sqsid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", zjh='" + zjh + '\'' +
                ", zjzl=" + zjzl +
                '}';
    }
}
