package cn.gtmap.realestate.common.core.dto.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@ApiModel(value = "BdcQscxDyaXxDTO", description = "权属查询抵押权信息")
public class BdcQscxDyaXxDTO {

    @ApiModelProperty("抵押权人")
    private String qlr;

    @ApiModelProperty("不动产权证明号")
    private String bdcqzmh;

    @ApiModelProperty("被担保主债权数额")
    private String bdbzzqse;

    @ApiModelProperty("最高债权确定数额")
    private String zgzqqdse;

    @ApiModelProperty("抵押面积")
    private String dymj;

    @ApiModelProperty("附记")
    private String fj;

    @ApiModelProperty("抵押方式名称")
    private String dyfs;

    @ApiModelProperty("抵押方式")
    private Integer dyfsdm;

    @ApiModelProperty(value = "债务履行起始时间", example = "2018-12-20 20:00:00")
    private String zwlxqssj;

    @ApiModelProperty(value = "债务履行结束时间", example = "2018-12-20 20:00:00")
    private String zwlxjssj;

    @ApiModelProperty(value = "登簿时间", example = "2018-12-20 20:00:00")
    private String dbsj;

    @ApiModelProperty("是否注销")
    private boolean sfzx;

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getBdcqzmh() {
        return bdcqzmh;
    }

    public void setBdcqzmh(String bdcqzmh) {
        this.bdcqzmh = bdcqzmh;
    }

    public String getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(String bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public String getZgzqqdse() {
        return zgzqqdse;
    }

    public void setZgzqqdse(String zgzqqdse) {
        this.zgzqqdse = zgzqqdse;
    }

    public String getDymj() {
        return dymj;
    }

    public void setDymj(String dymj) {
        this.dymj = dymj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public Integer getDyfsdm() {
        return dyfsdm;
    }

    public void setDyfsdm(Integer dyfsdm) {
        this.dyfsdm = dyfsdm;
    }

    public String getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(String zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public String getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(String zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public String getDbsj() {
        return dbsj;
    }

    public void setDbsj(String dbsj) {
        this.dbsj = dbsj;
    }

    public boolean isSfzx() {
        return sfzx;
    }

    public void setSfzx(boolean sfzx) {
        this.sfzx = sfzx;
    }
}
