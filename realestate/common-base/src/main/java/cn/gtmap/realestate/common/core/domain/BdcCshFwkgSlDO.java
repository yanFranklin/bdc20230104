package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;
import java.util.Objects;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/5.
 * @description 初始化服务开关实例表
 */
@Table(name = "BDC_CSH_FWKG_SL")
@ApiModel(value = "BdcCshFwkgSlDO",description = "初始化服务开关实例表")
public class BdcCshFwkgSlDO extends BdcCshFwkgDO {

    @ApiModelProperty(value = "是否分别持证  0:否  1：是")
    private Integer sffbcz;

    @ApiModelProperty(value = "是否增量初始化  0:否  1：是")
    private Integer sfzlcsh;

    @ApiModelProperty(value = "证书序号：用于组合发证 分组")
    private Integer zsxh;

    @ApiModelProperty(value = "是否主房  0：否  1：是")
    private Integer sfzf;

    @ApiModelProperty(value = "是否还原已注销权利  0：否  1：是")
    private Integer sfhyyzxql;

    @ApiModelProperty(value = "是否换证  0：否  1：是")
    private Integer sfhz;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "是否生成证书  0：否  1：是")
    private Integer sfsczs;

    @ApiModelProperty(value = "是否生成家庭成员  0：否  1：是")
    private Integer sfscjtcy;

    @ApiModelProperty(value = "是否生成量化关系   0：否  1：是")
    private Integer sfsclhgx;

    @Override
    public Integer getSfsczs() {
        return sfsczs;
    }

    @Override
    public void setSfsczs(Integer sfsczs) {
        this.sfsczs = sfsczs;
    }

    public Integer getSfhz() {
        return sfhz;
    }

    public void setSfhz(Integer sfhz) {
        this.sfhz = sfhz;
    }

    public Integer getSfhyyzxql() {
        return sfhyyzxql;
    }

    public void setSfhyyzxql(Integer sfhyyzxql) {
        this.sfhyyzxql = sfhyyzxql;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getSfzf() {
        return sfzf;
    }

    public void setSfzf(Integer sfzf) {
        this.sfzf = sfzf;
    }

    public Integer getZsxh() {
        return zsxh;
    }

    public void setZsxh(Integer zsxh) {
        this.zsxh = zsxh;
    }

    public Integer getSfzlcsh() {
        return sfzlcsh;
    }

    public void setSfzlcsh(Integer sfzlcsh) {
        this.sfzlcsh = sfzlcsh;
    }

    public Integer getSffbcz() {
        return sffbcz;
    }

    public void setSffbcz(Integer sffbcz) {
        this.sffbcz = sffbcz;
    }

    @Override
    public Integer getSfscjtcy() {
        return sfscjtcy;
    }

    @Override
    public void setSfscjtcy(Integer sfscjtcy) {
        this.sfscjtcy = sfscjtcy;
    }

    public Integer getSfsclhgx() {
        return sfsclhgx;
    }

    public void setSfsclhgx(Integer sfsclhgx) {
        this.sfsclhgx = sfsclhgx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof BdcCshFwkgSlDO)){
            return false;
        }
        BdcCshFwkgSlDO that = (BdcCshFwkgSlDO) o;
        return Objects.equals(sffbcz, that.sffbcz) && Objects.equals(sfzlcsh, that.sfzlcsh) && Objects.equals(zsxh, that.zsxh) && Objects.equals(sfzf, that.sfzf) && Objects.equals(sfhyyzxql, that.sfhyyzxql) && Objects.equals(sfhz, that.sfhz) && Objects.equals(qllx, that.qllx) && Objects.equals(sfsczs, that.sfsczs) && Objects.equals(sfscjtcy, that.sfscjtcy) && Objects.equals(sfsclhgx, that.sfsclhgx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sffbcz, sfzlcsh, zsxh, sfzf, sfhyyzxql, sfhz, qllx, sfsczs, sfscjtcy, sfsclhgx);
    }
}
