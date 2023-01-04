package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/2/11
 * @description
 */
@ApiModel(value = "GetJtcyxxQO", description = "获取家庭成员接口入参")
public class GetJtcyxxQO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "申请人ID")
    private String sqrid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "审批来源")
    private Integer sply;

    @ApiModelProperty(value = "接口标识符")
    private String beanName;

    @ApiModelProperty(value = "查询婚姻接口标识符")
    private String hybeanName;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getSply() {
        return sply;
    }

    public void setSply(Integer sply) {
        this.sply = sply;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getHybeanName() {
        return hybeanName;
    }

    public void setHybeanName(String hybeanName) {
        this.hybeanName = hybeanName;
    }

    @Override
    public String toString() {
        return "GetJtcyxxQO{" +
                "slbh='" + slbh + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", sqrid='" + sqrid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", sply=" + sply +
                ", beanName='" + beanName + '\'' +
                ", hybeanName='" + hybeanName + '\'' +
                '}';
    }

    public GetJtcyxxQO(){};

    public GetJtcyxxQO(String qlrmc, String qlrzjh, String sqrid) {
        this.qlrmc = qlrmc;
        this.qlrzjh = qlrzjh;
        this.sqrid = sqrid;
    }

    public GetJtcyxxQO(String qlrzjh, String beanName) {
        this.qlrzjh = qlrzjh;
        this.beanName = beanName;
    }

    public GetJtcyxxQO withSlbh(String slbh){
        this.slbh = slbh;
        return this;
    }
}
