package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 常州查询证明明细权利人信息
 */
public class BdcCxzmdPdfQlrDTO {
    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @ApiModelProperty(value = "房屋所有权证号/不动产权证号")
    private String fwsyqzh;

    @ApiModelProperty(value = "土地使用权证号")
    private String tdsyqzh;

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getFwsyqzh() {
        return fwsyqzh;
    }

    public void setFwsyqzh(String fwsyqzh) {
        this.fwsyqzh = fwsyqzh;
    }

    public String getTdsyqzh() {
        return tdsyqzh;
    }

    public void setTdsyqzh(String tdsyqzh) {
        this.tdsyqzh = tdsyqzh;
    }
}
