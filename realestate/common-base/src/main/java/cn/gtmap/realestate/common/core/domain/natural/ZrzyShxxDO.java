package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ZRZY_SHXX
 * @author 
 */
@Table(name="ZRZY_SHXX")
@ApiModel(value="generate.ZrzyShxxDo")
@Data
public class ZrzyShxxDO implements Serializable {
    /**
     * 审核信息ID
     */
    @Id
    @ApiModelProperty(value="审核信息ID")
    private String shxxid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 节点名称
     */
    @ApiModelProperty(value="节点名称")
    private String jdmc;

    /**
     * 顺序号
     */
    @ApiModelProperty(value="顺序号")
    private Integer sxh;

    /**
     * 审核人员姓名
     */
    @ApiModelProperty(value="审核人员姓名")
    private String shryxm;

    /**
     * 审核人ID
     */
    @ApiModelProperty(value="审核人ID")
    private String shrid;

    /**
     * 审核开始时间
     */
    @ApiModelProperty(value="审核开始时间")
    private Date shkssj;

    /**
     * 审核结束时间
     */
    @ApiModelProperty(value="审核结束时间")
    private Date shjssj;

    /**
     * 审核意见
     */
    @ApiModelProperty(value="审核意见")
    private String shyj;

    /**
     * 签名时间
     */
    @ApiModelProperty(value="签名时间")
    private Date qmsj;

    /**
     * 签名ID
     */
    @ApiModelProperty(value="签名ID")
    private String qmid;

    /**
     * 操作结果
     */
    @ApiModelProperty(value="操作结果")
    private Integer czjg;


    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String bz;

    /**
     * 工作流实例ID
     */
    @ApiModelProperty(value = "工作流实例ID")
    String gzlslid;

    public String getShxxid() {
        return shxxid;
    }

    public void setShxxid(String shxxid) {
        this.shxxid = shxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid;
    }

    public Date getShkssj() {
        return shkssj;
    }

    public void setShkssj(Date shkssj) {
        this.shkssj = shkssj;
    }

    public Date getShjssj() {
        return shjssj;
    }

    public void setShjssj(Date shjssj) {
        this.shjssj = shjssj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public Date getQmsj() {
        return qmsj;
    }

    public void setQmsj(Date qmsj) {
        this.qmsj = qmsj;
    }

    public String getQmid() {
        return qmid;
    }

    public void setQmid(String qmid) {
        this.qmid = qmid;
    }

    public Integer getCzjg() {
        return czjg;
    }

    public void setCzjg(Integer czjg) {
        this.czjg = czjg;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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
        ZrzyShxxDO other = (ZrzyShxxDO) that;
        return (this.getShxxid() == null ? other.getShxxid() == null : this.getShxxid().equals(other.getShxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getJdmc() == null ? other.getJdmc() == null : this.getJdmc().equals(other.getJdmc()))
            && (this.getSxh() == null ? other.getSxh() == null : this.getSxh().equals(other.getSxh()))
            && (this.getShryxm() == null ? other.getShryxm() == null : this.getShryxm().equals(other.getShryxm()))
            && (this.getShrid() == null ? other.getShrid() == null : this.getShrid().equals(other.getShrid()))
            && (this.getShkssj() == null ? other.getShkssj() == null : this.getShkssj().equals(other.getShkssj()))
            && (this.getShjssj() == null ? other.getShjssj() == null : this.getShjssj().equals(other.getShjssj()))
            && (this.getShyj() == null ? other.getShyj() == null : this.getShyj().equals(other.getShyj()))
            && (this.getQmsj() == null ? other.getQmsj() == null : this.getQmsj().equals(other.getQmsj()))
            && (this.getQmid() == null ? other.getQmid() == null : this.getQmid().equals(other.getQmid()))
            && (this.getCzjg() == null ? other.getCzjg() == null : this.getCzjg().equals(other.getCzjg()))
            && (this.getBz() == null ? other.getBz() == null : this.getBz().equals(other.getBz()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShxxid() == null) ? 0 : getShxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getJdmc() == null) ? 0 : getJdmc().hashCode());
        result = prime * result + ((getSxh() == null) ? 0 : getSxh().hashCode());
        result = prime * result + ((getShryxm() == null) ? 0 : getShryxm().hashCode());
        result = prime * result + ((getShrid() == null) ? 0 : getShrid().hashCode());
        result = prime * result + ((getShkssj() == null) ? 0 : getShkssj().hashCode());
        result = prime * result + ((getShjssj() == null) ? 0 : getShjssj().hashCode());
        result = prime * result + ((getShyj() == null) ? 0 : getShyj().hashCode());
        result = prime * result + ((getQmsj() == null) ? 0 : getQmsj().hashCode());
        result = prime * result + ((getQmid() == null) ? 0 : getQmid().hashCode());
        result = prime * result + ((getCzjg() == null) ? 0 : getCzjg().hashCode());
        result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", shxxid=").append(shxxid);
        sb.append(", xmid=").append(xmid);
        sb.append(", jdmc=").append(jdmc);
        sb.append(", sxh=").append(sxh);
        sb.append(", shryxm=").append(shryxm);
        sb.append(", shrid=").append(shrid);
        sb.append(", shkssj=").append(shkssj);
        sb.append(", shjssj=").append(shjssj);
        sb.append(", shyj=").append(shyj);
        sb.append(", qmsj=").append(qmsj);
        sb.append(", qmid=").append(qmid);
        sb.append(", czjg=").append(czjg);
        sb.append(", bz=").append(bz);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }
}