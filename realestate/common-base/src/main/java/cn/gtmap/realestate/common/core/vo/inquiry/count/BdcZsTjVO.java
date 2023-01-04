package cn.gtmap.realestate.common.core.vo.inquiry.count;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-08-29
 * @description 证书、证明 统计VO
 */
@Api(value = "BdcZsTjVO", description = "证书、证明统计VO")
public class BdcZsTjVO implements Serializable {

    private static final long serialVersionUID = -4396504710811419568L;
    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Long sl;
    /**
     * 部门
     */
    @ApiModelProperty("部门")
    private String djbmdm;
    /**
     * 证书类型
     */
    @ApiModelProperty("证书类型")
    private String zslx;
    /**
     *
     */
    @ApiModelProperty("登记机构")
    private String djjg;

    public Long getSl() {
        return sl;
    }

    public void setSl(Long sl) {
        this.sl = sl;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    public String getZslx() {
        return zslx;
    }

    public void setZslx(String zslx) {
        this.zslx = zslx;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    @Override
    public String toString() {
        return "BdcZsTjVO{" +
                "sl=" + sl +
                ", djbmdm='" + djbmdm + '\'' +
                ", zslx='" + zslx + '\'' +
                ", djjg='" + djjg + '\'' +
                '}';
    }
}
