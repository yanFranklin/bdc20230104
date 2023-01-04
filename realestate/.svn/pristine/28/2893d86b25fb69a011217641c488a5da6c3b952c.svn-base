package cn.gtmap.realestate.exchange.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/5.
 * @description 初始化服务开关
 */
@Table(name = "BDC_CSH_FWKG")
@ApiModel(value = "BdcCshFwkgDO", description = "初始化服务开关")
public class BdcCshFwkgDO {

    @Id
    @ApiModelProperty(value = "主键(实例表中存储xmid)")
    private String id;
    @ApiModelProperty(value = "单一业务代码")
    private String djxl;
    @ApiModelProperty(value = "权利人数据来源 1：权籍 2：上一手权利人 3：上一手义务人")
    private Integer qlrsjly;
    @ApiModelProperty(value = "义务人数据来源 1：权籍 2：上一手权利人 3：上一手义务人")
    private Integer ywrsjly;
    @ApiModelProperty(value = "是否生成权利 0：否  1：是")
    private Integer sfscql;
    @ApiModelProperty(value = "权利数据来源 1：权籍 2：上一手  可组合(1,2)")
    private String qlsjly;
    @ApiModelProperty(value = "是否生成证书  0：否  1：是")
    private Integer sfsczs;
    @ApiModelProperty(value = "证书种类   1：证书  2：证明")
    private Integer zszl;
    @ApiModelProperty(value = "是否注销原权利   0：否  1：是")
    private Integer sfzxyql;
    @ApiModelProperty(value = "是否生成家庭成员   0：否  1：是")
    private Integer sfscjtcy;
    @ApiModelProperty(value = "是否生成量化关系   0：否  1：是")
    private Integer sfsclhgx;
    @ApiModelProperty(value = "是否更正登记   0：否  1：是")
    private Integer sfgzdj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }


    public Integer getQlrsjly() {
        return qlrsjly;
    }

    public void setQlrsjly(Integer qlrsjly) {
        this.qlrsjly = qlrsjly;
    }

    public Integer getYwrsjly() {
        return ywrsjly;
    }

    public void setYwrsjly(Integer ywrsjly) {
        this.ywrsjly = ywrsjly;
    }

    public Integer getSfscql() {
        return sfscql;
    }

    public void setSfscql(Integer sfscql) {
        this.sfscql = sfscql;
    }

    public String getQlsjly() {
        return qlsjly;
    }

    public void setQlsjly(String qlsjly) {
        this.qlsjly = qlsjly;
    }

    public Integer getSfsczs() {
        return sfsczs;
    }

    public void setSfsczs(Integer sfsczs) {
        this.sfsczs = sfsczs;
    }

    public Integer getZszl() {
        return zszl;
    }

    public void setZszl(Integer zszl) {
        this.zszl = zszl;
    }

    public Integer getSfzxyql() {
        return sfzxyql;
    }

    public void setSfzxyql(Integer sfzxyql) {
        this.sfzxyql = sfzxyql;
    }

    public Integer getSfscjtcy() {
        return sfscjtcy;
    }

    public void setSfscjtcy(Integer sfscjtcy) {
        this.sfscjtcy = sfscjtcy;
    }

    public Integer getSfsclhgx() {
        return sfsclhgx;
    }

    public void setSfsclhgx(Integer sfsclhgx) {
        this.sfsclhgx = sfsclhgx;
    }

    public Integer getSfgzdj() {
        return sfgzdj;
    }

    public void setSfgzdj(Integer sfgzdj) {
        this.sfgzdj = sfgzdj;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcCshFwkgDO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", djxl='").append(djxl).append('\'');
        sb.append(", qlrsjly=").append(qlrsjly);
        sb.append(", ywrsjly=").append(ywrsjly);
        sb.append(", sfscql=").append(sfscql);
        sb.append(", qlsjly='").append(qlsjly).append('\'');
        sb.append(", sfsczs=").append(sfsczs);
        sb.append(", zszl=").append(zszl);
        sb.append(", sfzxyql=").append(sfzxyql);
        sb.append(", sfscjtcy=").append(sfscjtcy);
        sb.append(", sfsclhgx=").append(sfsclhgx);
        sb.append('}');
        return sb.toString();
    }
}
