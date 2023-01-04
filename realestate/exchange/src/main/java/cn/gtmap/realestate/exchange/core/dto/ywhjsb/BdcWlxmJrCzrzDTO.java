package cn.gtmap.realestate.exchange.core.dto.ywhjsb;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @program: bdcdj3.0
 * @description: 外联项目接入操作日志DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-12-05 17:30
 **/
public class BdcWlxmJrCzrzDTO {

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("操作内容")
    private String cznr;

    @ApiModelProperty("报文id")
    private String bwid;

    @ApiModelProperty("操作结果")
    private String czjg;

    @ApiModelProperty("操作类型")
    private Integer czlx;

    @ApiModelProperty("操作时间")
    private Date czsj;

    @ApiModelProperty("外联项目的关系id")
    private String gxid;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getCznr() {
        return cznr;
    }

    public void setCznr(String cznr) {
        this.cznr = cznr;
    }

    public String getBwid() {
        return bwid;
    }

    public void setBwid(String bwid) {
        this.bwid = bwid;
    }

    public String getCzjg() {
        return czjg;
    }

    public void setCzjg(String czjg) {
        this.czjg = czjg;
    }

    public Integer getCzlx() {
        return czlx;
    }

    public void setCzlx(Integer czlx) {
        this.czlx = czlx;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }
}
