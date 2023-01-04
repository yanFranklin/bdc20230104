package cn.gtmap.realestate.common.core.qo.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
 * @version 1.0  2021-11-17
 * @description 根据籍权信息查询宗地信息入参
 */
@ApiModel(value = "ZdxxByJqxxQO", description = "根据籍权信息查询宗地信息查询QO对象")
public class ZdxxByJqxxQO {
    @ApiModelProperty(value = "坐落（左模糊）")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "地籍号")
    private String djh;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    @Override
    public String toString() {
        return "ZdxxByJqxxQO{" +
                "zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", djh='" + djh + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                '}';
    }
}
