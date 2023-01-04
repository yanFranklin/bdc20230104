package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.StringJoiner;

/**
 * @program: realestate
 * @description: 推送缴费与流程关系
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-16 13:45
 **/
@Table(name = "bdc_lc_tsjf_gx")
public class BdcLcTsjfGxDO {

    @Id
    @ApiModelProperty("关系主键")
    private String gxid;

    @ApiModelProperty("工作流实例id")
    private String gzlslid;

    @ApiModelProperty("收费信息id")
    private String sfxxid;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("推送类型- 产权/抵押")
    private String tslx;

    @ApiModelProperty("权利人")
    private String qlrmc;

    @ApiModelProperty("代理人")
    private String dlrmc;

    @ApiModelProperty("联系人名称-抵押推送使用")
    private String lxrmc;

    @ApiModelProperty("联系电话-抵押推送使用")
    private String lxdh;

    @ApiModelProperty("推送财政的推送信息ID")
    private String tsid;

    @ApiModelProperty("序号")
    private Integer xh;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getTslx() {
        return tslx;
    }

    public void setTslx(String tslx) {
        this.tslx = tslx;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getLxrmc() {
        return lxrmc;
    }

    public void setLxrmc(String lxrmc) {
        this.lxrmc = lxrmc;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getTsid() {
        return tsid;
    }

    public void setTsid(String tsid) {
        this.tsid = tsid;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcLcTsjfGxDO.class.getSimpleName() + "[", "]")
                .add("gxid='" + gxid + "'")
                .add("gzlslid='" + gzlslid + "'")
                .add("sfxxid='" + sfxxid + "'")
                .add("slbh='" + slbh + "'")
                .add("tslx='" + tslx + "'")
                .add("qlrmc='" + qlrmc + "'")
                .add("dlrmc='" + dlrmc + "'")
                .add("lxrmc='" + lxrmc + "'")
                .add("lxdh='" + lxdh + "'")
                .add("tsid='" + tsid + "'")
                .add("xh=" + xh)
                .toString();
    }
}
