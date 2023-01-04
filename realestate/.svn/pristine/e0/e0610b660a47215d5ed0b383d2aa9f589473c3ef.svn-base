package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: realestate
 * @description: 不动产领证人表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-17 15:05
 **/
@Table(
        name = "BDC_LZR"
)
@ApiModel(value = "BdcLzrDO",description = "不动产领证人")
public class BdcLzrDO {
    @Id
    @ApiModelProperty(value = "领证人id")
    private String lzrid;
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "权利人id")
    private String qlrid;
    @ApiModelProperty(value = "领证人名称")
    private String lzrmc;
    @ApiModelProperty(value = "领证人证件种类")
    private Integer lzrzjzl;
    @ApiModelProperty(value = "领证人证件号")
    private String lzrzjh;
    @ApiModelProperty(value = "领证人电话")
    private String lzrdh;

    @ApiModelProperty(value = "领证方式")
    private Integer lzfs;

    public String getLzrid() {
        return lzrid;
    }

    public void setLzrid(String lzrid) {
        this.lzrid = lzrid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getLzrmc() {
        return lzrmc;
    }

    public void setLzrmc(String lzrmc) {
        this.lzrmc = lzrmc;
    }

    public Integer getLzrzjzl() {
        return lzrzjzl;
    }

    public void setLzrzjzl(Integer lzrzjzl) {
        this.lzrzjzl = lzrzjzl;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
    }

    public String getLzrdh() {
        return lzrdh;
    }

    public void setLzrdh(String lzrdh) {
        this.lzrdh = lzrdh;
    }

    public Integer getLzfs() {
        return lzfs;
    }

    public void setLzfs(Integer lzfs) {
        this.lzfs = lzfs;
    }

    @Override
    public String toString() {
        return "BdcLzrDO{" +
                "lzrid='" + lzrid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qlrid='" + qlrid + '\'' +
                ", lzrmc='" + lzrmc + '\'' +
                ", lzrzjzl=" + lzrzjzl +
                ", lzrzjh='" + lzrzjh + '\'' +
                ", lzrdh='" + lzrdh + '\'' +
                ", lzfs=" + lzfs +
                '}';
    }
}
