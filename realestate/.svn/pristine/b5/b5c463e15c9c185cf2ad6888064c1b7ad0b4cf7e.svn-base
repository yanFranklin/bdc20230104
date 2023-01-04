package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/11/21
 * @description 水电气过户
 */
@Table(name = "BDC_SDQGH")
public class BdcSdqghDO {
    @Id
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "户号")
    private String consno;

    @ApiModelProperty(value = "户主名称")
    private String hzmc;

    @ApiModelProperty(value = "新户主名称")
    private String xhzmc;

    @ApiModelProperty(value = "户主坐落")
    private String hzzl;

    @ApiModelProperty(value = "申请时间",example = "2018-10-01 12:18:48")
    private Date sqsj;

    @ApiModelProperty(value = "申请办理人")
    private String sqblrmc;

    @ApiModelProperty(value = "是否办理水业务，保留用于exchange接口对照")
    private Integer sfblsyw;

    @ApiModelProperty(value = "是否办理电业务，保留用于exchange接口对照")
    private Integer sfbldyw;

    @ApiModelProperty(value = "是否办理气业务，保留用于exchange接口对照")
    private Integer sfblqyw;

    @ApiModelProperty(value = "是否办理广电业务，保留用于exchange接口对照")
    private Integer sfblgdyw;

    @ApiModelProperty(value = "是否办理网络业务，保留用于exchange接口对照")
    private Integer sfblwlyw;

    @Deprecated
    @ApiModelProperty(value = "水业务办理状态")
    private Integer sywblzt;

    @Deprecated
    @ApiModelProperty(value = "电业务办理状态")
    private Integer dywblzt;

    @Deprecated
    @ApiModelProperty(value = "气业务办理状态")
    private Integer qywblzt;

    @Deprecated
    @ApiModelProperty(value = "广电业务办理状态")
    private Integer gdywblzt;

    @Deprecated
    @ApiModelProperty(value = "网络业务办理状态")
    private Integer wlywblzt;

    @ApiModelProperty(value = "是否办理")
    private Integer sfbl;

    @ApiModelProperty(value = "业务类型")
    private Integer ywlx;

    @ApiModelProperty(value = "办理状态")
    private Integer blzt;

    @ApiModelProperty(value = "第三方服务标识")
    private String rqfwbsm;

    @ApiModelProperty(value = "推送次数")
    private Integer tscs;

    @ApiModelProperty(value = "核验结果 1：成功，0：失败")
    private Integer hyjg;

    @ApiModelProperty(value = "核验详情")
    private String hyxq;

    @ApiModelProperty(value = "是否峰谷用电 1：是，0：否")
    private Integer sffgyd;

    /**
     * 用于记录水电气审核意见（接口返回相关审核意见信息）
     */
    @ApiModelProperty(value = "水电气审核意见")
    private String sdqshyj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getConsno() {
        return consno;
    }

    public void setConsno(String consno) {
        this.consno = consno;
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getSqblrmc() {
        return sqblrmc;
    }

    public void setSqblrmc(String sqblrmc) {
        this.sqblrmc = sqblrmc;
    }

    public Integer getSfblsyw() {
        return sfblsyw;
    }

    public void setSfblsyw(Integer sfblsyw) {
        this.sfblsyw = sfblsyw;
    }

    public Integer getSfbldyw() {
        return sfbldyw;
    }

    public void setSfbldyw(Integer sfbldyw) {
        this.sfbldyw = sfbldyw;
    }

    public Integer getSfblqyw() {
        return sfblqyw;
    }

    public void setSfblqyw(Integer sfblqyw) {
        this.sfblqyw = sfblqyw;
    }

    public Integer getSffgyd() {
        return sffgyd;
    }

    public void setSffgyd(Integer sffgyd) {
        this.sffgyd = sffgyd;
    }

    @Deprecated
    public Integer getSywblzt() {
        return sywblzt;
    }

    @Deprecated
    public void setSywblzt(Integer sywblzt) {
        this.sywblzt = sywblzt;
    }

    @Deprecated
    public Integer getDywblzt() {
        return dywblzt;
    }

    @Deprecated
    public void setDywblzt(Integer dywblzt) {
        this.dywblzt = dywblzt;
    }

    @Deprecated
    public Integer getQywblzt() {
        return qywblzt;
    }

    @Deprecated
    public void setQywblzt(Integer qywblzt) {
        this.qywblzt = qywblzt;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getHzmc() {
        return hzmc;
    }

    public void setHzmc(String hzmc) {
        this.hzmc = hzmc;
    }

    public String getXhzmc() {
        return xhzmc;
    }

    public void setXhzmc(String xhzmc) {
        this.xhzmc = xhzmc;
    }

    public String getHzzl() {
        return hzzl;
    }

    public void setHzzl(String hzzl) {
        this.hzzl = hzzl;
    }

    public Integer getSfblgdyw() {
        return sfblgdyw;
    }

    public void setSfblgdyw(Integer sfblgdyw) {
        this.sfblgdyw = sfblgdyw;
    }

    public Integer getSfblwlyw() {
        return sfblwlyw;
    }

    public void setSfblwlyw(Integer sfblwlyw) {
        this.sfblwlyw = sfblwlyw;
    }

    public String getSdqshyj() {
        return sdqshyj;
    }

    public void setSdqshyj(String sdqshyj) {
        this.sdqshyj = sdqshyj;
    }

    @Deprecated
    public Integer getGdywblzt() {
        return gdywblzt;
    }

    @Deprecated
    public void setGdywblzt(Integer gdywblzt) {
        this.gdywblzt = gdywblzt;
    }

    @Deprecated
    public Integer getWlywblzt() {
        return wlywblzt;
    }

    @Deprecated
    public void setWlywblzt(Integer wlywblzt) {
        this.wlywblzt = wlywblzt;
    }

    public Integer getSfbl() {
        return sfbl;
    }

    public void setSfbl(Integer sfbl) {
        this.sfbl = sfbl;
    }

    public Integer getYwlx() {
        return ywlx;
    }

    public void setYwlx(Integer ywlx) {
        this.ywlx = ywlx;
    }

    public Integer getBlzt() {
        return blzt;
    }

    public void setBlzt(Integer blzt) {
        this.blzt = blzt;
    }

    public String getRqfwbsm() {
        return rqfwbsm;
    }

    public void setRqfwbsm(String rqfwbsm) {
        this.rqfwbsm = rqfwbsm;
    }

    public Integer getTscs() {
        return tscs;
    }

    public void setTscs(Integer tscs) {
        this.tscs = tscs;
    }

    public Integer getHyjg() {
        return hyjg;
    }

    public void setHyjg(Integer hyjg) {
        this.hyjg = hyjg;
    }

    public String getHyxq() {
        return hyxq;
    }

    public void setHyxq(String hyxq) {
        this.hyxq = hyxq;
    }

    @Override
    public String toString() {
        return "BdcSdqghDO{" +
                "id='" + id + '\'' +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", consno='" + consno + '\'' +
                ", hzmc='" + hzmc + '\'' +
                ", xhzmc='" + xhzmc + '\'' +
                ", hzzl='" + hzzl + '\'' +
                ", sqsj=" + sqsj +
                ", sqblrmc='" + sqblrmc + '\'' +
                ", sfblsyw=" + sfblsyw +
                ", sfbldyw=" + sfbldyw +
                ", sfblqyw=" + sfblqyw +
                ", sfblgdyw=" + sfblgdyw +
                ", sfblwlyw=" + sfblwlyw +
                ", sywblzt=" + sywblzt +
                ", dywblzt=" + dywblzt +
                ", qywblzt=" + qywblzt +
                ", gdywblzt=" + gdywblzt +
                ", wlywblzt=" + wlywblzt +
                ", sfbl=" + sfbl +
                ", ywlx=" + ywlx +
                ", blzt=" + blzt +
                ", rqfwbsm='" + rqfwbsm + '\'' +
                ", tscs=" + tscs +
                ", hyjg=" + hyjg +
                ", hyxq='" + hyxq + '\'' +
                ", sdqshyj='" + sdqshyj + '\'' +
                '}';
    }
}
