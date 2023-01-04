package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/11
 * @description  住房信息打印查询参数DTO
 */
public class BdcZfxxCsDTO {
    @ApiModelProperty(value = "权利人信息")
    private List<BdcQlrQO> qlrQOList;

    @ApiModelProperty(value = "二维码请求地址")
    private String ewmurl;

    @ApiModelProperty(value = "（多个拼接）不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "查询申请书中填写的查询目的或用途")
    private String cxmd;

    @ApiModelProperty(value = "当前登录用户中文名")
    private String useralias;

    @ApiModelProperty(value = "家庭成员")
    private List<BdcJtcyDO> jtcyDOList;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "打印证明类型")
    private String printType;

    @ApiModelProperty(value = "权利人曾用名信息")
    private List<BdcQlrQO> qlrcymQOList;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty(value = "是否需要过滤规划用途  Y 是 N 否")
    private String sfghyt;
    

    public String getSfghyt() {
        return sfghyt;
    }

    public void setSfghyt(String sfghyt) {
        this.sfghyt = sfghyt;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<BdcJtcyDO> getJtcyDOList() {
        return jtcyDOList;
    }

    public void setJtcyDOList(List<BdcJtcyDO> jtcyDOList) {
        this.jtcyDOList = jtcyDOList;
    }

    public String getUseralias() {
        return useralias;
    }

    public void setUseralias(String useralias) {
        this.useralias = useralias;
    }

    public String getCxmd() {
        return cxmd;
    }

    public void setCxmd(String cxmd) {
        this.cxmd = cxmd;
    }

    public List<BdcQlrQO> getQlrQOList() {
        return qlrQOList;
    }

    public void setQlrQOList(List<BdcQlrQO> qlrQOList) {
        this.qlrQOList = qlrQOList;
    }

    public String getEwmurl() {
        return ewmurl;
    }

    public void setEwmurl(String ewmurl) {
        this.ewmurl = ewmurl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public List<BdcQlrQO> getQlrcymQOList() {
        return qlrcymQOList;
    }

    public void setQlrcymQOList(List<BdcQlrQO> qlrcymQOList) {
        this.qlrcymQOList = qlrcymQOList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcZfxxCsDTO{");
        sb.append("qlrQOList=").append(qlrQOList);
        sb.append(", ewmurl='").append(ewmurl).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", cxmd='").append(cxmd).append('\'');
        sb.append(", useralias='").append(useralias).append('\'');
        sb.append(", jtcyDOList=").append(jtcyDOList);
        sb.append(", version='").append(version).append('\'');
        sb.append(", printType='").append(printType).append('\'');
        sb.append(", qlrcymQOList=").append(qlrcymQOList);
        sb.append('}');
        return sb.toString();
    }
}
