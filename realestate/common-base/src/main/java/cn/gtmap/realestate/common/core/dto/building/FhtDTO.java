package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModelProperty;

public class FhtDTO {

    @ApiModelProperty(value = "页面传参base64")
    private String base64;

    @ApiModelProperty(value = "bdcdyh")
    private String bdcdyh;

    @ApiModelProperty(value = "当前页")
    private Integer nowPage;

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        return "FhtDTO{" +
                "base64='" + base64 + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", nowPage='" + nowPage + '\'' +
                '}';
    }
}
