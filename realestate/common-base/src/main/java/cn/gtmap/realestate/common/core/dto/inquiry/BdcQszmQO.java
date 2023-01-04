package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/15
 * @description 查询子系统抵权属证明打印参数QO
 */
public class BdcQszmQO {
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权属状态")
    private String qszt;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "查询号")
    private String cxh;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "当前用户名称")
    private String username;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;

    @ApiModelProperty(value = "查阅日期")
    private String cyrq;
    @ApiModelProperty(value = "是否查询房地匹配信息")
    private String sfcxfdppxx;

    public String getCyrq() {
        return cyrq;
    }

    public void setCyrq(String cyrq) {
        this.cyrq = cyrq;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getCxh() {
        return cxh;
    }

    public void setCxh(String cxh) {
        this.cxh = cxh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSfcxfdppxx() {return sfcxfdppxx;}

    public void setSfcxfdppxx(String sfcxfdppxx) {this.sfcxfdppxx = sfcxfdppxx;}

    @Override
    public String toString() {
        return "BdcQszmQO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", qszt='" + qszt + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", cxh='" + cxh + '\'' +
                ", username='" + username + '\'' +
                ", xmid='" + xmid + '\'' +
                ", cyrq='" + cyrq + '\'' +
                ", sfcxfdppxx='" + sfcxfdppxx + '\'' +
                ", bdclx=" + bdclx +
                '}';
    }
}
