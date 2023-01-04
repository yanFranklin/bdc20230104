package cn.gtmap.realestate.common.core.dto.certificate;

import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/11/20
 * @description 项目归档信息
 */
public class BdcXmGdxxDTO extends BdcGdxxDO implements Serializable {

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "移交单id")
    private String yjdid;


    @ApiModelProperty(value = "数据来源")
    private String sjly = "0";


    public String getYjdid() {
        return yjdid;
    }

    public void setYjdid(String yjdid) {
        this.yjdid = yjdid;
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

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    @Override
    public String toString() {
        return "BdcXmGdxxDTO{" +
                "bdcqzh='" + bdcqzh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", yjdid='" + yjdid + '\'' +
                '}';
    }
}
