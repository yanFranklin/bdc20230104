package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/14
 * @description 税务信息查询QO
 */
@ApiModel(value = "BdcSwxxQO", description = "税务信息查询QO")
public class BdcSwxxQO {

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "接口定义名称")
    private String beanName;

    @ApiModelProperty(value = "更新类型:0:不更新,1：先删除原有税务信息，重新插入税务信息 2.根据纳税人识别号更新核税信息")
    private String gxlx;

    @ApiModelProperty(value = "证件号")
    private String zjh;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "税务唯一标识")
    private String fwuuid;

    @ApiModelProperty(value = "纳税人识别号")
    private String nsrsbh;

    @ApiModelProperty(value = "并案业务标记：查询并案所有业务时，传1，单独查询业务时，可不传或者传0")
    private String mergebz;

    @ApiModelProperty(value = "土地业务标记：查询房产类业务可不传或传0，查询土地业务需传1")
    private String tdbz;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "房屋类型， 1：增量房， 2：存量房")
    private String fwlx;

    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;

    @ApiModelProperty(value = "数据归属地区")
    private String sjgsdq;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getGxlx() {
        return gxlx;
    }

    public void setGxlx(String gxlx) {
        this.gxlx = gxlx;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getMergebz() {
        return mergebz;
    }

    public void setMergebz(String mergebz) {
        this.mergebz = mergebz;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public BdcSwxxQO(String gzlslid, String htbh, String fwlx) {
        this.gzlslid = gzlslid;
        this.htbh = htbh;
        this.fwlx = fwlx;
    }

    public BdcSwxxQO() {
    }

    public String getTdbz() {
        return tdbz;
    }

    public void setTdbz(String tdbz) {
        this.tdbz = tdbz;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getSjgsdq() {
        return sjgsdq;
    }

    public void setSjgsdq(String sjgsdq) {
        this.sjgsdq = sjgsdq;
    }

    @Override
    public String toString() {
        return "BdcSwxxQO{" +
                "xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", beanName='" + beanName + '\'' +
                ", gxlx='" + gxlx + '\'' +
                ", zjh='" + zjh + '\'' +
                ", htbh='" + htbh + '\'' +
                ", fwuuid='" + fwuuid + '\'' +
                ", nsrsbh='" + nsrsbh + '\'' +
                ", mergebz='" + mergebz + '\'' +
                ", tdbz='" + tdbz + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", fwlx='" + fwlx + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                ", sjgsdq='" + sjgsdq + '\'' +
                '}';
    }
}
