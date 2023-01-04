package cn.gtmap.realestate.common.core.domain.inquiry;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/3/30
 * @description 质检单信息
 */
@Api(value = "BdcZjdDO", description = "不动产质检单信息")
@Table(name = "BDC_ZJD")
public class BdcZjdDO {

    @ApiModelProperty(value = "主键id")
    @Id
    private String zjdid;
    @ApiModelProperty(value = "质检单编号")
    private String zjdbh;
    @ApiModelProperty(value = "质检单创建时间")
    private Date zjdcjsj;
    @ApiModelProperty(value = "质检状态")
    private Integer zjzt;
    @ApiModelProperty(value = "质检人")
    private String zjr;

    public String getZjdid() {
        return zjdid;
    }

    public void setZjdid(String zjdid) {
        this.zjdid = zjdid;
    }

    public String getZjdbh() {
        return zjdbh;
    }

    public void setZjdbh(String zjdbh) {
        this.zjdbh = zjdbh;
    }

    public Date getZjdcjsj() {
        return zjdcjsj;
    }

    public void setZjdcjsj(Date zjdcjsj) {
        this.zjdcjsj = zjdcjsj;
    }

    public Integer getZjzt() {
        return zjzt;
    }

    public void setZjzt(Integer zjzt) {
        this.zjzt = zjzt;
    }

    public String getZjr() {
        return zjr;
    }

    public void setZjr(String zjr) {
        this.zjr = zjr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcZjdDO{");
        sb.append("zjdid='").append(zjdid).append('\'');
        sb.append(", zjdbh='").append(zjdbh).append('\'');
        sb.append(", zjdcjsj=").append(zjdcjsj);
        sb.append(", zjzt=").append(zjzt);
        sb.append(", zjr='").append(zjr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
