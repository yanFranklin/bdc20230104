package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: realestate
 * @description: 领证人查询条件QO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-17 16:16
 **/
@ApiModel(value = "BdcLzrQO",description = "不动领证人查询参数封装对象")
public class BdcLzrQO implements Serializable {
    private static final long serialVersionUID = 5050529030964028967L;

    @ApiModelProperty(value = "领证人id")
    private String lzrid;

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "权利人id")
    private String qlrid;

    @ApiModelProperty(value = "领证人名称")
    private String lzrmc;

    @ApiModelProperty(value = "领证人名称")
    private String lzrzjh;

    public String getLzrid() {
        return lzrid;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
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

    @Override
    public String toString() {
        return "BdcLzrQO{" +
                "lzrid='" + lzrid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qlrid='" + qlrid + '\'' +
                ", lzrmc='" + lzrmc + '\'' +
                ", lzrzjh='" + lzrzjh + '\'' +
                '}';
    }
}
