package cn.gtmap.realestate.common.core.domain.natural;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author
 * @description 自然资源海域
 */
@Table(name="ZRZY_HY")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = ZrzyHyDO.class)
@ApiModel(value="ZrzyHyDO",description = "自然资源海域")
@Data
public class ZrzyHyDO implements Serializable,ZrzyZrzk {
    /**
     * 自然状况信息ID
     */
    @Id
    @ApiModelProperty(value="自然状况信息ID")
    private String zkxxid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 自然资源单元号
     */
    @ApiModelProperty(value="自然资源单元号")
    private String zrzydjdyh;

    /**
     * 国有面积
     */
    @ApiModelProperty(value="国有面积")
    private Double gymj;

    /**
     * 集体面积
     */
    @ApiModelProperty(value="集体面积")
    private Double jtmj;

    /**
     * 争议区面积
     */
    @ApiModelProperty(value="争议区面积")
    private Double zyqmj;

    /**
     * 海域面积
     */
    @ApiModelProperty(value="海域面积")
    private Double hymj;

    /**
     * 海域等别
     */
    @ApiModelProperty(value="海域等别")
    private String hydb;

    /**
     * 大陆海岸线长度
     */
    @ApiModelProperty(value="大陆海岸线长度")
    private Double dlhaxcd;

    /**
     * 有居民海岛海岸线长度
     */
    @ApiModelProperty(value="有居民海岛海岸线长度")
    private Double yjmhdhaxcd;

    /**
     * 区县代码
     */
    @ApiModelProperty(value="区县代码")
    private String qxdm;

    public String getZkxxid() {
        return zkxxid;
    }

    public void setZkxxid(String zkxxid) {
        this.zkxxid = zkxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public Double getGymj() {
        return gymj;
    }

    public void setGymj(Double gymj) {
        this.gymj = gymj;
    }

    public Double getJtmj() {
        return jtmj;
    }

    public void setJtmj(Double jtmj) {
        this.jtmj = jtmj;
    }

    public Double getZyqmj() {
        return zyqmj;
    }

    public void setZyqmj(Double zyqmj) {
        this.zyqmj = zyqmj;
    }

    public Double getHymj() {
        return hymj;
    }

    public void setHymj(Double hymj) {
        this.hymj = hymj;
    }

    public String getHydb() {
        return hydb;
    }

    public void setHydb(String hydb) {
        this.hydb = hydb;
    }

    public Double getDlhaxcd() {
        return dlhaxcd;
    }

    public void setDlhaxcd(Double dlhaxcd) {
        this.dlhaxcd = dlhaxcd;
    }

    public Double getYjmhdhaxcd() {
        return yjmhdhaxcd;
    }

    public void setYjmhdhaxcd(Double yjmhdhaxcd) {
        this.yjmhdhaxcd = yjmhdhaxcd;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
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
        ZrzyHyDO other = (ZrzyHyDO) that;
        return (this.getZkxxid() == null ? other.getZkxxid() == null : this.getZkxxid().equals(other.getZkxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
            && (this.getHymj() == null ? other.getHymj() == null : this.getHymj().equals(other.getHymj()))
            && (this.getHydb() == null ? other.getHydb() == null : this.getHydb().equals(other.getHydb()))
            && (this.getDlhaxcd() == null ? other.getDlhaxcd() == null : this.getDlhaxcd().equals(other.getDlhaxcd()))
            && (this.getYjmhdhaxcd() == null ? other.getYjmhdhaxcd() == null : this.getYjmhdhaxcd().equals(other.getYjmhdhaxcd()))
            && (this.getQxdm() == null ? other.getQxdm() == null : this.getQxdm().equals(other.getQxdm()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZkxxid() == null) ? 0 : getZkxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getHymj() == null) ? 0 : getHymj().hashCode());
        result = prime * result + ((getHydb() == null) ? 0 : getHydb().hashCode());
        result = prime * result + ((getDlhaxcd() == null) ? 0 : getDlhaxcd().hashCode());
        result = prime * result + ((getYjmhdhaxcd() == null) ? 0 : getYjmhdhaxcd().hashCode());
        result = prime * result + ((getQxdm() == null) ? 0 : getQxdm().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", zkxxid=").append(zkxxid);
        sb.append(", xmid=").append(xmid);
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", hymj=").append(hymj);
        sb.append(", hydb=").append(hydb);
        sb.append(", dlhaxcd=").append(dlhaxcd);
        sb.append(", yjmhdhaxcd=").append(yjmhdhaxcd);
        sb.append(", qxdm=").append(qxdm);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}