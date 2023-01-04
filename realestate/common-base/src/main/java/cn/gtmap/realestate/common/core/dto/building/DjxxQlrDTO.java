package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/16
 * @description 地籍权利人DTO
 */
@ApiModel(value = "DjxxQlrDTO", description = "地籍权利人实体")
public class DjxxQlrDTO {
    /**
     * 权利人名称
     */
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    /**
     * 权利人联系电话
     */
    @ApiModelProperty(value = "权利人联系电话")
    private String qlrlxdh;
    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    private String zjzl;
    /**
     * 证件号
     */
    @ApiModelProperty(value = "证件号")
    private String zjh;
    /**
     * 通讯地址
     */
    @ApiModelProperty(value = "通讯地址")
    private String txdz;
    /**
     * 邮编
     */
    private String yb;

    /**
     * 法人名称
     */
    @ApiModelProperty(value = "法人名称")
    private String frmc;

    /**
     * 法人代表证件类型
     */
    @ApiModelProperty(value = "法人代表证件类型")
    private String frdbzjlx;
    /**
     * 法人代表证件号
     */
    @ApiModelProperty(value = "法人代表证件号")
    private String frdbzjh;

    /**
     * 法人电话
     */
    @ApiModelProperty(value = "法人电话")
    private String frdh;
    /**
     * 代理人名称
     */
    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;

    /**
     * 代理人证件类型
     */
    @ApiModelProperty(value = "代理人证件类型")
    private String dlrzjlx;
    /**
     * 代理人证件号
     */
    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;
    /**
     * 代理人电话
     */
    @ApiModelProperty(value = "代理人电话")

    private String dlrdh;
    /**
     * 权利人类型
     */
    @ApiModelProperty(value = "权利人类型")
    private String qlrlx;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
    /**
     * 顺序号
     */
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    @ApiModelProperty(value = "权利面积")
    private Double qlmj;

    @ApiModelProperty(value = "分摊面积")
    private Double ftmj;

    @ApiModelProperty(value = "权利人特征")
    private String qlrtz;

    public String getQlrlxdh() {
        return qlrlxdh;
    }

    public void setQlrlxdh(String qlrlxdh) {
        this.qlrlxdh = qlrlxdh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public String getFrmc() {
        return frmc;
    }

    public void setFrmc(String frmc) {
        this.frmc = frmc;
    }

    public String getFrdh() {
        return frdh;
    }

    public void setFrdh(String frdh) {
        this.frdh = frdh;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getDlrdh() {
        return dlrdh;
    }

    public void setDlrdh(String dlrdh) {
        this.dlrdh = dlrdh;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public Double getQlmj() {
        return qlmj;
    }

    public void setQlmj(Double qlmj) {
        this.qlmj = qlmj;
    }

    public Double getFtmj() {
        return ftmj;
    }

    public void setFtmj(Double ftmj) {
        this.ftmj = ftmj;
    }

    public String getFrdbzjlx() {
        return frdbzjlx;
    }

    public void setFrdbzjlx(String frdbzjlx) {
        this.frdbzjlx = frdbzjlx;
    }

    public String getFrdbzjh() {
        return frdbzjh;
    }

    public void setFrdbzjh(String frdbzjh) {
        this.frdbzjh = frdbzjh;
    }

    public String getDlrzjlx() {
        return dlrzjlx;
    }

    public void setDlrzjlx(String dlrzjlx) {
        this.dlrzjlx = dlrzjlx;
    }

    public String getDlrzjh() {
        return dlrzjh;
    }

    public void setDlrzjh(String dlrzjh) {
        this.dlrzjh = dlrzjh;
    }

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public String getQlrtz() {
        return qlrtz;
    }

    public void setQlrtz(String qlrtz) {
        this.qlrtz = qlrtz;
    }

    @Override
    public String toString() {
        return "DjxxQlrDTO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrlxdh='" + qlrlxdh + '\'' +
                ", zjzl='" + zjzl + '\'' +
                ", zjh='" + zjh + '\'' +
                ", txdz='" + txdz + '\'' +
                ", yb='" + yb + '\'' +
                ", frmc='" + frmc + '\'' +
                ", frdbzjlx='" + frdbzjlx + '\'' +
                ", frdbzjh='" + frdbzjh + '\'' +
                ", frdh='" + frdh + '\'' +
                ", dlrmc='" + dlrmc + '\'' +
                ", dlrzjlx='" + dlrzjlx + '\'' +
                ", dlrzjh='" + dlrzjh + '\'' +
                ", dlrdh='" + dlrdh + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", bz='" + bz + '\'' +
                ", sxh=" + sxh +
                ", qlmj=" + qlmj +
                ", ftmj=" + ftmj +
                ", qlrtz='" + qlrtz + '\'' +
                '}';
    }
}