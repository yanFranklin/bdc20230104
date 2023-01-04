package cn.gtmap.realestate.common.core.domain.engine;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description 不动产组合规则和子规则关联关系实体DO定义
 */
@Table(name = "BDC_GZ_GX")
public class BdcGzGxDO {
    /**
     * 关系ID
     */
    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;
    /**
     * 组合规则ID
     */
    @ApiModelProperty(value = "组合规则ID")
    private String zhid;
    /**
     * 规则ID
     */
    @ApiModelProperty(value = "规则ID")
    private String gzid;


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

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    @Override
    public String toString() {
        return "BdcGzGxDO{" +
                "gxid='" + gxid + '\'' +
                ", zhid='" + zhid + '\'' +
                ", gzid='" + gzid + '\'' +
                '}';
    }
}
