package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/11/15
 * @description
 */
@Api(value = "BdcBaxxCxQO", description = "批量查询查询对象")
public class BdcBaxxCxQO {

    @ApiModelProperty("权利人")
    private String singleMsr;

    @ApiModelProperty("证件号")
    private String singleGmrzjhm;

    @ApiModelProperty("权利人集合")
    private List<String> qlr;

    @ApiModelProperty("证件号集合")
    private List<String> zjh;

    @ApiModelProperty("导出查询")
    private String export;

    @ApiModelProperty("自定义序号")
    private String zdyxhs;

    @ApiModelProperty("自定义序号集合")
    private List<String> zdyxhList;

    public String getSingleMsr() {
        return singleMsr;
    }

    public void setSingleMsr(String singleMsr) {
        this.singleMsr = singleMsr;
    }

    public String getSingleGmrzjhm() {
        return singleGmrzjhm;
    }

    public void setSingleGmrzjhm(String singleGmrzjhm) {
        this.singleGmrzjhm = singleGmrzjhm;
    }

    public List<String> getQlr() {
        return qlr;
    }

    public void setQlr(List<String> qlr) {
        this.qlr = qlr;
    }

    public List<String> getZjh() {
        return zjh;
    }

    public void setZjh(List<String> zjh) {
        this.zjh = zjh;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }

    public String getZdyxhs() {
        return zdyxhs;
    }

    public void setZdyxhs(String zdyxhs) {
        this.zdyxhs = zdyxhs;
    }

    public List<String> getZdyxhList() {
        return zdyxhList;
    }

    public void setZdyxhList(List<String> zdyxhList) {
        this.zdyxhList = zdyxhList;
    }

    @Override
    public String toString() {
        return "BdcBaxxCxQO{" +
                "singleMsr='" + singleMsr + '\'' +
                ", singleGmrzjhm='" + singleGmrzjhm + '\'' +
                ", qlr=" + qlr +
                ", zjh=" + zjh +
                ", export='" + export + '\'' +
                ", zdyxhs='" + zdyxhs + '\'' +
                ", zdyxhList=" + zdyxhList +
                '}';
    }
}
