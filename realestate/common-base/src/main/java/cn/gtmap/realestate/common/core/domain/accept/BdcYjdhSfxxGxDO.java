package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/10
 * @description 不动产银行月结单号与收费信息关系表
 */
@Table(name = "BDC_YJDH_SFXX_GX")
@ApiModel(value = "BdcYjdhSfxxGxDO", description = "不动产银行月结单号与收费信息关系表")
public class BdcYjdhSfxxGxDO {

    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;

    @ApiModelProperty(value = "月结单号")
    private String yjdh;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "收费信息ID")
    private String sfxxid;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getYjdh() {
        return yjdh;
    }

    public void setYjdh(String yjdh) {
        this.yjdh = yjdh;
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

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcYjdhSfxxGxDO{");
        sb.append("gxid='").append(gxid).append('\'');
        sb.append(", yjdh='").append(yjdh).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append(", sfxxid='").append(sfxxid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
