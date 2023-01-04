package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ZRZY_GG
 * @author 
 */
@Table(name="ZRZY_GG")
@Data
public class ZrzyGgDO implements Serializable {
    /**
     * 公告ID
     */
    @Id
    @ApiModelProperty(value="公告ID")
    private String ggid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 公告类型
     */
    @ApiModelProperty(value="公告类型")
    private Short gglx;

    /**
     * 公告标题
     */
    @ApiModelProperty(value="公告标题")
    private String ggbt;

    /**
     * 公告内容
     */
    @ApiModelProperty(value="公告内容")
    private String ggnr;

    /**
     * 公告开始时间
     */
    @ApiModelProperty(value="公告开始时间")
    private Date ggkssj;

    /**
     * 公告结束时间
     */
    @ApiModelProperty(value="公告结束时间")
    private Date ggjssj;

    private static final long serialVersionUID = 1L;

    public String getGgid() {
        return ggid;
    }

    public void setGgid(String ggid) {
        this.ggid = ggid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Short getGglx() {
        return gglx;
    }

    public void setGglx(Short gglx) {
        this.gglx = gglx;
    }

    public String getGgbt() {
        return ggbt;
    }

    public void setGgbt(String ggbt) {
        this.ggbt = ggbt;
    }

    public String getGgnr() {
        return ggnr;
    }

    public void setGgnr(String ggnr) {
        this.ggnr = ggnr;
    }

    public Date getGgkssj() {
        return ggkssj;
    }

    public void setGgkssj(Date ggkssj) {
        this.ggkssj = ggkssj;
    }

    public Date getGgjssj() {
        return ggjssj;
    }

    public void setGgjssj(Date ggjssj) {
        this.ggjssj = ggjssj;
    }

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
        ZrzyGgDO other = (ZrzyGgDO) that;
        return (this.getGgid() == null ? other.getGgid() == null : this.getGgid().equals(other.getGgid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getGglx() == null ? other.getGglx() == null : this.getGglx().equals(other.getGglx()))
            && (this.getGgbt() == null ? other.getGgbt() == null : this.getGgbt().equals(other.getGgbt()))
            && (this.getGgnr() == null ? other.getGgnr() == null : this.getGgnr().equals(other.getGgnr()))
            && (this.getGgkssj() == null ? other.getGgkssj() == null : this.getGgkssj().equals(other.getGgkssj()))
            && (this.getGgjssj() == null ? other.getGgjssj() == null : this.getGgjssj().equals(other.getGgjssj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGgid() == null) ? 0 : getGgid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getGglx() == null) ? 0 : getGglx().hashCode());
        result = prime * result + ((getGgbt() == null) ? 0 : getGgbt().hashCode());
        result = prime * result + ((getGgnr() == null) ? 0 : getGgnr().hashCode());
        result = prime * result + ((getGgkssj() == null) ? 0 : getGgkssj().hashCode());
        result = prime * result + ((getGgjssj() == null) ? 0 : getGgjssj().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ggid=").append(ggid);
        sb.append(", xmid=").append(xmid);
        sb.append(", gglx=").append(gglx);
        sb.append(", ggbt=").append(ggbt);
        sb.append(", ggnr=").append(ggnr);
        sb.append(", ggkssj=").append(ggkssj);
        sb.append(", ggjssj=").append(ggjssj);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}