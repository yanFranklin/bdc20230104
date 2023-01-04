package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/22
 * @description 资金监管
 */
@Table(name = "BDC_SL_ZJJG")
@ApiModel(value = "BdcSlZjjgDO", description = "受理资金监管对象")
public class BdcSlZjjgDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String zjjgid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "行政区代码")
    private String xzqdm;

    @ApiModelProperty(value = "产权证号")
    private String cqzh;

    @ApiModelProperty(value = "房屋坐落")
    private String fwzl;

    @ApiModelProperty(value = "监管确认信息")
    private String zjjgqrxx;

    @ApiModelProperty(value = "产权xmid")
    private String cqxmid;

    @ApiModelProperty(value = "状态, 1为撤销，0为未撤销")
    private Integer zt;

    @ApiModelProperty(value = "撤销原因")
    private String cxyy;

    @ApiModelProperty(value = "受理项目ID")
    private String xmid;

    public String getZjjgid() {
        return zjjgid;
    }

    public void setZjjgid(String zjjgid) {
        this.zjjgid = zjjgid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getZjjgqrxx() {
        return zjjgqrxx;
    }

    public void setZjjgqrxx(String zjjgqrxx) {
        this.zjjgqrxx = zjjgqrxx;
    }

    public String getCqxmid() {
        return cqxmid;
    }

    public void setCqxmid(String cqxmid) {
        this.cqxmid = cqxmid;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getCxyy() {
        return cxyy;
    }

    public void setCxyy(String cxyy) {
        this.cxyy = cxyy;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    @Override
    public String toString() {
        return "BdcSlZjjgDO{" +
                "zjjgid='" + zjjgid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xzqdm='" + xzqdm + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", fwzl='" + fwzl + '\'' +
                ", zjjgqrxx='" + zjjgqrxx + '\'' +
                ", cqxmid='" + cqxmid + '\'' +
                ", zt=" + zt +
                ", cxyy='" + cxyy + '\'' +
                ", xmid='" + xmid + '\'' +
                '}';
    }
}
