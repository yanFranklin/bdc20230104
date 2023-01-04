package cn.gtmap.realestate.common.core.qo.init;

import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/21
 * @description
 */
@ApiModel(value = "BdcZssdQO",description = "证书锁定查询对象")
public class BdcZssdQO extends BdcZssdDO {

    @ApiModelProperty(value = "不动产权证号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String cqzhmh;

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

    @ApiModelProperty(value = "证号流水号")
    private String zhlsh;

    @ApiModelProperty(value = "证号流水号模糊方式 1:精确 2:左模糊 3:右模糊 4:全模糊")
    private String zhlshmh;

    @ApiModelProperty(value = "区县代码集合")
    private List<String> qxdmList;

    @ApiModelProperty(value = "项目ID集合")
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

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public String getZhlshmh() {
        if (StringUtils.isBlank(zhlshmh)){
            return "4";
        } else {
            return zhlshmh;
        }
    }

    public void setZhlshmh(String zhlshmh) {
        this.zhlshmh = zhlshmh;
    }

    public String getCqzhmh() {
        if (StringUtils.isBlank(cqzhmh)){
            return "4";
        }else {
            return cqzhmh;
        }
    }

    public void setCqzhmh(String cqzhmh) {
        this.cqzhmh = cqzhmh;
    }

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
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
        return "BdcZssdQO{" +
                "cqzhmh='" + cqzhmh + '\'' +
                ", zl='" + zl + '\'' +
                ", zlmh='" + zlmh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrmh='" + qlrmh + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrzjhmh='" + qlrzjhmh + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", zhlshmh='" + zhlshmh + '\'' +
                ", qxdmList=" + qxdmList +
                ", xmidList=" + xmidList +
                ", gzldyid='" + gzldyid + '\'' +
                "} " + super.toString();
    }
}
