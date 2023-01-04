package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2021/1/20
 * @description 机构和领证人关系
 */
@Table(
        name = "BDC_JG_LZR_GX"
)
@ApiModel(value = "BdcJgLzrGxDO",description = "机构和领证人关系")
public class BdcJgLzrGxDO {

    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;

    @ApiModelProperty(value = "机构ID")
    private String jgid;

    @ApiModelProperty(value = "领证人名称")
    private String lzrmc;

    @ApiModelProperty(value = "领证人证件种类")
    private Integer lzrzjzl;

    @ApiModelProperty(value = "领证人证件号")
    private String lzrzjh;

    @ApiModelProperty(value = "领证人电话")
    private String lzrdh;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getJgid() {
        return jgid;
    }

    public void setJgid(String jgid) {
        this.jgid = jgid;
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

    @Override
    public String toString() {
        return "BdcJgLzrGxDO{" +
                "gxid='" + gxid + '\'' +
                ", jgid='" + jgid + '\'' +
                ", lzrmc='" + lzrmc + '\'' +
                ", lzrzjzl=" + lzrzjzl +
                ", lzrzjh='" + lzrzjh + '\'' +
                ", lzrdh=" + lzrdh +
                '}';
    }
}
