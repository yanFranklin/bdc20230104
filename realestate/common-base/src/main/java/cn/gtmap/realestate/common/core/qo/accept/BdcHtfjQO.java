package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2021/5/12
 * @description 查询备案合同qo
 */
@ApiModel(value = "BdcHtfjQO", description = "合同附件qo")
public class BdcHtfjQO {

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "电子备案号")
    private String dzbah;

    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @ApiModelProperty(value = "宗地编号")
    private String zdbh;

    public String getHtbh() {return htbh;}

    public void setHtbh(String htbh) {this.htbh = htbh;}

    public String getDzbah() {return dzbah;}

    public void setDzbah(String dzbah) {this.dzbah = dzbah;}

    public String getXmmc() {return xmmc;}

    public void setXmmc(String xmmc) {this.xmmc = xmmc;}

    public String getZdbh() {return zdbh;}

    public void setZdbh(String zdbh) {this.zdbh = zdbh;}

    @Override
    public String toString() {
        return "BdcHtfjQO{" +
                "htbh='" + htbh + '\'' +
                ", dzbah='" + dzbah + '\'' +
                ", xmmc='" + xmmc + '\'' +
                ", zdbh='" + zdbh + '\'' +
                '}';
    }
}
