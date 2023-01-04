package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

/**
 * 交接单传输对象
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/08/23
 */
public class BdcAjxxDTO {

    /**
     * 工作流实例 ID
     */
    @ApiModelProperty(value = "工作流实例 ID")
    private String gzlslid;
    /**
     * 受理编号
     */
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**
     * 不动产权证号
     */
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;
    /**
     * 项目 ID
     */
    @ApiModelProperty(value = "项目 ID")
    private String xmid;
    /**
     * 案件状态
     */
    @ApiModelProperty(value = "案件状态")
    private Integer ajzt;
    /**
     * 交接单 ID
     */
    @ApiModelProperty(value = "交接单 ID")
    private String jjdid;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getJjdid() {
        return jjdid;
    }

    public void setJjdid(String jjdid) {
        this.jjdid = jjdid;
    }

    public Integer getAjzt() {
        return ajzt;
    }

    public void setAjzt(Integer ajzt) {
        this.ajzt = ajzt;
    }

    @Override
    public String toString() {
        return "BdcAjxxDTO{" +
                "gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", zl='" + zl + '\'' +
                ", ajzt='" + ajzt +
                ", xmid='" + xmid + '\'' +
                ", jjdid='" + jjdid + '\'' +
                '}';
    }
}
