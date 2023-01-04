package cn.gtmap.realestate.exchange.core.dto.shucheng.fgj.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/4/20
 * @description 商品房、二手房合同信息,返回值中data的houses
 */
@ApiModel("舒城县房管局-商品房、二手房合同信息中的房屋信息")
public class ResponseHouse {

    @ApiModelProperty("全省统一房屋编号")
    private String fwtybh;

    @ApiModelProperty("房屋坐落")
    private String fwzl;

    @ApiModelProperty("房屋建筑面积(平方米)")
    private String jzmj;

    @ApiModelProperty("房屋用途(预售用途)")
    private String fwyt;

    @ApiModelProperty("房屋性质")
    private String fwxz;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    public String getFwtybh() {
        return fwtybh;
    }

    public void setFwtybh(String fwtybh) {
        this.fwtybh = fwtybh;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        return "ResponseHouse{" +
                "fwtybh='" + fwtybh + '\'' +
                ", fwzl='" + fwzl + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", fwyt='" + fwyt + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
