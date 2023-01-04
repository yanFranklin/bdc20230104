package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/21.
 * @description 不动产受理评价统计QO
 */
@ApiModel(value = "BdcSlPjqQO", description = "评价统计QO")
public class BdcSlPjqQO {

    @ApiModelProperty(value = "办理人员")
    private String blry;
    @ApiModelProperty(value = "办理人员id")
    private String blryid;
    @ApiModelProperty(value = "部门代码")
    private String djbmdm;
    @ApiModelProperty(value = "部门名称")
    private String djbmmc;
    @ApiModelProperty(value = "节点名称")
    private String jdmc;
    @ApiModelProperty(value = "业务名称")
    private String ywmc;
    @ApiModelProperty(value = "业务编号")
    private String ywbh;
    @ApiModelProperty(value = "开始时间")
    private String kssj;
    @ApiModelProperty(value = "截止时间")
    private String jzsj;
    @ApiModelProperty(value = "统计维度")
    private String tjwd;

    public String getBlry() {
        return blry;
    }

    public void setBlry(String blry) {
        this.blry = blry;
    }

    public String getBlryid() {
        return blryid;
    }

    public void setBlryid(String blryid) {
        this.blryid = blryid;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    public String getDjbmmc() {
        return djbmmc;
    }

    public void setDjbmmc(String djbmmc) {
        this.djbmmc = djbmmc;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
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

    public String getTjwd() {
        return tjwd;
    }

    public void setTjwd(String tjwd) {
        this.tjwd = tjwd;
    }
}
