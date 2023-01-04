package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ZRZY_XM_LSGX
 * @author 
 */
@Table(name="ZRZY_XM_LSGX")
@ApiModel(value="generate.ZrzyXmLsgxDo")
@Data
public class ZrzyXmLsgxDO implements Serializable {
    /**
     * 关系ID
     */
    @Id
    @ApiModelProperty(value="关系ID")
    private String gxid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 原项目ID
     */
    @ApiModelProperty(value="原项目ID")
    private String yxmid;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ZrzyXmLsgxDO other = (ZrzyXmLsgxDO) that;
        return (this.getGxid() == null ? other.getGxid() == null : this.getGxid().equals(other.getGxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getYxmid() == null ? other.getYxmid() == null : this.getYxmid().equals(other.getYxmid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGxid() == null) ? 0 : getGxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getYxmid() == null) ? 0 : getYxmid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gxid=").append(gxid);
        sb.append(", xmid=").append(xmid);
        sb.append(", yxmid=").append(yxmid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}