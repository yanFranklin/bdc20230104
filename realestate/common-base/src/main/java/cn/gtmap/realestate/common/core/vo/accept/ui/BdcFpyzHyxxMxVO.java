package cn.gtmap.realestate.common.core.vo.accept.ui;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/22
 * @description 分屏验证-婚姻信息明细
 */
@ApiModel(value = "BdcFpyzHyxxMxVO", description = "分屏验证-婚姻信息明细")
public class BdcFpyzHyxxMxVO {

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "证件号码")
    private String zjhm;

    @ApiModelProperty(value = "配偶姓名")
    private String poxm;

    @ApiModelProperty(value = "配偶证件号码")
    private String pozjhm;


    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getPoxm() {
        return poxm;
    }

    public void setPoxm(String poxm) {
        this.poxm = poxm;
    }

    public String getPozjhm() {
        return pozjhm;
    }

    public void setPozjhm(String pozjhm) {
        this.pozjhm = pozjhm;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcFpyzHyxxMxVO{");
        sb.append("xm='").append(xm).append('\'');
        sb.append(", zjhm='").append(zjhm).append('\'');
        sb.append(", poxm='").append(poxm).append('\'');
        sb.append(", pozjhm='").append(pozjhm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
