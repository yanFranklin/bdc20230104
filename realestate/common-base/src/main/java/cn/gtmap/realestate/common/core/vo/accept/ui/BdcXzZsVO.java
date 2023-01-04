package cn.gtmap.realestate.common.core.vo.accept.ui;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 修正流程更新证书信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-11-25 09:37
 **/
public class BdcXzZsVO {

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("年份")
    private String nf;

    @ApiModelProperty("证号流水号")
    private String zhlsh;

    @ApiModelProperty("省区市简称")
    private String sqsjc;

    @ApiModelProperty("所在市县全称")
    private String szsxqc;

    @ApiModelProperty("证号简称")
    private String bdcqzhjc;

    @ApiModelProperty("证号序列号")
    private Integer zhxlh;

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public String getSqsjc() {
        return sqsjc;
    }

    public void setSqsjc(String sqsjc) {
        this.sqsjc = sqsjc;
    }

    public String getSzsxqc() {
        return szsxqc;
    }

    public void setSzsxqc(String szsxqc) {
        this.szsxqc = szsxqc;
    }

    public String getBdcqzhjc() {
        return bdcqzhjc;
    }

    public void setBdcqzhjc(String bdcqzhjc) {
        this.bdcqzhjc = bdcqzhjc;
    }

    public Integer getZhxlh() {
        return zhxlh;
    }

    public void setZhxlh(Integer zhxlh) {
        this.zhxlh = zhxlh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    @Override
    public String toString() {
        return "BdcXzZsVO{" +
                "xmid='" + xmid + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", nf='" + nf + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", sqsjc='" + sqsjc + '\'' +
                ", szsxqc='" + szsxqc + '\'' +
                ", bdcqzhjc='" + bdcqzhjc + '\'' +
                ", zhxlh=" + zhxlh +
                '}';
    }
}
