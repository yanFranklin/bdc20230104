package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 虚拟不动产单元号关系表
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/28.
 * @description
 */
@Table(name = "BDC_XNBDCDYH_GX")
public class BdcXnbdcdyhGxDO {
    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "虚拟不动产单元号")
    private String xnbdcdyh;
    @ApiModelProperty(value = "虚拟不动产单元号项目ID")
    private String xnbdcdyhxmid;
    @ApiModelProperty(value = "操作人")
    private String czr;
    @ApiModelProperty(value = "操作时间")
    private Date czsj;
    @ApiModelProperty(value = "关系类别 1:单元号落宗 2:房地匹配 3:复制土地项目匹配 4：单元号更正")
    private Integer gxlb;

    public String getXnbdcdyhxmid() {
        return xnbdcdyhxmid;
    }

    public void setXnbdcdyhxmid(String xnbdcdyhxmid) {
        this.xnbdcdyhxmid = xnbdcdyhxmid;
    }

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXnbdcdyh() {
        return xnbdcdyh;
    }

    public void setXnbdcdyh(String xnbdcdyh) {
        this.xnbdcdyh = xnbdcdyh;
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

    @Override
    public String toString() {
        return "BdcXnbdcdyhGxDO{" +
                "gxid='" + gxid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", xnbdcdyh='" + xnbdcdyh + '\'' +
                ", xnbdcdyhxmid='" + xnbdcdyhxmid + '\'' +
                ", czr='" + czr + '\'' +
                ", czsj=" + czsj +
                ", gxlb=" + gxlb +
                '}';
    }
}
