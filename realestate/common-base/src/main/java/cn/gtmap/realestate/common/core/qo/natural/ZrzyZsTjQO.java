package cn.gtmap.realestate.common.core.qo.natural;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-08-29
 * @description 证书统计查询QO
 */
@Api(value = "BdcZsTjQO", description = "证书统计查询QO")
public class ZrzyZsTjQO implements Serializable {
    private static final long serialVersionUID = 1046146341204720534L;

    @ApiModelProperty("证书类型")
    private String zslx;
    @ApiModelProperty("证书类型集合")
    private List zslxlist;
    @ApiModelProperty("开始时间")
    private String kssj;
    @ApiModelProperty("结束时间")
    private String jzsj;
    @ApiModelProperty("部门")
    private String djbmdm;
    @ApiModelProperty("部门集合")
    private List djbmdmlist;
    @ApiModelProperty("登记机构")
    private String djjg;
    @ApiModelProperty("发证量统计标识")
    private String fzltj;

    public String getFzltj() {
        return fzltj;
    }

    public void setFzltj(String fzltj) {
        this.fzltj = fzltj;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getZslx() {
        return zslx;
    }

    public void setZslx(String zslx) {
        this.zslx = zslx;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List getZslxlist() {
        return zslxlist;
    }

    public void setZslxlist(List zslxlist) {
        this.zslxlist = zslxlist;
    }

    public List getDjbmdmlist() {
        return djbmdmlist;
    }

    public void setDjbmdmlist(List djbmdmlist) {
        this.djbmdmlist = djbmdmlist;
    }

    @Override
    public String toString() {
        return "BdcZsTjQO{" +
                "zslx='" + zslx + '\'' +
                ", zslxlist=" + zslxlist +
                ", kssj='" + kssj + '\'' +
                ", jzsj='" + jzsj + '\'' +
                ", djbmdm='" + djbmdm + '\'' +
                ", djbmdmlist=" + djbmdmlist +
                ", djjg='" + djjg + '\'' +
                ", fzltj='" + fzltj + '\'' +
                '}';
    }
}
