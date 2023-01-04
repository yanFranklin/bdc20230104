package cn.gtmap.realestate.common.core.dto.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/14
 * @description 分页查询不动产权利页面的返回对象
 */
@ApiModel(value = "BdcQlPageResponseDTO",description = "分页查询不动产权利页面的返回对象")
public class BdcQlPageResponseDTO {
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;
    @ApiModelProperty(value = "权利类型")
    private String qllx;
    @ApiModelProperty(value = "权利类型名称")
    private String qllxMc;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "证号")
    private String bdcqzh;
    @ApiModelProperty(value = "权利人")
    private String qlrmc;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "定着物面积")
    private Double dzwmj;
    @ApiModelProperty(value = "定着物用途")
    private Integer dzwyt;
    @ApiModelProperty(value = "定着物用途2")
    private Integer dzwyt2;
    @ApiModelProperty(value = "定着物用途3")
    private Integer dzwyt3;
    @ApiModelProperty(value = "宗地宗海面积")
    private Double zdzhmj;
    @ApiModelProperty(value = "小班")
    private String xb;

    @ApiModelProperty(value = "不动产单元房屋类型")
    private Integer bdcdyfwlx;
    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;
    @ApiModelProperty(value = "原房产证号")
    private String yfczh;
    @ApiModelProperty(value = "原土地证号")
    private String ytdzh;
    @ApiModelProperty(value = "流程名称")
    private String gzldymc;
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    private Date djsj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "受理时间",example = "2018-10-01 12:18:48")
    private Date slsj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间",example = "2018-10-01 12:18:48")
    private Date jssj;

    @ApiModelProperty(value = "不动产单元唯一编号")
    private String bdcdywybh;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "项目来源")
    private String xmly;

    @ApiModelProperty(value = "不动产类型")
    private String bdclx;

    @ApiModelProperty(value = "房产预售房屋编码")
    private String ysfwbm;

    @ApiModelProperty(value = "TT系统证号")
    private String yxtcqzh;

    @ApiModelProperty(value = "原不动产单元号")
    private String ybdcdyh;

    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "证书类型")
    private Integer zslx;
    @ApiModelProperty(value = "合同编号")
    private String htbh;
    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;
    @ApiModelProperty(value = "幢号")
    private String zh;
    @ApiModelProperty(value = "房间号")
    private String fjh;
    @ApiModelProperty(value = "锁定申请文号")
    private String sdsqwh;
    @ApiModelProperty(value = "土地证号")
    private String tdzh;
    @ApiModelProperty(value = "土地证宗地面积")
    private String tdzzdmj;
    @ApiModelProperty(value = "分摊土地面积")
    private String tdzfttdmj;
    @ApiModelProperty(value = "独用土地面积")
    private String tdzdytdmj;
    @ApiModelProperty(value = "土地使用期限")
    private String tdzsyqx;
    @ApiModelProperty(value = "土地使用起始时间", example = "2018-10-01 12:18:48")
    private String tdztdsyqssj;
    @ApiModelProperty(value = "土地使用结束时间", example = "2018-10-01 12:18:48")
    private String tdztdsyjssj;
    @ApiModelProperty(value = "土地使用起始时间2", example = "2018-10-01 12:18:48")
    private String tdztdsyqssj2;
    @ApiModelProperty(value = "土地使用结束时间2", example = "2018-10-01 12:18:48")
    private String tdztdsyjssj2;
    @ApiModelProperty(value = "土地使用起始时间3", example = "2018-10-01 12:18:48")
    private String tdztdsyqssj3;
    @ApiModelProperty(value = "土地使用结束时间3", example = "2018-10-01 12:18:48")
    private String tdztdsyjssj3;
    @ApiModelProperty(value = "锁定状态")
    private String sdzt;

    @ApiModelProperty(value = "首次证明单号")
    private String sczmdh;

    @ApiModelProperty(value = "所属乡镇")
    private String ssxz;

    @ApiModelProperty(value = "原权利类型")
    private String yqllx;

    @ApiModelProperty(value = "证书证明类型")
    private String zszmlx;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;


    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getYbdcdyh() {
        return ybdcdyh;
    }

    public void setYbdcdyh(String ybdcdyh) {
        this.ybdcdyh = ybdcdyh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public Double getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(Double dzwmj) {
        this.dzwmj = dzwmj;
    }

    public Integer getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(Integer dzwyt) {
        this.dzwyt = dzwyt;
    }

    public Integer getDzwyt2() {
        return dzwyt2;
    }

    public void setDzwyt2(Integer dzwyt2) {
        this.dzwyt2 = dzwyt2;
    }

    public Integer getDzwyt3() {
        return dzwyt3;
    }

    public void setDzwyt3(Integer dzwyt3) {
        this.dzwyt3 = dzwyt3;
    }

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }


    public Integer getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(Integer bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getYfczh() {
        return yfczh;
    }

    public void setYfczh(String yfczh) {
        this.yfczh = yfczh;
    }

    public String getYtdzh() {
        return ytdzh;
    }

    public void setYtdzh(String ytdzh) {
        this.ytdzh = ytdzh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }


    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getXmly() {
        return xmly;
    }

    public void setXmly(String xmly) {
        this.xmly = xmly;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getYsfwbm() {
        return ysfwbm;
    }

    public void setYsfwbm(String ysfwbm) {
        this.ysfwbm = ysfwbm;
    }

    public String getQllxMc() {
        return qllxMc;
    }

    public void setQllxMc(String qllxMc) {
        this.qllxMc = qllxMc;
    }

    public String getYxtcqzh() {
        return yxtcqzh;
    }

    public void setYxtcqzh(String yxtcqzh) {
        this.yxtcqzh = yxtcqzh;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getSdsqwh() {
        return sdsqwh;
    }

    public void setSdsqwh(String sdsqwh) {
        this.sdsqwh = sdsqwh;
    }

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public String getTdzzdmj() {
        return tdzzdmj;
    }

    public void setTdzzdmj(String tdzzdmj) {
        this.tdzzdmj = tdzzdmj;
    }

    public String getTdzfttdmj() {
        return tdzfttdmj;
    }

    public void setTdzfttdmj(String tdzfttdmj) {
        this.tdzfttdmj = tdzfttdmj;
    }

    public String getTdzdytdmj() {
        return tdzdytdmj;
    }

    public void setTdzdytdmj(String tdzdytdmj) {
        this.tdzdytdmj = tdzdytdmj;
    }

    public String getTdzsyqx() {
        return tdzsyqx;
    }

    public void setTdzsyqx(String tdzsyqx) {
        this.tdzsyqx = tdzsyqx;
    }

    public String getTdztdsyqssj() {
        return tdztdsyqssj;
    }

    public void setTdztdsyqssj(String tdztdsyqssj) {
        this.tdztdsyqssj = tdztdsyqssj;
    }

    public String getTdztdsyjssj() {
        return tdztdsyjssj;
    }

    public void setTdztdsyjssj(String tdztdsyjssj) {
        this.tdztdsyjssj = tdztdsyjssj;
    }

    public String getTdztdsyqssj2() {
        return tdztdsyqssj2;
    }

    public void setTdztdsyqssj2(String tdztdsyqssj2) {
        this.tdztdsyqssj2 = tdztdsyqssj2;
    }

    public String getTdztdsyjssj2() {
        return tdztdsyjssj2;
    }

    public void setTdztdsyjssj2(String tdztdsyjssj2) {
        this.tdztdsyjssj2 = tdztdsyjssj2;
    }

    public String getTdztdsyqssj3() {
        return tdztdsyqssj3;
    }

    public void setTdztdsyqssj3(String tdztdsyqssj3) {
        this.tdztdsyqssj3 = tdztdsyqssj3;
    }

    public String getTdztdsyjssj3() {
        return tdztdsyjssj3;
    }

    public void setTdztdsyjssj3(String tdztdsyjssj3) {
        this.tdztdsyjssj3 = tdztdsyjssj3;
    }

    public String getSdzt() {
        return sdzt;
    }

    public void setSdzt(String sdzt) {
        this.sdzt = sdzt;
    }

    public String getSczmdh() {
        return sczmdh;
    }

    public void setSczmdh(String sczmdh) {
        this.sczmdh = sczmdh;
    }

    public String getSsxz() {
        return ssxz;
    }

    public void setSsxz(String ssxz) {
        this.ssxz = ssxz;
    }

    public String getYqllx() {
        return yqllx;
    }

    public void setYqllx(String yqllx) {
        this.yqllx = yqllx;
    }

    public String getZszmlx() {
        return zszmlx;
    }

    public void setZszmlx(String zszmlx) {
        this.zszmlx = zszmlx;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    @Override
    public String toString() {
        return "BdcQlPageResponseDTO{" +
                "xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qllxMc='" + qllxMc + '\'' +
                ", djxl='" + djxl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", zl='" + zl + '\'' +
                ", qszt=" + qszt +
                ", dzwmj=" + dzwmj +
                ", dzwyt=" + dzwyt +
                ", dzwyt2=" + dzwyt2 +
                ", dzwyt3=" + dzwyt3 +
                ", zdzhmj=" + zdzhmj +
                ", bdcdyfwlx=" + bdcdyfwlx +
                ", ycqzh='" + ycqzh + '\'' +
                ", yfczh='" + yfczh + '\'' +
                ", ytdzh='" + ytdzh + '\'' +
                ", gzldymc='" + gzldymc + '\'' +
                ", djsj=" + djsj +
                ", slsj=" + slsj +
                ", jssj=" + jssj +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", xmly='" + xmly + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", ysfwbm='" + ysfwbm + '\'' +
                ", yxtcqzh='" + yxtcqzh + '\'' +
                ", ybdcdyh='" + ybdcdyh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", zslx=" + zslx +
                ", htbh='" + htbh + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", zh='" + zh + '\'' +
                ", fjh='" + fjh + '\'' +
                ", sdsqwh='" + sdsqwh + '\'' +
                ", tdzh='" + tdzh + '\'' +
                ", tdzzdmj='" + tdzzdmj + '\'' +
                ", tdzfttdmj='" + tdzfttdmj + '\'' +
                ", tdzdytdmj='" + tdzdytdmj + '\'' +
                ", tdzsyqx='" + tdzsyqx + '\'' +
                ", tdztdsyqssj='" + tdztdsyqssj + '\'' +
                ", tdztdsyjssj='" + tdztdsyjssj + '\'' +
                ", tdztdsyqssj2='" + tdztdsyqssj2 + '\'' +
                ", tdztdsyjssj2='" + tdztdsyjssj2 + '\'' +
                ", tdztdsyqssj3='" + tdztdsyqssj3 + '\'' +
                ", tdztdsyjssj3='" + tdztdsyjssj3 + '\'' +
                ", sdzt='" + sdzt + '\'' +
                ", sczmdh='" + sczmdh + '\'' +
                ", ssxz='" + ssxz + '\'' +
                ", yqllx='" + yqllx + '\'' +
                ", zszmlx='" + zszmlx + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", xb='" + xb + '\'' +

                '}';
    }
}
