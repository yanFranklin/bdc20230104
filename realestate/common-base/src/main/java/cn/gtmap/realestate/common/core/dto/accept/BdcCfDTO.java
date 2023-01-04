package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcZdCflxDO;
import io.swagger.annotations.ApiModelProperty;


/**
 * @program: 3.0
 * @description: 查封信息页面展示数据
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-30 13:47
 **/
public class BdcCfDTO {

    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "查封机关")
    private String cfjg;
    @Zd(tableClass = BdcZdCflxDO.class)
    @ApiModelProperty(value = "查封类型")
    private Integer cflx;
    @ApiModelProperty(value = "查封文号")
    private String cfwh;
    @ApiModelProperty(value = "查封范围")
    private String cffw;
    @ApiModelProperty(value = "查封起始时间", example = "2018-10-01 12:18:48")
    private String cfqssj;
    @ApiModelProperty(value = "查封结束时间", example = "2018-10-01 12:18:48")
    private String cfjssj;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "查封编号")
    private String cfbh;
    @ApiModelProperty("原查封编号")
    private String ycfbh;

    @ApiModelProperty("受理时间")
    private String slsj;

    @ApiModelProperty("解封登记时间")
    private String jfdjsj;
    @ApiModelProperty("坐落")
    private String zl;
    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("工作流id")
    private String gzlslid;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    @ApiModelProperty("原查封起始时间")
    private String ycfsj;

    @ApiModelProperty("原查封流程的工作流实例id")
    private String ycfgzlslid;

    @ApiModelProperty("原查封流程的项目来源")
    private String ycfxmly;

    @ApiModelProperty("项目来源")
    private String xmly;

    @ApiModelProperty("原项目id")
    private String yxmid;

    public String getYcfbh() {
        return ycfbh;
    }

    public void setYcfbh(String ycfbh) {
        this.ycfbh = ycfbh;
    }

    public String getSlsj() {
        return slsj;
    }

    public void setSlsj(String slsj) {
        this.slsj = slsj;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public Integer getCflx() {
        return cflx;
    }

    public void setCflx(Integer cflx) {
        this.cflx = cflx;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getCffw() {
        return cffw;
    }

    public void setCffw(String cffw) {
        this.cffw = cffw;
    }

    public String getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(String cfqssj) {
        this.cfqssj = cfqssj;
    }

    public String getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(String cfjssj) {
        this.cfjssj = cfjssj;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getCfbh() {
        return cfbh;
    }

    public void setCfbh(String cfbh) {
        this.cfbh = cfbh;
    }

    public String getJfdjsj() {
        return jfdjsj;
    }

    public void setJfdjsj(String jfdjsj) {
        this.jfdjsj = jfdjsj;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getYcfsj() {
        return ycfsj;
    }

    public void setYcfsj(String ycfsj) {
        this.ycfsj = ycfsj;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getYcfgzlslid() {
        return ycfgzlslid;
    }

    public void setYcfgzlslid(String ycfgzlslid) {
        this.ycfgzlslid = ycfgzlslid;
    }

    public String getYcfxmly() {
        return ycfxmly;
    }

    public void setYcfxmly(String ycfxmly) {
        this.ycfxmly = ycfxmly;
    }

    public String getXmly() {
        return xmly;
    }

    public void setXmly(String xmly) {
        this.xmly = xmly;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }
}
