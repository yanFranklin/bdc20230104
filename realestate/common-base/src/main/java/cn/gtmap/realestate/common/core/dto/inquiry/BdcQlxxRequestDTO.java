package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-27
 * @description 不动产查询权利信息requestDTO
 */
@Api(value = "BdcQlxxRequestDTO", description = "不动产查询权利信息requestDTO")
public class BdcQlxxRequestDTO {

    @ApiModelProperty(value = "权利类型数组")
    private String[] qllxs;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "权利人类别，1权利人2义务人")
    private String qlrlb;

    @ApiModelProperty(value = "权属状态数组")
    private String[] qszts;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String[] getQllxs() {
        return qllxs;
    }

    public void setQllxs(String[] qllxs) {
        this.qllxs = qllxs;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String[] getQszts() {
        return qszts;
    }

    public void setQszts(String[] qszts) {
        this.qszts = qszts;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        return "BdcQlxxRequestDTO{" +
                "qllxs=" + Arrays.toString(qllxs) +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                ", qszts=" + Arrays.toString(qszts) +
                ", bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
