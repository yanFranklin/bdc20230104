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
 * @description 自然资源无居民海岛
 */
@Table(name="ZRZY_WJMHD")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = ZrzyWjmhdDO.class)
@ApiModel(value = "ZrzyWjmhdDO",description = "自然资源无居民海岛")
@Data
public class ZrzyWjmhdDO implements Serializable,ZrzyZrzk {
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
     * 海岛名称
     */
    @ApiModelProperty(value="海岛名称")
    private String hdmc;

    /**
     * 海岛位置
     */
    @ApiModelProperty(value="海岛位置")
    private String hdwz;

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
     * 海岛面积
     */
    @ApiModelProperty(value="海岛面积")
    private Double hdmj;

    /**
     * 海岛类型
     */
    @ApiModelProperty(value="海岛类型")
    private String hdlx;

    /**
     * 海岛高程
     */
    @ApiModelProperty(value="海岛高程")
    private String hdgc;

    /**
     * 植被覆盖情况
     */
    @ApiModelProperty(value="植被覆盖情况")
    private String zbfgqk;

    /**
     * 岸线长度
     */
    @ApiModelProperty(value="岸线长度")
    private Double axcd;

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

    public String getHdmc() {
        return hdmc;
    }

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }

    public String getHdwz() {
        return hdwz;
    }

    public void setHdwz(String hdwz) {
        this.hdwz = hdwz;
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

    public Double getHdmj() {
        return hdmj;
    }

    public void setHdmj(Double hdmj) {
        this.hdmj = hdmj;
    }

    public String getHdlx() {
        return hdlx;
    }

    public void setHdlx(String hdlx) {
        this.hdlx = hdlx;
    }

    public String getHdgc() {
        return hdgc;
    }

    public void setHdgc(String hdgc) {
        this.hdgc = hdgc;
    }

    public String getZbfgqk() {
        return zbfgqk;
    }

    public void setZbfgqk(String zbfgqk) {
        this.zbfgqk = zbfgqk;
    }

    public Double getAxcd() {
        return axcd;
    }

    public void setAxcd(Double axcd) {
        this.axcd = axcd;
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
        ZrzyWjmhdDO other = (ZrzyWjmhdDO) that;
        return (this.getZkxxid() == null ? other.getZkxxid() == null : this.getZkxxid().equals(other.getZkxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getHdmc() == null ? other.getHdmc() == null : this.getHdmc().equals(other.getHdmc()))
            && (this.getHdwz() == null ? other.getHdwz() == null : this.getHdwz().equals(other.getHdwz()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
            && (this.getHdmj() == null ? other.getHdmj() == null : this.getHdmj().equals(other.getHdmj()))
            && (this.getHdlx() == null ? other.getHdlx() == null : this.getHdlx().equals(other.getHdlx()))
            && (this.getHdgc() == null ? other.getHdgc() == null : this.getHdgc().equals(other.getHdgc()))
            && (this.getZbfgqk() == null ? other.getZbfgqk() == null : this.getZbfgqk().equals(other.getZbfgqk()))
            && (this.getAxcd() == null ? other.getAxcd() == null : this.getAxcd().equals(other.getAxcd()))
            && (this.getQxdm() == null ? other.getQxdm() == null : this.getQxdm().equals(other.getQxdm()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZkxxid() == null) ? 0 : getZkxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getHdmc() == null) ? 0 : getHdmc().hashCode());
        result = prime * result + ((getHdwz() == null) ? 0 : getHdwz().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getHdmj() == null) ? 0 : getHdmj().hashCode());
        result = prime * result + ((getHdlx() == null) ? 0 : getHdlx().hashCode());
        result = prime * result + ((getHdgc() == null) ? 0 : getHdgc().hashCode());
        result = prime * result + ((getZbfgqk() == null) ? 0 : getZbfgqk().hashCode());
        result = prime * result + ((getAxcd() == null) ? 0 : getAxcd().hashCode());
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
        sb.append(", hdmc=").append(hdmc);
        sb.append(", hdwz=").append(hdwz);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", hdmj=").append(hdmj);
        sb.append(", hdlx=").append(hdlx);
        sb.append(", hdgc=").append(hdgc);
        sb.append(", zbfgqk=").append(zbfgqk);
        sb.append(", axcd=").append(axcd);
        sb.append(", qxdm=").append(qxdm);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}