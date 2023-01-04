package cn.gtmap.realestate.common.core.qo.init;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2021/12/16
 * @description
 */
@ApiModel(value = "BdcDysdQO",description = "单元锁定查询对象")
public class BdcDysdQO extends BdcDysdDO {

    @ApiModelProperty(value = "不动产单元号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String bdcdyhmh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "坐落模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String zlmh;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "权利人模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String qlrmh;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "权利人证件号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String qlrzjhmh;

    @ApiModelProperty(value = "区县代码集合")
    private List<String> qxdmList;

    @ApiModelProperty(value = "不动产单元号List")
    private List<String> bdcdyhList;

    @ApiModelProperty(value = "工作流实例IDList")
    private List<String> gzlslidList;

    @ApiModelProperty(value = "项目IDList")
    private List<String> xmidList;

    @ApiModelProperty("工作流定义id")
    private String gzldyid;

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getZlmh() {
        if (StringUtils.isBlank(zlmh)){
            return "4";
        } else {
            return zlmh;
        }
    }

    public void setZlmh(String zlmh) {
        this.zlmh = zlmh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrmh() {
        if (StringUtils.isBlank(qlrmh)){
            return "4";
        } else {
            return qlrmh;
        }
    }

    public void setQlrmh(String qlrmh) {
        this.qlrmh = qlrmh;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrzjhmh() {
        if (StringUtils.isBlank(qlrzjhmh)){
            return "4";
        } else {
            return qlrzjhmh;
        }
    }

    public void setQlrzjhmh(String qlrzjhmh) {
        this.qlrzjhmh = qlrzjhmh;
    }

    public String getBdcdyhmh() {
        if (StringUtils.isBlank(bdcdyhmh)){
            return "4";
        } else {
            return bdcdyhmh;
        }
    }

    public void setBdcdyhmh(String bdcdyhmh) {
        this.bdcdyhmh = bdcdyhmh;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    public List<String> getBdcdyhList() {
        return bdcdyhList;
    }

    public void setBdcdyhList(List<String> bdcdyhList) {
        this.bdcdyhList = bdcdyhList;
    }

    public List<String> getGzlslidList() {
        return gzlslidList;
    }

    public void setGzlslidList(List<String> gzlslidList) {
        this.gzlslidList = gzlslidList;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    @Override
    public String toString() {
        return "BdcDysdQO{" +
                "bdcdyhmh='" + bdcdyhmh + '\'' +
                ", zl='" + zl + '\'' +
                ", zlmh='" + zlmh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrmh='" + qlrmh + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrzjhmh='" + qlrzjhmh + '\'' +
                ", qxdmList=" + qxdmList +
                ", bdcdyhList=" + bdcdyhList +
                ", gzlslidList=" + gzlslidList +
                ", xmidList=" + xmidList +
                ", gzldyid='" + gzldyid + '\'' +
                "} " + super.toString();
    }
}
