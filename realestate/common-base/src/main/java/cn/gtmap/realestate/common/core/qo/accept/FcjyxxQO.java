package cn.gtmap.realestate.common.core.qo.accept;

import cn.gtmap.realestate.common.core.dto.exchange.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/8/3
 * @description
 */
@ApiModel(value = "FcjyxxQO", description = "房产交易查询对象")
public class FcjyxxQO {

    @ApiModelProperty(value = "接口标识符")
    private String beanName;
    @ApiModelProperty(value = "合同编号")
    private String htbh;
    @ApiModelProperty(value = "证件号")
    private String zjh;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "房屋类型")
    private String fwlx;
    @ApiModelProperty(value = "流程类型")
    private String lclx;
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;
    @ApiModelProperty(value = "房屋编码")
    private String fwbm;
    @ApiModelProperty(value = "是否更新受理交易信息，默认false")
    private boolean modifyJyxx = false;
    @ApiModelProperty(value = "是否更新受理房屋信息，默认false")
    private boolean modifyFwxx = false;
    @ApiModelProperty(value = "是否更新登记项目与权利信息，默认false")
    private boolean modifyXmAndQlxx = false;
    @ApiModelProperty(value = "是否更新权利人信息，默认false")
    private boolean modifyQlrxx = false;

    @ApiModelProperty(value = "分页信息")
    private PageDTO pageDTO;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "是否更新第三权利人信息，默认false")
    private boolean modifyDsQlr = false;

    @ApiModelProperty(value = "是否需要分割权利人，默认false")
    private boolean splitQlr = false;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getLclx() {
        return lclx;
    }

    public void setLclx(String lclx) {
        this.lclx = lclx;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public boolean isModifyJyxx() {
        return modifyJyxx;
    }

    public void setModifyJyxx(boolean modifyJyxx) {
        this.modifyJyxx = modifyJyxx;
    }

    public boolean isModifyFwxx() {
        return modifyFwxx;
    }

    public void setModifyFwxx(boolean modifyFwxx) {
        this.modifyFwxx = modifyFwxx;
    }

    public boolean isModifyXmAndQlxx() {
        return modifyXmAndQlxx;
    }

    public void setModifyXmAndQlxx(boolean modifyXmAndQlxx) {
        this.modifyXmAndQlxx = modifyXmAndQlxx;
    }

    public boolean isModifyQlrxx() {
        return modifyQlrxx;
    }

    public void setModifyQlrxx(boolean modifyQlrxx) {
        this.modifyQlrxx = modifyQlrxx;
    }

    public boolean isNeedImport(){
        if(this.modifyFwxx || this.modifyJyxx
                || this.modifyXmAndQlxx || this.modifyQlrxx){
            return true;
        }
        return false;
    }

    public PageDTO getPageDTO() {
        return pageDTO;
    }

    public void setPageDTO(PageDTO pageDTO) {
        this.pageDTO = pageDTO;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public boolean isModifyDsQlr() {
        return modifyDsQlr;
    }

    public void setModifyDsQlr(boolean modifyDsQlr) {
        this.modifyDsQlr = modifyDsQlr;
    }

    public boolean isSplitQlr() {
        return splitQlr;
    }

    public void setSplitQlr(boolean splitQlr) {
        this.splitQlr = splitQlr;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    @Override
    public String toString() {
        return "FcjyxxQO{" +
                "beanName='" + beanName + '\'' +
                ", htbh='" + htbh + '\'' +
                ", zjh='" + zjh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", fwlx='" + fwlx + '\'' +
                ", lclx='" + lclx + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", modifyJyxx=" + modifyJyxx +
                ", modifyFwxx=" + modifyFwxx +
                ", modifyXmAndQlxx=" + modifyXmAndQlxx +
                ", modifyQlrxx=" + modifyQlrxx +
                ", pageDTO=" + pageDTO +
                ", version='" + version + '\'' +
                ", modifyDsQlr=" + modifyDsQlr +
                ", splitQlr=" + splitQlr +
                '}';
    }
}
