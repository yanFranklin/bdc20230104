package cn.gtmap.realestate.common.core.qo.inquiry.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/29
 * @description  不动产综合查询打印证明统计QO
 */
@ApiModel(value = "BdcZhcxDyzmTjQO", description = "不动产综合查询打印证明统计QO")
public class BdcZhcxDyzmTjQO {
    @ApiModelProperty(value = "统计维度")
    private String tjwd;

    @ApiModelProperty(value = "部门名称")
    private String bmmc;

    @ApiModelProperty(value = "人员名称")
    private String rymc;

    @ApiModelProperty("查询起始日期")
    private String kssj;

    @ApiModelProperty("查询截止日期")
    private String jzsj;

    @ApiModelProperty("打印类型")
    private List<String> printTypes;


    public String getTjwd() {
        return tjwd;
    }

    public void setTjwd(String tjwd) {
        this.tjwd = tjwd;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getRymc() {
        return rymc;
    }

    public void setRymc(String rymc) {
        this.rymc = rymc;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public List<String> getPrintTypes() {
        return printTypes;
    }

    public void setPrintTypes(List<String> printTypes) {
        this.printTypes = printTypes;
    }

    @Override
    public String toString() {
        return "BdcZhcxDyzmTjQO{" +
                "tjwd='" + tjwd + '\'' +
                ", bmmc='" + bmmc + '\'' +
                ", rymc='" + rymc + '\'' +
                ", kssj='" + kssj + '\'' +
                ", jzsj='" + jzsj + '\'' +
                ", printTypes=" + printTypes +
                '}';
    }
}
