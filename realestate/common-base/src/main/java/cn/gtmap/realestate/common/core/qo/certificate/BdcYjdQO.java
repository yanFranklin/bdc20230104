package cn.gtmap.realestate.common.core.qo.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/1
 * @description 移交单查询QO对象
 */
@ApiModel(value = "BdcYjdQO", description = "移交单查询QO对象")
public class BdcYjdQO {
    @ApiModelProperty(value = "移交单id")
    String yjdid;
    @ApiModelProperty(value = "移交单清册受理编号")
    String yjdceSlbh;
    @ApiModelProperty(value = "权利人")
    String qlr;
    @ApiModelProperty(value = "坐落")
    String zl;
    @ApiModelProperty(value = "移交单编号")
    String yjdbh;
    @ApiModelProperty(value = "移交时间")
    Date yjsj;
    @ApiModelProperty(value = "查询起始时间")
    Date qssj;
    @ApiModelProperty(value = "查询截止时间")
    Date jzsj;
    @ApiModelProperty(value = "移交人")
    String yjr;
    @ApiModelProperty(value = "不动产权证号")
    String bdcqzh;
    @ApiModelProperty(value = "受理编号")
    String slbh;
    @ApiModelProperty(value = "区县代码")
    String qxdm;
    @ApiModelProperty(value = "项目ID")
    String xmid;
    @ApiModelProperty(value = "项目ID集合")
    List<String> listXmid;
    @ApiModelProperty(value = "不动产单元号")
    String bdcdyh;
    @ApiModelProperty(value = "移交状态")
    Integer yjzt;
    @ApiModelProperty(value = "版本信息")
    String version;

    @ApiModelProperty(value = "受理编号集合")
    List<String> listSlbh;

    @ApiModelProperty(value = "受理编号手动")
    String slbhsd;

    @ApiModelProperty(value = "是否出证")
    private Integer sfcz;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getYjzt() {
        return yjzt;
    }

    public void setYjzt(Integer yjzt) {
        this.yjzt = yjzt;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getYjdbh() {
        return yjdbh;
    }

    public void setYjdbh(String yjdbh) {
        this.yjdbh = yjdbh;
    }

    public Date getYjsj() {
        return yjsj;
    }

    public void setYjsj(Date yjsj) {
        this.yjsj = yjsj;
    }

    public String getYjr() {
        return yjr;
    }

    public void setYjr(String yjr) {
        this.yjr = yjr;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getSlbh() {
        return slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<String> getListXmid() {
        return listXmid;
    }

    public void setListXmid(List<String> listXmid) {
        this.listXmid = listXmid;
    }

    public Date getQssj() {
        return qssj;
    }

    public void setQssj(Date qssj) {
        this.qssj = qssj;
    }

    public Date getJzsj() {
        return jzsj;
    }

    public void setJzsj(Date jzsj) {
        this.jzsj = jzsj;
    }


    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getYjdceSlbh() {
        return yjdceSlbh;
    }

    public void setYjdceSlbh(String yjdceSlbh) {
        this.yjdceSlbh = yjdceSlbh;
    }

    public String getYjdid() {
        return yjdid;
    }

    public void setYjdid(String yjdid) {
        this.yjdid = yjdid;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public List<String> getListSlbh() {
        return listSlbh;
    }

    public void setListSlbh(List<String> listSlbh) {
        this.listSlbh = listSlbh;
    }

    public String getSlbhsd() {
        return slbhsd;
    }

    public void setSlbhsd(String slbhsd) {
        this.slbhsd = slbhsd;
    }

    public Integer getSfcz() {
        return sfcz;
    }

    public void setSfcz(Integer sfcz) {
        this.sfcz = sfcz;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcYjdQO.class.getSimpleName() + "[", "]")
                .add("yjdid='" + yjdid + "'")
                .add("yjdceSlbh='" + yjdceSlbh + "'")
                .add("qlr='" + qlr + "'")
                .add("zl='" + zl + "'")
                .add("yjdbh='" + yjdbh + "'")
                .add("yjsj=" + yjsj)
                .add("qssj=" + qssj)
                .add("jzsj=" + jzsj)
                .add("yjr='" + yjr + "'")
                .add("bdcqzh='" + bdcqzh + "'")
                .add("slbh='" + slbh + "'")
                .add("qxdm='" + qxdm + "'")
                .add("xmid='" + xmid + "'")
                .add("listXmid=" + listXmid)
                .add("bdcdyh='" + bdcdyh + "'")
                .add("yjzt=" + yjzt)
                .add("version='" + version + "'")
                .add("listSlbh=" + listSlbh)
                .add("slbhsd='" + slbhsd + "'")
                .add("sfcz=" + sfcz)
                .toString();
    }
}
