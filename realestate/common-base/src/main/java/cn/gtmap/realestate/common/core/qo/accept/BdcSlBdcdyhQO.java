package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/4/19
 * @description 受理不动产单元号查询QO对象
 */
@ApiModel(value = "BdcSlBdcdyhQO", description = "受理不动产单元号查询QO对象")
public class BdcSlBdcdyhQO {

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "权利人模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String qlrmh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "坐落模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String zlmh;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产单元号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String bdcdyhmh;

    @ApiModelProperty(value = "不动产类型")
    private String bdclx;

    @ApiModelProperty(value = "权利类型")
    private String qllx;

    @ApiModelProperty(value = "宗地特征码")
    private String zdtzm;

    @ApiModelProperty(value = "权利性质及宗地特征码,不动产单元号的13+14位")
    private String qlxzAndZdtzm;

    @ApiModelProperty(value = "定着物特征码")
    private String dzwtzm;

    @ApiModelProperty(value = "不动产单元房屋类型")
    private String bdcdyfwlx;

    @ApiModelProperty(value = "单元号查询类型")
    private Integer dyhcxlx;

    @ApiModelProperty(value = "房间号")
    private String fjh;

    @ApiModelProperty(value = "房间号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String fjhmh;

    @ApiModelProperty(value = "地籍号")
    private String djh;

    @ApiModelProperty(value = "地籍号列表")
    private List<String> djhList;

    @ApiModelProperty(value = "是否加载全部")
    private Boolean loadTotal;

    @ApiModelProperty(value = "下级乡镇行政区划-9位代码")
    private String xjxzqh;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "工作流定义id")
    private String gzldyid;


    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "承包合同编号")
    private String cbhtbh;

    @ApiModelProperty(value = "承包合同编号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String cbhtbhmh;

    @ApiModelProperty(value = "房屋调查表index")
    private String fwDcbIndex;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty(value = "区县代码列表")
    private List<String> qxdmList;

    @ApiModelProperty(value = "单元截取长度")
    private Integer dycd;

    @ApiModelProperty("是否查询锁定状态")
    private String sfsdzt;

    @ApiModelProperty("规划用途")
    private String ghyt;

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

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }


    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getZdtzm() {
        return zdtzm;
    }

    public void setZdtzm(String zdtzm) {
        this.zdtzm = zdtzm;
    }

    public String getQlxzAndZdtzm() {
        return qlxzAndZdtzm;
    }

    public void setQlxzAndZdtzm(String qlxzAndZdtzm) {
        this.qlxzAndZdtzm = qlxzAndZdtzm;
    }

    public String getDzwtzm() {
        return dzwtzm;
    }

    public void setDzwtzm(String dzwtzm) {
        this.dzwtzm = dzwtzm;
    }

    public String getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(String bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public Integer getDyhcxlx() {
        return dyhcxlx;
    }

    public void setDyhcxlx(Integer dyhcxlx) {
        this.dyhcxlx = dyhcxlx;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public Boolean getLoadTotal() {
        return loadTotal;
    }

    public void setLoadTotal(Boolean loadTotal) {
        this.loadTotal = loadTotal;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public List<String> getDjhList() {
        return djhList;
    }

    public void setDjhList(List<String> djhList) {
        this.djhList = djhList;
    }

    public String getXjxzqh() {
        return xjxzqh;
    }

    public void setXjxzqh(String xjxzqh) {
        this.xjxzqh = xjxzqh;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getCbhtbh() {
        return cbhtbh;
    }

    public void setCbhtbh(String cbhtbh) {
        this.cbhtbh = cbhtbh;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getQlrmh() {
        if (StringUtils.isBlank(qlrmh)){
            return "4";
        }else {
            return qlrmh;
        }
    }

    public void setQlrmh(String qlrmh) {
        this.qlrmh = qlrmh;
    }

    public String getZlmh() {
        if (StringUtils.isBlank(zlmh)){
            return "4";
        }else {
            return zlmh;
        }
    }

    public void setZlmh(String zlmh) {
        this.zlmh = zlmh;
    }

    public String getBdcdyhmh() {
        if (StringUtils.isBlank(bdcdyhmh)){
            return "4";
        }else {
            return bdcdyhmh;
        }
    }

    public void setBdcdyhmh(String bdcdyhmh) {
        this.bdcdyhmh = bdcdyhmh;
    }

    public String getFjhmh() {
        if (StringUtils.isBlank(fjhmh)){
            return "4";
        }else {
            return fjhmh;
        }
    }

    public void setFjhmh(String fjhmh) {
        this.fjhmh = fjhmh;
    }

    public String getCbhtbhmh() {
        if (StringUtils.isBlank(cbhtbhmh)){
            return "4";
        }else {
            return cbhtbhmh;
        }
    }

    public void setCbhtbhmh(String cbhtbhmh) {
        this.cbhtbhmh = cbhtbhmh;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public Integer getDycd() {
        return dycd;
    }

    public void setDycd(Integer dycd) {
        this.dycd = dycd;
    }


    public String getSfsdzt() {
        return sfsdzt;
    }

    public void setSfsdzt(String sfsdzt) {
        this.sfsdzt = sfsdzt;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    @Override
    public String toString() {
        return "BdcSlBdcdyhQO{" +
                "qlr='" + qlr + '\'' +
                ", qlrmh='" + qlrmh + '\'' +
                ", zl='" + zl + '\'' +
                ", zlmh='" + zlmh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdyhmh='" + bdcdyhmh + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", qllx='" + qllx + '\'' +
                ", zdtzm='" + zdtzm + '\'' +
                ", qlxzAndZdtzm='" + qlxzAndZdtzm + '\'' +
                ", dzwtzm='" + dzwtzm + '\'' +
                ", bdcdyfwlx='" + bdcdyfwlx + '\'' +
                ", dyhcxlx=" + dyhcxlx +
                ", fjh='" + fjh + '\'' +
                ", fjhmh='" + fjhmh + '\'' +
                ", djh='" + djh + '\'' +
                ", djhList=" + djhList +
                ", loadTotal=" + loadTotal +
                ", xjxzqh='" + xjxzqh + '\'' +
                ", htbh='" + htbh + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", cbhtbh='" + cbhtbh + '\'' +
                ", cbhtbhmh='" + cbhtbhmh + '\'' +
                ", fwDcbIndex='" + fwDcbIndex + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", qxdmList=" + qxdmList +
                ", dycd=" + dycd +
                ", sfsdzt='" + sfsdzt + '\'' +
                ", ghyt='" + ghyt + '\'' +
                '}';
    }


}
