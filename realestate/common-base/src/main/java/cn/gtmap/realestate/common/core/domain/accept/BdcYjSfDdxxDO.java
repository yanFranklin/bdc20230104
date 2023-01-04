package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/12
 * @description 不动产月结收费订单信息
 */
@Table(name = "BDC_YJSF_DDXX")
@ApiModel(value = "BdcYjSfDdxxDO", description = "不动产月结收费订单信息表")
public class BdcYjSfDdxxDO {

    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;
    @ApiModelProperty(value = "移交单号")
    private String yjdh;
    @ApiModelProperty(value = "订单生成时间")
    private Date ddscsj;
    @ApiModelProperty(value = "操作人姓名")
    private String czrxm;
    @ApiModelProperty(value = "订单状态")
    private Integer ddzt;
    @ApiModelProperty(value = "状态修改时间")
    private Date ztxgsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYjdh() {
        return yjdh;
    }

    public void setYjdh(String yjdh) {
        this.yjdh = yjdh;
    }

    public Date getDdscsj() {
        return ddscsj;
    }

    public void setDdscsj(Date ddscsj) {
        this.ddscsj = ddscsj;
    }

    public String getCzrxm() {
        return czrxm;
    }

    public void setCzrxm(String czrxm) {
        this.czrxm = czrxm;
    }

    public Integer getDdzt() {
        return ddzt;
    }

    public void setDdzt(Integer ddzt) {
        this.ddzt = ddzt;
    }

    public Date getZtxgsj() {
        return ztxgsj;
    }

    public void setZtxgsj(Date ztxgsj) {
        this.ztxgsj = ztxgsj;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcYjSfDdxxDO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", yjdh='").append(yjdh).append('\'');
        sb.append(", ddscsj='").append(ddscsj).append('\'');
        sb.append(", czrxm='").append(czrxm).append('\'');
        sb.append(", ddzt='").append(ddzt).append('\'');
        sb.append(", ztxgsj='").append(ztxgsj).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
