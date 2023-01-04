package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/12
 * @description 不动产土地抵押释放单元号信息
 */
@Table(
        name = "BDC_TDDYSF_DYH"
)
@ApiModel(description = "不动产土地抵押释放单元号信息")
public class BdcTddysfDyhDO {

    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @ApiModelProperty(value = "生成方式")
    private Integer scfs;

    @ApiModelProperty(value = "例外原因")
    private String lwyy;

    @ApiModelProperty(value = "审核状态 0:新建 1:通过 2:不通过")
    private Integer shzt;

    @ApiModelProperty(value = "审核人")
    private String shr;

    @ApiModelProperty(value = "审核时间")
    private Date shsj;

    @ApiModelProperty(value = "审核意见")
    private String shyj;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Integer getScfs() {
        return scfs;
    }

    public void setScfs(Integer scfs) {
        this.scfs = scfs;
    }

    public String getLwyy() {
        return lwyy;
    }

    public void setLwyy(String lwyy) {
        this.lwyy = lwyy;
    }

    public Integer getShzt() {
        return shzt;
    }

    public void setShzt(Integer shzt) {
        this.shzt = shzt;
    }

    public String getShr() {
        return shr;
    }

    public void setShr(String shr) {
        this.shr = shr;
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "BdcTddysfDyhDO{" +
                "id='" + id + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", cjr='" + cjr + '\'' +
                ", cjsj=" + cjsj +
                ", scfs=" + scfs +
                ", lwyy='" + lwyy + '\'' +
                ", shzt=" + shzt +
                ", shr='" + shr + '\'' +
                ", shsj=" + shsj +
                ", shyj='" + shyj + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}
