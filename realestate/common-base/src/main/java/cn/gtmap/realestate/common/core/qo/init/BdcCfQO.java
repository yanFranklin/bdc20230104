package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @description 不动产查封信息查询对象
 */
@ApiModel(value = "BdcCfQO", description = "不动产查封信息查询对象")
public class BdcCfQO {
    @ApiModelProperty(value = "权利人")
    private String qlrmc;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "坐落模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String zlmh;
    @ApiModelProperty(value = "不动产单元号")
    private List<String> bdcdyh;
    @ApiModelProperty(value = "不动产单元号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String bdcdyhmh;
    @ApiModelProperty(value = "义务人")
    private String ywrmc;
    @ApiModelProperty(value = "义务人模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String ywrmcmh;
    @ApiModelProperty(value = "查封文号")
    private String cfwh;
    @ApiModelProperty(value = "查封文号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String cfwhmh;
    @ApiModelProperty(value = "查封机关")
    private String cfjg;
    @ApiModelProperty(value = "查封机关模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String cfjgmh;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "受理编号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String slbhmh;
    @ApiModelProperty(value = "查封编号")
    private String cfbh;
    @ApiModelProperty(value = "查封编号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String cfbhmh;
    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;
    @ApiModelProperty(value = "原产权证号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String ycqzhmh;
    @ApiModelProperty(value = "项目ID")
    private List<String> xmid;
    @ApiModelProperty(value = "区县代码集合")
    private List<String> qxdmList;
    @ApiModelProperty("工作流定义id")
    private String gzldyid;

    @ApiModelProperty(value = "查封类型")
    private List<Integer> cflxList;

    @ApiModelProperty("是否查询锁定状态")
    private String sfsdzt;

    @ApiModelProperty(value = "证号简称")
    private String bdcqzhjc;

    @ApiModelProperty(value = "证号简称模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String bdcqzhjcmh;

    @ApiModelProperty(value = "定着物用途")
    private List<String> dzwytList;

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

    public List<String> getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(List<String> bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String ywrmc) {
        this.ywrmc = ywrmc;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public List<String> getXmid() {
        return xmid;
    }

    public void setXmid(List<String> xmid) {
        this.xmid = xmid;
    }

    public String getYwrmcmh() {
        if (StringUtils.isBlank(ywrmcmh)) {
            return "3";
        } else {
            return ywrmcmh;
        }
    }

    public void setYwrmcmh(String ywrmcmh) {
        this.ywrmcmh = ywrmcmh;
    }

    public String getZlmh() {
        if (StringUtils.isBlank(zlmh)) {
            return "4";
        } else {
            return zlmh;
        }
    }

    public void setZlmh(String zlmh) {
        this.zlmh = zlmh;
    }

    public String getCfwhmh() {
        if (StringUtils.isBlank(cfwhmh)) {
            return "4";
        } else {
            return cfwhmh;
        }
    }

    public void setCfwhmh(String cfwhmh) {
        this.cfwhmh = cfwhmh;
    }

    public String getCfjgmh() {
        if (StringUtils.isBlank(cfjgmh)) {
            return "4";
        } else {
            return cfjgmh;
        }
    }

    public void setCfjgmh(String cfjgmh) {
        this.cfjgmh = cfjgmh;
    }

    public String getSlbhmh() {
        if (StringUtils.isBlank(slbhmh)) {
            return "1";
        } else {
            return slbhmh;
        }
    }

    public void setSlbhmh(String slbhmh) {
        this.slbhmh = slbhmh;
    }

    public String getYcqzhmh() {
        if (StringUtils.isBlank(ycqzhmh)) {
            return "4";
        } else {
            return ycqzhmh;
        }
    }

    public void setYcqzhmh(String ycqzhmh) {
        this.ycqzhmh = ycqzhmh;
    }

    public String getCfbh() {
        return cfbh;
    }

    public void setCfbh(String cfbh) {
        this.cfbh = cfbh;
    }

    public String getCfbhmh() {
        if (StringUtils.isBlank(cfbhmh)) {
            return "1";
        } else {
            return cfbhmh;
        }
    }

    public void setCfbhmh(String cfbhmh) {
        this.cfbhmh = cfbhmh;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public List<Integer> getCflxList() {
        return cflxList;
    }

    public void setCflxList(List<Integer> cflxList) {
        this.cflxList = cflxList;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }


    public String getSfsdzt() {
        return sfsdzt;
    }

    public void setSfsdzt(String sfsdzt) {
        this.sfsdzt = sfsdzt;
    }

    public String getBdcqzhjc() {
        return bdcqzhjc;
    }

    public void setBdcqzhjc(String bdcqzhjc) {
        this.bdcqzhjc = bdcqzhjc;
    }

    public String getBdcqzhjcmh() {
        if (StringUtils.isBlank(bdcqzhjcmh)) {
            return "1";
        } else {
            return bdcqzhjcmh;
        }
    }

    public void setBdcqzhjcmh(String bdcqzhjcmh) {
        this.bdcqzhjcmh = bdcqzhjcmh;
    }

    public String getBdcdyhmh() {
        return bdcdyhmh;
    }

    public void setBdcdyhmh(String bdcdyhmh) {
        this.bdcdyhmh = bdcdyhmh;
    }

    public List<String> getDzwytList() {
        return dzwytList;
    }

    public void setDzwytList(List<String> dzwytList) {
        this.dzwytList = dzwytList;
    }

    @Override
    public String toString() {
        return "BdcCfQO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", zl='" + zl + '\'' +
                ", zlmh='" + zlmh + '\'' +
                ", bdcdyh=" + bdcdyh +
                ", bdcdyhmh='" + bdcdyhmh + '\'' +
                ", ywrmc='" + ywrmc + '\'' +
                ", ywrmcmh='" + ywrmcmh + '\'' +
                ", cfwh='" + cfwh + '\'' +
                ", cfwhmh='" + cfwhmh + '\'' +
                ", cfjg='" + cfjg + '\'' +
                ", cfjgmh='" + cfjgmh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", slbhmh='" + slbhmh + '\'' +
                ", cfbh='" + cfbh + '\'' +
                ", cfbhmh='" + cfbhmh + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                ", ycqzhmh='" + ycqzhmh + '\'' +
                ", xmid=" + xmid +
                ", qxdmList=" + qxdmList +
                ", gzldyid='" + gzldyid + '\'' +
                ", cflxList=" + cflxList +
                ", sfsdzt='" + sfsdzt + '\'' +
                ", bdcqzhjc='" + bdcqzhjc + '\'' +
                ", bdcqzhjcmh='" + bdcqzhjcmh + '\'' +
                ", dzwytList=" + dzwytList +
                '}';
    }
}
