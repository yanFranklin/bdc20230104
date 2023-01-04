package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 摩科评价器请求数据实体
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-11-12 13:51
 **/
public class BdcMkPjqRequestDTO {

    @ApiModelProperty("当前系统的ip")
    private String sysIp;

    @ApiModelProperty("业务编号")
    private String ywbh;

    @ApiModelProperty("业务名称")
    private String ywmc;

    @ApiModelProperty("节点名称")
    private String jdmc;

    @ApiModelProperty("办理人员")
    private String blry;

    @ApiModelProperty("办理人员")
    private String name;

    @ApiModelProperty("办理人员工号")
    private String jobNum;

    @ApiModelProperty("权利人名称")
    private String sqrxm;

    @ApiModelProperty("权利人联系方式")
    private String sqrlxfs;

    @ApiModelProperty("超时时间(单位：秒)")
    private String timeOut;

    @ApiModelProperty("业务编号-人证对比参数")
    private String serviceId;

    @ApiModelProperty("证件号-人证对比参数")
    private String idCard;

    @ApiModelProperty("行政区代码")
    private String xzqdm;

    @ApiModelProperty("签名id")
    private String signid;

    @ApiModelProperty("签名key")
    private String signkey;

    @ApiModelProperty("区县代码：用于exchange区分请求地址")
    private String qxdm;

    @ApiModelProperty("窗口号")
    private String winNum;

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public String getSysIp() {
        return sysIp;
    }

    public void setSysIp(String sysIp) {
        this.sysIp = sysIp;
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getBlry() {
        return blry;
    }

    public void setBlry(String blry) {
        this.blry = blry;
    }

    public String getSqrxm() {
        return sqrxm;
    }

    public void setSqrxm(String sqrxm) {
        this.sqrxm = sqrxm;
    }

    public String getSqrlxfs() {
        return sqrlxfs;
    }

    public void setSqrlxfs(String sqrlxfs) {
        this.sqrlxfs = sqrlxfs;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getSignid() {
        return signid;
    }

    public void setSignid(String signid) {
        this.signid = signid;
    }

    public String getSignkey() {
        return signkey;
    }

    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getWinNum() {
        return winNum;
    }

    public void setWinNum(String winNum) {
        this.winNum = winNum;
    }

    @Override
    public String toString() {
        return "BdcMkPjqRequestDTO{" +
                "sysIp='" + sysIp + '\'' +
                ", ywbh='" + ywbh + '\'' +
                ", ywmc='" + ywmc + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", blry='" + blry + '\'' +
                ", name='" + name + '\'' +
                ", jobNum='" + jobNum + '\'' +
                ", sqrxm='" + sqrxm + '\'' +
                ", sqrlxfs='" + sqrlxfs + '\'' +
                ", timeOut='" + timeOut + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", idCard='" + idCard + '\'' +
                ", xzqdm='" + xzqdm + '\'' +
                ", signid='" + signid + '\'' +
                ", signkey='" + signkey + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", winNum='" + winNum + '\'' +
                '}';
    }
}
