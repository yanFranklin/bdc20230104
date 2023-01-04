package cn.gtmap.realestate.etl.core.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/15
 * @description 外网申请导出DTO
 */
public class WwsqExportDTO {

    @ApiModelProperty("打印类型")
    private String dylx;

    @ApiModelProperty("外网受理编号")
    private String wwslbh;

    @ApiModelProperty("外网项目ID")
    private String wwxmid;

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getWwslbh() {
        return wwslbh;
    }

    public void setWwslbh(String wwslbh) {
        this.wwslbh = wwslbh;
    }

    public String getWwxmid() {
        return wwxmid;
    }

    public void setWwxmid(String wwxmid) {
        this.wwxmid = wwxmid;
    }
}
