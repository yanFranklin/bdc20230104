package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 房产与土地存量数据匹配关系
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/28.
 * @description
 */
@Table(name = "BDC_FCTD_PPGX")
public class BdcFctdPpgxDO {
    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;
    @ApiModelProperty(value = "房屋产权项目ID")
    private String fwcqxmid;
    @ApiModelProperty(value = "土地产权项目ID")
    private String tdcqxmid;
    @ApiModelProperty(value = "操作人")
    private String czr;
    @ApiModelProperty(value = "操作时间")
    private Date czsj;
    @ApiModelProperty(value = "关系类别 1:单元号落宗 2:房地匹配 3:复制土地项目匹配 4：单元号更正")
    private Integer gxlb;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getFwcqxmid() {
        return fwcqxmid;
    }

    public void setFwcqxmid(String fwcqxmid) {
        this.fwcqxmid = fwcqxmid;
    }

    public String getTdcqxmid() {
        return tdcqxmid;
    }

    public void setTdcqxmid(String tdcqxmid) {
        this.tdcqxmid = tdcqxmid;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public Integer getGxlb() {
        return gxlb;
    }

    public void setGxlb(Integer gxlb) {
        this.gxlb = gxlb;
    }
}
