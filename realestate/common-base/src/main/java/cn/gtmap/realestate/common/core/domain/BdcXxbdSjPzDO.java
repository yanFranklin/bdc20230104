package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/27
 * @description 不动产信息比对数据配置
 */
@Table(name = "BDC_XXBD_SJ_PZ")
@ApiModel(value = "BdcXxbdSjPzDO", description = "不动产信息比对数据配置")
public class BdcXxbdSjPzDO {

    @Id
    @ApiModelProperty(value = "配置ID")
    private String pzid;

    @ApiModelProperty(value = "比对类型")
    private String bdlx;

    @ApiModelProperty(value = "数据来源名称")
    private String sjlymc;

    @ApiModelProperty(value = "参数")
    private String cs;

    @ApiModelProperty(value = "请求方式：SQL/服务")
    private String qqfs;

    @ApiModelProperty(value = "请求内容")
    private String qqnr;

    @ApiModelProperty(value = "请求应用，调用服务时")
    private String qqyy;

    @ApiModelProperty(value = "数据源，调用SQL时")
    private String sjy;

    @ApiModelProperty(value = "配置人姓名")
    private String pzrxm;

    @ApiModelProperty(value = "配置时间")
    private Date pzsj;

    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    public String getBdlx() {
        return bdlx;
    }

    public void setBdlx(String bdlx) {
        this.bdlx = bdlx;
    }

    public String getSjlymc() {
        return sjlymc;
    }

    public void setSjlymc(String sjlymc) {
        this.sjlymc = sjlymc;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getQqfs() {
        return qqfs;
    }

    public void setQqfs(String qqfs) {
        this.qqfs = qqfs;
    }

    public String getQqnr() {
        return qqnr;
    }

    public void setQqnr(String qqnr) {
        this.qqnr = qqnr;
    }

    public String getQqyy() {
        return qqyy;
    }

    public void setQqyy(String qqyy) {
        this.qqyy = qqyy;
    }


    public String getSjy() {
        return sjy;
    }

    public void setSjy(String sjy) {
        this.sjy = sjy;
    }

    public String getPzrxm() {
        return pzrxm;
    }

    public void setPzrxm(String pzrxm) {
        this.pzrxm = pzrxm;
    }

    public Date getPzsj() {
        return pzsj;
    }

    public void setPzsj(Date pzsj) {
        this.pzsj = pzsj;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }
}
