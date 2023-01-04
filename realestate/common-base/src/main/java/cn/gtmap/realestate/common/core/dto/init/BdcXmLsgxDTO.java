package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0, 2019/12/18
 * @description 不动产项目历史关系
 */
@ApiModel(value = "BdcXmLsgxDTO", description = "不动产项目历史关系")
public class BdcXmLsgxDTO {
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产项目")
    private BdcXmDO bdcXmDO;

    @ApiModelProperty(value = "上一手")
    List<BdcXmLsgxDTO> parentXm;

    @ApiModelProperty(value = "下一手")
    List<BdcXmLsgxDTO> sonXm;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public BdcXmDO getBdcXmDO() {
        return bdcXmDO;
    }

    public void setBdcXmDO(BdcXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
    }

    public List<BdcXmLsgxDTO> getParentXm() {
        return parentXm;
    }

    public void setParentXm(List<BdcXmLsgxDTO> parentXm) {
        this.parentXm = parentXm;
    }

    public List<BdcXmLsgxDTO> getSonXm() {
        return sonXm;
    }

    public void setSonXm(List<BdcXmLsgxDTO> sonXm) {
        this.sonXm = sonXm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BdcXmLsgxDTO that = (BdcXmLsgxDTO) o;
        return Objects.equals(xmid, that.xmid) && Objects.equals(bdcdyh, that.bdcdyh) && Objects.equals(zl, that.zl) && Objects.equals(bdcXmDO, that.bdcXmDO) && Objects.equals(parentXm, that.parentXm) && Objects.equals(sonXm, that.sonXm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmid, bdcdyh, zl, bdcXmDO, parentXm, sonXm);
    }

    @Override
    public String toString() {
        return "BdcXmLsgxDTO{" +
                "xmid='" + xmid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcXmDO=" + bdcXmDO +
                ", parentXm=" + parentXm +
                ", sonXm=" + sonXm +
                '}';
    }
}
