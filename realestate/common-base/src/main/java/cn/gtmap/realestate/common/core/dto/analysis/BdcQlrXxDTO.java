package cn.gtmap.realestate.common.core.dto.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@ApiModel(value = "BdcQlrXxDTO", description = "权利人信息")
public class BdcQlrXxDTO {
    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("证件种类名称")
    private String zjzl;

    @ApiModelProperty("证件种类代码")
    private Integer zjzldm;

    @ApiModelProperty("证件号")
    private String zjh;

    @ApiModelProperty("共有方式名称")
    private String gyfs;

    @ApiModelProperty("共有方式代码")
    private Integer gyfsdm;

    @ApiModelProperty("权利比例")
    private String qlbl;

    @ApiModelProperty(value = "发证日期", example = "2018-12-20")
    private String fzrq;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }
    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public Integer getZjzldm() {
        return zjzldm;
    }

    public void setZjzldm(Integer zjzldm) {
        this.zjzldm = zjzldm;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public Integer getGyfsdm() {
        return gyfsdm;
    }

    public void setGyfsdm(Integer gyfsdm) {
        this.gyfsdm = gyfsdm;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public String getFzrq() {
        return fzrq;
    }

    public void setFzrq(String fzrq) {
        this.fzrq = fzrq;
    }
}
