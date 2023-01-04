package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2022/05/18
 * @description 不动产证书证明打印量
 */
@Table(name = "BDC_ZSZM_DYL")
public class BdcZszmDylDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;
    @ApiModelProperty(value = "组织编码")
    private String orgcode;
    @ApiModelProperty(value = "组织名称")
    private String orgname;
    @ApiModelProperty(value = "证书类型")
    private Integer zslx;
    @ApiModelProperty(value = "打印量")
    private Integer dyl;
    @ApiModelProperty(value = "打印人")
    private String dyr;
    @ApiModelProperty(value = "打印时间")
    private Date dysj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public Integer getDyl() {
        return dyl;
    }

    public void setDyl(Integer dyl) {
        this.dyl = dyl;
    }

    public String getDyr() {
        return dyr;
    }

    public void setDyr(String dyr) {
        this.dyr = dyr;
    }

    public Date getDysj() {
        return dysj;
    }

    public void setDysj(Date dysj) {
        this.dysj = dysj;
    }

    @Override
    public String toString() {
        return "BdcZszmDylDO{" +
                "id='" + id + '\'' +
                ", orgcode='" + orgcode + '\'' +
                ", orgname='" + orgname + '\'' +
                ", zslx=" + zslx +
                ", dyl=" + dyl +
                ", dyr='" + dyr + '\'' +
                ", dysj=" + dysj +
                '}';
    }
}
