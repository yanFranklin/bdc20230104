package cn.gtmap.realestate.common.core.domain.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0  2018-11-10
 * @description 不动产规则组合关系
 */
@Table(name = "bdc_gz_zh_gx")
@ApiModel(value = "BdcGzZhGxDO", description = "不动产规则组合关系")
public class BdcGzZhGxDO implements Serializable {

    private static final long serialVersionUID = -652616675699809596L;
    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;

    @ApiModelProperty(value = "组合ID")
    private String zhid;

    @ApiModelProperty(value = "业务规则ID")
    private String ywgzid;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getZhid() {
        return zhid;
    }

    public void setZhid(String zhid) {
        this.zhid = zhid;
    }

    public String getYwgzid() {
        return ywgzid;
    }

    public void setYwgzid(String ywgzid) {
        this.ywgzid = ywgzid;
    }

    @Override
    public String toString() {
        return "BdcGzZhGxDO{" +
                "gxid='" + gxid + '\'' +
                ", zhid='" + zhid + '\'' +
                ", ywgzid='" + ywgzid + '\'' +
                '}';
    }
}
